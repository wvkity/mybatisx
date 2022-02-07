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
public interface LambdaFuncRange<T, C extends LambdaFuncRange<T, C>> extends Slot<T, C>, PropertyConverter<T> {

    // region Between methods

    // region Count

    /**
     * 总数范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C countBetween(final Property<T, ?> property, final long begin, final long end) {
        return this.countBetween(property, begin, end, this.slot());
    }

    /**
     * 总数范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countBetween(final Property<T, ?> property, final long begin, final long end,
                           final LogicSymbol slot) {
        return this.countBetween(property, false, begin, end, slot);
    }

    /**
     * 总数范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C countBetween(final Property<T, ?> property, final boolean distinct,
                           final long begin, final long end) {
        return this.countBetween(property, distinct, begin, end, this.slot());
    }

    /**
     * 总数范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countBetween(final Property<T, ?> property, final boolean distinct, final long begin, final long end,
                           final LogicSymbol slot) {
        return this.countBetween(this.convert(property), distinct, begin, end, slot);
    }

    /**
     * 总数范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C countBetween(final String property, final long begin, final long end) {
        return this.countBetween(property, begin, end, this.slot());
    }

    /**
     * 总数范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countBetween(final String property, final long begin, final long end, final LogicSymbol slot) {
        return this.countBetween(property, false, begin, end, slot);
    }

    /**
     * 总数范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C countBetween(final String property, final boolean distinct, final long begin, final long end) {
        return this.countBetween(property, distinct, begin, end, this.slot());
    }

    /**
     * 总数范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C countBetween(final String property, final boolean distinct, final long begin, final long end,
                   final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C sumBetween(final Property<T, ?> property, final Object begin, final Object end) {
        return this.sumBetween(property, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumBetween(final Property<T, ?> property, final Object begin, final Object end,
                         final LogicSymbol slot) {
        return this.sumBetween(property, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C sumBetween(final Property<T, ?> property, final boolean distinct,
                         final Object begin, final Object end) {
        return this.sumBetween(property, distinct, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumBetween(final Property<T, ?> property, final boolean distinct, final Object begin, final Object end,
                         final LogicSymbol slot) {
        return this.sumBetween(this.convert(property), distinct, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C sumBetween(final String property, final Object begin, final Object end) {
        return this.sumBetween(property, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumBetween(final String property, final Object begin, final Object end, final LogicSymbol slot) {
        return this.sumBetween(property, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C sumBetween(final String property, final boolean distinct, final Object begin, final Object end) {
        return this.sumBetween(property, distinct, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C sumBetween(final String property, final boolean distinct, final Object begin, final Object end,
                 final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C avgBetween(final Property<T, ?> property, final Object begin, final Object end) {
        return this.avgBetween(property, begin, end, this.slot());
    }

    /**
     * 平均值范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgBetween(final Property<T, ?> property, final Object begin, final Object end,
                         final LogicSymbol slot) {
        return this.avgBetween(property, false, begin, end, slot);
    }

    /**
     * 平均值范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C avgBetween(final Property<T, ?> property, final boolean distinct,
                         final Object begin, final Object end) {
        return this.avgBetween(property, distinct, begin, end, this.slot());
    }

    /**
     * 平均值范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgBetween(final Property<T, ?> property, final boolean distinct, final Object begin, final Object end,
                         final LogicSymbol slot) {
        return this.avgBetween(this.convert(property), distinct, begin, end, slot);
    }

    /**
     * 平均值范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C avgBetween(final String property, final Object begin, final Object end) {
        return this.avgBetween(property, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgBetween(final String property, final Object begin, final Object end, final LogicSymbol slot) {
        return this.avgBetween(property, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C avgBetween(final String property, final boolean distinct, final Object begin, final Object end) {
        return this.avgBetween(property, distinct, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C avgBetween(final String property, final boolean distinct, final Object begin, final Object end,
                 final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C minBetween(final Property<T, ?> property, final Object begin, final Object end) {
        return this.minBetween(property, begin, end, this.slot());
    }

    /**
     * 最小值范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C minBetween(final Property<T, ?> property, final Object begin, final Object end,
                         final LogicSymbol slot) {
        return this.minBetween(this.convert(property), begin, end, slot);
    }

    /**
     * 最小值范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C minBetween(final String property, final Object begin, final Object end) {
        return this.minBetween(property, begin, end, this.slot());
    }

    /**
     * 最小值范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C minBetween(final String property, final Object begin, final Object end, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C maxBetween(final Property<T, ?> property, final Object begin, final Object end) {
        return this.maxBetween(property, begin, end, this.slot());
    }

    /**
     * 最大值范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C maxBetween(final Property<T, ?> property, final Object begin, final Object end,
                         final LogicSymbol slot) {
        return this.maxBetween(this.convert(property), begin, end, slot);
    }

    /**
     * 最大值范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C maxBetween(final String property, final Object begin, final Object end) {
        return this.maxBetween(property, begin, end, this.slot());
    }

    /**
     * 最大值范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C maxBetween(final String property, final Object begin, final Object end, final LogicSymbol slot);

    // endregion

    // endregion

    // region Not between methods

    // region Count

    /**
     * 总数范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C countNotBetween(final Property<T, ?> property, final long begin, final long end) {
        return this.countNotBetween(property, begin, end, this.slot());
    }

    /**
     * 总数范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countNotBetween(final Property<T, ?> property, final long begin, final long end,
                              final LogicSymbol slot) {
        return this.countNotBetween(property, false, begin, end, slot);
    }

    /**
     * 总数范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C countNotBetween(final Property<T, ?> property, final boolean distinct,
                              final long begin, final long end) {
        return this.countNotBetween(property, distinct, begin, end, this.slot());
    }

    /**
     * 总数范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countNotBetween(final Property<T, ?> property, final boolean distinct, final long begin,
                              final long end, final LogicSymbol slot) {
        return this.countNotBetween(this.convert(property), distinct, begin, end, slot);
    }

    /**
     * 总数范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C countNotBetween(final String property, final long begin, final long end) {
        return this.countNotBetween(property, begin, end, this.slot());
    }

    /**
     * 总数范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countNotBetween(final String property, final long begin, final long end, final LogicSymbol slot) {
        return this.countNotBetween(property, false, begin, end, slot);
    }

    /**
     * 总数范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C countNotBetween(final String property, final boolean distinct, final long begin, final long end) {
        return this.countNotBetween(property, distinct, begin, end, this.slot());
    }

    /**
     * 总数范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C countNotBetween(final String property, final boolean distinct, final long begin, final long end,
                      final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C sumNotBetween(final Property<T, ?> property, final Object begin, final Object end) {
        return this.sumNotBetween(property, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumNotBetween(final Property<T, ?> property, final Object begin, final Object end,
                            final LogicSymbol slot) {
        return this.sumNotBetween(property, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C sumNotBetween(final Property<T, ?> property, final boolean distinct,
                            final Object begin, final Object end) {
        return this.sumNotBetween(property, distinct, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumNotBetween(final Property<T, ?> property, final boolean distinct, final Object begin,
                            final Object end, final LogicSymbol slot) {
        return this.sumNotBetween(this.convert(property), distinct, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C sumNotBetween(final String property, final Object begin, final Object end) {
        return this.sumNotBetween(property, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumNotBetween(final String property, final Object begin, final Object end, final LogicSymbol slot) {
        return this.sumNotBetween(property, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C sumNotBetween(final String property, final boolean distinct, final Object begin, final Object end) {
        return this.sumNotBetween(property, distinct, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C sumNotBetween(final String property, final boolean distinct, final Object begin, final Object end,
                    final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C avgNotBetween(final Property<T, ?> property, final Object begin, final Object end) {
        return this.avgNotBetween(property, begin, end, this.slot());
    }

    /**
     * 平均值范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgNotBetween(final Property<T, ?> property, final Object begin, final Object end,
                            final LogicSymbol slot) {
        return this.avgNotBetween(property, false, begin, end, slot);
    }

    /**
     * 平均值范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C avgNotBetween(final Property<T, ?> property, final boolean distinct,
                            final Object begin, final Object end) {
        return this.avgNotBetween(property, distinct, begin, end, this.slot());
    }

    /**
     * 平均值范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgNotBetween(final Property<T, ?> property, final boolean distinct, final Object begin, final Object end,
                            final LogicSymbol slot) {
        return this.avgNotBetween(this.convert(property), distinct, begin, end, slot);
    }

    /**
     * 平均值范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C avgNotBetween(final String property, final Object begin, final Object end) {
        return this.avgNotBetween(property, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgNotBetween(final String property, final Object begin, final Object end, final LogicSymbol slot) {
        return this.avgNotBetween(property, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C avgNotBetween(final String property, final boolean distinct, final Object begin, final Object end) {
        return this.avgNotBetween(property, distinct, begin, end, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C avgNotBetween(final String property, final boolean distinct, final Object begin, final Object end,
                    final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C minNotBetween(final Property<T, ?> property, final Object begin, final Object end) {
        return this.minNotBetween(property, begin, end, this.slot());
    }

    /**
     * 最小值范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C minNotBetween(final Property<T, ?> property, final Object begin, final Object end,
                            final LogicSymbol slot) {
        return this.minNotBetween(this.convert(property), begin, end, slot);
    }

    /**
     * 最小值范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C minNotBetween(final String property, final Object begin, final Object end) {
        return this.minNotBetween(property, begin, end, this.slot());
    }

    /**
     * 最小值范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C minNotBetween(final String property, final Object begin, final Object end, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C maxNotBetween(final Property<T, ?> property, final Object begin, final Object end) {
        return this.maxNotBetween(property, begin, end, this.slot());
    }

    /**
     * 最大值范围
     *
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C maxNotBetween(final Property<T, ?> property, final Object begin, final Object end,
                            final LogicSymbol slot) {
        return this.maxNotBetween(this.convert(property), begin, end, slot);
    }

    /**
     * 最大值范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default C maxNotBetween(final String property, final Object begin, final Object end) {
        return this.maxNotBetween(property, begin, end, this.slot());
    }

    /**
     * 最大值范围
     *
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C maxNotBetween(final String property, final Object begin, final Object end, final LogicSymbol slot);

    // endregion

    // endregion

    // region In methods

    // region Count

    /**
     * 总数范围
     *
     * @param property {@link Property}
     * @param values   值
     * @return {@code this}
     */
    default C countIn(final Property<T, ?> property, final Collection<?> values) {
        return this.countIn(property, values, this.slot());
    }

