/*
 * Copyright (c) 2021-Now, wvkity(wvkity@gmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.mybatisx.uid.snowflake.twitter;

import io.github.mybatisx.lang.Maths;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.SingletonClock;
import io.github.mybatisx.uid.snowflake.SnowflakeException;
import io.github.mybatisx.uid.snowflake.core.Config;
import io.github.mybatisx.uid.snowflake.core.Core;
import io.github.mybatisx.uid.snowflake.core.Level;
import io.github.mybatisx.uid.snowflake.core.Policy;
import io.github.mybatisx.uid.snowflake.distributor.Distributor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ID生成核心
 *
 * @author wvkity
 * @created 2021/12/18
 * @since 1.0.0
 */
public class SequenceCore implements Core {

    private static final long serialVersionUID = 325812164239877694L;
    /**
     * 计算偏移量开始时间戳(2021-12-18 00:00:00)
     */
    public static final long DEFAULT_EPOCH_TIMESTAMP_SECOND = 1639756800L;
    /**
     * 计算偏移量开始时间戳(2021-12-18 00:00:00)
     */
    public static final long DEFAULT_EPOCH_TIMESTAMP_MILLISECOND = 1639756800000L;
    /**
     * 最大位数
     */
    public static final int DEFAULT_MAX_BITS = 1 << 6;
    /**
     * 分配器
     */
    private final Distributor distributor;
    /**
     * 策略
     */
    private final Policy policy;

    // [sign - timestamp - dataCenterId - workerId - sequence]

    /**
     * 标识位数
     */
    private final int signBits = 1;
    /**
     * 时间戳位数
     */
    private final int timestampBits;
    /**
     * 数据中心位数
     */
    private final int datacenterIdBits;
    /**
     * 机器标识位数
     */
    private final int workerIdBits;
    /**
     * 序列位数
     */
    private final int sequenceBits;
    /**
     * 最大时间
     */
    private final long maxTimestamp;
    /**
     * 最大机器标识
     */
    private final long maxWorkerId;
    /**
     * 最大数据中心标识
     */
    private final long maxDataCenterId;
    /**
     * 最大序列
     */
    private final long maxSequence;
    /**
     * 时间戳左移位数
     */
    private final long timestampShift;
    /**
     * 数据中心标识左移位数
     */
    private final long dataCenterIdShift;
    /**
     * 机器标识左移位数
     */
    private final long workerIdShift;
    /**
     * 起始时间
     */
    private final long epochTimestamp;
    /**
     * 数据中心标识
     */
    private final long datacenterId;
    /**
     * 机器标识
     */
    private final long workerId;
    /**
     * 是否使用缓存时刻
     */
    private final AtomicStampedReference<Boolean> useClockReference;
    /**
     * 时间单位
     */
    private final TimeUnit timeUnit;


