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
import io.github.mybatisx.core.property.Property;
import io.github.mybatisx.core.property.PropertyConverter;
import io.github.mybatisx.matcher.Matcher;

import java.util.Map;

/**
 * 比较条件接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
public interface LambdaCompare<T, C extends LambdaCompare<T, C>> extends Slot<T, C>, PropertyConverter<T> {

    // region Equal methods 

    // region Single property

    /**
     * 等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C eq(final Property<T, V> property, final V value) {
        return this.eq(property, value, this.slot());
    }

    /**
     * 等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param matcher  匹配器
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C eq(final Property<T, V> property, final V value, final Matcher<V> matcher) {
        return this.eq(property, value, matcher, this.slot());
    }

    /**
     * 等于
     *
     * @param slot     {@link LogicSymbol}
     * @param property {@link Property}
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C eq(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.eq(property, value, (Matcher<V>) null, slot);
    }

    /**
     * 等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param matcher  匹配器
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C eq(final Property<T, V> property, final V value, final Matcher<V> matcher, final LogicSymbol slot) {
        return this.eq(this.convert(property), value, matcher, slot);
    }

    /**
     * 等于
     *
     * @param property 属性名
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C eq(final String property, final V value) {
        return this.eq(property, value, this.slot());
    }

    /**
     * 等于
     *
     * @param property 属性名
     * @param value    值
     * @param matcher  匹配器
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C eq(final String property, final V value, final Matcher<V> matcher) {
        return this.eq(property, value, matcher, this.slot());
    }

    /**
     * 等于
     *
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C eq(final String property, final V value, final LogicSymbol slot) {
        return this.eq(property, value, (Matcher<? super V>) null, slot);
    }

    /**
     * 等于
     *
     * @param slot     {@link LogicSymbol}
     * @param property 属性名
     * @param value    值
     * @param matcher  匹配器
     * @param <V>      值类型
     * @return {@code this}
     */
    <V> C eq(final String property, final V value, final Matcher<V> matcher, final LogicSymbol slot);

    // endregion

    // region Multi properties

    /**
     * 等于
     *
     * @param p1   属性1
     * @param v1   属性1对应值
     * @param p2   属性2
     * @param v2   属性2对应值
     * @param <V1> 属性1类型
     * @param <V2> 属性2类型
     * @param <V3> 属性3类型
     * @return {@code this}
     */
    default <V1, V2, V3> C eq(final Property<T, V1> p1, final V1 v1, final Property<T, V2> p2, final V2 v2) {
        return this.eq(p1, v1, p2, v2, this.slot());
    }

    /**
     * 等于
     *
     * @param p1   属性1
     * @param v1   属性1对应值
     * @param p2   属性2
     * @param v2   属性2对应值
     * @param slot {@link LogicSymbol}
     * @param <V1> 属性1类型
     * @param <V2> 属性2类型
     * @param <V3> 属性3类型
     * @return {@code this}
     */
    default <V1, V2, V3> C eq(final Property<T, V1> p1, final V1 v1, final Property<T, V2> p2, final V2 v2,
                              final LogicSymbol slot) {
        return this.eq(p1, v1, slot).eq(p2, v2, slot);
    }

    /**
     * 等于
     *
     * @param p1   属性1
     * @param v1   属性1对应值
     * @param p2   属性2
     * @param v2   属性2对应值
     * @param p3   属性3
     * @param v3   属性3对应值
     * @param <V1> 属性1类型
     * @param <V2> 属性2类型
     * @param <V3> 属性3类型
     * @return {@code this}
     */
    default <V1, V2, V3> C eq(final Property<T, V1> p1, final V1 v1, final Property<T, V2> p2, final V2 v2,
                              final Property<T, V3> p3, final V3 v3) {
        return this.eq(p1, v1, p2, v2, p3, v3, this.slot());
    }

    /**
     * 等于
     *
     * @param p1   属性1
     * @param v1   属性1对应值
     * @param p2   属性2
     * @param v2   属性2对应值
     * @param p3   属性3
     * @param v3   属性3对应值
     * @param slot {@link LogicSymbol}
     * @param <V1> 属性1类型
     * @param <V2> 属性2类型
     * @param <V3> 属性3类型
     * @return {@code this}
     */
    default <V1, V2, V3> C eq(final Property<T, V1> p1, final V1 v1, final Property<T, V2> p2, final V2 v2,
                              final Property<T, V3> p3, final V3 v3, final LogicSymbol slot) {
        return this.eq(p1, v1, slot).eq(p2, v2, slot).eq(p3, v3, slot);
    }

    /**
     * 等于
     *
     * @param p1 属性1
     * @param v1 属性1对应值
     * @param p2 属性2
     * @param v2 属性2对应值
     * @return {@code this}
     */
    default C eq(final String p1, final Object v1, final String p2, final Object v2) {
        return this.eq(p1, v1, p2, v2, this.slot());
    }

    /**
     * 等于
     *
     * @param p1   属性1
     * @param v1   属性1对应值
     * @param p2   属性2
     * @param v2   属性2对应值
     * @param slot {@link LogicSymbol}
     * @return {@code this}
     */
    default C eq(final String p1, final Object v1,
                 final String p2, final Object v2, final LogicSymbol slot) {
        return this.eq(p1, v1, slot).eq(p2, v2, slot);
    }

    /**
     * 等于
     *
     * @param p1 属性1
     * @param v1 属性1对应值
     * @param p2 属性2
     * @param v2 属性2对应值
     * @param p3 属性3
     * @param v3 属性3对应值
     * @return {@code this}
     */
    default C eq(final String p1, final Object v1,
                 final String p2, final Object v2, final String p3, final Object v3) {
        return this.eq(p1, v1, p2, v2, p3, v3, this.slot());
    }

    /**
     * 等于
     *
     * @param p1   属性1
     * @param v1   属性1对应值
     * @param p2   属性2
     * @param v2   属性2对应值
     * @param p3   属性3
     * @param v3   属性3对应值
     * @param slot {@link LogicSymbol}
     * @return {@code this}
     */
    default C eq(final String p1, final Object v1,
                 final String p2, final Object v2, final String p3, final Object v3, final LogicSymbol slot) {
        return this.eq(p1, v1, slot).eq(p2, v2, slot).eq(p3, v3, slot);
    }

    /**
     * 等于
     *
     * @param properties 属性-值集合
     * @return {@code this}
     */
    default C eq(final Map<String, Object> properties) {
        return this.eq(properties, this.slot());
    }

    /**
     * 等于
     *
     * @param slot       {@link LogicSymbol}
     * @param properties 属性-值集合
     * @return {@code this}
     */
    C eq(final Map<String, Object> properties, final LogicSymbol slot);

    // endregion

    // endregion

    // region Not equal methods

    /**
     * 不等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C ne(final Property<T, V> property, final V value) {
        return this.ne(this.convert(property), value, this.slot());
    }

    /**
     * 不等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C ne(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.ne(property, value, null, slot);
    }

    /**
     * 不等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param matcher  匹配器
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C ne(final Property<T, V> property, final V value, final Matcher<V> matcher) {
        return this.ne(property, value, matcher, this.slot());
    }

    /**
     * 不等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param matcher  匹配器
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C ne(final Property<T, V> property, final V value, final Matcher<V> matcher, final LogicSymbol slot) {
        return this.ne(this.convert(property), value, matcher, slot);
    }

    /**
     * 不等于
     *
     * @param property 属性名
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C ne(final String property, final V value) {
        return this.ne(property, value, this.slot());
    }

    /**
     * 不等于
     *
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C ne(final String property, final V value, final LogicSymbol slot) {
        return this.ne(property, value, null, slot);
    }

    /**
     * 不等于
     *
     * @param property 属性名
     * @param value    值
     * @param matcher  匹配器
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C ne(final String property, final V value, final Matcher<V> matcher) {
        return this.ne(property, value, matcher, this.slot());
    }

    /**
     * 不等于
     *
     * @param property 属性名
     * @param value    值
     * @param matcher  匹配器
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    <V> C ne(final String property, final V value, final Matcher<V> matcher, final LogicSymbol slot);

    // endregion

    // region Greater than methods

    /**
     * 大于
     *
     * @param property {@link Property}
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C gt(final Property<T, V> property, final V value) {
        return this.gt(property, value, this.slot());
    }

    /**
     * 大于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C gt(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.gt(property, value, null, slot);
    }

    /**
     * 大于
     *
     * @param property {@link Property}
     * @param value    值
     * @param matcher  匹配器
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C gt(final Property<T, V> property, final V value, final Matcher<V> matcher) {
        return this.gt(property, value, matcher, this.slot());
    }

    /**
     * 大于
     *
     * @param property {@link Property}
     * @param value    值
     * @param matcher  匹配器
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C gt(final Property<T, V> property, final V value, final Matcher<V> matcher, final LogicSymbol slot) {
        return this.gt(this.convert(property), value, matcher, slot);
    }

    /**
     * 大于
     *
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C gt(final String property, final V value) {
        return this.gt(property, value, this.slot());
    }

    /**
     * 大于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C gt(final String property, final V value, final LogicSymbol slot) {
        return this.gt(property, value, null, slot);
    }

    /**
     * 大于
     *
     * @param property 属性
     * @param value    值
     * @param matcher  匹配器
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C gt(final String property, final V value, final Matcher<V> matcher) {
        return this.gt(property, value, matcher, this.slot());
    }

    /**
     * 大于
     *
     * @param property 属性
     * @param value    值
     * @param matcher  匹配器
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    <V> C gt(final String property, final V value, final Matcher<V> matcher, final LogicSymbol slot);

    // endregion

    // region Greater than or equal to methods

    /**
     * 大于或等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C ge(final Property<T, V> property, final V value) {
        return this.ge(property, value, this.slot());
    }

    /**
     * 大于或等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C ge(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.ge(property, value, null, slot);
    }

    /**
     * 大于或等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param matcher  匹配器
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C ge(final Property<T, V> property, final V value, final Matcher<V> matcher) {
        return this.ge(property, value, matcher, this.slot());
    }

    /**
     * 大于或等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param matcher  匹配器
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C ge(final Property<T, V> property, final V value, final Matcher<V> matcher, final LogicSymbol slot) {
        return this.ge(this.convert(property), value, matcher, slot);
    }

    /**
     * 大于或等于
     *
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C ge(final String property, final V value) {
        return this.ge(property, value, this.slot());
    }

    /**
     * 大于或等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C ge(final String property, final V value, final LogicSymbol slot) {
        return this.ge(property, value, null, slot);
    }

    /**
     * 大于或等于
     *
     * @param property 属性
     * @param value    值
     * @param matcher  匹配器
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C ge(final String property, final V value, final Matcher<V> matcher) {
        return this.ge(property, value, matcher, this.slot());
    }

    /**
     * 大于或等于
     *
     * @param property 属性
     * @param value    值
     * @param matcher  匹配器
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    <V> C ge(final String property, final V value, final Matcher<V> matcher, final LogicSymbol slot);

    // endregion

    // region Less than methods

    /**
     * 小于
     *
     * @param property {@link Property}
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C lt(final Property<T, V> property, final V value) {
        return this.lt(property, value, this.slot());
    }

    /**
     * 小于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C lt(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.lt(property, value, null, slot);
    }

    /**
     * 小于
     *
     * @param property {@link Property}
     * @param value    值
     * @param matcher  匹配器
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C lt(final Property<T, V> property, final V value, final Matcher<V> matcher) {
        return this.lt(property, value, matcher, this.slot());
    }

    /**
     * 小于
     *
     * @param property {@link Property}
     * @param value    值
     * @param matcher  匹配器
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C lt(final Property<T, V> property, final V value, final Matcher<V> matcher, final LogicSymbol slot) {
        return this.lt(this.convert(property), value, matcher, slot);
    }

    /**
     * 小于
     *
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C lt(final String property, final V value) {
        return this.lt(property, value, this.slot());
    }

    /**
     * 小于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C lt(final String property, final V value, final LogicSymbol slot) {
        return this.lt(property, value, null, slot);
    }

    /**
     * 小于
     *
     * @param property 属性
     * @param value    值
     * @param matcher  匹配器
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C lt(final String property, final V value, final Matcher<V> matcher) {
        return this.lt(property, value, matcher, this.slot());
    }

    /**
     * 小于
     *
     * @param property 属性
     * @param value    值
     * @param matcher  匹配器
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    <V> C lt(final String property, final V value, final Matcher<V> matcher, final LogicSymbol slot);

    // endregion

    // region Less than or equal to methods

    /**
     * 小于或等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C le(final Property<T, V> property, final V value) {
        return this.le(property, value, this.slot());
    }

    /**
     * 小于或等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C le(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.le(property, value, null, slot);
    }

    /**
     * 小于或等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param matcher  匹配器
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C le(final Property<T, V> property, final V value, final Matcher<V> matcher) {
        return this.le(property, value, matcher, this.slot());
    }

    /**
     * 小于或等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param matcher  匹配器
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C le(final Property<T, V> property, final V value, final Matcher<V> matcher, final LogicSymbol slot) {
        return this.le(this.convert(property), value, matcher, slot);
    }

    /**
     * 小于或等于
     *
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C le(final String property, final V value) {
        return this.le(property, value, this.slot());
    }

    /**
     * 小于或等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C le(final String property, final V value, final LogicSymbol slot) {
        return this.le(property, value, null, slot);
    }

    /**
     * 小于或等于
     *
     * @param property 属性
     * @param value    值
     * @param matcher  匹配器
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C le(final String property, final V value, final Matcher<V> matcher) {
        return this.le(property, value, matcher, this.slot());
    }

    /**
     * 小于或等于
     *
     * @param property 属性
     * @param value    值
     * @param matcher  匹配器
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    <V> C le(final String property, final V value, final Matcher<V> matcher, final LogicSymbol slot);

    // endregion

}
