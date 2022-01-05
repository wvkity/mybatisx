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
import io.github.mybatisx.id.snowflake.core.Core;

import java.util.concurrent.locks.StampedLock;

/**
 * 默认ID生成器
 *
 * @author wvkity
 * @created 2021/12/19
 * @since 1.0.0
 */
public class DefaultSequence extends AbstractSequence implements SnowflakeSequence {

    private static final long serialVersionUID = -2361095701922407183L;
    /**
     * 锁
     */
    private final StampedLock lock = new StampedLock();

    public DefaultSequence(Core core) {
        super(core);
    }

    public DefaultSequence(Core core, Parser parser) {
        super(core, parser);
    }

    @Override
    public long nextId() {
        final StampedLock _$lock = this.lock;
        final long stamp = _$lock.writeLock(), uid;
        try {
            uid = this.nextValue();
        } finally {
            _$lock.unlockWrite(stamp);
        }
        return uid;
    }

    public static DefaultSequence of(final Core core) {
        return new DefaultSequence(core);
    }

    public static DefaultSequence of(final Core core, final Parser parser) {
        return new DefaultSequence(core, parser);
    }

}
