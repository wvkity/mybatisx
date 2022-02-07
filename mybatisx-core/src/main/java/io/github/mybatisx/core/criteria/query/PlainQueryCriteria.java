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

import io.github.mybatisx.core.criteria.CriteriaWrapper;

import java.util.Arrays;
import java.util.List;

/**
 * 查询条件接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
public interface PlainQueryCriteria<T, C extends PlainQueryCriteria<T, C>> extends CriteriaWrapper<T, C>, Query<T>,
        PlainSelect<T, C>, PlainFunctionSelect<T, C>, PlainSort<T, C>, PlainHaving<T, C> {

    /**
     * 分组
     *
     * @param column 字段名
     * @return {@code this}
     */
    C colGroup(final String column);

    /**
     * 分组
     *
     * @param columns 字段列表
     * @return {@code this}
     */
    default C colGroups(final String... columns) {
        return this.colGroups(Arrays.asList(columns));
    }

    /**
     * 分组
     *
     * @param columns 字段列表
     * @return {@code this}
     */
    C colGroups(final List<String> columns);

}
