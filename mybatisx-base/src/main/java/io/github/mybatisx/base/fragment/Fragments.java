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
package io.github.mybatisx.base.fragment;

import java.util.Collection;

/**
 * 片段列表
 *
 * @param <T> 片段类型
 * @author wvkity
 * @created 2021/12/23
 * @since 1.0.0
 */
public interface Fragments<T> extends Fragment {

    /**
     * 添加片段
     *
     * @param t 片段
     */
    void add(final T t);

    /**
     * 添加多个片段
     *
     * @param c 片段列表
     */
    void addAll(final Collection<? extends T> c);

    /**
     * 检查片段列表是否为空
     *
     * @return boolean
     */
    boolean isEmpty();

    /**
     * 获取所有片段
     *
     * @return 片段列表
     */
    Collection<T> getAll();
}
