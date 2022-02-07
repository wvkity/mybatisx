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
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.core.criteria.support.Slot;
import io.github.mybatisx.core.param.BetweenParam;
import io.github.mybatisx.core.param.InParam;
import io.github.mybatisx.core.param.Param;
import io.github.mybatisx.core.param.SimpleParam;
import io.github.mybatisx.core.support.function.AggFunction;

import java.util.Collection;

/**
 * 通用分组筛选条件接口
 *
 * @author wvkity
 * @created 2022/1/30
 * @since 1.0.0
 */
public interface GenericHaving<T, C extends GenericHaving<T, C>> extends Slot<T, C> {

    // region Count methods

    /**
     * 总数等于
     *
     * @param value 值
     * @return {@code this}
     */
    default C countEq(final long value) {
        return this.countEq(value, this.slot());
    }

    /**
     * 总数等于
     *
     * @param value 值
     * @param slot  {@link LogicSymbol}
     * @return {@code this}
     */
    C countEq(final long value, final LogicSymbol slot);

    /**
     * 总数不等于
     *
     * @param value 值
     * @return {@code this}
     */
    default C countNe(final long value) {
        return this.countNe(value, this.slot());
    }

    /**
     * 总数不等于
     *
     * @param value 值
     * @param slot  {@link LogicSymbol}
     * @return {@code this}
     */
    C countNe(final long value, final LogicSymbol slot);

    /**
     * 总数大于
     *
     * @param value 值
     * @return {@code this}
     */
    default C countGt(final long value) {
        return this.countGt(value, this.slot());
    }

    /**
     * 总数大于
     *
     * @param value 值
     * @param slot  {@link LogicSymbol}
     * @return {@code this}
     */
    C countGt(final long value, final LogicSymbol slot);

    /**
     * 总数大于等于
     *
     * @param value 值
     * @return {@code this}
     */
    default C countGe(final long value) {
        return this.countGe(value, this.slot());
    }

    /**
     * 总数大于等于
     *
     * @param value 值
     * @param slot  {@link LogicSymbol}
     * @return {@code this}
     */
    C countGe(final long value, final LogicSymbol slot);

    /**
     * 总数小于
     *
     * @param value 值
     * @return {@code this}
     */
    default C countLt(final long value) {
        return this.countLt(value, this.slot());
    }

    /**
     * 总数小于
     *
     * @param value 值
     * @param slot  {@link LogicSymbol}
     * @return {@code this}
     */
    C countLt(final long value, final LogicSymbol slot);

    /**
     * 总数小于等于
     *
     * @param value 值
     * @return {@code this}
     */
    default C countLe(final long value) {
        return this.countLe(value, this.slot());
    }

    /**
     * 总数小于等于
     *
     * @param value 值
     * @param slot  {@link LogicSymbol}
     * @return {@code this}
     */
    C countLe(final long value, final LogicSymbol slot);

    /**
     * 总数范围
     *
     * @param begin 开始值
     * @param end   结束值
     * @return {@code this}
     */
    default C countBetween(final long begin, final long end) {
        return this.countBetween(begin, end, this.slot());
    }

    /**
     * 总数范围
     *
     * @param begin 开始值
     * @param end   结束值
     * @param slot  {@link LogicSymbol}
     * @return {@code this}
     */
    C countBetween(final long begin, final long end, final LogicSymbol slot);

    /**
     * 总数范围
     *
     * @param begin 开始值
     * @param end   结束值
     * @return {@code this}
     */
    default C countNotBetween(final long begin, final long end) {
        return this.countNotBetween(begin, end, this.slot());
    }

    /**
     * 总数范围
     *
     * @param begin 开始值
     * @param end   结束值
     * @param slot  {@link LogicSymbol}
     * @return {@code this}
     */
    C countNotBetween(final long begin, final long end, final LogicSymbol slot);

    /**
     * 总数范围
     *
     * @param values 值
     * @return {@code this}
     */
    default C countIn(final Collection<?> values) {
        return this.countIn(values, this.slot());
    }

    /**
     * 总数范围
     *
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C countIn(final Collection<?> values, final LogicSymbol slot);

    /**
     * 总数范围
     *
     * @param values 值
     * @return {@code this}
     */
    default C countNotIn(final Collection<?> values) {
        return this.countNotIn(values, this.slot());
    }

    /**
     * 总数范围
     *
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    C countNotIn(final Collection<?> values, final LogicSymbol slot);

    // endregion

    /**
     * 聚合函数等于
     *
     * @param alias 聚合函数别名
     * @param value 值
     * @return {@code this}
     */
    default C funcEq(final String alias, final Object value) {
        return this.funcEq(alias, value, this.slot());
    }

    /**
     * 聚合函数等于
     *
     * @param alias 聚合函数别名
     * @param value 值
     * @param slot  {@link LogicSymbol}
     * @return {@code this}
     */
    default C funcEq(final String alias, final Object value, final LogicSymbol slot) {
        return this.having(alias, SimpleParam.builder()
                .symbol(Symbol.EQ)
                .slot(slot)
                .value(value)
                .build());
    }

    /**
     * 聚合函数不等于
     *
     * @param alias 聚合函数别名
     * @param value 值
     * @return {@code this}
     */
    default C funcNe(final String alias, final Object value) {
        return this.funcNe(alias, value, this.slot());
    }

    /**
     * 聚合函数不等于
     *
     * @param alias 聚合函数别名
     * @param value 值
     * @param slot  {@link LogicSymbol}
     * @return {@code this}
     */
    default C funcNe(final String alias, final Object value, final LogicSymbol slot) {
        return this.having(alias, SimpleParam.builder()
                .symbol(Symbol.NE)
                .slot(slot)
                .value(value)
                .build());
    }

