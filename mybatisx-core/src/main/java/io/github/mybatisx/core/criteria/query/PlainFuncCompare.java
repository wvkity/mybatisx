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

/**
 * 聚合函数筛选比较条件接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/2/6
 * @since 1.0.0
 */
public interface PlainFuncCompare<T, C extends PlainFuncCompare<T, C>> extends Slot<T, C> {

    // region Equal methods 

    // region Count

    /**
     * 总数等于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colCountEq(final String column, final long value) {
        return this.colCountEq(column, value, this.slot());
    }

    /**
     * 总数等于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colCountEq(final String column, final long value, final LogicSymbol slot) {
        return this.colCountEq(column, false, value, slot);
    }

    /**
     * 总数等于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colCountEq(final String column, final boolean distinct, final long value) {
        return this.colCountEq(column, distinct, value, this.slot());
    }

    /**
     * 总数等于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colCountEq(final String column, final boolean distinct, final long value, final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和值等于
     *
     * @param column 字段名名
     * @param value  值
     * @return {@code this}
     */
    default C colSumEq(final String column, final Object value) {
        return this.colSumEq(column, value, this.slot());
    }

    /**
     * 总和值等于
     *
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colSumEq(final String column, final Object value, final LogicSymbol slot) {
        return this.colSumEq(column, false, value, slot);
    }

    /**
     * 总和值等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colSumEq(final String column, final boolean distinct, final Object value) {
        return this.colSumEq(column, distinct, value, this.slot());
    }

    /**
     * 总和值等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colSumEq(final String column, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值等于
     *
     * @param column 字段名名
     * @param value  值
     * @return {@code this}
     */
    default C colAvgEq(final String column, final Object value) {
        return this.colAvgEq(column, value, this.slot());
    }

    /**
     * 平均值等于
     *
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colAvgEq(final String column, final Object value, final LogicSymbol slot) {
        return this.colAvgEq(column, false, value, slot);
    }

    /**
     * 平均值等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colAvgEq(final String column, final boolean distinct, final Object value) {
        return this.colAvgEq(column, distinct, value, this.slot());
    }

    /**
     * 平均值等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colAvgEq(final String column, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值等于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colMinEq(final String column, final Object value) {
        return this.colMinEq(column, value, this.slot());
    }

    /**
     * 最小值等于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMinEq(final String column, final Object value, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值等于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colMaxEq(final String column, final Object value) {
        return this.colMaxEq(column, value, this.slot());
    }

    /**
     * 最大值等于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMaxEq(final String column, final Object value, final LogicSymbol slot);

    // endregion

    // endregion

    // region Not equal methods

    // region Count

    /**
     * 总数不等于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colCountNe(final String column, final long value) {
        return this.colCountNe(column, value, this.slot());
    }

    /**
     * 总数不等于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colCountNe(final String column, final long value, final LogicSymbol slot) {
        return this.colCountNe(column, false, value, slot);
    }

    /**
     * 总数不等于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colCountNe(final String column, final boolean distinct, final long value) {
        return this.colCountNe(column, distinct, value, this.slot());
    }

    /**
     * 总数不等于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colCountNe(final String column, final boolean distinct, final long value, final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和值不等于
     *
     * @param column 字段名名
     * @param value  值
     * @return {@code this}
     */
    default C colSumNe(final String column, final Object value) {
        return this.colSumNe(column, value, this.slot());
    }

    /**
     * 总和值不等于
     *
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colSumNe(final String column, final Object value, final LogicSymbol slot) {
        return this.colSumNe(column, false, value, slot);
    }

    /**
     * 总和值不等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colSumNe(final String column, final boolean distinct, final Object value) {
        return this.colSumNe(column, distinct, value, this.slot());
    }

    /**
     * 总和值不等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colSumNe(final String column, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值不等于
     *
     * @param column 字段名名
     * @param value  值
     * @return {@code this}
     */
    default C colAvgNe(final String column, final Object value) {
        return this.colAvgNe(column, value, this.slot());
    }

