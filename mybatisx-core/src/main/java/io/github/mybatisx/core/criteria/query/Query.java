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
package io.github.mybatisx.core.criteria.query;

import io.github.mybatisx.core.criteria.GenericCriteria;

/**
 * 查询条件接口
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2021/12/23
 * @since 1.0.0
 */
public interface Query<T> extends GenericCriteria<T> {

    /**
     * 设置表别名
     *
     * @param alias 表别名
     * @return {@code this}
     */
    Query<T> as(final String alias);

    /**
     * 获取引用属性
     *
     * @return 引用属性
     */
    String getReference();

    /**
     * 获取查询字段片段
     *
     * @return 查询字段片段
     */
    String getSelectFragment();

    /**
     * 获取分组片段
     *
     * @return 分组片段
     */
    String getGroupFragment();

}
