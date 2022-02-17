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
import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.core.property.LambdaMetadataWeakCache;
import io.github.mybatisx.core.property.Property;
import io.github.mybatisx.matcher.Matcher;
import io.github.mybatisx.util.Maps;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * 公共通用条件
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/10
 * @since 1.0.0
 */
public interface GenericCondition<T, C extends GenericCondition<T, C>> extends Slot<T, C> {


    // region Primary key equal methods 

    /**
     * 主键等于
     *
     * @param value 值
     * @param <V>   值类型
     * @return {@code this}
     */
    default <V> C idEq(final V value) {
        return this.idEq(value, null, this.slot());
    }

    /**
     * 主键等于
     *
     * @param value   值
     * @param matcher {@link Matcher}
     * @param <V>     值类型
     * @return {@code this}
     */
    default <V> C idEq(final V value, final Matcher<V> matcher) {
        return this.idEq(value, matcher, this.slot());
    }

    /**
     * 主键等于
     *
     * @param value 值
     * @param slot  {@link LogicSymbol}
     * @param <V>   值类型
     * @return {@code this}
     */
    default <V> C idEq(final V value, final LogicSymbol slot) {
        return this.idEq(value, null, slot);
    }

    /**
     * 主键等于
     *
     * @param value   值
     * @param matcher {@link Matcher}
     * @param <V>     值类型
     * @param slot    {@link LogicSymbol}
     * @return {@code this}
     */
    <V> C idEq(final V value, final Matcher<V> matcher, final LogicSymbol slot);

    // endregion

    // region Template methods

    /**
     * 模板条件
     *
     * @param template 模板
     * @param value    值
     * @return {@code this}
     */
    default C template(final String template, final Object value) {
        return this.template(template, value, this.slot());
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C template(final String template, final Object value, final LogicSymbol slot);

    /**
     * 模板条件
     *
     * @param template 模板
     * @param values   值列表
     * @return {@code this}
     */
    default C template(final String template, final Object... values) {
        return this.template(template, this.slot(), values);
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param slot     {@link LogicSymbol}
     * @param values   值列表
     * @return {@code this}
     */
    default C template(final String template, final LogicSymbol slot, final Object... values) {
        return this.template(template, Arrays.asList(values), slot);
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param values   值列表
     * @return {@code this}
     */
    default C template(final String template, final Collection<Object> values) {
        return this.template(template, values, this.slot());
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C template(final String template, final Collection<Object> values, final LogicSymbol slot);

    /**
     * 模板条件
     *
     * @param template 模板
     * @param k1       占位符1
     * @param v1       占位符1对应值
     * @return {@code this}
     */
    default C template(final String template, final String k1, final Object v1) {
        return this.template(template, k1, v1, this.slot());
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param k1       占位符1
     * @param v1       占位符1对应值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C template(final String template, final String k1, final Object v1, final LogicSymbol slot) {
        return this.template(template, Maps.of(k1, v1), slot);
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param k1       占位符1
     * @param v1       占位符1对应值
     * @param k2       占位符2
     * @param v2       占位符2对应值
     * @return {@code this}
     */
    default C template(final String template, final String k1, final Object v1, final String k2, final Object v2) {
        return this.template(template, k1, v1, k2, v2, this.slot());
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param k1       占位符1
     * @param v1       占位符1对应值
     * @param k2       占位符2
     * @param v2       占位符2对应值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C template(final String template, final String k1, final Object v1, final String k2, final Object v2,
                       final LogicSymbol slot) {
        return this.template(template, Maps.of(k1, v1, k2, v2), slot);
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param k1       占位符1
     * @param v1       占位符1对应值
     * @param k2       占位符2
     * @param v2       占位符2对应值
     * @param k3       占位符3
     * @param v3       占位符3对应值
     * @return {@code this}
     */
    default C template(final String template, final String k1, final Object v1, final String k2, final Object v2,
                       final String k3, final Object v3) {
        return this.template(template, k1, v1, k2, v2, k3, v3, this.slot());
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param k1       占位符1
     * @param v1       占位符1对应值
     * @param k2       占位符2
     * @param v2       占位符2对应值
     * @param k3       占位符3
     * @param v3       占位符3对应值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C template(final String template, final String k1, final Object v1, final String k2, final Object v2,
                       final String k3, final Object v3, final LogicSymbol slot) {
        return this.template(template, Maps.of(k1, v1, k2, v2, k3, v3), slot);
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param values   值列表
     * @return {@code this}
     */
    default C template(final String template, final Map<String, Object> values) {
        return this.template(template, values, this.slot());
    }

    /**
     * 模板条件
     *
     * @param template 模板
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C template(final String template, final Map<String, Object> values, final LogicSymbol slot);

    // endregion

}
