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

import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.core.criteria.support.Slot;
import io.github.mybatisx.core.property.Property;
import io.github.mybatisx.core.property.PropertyConverter;

/**
 * 聚合函数筛选比较条件接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/2/6
 * @since 1.0.0
 */
public interface LambdaFuncCompare<T, C extends LambdaFuncCompare<T, C>> extends Slot<T, C>, PropertyConverter<T> {

    // region Equal methods 
    
    // region Count

    /**
     * 总数等于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C countEq(final Property<T, ?> property, final long value) {
        return this.countEq(property, value, this.slot());
    }

    /**
     * 总数等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countEq(final Property<T, ?> property, final long value, final LogicSymbol slot) {
        return this.countEq(property, false, value, slot);
    }

    /**
     * 总数等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C countEq(final Property<T, ?> property, final boolean distinct, final long value) {
        return this.countEq(property, distinct, value, this.slot());
    }

    /**
     * 总数等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countEq(final Property<T, ?> property, final boolean distinct, final long value,
                      final LogicSymbol slot) {
        return this.countEq(this.convert(property), distinct, value, slot);
    }

    /**
     * 总数等于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C countEq(final String property, final long value) {
        return this.countEq(property, value, this.slot());
    }

    /**
     * 总数等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countEq(final String property, final long value, final LogicSymbol slot) {
        return this.countEq(property, false, value, slot);
    }

    /**
     * 总数等于
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C countEq(final String property, final boolean distinct, final long value) {
        return this.countEq(property, distinct, value, this.slot());
    }

    /**
     * 总数等于
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C countEq(final String property, final boolean distinct, final long value, final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和值等于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C sumEq(final Property<T, ?> property, final Object value) {
        return this.sumEq(property, value, this.slot());
    }

    /**
     * 总和值等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumEq(final Property<T, ?> property, final Object value, final LogicSymbol slot) {
        return this.sumEq(property, false, value, slot);
    }

    /**
     * 总和值等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C sumEq(final Property<T, ?> property, final boolean distinct, final Object value) {
        return this.sumEq(property, distinct, value, this.slot());
    }

    /**
     * 总和值等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumEq(final Property<T, ?> property, final boolean distinct, final Object value,
                    final LogicSymbol slot) {
        return this.sumEq(this.convert(property), distinct, value, slot);
    }

    /**
     * 总和值等于
     *
     * @param property 属性名
     * @param value    值
     * @return {@code this}
     */
    default C sumEq(final String property, final Object value) {
        return this.sumEq(property, value, this.slot());
    }

    /**
     * 总和值等于
     *
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumEq(final String property, final Object value, final LogicSymbol slot) {
        return this.sumEq(property, false, value, slot);
    }

    /**
     * 总和值等于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C sumEq(final String property, final boolean distinct, final Object value) {
        return this.sumEq(property, distinct, value, this.slot());
    }

    /**
     * 总和值等于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C sumEq(final String property, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值等于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C avgEq(final Property<T, ?> property, final Object value) {
        return this.avgEq(property, value, this.slot());
    }

    /**
     * 平均值等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgEq(final Property<T, ?> property, final Object value, final LogicSymbol slot) {
        return this.avgEq(property, false, value, slot);
    }

    /**
     * 平均值等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C avgEq(final Property<T, ?> property, final boolean distinct, final Object value) {
        return this.avgEq(property, distinct, value, this.slot());
    }

    /**
     * 平均值等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgEq(final Property<T, ?> property, final boolean distinct, final Object value,
                    final LogicSymbol slot) {
        return this.avgEq(this.convert(property), distinct, value, slot);
    }

    /**
     * 平均值等于
     *
     * @param property 属性名
     * @param value    值
     * @return {@code this}
     */
    default C avgEq(final String property, final Object value) {
        return this.avgEq(property, value, this.slot());
    }

