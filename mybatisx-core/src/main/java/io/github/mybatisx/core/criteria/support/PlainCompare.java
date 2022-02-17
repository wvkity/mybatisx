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

import java.util.Map;

/**
 * 比较条件接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/11
 * @since 1.0.0
 */
public interface PlainCompare<T, C extends PlainCompare<T, C>> extends Slot<T, C> {

    // region Equal methods

    /**
     * 等于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colEq(final String column, final Object value) {
        return this.colEq(column, value, this.slot());
    }

    /**
     * 等于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colEq(final String column, final Object value, final LogicSymbol slot) {
        return this.colEq(column, value, (Matcher<? super Object>) null, slot);
    }

    /**
     * 等于
     *
     * @param column  字段名
     * @param value   值
     * @param matcher 匹配器
     * @param <V>     值类型
     * @return {@code this}
     */
    default <V> C colEq(final String column, final V value, final Matcher<V> matcher) {
        return this.colEq(column, value, matcher, this.slot());
    }

    /**
     * 等于
     *
     * @param column  字段名
     * @param value   值
     * @param matcher 匹配器
     * @param slot    {@link LogicSymbol}
     * @param <V>     值类型
     * @return {@code this}
     */
    <V> C colEq(final String column, final V value, final Matcher<V> matcher, final LogicSymbol slot);

    /**
     * 等于
     *
     * @param c1 字段1
     * @param v1 字段1对应值
     * @param c2 字段2
     * @param v2 字段2对应值
     * @return {@code this}
     */
    default C colEq(final String c1, final Object v1, final String c2, final Object v2) {
        return this.colEq(c1, v1, c2, v2, this.slot());
    }

    /**
     * 等于
     *
     * @param c1   字段1
     * @param v1   字段1对应值
     * @param c2   字段2
     * @param v2   字段2对应值
     * @param slot {@link LogicSymbol}
     * @return {@code this}
     */
    default C colEq(final String c1, final Object v1, final String c2, final Object v2, final LogicSymbol slot) {
        return this.colEq(c1, v1, slot).colEq(c2, v2, slot);
    }

    /**
     * 等于
     *
     * @param c1 字段1
     * @param v1 字段1对应值
     * @param c2 字段2
     * @param v2 字段2对应值
     * @param c3 字段3
     * @param v3 字段3对应值
     * @return {@code this}
     */
    default C colEq(final String c1, final Object v1, final String c2, final Object v2,
                    final String c3, final Object v3) {
        return this.colEq(c1, v1, c2, v2, c3, v3, this.slot());
    }

    /**
     * 等于
     *
     * @param c1   字段1
     * @param v1   字段1对应值
     * @param c2   字段2
     * @param v2   字段2对应值
     * @param c3   字段3
     * @param v3   字段3对应值
     * @param slot {@link LogicSymbol}
     * @return {@code this}
     */
    default C colEq(final String c1, final Object v1, final String c2, final Object v2,
                    final String c3, final Object v3, final LogicSymbol slot) {
        return this.colEq(c1, v1, slot).colEq(c2, v2, slot).colEq(c3, v3, slot);
    }

    /**
     * 等于
     *
     * @param columns 字段-值集合
     * @return {@code this}
     */
    default C colEq(final Map<String, ?> columns) {
        return this.colEq(columns, this.slot());
    }

    /**
     * 等于
     *
     * @param columns 字段-值集合
     * @param slot    {@link LogicSymbol}
     * @return {@code this}
     */
    C colEq(final Map<String, ?> columns, final LogicSymbol slot);

    // endregion

    // region Not equal methods

    /**
     * 不等于
     *
     * @param column 字段名
     * @param value  值
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colNe(final String column, final V value) {
        return this.colNe(column, value, this.slot());
    }

    /**
     * 不等于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colNe(final String column, final V value, final LogicSymbol slot) {
        return this.colNe(column, value, null, slot);
    }

    /**
     * 不等于
     *
     * @param column  字段名
     * @param value   值
     * @param matcher 匹配器
     * @param <V>     值类型
     * @return {@code this}
     */
    default <V> C colNe(final String column, final V value, final Matcher<V> matcher) {
        return this.colNe(column, value, matcher, this.slot());
    }

    /**
     * 不等于
     *
     * @param column  字段名
     * @param value   值
     * @param matcher 匹配器
     * @param slot    {@link LogicSymbol}
     * @param <V>     值类型
     * @return {@code this}
     */
    <V> C colNe(final String column, final V value, final Matcher<V> matcher, final LogicSymbol slot);

    // endregion

    // region Greater than methods

    /**
     * 大于
     *
     * @param column 字段名
     * @param value  值
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colGt(final String column, final V value) {
        return this.colGt(column, value, this.slot());
    }

    /**
     * 大于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colGt(final String column, final V value, final LogicSymbol slot) {
        return this.colGt(column, value, null, slot);
    }

    /**
     * 大于
     *
     * @param column  字段名
     * @param value   值
     * @param matcher 匹配器
     * @param <V>     值类型
     * @return {@code this}
     */
    default <V> C colGt(final String column, final V value, final Matcher<V> matcher) {
        return this.colGt(column, value, matcher, this.slot());
    }

