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
package io.github.mybatisx.uid.snowflake.core;

import io.github.mybatisx.uid.snowflake.distributor.Distributor;

import java.util.concurrent.TimeUnit;

/**
 * ID生成核心接口
 *
 * @author wvkity
 * @created 2021/12/18
 * @since 1.0.0
 */
public interface Core extends Distributor {

    /**
     * 获取分配器
     *
     * @return 分配器
     */
    Distributor getDistributor();

    /**
     * 获取策略
     *
     * @return 策略
     */
    Policy getPolicy();

    /**
     * 获取时间单位
     *
     * @return 时间单位
     */
    TimeUnit getTimeUnit();

    /**
     * 获取标识位数
     *
     * @return 标识位数
     */
    int getSignBits();

    /**
     * 获取最大时间
     *
     * @return 最大时间
     */
    long getMaxTimestamp();

    /**
     * 获取最大机器标识
     *
     * @return 最大机器标识
     */
    long getMaxWorkerId();

    /**
     * 获取最大数据中心标识
     *
     * @return 最大数据中心标识
     */
    long getMaxDataCenterId();

    /**
     * 获取最大序列
     *
     * @return 最大序列
     */
    long getMaxSequence();

    /**
     * 获取是否使用缓存时刻
     *
     * @return boolean
     */
    boolean isUseClock();

    /**
     * 设置是否使用缓存时刻
     *
     * @param useClock 是否使用缓存时刻
     * @return boolean
     */
    boolean setUseClock(boolean useClock);

    /**
     * 获取当前时间戳
     *
     * @return 时间戳
     */
    long getTimestamp();

    /**
     * 生成唯一ID
     * <p>
     * (时间戳部分 | 数据中心部分 | 机器码部分 | 序列部分)
     *
     * @param deltaTime 偏移时间
     * @param sequence  序列
     * @return 唯一ID
     */
    long allocate(final long deltaTime, final long sequence);

}
