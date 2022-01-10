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
package io.github.mybatisx.base.dialect;

/**
 * Like方言
 *
 * @author wvkity
 * @created 2022/1/10
 * @since 1.0.0
 */
public interface Dialect {

    /**
     * 是否支持不区分大小比较like限制
     *
     * @return boolean
     */
    default boolean supportsCaseInsensitiveLike() {
        return false;
    }

    /**
     * 不区分大小写比较函数名称
     *
     * @return 函数名称
     */
    default String getCaseInsensitiveLike() {
        return "like";
    }

    /**
     * 将字符串转成小写函数名称
     *
     * @return 小写字符串函数名称
     */
    default String getLowercaseFunction() {
        return "lower";
    }

}