    /**
     * 平均值等于
     *
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgEq(final String property, final Object value, final LogicSymbol slot) {
        return this.avgEq(property, false, value, slot);
    }

    /**
     * 平均值等于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C avgEq(final String property, final boolean distinct, final Object value) {
        return this.avgEq(property, distinct, value, this.slot());
    }

    /**
     * 平均值等于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C avgEq(final String property, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值等于
     *
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C minEq(final Property<T, V> property, final V value) {
        return this.minEq(property, value, this.slot());
    }

    /**
     * 最小值等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C minEq(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.minEq(this.convert(property), value, slot);
    }

    /**
     * 最小值等于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C minEq(final String property, final Object value) {
        return this.minEq(property, value, this.slot());
    }

    /**
     * 最小值等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C minEq(final String property, final Object value, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值等于
     *
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C maxEq(final Property<T, V> property, final V value) {
        return this.maxEq(property, value, this.slot());
    }

    /**
     * 最大值等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C maxEq(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.maxEq(this.convert(property), value, slot);
    }

    /**
     * 最大值等于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C maxEq(final String property, final Object value) {
        return this.maxEq(property, value, this.slot());
    }

    /**
     * 最大值等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C maxEq(final String property, final Object value, final LogicSymbol slot);

    // endregion

    // endregion

    // region Not equal methods

    // region Count

    /**
     * 总数不等于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C countNe(final Property<T, ?> property, final long value) {
        return this.countNe(property, value, this.slot());
    }

    /**
     * 总数不等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countNe(final Property<T, ?> property, final long value, final LogicSymbol slot) {
        return this.countNe(property, false, value, slot);
    }

    /**
     * 总数不等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C countNe(final Property<T, ?> property, final boolean distinct, final long value) {
        return this.countNe(property, distinct, value, this.slot());
    }

    /**
     * 总数不等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countNe(final Property<T, ?> property, final boolean distinct, final long value,
                      final LogicSymbol slot) {
        return this.countNe(this.convert(property), distinct, value, slot);
    }

    /**
     * 总数不等于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C countNe(final String property, final long value) {
        return this.countNe(property, value, this.slot());
    }

    /**
     * 总数不等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countNe(final String property, final long value, final LogicSymbol slot) {
        return this.countNe(property, false, value, slot);
    }

    /**
     * 总数不等于
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C countNe(final String property, final boolean distinct, final long value) {
        return this.countNe(property, distinct, value, this.slot());
    }

    /**
     * 总数不等于
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C countNe(final String property, final boolean distinct, final long value, final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和值不等于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C sumNe(final Property<T, ?> property, final Object value) {
        return this.sumNe(property, value, this.slot());
    }

    /**
     * 总和值不等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumNe(final Property<T, ?> property, final Object value, final LogicSymbol slot) {
        return this.sumNe(property, false, value, slot);
    }

    /**
     * 总和值不等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C sumNe(final Property<T, ?> property, final boolean distinct, final Object value) {
        return this.sumNe(property, distinct, value, this.slot());
    }

    /**
     * 总和值不等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumNe(final Property<T, ?> property, final boolean distinct, final Object value,
                    final LogicSymbol slot) {
        return this.sumNe(this.convert(property), distinct, value, slot);
    }

    /**
     * 总和值不等于
     *
     * @param property 属性名
     * @param value    值
     * @return {@code this}
     */
    default C sumNe(final String property, final Object value) {
        return this.sumNe(property, value, this.slot());
    }

    /**
     * 总和值不等于
     *
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumNe(final String property, final Object value, final LogicSymbol slot) {
        return this.sumNe(property, false, value, slot);
    }

    /**
     * 总和值不等于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C sumNe(final String property, final boolean distinct, final Object value) {
        return this.sumNe(property, distinct, value, this.slot());
    }

    /**
     * 总和值不等于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C sumNe(final String property, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值不等于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C avgNe(final Property<T, ?> property, final Object value) {
        return this.avgNe(property, value, this.slot());
    }

    /**
     * 平均值不等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgNe(final Property<T, ?> property, final Object value, final LogicSymbol slot) {
        return this.avgNe(property, false, value, slot);
    }

    /**
     * 平均值不等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C avgNe(final Property<T, ?> property, final boolean distinct, final Object value) {
        return this.avgNe(property, distinct, value, this.slot());
    }

    /**
     * 平均值不等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgNe(final Property<T, ?> property, final boolean distinct, final Object value,
                    final LogicSymbol slot) {
        return this.avgNe(this.convert(property), distinct, value, slot);
    }

    /**
     * 平均值不等于
     *
     * @param property 属性名
     * @param value    值
     * @return {@code this}
     */
    default C avgNe(final String property, final Object value) {
        return this.avgNe(property, value, this.slot());
    }

