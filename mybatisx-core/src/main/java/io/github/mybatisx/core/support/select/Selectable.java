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
package io.github.mybatisx.core.support.select;

import io.github.mybatisx.base.fragment.Fragment;

/**
 * 查询列
 *
 * @author wvkity
 * @created 2022/1/18
 * @since 1.0.0
 */
public interface Selectable extends Fragment {

    /**
     * 获取字段名
     *
     * @return 字段名
     */
    String getColumn();

    /**
     * 获取别名
     *
     * @return 别名
     */
    String getAlias();

    /**
     * 查询列类型
     *
     * @return {@link SelectType}
     */
    SelectType getType();

    /**
     * 获取SQL片段
     *
     * @param isQuery 是否为查询
     * @return SQL片段
     */
    String getFragment(final boolean isQuery);

    /**
     * 获取属性名
     *
     * @return 属性名
     */
    default String getProperty() {
        return null;
    }

    /**
     * 获取引用属性
     *
     * @return 引用属性
     */
    default String getReference() {
        return null;
    }

    @Override
    default String getFragment() {
        return this.getFragment(true);
    }
}