    /**
     * 聚合函数大于
     *
     * @param alias 表别名
     * @param value 值
     * @return {@code this}
     */
    default C funcGt(final String alias, final Object value) {
        return this.funcGt(alias, value, this.slot());
    }

    /**
     * 聚合函数大于
     *
     * @param alias 表别名
     * @param value 值
     * @param slot  {@link LogicSymbol}
     * @return {@code this}
     */
    default C funcGt(final String alias, final Object value, final LogicSymbol slot) {
        return this.having(alias, SimpleParam.builder()
                .symbol(Symbol.GT)
                .slot(slot)
                .value(value)
                .build());
    }

    /**
     * 聚合函数大于等于
     *
     * @param alias 表别名
     * @param value 值
     * @return {@code this}
     */
    default C funcGe(final String alias, final Object value) {
        return this.funcGe(alias, value, this.slot());
    }

    /**
     * 聚合函数大于等于
     *
     * @param alias 表别名
     * @param value 值
     * @param slot  {@link LogicSymbol}
     * @return {@code this}
     */
    default C funcGe(final String alias, final Object value, final LogicSymbol slot) {
        return this.having(alias, SimpleParam.builder()
                .symbol(Symbol.GE)
                .slot(slot)
                .value(value)
                .build());
    }

    /**
     * 聚合函数小于
     *
     * @param alias 表别名
     * @param value 值
     * @return {@code this}
     */
    default C funcLt(final String alias, final Object value) {
        return this.funcLt(alias, value, this.slot());
    }

    /**
     * 聚合函数小于
     *
     * @param alias 表别名
     * @param value 值
     * @param slot  {@link LogicSymbol}
     * @return {@code this}
     */
    default C funcLt(final String alias, final Object value, final LogicSymbol slot) {
        return this.having(alias, SimpleParam.builder()
                .symbol(Symbol.LT)
                .slot(slot)
                .value(value)
                .build());
    }

    /**
     * 聚合函数小于等于
     *
     * @param alias 表别名
     * @param value 值
     * @return {@code this}
     */
    default C funcLe(final String alias, final Object value) {
        return this.funcLe(alias, value, this.slot());
    }

    /**
     * 聚合函数小于等于
     *
     * @param alias 表别名
     * @param value 值
     * @param slot  {@link LogicSymbol}
     * @return {@code this}
     */
    default C funcLe(final String alias, final Object value, final LogicSymbol slot) {
        return this.having(alias, SimpleParam.builder()
                .symbol(Symbol.LE)
                .slot(slot)
                .value(value)
                .build());
    }

    /**
     * 聚合函数in范围
     *
     * @param alias  表别名
     * @param values 值列表
     * @return {@code this}
     */
    default C funcIn(final String alias, final Collection<?> values) {
        return this.funcIn(alias, values, this.slot());
    }

    /**
     * 聚合函数in范围
     *
     * @param alias  表别名
     * @param values 值列表
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C funcIn(final String alias, final Collection<?> values, final LogicSymbol slot) {
        return this.having(alias, InParam.builder()
                .symbol(Symbol.IN)
                .slot(slot)
                .values(values)
                .build());
    }

    /**
     * 聚合函数in范围
     *
     * @param alias  表别名
     * @param values 值列表
     * @return {@code this}
     */
    default C funcNotIn(final String alias, final Collection<?> values) {
        return this.funcNotIn(alias, values, this.slot());
    }

    /**
     * 聚合函数in范围
     *
     * @param alias  表别名
     * @param values 值列表
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C funcNotIn(final String alias, final Collection<?> values, final LogicSymbol slot) {
        return this.having(alias, InParam.builder()
                .symbol(Symbol.NOT_IN)
                .slot(slot)
                .values(values)
                .build());
    }

    /**
     * 聚合函数between范围
     *
     * @param alias 表别名
     * @param begin 开始值
     * @param end   结束值
     * @return {@code this}
     */
    default C funcBetween(final String alias, final Object begin, final Object end) {
        return this.funcBetween(alias, begin, end, this.slot());
    }

    /**
     * 聚合函数between范围
     *
     * @param alias 表别名
     * @param begin 开始值
     * @param end   结束值
     * @param slot  {@link LogicSymbol}
     * @return {@code this}
     */
    default C funcBetween(final String alias, final Object begin, final Object end, final LogicSymbol slot) {
        return this.having(alias, BetweenParam.builder()
                .symbol(Symbol.BETWEEN)
                .slot(slot)
                .begin(begin)
                .end(end)
                .build());
    }

    /**
     * 聚合函数not between范围
     *
     * @param alias 表别名
     * @param begin 开始值
     * @param end   结束值
     * @return {@code this}
     */
    default C funcNotBetween(final String alias, final Object begin, final Object end) {
        return this.funcNotBetween(alias, begin, end, this.slot());
    }

    /**
     * 聚合函数not between范围
     *
     * @param alias 表别名
     * @param begin 开始值
     * @param end   结束值
     * @param slot  {@link LogicSymbol}
     * @return {@code this}
     */
    default C funcNotBetween(final String alias, final Object begin, final Object end, final LogicSymbol slot) {
        return this.having(alias, BetweenParam.builder()
                .symbol(Symbol.NOT_BETWEEN)
                .slot(slot)
                .begin(begin)
                .end(end)
                .build());
    }

    /**
     * 分组筛选
     *
     * @param alias 聚合函数别名
     * @param param {@link Param}
     * @return {@code this}
     */
    C having(final String alias, final Param param);

    /**
     * 分组筛选
     *
     * @param function {@link AggFunction}
     * @param param    {@link Param}
     * @return {@code this}
     */
    C having(final AggFunction function, final Param param);

}