    public SequenceCore(Config config) {
        final Distributor _$db = config.getDistributor();
        final Policy _$po = config.getPolicy();
        final Level _$level = config.getLevel();
        final long _$epochTimestamp = _$db.getEpochTimestamp() > 0 ? _$db.getEpochTimestamp() :
                (_$level == Level.MILLISECONDS ? DEFAULT_EPOCH_TIMESTAMP_MILLISECOND : DEFAULT_EPOCH_TIMESTAMP_SECOND);
        final int _$timestampBits = _$db.getTimestampBits();
        final int _$datacenterIdBits = _$db.getDataCenterIdBits();
        final int _$workerIdBits = _$db.getWorkerIdBits();
        final int _$sequenceBits = _$db.getSequenceBits();
        final int totalBits = this.signBits + _$timestampBits + _$datacenterIdBits + _$workerIdBits + _$sequenceBits;
        if (totalBits != DEFAULT_MAX_BITS) {
            throw new SnowflakeException("Allocate not enough 64 bits.");
        }
        final long _$workerId = _$db.getWorkerId();
        final long _$dataCenterId = _$db.getDataCenterId();
        final long _$maxWorkerId = Maths.shiftLeft(_$workerIdBits);
        final long _$maxDataCenterId = Maths.shiftLeft(_$datacenterIdBits);
        // 校验
        Objects.isTrue(_$workerId >= 0 && _$workerId < _$maxWorkerId,
                "Worker Id can not be greater than " + _$maxWorkerId + " or less than 0");
        Objects.isTrue(_$dataCenterId >= 0 || _$dataCenterId < _$maxDataCenterId,
                "DataCenterId can not be greater than " + _$dataCenterId + " or less than 0");
        this.distributor = _$db;
        this.policy = _$po;
        this.timestampBits = _$timestampBits;
        this.datacenterIdBits = _$datacenterIdBits;
        this.workerIdBits = _$workerIdBits;
        this.sequenceBits = _$sequenceBits;
        this.maxTimestamp = Maths.shiftLeft(_$timestampBits);
        this.maxWorkerId = _$maxWorkerId;
        this.maxDataCenterId = _$maxDataCenterId;
        this.maxSequence = Maths.shiftLeft(_$sequenceBits);
        this.timestampShift = _$workerIdBits + _$datacenterIdBits + _$sequenceBits;
        this.dataCenterIdShift = _$workerIdBits + _$sequenceBits;
        this.workerIdShift = _$sequenceBits;
        this.epochTimestamp = _$epochTimestamp;
        this.workerId = _$workerId;
        this.datacenterId = _$dataCenterId;
        this.useClockReference = new AtomicStampedReference<>(config.isUseClock(), 0);
        this.timeUnit = _$level == Level.SECONDS ? TimeUnit.SECONDS : TimeUnit.MILLISECONDS;
    }

    @Override
    public long getTimestamp() {
        final long now;
        if (this.isUseClock()) {
            now = SingletonClock.getTimestamp();
        } else {
            now = System.currentTimeMillis();
        }
        final long timestamp = this.timeUnit.convert(now, TimeUnit.MILLISECONDS);
        if ((timestamp - this.epochTimestamp) > this.maxTimestamp) {
            throw new SnowflakeException("Timestamp bits is exhausted. Refusing UID generate. Now: " + timestamp);
        }
        return timestamp;
    }

    @Override
    public long allocate(long deltaTime, long sequence) {
        return (deltaTime << this.timestampShift) | (this.datacenterId << this.dataCenterIdShift)
                | (this.workerId << this.workerIdShift) | sequence;
    }

    @Override
    public Distributor getDistributor() {
        return Objects.isNull(this.distributor) ? this : this.distributor;
    }

    @Override
    public Policy getPolicy() {
        return this.policy;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return this.timeUnit;
    }

    @Override
    public int getSignBits() {
        return this.signBits;
    }

    @Override
    public int getTimestampBits() {
        return this.timestampBits;
    }

    @Override
    public int getDataCenterIdBits() {
        return this.datacenterIdBits;
    }

    @Override
    public int getWorkerIdBits() {
        return this.workerIdBits;
    }

    @Override
    public int getSequenceBits() {
        return this.sequenceBits;
    }

    @Override
    public long getMaxTimestamp() {
        return this.maxTimestamp;
    }

    @Override
    public long getMaxWorkerId() {
        return this.maxWorkerId;
    }

    @Override
    public long getMaxDataCenterId() {
        return this.maxDataCenterId;
    }

    @Override
    public long getMaxSequence() {
        return this.maxSequence;
    }

    @Override
    public long getEpochTimestamp() {
        return this.epochTimestamp;
    }

    @Override
    public long getWorkerId() {
        return this.workerId;
    }

    @Override
    public long getDataCenterId() {
        return this.datacenterId;
    }

    @Override
    public boolean isUseClock() {
        return this.useClockReference.getReference();
    }

    @Override
    public boolean setUseClock(boolean useClock) {
        final int _$timestamp = this.useClockReference.getStamp();
        final boolean _$reference = this.useClockReference.getReference();
        return this.useClockReference.compareAndSet(_$reference, !_$reference, _$timestamp, _$timestamp + 1);
    }

    public static SequenceCore of(final Config config) {
        return new SequenceCore(config);
    }
}
