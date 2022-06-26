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

import io.github.mybatisx.core.criteria.support.LambdaCriteriaWrapper;
import io.github.mybatisx.core.property.Property;
import io.github.mybatisx.matcher.Matcher;

/**
 * 更新条件接口(支持Lambda表达式)
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
public interface LambdaUpdateCriteria<T, C extends LambdaUpdateCriteria<T, C>> extends Update<T>,
        LambdaCriteriaWrapper<T, C> {

    /**
     * 更新字段值
     *
     * @param property {@link Property}
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C set(final Property<T, V> property, final V value) {
        return this.set(property, value, null);
    }

    /**
     * 更新字段值
     *
     * @param property {@link Property}
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C set(final Property<T, V> property, final V value, final Matcher<V> matcher) {
        return this.set(this.convert(property), value, matcher);
    }

    /**
     * 更新字段值
     *
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C setIfAbsent(final Property<T, V> property, final V value) {
        return this.setIfAbsent(property, value, null);
    }

    /**
     * 更新字段值
     *
     * @param property 属性
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C setIfAbsent(final Property<T, V> property, final V value, final Matcher<V> matcher) {
        return this.setIfAbsent(this.convert(property), value, matcher);
    }

    /**
     * 更新字段值
     *
     * @param property 属性名
     * @param value    值
     * @return {@code this}
     */
    default C set(final String property, final Object value) {
        return this.set(property, value, null);
    }

    /**
     * 更新字段值
     *
     * @param property 属性名
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <V>      值类型
     * @return {@code this}
     */
    <V> C set(final String property, final V value, final Matcher<V> matcher);

    /**
     * 更新字段值
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C setIfAbsent(final String property, final Object value) {
        return this.setIfAbsent(property, value, null);
    }

    /**
     * 更新字段值
     *
     * @param property 属性
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <V>      值类型
     * @return {@code this}
     */
    <V> C setIfAbsent(final String property, final V value, final Matcher<V> matcher);

    /**
     * 更新字段值
     *
     * @param p1   属性1
     * @param v1   属性1对应值
     * @param p2   属性2
     * @param v2   属性2对应值
     * @param <V1> 属性1值类型
     * @param <V2> 属性2值类型
     * @return {@code this}
     */
    default <V1, V2> C set(final Property<T, V1> p1, final V1 v1, final Property<T, V2> p2, final V2 v2) {
        return this.set(p1, v1).set(p2, v2);
    }

    /**
     * 更新字段值
     *
     * @param p1   属性1
     * @param v1   属性1对应值
     * @param p2   属性2
     * @param v2   属性2对应值
     * @param p3   属性3
     * @param v3   属性3对应值
     * @param <V1> 属性1值类型
     * @param <V2> 属性2值类型
     * @param <V3> 属性3值类型
     * @return {@code this}
     */
    default <V1, V2, V3> C set(final Property<T, V1> p1, final V1 v1, final Property<T, V2> p2, final V2 v2,
                               final Property<T, V3> p3, final V3 v3) {
        return this.set(p1, v1).set(p2, v2).set(p3, v3);
    }

    /**
     * 更新字段值
     *
     * @param p1 属性1
     * @param v1 属性1对应值
     * @param p2 属性2
     * @param v2 属性2对应值
     * @return {@code this}
     */
    default C set(final String p1, final Object v1, final String p2, final Object v2) {
        return this.set(p1, v1).set(p2, v2);
    }

    /**
     * 更新字段值
     *
     * @param p1 属性1
     * @param v1 属性1对应值
     * @param p2 属性2
     * @param v2 属性2对应值
     * @param p3 属性3
     * @param v3 属性3对应值
     * @return {@code this}
     */
    default C set(final String p1, final Object v1, final String p2, final Object v2,
                  final String p3, final Object v3) {
        return this.set(p1, v1).set(p2, v2).set(p3, v3);
    }

}
