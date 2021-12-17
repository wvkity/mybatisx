/*
 * Copyright (c) 2021-2023 wvkity(wvkity@gmail.com).
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
package io.github.mybatisx.result;

import java.io.Serializable;
import java.util.Objects;

/**
 * 数据模型
 *
 * @param <T> 数据类型
 * @author wvkity
 * @created 2021/12/15
 * @since 1.0.0
 */
public interface Data<T> extends Serializable {

    /**
     * 是否存在数据
     *
     * @return boolean
     */
    default boolean isNotEmpty() {
        return Objects.nonNull(this.getData());
    }

    /**
     * 获取返回数据
     *
     * @return 数据
     */
    T getData();

    /**
     * 设置返回数据
     *
     * @param data 数据
     */
    void setData(final T data);

}