    /**
     * 平均值不等于
     *
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgNe(final String property, final Object value, final LogicSymbol slot) {
        return this.avgNe(property, false, value, slot);
    }

    /**
     * 平均值不等于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C avgNe(final String property, final boolean distinct, final Object value) {
        return this.avgNe(property, distinct, value, this.slot());
    }

    /**
     * 平均值不等于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C avgNe(final String property, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值不等于
     *
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C minNe(final Property<T, V> property, final V value) {
        return this.minNe(property, value, this.slot());
    }

    /**
     * 最小值不等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C minNe(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.minNe(this.convert(property), value, slot);
    }

    /**
     * 最小值不等于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C minNe(final String property, final Object value) {
        return this.minNe(property, value, this.slot());
    }

    /**
     * 最小值不等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C minNe(final String property, final Object value, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值不等于
     *
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C maxNe(final Property<T, V> property, final V value) {
        return this.maxNe(property, value, this.slot());
    }

    /**
     * 最大值不等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C maxNe(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.maxNe(this.convert(property), value, slot);
    }

    /**
     * 最大值不等于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C maxNe(final String property, final Object value) {
        return this.maxNe(property, value, this.slot());
    }

    /**
     * 最大值不等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C maxNe(final String property, final Object value, final LogicSymbol slot);

    // endregion

    // endregion

    // region Greater than methods

    // region Count

    /**
     * 总数大于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C countGt(final Property<T, ?> property, final long value) {
        return this.countGt(property, value, this.slot());
    }

    /**
     * 总数大于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countGt(final Property<T, ?> property, final long value, final LogicSymbol slot) {
        return this.countGt(property, false, value, slot);
    }

    /**
     * 总数大于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C countGt(final Property<T, ?> property, final boolean distinct, final long value) {
        return this.countGt(property, distinct, value, this.slot());
    }

    /**
     * 总数大于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countGt(final Property<T, ?> property, final boolean distinct, final long value,
                      final LogicSymbol slot) {
        return this.countGt(this.convert(property), distinct, value, slot);
    }

    /**
     * 总数大于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C countGt(final String property, final long value) {
        return this.countGt(property, value, this.slot());
    }

    /**
     * 总数大于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countGt(final String property, final long value, final LogicSymbol slot) {
        return this.countGt(property, false, value, slot);
    }

    /**
     * 总数大于
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C countGt(final String property, final boolean distinct, final long value) {
        return this.countGt(property, distinct, value, this.slot());
    }

    /**
     * 总数大于
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C countGt(final String property, final boolean distinct, final long value, final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和值大于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C sumGt(final Property<T, ?> property, final Object value) {
        return this.sumGt(property, value, this.slot());
    }

    /**
     * 总和值大于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumGt(final Property<T, ?> property, final Object value, final LogicSymbol slot) {
        return this.sumGt(property, false, value, slot);
    }

    /**
     * 总和值大于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C sumGt(final Property<T, ?> property, final boolean distinct, final Object value) {
        return this.sumGt(property, distinct, value, this.slot());
    }

    /**
     * 总和值大于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumGt(final Property<T, ?> property, final boolean distinct, final Object value,
                    final LogicSymbol slot) {
        return this.sumGt(this.convert(property), distinct, value, slot);
    }

    /**
     * 总和值大于
     *
     * @param property 属性名
     * @param value    值
     * @return {@code this}
     */
    default C sumGt(final String property, final Object value) {
        return this.sumGt(property, value, this.slot());
    }

