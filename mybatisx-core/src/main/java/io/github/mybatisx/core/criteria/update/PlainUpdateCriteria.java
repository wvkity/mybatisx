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
package io.github.mybatisx.core.criteria.update;

import io.github.mybatisx.core.criteria.support.PlainCriteriaWrapper;
import io.github.mybatisx.matcher.Matcher;

/**
 * 更新条件接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
public interface PlainUpdateCriteria<T, C extends PlainUpdateCriteria<T, C>> extends Update<T>,
        PlainCriteriaWrapper<T, C> {


    /**
     * 更新字段值
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colSet(final String column, final Object value) {
        return this.colSet(column, value, null);
    }

    /**
     * 更新字段值
     *
     * @param column  字段名
     * @param value   值
     * @param matcher {@link Matcher}
     * @param <V>     值类型
     * @return {@code this}
     */
    <V> C colSet(final String column, final V value, final Matcher<V> matcher);

    /**
     * 更新字段值
     *
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default C colSetIfAbsent(final String column, final Object value) {
        return this.colSetIfAbsent(column, value, null);
    }

    /**
     * 更新字段值
     *
     * @param column  字段
     * @param value   值
     * @param matcher {@link Matcher}
     * @param <V>     值类型
     * @return {@code this}
     */
    <V> C colSetIfAbsent(final String column, final V value, final Matcher<V> matcher);

    /**
     * 更新字段值
     *
     * @param c1 字段1
     * @param v1 字段1对应值
     * @param c2 字段2
     * @param v2 字段2对应值
     * @return {@code this}
     */
    default C colSet(final String c1, final Object v1, final String c2, final Object v2) {
        return this.colSet(c1, v1).colSet(c2, v2);
    }

    /**
     * 更新字段值
     *
     * @param c1 字段1
     * @param v1 字段1对应值
     * @param c2 字段2
     * @param v2 字段2对应值
     * @param c3 字段3
     * @param v3 字段3对应值
     * @return {@code this}
     */
    default C colSet(final String c1, final Object v1, final String c2, final Object v2,
                     final String c3, final Object v3) {
        return this.colSet(c1, v1).colSet(c2, v2).colSet(c3, v3);
    }
}
