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

import io.github.mybatisx.uid.snowflake.Parser;
import io.github.mybatisx.uid.snowflake.SnowflakeException;
import io.github.mybatisx.uid.snowflake.Unique;
import io.github.mybatisx.uid.snowflake.core.Core;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 抽象雪花ID生成器
 *
 * @author wvkity
 * @created 2021/12/19
 * @since 1.0.0
 */
public abstract class AbstractSequence {

    /**
     * 序列
     */
    protected long sequence = 0L;
    /**
     * 最近一次时间
     */
    protected long lastTimestamp = -1L;
    /**
     * 核心接口
     */
    protected final Core core;
    /**
     * ID解析器
     */
    protected final Parser parser;

    public AbstractSequence(Core core) {
        this(core, new SequenceParser(core));
    }

    public AbstractSequence(Core core, Parser parser) {
        this.core = core;
        this.parser = parser;
    }

    protected long tilNextMills(final long lastTimestamp) {
        long timestamp = this.getTimestampValue();
        while (timestamp <= lastTimestamp) {
            timestamp = this.getTimestampValue();
        }
        return timestamp;
    }

    /**
     * 默认生成唯一ID
     *
     * @return 唯一ID
     */
    protected long nextValue() {
        long newTimestamp = this.getTimestampValue();
        final long _$lastTimestamp = this.lastTimestamp;
        if (newTimestamp < _$lastTimestamp) {
            throw new SnowflakeException("Clock moved backwards. Refusing for " +
                    (_$lastTimestamp - newTimestamp) + " timeStamp");
        }
        long _$sequence = this.sequence;
        if (newTimestamp == _$lastTimestamp) {
            _$sequence = (_$sequence + 1L) & this.core.getMaxSequence();
            if (_$sequence == 0) {
                newTimestamp = this.tilNextMills(_$lastTimestamp);
            }
        } else {
            _$sequence = ThreadLocalRandom.current().nextLong(1L, 3L);
        }
        this.lastTimestamp = newTimestamp;
        this.sequence = _$sequence;
        return this.core.allocate(newTimestamp - this.core.getEpochTimestamp(), _$sequence);
    }

    long getTimestampValue() {
        return this.core.getTimestamp();
    }

    /**
     * 获取当前时间戳
     *
     * @return 时间戳
     */
    public Long getTimestamp() {
        return this.core.getTimestamp();
    }

    /**
     * 解析ID信息
     *
     * @param id 唯一ID
     * @return {@link Unique}
     */
    public Unique parse(final long id) {
        return this.parser.parse(id);
    }

    /**
     * 设置是否使用缓存时刻
     *
     * @param useClock 是否使用缓存时刻
     * @return boolean
     */
    public boolean setUseClock(final boolean useClock) {
        return this.core.setUseClock(useClock);
    }

}