    /**
     * 总和值大于
     *
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumGt(final String property, final Object value, final LogicSymbol slot) {
        return this.sumGt(property, false, value, slot);
    }

    /**
     * 总和值大于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C sumGt(final String property, final boolean distinct, final Object value) {
        return this.sumGt(property, distinct, value, this.slot());
    }

    /**
     * 总和值大于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C sumGt(final String property, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值大于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C avgGt(final Property<T, ?> property, final Object value) {
        return this.avgGt(property, value, this.slot());
    }

    /**
     * 平均值大于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgGt(final Property<T, ?> property, final Object value, final LogicSymbol slot) {
        return this.avgGt(property, false, value, slot);
    }

    /**
     * 平均值大于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C avgGt(final Property<T, ?> property, final boolean distinct, final Object value) {
        return this.avgGt(property, distinct, value, this.slot());
    }

    /**
     * 平均值大于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgGt(final Property<T, ?> property, final boolean distinct, final Object value,
                    final LogicSymbol slot) {
        return this.avgGt(this.convert(property), distinct, value, slot);
    }

    /**
     * 平均值大于
     *
     * @param property 属性名
     * @param value    值
     * @return {@code this}
     */
    default C avgGt(final String property, final Object value) {
        return this.avgGt(property, value, this.slot());
    }

    /**
     * 平均值大于
     *
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgGt(final String property, final Object value, final LogicSymbol slot) {
        return this.avgGt(property, false, value, slot);
    }

    /**
     * 平均值大于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C avgGt(final String property, final boolean distinct, final Object value) {
        return this.avgGt(property, distinct, value, this.slot());
    }

    /**
     * 平均值大于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C avgGt(final String property, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值大于
     *
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C minGt(final Property<T, V> property, final V value) {
        return this.minGt(property, value, this.slot());
    }

    /**
     * 最小值大于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C minGt(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.minGt(this.convert(property), value, slot);
    }

    /**
     * 最小值大于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C minGt(final String property, final Object value) {
        return this.minGt(property, value, this.slot());
    }

    /**
     * 最小值大于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C minGt(final String property, final Object value, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值大于
     *
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C maxGt(final Property<T, V> property, final V value) {
        return this.maxGt(property, value, this.slot());
    }

    /**
     * 最大值大于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C maxGt(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.maxGt(this.convert(property), value, slot);
    }

    /**
     * 最大值大于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C maxGt(final String property, final Object value) {
        return this.maxGt(property, value, this.slot());
    }

    /**
     * 最大值大于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C maxGt(final String property, final Object value, final LogicSymbol slot);

    // endregion

    // endregion

    // region Greater than or equal to methods

    // region Count

    /**
     * 总数大于等于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C countGe(final Property<T, ?> property, final long value) {
        return this.countGe(property, value, this.slot());
    }

    /**
     * 总数大于等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countGe(final Property<T, ?> property, final long value, final LogicSymbol slot) {
        return this.countGe(property, false, value, slot);
    }

    /**
     * 总数大于等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C countGe(final Property<T, ?> property, final boolean distinct, final long value) {
        return this.countGe(property, distinct, value, this.slot());
    }

    /**
     * 总数大于等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countGe(final Property<T, ?> property, final boolean distinct, final long value,
                      final LogicSymbol slot) {
        return this.countGe(this.convert(property), distinct, value, slot);
    }

    /**
     * 总数大于等于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C countGe(final String property, final long value) {
        return this.countGe(property, value, this.slot());
    }

    /**
     * 总数大于等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countGe(final String property, final long value, final LogicSymbol slot) {
        return this.countGe(property, false, value, slot);
    }

    /**
     * 总数大于等于
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C countGe(final String property, final boolean distinct, final long value) {
        return this.countGe(property, distinct, value, this.slot());
    }

    /**
     * 总数大于等于
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C countGe(final String property, final boolean distinct, final long value, final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和值大于等于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C sumGe(final Property<T, ?> property, final Object value) {
        return this.sumGe(property, value, this.slot());
    }

    /**
     * 总和值大于等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumGe(final Property<T, ?> property, final Object value, final LogicSymbol slot) {
        return this.sumGe(property, false, value, slot);
    }

    /**
     * 总和值大于等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C sumGe(final Property<T, ?> property, final boolean distinct, final Object value) {
        return this.sumGe(property, distinct, value, this.slot());
    }

    /**
     * 总和值大于等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumGe(final Property<T, ?> property, final boolean distinct, final Object value,
                    final LogicSymbol slot) {
        return this.sumGe(this.convert(property), distinct, value, slot);
    }

    /**
     * 总和值大于等于
     *
     * @param property 属性名
     * @param value    值
     * @return {@code this}
     */
    default C sumGe(final String property, final Object value) {
        return this.sumGe(property, value, this.slot());
    }

