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
package io.github.mybatisx.id.snowflake.core;

import io.github.mybatisx.id.snowflake.distributor.Distributor;

import java.io.Serializable;

/**
 * 配置接口
 *
 * @author wvkity
 * @created 2021/12/18
 * @since 1.0.0
 */
public interface Config extends Serializable {

    /**
     * 获取级别
     * <p>
     * 默认值: {@link Level#MILLISECONDS}
     *
     * @return {@link Level}
     */
    default Level getLevel() {
        return Level.MILLISECONDS;
    }

    /**
     * 获取是否使用缓存时刻
     *
     * @return boolean
     */
    default boolean isUseClock() {
        return false;
    }

    /**
     * 获取策略
     *
     * @return 策略
     */
    Policy getPolicy();

    /**
     * 获取分配器
     *
     * @return 分配器
     */
    Distributor getDistributor();

}