    /**
     * 总数范围
     *
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countIn(final Property<T, ?> property, final Collection<?> values, final LogicSymbol slot) {
        return this.countIn(property, false, values, slot);
    }

    /**
     * 总数范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C countIn(final Property<T, ?> property, final boolean distinct, final Collection<?> values) {
        return this.countIn(property, distinct, values, this.slot());
    }

    /**
     * 总数范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countIn(final Property<T, ?> property, final boolean distinct, final Collection<?> values,
                      final LogicSymbol slot) {
        return this.countIn(this.convert(property), distinct, values, slot);
    }

    /**
     * 总数范围
     *
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default C countIn(final String property, final Collection<?> values) {
        return this.countIn(property, values, this.slot());
    }

    /**
     * 总数范围
     *
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countIn(final String property, final Collection<?> values, final LogicSymbol slot) {
        return this.countIn(property, false, values, slot);
    }

    /**
     * 总数范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C countIn(final String property, final boolean distinct, final Collection<?> values) {
        return this.countIn(property, distinct, values, this.slot());
    }

    /**
     * 总数范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C countIn(final String property, final boolean distinct, final Collection<?> values,
              final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和范围
     *
     * @param property {@link Property}
     * @param values   值
     * @return {@code this}
     */
    default C sumIn(final Property<T, ?> property, final Collection<?> values) {
        return this.sumIn(property, values, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumIn(final Property<T, ?> property, final Collection<?> values, final LogicSymbol slot) {
        return this.sumIn(property, false, values, slot);
    }

    /**
     * 总和范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C sumIn(final Property<T, ?> property, final boolean distinct, final Collection<?> values) {
        return this.sumIn(property, distinct, values, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumIn(final Property<T, ?> property, final boolean distinct, final Collection<?> values,
                    final LogicSymbol slot) {
        return this.sumIn(this.convert(property), distinct, values, slot);
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default C sumIn(final String property, final Collection<?> values) {
        return this.sumIn(property, values, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumIn(final String property, final Collection<?> values, final LogicSymbol slot) {
        return this.sumIn(property, false, values, slot);
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C sumIn(final String property, final boolean distinct, final Collection<?> values) {
        return this.sumIn(property, distinct, values, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C sumIn(final String property, final boolean distinct, final Collection<?> values,
            final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值范围
     *
     * @param property {@link Property}
     * @param values   值
     * @return {@code this}
     */
    default C avgIn(final Property<T, ?> property, final Collection<?> values) {
        return this.avgIn(property, values, this.slot());
    }

    /**
     * 平均值范围
     *
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgIn(final Property<T, ?> property, final Collection<?> values, final LogicSymbol slot) {
        return this.avgIn(property, false, values, slot);
    }

    /**
     * 平均值范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C avgIn(final Property<T, ?> property, final boolean distinct, final Collection<?> values) {
        return this.avgIn(property, distinct, values, this.slot());
    }

    /**
     * 平均值范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgIn(final Property<T, ?> property, final boolean distinct, final Collection<?> values,
                    final LogicSymbol slot) {
        return this.avgIn(this.convert(property), distinct, values, slot);
    }

    /**
     * 平均值范围
     *
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default C avgIn(final String property, final Collection<?> values) {
        return this.avgIn(property, values, this.slot());
    }

    /**
     * 平均值范围
     *
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgIn(final String property, final Collection<?> values, final LogicSymbol slot) {
        return this.avgIn(property, false, values, slot);
    }

    /**
     * 平均值范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C avgIn(final String property, final boolean distinct, final Collection<?> values) {
        return this.avgIn(property, distinct, values, this.slot());
    }

    /**
     * 平均值范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C avgIn(final String property, final boolean distinct, final Collection<?> values,
            final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值范围
     *
     * @param property {@link Property}
     * @param values   值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C minIn(final Property<T, V> property, final Collection<V> values) {
        return this.minIn(property, values, this.slot());
    }

    /**
     * 最小值范围
     *
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C minIn(final Property<T, V> property, final Collection<V> values, final LogicSymbol slot) {
        return this.minIn(this.convert(property), values, slot);
    }

    /**
     * 最小值范围
     *
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default C minIn(final String property, final Collection<?> values) {
        return this.minIn(property, values, this.slot());
    }

    /**
     * 最小值范围
     *
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C minIn(final String property, final Collection<?> values, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值范围
     *
     * @param property {@link Property}
     * @param values   值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C maxIn(final Property<T, V> property, final Collection<V> values) {
        return this.maxIn(property, values, this.slot());
    }

    /**
     * 最大值范围
     *
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C maxIn(final Property<T, V> property, final Collection<V> values, final LogicSymbol slot) {
        return this.maxIn(this.convert(property), values, slot);
    }

    /**
     * 最大值范围
     *
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default C maxIn(final String property, final Collection<?> values) {
        return this.maxIn(property, values, this.slot());
    }

    /**
     * 最大值范围
     *
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C maxIn(final String property, final Collection<?> values, final LogicSymbol slot);

    // endregion

    // endregion

    // region Not in methods

    // region Count

    /**
     * 总数范围
     *
     * @param property {@link Property}
     * @param values   值
     * @return {@code this}
     */
    default C countNotIn(final Property<T, ?> property, final Collection<?> values) {
        return this.countNotIn(property, values, this.slot());
    }

    /**
     * 总数范围
     *
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countNotIn(final Property<T, ?> property, final Collection<?> values, final LogicSymbol slot) {
        return this.countNotIn(property, false, values, slot);
    }

    /**
     * 总数范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C countNotIn(final Property<T, ?> property, final boolean distinct, final Collection<?> values) {
        return this.countNotIn(property, distinct, values, this.slot());
    }

    /**
     * 总数范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countNotIn(final Property<T, ?> property, final boolean distinct, final Collection<?> values,
                         final LogicSymbol slot) {
        return this.countNotIn(this.convert(property), distinct, values, slot);
    }

    /**
     * 总数范围
     *
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default C countNotIn(final String property, final Collection<?> values) {
        return this.countNotIn(property, values, this.slot());
    }

    /**
     * 总数范围
     *
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C countNotIn(final String property, final Collection<?> values, final LogicSymbol slot) {
        return this.countNotIn(property, false, values, slot);
    }

    /**
     * 总数范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C countNotIn(final String property, final boolean distinct, final Collection<?> values) {
        return this.countNotIn(property, distinct, values, this.slot());
    }

    /**
     * 总数范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C countNotIn(final String property, final boolean distinct, final Collection<?> values,
                 final LogicSymbol slot);

    // endregion

    // region Sum

    /**
     * 总和范围
     *
     * @param property {@link Property}
     * @param values   值
     * @return {@code this}
     */
    default C sumNotIn(final Property<T, ?> property, final Collection<?> values) {
        return this.sumNotIn(property, values, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumNotIn(final Property<T, ?> property, final Collection<?> values, final LogicSymbol slot) {
        return this.sumNotIn(property, false, values, slot);
    }

    /**
     * 总和范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C sumNotIn(final Property<T, ?> property, final boolean distinct, final Collection<?> values) {
        return this.sumNotIn(property, distinct, values, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumNotIn(final Property<T, ?> property, final boolean distinct, final Collection<?> values,
                       final LogicSymbol slot) {
        return this.sumNotIn(this.convert(property), distinct, values, slot);
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default C sumNotIn(final String property, final Collection<?> values) {
        return this.sumNotIn(property, values, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C sumNotIn(final String property, final Collection<?> values, final LogicSymbol slot) {
        return this.sumNotIn(property, false, values, slot);
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C sumNotIn(final String property, final boolean distinct, final Collection<?> values) {
        return this.sumNotIn(property, distinct, values, this.slot());
    }

    /**
     * 总和范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C sumNotIn(final String property, final boolean distinct, final Collection<?> values,
               final LogicSymbol slot);

    // endregion

    // region Avg

    /**
     * 平均值范围
     *
     * @param property {@link Property}
     * @param values   值
     * @return {@code this}
     */
    default C avgNotIn(final Property<T, ?> property, final Collection<?> values) {
        return this.avgNotIn(property, values, this.slot());
    }

    /**
     * 平均值范围
     *
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgNotIn(final Property<T, ?> property, final Collection<?> values, final LogicSymbol slot) {
        return this.avgNotIn(property, false, values, slot);
    }

    /**
     * 平均值范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C avgNotIn(final Property<T, ?> property, final boolean distinct, final Collection<?> values) {
        return this.avgNotIn(property, distinct, values, this.slot());
    }

    /**
     * 平均值范围
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgNotIn(final Property<T, ?> property, final boolean distinct, final Collection<?> values,
                       final LogicSymbol slot) {
        return this.avgNotIn(this.convert(property), distinct, values, slot);
    }

    /**
     * 平均值范围
     *
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default C avgNotIn(final String property, final Collection<?> values) {
        return this.avgNotIn(property, values, this.slot());
    }

    /**
     * 平均值范围
     *
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C avgNotIn(final String property, final Collection<?> values, final LogicSymbol slot) {
        return this.avgNotIn(property, false, values, slot);
    }

    /**
     * 平均值范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @return {@code this}
     */
    default C avgNotIn(final String property, final boolean distinct, final Collection<?> values) {
        return this.avgNotIn(property, distinct, values, this.slot());
    }

    /**
     * 平均值范围
     *
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C avgNotIn(final String property, final boolean distinct, final Collection<?> values,
               final LogicSymbol slot);

    // endregion

    // region Min

    /**
     * 最小值范围
     *
     * @param property {@link Property}
     * @param values   值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C minNotIn(final Property<T, V> property, final Collection<V> values) {
        return this.minNotIn(property, values, this.slot());
    }

    /**
     * 最小值范围
     *
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C minNotIn(final Property<T, V> property, final Collection<V> values, final LogicSymbol slot) {
        return this.minNotIn(this.convert(property), values, slot);
    }

    /**
     * 最小值范围
     *
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default C minNotIn(final String property, final Collection<?> values) {
        return this.minNotIn(property, values, this.slot());
    }

    /**
     * 最小值范围
     *
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C minNotIn(final String property, final Collection<?> values, final LogicSymbol slot);

    // endregion

    // region Max

    /**
     * 最大值范围
     *
     * @param property {@link Property}
     * @param values   值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C maxNotIn(final Property<T, V> property, final Collection<V> values) {
        return this.maxNotIn(property, values, this.slot());
    }

    /**
     * 最大值范围
     *
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> C maxNotIn(final Property<T, V> property, final Collection<V> values, final LogicSymbol slot) {
        return this.maxNotIn(this.convert(property), values, slot);
    }

    /**
     * 最大值范围
     *
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default C maxNotIn(final String property, final Collection<?> values) {
        return this.maxNotIn(property, values, this.slot());
    }

    /**
     * 最大值范围
     *
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C maxNotIn(final String property, final Collection<?> values, final LogicSymbol slot);

    // endregion

    // endregion

}