    /**
     * 大于
     *
     * @param column  字段名
     * @param value   值
     * @param matcher 匹配器
     * @param slot    {@link LogicSymbol}
     * @param <V>     值类型
     * @return {@code this}
     */
    <V> C colGt(final String column, final V value, final Matcher<V> matcher, final LogicSymbol slot);

    // endregion

    // region Greater than or equal methods

    /**
     * 大于或等于
     *
     * @param column 字段名
     * @param value  值
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colGe(final String column, final V value) {
        return this.colGe(column, value, this.slot());
    }

    /**
     * 大于或等于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colGe(final String column, final V value, final LogicSymbol slot) {
        return this.colGe(column, value, null, slot);
    }

    /**
     * 大于或等于
     *
     * @param column  字段名
     * @param value   值
     * @param matcher 匹配器
     * @param <V>     值类型
     * @return {@code this}
     */
    default <V> C colGe(final String column, final V value, final Matcher<V> matcher) {
        return this.colGe(column, value, matcher, this.slot());
    }

    /**
     * 大于或等于
     *
     * @param column  字段名
     * @param value   值
     * @param matcher 匹配器
     * @param slot    {@link LogicSymbol}
     * @param <V>     值类型
     * @return {@code this}
     */
    <V> C colGe(final String column, final V value, final Matcher<V> matcher, final LogicSymbol slot);

    // endregion

    // region Less than methods

    /**
     * 小于
     *
     * @param column 字段名
     * @param value  值
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colLt(final String column, final V value) {
        return this.colLt(column, value, this.slot());
    }

    /**
     * 小于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colLt(final String column, final V value, final LogicSymbol slot) {
        return this.colLt(column, value, null, slot);
    }

    /**
     * 小于
     *
     * @param column  字段名
     * @param value   值
     * @param matcher 匹配器
     * @param <V>     值类型
     * @return {@code this}
     */
    default <V> C colLt(final String column, final V value, final Matcher<V> matcher) {
        return this.colLt(column, value, matcher, this.slot());
    }

    /**
     * 小于
     *
     * @param column  字段名
     * @param value   值
     * @param matcher 匹配器
     * @param slot    {@link LogicSymbol}
     * @param <V>     值类型
     * @return {@code this}
     */
    <V> C colLt(final String column, final V value, final Matcher<V> matcher, final LogicSymbol slot);

    // endregion

    // region Less than or equal methods

    /**
     * 小于或等于
     *
     * @param column 字段名
     * @param value  值
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colLe(final String column, final V value) {
        return this.colLe(column, value, this.slot());
    }

    /**
     * 小于或等于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@code this}
     */
    default <V> C colLe(final String column, final V value, final LogicSymbol slot) {
        return this.colLe(column, value, null, slot);
    }

    /**
     * 小于或等于
     *
     * @param column  字段名
     * @param value   值
     * @param matcher 匹配器
     * @param <V>     值类型
     * @return {@code this}
     */
    default <V> C colLe(final String column, final V value, final Matcher<V> matcher) {
        return this.colLe(column, value, matcher, this.slot());
    }

    /**
     * 小于或等于
     *
     * @param column  字段名
     * @param value   值
     * @param matcher 匹配器
     * @param slot    {@link LogicSymbol}
     * @param <V>     值类型
     * @return {@code this}
     */
    <V> C colLe(final String column, final V value, final Matcher<V> matcher, final LogicSymbol slot);

    // endregion

    // region Column equal methods

    /**
     * 关联条件
     *
     * @param rc          {@link Criteria}
     * @param rightColumn 右表关联对象
     * @return {@code this}
     */
    C colOn(final Criteria<?> rc, final String rightColumn);

    /**
     * 关联条件
     *
     * @param leftColumn 左表关联字段名
     * @param rc         {@link Criteria}
     * @return {@code this}
     */
    C colOn(final String leftColumn, final Criteria<?> rc);

    /**
     * 关联条件
     *
     * @param leftColumn  左表关联字段名
     * @param rc          {@link Criteria}
     * @param rightColumn 右表关联字段名
     * @return {@code this}
     */
    C colOn(final String leftColumn, final Criteria<?> rc, final String rightColumn);


    /**
     * 关联条件
     *
     * @param leftColumn    左表关联字段名
     * @param rc            {@link Criteria}
     * @param rightProperty 属性
     * @param <R>           实体类型
     * @return {@code this}
     */
    default <R> C colOnWith(final String leftColumn, final Criteria<R> rc, final Property<R, ?> rightProperty) {
        return this.colOnWith(leftColumn, rc, LambdaMetadataWeakCache.getProperty(rightProperty));
    }

    /**
     * 关联条件
     *
     * @param leftColumn    左表关联字段名
     * @param rc            {@link Criteria}
     * @param rightProperty 属性
     * @return {@code this}
     */
    C colOnWith(final String leftColumn, final Criteria<?> rc, final String rightProperty);

    // endregion

}
