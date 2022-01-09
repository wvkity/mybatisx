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

import java.util.Arrays;
import java.util.Collection;

/**
 * 范围条件接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/9
 * @since 1.0.0
 */
public interface LambdaRange<T, C extends LambdaRange<T, C>> extends Slot<T, C>, PropertyConverter<T> {

    // region Between methods

    /**
     * between
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C between(final Property<T, V> property, final V begin, final V end) {
        return this.between(property, begin, end, this.slot());
    }

    /**
     * between
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C between(final Property<T, V> property, final V begin, final V end, final LogicSymbol slot) {
        return this.between(this.convert(property), begin, end, slot);
    }

    /**
     * between
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C between(final String property, final V begin, final V end) {
        return this.between(property, begin, end, this.slot());
    }

    /**
     * between
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    <V> C between(final String property, final V begin, final V end, final LogicSymbol slot);

    // endregion

    // region Not between methods

    /**
     * not between
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C notBetween(final Property<T, V> property, final V begin, final V end) {
        return this.notBetween(property, begin, end, this.slot());
    }

    /**
     * not between
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C notBetween(final Property<T, V> property, final V begin, final V end, final LogicSymbol slot) {
        return this.notBetween(this.convert(property), begin, end, slot);
    }

    /**
     * not between
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C notBetween(final String property, final V begin, final V end) {
        return this.notBetween(property, begin, end, this.slot());
    }

    /**
     * not between
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    <V> C notBetween(final String property, final V begin, final V end, final LogicSymbol slot);

    // endregion

    // region In methods

    /**
     * in
     *
     * @param property {@link Property}
     * @param values   值列表
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C in(final Property<T, V> property, final V[] values) {
        return this.in(property, values, this.slot());
    }

    /**
     * in
     *
     * @param property {@link Property}
     * @param values   值列表
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C in(final Property<T, V> property, final Collection<V> values) {
        return this.in(property, values, this.slot());
    }

    /**
     * in
     *
     * @param property {@link Property}
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C in(final Property<T, V> property, final V[] values, final LogicSymbol slot) {
        return this.in(property, Arrays.asList(values), slot);
    }

    /**
     * in
     *
     * @param property {@link Property}
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C in(final Property<T, V> property, final Collection<V> values, final LogicSymbol slot) {
        return this.in(this.convert(property), values, slot);
    }

    /**
     * in
     *
     * @param property 属性
     * @param values   值列表
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C in(final String property, final V[] values) {
        return this.in(property, values, this.slot());
    }

    /**
     * in
     *
     * @param property 属性
     * @param values   值列表
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C in(final String property, final Collection<V> values) {
        return this.in(property, values, this.slot());
    }

    /**
     * in
     *
     * @param property 属性
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C in(final String property, final V[] values, final LogicSymbol slot) {
        return this.in(property, Arrays.asList(values), slot);
    }

    /**
     * in
     *
     * @param property 属性
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    <V> C in(final String property, final Collection<V> values, final LogicSymbol slot);

    // endregion

    // region Not in methods

    /**
     * not in
     *
     * @param property {@link Property}
     * @param values   值列表
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C notIn(final Property<T, V> property, final V[] values) {
        return this.notIn(property, values, this.slot());
    }

    /**
     * not in
     *
     * @param property {@link Property}
     * @param values   值列表
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C notIn(final Property<T, V> property, final Collection<V> values) {
        return this.notIn(property, values, this.slot());
    }

    /**
     * not in
     *
     * @param property {@link Property}
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C notIn(final Property<T, V> property, final V[] values, final LogicSymbol slot) {
        return this.notIn(property, Arrays.asList(values), slot);
    }

    /**
     * not in
     *
     * @param property {@link Property}
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C notIn(final Property<T, V> property, final Collection<V> values, final LogicSymbol slot) {
        return this.notIn(this.convert(property), values, slot);
    }

    /**
     * not in
     *
     * @param property 属性
     * @param values   值列表
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C notIn(final String property, final V[] values) {
        return this.notIn(property, values, this.slot());
    }

    /**
     * not in
     *
     * @param property 属性
     * @param values   值列表
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C notIn(final String property, final Collection<V> values) {
        return this.notIn(property, values, this.slot());
    }

    /**
     * not in
     *
     * @param property 属性
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C notIn(final String property, final V[] values, final LogicSymbol slot) {
        return this.notIn(property, Arrays.asList(values), slot);
    }

    /**
     * not in
     *
     * @param property 属性
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    <V> C notIn(final String property, final Collection<V> values, final LogicSymbol slot);

    // endregion

}
