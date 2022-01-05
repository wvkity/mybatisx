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
package io.github.mybatisx.id.snowflake.twitter;

import io.github.mybatisx.id.snowflake.Parser;
import io.github.mybatisx.id.snowflake.SnowflakeSequence;
import io.github.mybatisx.id.snowflake.SnowflakeException;
import io.github.mybatisx.id.snowflake.core.Core;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 唯一ID生成器
 *
 * @author wvkity
 * @created 2021/12/19
 * @since 1.0.0
 */
public class AtomicSequence extends AbstractSequence implements SnowflakeSequence {

    private static final long serialVersionUID = -8870928790782666635L;

    private final AtomicStampedReference<Long> reference = new AtomicStampedReference<>(-1L, 0);

    public AtomicSequence(Core core) {
        super(core);
    }

    public AtomicSequence(Core core, Parser parser) {
        super(core, parser);
    }

    @Override
    public long nextId() {
        Long newTimestamp, oldTimestamp;
        int newStamp, oldStamp;
        final Core _$core = this.core;
        final AtomicStampedReference<Long> _$ref = this.reference;
        final long maxSequence = _$core.getMaxSequence();
        for (; ; ) {
            oldStamp = _$ref.getStamp();
            oldTimestamp = _$ref.getReference();
            newTimestamp = this.getTimestamp();
            if (newTimestamp < oldTimestamp) {
                throw new SnowflakeException("Clock moved backwards. Refusing for " + (oldTimestamp - newTimestamp) + " timeStamp");
            }
            if (newTimestamp.longValue() == oldTimestamp.longValue()) {
                newStamp = (int) ((oldStamp + 1) & maxSequence);
                if (newStamp == 0) {
                    newTimestamp = this.tilNextMills(oldTimestamp);
                }
            } else {
                newStamp = ThreadLocalRandom.current().nextInt(1, 3);
            }
            if (_$ref.compareAndSet(oldTimestamp, newTimestamp, oldStamp, newStamp)) {
                this.lastTimestamp = newTimestamp;
                this.sequence = newStamp;
                return _$core.allocate((newTimestamp - _$core.getEpochTimestamp()), newStamp);
            }
        }
    }

    public static AtomicSequence of(final Core core) {
        return new AtomicSequence(core);
    }

    public static AtomicSequence of(final Core core, final Parser parser) {
        return new AtomicSequence(core, parser);
    }

}