    /**
     * 平均值不等于
     *
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colAvgNe(final String column, final Object value, final LogicSymbol slot) {
        return this.colAvgNe(column, false, value, slot);
    }

    /**
     * 平均值不等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colAvgNe(final String column, final boolean distinct, final Object value) {
        return this.colAvgNe(column, distinct, value, this.slot());
    }

    /**
     * 平均值不等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colAvgNe(final String column, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值不等于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colMinNe(final String column, final Object value) {
        return this.colMinNe(column, value, this.slot());
    }

    /**
     * 最小值不等于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMinNe(final String column, final Object value, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值不等于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colMaxNe(final String column, final Object value) {
        return this.colMaxNe(column, value, this.slot());
    }

    /**
     * 最大值不等于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMaxNe(final String column, final Object value, final LogicSymbol slot);

    // endregion

    // endregion

    // region Greater than methods

    // region Count

    /**
     * 总数大于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colCountGt(final String column, final long value) {
        return this.colCountGt(column, value, this.slot());
    }

    /**
     * 总数大于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colCountGt(final String column, final long value, final LogicSymbol slot) {
        return this.colCountGt(column, false, value, slot);
    }

    /**
     * 总数大于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colCountGt(final String column, final boolean distinct, final long value) {
        return this.colCountGt(column, distinct, value, this.slot());
    }

    /**
     * 总数大于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colCountGt(final String column, final boolean distinct, final long value, final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和值大于
     *
     * @param column 字段名名
     * @param value  值
     * @return {@code this}
     */
    default C colSumGt(final String column, final Object value) {
        return this.colSumGt(column, value, this.slot());
    }

    /**
     * 总和值大于
     *
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colSumGt(final String column, final Object value, final LogicSymbol slot) {
        return this.colSumGt(column, false, value, slot);
    }

    /**
     * 总和值大于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colSumGt(final String column, final boolean distinct, final Object value) {
        return this.colSumGt(column, distinct, value, this.slot());
    }

    /**
     * 总和值大于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colSumGt(final String column, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值大于
     *
     * @param column 字段名名
     * @param value  值
     * @return {@code this}
     */
    default C colAvgGt(final String column, final Object value) {
        return this.colAvgGt(column, value, this.slot());
    }

    /**
     * 平均值大于
     *
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colAvgGt(final String column, final Object value, final LogicSymbol slot) {
        return this.colAvgGt(column, false, value, slot);
    }

    /**
     * 平均值大于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colAvgGt(final String column, final boolean distinct, final Object value) {
        return this.colAvgGt(column, distinct, value, this.slot());
    }

    /**
     * 平均值大于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colAvgGt(final String column, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值大于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colMinGt(final String column, final Object value) {
        return this.colMinGt(column, value, this.slot());
    }

    /**
     * 最小值大于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMinGt(final String column, final Object value, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值大于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colMaxGt(final String column, final Object value) {
        return this.colMaxGt(column, value, this.slot());
    }

    /**
     * 最大值大于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMaxGt(final String column, final Object value, final LogicSymbol slot);

    // endregion

    // endregion

    // region Greater than or equal to methods

    // region Count

    /**
     * 总数大于等于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colCountGe(final String column, final long value) {
        return this.colCountGe(column, value, this.slot());
    }

    /**
     * 总数大于等于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colCountGe(final String column, final long value, final LogicSymbol slot) {
        return this.colCountGe(column, false, value, slot);
    }

    /**
     * 总数大于等于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colCountGe(final String column, final boolean distinct, final long value) {
        return this.colCountGe(column, distinct, value, this.slot());
    }

    /**
     * 总数大于等于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colCountGe(final String column, final boolean distinct, final long value, final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和值大于等于
     *
     * @param column 字段名名
     * @param value  值
     * @return {@code this}
     */
    default C colSumGe(final String column, final Object value) {
        return this.colSumGe(column, value, this.slot());
    }

    /**
     * 总和值大于等于
     *
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colSumGe(final String column, final Object value, final LogicSymbol slot) {
        return this.colSumGe(column, false, value, slot);
    }

    /**
     * 总和值大于等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colSumGe(final String column, final boolean distinct, final Object value) {
        return this.colSumGe(column, distinct, value, this.slot());
    }

    /**
     * 总和值大于等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colSumGe(final String column, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值大于等于
     *
     * @param column 字段名名
     * @param value  值
     * @return {@code this}
     */
    default C colAvgGe(final String column, final Object value) {
        return this.colAvgGe(column, value, this.slot());
    }