    /**
     * 总和值大于等于
     *
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumGe(final String property, final Object value, final LogicSymbol slot) {
        return this.sumGe(property, false, value, slot);
    }

    /**
     * 总和值大于等于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C sumGe(final String property, final boolean distinct, final Object value) {
        return this.sumGe(property, distinct, value, this.slot());
    }

    /**
     * 总和值大于等于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C sumGe(final String property, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值大于等于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C avgGe(final Property<T, ?> property, final Object value) {
        return this.avgGe(property, value, this.slot());
    }

    /**
     * 平均值大于等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgGe(final Property<T, ?> property, final Object value, final LogicSymbol slot) {
        return this.avgGe(property, false, value, slot);
    }

    /**
     * 平均值大于等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C avgGe(final Property<T, ?> property, final boolean distinct, final Object value) {
        return this.avgGe(property, distinct, value, this.slot());
    }

    /**
     * 平均值大于等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgGe(final Property<T, ?> property, final boolean distinct, final Object value,
                    final LogicSymbol slot) {
        return this.avgGe(this.convert(property), distinct, value, slot);
    }

    /**
     * 平均值大于等于
     *
     * @param property 属性名
     * @param value    值
     * @return {@code this}
     */
    default C avgGe(final String property, final Object value) {
        return this.avgGe(property, value, this.slot());
    }

    /**
     * 平均值大于等于
     *
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgGe(final String property, final Object value, final LogicSymbol slot) {
        return this.avgGe(property, false, value, slot);
    }

    /**
     * 平均值大于等于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C avgGe(final String property, final boolean distinct, final Object value) {
        return this.avgGe(property, distinct, value, this.slot());
    }

    /**
     * 平均值大于等于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C avgGe(final String property, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值大于等于
     *
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C minGe(final Property<T, V> property, final V value) {
        return this.minGe(property, value, this.slot());
    }

    /**
     * 最小值大于等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C minGe(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.minGe(this.convert(property), value, slot);
    }

    /**
     * 最小值大于等于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C minGe(final String property, final Object value) {
        return this.minGe(property, value, this.slot());
    }

    /**
     * 最小值大于等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C minGe(final String property, final Object value, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值大于等于
     *
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C maxGe(final Property<T, V> property, final V value) {
        return this.maxGe(property, value, this.slot());
    }

    /**
     * 最大值大于等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C maxGe(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.maxGe(this.convert(property), value, slot);
    }

    /**
     * 最大值大于等于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C maxGe(final String property, final Object value) {
        return this.maxGe(property, value, this.slot());
    }

    /**
     * 最大值大于等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C maxGe(final String property, final Object value, final LogicSymbol slot);

    // endregion

    // endregion 

    // region Less than methods

    // region Count

    /**
     * 总数小于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C countLt(final Property<T, ?> property, final long value) {
        return this.countLt(property, value, this.slot());
    }

    /**
     * 总数小于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countLt(final Property<T, ?> property, final long value, final LogicSymbol slot) {
        return this.countLt(property, false, value, slot);
    }

    /**
     * 总数小于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C countLt(final Property<T, ?> property, final boolean distinct, final long value) {
        return this.countLt(property, distinct, value, this.slot());
    }

    /**
     * 总数小于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countLt(final Property<T, ?> property, final boolean distinct, final long value,
                      final LogicSymbol slot) {
        return this.countLt(this.convert(property), distinct, value, slot);
    }

    /**
     * 总数小于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C countLt(final String property, final long value) {
        return this.countLt(property, value, this.slot());
    }

    /**
     * 总数小于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countLt(final String property, final long value, final LogicSymbol slot) {
        return this.countLt(property, false, value, slot);
    }

    /**
     * 总数小于
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C countLt(final String property, final boolean distinct, final long value) {
        return this.countLt(property, distinct, value, this.slot());
    }

    /**
     * 总数小于
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C countLt(final String property, final boolean distinct, final long value, final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和值小于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C sumLt(final Property<T, ?> property, final Object value) {
        return this.sumLt(property, value, this.slot());
    }

    /**
     * 总和值小于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumLt(final Property<T, ?> property, final Object value, final LogicSymbol slot) {
        return this.sumLt(property, false, value, slot);
    }

    /**
     * 总和值小于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C sumLt(final Property<T, ?> property, final boolean distinct, final Object value) {
        return this.sumLt(property, distinct, value, this.slot());
    }

    /**
     * 总和值小于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumLt(final Property<T, ?> property, final boolean distinct, final Object value,
                    final LogicSymbol slot) {
        return this.sumLt(this.convert(property), distinct, value, slot);
    }

    /**
     * 总和值小于
     *
     * @param property 属性名
     * @param value    值
     * @return {@code this}
     */
    default C sumLt(final String property, final Object value) {
        return this.sumLt(property, value, this.slot());
    }

