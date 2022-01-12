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
package io.github.mybatisx.core.criteria.support;

import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.util.Maps;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * 模板条件接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/11
 * @since 1.0.0
 */
public interface PlainTemplate<T, C extends PlainTemplate<T, C>> extends Slot<T, C> {

    /**
     * 模板条件
     *
     * @param template 模板
     * @param column   字段名
     * @param values   值列表
     * @return {@code this}
     */
    default C colTemplate(final String template, final String column, final Object... values) {
        return this.colTemplate(template, column, this.slot(), values);
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param column   字段名
     * @param slot     {@link LogicSymbol}
     * @param values   值列表
     * @return {@code this}
     */
    default C colTemplate(final String template, final String column, final LogicSymbol slot,
                          final Object... values) {
        return this.colTemplate(template, column, Arrays.asList(values), slot);
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param column   字段名
     * @param values   值列表
     * @return {@code this}
     */
    default C colTemplate(final String template, final String column, final Collection<Object> values) {
        return this.colTemplate(template, column, values, this.slot());
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param column   字段名
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colTemplate(final String template, final String column, final Collection<Object> values,
                  final LogicSymbol slot);

    /**
     * 模板条件
     *
     * @param template 模板
     * @param column   字段名
     * @param k1       占位符1
     * @param v1       占位符1对应值
     * @return {@code this}
     */
    default C colTemplate(final String template, final String column, final String k1, final Object v1) {
        return this.colTemplate(template, column, k1, v1, this.slot());
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param column   字段名
     * @param k1       占位符1
     * @param v1       占位符1对应值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C colTemplate(final String template, final String column, final String k1, final Object v1,
                          final LogicSymbol slot) {
        return this.colTemplate(template, column, Maps.of(k1, v1), slot);
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param column   字段名
     * @param k1       占位符1
     * @param v1       占位符1对应值
     * @param k2       占位符2
     * @param v2       占位符2对应值
     * @return {@code this}
     */
    default C colTemplate(final String template, final String column, final String k1, final Object v1,
                          final String k2, final Object v2) {
        return this.colTemplate(template, column, k1, v1, k2, v2, this.slot());
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param column   字段名
     * @param k1       占位符1
     * @param v1       占位符1对应值
     * @param k2       占位符2
     * @param v2       占位符2对应值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C colTemplate(final String template, final String column, final String k1, final Object v1,
                          final String k2, final Object v2, final LogicSymbol slot) {
        return this.colTemplate(template, column, Maps.of(k1, v1, k2, v2), slot);
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param column   字段名
     * @param k1       占位符1
     * @param v1       占位符1对应值
     * @param k2       占位符2
     * @param v2       占位符2对应值
     * @param k3       占位符3
     * @param v3       占位符3对应值
     * @return {@code this}
     */
    default C colTemplate(final String template, final String column, final String k1, final Object v1,
                          final String k2, final Object v2, final String k3, final Object v3) {
        return this.colTemplate(template, column, k1, v1, k2, v2, k3, v3, this.slot());
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param column   字段名
     * @param k1       占位符1
     * @param v1       占位符1对应值
     * @param k2       占位符2
     * @param v2       占位符2对应值
     * @param k3       占位符3
     * @param v3       占位符3对应值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C colTemplate(final String template, final String column, final String k1, final Object v1,
                          final String k2, final Object v2, final String k3, final Object v3, final LogicSymbol slot) {
        return this.colTemplate(template, column, Maps.of(k1, v1, k2, v2, k3, v3), slot);
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param column   字段名
     * @param values   值列表
     * @return {@code this}
     */
    default C colTemplate(final String template, final String column, final Map<String, Object> values) {
        return this.colTemplate(template, column, values, this.slot());
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param column   字段名
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colTemplate(final String template, final String column, final Map<String, Object> values,
                  final LogicSymbol slot);

}
