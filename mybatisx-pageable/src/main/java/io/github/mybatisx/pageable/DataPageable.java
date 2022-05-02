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
package io.github.mybatisx.pageable;

import io.github.mybatisx.util.Collections;

import java.util.Collection;
import java.util.List;

/**
 * 分页
 *
 * @param <T> 数据类型
 * @author wvkity
 * @created 2021/12/17
 * @since 1.0.0
 */
public interface DataPageable<T> extends Pageable {

    /**
     * 获取分页数据
     *
     * @return 分页数据
     */
    List<T> getData();

    /**
     * 设置分页数据
     *
     * @param data 分页数据
     */
    void setData(List<T> data);

    /**
     * 添加分页数据
     *
     * @param data 分页数据
     */
    void addAll(final Collection<T> data);

    /**
     * 获取数据元素个数
     *
     * @return 数据元素个数
     */
    default int getDataSize() {
        return Collections.size(this.getData());
    }

    /**
     * 数据是否为空
     *
     * @return boolean
     */
    default boolean isEmpty() {
        return Collections.isEmpty(this.getData());
    }
}
