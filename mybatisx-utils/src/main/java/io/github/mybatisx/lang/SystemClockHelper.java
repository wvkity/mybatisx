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
package io.github.mybatisx.lang;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 单例时钟
 *
 * @author wvkity
 * @created 2021/12/19
 * @since 1.0.0
 */
public class SystemClockHelper {

    private final long period;
    private final AtomicLong nowRef;

    public SystemClockHelper(long period) {
        this.period = period;
        this.nowRef = new AtomicLong(System.currentTimeMillis());
        this.start();
    }

    private void start() {
        final long _$period = this.period;
        new ScheduledThreadPoolExecutor(1, it -> {
            final Thread thread = new Thread(it, "Singleton clock");
            thread.setDaemon(true);
            return thread;
        }).scheduleAtFixedRate(() -> this.nowRef.set(System.currentTimeMillis()),
                _$period, _$period, TimeUnit.MILLISECONDS);
    }

    public long currentTimeMillis() {
        return this.nowRef.get();
    }

    private static final class SingletonHolder {
        private static final SystemClockHelper INSTANCE = new SystemClockHelper(1L);
    }

    public static SystemClockHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static long currentTimestamp() {
        return getInstance().currentTimeMillis();
    }

}
