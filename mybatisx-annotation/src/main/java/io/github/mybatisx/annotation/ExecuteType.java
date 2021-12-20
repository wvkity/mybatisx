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
package io.github.mybatisx.annotation;

/**
 * 获取主键时机类型
 *
 * @author wvkity
 * @created 2021/12/20
 * @since 1.0.0
 */
public enum ExecuteType {

    /**
     * 依据全局配置
     */
    UNKNOWN,
    /**
     * 插入前执行
     */
    BEFORE,
    /**
     * 插入后执行
     */
    AFTER;

    /**
     * 是否插入前执行
     *
     * @return boolean
     */
    public boolean isBefore() {
        return this == BEFORE;
    }
}
