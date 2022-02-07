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

import java.util.Collection;

/**
 * 聚合函数筛选范围条件接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/2/6
 * @since 1.0.0
 */
public interface PlainFuncRange<T, C extends PlainFuncRange<T, C>> extends Slot<T, C> {

    // region Between methods

    // region Count

    /**
     * 总数范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    default C colCountBetween(final String column, final long begin, final long end) {
        return this.colCountBetween(column, begin, end, this.slot());
    }

    /**
     * 总数范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colCountBetween(final String column, final long begin, final long end, final LogicSymbol slot) {
        return this.colCountBetween(column, false, begin, end, slot);
    }

    /**
     * 总数范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C colCountBetween(final String column, final boolean distinct, final long begin, final long end) {
        return this.colCountBetween(column, distinct, begin, end, this.slot());
    }

    /**
     * 总数范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colCountBetween(final String column, final boolean distinct, final long begin, final long end,
                      final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    default C colSumBetween(final String column, final Object begin, final Object end) {
        return this.colSumBetween(column, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colSumBetween(final String column, final Object begin, final Object end, final LogicSymbol slot) {
        return this.colSumBetween(column, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C colSumBetween(final String column, final boolean distinct, final Object begin, final Object end) {
        return this.colSumBetween(column, distinct, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colSumBetween(final String column, final boolean distinct, final Object begin, final Object end,
                    final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    default C colAvgBetween(final String column, final Object begin, final Object end) {
        return this.colAvgBetween(column, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colAvgBetween(final String column, final Object begin, final Object end, final LogicSymbol slot) {
        return this.colAvgBetween(column, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C colAvgBetween(final String column, final boolean distinct, final Object begin, final Object end) {
        return this.colAvgBetween(column, distinct, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colAvgBetween(final String column, final boolean distinct, final Object begin, final Object end,
                    final LogicSymbol slot);

    // endregion

    // region Min


    /**
     * 最小值范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    default C colMinBetween(final String column, final Object begin, final Object end) {
        return this.colMinBetween(column, begin, end, this.slot());
    }

    /**
     * 最小值范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMinBetween(final String column, final Object begin, final Object end, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    default C colMaxBetween(final String column, final Object begin, final Object end) {
        return this.colMaxBetween(column, begin, end, this.slot());
    }

    /**
     * 最大值范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMaxBetween(final String column, final Object begin, final Object end, final LogicSymbol slot);

    // endregion

    // endregion

    // region Not between methods

    // region Count

    /**
     * 总数范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    default C colCountNotBetween(final String column, final long begin, final long end) {
        return this.colCountNotBetween(column, begin, end, this.slot());
    }

    /**
     * 总数范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colCountNotBetween(final String column, final long begin, final long end, final LogicSymbol slot) {
        return this.colCountNotBetween(column, false, begin, end, slot);
    }

    /**
     * 总数范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C colCountNotBetween(final String column, final boolean distinct, final long begin, final long end) {
        return this.colCountNotBetween(column, distinct, begin, end, this.slot());
    }

    /**
     * 总数范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colCountNotBetween(final String column, final boolean distinct, final long begin, final long end,
                         final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    default C colSumNotBetween(final String column, final Object begin, final Object end) {
        return this.colSumNotBetween(column, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colSumNotBetween(final String column, final Object begin, final Object end, final LogicSymbol slot) {
        return this.colSumNotBetween(column, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C colSumNotBetween(final String column, final boolean distinct, final Object begin, final Object end) {
        return this.colSumNotBetween(column, distinct, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colSumNotBetween(final String column, final boolean distinct, final Object begin, final Object end,
                       final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    default C colAvgNotBetween(final String column, final Object begin, final Object end) {
        return this.colAvgNotBetween(column, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colAvgNotBetween(final String column, final Object begin, final Object end, final LogicSymbol slot) {
        return this.colAvgNotBetween(column, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C colAvgNotBetween(final String column, final boolean distinct, final Object begin, final Object end) {
        return this.colAvgNotBetween(column, distinct, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colAvgNotBetween(final String column, final boolean distinct, final Object begin, final Object end,
                       final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    default C colMinNotBetween(final String column, final Object begin, final Object end) {
        return this.colMinNotBetween(column, begin, end, this.slot());
    }

    /**
     * 最小值范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMinNotBetween(final String column, final Object begin, final Object end, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    default C colMaxNotBetween(final String column, final Object begin, final Object end) {
        return this.colMaxNotBetween(column, begin, end, this.slot());
    }

    /**
     * 最大值范围
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMaxNotBetween(final String column, final Object begin, final Object end, final LogicSymbol slot);

    // endregion

    // endregion

    // region In methods

    // region Count

    /**
     * 总数范围
     *
     * @param column 字段名
     * @param values 值
     * @return {@code this}
     */
    default C colCountIn(final String column, final Collection<?> values) {
        return this.colCountIn(column, values, this.slot());
    }

