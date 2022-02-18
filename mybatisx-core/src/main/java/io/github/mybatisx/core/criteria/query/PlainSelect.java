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

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * 查询字段接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/24
 * @since 1.0.0
 */
public interface PlainSelect<T, C extends PlainSelect<T, C>> {

    /**
     * 添加查询列
     *
     * @param column 子弹名
     * @return {@code this}
     */
    default C colSelect(final String column) {
        return this.colSelect(column, null);
    }

    /**
     * 添加查询列
     *
     * @param column 子弹名
     * @param alias  别名
     * @return {@code this}
     */
    C colSelect(final String column, final String alias);

    /**
     * 添加查询列
     *
     * @param c1  字段1
     * @param as1 字段1对应别名
     * @param c2  字段2
     * @param as2 字段2对应别名
     * @return {@code this}
     */
    default C colSelect(final String c1, final String as1, final String c2, final String as2) {
        return this.colSelect(c1, as1).colSelect(c2, as2);
    }

    /**
     * 添加查询列
     *
     * @param c1  字段1
     * @param as1 字段1对应别名
     * @param c2  字段2
     * @param as2 字段2对应别名
     * @param c3  字段3
     * @param as3 字段3对应别名
     * @return {@code this}
     */
    default C colSelect(final String c1, final String as1, final String c2, final String as2,
                        final String c3, final String as3) {
        return this.colSelect(c1, as1).colSelect(c2, as2).colSelect(c3, as3);
    }

    /**
     * 添加查询列
     *
     * @param columns 字段列表
     * @return {@code this}
     */
    default C colSelects(final String... columns) {
        return this.colSelects(Arrays.asList(columns));
    }

    /**
     * 添加查询列
     *
     * @param columns 字段列表
     * @return {@code this}
     */
    C colSelects(final Collection<String> columns);

    /**
     * 添加查询列
     *
     * @param columns 多个字段(Map&lt;别名, 字段名&gt;)
     * @return {@code this}
     */
    C colSelects(final Map<String, String> columns);

    /**
     * 排除查询列
     *
     * @param column 字段名
     * @return {@code this}
     */
    C excludeColumn(final String column);

    /**
     * 排除查询列
     *
     * @param columns 字段名列表
     * @return {@code this}
     */
    C excludeColumns(final Collection<String> columns);

}