    /**
     * 总和值小于
     *
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumLt(final String property, final Object value, final LogicSymbol slot) {
        return this.sumLt(property, false, value, slot);
    }

    /**
     * 总和值小于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C sumLt(final String property, final boolean distinct, final Object value) {
        return this.sumLt(property, distinct, value, this.slot());
    }

    /**
     * 总和值小于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C sumLt(final String property, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值小于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C avgLt(final Property<T, ?> property, final Object value) {
        return this.avgLt(property, value, this.slot());
    }

    /**
     * 平均值小于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgLt(final Property<T, ?> property, final Object value, final LogicSymbol slot) {
        return this.avgLt(property, false, value, slot);
    }

    /**
     * 平均值小于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C avgLt(final Property<T, ?> property, final boolean distinct, final Object value) {
        return this.avgLt(property, distinct, value, this.slot());
    }

    /**
     * 平均值小于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgLt(final Property<T, ?> property, final boolean distinct, final Object value,
                    final LogicSymbol slot) {
        return this.avgLt(this.convert(property), distinct, value, slot);
    }

    /**
     * 平均值小于
     *
     * @param property 属性名
     * @param value    值
     * @return {@code this}
     */
    default C avgLt(final String property, final Object value) {
        return this.avgLt(property, value, this.slot());
    }

    /**
     * 平均值小于
     *
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgLt(final String property, final Object value, final LogicSymbol slot) {
        return this.avgLt(property, false, value, slot);
    }

    /**
     * 平均值小于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C avgLt(final String property, final boolean distinct, final Object value) {
        return this.avgLt(property, distinct, value, this.slot());
    }

    /**
     * 平均值小于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C avgLt(final String property, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值小于
     *
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C minLt(final Property<T, V> property, final V value) {
        return this.minLt(property, value, this.slot());
    }

    /**
     * 最小值小于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C minLt(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.minLt(this.convert(property), value, slot);
    }

    /**
     * 最小值小于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C minLt(final String property, final Object value) {
        return this.minLt(property, value, this.slot());
    }

    /**
     * 最小值小于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C minLt(final String property, final Object value, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值小于
     *
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C maxLt(final Property<T, V> property, final V value) {
        return this.maxLt(property, value, this.slot());
    }

    /**
     * 最大值小于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C maxLt(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.maxLt(this.convert(property), value, slot);
    }

    /**
     * 最大值小于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C maxLt(final String property, final Object value) {
        return this.maxLt(property, value, this.slot());
    }

    /**
     * 最大值小于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C maxLt(final String property, final Object value, final LogicSymbol slot);

    // endregion

    // endregion

    // region Less than or equal to methods

    // region Count

    /**
     * 总数小于等于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C countLe(final Property<T, ?> property, final long value) {
        return this.countLe(property, value, this.slot());
    }

    /**
     * 总数小于等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countLe(final Property<T, ?> property, final long value, final LogicSymbol slot) {
        return this.countLe(property, false, value, slot);
    }

    /**
     * 总数小于等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C countLe(final Property<T, ?> property, final boolean distinct, final long value) {
        return this.countLe(property, distinct, value, this.slot());
    }

    /**
     * 总数小于等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countLe(final Property<T, ?> property, final boolean distinct, final long value,
                      final LogicSymbol slot) {
        return this.countLe(this.convert(property), distinct, value, slot);
    }

    /**
     * 总数小于等于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C countLe(final String property, final long value) {
        return this.countLe(property, value, this.slot());
    }

    /**
     * 总数小于等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countLe(final String property, final long value, final LogicSymbol slot) {
        return this.countLe(property, false, value, slot);
    }

    /**
     * 总数小于等于
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C countLe(final String property, final boolean distinct, final long value) {
        return this.countLe(property, distinct, value, this.slot());
    }

    /**
     * 总数小于等于
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C countLe(final String property, final boolean distinct, final long value, final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和值小于等于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C sumLe(final Property<T, ?> property, final Object value) {
        return this.sumLe(property, value, this.slot());
    }

    /**
     * 总和值小于等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumLe(final Property<T, ?> property, final Object value, final LogicSymbol slot) {
        return this.sumLe(property, false, value, slot);
    }

    /**
     * 总和值小于等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C sumLe(final Property<T, ?> property, final boolean distinct, final Object value) {
        return this.sumLe(property, distinct, value, this.slot());
    }

    /**
     * 总和值小于等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumLe(final Property<T, ?> property, final boolean distinct, final Object value,
                    final LogicSymbol slot) {
        return this.sumLe(this.convert(property), distinct, value, slot);
    }

    /**
     * 总和值小于等于
     *
     * @param property 属性名
     * @param value    值
     * @return {@code this}
     */
    default C sumLe(final String property, final Object value) {
        return this.sumLe(property, value, this.slot());
    }

