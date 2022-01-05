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
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.id.snowflake.core.Core;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.StampedLock;

/**
 * 唯一ID生成器(批量生成缓存)
 *
 * @author wvkity
 * @created 2021/12/19
 * @since 1.0.0
 */
public class LocalCacheSequence extends AbstractSequence implements SnowflakeSequence {

    private static final long serialVersionUID = 5006936822283696581L;
    /**
     * 默认缓存大小
     */
    private static final int DEFAULT_CACHE_SIZE = 1 << 8;
    /**
     * 锁
     */
    private final StampedLock lock = new StampedLock();
    /**
     * 批量大小
     */
    private final int cacheSize;
    /**
     * 队列
     */
    private final Queue<Long> queue;

    public LocalCacheSequence(Core core) {
        this(core, new SequenceParser(core), DEFAULT_CACHE_SIZE);
    }

    public LocalCacheSequence(Core core, int cacheSize) {
        this(core, new SequenceParser(core), cacheSize);
    }

    public LocalCacheSequence(Core core, Parser parser, int cacheSize) {
        super(core, parser);
        this.cacheSize = cacheSize < 8 ? DEFAULT_CACHE_SIZE : cacheSize;
        this.queue = new ConcurrentLinkedDeque<>();
        this.batchGen(this.cacheSize);
    }

    @Override
    public long nextId() {
        Long uid;
        final Queue<Long> _$queue = this.queue;
        while (Objects.isNull(uid = _$queue.poll())) {
            final StampedLock _$lock = this.lock;
            final long stamp = _$lock.writeLock();
            try {
                if (Objects.isNull(_$queue.peek())) {
                    this.batchGen(this.cacheSize);
                }
            } finally {
                _$lock.unlockWrite(stamp);
            }
        }
        return uid;
    }

    void batchGen(final int size) {
        for (int i = 0; i < size; i++) {
            this.queue.offer(this.nextValue());
        }
    }

    public static LocalCacheSequence of(final Core core) {
        return new LocalCacheSequence(core);
    }

    public static LocalCacheSequence of(final Core core, final int cacheSize) {
        return new LocalCacheSequence(core, cacheSize);
    }

    public static LocalCacheSequence of(final Core core, final Parser parser, final int cacheSize) {
        return new LocalCacheSequence(core, parser, cacheSize);
    }

}
