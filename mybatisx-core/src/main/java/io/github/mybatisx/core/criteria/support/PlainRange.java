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

import java.util.Arrays;
import java.util.Collection;

/**
 * 范围条件接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/11
 * @since 1.0.0
 */
public interface PlainRange<T, C extends PlainRange<T, C>> extends Slot<T, C> {

    // region Between methods

    /**
     * between
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colBetween(final String column, final V begin, final V end) {
        return this.colBetween(column, begin, end, this.slot());
    }

    /**
     * between
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@code this}
     */
    <V> C colBetween(final String column, final V begin, final V end, final LogicSymbol slot);

    // endregion

    // region Not between methods

    /**
     * not between
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colNotBetween(final String column, final V begin, final V end) {
        return this.colNotBetween(column, begin, end, this.slot());
    }

    /**
     * not between
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@code this}
     */
    <V> C colNotBetween(final String column, final V begin, final V end, final LogicSymbol slot);

    // endregion

    // region In methods

    /**
     * in
     *
     * @param column 字段名
     * @param values 值列表
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colIn(final String column, final V[] values) {
        return this.colIn(column, values, this.slot());
    }

    /**
     * in
     *
     * @param column 字段名
     * @param values 值列表
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colIn(final String column, final V[] values, final LogicSymbol slot) {
        return this.colIn(column, Arrays.asList(values), slot);
    }

    /**
     * in
     *
     * @param column 字段名
     * @param values 值列表
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colIn(final String column, final Collection<V> values) {
        return this.colIn(column, values, this.slot());
    }

    /**
     * in
     *
     * @param column 字段名
     * @param values 值列表
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@code this}
     */
    <V> C colIn(final String column, final Collection<V> values, final LogicSymbol slot);

    // endregion

    // region Not in methods

    /**
     * not in
     *
     * @param column 字段名
     * @param values 值列表
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colNotIn(final String column, final V[] values) {
        return this.colNotIn(column, values, this.slot());
    }

    /**
     * not in
     *
     * @param column 字段名
     * @param values 值列表
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colNotIn(final String column, final V[] values, final LogicSymbol slot) {
        return this.colNotIn(column, Arrays.asList(values), slot);
    }

    /**
     * not in
     *
     * @param column 字段名
     * @param values 值列表
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colNotIn(final String column, final Collection<V> values) {
        return this.colNotIn(column, values, this.slot());
    }

    /**
     * not in
     *
     * @param column 字段名
     * @param values 值列表
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@code this}
     */
    <V> C colNotIn(final String column, final Collection<V> values, final LogicSymbol slot);

    // endregion
}