    /**
     * 总和值小于等于
     *
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumLe(final String property, final Object value, final LogicSymbol slot) {
        return this.sumLe(property, false, value, slot);
    }

    /**
     * 总和值小于等于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C sumLe(final String property, final boolean distinct, final Object value) {
        return this.sumLe(property, distinct, value, this.slot());
    }

    /**
     * 总和值小于等于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C sumLe(final String property, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值小于等于
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C avgLe(final Property<T, ?> property, final Object value) {
        return this.avgLe(property, value, this.slot());
    }

    /**
     * 平均值小于等于
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgLe(final Property<T, ?> property, final Object value, final LogicSymbol slot) {
        return this.avgLe(property, false, value, slot);
    }

    /**
     * 平均值小于等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C avgLe(final Property<T, ?> property, final boolean distinct, final Object value) {
        return this.avgLe(property, distinct, value, this.slot());
    }

    /**
     * 平均值小于等于
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgLe(final Property<T, ?> property, final boolean distinct, final Object value,
                    final LogicSymbol slot) {
        return this.avgLe(this.convert(property), distinct, value, slot);
    }

    /**
     * 平均值小于等于
     *
     * @param property 属性名
     * @param value    值
     * @return {@code this}
     */
    default C avgLe(final String property, final Object value) {
        return this.avgLe(property, value, this.slot());
    }

    /**
     * 平均值小于等于
     *
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgLe(final String property, final Object value, final LogicSymbol slot) {
        return this.avgLe(property, false, value, slot);
    }

    /**
     * 平均值小于等于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C avgLe(final String property, final boolean distinct, final Object value) {
        return this.avgLe(property, distinct, value, this.slot());
    }

    /**
     * 平均值小于等于
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C avgLe(final String property, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值小于等于
     *
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C minLe(final Property<T, V> property, final V value) {
        return this.minLe(property, value, this.slot());
    }

    /**
     * 最小值小于等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C minLe(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.minLe(this.convert(property), value, slot);
    }

    /**
     * 最小值小于等于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C minLe(final String property, final Object value) {
        return this.minLe(property, value, this.slot());
    }

    /**
     * 最小值小于等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C minLe(final String property, final Object value, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值小于等于
     *
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C maxLe(final Property<T, V> property, final V value) {
        return this.maxLe(property, value, this.slot());
    }

    /**
     * 最大值小于等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C maxLe(final Property<T, V> property, final V value, final LogicSymbol slot) {
        return this.maxLe(this.convert(property), value, slot);
    }

    /**
     * 最大值小于等于
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C maxLe(final String property, final Object value) {
        return this.maxLe(property, value, this.slot());
    }

    /**
     * 最大值小于等于
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C maxLe(final String property, final Object value, final LogicSymbol slot);

    // endregion
    
    // endregion

}