    /**
     * 平均值大于等于
     *
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colAvgGe(final String column, final Object value, final LogicSymbol slot) {
        return this.colAvgGe(column, false, value, slot);
    }

    /**
     * 平均值大于等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colAvgGe(final String column, final boolean distinct, final Object value) {
        return this.colAvgGe(column, distinct, value, this.slot());
    }

    /**
     * 平均值大于等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colAvgGe(final String column, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值大于等于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colMinGe(final String column, final Object value) {
        return this.colMinGe(column, value, this.slot());
    }

    /**
     * 最小值大于等于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMinGe(final String column, final Object value, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值大于等于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colMaxGe(final String column, final Object value) {
        return this.colMaxGe(column, value, this.slot());
    }

    /**
     * 最大值大于等于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMaxGe(final String column, final Object value, final LogicSymbol slot);

    // endregion

    // endregion 

    // region Less than methods

    // region Count

    /**
     * 总数小于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colCountLt(final String column, final long value) {
        return this.colCountLt(column, value, this.slot());
    }

    /**
     * 总数小于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colCountLt(final String column, final long value, final LogicSymbol slot) {
        return this.colCountLt(column, false, value, slot);
    }

    /**
     * 总数小于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colCountLt(final String column, final boolean distinct, final long value) {
        return this.colCountLt(column, distinct, value, this.slot());
    }

    /**
     * 总数小于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colCountLt(final String column, final boolean distinct, final long value, final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和值小于
     *
     * @param column 字段名名
     * @param value  值
     * @return {@code this}
     */
    default C colSumLt(final String column, final Object value) {
        return this.colSumLt(column, value, this.slot());
    }

    /**
     * 总和值小于
     *
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colSumLt(final String column, final Object value, final LogicSymbol slot) {
        return this.colSumLt(column, false, value, slot);
    }

    /**
     * 总和值小于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colSumLt(final String column, final boolean distinct, final Object value) {
        return this.colSumLt(column, distinct, value, this.slot());
    }

    /**
     * 总和值小于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colSumLt(final String column, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值小于
     *
     * @param column 字段名名
     * @param value  值
     * @return {@code this}
     */
    default C colAvgLt(final String column, final Object value) {
        return this.colAvgLt(column, value, this.slot());
    }

    /**
     * 平均值小于
     *
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colAvgLt(final String column, final Object value, final LogicSymbol slot) {
        return this.colAvgLt(column, false, value, slot);
    }

    /**
     * 平均值小于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colAvgLt(final String column, final boolean distinct, final Object value) {
        return this.colAvgLt(column, distinct, value, this.slot());
    }

    /**
     * 平均值小于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colAvgLt(final String column, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值小于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colMinLt(final String column, final Object value) {
        return this.colMinLt(column, value, this.slot());
    }

    /**
     * 最小值小于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMinLt(final String column, final Object value, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值小于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colMaxLt(final String column, final Object value) {
        return this.colMaxLt(column, value, this.slot());
    }

    /**
     * 最大值小于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMaxLt(final String column, final Object value, final LogicSymbol slot);

    // endregion

    // endregion

    // region Less than or equal to methods

    // region Count

    /**
     * 总数小于等于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colCountLe(final String column, final long value) {
        return this.colCountLe(column, value, this.slot());
    }

    /**
     * 总数小于等于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colCountLe(final String column, final long value, final LogicSymbol slot) {
        return this.colCountLe(column, false, value, slot);
    }

    /**
     * 总数小于等于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colCountLe(final String column, final boolean distinct, final long value) {
        return this.colCountLe(column, distinct, value, this.slot());
    }

    /**
     * 总数小于等于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colCountLe(final String column, final boolean distinct, final long value, final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和值小于等于
     *
     * @param column 字段名名
     * @param value  值
     * @return {@code this}
     */
    default C colSumLe(final String column, final Object value) {
        return this.colSumLe(column, value, this.slot());
    }

    /**
     * 总和值小于等于
     *
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colSumLe(final String column, final Object value, final LogicSymbol slot) {
        return this.colSumLe(column, false, value, slot);
    }

    /**
     * 总和值小于等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colSumLe(final String column, final boolean distinct, final Object value) {
        return this.colSumLe(column, distinct, value, this.slot());
    }

    /**
     * 总和值小于等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colSumLe(final String column, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值小于等于
     *
     * @param column 字段名名
     * @param value  值
     * @return {@code this}
     */
    default C colAvgLe(final String column, final Object value) {
        return this.colAvgLe(column, value, this.slot());
    }

    /**
     * 平均值小于等于
     *
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colAvgLe(final String column, final Object value, final LogicSymbol slot) {
        return this.colAvgLe(column, false, value, slot);
    }

    /**
     * 平均值小于等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@code this}
     */
    default C colAvgLe(final String column, final boolean distinct, final Object value) {
        return this.colAvgLe(column, distinct, value, this.slot());
    }

    /**
     * 平均值小于等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colAvgLe(final String column, final boolean distinct, final Object value, final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值小于等于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colMinLe(final String column, final Object value) {
        return this.colMinLe(column, value, this.slot());
    }

    /**
     * 最小值小于等于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMinLe(final String column, final Object value, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值小于等于
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colMaxLe(final String column, final Object value) {
        return this.colMaxLe(column, value, this.slot());
    }

    /**
     * 最大值小于等于
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMaxLe(final String column, final Object value, final LogicSymbol slot);

    // endregion

    // endregion

}