    /**
     * 总数范围
     *
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colCountIn(final String column, final Collection<?> values, final LogicSymbol slot) {
        return this.colCountIn(column, false, values, slot);
    }

    /**
     * 总数范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C colCountIn(final String column, final boolean distinct, final Collection<?> values) {
        return this.colCountIn(column, distinct, values, this.slot());
    }

    /**
     * 总数范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colCountIn(final String column, final boolean distinct, final Collection<?> values,
                 final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和范围
     *
     * @param column 字段名
     * @param values 值
     * @return {@code this}
     */
    default C colSumIn(final String column, final Collection<?> values) {
        return this.colSumIn(column, values, this.slot());
    }

    /**
     * 总和范围
     *
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colSumIn(final String column, final Collection<?> values, final LogicSymbol slot) {
        return this.colSumIn(column, false, values, slot);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C colSumIn(final String column, final boolean distinct, final Collection<?> values) {
        return this.colSumIn(column, distinct, values, this.slot());
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colSumIn(final String column, final boolean distinct, final Collection<?> values,
               final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值范围
     *
     * @param column 字段名
     * @param values 值
     * @return {@code this}
     */
    default C colAvgIn(final String column, final Collection<?> values) {
        return this.colAvgIn(column, values, this.slot());
    }

    /**
     * 平均值范围
     *
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colAvgIn(final String column, final Collection<?> values, final LogicSymbol slot) {
        return this.colAvgIn(column, false, values, slot);
    }

    /**
     * 平均值范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C colAvgIn(final String column, final boolean distinct, final Collection<?> values) {
        return this.colAvgIn(column, distinct, values, this.slot());
    }

    /**
     * 平均值范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colAvgIn(final String column, final boolean distinct, final Collection<?> values,
               final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值范围
     *
     * @param column 字段名
     * @param values 值
     * @return {@code this}
     */
    default C colMinIn(final String column, final Collection<?> values) {
        return this.colMinIn(column, values, this.slot());
    }

    /**
     * 最小值范围
     *
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMinIn(final String column, final Collection<?> values, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值范围
     *
     * @param column 字段名
     * @param values 值
     * @return {@code this}
     */
    default C colMaxIn(final String column, final Collection<?> values) {
        return this.colMaxIn(column, values, this.slot());
    }

    /**
     * 最大值范围
     *
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMaxIn(final String column, final Collection<?> values, final LogicSymbol slot);

    // endregion

    // endregion

    // region Not in methods

    // region Count

    /**
     * 总数范围
     *
     * @param column 字段名
     * @param values 值
     * @return {@code this}
     */
    default C colCountNotIn(final String column, final Collection<?> values) {
        return this.colCountNotIn(column, values, this.slot());
    }

    /**
     * 总数范围
     *
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colCountNotIn(final String column, final Collection<?> values, final LogicSymbol slot) {
        return this.colCountNotIn(column, false, values, slot);
    }

    /**
     * 总数范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C colCountNotIn(final String column, final boolean distinct, final Collection<?> values) {
        return this.colCountNotIn(column, distinct, values, this.slot());
    }

    /**
     * 总数范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colCountNotIn(final String column, final boolean distinct, final Collection<?> values,
                    final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和范围
     *
     * @param column 字段名
     * @param values 值
     * @return {@code this}
     */
    default C colSumNotIn(final String column, final Collection<?> values) {
        return this.colSumNotIn(column, values, this.slot());
    }

    /**
     * 总和范围
     *
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colSumNotIn(final String column, final Collection<?> values, final LogicSymbol slot) {
        return this.colSumNotIn(column, false, values, slot);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C colSumNotIn(final String column, final boolean distinct, final Collection<?> values) {
        return this.colSumNotIn(column, distinct, values, this.slot());
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colSumNotIn(final String column, final boolean distinct, final Collection<?> values,
                  final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值范围
     *
     * @param column 字段名
     * @param values 值
     * @return {@code this}
     */
    default C colAvgNotIn(final String column, final Collection<?> values) {
        return this.colAvgNotIn(column, values, this.slot());
    }

    /**
     * 平均值范围
     *
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colAvgNotIn(final String column, final Collection<?> values, final LogicSymbol slot) {
        return this.colAvgNotIn(column, false, values, slot);
    }

    /**
     * 平均值范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C colAvgNotIn(final String column, final boolean distinct, final Collection<?> values) {
        return this.colAvgNotIn(column, distinct, values, this.slot());
    }

    /**
     * 平均值范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C colAvgNotIn(final String column, final boolean distinct, final Collection<?> values,
                  final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值范围
     *
     * @param column 字段名
     * @param values 值
     * @return {@code this}
     */
    default C colMinNotIn(final String column, final Collection<?> values) {
        return this.colMinNotIn(column, values, this.slot());
    }

    /**
     * 最小值范围
     *
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMinNotIn(final String column, final Collection<?> values, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值范围
     *
     * @param column 字段名
     * @param values 值
     * @return {@code this}
     */
    default C colMaxNotIn(final String column, final Collection<?> values) {
        return this.colMaxNotIn(column, values, this.slot());
    }

    /**
     * 最大值范围
     *
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C colMaxNotIn(final String column, final Collection<?> values, final LogicSymbol slot);

    // endregion

    // endregion

}
