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
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.base.helper.TableHelper;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.core.param.BetweenParam;
import io.github.mybatisx.core.param.InParam;
import io.github.mybatisx.core.param.Param;
import io.github.mybatisx.core.param.SimpleParam;
import io.github.mybatisx.core.property.LambdaMetadataWeakCache;
import io.github.mybatisx.core.property.Property;
import io.github.mybatisx.core.support.function.AggFunction;
import io.github.mybatisx.core.support.function.Avg;
import io.github.mybatisx.core.support.function.Count;
import io.github.mybatisx.core.support.function.Max;
import io.github.mybatisx.core.support.function.Min;
import io.github.mybatisx.core.support.function.Sum;
import io.github.mybatisx.core.support.having.FunctionHaving;
import io.github.mybatisx.lang.Strings;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * 聚合函数筛选
 *
 * @author wvkity
 * @created 2022/2/21
 * @since 1.0.0
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HavingFiltering {

    /**
     * Lambda属性转字符串属性
     *
     * @param property {@link Property}
     * @param <T>      实体类型
     * @return 字符串属性
     */
    protected static <T> String convert(final Property<T, ?> property) {
        return LambdaMetadataWeakCache.getProperty(property);
    }


    /**
     * 获取{@link Column}
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @return {@link Column}
     */
    protected static Column getColumn(final Criteria<?> criteria, final String property) {
        if (criteria == null) {
            return null;
        }
        return TableHelper.getColumnByProperty(criteria.getEntity(), property, criteria.isStrict(), true);
    }


    /**
     * 简单分组筛选条件
     *
     * @param query    {@link Query}
     * @param target   属性/字段
     * @param distinct 是否去重
     * @param value    值
     * @param symbol   {@link Symbol}
     * @param slot     {@link LogicSymbol}
     * @param aggType  {@link AggType}
     * @param isColumn 是否为字段名
     * @return {@link FunctionHaving}
     */
    protected static FunctionHaving havingAccept(final Query<?> query, final String target, final boolean distinct,
                                                 final Object value, final Symbol symbol, final LogicSymbol slot,
                                                 final AggType aggType, final boolean isColumn) {
        if (Strings.isNotWhitespace(target)) {
            String columnName = null;
            if (aggType == AggType.COUNT && (SqlSymbol.STAR.equals(target) || SqlSymbol.ZERO.equals(target)
                    || SqlSymbol.ONE.equals(target)) || isColumn) {
                columnName = target;
            } else {
                final Column column = getColumn(query, target);
                if (column != null) {
                    columnName = column.getColumn();
                }
            }
            if (columnName != null) {
                final AggFunction function;
                final Param param;
                if (symbol == Symbol.BETWEEN || symbol == Symbol.NOT_BETWEEN) {
                    final Object[] array = (Object[]) value;
                    param = BetweenParam.builder()
                            .symbol(symbol)
                            .slot(slot)
                            .begin(array[0])
                            .end(array[1])
                            .build();
                } else if (symbol == Symbol.IN || symbol == Symbol.NOT_IN) {
                    param = InParam.builder()
                            .symbol(symbol)
                            .slot(slot)
                            .values((Collection<?>) value)
                            .build();
                } else {
                    param = SimpleParam.builder()
                            .symbol(symbol)
                            .slot(slot)
                            .value(value)
                            .build();
                }
                switch (aggType) {
                    case COUNT:
                        function = new Count(query, columnName, null, distinct);
                        break;
                    case SUM:
                        function = new Sum(query, columnName, distinct);
                        break;
                    case AVG:
                        function = new Avg(query, columnName, distinct);
                        break;
                    case MIN:
                        function = new Min(query, columnName, distinct);
                        break;
                    default:
                        function = new Max(query, columnName, distinct);
                        break;
                }
                return new FunctionHaving(function, param);
            }
        } else {
            log.warn("If the field or entity attribute name of the grouping filter table is empty, " +
                    "it will be ignored automatically");
        }
        return null;
    }

    // region Equal methods 

    // region Count

    /**
     * 总数等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countEq(final Query<T> query, final Property<T, ?> property, final long value) {
        return countEq(query, property, value, null);
    }

    /**
     * 总数等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countEq(final Query<T> query, final Property<T, ?> property,
                                             final long value, final LogicSymbol slot) {
        return countEq(query, property, false, value, slot);
    }

    /**
     * 总数等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countEq(final Query<T> query, final Property<T, ?> property,
                                             final boolean distinct, final long value) {
        return countEq(query, property, distinct, value, null);
    }

    /**
     * 总数等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countEq(final Query<T> query, final Property<T, ?> property,
                                             final boolean distinct, final long value,
                                             final LogicSymbol slot) {
        return countEq(query, convert(property), distinct, value, slot);
    }

    /**
     * 总数等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countEq(final Query<?> query, final String property,
                                         final long value) {
        return countEq(query, property, value, null);
    }

    /**
     * 总数等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countEq(final Query<?> query, final String property,
                                         final long value, final LogicSymbol slot) {
        return countEq(query, property, false, value, slot);
    }

    /**
     * 总数等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countEq(final Query<?> query, final String property,
                                         final boolean distinct, final long value) {
        return countEq(query, property, distinct, value, null);
    }

    /**
     * 总数等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countEq(final Query<?> query, final String property,
                                         final boolean distinct,
                                         final long value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.EQ, slot, AggType.COUNT, false);
    }

    /**
     * 总数等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountEq(final Query<?> query, final String column, final long value) {
        return colCountEq(query, column, value, null);
    }

    /**
     * 总数等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountEq(final Query<?> query, final String column, final long value, final LogicSymbol slot) {
        return colCountEq(query, column, false, value, slot);
    }

    /**
     * 总数等于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountEq(final Query<?> query, final String column, final boolean distinct, final long value) {
        return colCountEq(query, column, distinct, value, null);
    }

    /**
     * 总数等于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountEq(final Query<?> query, final String column, final boolean distinct,
                                            final long value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.EQ, slot, AggType.COUNT, true);
    }

    // endregion

    // region Sum

    /**
     * 总和值等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumEq(final Query<T> query, final Property<T, ?> property, final Object value) {
        return sumEq(query, property, value, null);
    }

    /**
     * 总和值等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumEq(final Query<T> query, final Property<T, ?> property,
                                           final Object value, final LogicSymbol slot) {
        return sumEq(query, property, false, value, slot);
    }

    /**
     * 总和值等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumEq(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value) {
        return sumEq(query, property, distinct, value, null);
    }

    /**
     * 总和值等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumEq(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value,
                                           final LogicSymbol slot) {
        return sumEq(query, convert(property), distinct, value, slot);
    }

    /**
     * 总和值等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumEq(final Query<?> query, final String property,
                                       final Object value) {
        return sumEq(query, property, value, null);
    }

    /**
     * 总和值等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumEq(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return sumEq(query, property, false, value, slot);
    }

    /**
     * 总和值等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumEq(final Query<?> query, final String property,
                                       final boolean distinct, final Object value) {
        return sumEq(query, property, distinct, value, null);
    }

    /**
     * 总和值等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumEq(final Query<?> query, final String property,
                                       final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.EQ, slot, AggType.SUM, false);
    }

    /**
     * 总和值等于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumEq(final Query<?> query, final String column, final Object value) {
        return colSumEq(query, column, value, null);
    }

    /**
     * 总和值等于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumEq(final Query<?> query, final String column, final Object value,
                                          final LogicSymbol slot) {
        return colSumEq(query, column, false, value, slot);
    }

    /**
     * 总和值等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumEq(final Query<?> query, final String column,
                                          final boolean distinct, final Object value) {
        return colSumEq(query, column, distinct, value, null);
    }

    /**
     * 总和值等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumEq(final Query<?> query, final String column,
                                          final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.EQ, slot, AggType.SUM, true);
    }

    // endregion

    // region Avg

    /**
     * 平均值等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgEq(final Query<T> query, final Property<T, ?> property, final Object value) {
        return avgEq(query, property, value, null);
    }

    /**
     * 平均值等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgEq(final Query<T> query, final Property<T, ?> property,
                                           final Object value, final LogicSymbol slot) {
        return avgEq(query, property, false, value, slot);
    }

    /**
     * 平均值等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgEq(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value) {
        return avgEq(query, property, distinct, value, null);
    }

    /**
     * 平均值等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgEq(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value,
                                           final LogicSymbol slot) {
        return avgEq(query, convert(property), distinct, value, slot);
    }

    /**
     * 平均值等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgEq(final Query<?> query, final String property,
                                       final Object value) {
        return avgEq(query, property, value, null);
    }

    /**
     * 平均值等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgEq(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return avgEq(query, property, false, value, slot);
    }

    /**
     * 平均值等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgEq(final Query<?> query, final String property,
                                       final boolean distinct, final Object value) {
        return avgEq(query, property, distinct, value, null);
    }

    /**
     * 平均值等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgEq(final Query<?> query, final String property,
                                       final boolean distinct,
                                       final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.EQ, slot, AggType.AVG, false);
    }

    /**
     * 平均值等于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgEq(final Query<?> query, final String column, final Object value) {
        return colAvgEq(query, column, value, null);
    }

    /**
     * 平均值等于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgEq(final Query<?> query, final String column, final Object value,
                                          final LogicSymbol slot) {
        return colAvgEq(query, column, false, value, slot);
    }

    /**
     * 平均值等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgEq(final Query<?> query, final String column, final boolean distinct,
                                          final Object value) {
        return colAvgEq(query, column, distinct, value, null);
    }

    /**
     * 平均值等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgEq(final Query<?> query, final String column,
                                          final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.EQ, slot, AggType.AVG, true);
    }

    // endregion

    // region Min

    /**
     * 最小值等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving minEq(final Query<T> query, final Property<T, V> property, final V value) {
        return minEq(query, property, value, null);
    }

    /**
     * 最小值等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving minEq(final Query<T> query, final Property<T, V> property,
                                              final V value, final LogicSymbol slot) {
        return minEq(query, convert(property), value, slot);
    }

    /**
     * 最小值等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minEq(final Query<?> query, final String property,
                                       final Object value) {
        return minEq(query, property, value, null);
    }

    /**
     * 最小值等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minEq(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, false, value, Symbol.EQ, slot, AggType.MIN, false);
    }

    /**
     * 最小值等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinEq(final Query<?> query, final String column, final Object value) {
        return colMinEq(query, column, value, null);
    }

    /**
     * 最小值等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinEq(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, false, value, Symbol.EQ, slot, AggType.MIN, true);
    }

    // endregion

    // region Max

    /**
     * 最大值等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving maxEq(final Query<T> query, final Property<T, V> property, final V value) {
        return maxEq(query, property, value, null);
    }

    /**
     * 最大值等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving maxEq(final Query<T> query, final Property<T, V> property,
                                              final V value, final LogicSymbol slot) {
        return maxEq(query, convert(property), value, slot);
    }

    /**
     * 最大值等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxEq(final Query<?> query, final String property,
                                       final Object value) {
        return maxEq(query, property, value, null);
    }

    /**
     * 最大值等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxEq(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, false, value, Symbol.EQ, slot, AggType.MAX, false);
    }

    /**
     * 最大值等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxEq(final Query<?> query, final String column, final Object value) {
        return colMaxEq(query, column, value, null);
    }

    /**
     * 最大值等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxEq(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, false, value, Symbol.EQ, slot, AggType.MAX, true);
    }

    // endregion

    // endregion

    // region Not equal methods

    // region Count

    /**
     * 总数不等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countNe(final Query<T> query, final Property<T, ?> property, final long value) {
        return countNe(query, property, value, null);
    }

    /**
     * 总数不等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countNe(final Query<T> query, final Property<T, ?> property,
                                             final long value, final LogicSymbol slot) {
        return countNe(query, property, false, value, slot);
    }

    /**
     * 总数不等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countNe(final Query<T> query, final Property<T, ?> property,
                                             final boolean distinct, final long value) {
        return countNe(query, property, distinct, value, null);
    }

    /**
     * 总数不等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countNe(final Query<T> query, final Property<T, ?> property,
                                             final boolean distinct, final long value, final LogicSymbol slot) {
        return countNe(query, convert(property), distinct, value, slot);
    }

    /**
     * 总数不等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countNe(final Query<?> query, final String property,
                                         final long value) {
        return countNe(query, property, value, null);
    }

    /**
     * 总数不等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countNe(final Query<?> query, final String property,
                                         final long value, final LogicSymbol slot) {
        return countNe(query, property, false, value, slot);
    }

    /**
     * 总数不等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countNe(final Query<?> query, final String property,
                                         final boolean distinct, final long value) {
        return countNe(query, property, distinct, value, null);
    }

    /**
     * 总数不等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countNe(final Query<?> query, final String property,
                                         final boolean distinct, final long value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.NE, slot, AggType.COUNT, false);
    }

    /**
     * 总数不等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountNe(final Query<?> query, final String column, final long value) {
        return colCountNe(query, column, value, null);
    }

    /**
     * 总数不等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountNe(final Query<?> query, final String column,
                                            final long value, final LogicSymbol slot) {
        return colCountNe(query, column, false, value, slot);
    }

    /**
     * 总数不等于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountNe(final Query<?> query, final String column,
                                            final boolean distinct, final long value) {
        return colCountNe(query, column, distinct, value, null);
    }

    /**
     * 总数不等于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountNe(final Query<?> query, final String column,
                                            final boolean distinct, final long value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.NE, slot, AggType.COUNT, true);
    }

    // endregion

    // region Sum

    /**
     * 总和值不等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumNe(final Query<T> query, final Property<T, ?> property, final Object value) {
        return sumNe(query, property, value, null);
    }

    /**
     * 总和值不等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumNe(final Query<T> query, final Property<T, ?> property,
                                           final Object value, final LogicSymbol slot) {
        return sumNe(query, property, false, value, slot);
    }

    /**
     * 总和值不等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumNe(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value) {
        return sumNe(query, property, distinct, value, null);
    }

    /**
     * 总和值不等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumNe(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value, final LogicSymbol slot) {
        return sumNe(query, convert(property), distinct, value, slot);
    }

    /**
     * 总和值不等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumNe(final Query<?> query, final String property,
                                       final Object value) {
        return sumNe(query, property, value, null);
    }

    /**
     * 总和值不等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumNe(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return sumNe(query, property, false, value, slot);
    }

    /**
     * 总和值不等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumNe(final Query<?> query, final String property,
                                       final boolean distinct, final Object value) {
        return sumNe(query, property, distinct, value, null);
    }

    /**
     * 总和值不等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumNe(final Query<?> query, final String property,
                                       final boolean distinct,
                                       final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.NE, slot, AggType.SUM, false);
    }

    /**
     * 总和值不等于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumNe(final Query<?> query, final String column, final Object value) {
        return colSumNe(query, column, value, null);
    }

    /**
     * 总和值不等于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumNe(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return colSumNe(query, column, false, value, slot);
    }

    /**
     * 总和值不等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumNe(final Query<?> query, final String column,
                                          final boolean distinct, final Object value) {
        return colSumNe(query, column, distinct, value, null);
    }

    /**
     * 总和值不等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumNe(final Query<?> query, final String column,
                                          final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.NE, slot, AggType.SUM, true);
    }

    // endregion

    // region Avg

    /**
     * 平均值不等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgNe(final Query<T> query, final Property<T, ?> property, final Object value) {
        return avgNe(query, property, value, null);
    }

    /**
     * 平均值不等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgNe(final Query<T> query, final Property<T, ?> property,
                                           final Object value, final LogicSymbol slot) {
        return avgNe(query, property, false, value, slot);
    }

    /**
     * 平均值不等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgNe(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value) {
        return avgNe(query, property, distinct, value, null);
    }

    /**
     * 平均值不等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgNe(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value, final LogicSymbol slot) {
        return avgNe(query, convert(property), distinct, value, slot);
    }

    /**
     * 平均值不等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgNe(final Query<?> query, final String property,
                                       final Object value) {
        return avgNe(query, property, value, null);
    }

    /**
     * 平均值不等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgNe(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return avgNe(query, property, false, value, slot);
    }

    /**
     * 平均值不等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgNe(final Query<?> query, final String property,
                                       final boolean distinct, final Object value) {
        return avgNe(query, property, distinct, value, null);
    }

    /**
     * 平均值不等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgNe(final Query<?> query, final String property,
                                       final boolean distinct,
                                       final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.NE, slot, AggType.AVG, false);
    }

    /**
     * 平均值不等于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgNe(final Query<?> query, final String column, final Object value) {
        return colAvgNe(query, column, value, null);
    }

    /**
     * 平均值不等于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgNe(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return colAvgNe(query, column, false, value, slot);
    }

    /**
     * 平均值不等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgNe(final Query<?> query, final String column,
                                          final boolean distinct, final Object value) {
        return colAvgNe(query, column, distinct, value, null);
    }

    /**
     * 平均值不等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgNe(final Query<?> query, final String column,
                                          final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.NE, slot, AggType.AVG, true);
    }

    // endregion

    // region Min

    /**
     * 最小值不等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving minNe(final Query<T> query, final Property<T, V> property, final V value) {
        return minNe(query, property, value, null);
    }

    /**
     * 最小值不等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving minNe(final Query<T> query, final Property<T, V> property,
                                              final V value, final LogicSymbol slot) {
        return minNe(query, convert(property), value, slot);
    }

    /**
     * 最小值不等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minNe(final Query<?> query, final String property,
                                       final Object value) {
        return minNe(query, property, value, null);
    }

    /**
     * 最小值不等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minNe(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, false, value, Symbol.NE, slot, AggType.MIN, false);
    }

    /**
     * 最小值不等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinNe(final Query<?> query, final String column, final Object value) {
        return colMinNe(query, column, value, null);
    }

    /**
     * 最小值不等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinNe(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, false, value, Symbol.NE, slot, AggType.MIN, true);
    }

    // endregion

    // region Max

    /**
     * 最大值不等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving maxNe(final Query<T> query, final Property<T, V> property, final V value) {
        return maxNe(query, property, value, null);
    }

    /**
     * 最大值不等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving maxNe(final Query<T> query, final Property<T, V> property,
                                              final V value, final LogicSymbol slot) {
        return maxNe(query, convert(property), value, slot);
    }

    /**
     * 最大值不等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxNe(final Query<?> query, final String property,
                                       final Object value) {
        return maxNe(query, property, value, null);
    }

    /**
     * 最大值不等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxNe(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, false, value, Symbol.NE, slot, AggType.MAX, false);
    }


    /**
     * 最大值不等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxNe(final Query<?> query, final String column, final Object value) {
        return colMaxNe(query, column, value, null);
    }

    /**
     * 最大值不等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxNe(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, false, value, Symbol.NE, slot, AggType.MAX, true);
    }

    // endregion

    // endregion

    // region Greater than methods

    // region Count

    /**
     * 总数大于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countGt(final Query<T> query, final Property<T, ?> property, final long value) {
        return countGt(query, property, value, null);
    }

    /**
     * 总数大于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countGt(final Query<T> query, final Property<T, ?> property,
                                             final long value, final LogicSymbol slot) {
        return countGt(query, property, false, value, slot);
    }

    /**
     * 总数大于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countGt(final Query<T> query, final Property<T, ?> property,
                                             final boolean distinct, final long value) {
        return countGt(query, property, distinct, value, null);
    }

    /**
     * 总数大于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countGt(final Query<T> query, final Property<T, ?> property,
                                             final boolean distinct, final long value, final LogicSymbol slot) {
        return countGt(query, convert(property), distinct, value, slot);
    }

    /**
     * 总数大于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countGt(final Query<?> query, final String property,
                                         final long value) {
        return countGt(query, property, value, null);
    }

    /**
     * 总数大于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countGt(final Query<?> query, final String property,
                                         final long value, final LogicSymbol slot) {
        return countGt(query, property, false, value, slot);
    }

    /**
     * 总数大于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countGt(final Query<?> query, final String property,
                                         final boolean distinct, final long value) {
        return countGt(query, property, distinct, value, null);
    }

    /**
     * 总数大于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countGt(final Query<?> query, final String property,
                                         final boolean distinct,
                                         final long value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.GT, slot, AggType.COUNT, false);
    }

    /**
     * 总数大于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountGt(final Query<?> query, final String column, final long value) {
        return colCountGt(query, column, value, null);
    }

    /**
     * 总数大于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountGt(final Query<?> query, final String column,
                                            final long value, final LogicSymbol slot) {
        return colCountGt(query, column, false, value, slot);
    }

    /**
     * 总数大于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountGt(final Query<?> query, final String column,
                                            final boolean distinct, final long value) {
        return colCountGt(query, column, distinct, value, null);
    }

    /**
     * 总数大于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountGt(final Query<?> query, final String column,
                                            final boolean distinct, final long value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.GT, slot, AggType.COUNT, true);
    }

    // endregion

    // region Sum

    /**
     * 总和值大于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumGt(final Query<T> query, final Property<T, ?> property, final Object value) {
        return sumGt(query, property, value, null);
    }

    /**
     * 总和值大于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumGt(final Query<T> query, final Property<T, ?> property,
                                           final Object value, final LogicSymbol slot) {
        return sumGt(query, property, false, value, slot);
    }

    /**
     * 总和值大于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumGt(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value) {
        return sumGt(query, property, distinct, value, null);
    }

    /**
     * 总和值大于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumGt(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value, final LogicSymbol slot) {
        return sumGt(query, convert(property), distinct, value, slot);
    }

    /**
     * 总和值大于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumGt(final Query<?> query, final String property,
                                       final Object value) {
        return sumGt(query, property, value, null);
    }

    /**
     * 总和值大于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumGt(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return sumGt(query, property, false, value, slot);
    }

    /**
     * 总和值大于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumGt(final Query<?> query, final String property,
                                       final boolean distinct, final Object value) {
        return sumGt(query, property, distinct, value, null);
    }

    /**
     * 总和值大于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumGt(final Query<?> query, final String property,
                                       final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.GT, slot, AggType.SUM, false);
    }

    /**
     * 总和值大于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumGt(final Query<?> query, final String column, final Object value) {
        return colSumGt(query, column, value, null);
    }

    /**
     * 总和值大于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumGt(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return colSumGt(query, column, false, value, slot);
    }

    /**
     * 总和值大于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumGt(final Query<?> query, final String column,
                                          final boolean distinct, final Object value) {
        return colSumGt(query, column, distinct, value, null);
    }

    /**
     * 总和值大于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumGt(final Query<?> query, final String column,
                                          final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.GT, slot, AggType.SUM, true);
    }

    // endregion

    // region Avg

    /**
     * 平均值大于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgGt(final Query<T> query, final Property<T, ?> property, final Object value) {
        return avgGt(query, property, value, null);
    }

    /**
     * 平均值大于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgGt(final Query<T> query, final Property<T, ?> property,
                                           final Object value, final LogicSymbol slot) {
        return avgGt(query, property, false, value, slot);
    }

    /**
     * 平均值大于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgGt(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value) {
        return avgGt(query, property, distinct, value, null);
    }

    /**
     * 平均值大于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgGt(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value, final LogicSymbol slot) {
        return avgGt(query, convert(property), distinct, value, slot);
    }

    /**
     * 平均值大于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgGt(final Query<?> query, final String property,
                                       final Object value) {
        return avgGt(query, property, value, null);
    }

    /**
     * 平均值大于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgGt(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return avgGt(query, property, false, value, slot);
    }

    /**
     * 平均值大于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgGt(final Query<?> query, final String property,
                                       final boolean distinct, final Object value) {
        return avgGt(query, property, distinct, value, null);
    }

    /**
     * 平均值大于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgGt(final Query<?> query, final String property,
                                       final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.GT, slot, AggType.AVG, false);
    }

    /**
     * 平均值大于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgGt(final Query<?> query, final String column, final Object value) {
        return colAvgGt(query, column, value, null);
    }

    /**
     * 平均值大于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgGt(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return colAvgGt(query, column, false, value, slot);
    }

    /**
     * 平均值大于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgGt(final Query<?> query, final String column,
                                          final boolean distinct, final Object value) {
        return colAvgGt(query, column, distinct, value, null);
    }

    /**
     * 平均值大于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgGt(final Query<?> query, final String column,
                                          final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.GT, slot, AggType.AVG, true);
    }

    // endregion

    // region Min

    /**
     * 最小值大于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving minGt(final Query<T> query, final Property<T, V> property, final V value) {
        return minGt(query, property, value, null);
    }

    /**
     * 最小值大于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving minGt(final Query<T> query, final Property<T, V> property,
                                              final V value, final LogicSymbol slot) {
        return minGt(query, convert(property), value, slot);
    }

    /**
     * 最小值大于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minGt(final Query<?> query, final String property,
                                       final Object value) {
        return minGt(query, property, value, null);
    }

    /**
     * 最小值大于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minGt(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, false, value, Symbol.GT, slot, AggType.MIN, false);
    }

    /**
     * 最小值大于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinGt(final Query<?> query, final String column, final Object value) {
        return colMinGt(query, column, value, null);
    }

    /**
     * 最小值大于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinGt(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, false, value, Symbol.GT, slot, AggType.MIN, true);
    }

    // endregion

    // region Max

    /**
     * 最大值大于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving maxGt(final Query<T> query, final Property<T, V> property, final V value) {
        return maxGt(query, property, value, null);
    }

    /**
     * 最大值大于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving maxGt(final Query<T> query, final Property<T, V> property,
                                              final V value, final LogicSymbol slot) {
        return maxGt(query, convert(property), value, slot);
    }

    /**
     * 最大值大于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxGt(final Query<?> query, final String property,
                                       final Object value) {
        return maxGt(query, property, value, null);
    }

    /**
     * 最大值大于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxGt(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, false, value, Symbol.GT, slot, AggType.MAX, false);
    }

    /**
     * 最大值大于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxGt(final Query<?> query, final String column, final Object value) {
        return colMaxGt(query, column, value, null);
    }

    /**
     * 最大值大于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxGt(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, false, value, Symbol.GT, slot, AggType.MAX, true);
    }

    // endregion

    // endregion

    // region Greater than or equal to methods

    // region Count

    /**
     * 总数大于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countGe(final Query<T> query, final Property<T, ?> property, final long value) {
        return countGe(query, property, value, null);
    }

    /**
     * 总数大于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countGe(final Query<T> query, final Property<T, ?> property,
                                             final long value, final LogicSymbol slot) {
        return countGe(query, property, false, value, slot);
    }

    /**
     * 总数大于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countGe(final Query<T> query, final Property<T, ?> property,
                                             final boolean distinct, final long value) {
        return countGe(query, property, distinct, value, null);
    }

    /**
     * 总数大于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countGe(final Query<T> query, final Property<T, ?> property,
                                             final boolean distinct, final long value, final LogicSymbol slot) {
        return countGe(query, convert(property), distinct, value, slot);
    }

    /**
     * 总数大于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countGe(final Query<?> query, final String property,
                                         final long value) {
        return countGe(query, property, value, null);
    }

    /**
     * 总数大于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countGe(final Query<?> query, final String property,
                                         final long value, final LogicSymbol slot) {
        return countGe(query, property, false, value, slot);
    }

    /**
     * 总数大于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countGe(final Query<?> query, final String property,
                                         final boolean distinct, final long value) {
        return countGe(query, property, distinct, value, null);
    }

    /**
     * 总数大于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countGe(final Query<?> query, final String property,
                                         final boolean distinct, final long value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.GE, slot, AggType.COUNT, false);
    }

    /**
     * 总数大于等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountGe(final Query<?> query, final String column, final long value) {
        return colCountGe(query, column, value, null);
    }

    /**
     * 总数大于等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountGe(final Query<?> query, final String column,
                                            final long value, final LogicSymbol slot) {
        return colCountGe(query, column, false, value, slot);
    }

    /**
     * 总数大于等于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountGe(final Query<?> query, final String column,
                                            final boolean distinct, final long value) {
        return colCountGe(query, column, distinct, value, null);
    }

    /**
     * 总数大于等于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountGe(final Query<?> query, final String column,
                                            final boolean distinct, final long value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.GE, slot, AggType.COUNT, true);
    }

    // endregion

    // region Sum

    /**
     * 总和值大于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumGe(final Query<T> query, final Property<T, ?> property, final Object value) {
        return sumGe(query, property, value, null);
    }

    /**
     * 总和值大于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumGe(final Query<T> query, final Property<T, ?> property,
                                           final Object value, final LogicSymbol slot) {
        return sumGe(query, property, false, value, slot);
    }

    /**
     * 总和值大于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumGe(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value) {
        return sumGe(query, property, distinct, value, null);
    }

    /**
     * 总和值大于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumGe(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value, final LogicSymbol slot) {
        return sumGe(query, convert(property), distinct, value, slot);
    }

    /**
     * 总和值大于等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumGe(final Query<?> query, final String property,
                                       final Object value) {
        return sumGe(query, property, value, null);
    }

    /**
     * 总和值大于等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumGe(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return sumGe(query, property, false, value, slot);
    }

    /**
     * 总和值大于等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumGe(final Query<?> query, final String property,
                                       final boolean distinct, final Object value) {
        return sumGe(query, property, distinct, value, null);
    }

    /**
     * 总和值大于等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumGe(final Query<?> query, final String property,
                                       final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.GE, slot, AggType.SUM, false);
    }

    /**
     * 总和值大于等于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumGe(final Query<?> query, final String column, final Object value) {
        return colSumGe(query, column, value, null);
    }

    /**
     * 总和值大于等于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumGe(final Query<?> query, final String column, final Object value, final LogicSymbol slot) {
        return colSumGe(query, column, false, value, slot);
    }

    /**
     * 总和值大于等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumGe(final Query<?> query, final String column,
                                          final boolean distinct, final Object value) {
        return colSumGe(query, column, distinct, value, null);
    }

    /**
     * 总和值大于等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumGe(final Query<?> query, final String column,
                                          final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.GE, slot, AggType.SUM, true);
    }

    // endregion

    // region Avg

    /**
     * 平均值大于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgGe(final Query<T> query, final Property<T, ?> property, final Object value) {
        return avgGe(query, property, value, null);
    }

    /**
     * 平均值大于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgGe(final Query<T> query, final Property<T, ?> property,
                                           final Object value, final LogicSymbol slot) {
        return avgGe(query, property, false, value, slot);
    }

    /**
     * 平均值大于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgGe(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value) {
        return avgGe(query, property, distinct, value, null);
    }

    /**
     * 平均值大于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgGe(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value, final LogicSymbol slot) {
        return avgGe(query, convert(property), distinct, value, slot);
    }

    /**
     * 平均值大于等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgGe(final Query<?> query, final String property,
                                       final Object value) {
        return avgGe(query, property, value, null);
    }

    /**
     * 平均值大于等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgGe(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return avgGe(query, property, false, value, slot);
    }

    /**
     * 平均值大于等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgGe(final Query<?> query, final String property,
                                       final boolean distinct, final Object value) {
        return avgGe(query, property, distinct, value, null);
    }

    /**
     * 平均值大于等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgGe(final Query<?> query, final String property,
                                       final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.GE, slot, AggType.AVG, false);
    }

    /**
     * 平均值大于等于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgGe(final Query<?> query, final String column, final Object value) {
        return colAvgGe(query, column, value, null);
    }

    /**
     * 平均值大于等于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgGe(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return colAvgGe(query, column, false, value, slot);
    }

    /**
     * 平均值大于等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgGe(final Query<?> query, final String column,
                                          final boolean distinct, final Object value) {
        return colAvgGe(query, column, distinct, value, null);
    }

    /**
     * 平均值大于等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgGe(final Query<?> query, final String column,
                                          final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.GE, slot, AggType.AVG, true);
    }

    // endregion

    // region Min

    /**
     * 最小值大于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving minGe(final Query<T> query, final Property<T, V> property, final V value) {
        return minGe(query, property, value, null);
    }

    /**
     * 最小值大于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving minGe(final Query<T> query, final Property<T, V> property,
                                              final V value, final LogicSymbol slot) {
        return minGe(query, convert(property), value, slot);
    }

    /**
     * 最小值大于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minGe(final Query<?> query, final String property,
                                       final Object value) {
        return minGe(query, property, value, null);
    }

    /**
     * 最小值大于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minGe(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, false, value, Symbol.GE, slot, AggType.MIN, false);
    }

    /**
     * 最小值大于等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinGe(final Query<?> query, final String column, final Object value) {
        return colMinGe(query, column, value, null);
    }

    /**
     * 最小值大于等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinGe(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, false, value, Symbol.GE, slot, AggType.MIN, true);
    }

    // endregion

    // region Max

    /**
     * 最大值大于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving maxGe(final Query<T> query, final Property<T, V> property, final V value) {
        return maxGe(query, property, value, null);
    }

    /**
     * 最大值大于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving maxGe(final Query<T> query, final Property<T, V> property,
                                              final V value, final LogicSymbol slot) {
        return maxGe(query, convert(property), value, slot);
    }

    /**
     * 最大值大于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxGe(final Query<?> query, final String property,
                                       final Object value) {
        return maxGe(query, property, value, null);
    }

    /**
     * 最大值大于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxGe(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, false, value, Symbol.GE, slot, AggType.MAX, false);
    }

    /**
     * 最大值大于等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxGe(final Query<?> query, final String column, final Object value) {
        return colMaxGe(query, column, value, null);
    }

    /**
     * 最大值大于等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxGe(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, false, value, Symbol.GE, slot, AggType.MAX, true);
    }

    // endregion

    // endregion 

    // region Less than methods

    // region Count

    /**
     * 总数小于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countLt(final Query<T> query, final Property<T, ?> property, final long value) {
        return countLt(query, property, value, null);
    }

    /**
     * 总数小于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countLt(final Query<T> query, final Property<T, ?> property,
                                             final long value, final LogicSymbol slot) {
        return countLt(query, property, false, value, slot);
    }

    /**
     * 总数小于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countLt(final Query<T> query, final Property<T, ?> property,
                                             final boolean distinct, final long value) {
        return countLt(query, property, distinct, value, null);
    }

    /**
     * 总数小于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countLt(final Query<T> query, final Property<T, ?> property,
                                             final boolean distinct, final long value, final LogicSymbol slot) {
        return countLt(query, convert(property), distinct, value, slot);
    }

    /**
     * 总数小于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countLt(final Query<?> query, final String property,
                                         final long value) {
        return countLt(query, property, value, null);
    }

    /**
     * 总数小于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countLt(final Query<?> query, final String property,
                                         final long value, final LogicSymbol slot) {
        return countLt(query, property, false, value, slot);
    }

    /**
     * 总数小于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countLt(final Query<?> query, final String property,
                                         final boolean distinct, final long value) {
        return countLt(query, property, distinct, value, null);
    }

    /**
     * 总数小于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countLt(final Query<?> query, final String property,
                                         final boolean distinct, final long value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.LT, slot, AggType.COUNT, false);
    }

    /**
     * 总数小于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountLt(final Query<?> query, final String column, final long value) {
        return colCountLt(query, column, value, null);
    }

    /**
     * 总数小于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountLt(final Query<?> query, final String column, final long value,
                                            final LogicSymbol slot) {
        return colCountLt(query, column, false, value, slot);
    }

    /**
     * 总数小于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountLt(final Query<?> query, final String column,
                                            final boolean distinct, final long value) {
        return colCountLt(query, column, distinct, value, null);
    }

    /**
     * 总数小于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountLt(final Query<?> query, final String column,
                                            final boolean distinct, final long value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.LT, slot, AggType.COUNT, true);
    }

    // endregion

    // region Sum

    /**
     * 总和值小于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumLt(final Query<T> query, final Property<T, ?> property, final Object value) {
        return sumLt(query, property, value, null);
    }

    /**
     * 总和值小于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumLt(final Query<T> query, final Property<T, ?> property,
                                           final Object value, final LogicSymbol slot) {
        return sumLt(query, property, false, value, slot);
    }

    /**
     * 总和值小于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumLt(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value) {
        return sumLt(query, property, distinct, value, null);
    }

    /**
     * 总和值小于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumLt(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value, final LogicSymbol slot) {
        return sumLt(query, convert(property), distinct, value, slot);
    }

    /**
     * 总和值小于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumLt(final Query<?> query, final String property,
                                       final Object value) {
        return sumLt(query, property, value, null);
    }

    /**
     * 总和值小于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumLt(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return sumLt(query, property, false, value, slot);
    }

    /**
     * 总和值小于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumLt(final Query<?> query, final String property,
                                       final boolean distinct, final Object value) {
        return sumLt(query, property, distinct, value, null);
    }

    /**
     * 总和值小于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumLt(final Query<?> query, final String property,
                                       final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.LT, slot, AggType.SUM, false);
    }

    /**
     * 总和值小于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumLt(final Query<?> query, final String column, final Object value) {
        return colSumLt(query, column, value, null);
    }

    /**
     * 总和值小于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumLt(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return colSumLt(query, column, false, value, slot);
    }

    /**
     * 总和值小于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumLt(final Query<?> query, final String column,
                                          final boolean distinct, final Object value) {
        return colSumLt(query, column, distinct, value, null);
    }

    /**
     * 总和值小于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumLt(final Query<?> query, final String column,
                                          final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.LT, slot, AggType.SUM, true);
    }

    // endregion

    // region Avg

    /**
     * 平均值小于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgLt(final Query<T> query, final Property<T, ?> property, final Object value) {
        return avgLt(query, property, value, null);
    }

    /**
     * 平均值小于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgLt(final Query<T> query, final Property<T, ?> property,
                                           final Object value, final LogicSymbol slot) {
        return avgLt(query, property, false, value, slot);
    }

    /**
     * 平均值小于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgLt(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value) {
        return avgLt(query, property, distinct, value, null);
    }

    /**
     * 平均值小于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgLt(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value, final LogicSymbol slot) {
        return avgLt(query, convert(property), distinct, value, slot);
    }

    /**
     * 平均值小于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgLt(final Query<?> query, final String property,
                                       final Object value) {
        return avgLt(query, property, value, null);
    }

    /**
     * 平均值小于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgLt(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return avgLt(query, property, false, value, slot);
    }

    /**
     * 平均值小于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgLt(final Query<?> query, final String property,
                                       final boolean distinct, final Object value) {
        return avgLt(query, property, distinct, value, null);
    }

    /**
     * 平均值小于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgLt(final Query<?> query, final String property,
                                       final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.LT, slot, AggType.AVG, false);
    }

    /**
     * 平均值小于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgLt(final Query<?> query, final String column, final Object value) {
        return colAvgLt(query, column, value, null);
    }

    /**
     * 平均值小于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgLt(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return colAvgLt(query, column, false, value, slot);
    }

    /**
     * 平均值小于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgLt(final Query<?> query, final String column,
                                          final boolean distinct, final Object value) {
        return colAvgLt(query, column, distinct, value, null);
    }

    /**
     * 平均值小于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgLt(final Query<?> query, final String column,
                                          final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.LT, slot, AggType.AVG, true);
    }

    // endregion

    // region Min

    /**
     * 最小值小于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving minLt(final Query<T> query, final Property<T, V> property, final V value) {
        return minLt(query, property, value, null);
    }

    /**
     * 最小值小于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving minLt(final Query<T> query, final Property<T, V> property,
                                              final V value, final LogicSymbol slot) {
        return minLt(query, convert(property), value, slot);
    }

    /**
     * 最小值小于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minLt(final Query<?> query, final String property,
                                       final Object value) {
        return minLt(query, property, value, null);
    }

    /**
     * 最小值小于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minLt(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, false, value, Symbol.LT, slot, AggType.MIN, false);
    }

    /**
     * 最小值小于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinLt(final Query<?> query, final String column, final Object value) {
        return colMinLt(query, column, value, null);
    }

    /**
     * 最小值小于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinLt(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, false, value, Symbol.LT, slot, AggType.MIN, true);
    }

    // endregion

    // region Max

    /**
     * 最大值小于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving maxLt(final Query<T> query, final Property<T, V> property, final V value) {
        return maxLt(query, property, value, null);
    }

    /**
     * 最大值小于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving maxLt(final Query<T> query, final Property<T, V> property,
                                              final V value, final LogicSymbol slot) {
        return maxLt(query, convert(property), value, slot);
    }

    /**
     * 最大值小于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxLt(final Query<?> query, final String property,
                                       final Object value) {
        return maxLt(query, property, value, null);
    }

    /**
     * 最大值小于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxLt(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, false, value, Symbol.LT, slot, AggType.MAX, false);
    }

    /**
     * 最大值小于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxLt(final Query<?> query, final String column, final Object value) {
        return colMaxLt(query, column, value, null);
    }

    /**
     * 最大值小于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxLt(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, false, value, Symbol.LT, slot, AggType.MAX, true);
    }

    // endregion

    // endregion

    // region Less than or equal to methods

    // region Count

    /**
     * 总数小于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countLe(final Query<T> query, final Property<T, ?> property, final long value) {
        return countLe(query, property, value, null);
    }

    /**
     * 总数小于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countLe(final Query<T> query, final Property<T, ?> property,
                                             final long value, final LogicSymbol slot) {
        return countLe(query, property, false, value, slot);
    }

    /**
     * 总数小于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countLe(final Query<T> query, final Property<T, ?> property,
                                             final boolean distinct, final long value) {
        return countLe(query, property, distinct, value, null);
    }

    /**
     * 总数小于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countLe(final Query<T> query, final Property<T, ?> property,
                                             final boolean distinct, final long value, final LogicSymbol slot) {
        return countLe(query, convert(property), distinct, value, slot);
    }

    /**
     * 总数小于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countLe(final Query<?> query, final String property,
                                         final long value) {
        return countLe(query, property, value, null);
    }

    /**
     * 总数小于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countLe(final Query<?> query, final String property,
                                         final long value, final LogicSymbol slot) {
        return countLe(query, property, false, value, slot);
    }

    /**
     * 总数小于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countLe(final Query<?> query, final String property,
                                         final boolean distinct, final long value) {
        return countLe(query, property, distinct, value, null);
    }

    /**
     * 总数小于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countLe(final Query<?> query, final String property,
                                         final boolean distinct, final long value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.LE, slot, AggType.COUNT, false);
    }

    /**
     * 总数小于等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountLe(final Query<?> query, final String column, final long value) {
        return colCountLe(query, column, value, null);
    }

    /**
     * 总数小于等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountLe(final Query<?> query, final String column,
                                            final long value, final LogicSymbol slot) {
        return colCountLe(query, column, false, value, slot);
    }

    /**
     * 总数小于等于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountLe(final Query<?> query, final String column,
                                            final boolean distinct, final long value) {
        return colCountLe(query, column, distinct, value, null);
    }

    /**
     * 总数小于等于
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountLe(final Query<?> query, final String column,
                                            final boolean distinct, final long value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.LE, slot, AggType.COUNT, true);
    }

    // endregion

    // region Sum

    /**
     * 总和值小于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumLe(final Query<T> query, final Property<T, ?> property, final Object value) {
        return sumLe(query, property, value, null);
    }

    /**
     * 总和值小于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumLe(final Query<T> query, final Property<T, ?> property,
                                           final Object value, final LogicSymbol slot) {
        return sumLe(query, property, false, value, slot);
    }

    /**
     * 总和值小于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumLe(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value) {
        return sumLe(query, property, distinct, value, null);
    }

    /**
     * 总和值小于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumLe(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value, final LogicSymbol slot) {
        return sumLe(query, convert(property), distinct, value, slot);
    }

    /**
     * 总和值小于等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumLe(final Query<?> query, final String property,
                                       final Object value) {
        return sumLe(query, property, value, null);
    }

    /**
     * 总和值小于等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumLe(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return sumLe(query, property, false, value, slot);
    }

    /**
     * 总和值小于等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumLe(final Query<?> query, final String property,
                                       final boolean distinct, final Object value) {
        return sumLe(query, property, distinct, value, null);
    }

    /**
     * 总和值小于等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumLe(final Query<?> query, final String property,
                                       final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.LE, slot, AggType.SUM, false);
    }

    /**
     * 总和值小于等于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumLe(final Query<?> query, final String column, final Object value) {
        return colSumLe(query, column, value, null);
    }

    /**
     * 总和值小于等于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumLe(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return colSumLe(query, column, false, value, slot);
    }

    /**
     * 总和值小于等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumLe(final Query<?> query, final String column,
                                          final boolean distinct, final Object value) {
        return colSumLe(query, column, distinct, value, null);
    }

    /**
     * 总和值小于等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumLe(final Query<?> query, final String column,
                                          final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.LE, slot, AggType.SUM, true);
    }

    // endregion

    // region Avg

    /**
     * 平均值小于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgLe(final Query<T> query, final Property<T, ?> property, final Object value) {
        return avgLe(query, property, value, null);
    }

    /**
     * 平均值小于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgLe(final Query<T> query, final Property<T, ?> property,
                                           final Object value, final LogicSymbol slot) {
        return avgLe(query, property, false, value, slot);
    }

    /**
     * 平均值小于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgLe(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value) {
        return avgLe(query, property, distinct, value, null);
    }

    /**
     * 平均值小于等于
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgLe(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Object value, final LogicSymbol slot) {
        return avgLe(query, convert(property), distinct, value, slot);
    }

    /**
     * 平均值小于等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgLe(final Query<?> query, final String property,
                                       final Object value) {
        return avgLe(query, property, value, null);
    }

    /**
     * 平均值小于等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgLe(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return avgLe(query, property, false, value, slot);
    }

    /**
     * 平均值小于等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgLe(final Query<?> query, final String property,
                                       final boolean distinct, final Object value) {
        return avgLe(query, property, distinct, value, null);
    }

    /**
     * 平均值小于等于
     *
     * @param query    {@link Query}
     * @param property 属性名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgLe(final Query<?> query, final String property,
                                       final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, value, Symbol.LE, slot, AggType.AVG, false);
    }

    /**
     * 平均值小于等于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgLe(final Query<?> query, final String column, final Object value) {
        return colAvgLe(query, column, value, null);
    }

    /**
     * 平均值小于等于
     *
     * @param query  {@link Query}
     * @param column 字段名名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgLe(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return colAvgLe(query, column, false, value, slot);
    }

    /**
     * 平均值小于等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgLe(final Query<?> query, final String column,
                                          final boolean distinct, final Object value) {
        return colAvgLe(query, column, distinct, value, null);
    }

    /**
     * 平均值小于等于
     *
     * @param column   字段名名
     * @param distinct 是否去重
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgLe(final Query<?> query, final String column,
                                          final boolean distinct, final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, value, Symbol.LE, slot, AggType.AVG, true);
    }

    // endregion

    // region Min

    /**
     * 最小值小于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving minLe(final Query<T> query, final Property<T, V> property, final V value) {
        return minLe(query, property, value, null);
    }

    /**
     * 最小值小于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving minLe(final Query<T> query, final Property<T, V> property,
                                              final V value, final LogicSymbol slot) {
        return minLe(query, convert(property), value, slot);
    }

    /**
     * 最小值小于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minLe(final Query<?> query, final String property,
                                       final Object value) {
        return minLe(query, property, value, null);
    }

    /**
     * 最小值小于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minLe(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, false, value, Symbol.LE, slot, AggType.MIN, false);
    }

    /**
     * 最小值小于等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinLe(final Query<?> query, final String column, final Object value) {
        return colMinLe(query, column, value, null);
    }

    /**
     * 最小值小于等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinLe(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, false, value, Symbol.LE, slot, AggType.MIN, true);
    }

    // endregion

    // region Max

    /**
     * 最大值小于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving maxLe(final Query<T> query, final Property<T, V> property, final V value) {
        return maxLe(query, property, value, null);
    }

    /**
     * 最大值小于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving maxLe(final Query<T> query, final Property<T, V> property,
                                              final V value, final LogicSymbol slot) {
        return maxLe(query, convert(property), value, slot);
    }

    /**
     * 最大值小于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxLe(final Query<?> query, final String property,
                                       final Object value) {
        return maxLe(query, property, value, null);
    }

    /**
     * 最大值小于等于
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxLe(final Query<?> query, final String property,
                                       final Object value, final LogicSymbol slot) {
        return havingAccept(query, property, false, value, Symbol.LE, slot, AggType.MAX, false);
    }

    /**
     * 最大值小于等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxLe(final Query<?> query, final String column, final Object value) {
        return colMaxLe(query, column, value, null);
    }

    /**
     * 最大值小于等于
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxLe(final Query<?> query, final String column,
                                          final Object value, final LogicSymbol slot) {
        return havingAccept(query, column, false, value, Symbol.LE, slot, AggType.MAX, true);
    }

    // endregion

    // endregion

    // region Between methods

    // region Count

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countBetween(final Query<T> query, final Property<T, ?> property,
                                                  final long begin, final long end) {
        return countBetween(query, property, begin, end, null);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countBetween(final Query<T> query, final Property<T, ?> property,
                                                  final long begin, final long end,
                                                  final LogicSymbol slot) {
        return countBetween(query, property, false, begin, end, slot);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countBetween(final Query<T> query, final Property<T, ?> property,
                                                  final boolean distinct, final long begin, final long end) {
        return countBetween(query, property, distinct, begin, end, null);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countBetween(final Query<T> query, final Property<T, ?> property,
                                                  final boolean distinct, final long begin, final long end,
                                                  final LogicSymbol slot) {
        return countBetween(query, convert(property), distinct, begin, end, slot);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countBetween(final Query<?> query, final String property, final long begin,
                                              final long end) {
        return countBetween(query, property, begin, end, null);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countBetween(final Query<?> query, final String property, final long begin,
                                              final long end, final LogicSymbol slot) {
        return countBetween(query, property, false, begin, end, slot);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countBetween(final Query<?> query, final String property, final boolean distinct
            , final long begin, final long end) {
        return countBetween(query, property, distinct, begin, end, null);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countBetween(final Query<?> query, final String property, final boolean distinct,
                                              final long begin, final long end, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, new Object[]{begin, end}, Symbol.BETWEEN, slot,
                AggType.COUNT, false);
    }

    /**
     * 总数范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountBetween(final Query<?> query, final String column,
                                                 final long begin, final long end) {
        return colCountBetween(query, column, begin, end, null);
    }

    /**
     * 总数范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountBetween(final Query<?> query, final String column, final long begin,
                                                 final long end, final LogicSymbol slot) {
        return colCountBetween(query, column, false, begin, end, slot);
    }

    /**
     * 总数范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountBetween(final Query<?> query, final String column, final boolean distinct,
                                                 final long begin, final long end) {
        return colCountBetween(query, column, distinct, begin, end, null);
    }

    /**
     * 总数范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountBetween(final Query<?> query, final String column, final boolean distinct,
                                                 final long begin, final long end, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, new Object[]{begin, end}, Symbol.BETWEEN, slot, AggType.COUNT,
                true);
    }

    // endregion

    // region Sum

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumBetween(final Query<T> query, final Property<T, ?> property,
                                                final Object begin, final Object end) {
        return sumBetween(query, property, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumBetween(final Query<T> query, final Property<T, ?> property,
                                                final Object begin, final Object end,
                                                final LogicSymbol slot) {
        return sumBetween(query, property, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumBetween(final Query<T> query, final Property<T, ?> property,
                                                final boolean distinct, final Object begin, final Object end) {
        return sumBetween(query, property, distinct, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumBetween(final Query<T> query, final Property<T, ?> property,
                                                final boolean distinct, final Object begin, final Object end,
                                                final LogicSymbol slot) {
        return sumBetween(query, convert(property), distinct, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumBetween(final Query<?> query, final String property, final Object begin,
                                            final Object end) {
        return sumBetween(query, property, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumBetween(final Query<?> query, final String property, final Object begin,
                                            final Object end, final LogicSymbol slot) {
        return sumBetween(query, property, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumBetween(final Query<?> query, final String property, final boolean distinct,
                                            final Object begin, final Object end) {
        return sumBetween(query, property, distinct, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumBetween(final Query<?> query, final String property, final boolean distinct,
                                            final Object begin, final Object end, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, new Object[]{begin, end}, Symbol.BETWEEN, slot,
                AggType.SUM, false);
    }

    /**
     * 总和范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumBetween(final Query<?> query, final String column,
                                               final Object begin, final Object end) {
        return colSumBetween(query, column, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumBetween(final Query<?> query, final String column, final Object begin,
                                               final Object end, final LogicSymbol slot) {
        return colSumBetween(query, column, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumBetween(final Query<?> query, final String column, final boolean distinct,
                                               final Object begin, final Object end) {
        return colSumBetween(query, column, distinct, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumBetween(final Query<?> query, final String column, final boolean distinct,
                                               final Object begin, final Object end, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, new Object[]{begin, end}, Symbol.BETWEEN, slot, AggType.SUM,
                true);
    }

    // endregion

    // region Avg

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgBetween(final Query<T> query, final Property<T, ?> property,
                                                final Object begin, final Object end) {
        return avgBetween(query, property, begin, end, null);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgBetween(final Query<T> query, final Property<T, ?> property,
                                                final Object begin, final Object end,
                                                final LogicSymbol slot) {
        return avgBetween(query, property, false, begin, end, slot);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgBetween(final Query<T> query, final Property<T, ?> property,
                                                final boolean distinct, final Object begin, final Object end) {
        return avgBetween(query, property, distinct, begin, end, null);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgBetween(final Query<T> query, final Property<T, ?> property,
                                                final boolean distinct, final Object begin, final Object end,
                                                final LogicSymbol slot) {
        return avgBetween(query, convert(property), distinct, begin, end, slot);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgBetween(final Query<?> query, final String property, final Object begin,
                                            final Object end) {
        return avgBetween(query, property, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgBetween(final Query<?> query, final String property, final Object begin,
                                            final Object end, final LogicSymbol slot) {
        return avgBetween(query, property, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgBetween(final Query<?> query, final String property, final boolean distinct,
                                            final Object begin, final Object end) {
        return avgBetween(query, property, distinct, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgBetween(final Query<?> query, final String property, final boolean distinct,
                                            final Object begin, final Object end, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, new Object[]{begin, end}, Symbol.BETWEEN, slot,
                AggType.AVG, false);
    }

    /**
     * 平均值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgBetween(final Query<?> query, final String column,
                                               final Object begin, final Object end) {
        return colAvgBetween(query, column, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgBetween(final Query<?> query, final String column, final Object begin,
                                               final Object end, final LogicSymbol slot) {
        return colAvgBetween(query, column, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgBetween(final Query<?> query, final String column, final boolean distinct,
                                               final Object begin, final Object end) {
        return colAvgBetween(query, column, distinct, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgBetween(final Query<?> query, final String column, final boolean distinct,
                                               final Object begin, final Object end, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, new Object[]{begin, end}, Symbol.BETWEEN, slot, AggType.AVG,
                true);
    }

    // endregion

    // region Min

    /**
     * 最小值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving minBetween(final Query<T> query, final Property<T, ?> property,
                                                final Object begin, final Object end) {
        return minBetween(query, property, begin, end, null);
    }

    /**
     * 最小值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving minBetween(final Query<T> query, final Property<T, ?> property,
                                                final Object begin, final Object end, final LogicSymbol slot) {
        return minBetween(query, convert(property), begin, end, slot);
    }

    /**
     * 最小值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minBetween(final Query<?> query, final String property, final Object begin,
                                            final Object end) {
        return minBetween(query, property, begin, end, null);
    }

    /**
     * 最小值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minBetween(final Query<?> query, final String property, final Object begin,
                                            final Object end, final LogicSymbol slot) {
        return havingAccept(query, property, false, new Object[]{begin, end}, Symbol.BETWEEN, slot,
                AggType.MIN, false);
    }

    /**
     * 最小值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinBetween(final Query<?> query, final String column,
                                               final Object begin, final Object end) {
        return colMinBetween(query, column, begin, end, null);
    }

    /**
     * 最小值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinBetween(final Query<?> query, final String column,
                                               final Object begin, final Object end, final LogicSymbol slot) {
        return havingAccept(query, column, false, new Object[]{begin, end}, Symbol.BETWEEN, slot, AggType.MIN,
                true);
    }

    // endregion

    // region Max

    /**
     * 最大值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving maxBetween(final Query<T> query, final Property<T, ?> property,
                                                final Object begin, final Object end) {
        return maxBetween(query, property, begin, end, null);
    }

    /**
     * 最大值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving maxBetween(final Query<T> query, final Property<T, ?> property,
                                                final Object begin, final Object end, final LogicSymbol slot) {
        return maxBetween(query, convert(property), begin, end, slot);
    }

    /**
     * 最大值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxBetween(final Query<?> query, final String property, final Object begin,
                                            final Object end) {
        return maxBetween(query, property, begin, end, null);
    }

    /**
     * 最大值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxBetween(final Query<?> query, final String property, final Object begin,
                                            final Object end, final LogicSymbol slot) {
        return havingAccept(query, property, false, new Object[]{begin, end}, Symbol.BETWEEN, slot,
                AggType.MAX, false);
    }

    /**
     * 最大值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxBetween(final Query<?> query, final String column,
                                               final Object begin, final Object end) {
        return colMaxBetween(query, column, begin, end, null);
    }

    /**
     * 最大值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxBetween(final Query<?> query, final String column,
                                               final Object begin, final Object end, final LogicSymbol slot) {
        return havingAccept(query, column, false, new Object[]{begin, end}, Symbol.BETWEEN, slot, AggType.MAX,
                true);
    }

    // endregion

    // endregion

    // region Not between methods

    // region Count

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countNotBetween(final Query<T> query, final Property<T, ?> property,
                                                     final long begin, final long end) {
        return countNotBetween(query, property, begin, end, null);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countNotBetween(final Query<T> query, final Property<T, ?> property,
                                                     final long begin, final long end, final LogicSymbol slot) {
        return countNotBetween(query, property, false, begin, end, slot);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countNotBetween(final Query<T> query, final Property<T, ?> property, final boolean distinct,
                                                     final long begin, final long end) {
        return countNotBetween(query, property, distinct, begin, end, null);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countNotBetween(final Query<T> query, final Property<T, ?> property,
                                                     final boolean distinct, final long begin,
                                                     final long end, final LogicSymbol slot) {
        return countNotBetween(query, convert(property), distinct, begin, end, slot);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countNotBetween(final Query<?> query, final String property, final long begin,
                                                 final long end) {
        return countNotBetween(query, property, begin, end, null);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countNotBetween(final Query<?> query, final String property, final long begin,
                                                 final long end, final LogicSymbol slot) {
        return countNotBetween(query, property, false, begin, end, slot);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countNotBetween(final Query<?> query, final String property,
                                                 final boolean distinct, final long begin, final long end) {
        return countNotBetween(query, property, distinct, begin, end, null);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countNotBetween(final Query<?> query, final String property,
                                                 final boolean distinct, final long begin, final long end,
                                                 final LogicSymbol slot) {
        return havingAccept(query, property, distinct, new Object[]{begin, end}, Symbol.NOT_BETWEEN, slot,
                AggType.COUNT, false);
    }

    /**
     * 总数范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountNotBetween(final Query<?> query, final String column,
                                                    final long begin, final long end) {
        return colCountNotBetween(query, column, begin, end, null);
    }

    /**
     * 总数范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountNotBetween(final Query<?> query, final String column,
                                                    final long begin, final long end, final LogicSymbol slot) {
        return colCountNotBetween(query, column, false, begin, end, slot);
    }

    /**
     * 总数范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountNotBetween(final Query<?> query, final String column,
                                                    final boolean distinct, final long begin, final long end) {
        return colCountNotBetween(query, column, distinct, begin, end, null);
    }

    /**
     * 总数范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountNotBetween(final Query<?> query, final String column, final boolean distinct,
                                                    final long begin, final long end, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, new Object[]{begin, end}, Symbol.NOT_BETWEEN, slot, AggType.COUNT,
                true);
    }

    // endregion

    // region Sum

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumNotBetween(final Query<T> query, final Property<T, ?> property,
                                                   final Object begin, final Object end) {
        return sumNotBetween(query, property, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumNotBetween(final Query<T> query, final Property<T, ?> property,
                                                   final Object begin, final Object end, final LogicSymbol slot) {
        return sumNotBetween(query, property, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumNotBetween(final Query<T> query, final Property<T, ?> property,
                                                   final boolean distinct, final Object begin, final Object end) {
        return sumNotBetween(query, property, distinct, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumNotBetween(final Query<T> query, final Property<T, ?> property,
                                                   final boolean distinct, final Object begin,
                                                   final Object end, final LogicSymbol slot) {
        return sumNotBetween(query, convert(property), distinct, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumNotBetween(final Query<?> query, final String property, final Object begin,
                                               final Object end) {
        return sumNotBetween(query, property, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumNotBetween(final Query<?> query, final String property, final Object begin,
                                               final Object end, final LogicSymbol slot) {
        return sumNotBetween(query, property, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumNotBetween(final Query<?> query, final String property,
                                               final boolean distinct, final Object begin, final Object end) {
        return sumNotBetween(query, property, distinct, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumNotBetween(final Query<?> query, final String property, final boolean distinct,
                                               final Object begin, final Object end, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, new Object[]{begin, end}, Symbol.NOT_BETWEEN, slot,
                AggType.SUM, false);
    }

    /**
     * 总和范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumNotBetween(final Query<?> query, final String column,
                                                  final Object begin, final Object end) {
        return colSumNotBetween(query, column, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumNotBetween(final Query<?> query, final String column, final Object begin,
                                                  final Object end, final LogicSymbol slot) {
        return colSumNotBetween(query, column, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumNotBetween(final Query<?> query, final String column, final boolean distinct,
                                                  final Object begin, final Object end) {
        return colSumNotBetween(query, column, distinct, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumNotBetween(final Query<?> query, final String column, final boolean distinct,
                                                  final Object begin, final Object end, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, new Object[]{begin, end}, Symbol.NOT_BETWEEN, slot, AggType.SUM,
                true);
    }

    // endregion

    // region Avg

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgNotBetween(final Query<T> query, final Property<T, ?> property,
                                                   final Object begin, final Object end) {
        return avgNotBetween(query, property, begin, end, null);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgNotBetween(final Query<T> query, final Property<T, ?> property,
                                                   final Object begin, final Object end, final LogicSymbol slot) {
        return avgNotBetween(query, property, false, begin, end, slot);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgNotBetween(final Query<T> query, final Property<T, ?> property,
                                                   final boolean distinct, final Object begin, final Object end) {
        return avgNotBetween(query, property, distinct, begin, end, null);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgNotBetween(final Query<T> query, final Property<T, ?> property,
                                                   final boolean distinct, final Object begin, final Object end,
                                                   final LogicSymbol slot) {
        return avgNotBetween(query, convert(property), distinct, begin, end, slot);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgNotBetween(final Query<?> query, final String property, final Object begin,
                                               final Object end) {
        return avgNotBetween(query, property, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgNotBetween(final Query<?> query, final String property, final Object begin,
                                               final Object end, final LogicSymbol slot) {
        return avgNotBetween(query, property, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgNotBetween(final Query<?> query, final String property,
                                               final boolean distinct, final Object begin, final Object end) {
        return avgNotBetween(query, property, distinct, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgNotBetween(final Query<?> query, final String property, final boolean distinct,
                                               final Object begin, final Object end, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, new Object[]{begin, end}, Symbol.NOT_BETWEEN, slot,
                AggType.AVG, false);
    }

    /**
     * 平均值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgNotBetween(final Query<?> query, final String column,
                                                  final Object begin, final Object end) {
        return colAvgNotBetween(query, column, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgNotBetween(final Query<?> query, final String column, final Object begin,
                                                  final Object end, final LogicSymbol slot) {
        return colAvgNotBetween(query, column, false, begin, end, slot);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgNotBetween(final Query<?> query, final String column, final boolean distinct,
                                                  final Object begin, final Object end) {
        return colAvgNotBetween(query, column, distinct, begin, end, null);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgNotBetween(final Query<?> query, final String column, final boolean distinct,
                                                  final Object begin, final Object end, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, new Object[]{begin, end}, Symbol.NOT_BETWEEN, slot, AggType.AVG,
                true);
    }

    // endregion

    // region Min

    /**
     * 最小值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving minNotBetween(final Query<T> query, final Property<T, ?> property,
                                                   final Object begin, final Object end) {
        return minNotBetween(query, property, begin, end, null);
    }

    /**
     * 最小值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving minNotBetween(final Query<T> query, final Property<T, ?> property,
                                                   final Object begin, final Object end, final LogicSymbol slot) {
        return minNotBetween(query, convert(property), begin, end, slot);
    }

    /**
     * 最小值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minNotBetween(final Query<?> query, final String property, final Object begin,
                                               final Object end) {
        return minNotBetween(query, property, begin, end, null);
    }

    /**
     * 最小值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minNotBetween(final Query<?> query, final String property, final Object begin,
                                               final Object end, final LogicSymbol slot) {
        return havingAccept(query, property, false, new Object[]{begin, end}, Symbol.NOT_BETWEEN, slot,
                AggType.MIN, false);
    }

    /**
     * 最小值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinNotBetween(final Query<?> query, final String column,
                                                  final Object begin, final Object end) {
        return colMinNotBetween(query, column, begin, end, null);
    }

    /**
     * 最小值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinNotBetween(final Query<?> query, final String column, final Object begin,
                                                  final Object end, final LogicSymbol slot) {
        return havingAccept(query, column, false, new Object[]{begin, end}, Symbol.NOT_BETWEEN, slot, AggType.MIN,
                true);
    }

    // endregion

    // region Max

    /**
     * 最大值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving maxNotBetween(final Query<T> query, final Property<T, ?> property,
                                                   final Object begin, final Object end) {
        return maxNotBetween(query, property, begin, end, null);
    }

    /**
     * 最大值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving maxNotBetween(final Query<T> query, final Property<T, ?> property,
                                                   final Object begin, final Object end, final LogicSymbol slot) {
        return maxNotBetween(query, convert(property), begin, end, slot);
    }

    /**
     * 最大值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxNotBetween(final Query<?> query, final String property, final Object begin,
                                               final Object end) {
        return maxNotBetween(query, property, begin, end, null);
    }

    /**
     * 最大值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxNotBetween(final Query<?> query, final String property, final Object begin,
                                               final Object end, final LogicSymbol slot) {
        return havingAccept(query, property, false, new Object[]{begin, end}, Symbol.NOT_BETWEEN, slot,
                AggType.MAX, false);
    }

    /**
     * 最大值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxNotBetween(final Query<?> query, final String column,
                                                  final Object begin, final Object end) {
        return colMaxNotBetween(query, column, begin, end, null);
    }

    /**
     * 最大值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxNotBetween(final Query<?> query, final String column,
                                                  final Object begin, final Object end, final LogicSymbol slot) {
        return havingAccept(query, column, false, new Object[]{begin, end}, Symbol.NOT_BETWEEN, slot, AggType.MAX,
                true);
    }

    // endregion

    // endregion

    // region In methods

    // region Count

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countIn(final Query<T> query, final Property<T, ?> property,
                                             final Collection<?> values) {
        return countIn(query, property, values, null);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countIn(final Query<T> query, final Property<T, ?> property,
                                             final Collection<?> values, final LogicSymbol slot) {
        return countIn(query, property, false, values, slot);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countIn(final Query<T> query, final Property<T, ?> property,
                                             final boolean distinct, final Collection<?> values) {
        return countIn(query, property, distinct, values, null);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countIn(final Query<T> query, final Property<T, ?> property,
                                             final boolean distinct, final Collection<?> values,
                                             final LogicSymbol slot) {
        return countIn(query, convert(property), distinct, values, slot);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countIn(final Query<?> query, final String property, final Collection<?> values) {
        return countIn(query, property, values, null);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countIn(final Query<?> query, final String property, final Collection<?> values,
                                         final LogicSymbol slot) {
        return countIn(query, property, false, values, slot);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countIn(final Query<?> query, final String property, final boolean distinct,
                                         final Collection<?> values) {
        return countIn(query, property, distinct, values, null);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countIn(final Query<?> query, final String property, final boolean distinct,
                                         final Collection<?> values, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, values, Symbol.IN, slot, AggType.COUNT, false);
    }

    /**
     * 总数范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountIn(final Query<?> query, final String column, final Collection<?> values) {
        return colCountIn(query, column, values, null);
    }

    /**
     * 总数范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountIn(final Query<?> query, final String column,
                                            final Collection<?> values, final LogicSymbol slot) {
        return colCountIn(query, column, false, values, slot);
    }

    /**
     * 总数范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountIn(final Query<?> query, final String column,
                                            final boolean distinct, final Collection<?> values) {
        return colCountIn(query, column, distinct, values, null);
    }

    /**
     * 总数范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountIn(final Query<?> query, final String column,
                                            final boolean distinct, final Collection<?> values,
                                            final LogicSymbol slot) {
        return havingAccept(query, column, distinct, values, Symbol.IN, slot, AggType.COUNT, true);
    }

    // endregion

    // region Sum

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumIn(final Query<T> query, final Property<T, ?> property,
                                           final Collection<?> values) {
        return sumIn(query, property, values, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumIn(final Query<T> query, final Property<T, ?> property,
                                           final Collection<?> values, final LogicSymbol slot) {
        return sumIn(query, property, false, values, slot);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumIn(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Collection<?> values) {
        return sumIn(query, property, distinct, values, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumIn(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Collection<?> values,
                                           final LogicSymbol slot) {
        return sumIn(query, convert(property), distinct, values, slot);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumIn(final Query<?> query, final String property, final Collection<?> values) {
        return sumIn(query, property, values, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumIn(final Query<?> query, final String property, final Collection<?> values,
                                       final LogicSymbol slot) {
        return sumIn(query, property, false, values, slot);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumIn(final Query<?> query, final String property, final boolean distinct,
                                       final Collection<?> values) {
        return sumIn(query, property, distinct, values, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumIn(final Query<?> query, final String property, final boolean distinct,
                                       final Collection<?> values, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, values, Symbol.IN, slot, AggType.SUM, false);
    }

    /**
     * 总和范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumIn(final Query<?> query, final String column, final Collection<?> values) {
        return colSumIn(query, column, values, null);
    }

    /**
     * 总和范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumIn(final Query<?> query, final String column, final Collection<?> values,
                                          final LogicSymbol slot) {
        return colSumIn(query, column, false, values, slot);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumIn(final Query<?> query, final String column, final boolean distinct,
                                          final Collection<?> values) {
        return colSumIn(query, column, distinct, values, null);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumIn(final Query<?> query, final String column, final boolean distinct,
                                          final Collection<?> values, final LogicSymbol slot) {
        return havingAccept(query, column, distinct, values, Symbol.IN, slot, AggType.SUM, true);
    }

    // endregion

    // region Avg

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgIn(final Query<T> query, final Property<T, ?> property,
                                           final Collection<?> values) {
        return avgIn(query, property, values, null);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgIn(final Query<T> query, final Property<T, ?> property,
                                           final Collection<?> values, final LogicSymbol slot) {
        return avgIn(query, property, false, values, slot);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgIn(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Collection<?> values) {
        return avgIn(query, property, distinct, values, null);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgIn(final Query<T> query, final Property<T, ?> property,
                                           final boolean distinct, final Collection<?> values,
                                           final LogicSymbol slot) {
        return avgIn(query, convert(property), distinct, values, slot);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgIn(final Query<?> query, final String property, final Collection<?> values) {
        return avgIn(query, property, values, null);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgIn(final Query<?> query, final String property, final Collection<?> values,
                                       final LogicSymbol slot) {
        return avgIn(query, property, false, values, slot);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgIn(final Query<?> query, final String property, final boolean distinct,
                                       final Collection<?> values) {
        return avgIn(query, property, distinct, values, null);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgIn(final Query<?> query, final String property, final boolean distinct,
                                       final Collection<?> values, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, values, Symbol.IN, slot, AggType.AVG, false);
    }

    /**
     * 平均值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgIn(final Query<?> query, final String column, final Collection<?> values) {
        return colAvgIn(query, column, values, null);
    }

    /**
     * 平均值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgIn(final Query<?> query, final String column,
                                          final Collection<?> values, final LogicSymbol slot) {
        return colAvgIn(query, column, false, values, slot);
    }

    /**
     * 平均值范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgIn(final Query<?> query, final String column,
                                          final boolean distinct, final Collection<?> values) {
        return colAvgIn(query, column, distinct, values, null);
    }

    /**
     * 平均值范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgIn(final Query<?> query, final String column,
                                          final boolean distinct, final Collection<?> values,
                                          final LogicSymbol slot) {
        return havingAccept(query, column, distinct, values, Symbol.IN, slot, AggType.AVG, true);
    }

    // endregion

    // region Min

    /**
     * 最小值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving minIn(final Query<T> query, final Property<T, V> property,
                                              final Collection<V> values) {
        return minIn(query, property, values, null);
    }

    /**
     * 最小值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving minIn(final Query<T> query, final Property<T, V> property,
                                              final Collection<V> values, final LogicSymbol slot) {
        return minIn(query, convert(property), values, slot);
    }

    /**
     * 最小值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minIn(final Query<?> query, final String property, final Collection<?> values) {
        return minIn(query, property, values, null);
    }

    /**
     * 最小值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minIn(final Query<?> query, final String property, final Collection<?> values,
                                       final LogicSymbol slot) {
        return havingAccept(query, property, false, values, Symbol.IN, slot, AggType.MIN, false);
    }

    /**
     * 最小值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinIn(final Query<?> query, final String column, final Collection<?> values) {
        return colMinIn(query, column, values, null);
    }

    /**
     * 最小值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinIn(final Query<?> query, final String column,
                                          final Collection<?> values, final LogicSymbol slot) {
        return havingAccept(query, column, false, values, Symbol.IN, slot, AggType.MIN, true);
    }

    // endregion

    // region Max

    /**
     * 最大值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving maxIn(final Query<T> query, final Property<T, V> property,
                                              final Collection<V> values) {
        return maxIn(query, property, values, null);
    }

    /**
     * 最大值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving maxIn(final Query<T> query, final Property<T, V> property,
                                              final Collection<V> values, final LogicSymbol slot) {
        return maxIn(query, convert(property), values, slot);
    }

    /**
     * 最大值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxIn(final Query<?> query, final String property, final Collection<?> values) {
        return maxIn(query, property, values, null);
    }

    /**
     * 最大值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxIn(final Query<?> query, final String property, final Collection<?> values,
                                       final LogicSymbol slot) {
        return havingAccept(query, property, false, values, Symbol.IN, slot, AggType.MAX, false);
    }

    /**
     * 最大值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxIn(final Query<?> query, final String column, final Collection<?> values) {
        return colMaxIn(query, column, values, null);
    }

    /**
     * 最大值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxIn(final Query<?> query, final String column,
                                          final Collection<?> values, final LogicSymbol slot) {
        return havingAccept(query, column, false, values, Symbol.IN, slot, AggType.MAX, true);
    }

    // endregion

    // endregion

    // region Not in methods

    // region Count

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countNotIn(final Query<T> query, final Property<T, ?> property,
                                                final Collection<?> values) {
        return countNotIn(query, property, values, null);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countNotIn(final Query<T> query, final Property<T, ?> property,
                                                final Collection<?> values, final LogicSymbol slot) {
        return countNotIn(query, property, false, values, slot);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countNotIn(final Query<T> query, final Property<T, ?> property,
                                                final boolean distinct, final Collection<?> values) {
        return countNotIn(query, property, distinct, values, null);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving countNotIn(final Query<T> query, final Property<T, ?> property,
                                                final boolean distinct, final Collection<?> values,
                                                final LogicSymbol slot) {
        return countNotIn(query, convert(property), distinct, values, slot);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countNotIn(final Query<?> query, final String property, final Collection<?> values) {
        return countNotIn(query, property, values, null);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countNotIn(final Query<?> query, final String property,
                                            final Collection<?> values, final LogicSymbol slot) {
        return countNotIn(query, property, false, values, slot);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countNotIn(final Query<?> query, final String property, final boolean distinct,
                                            final Collection<?> values) {
        return countNotIn(query, property, distinct, values, null);
    }

    /**
     * 总数范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving countNotIn(final Query<?> query, final String property, final boolean distinct,
                                            final Collection<?> values, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, values, Symbol.NOT_IN, slot, AggType.COUNT, false);
    }

    /**
     * 总数范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountNotIn(final Query<?> query, final String column, final Collection<?> values) {
        return colCountNotIn(query, column, values, null);
    }

    /**
     * 总数范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountNotIn(final Query<?> query, final String column,
                                               final Collection<?> values, final LogicSymbol slot) {
        return colCountNotIn(query, column, false, values, slot);
    }

    /**
     * 总数范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountNotIn(final Query<?> query, final String column,
                                               final boolean distinct, final Collection<?> values) {
        return colCountNotIn(query, column, distinct, values, null);
    }

    /**
     * 总数范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colCountNotIn(final Query<?> query, final String column,
                                               final boolean distinct, final Collection<?> values,
                                               final LogicSymbol slot) {
        return havingAccept(query, column, distinct, values, Symbol.NOT_IN, slot, AggType.COUNT, true);
    }

    // endregion

    // region Sum

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumNotIn(final Query<T> query, final Property<T, ?> property,
                                              final Collection<?> values) {
        return sumNotIn(query, property, values, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumNotIn(final Query<T> query, final Property<T, ?> property,
                                              final Collection<?> values, final LogicSymbol slot) {
        return sumNotIn(query, property, false, values, slot);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumNotIn(final Query<T> query, final Property<T, ?> property,
                                              final boolean distinct, final Collection<?> values) {
        return sumNotIn(query, property, distinct, values, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving sumNotIn(final Query<T> query, final Property<T, ?> property,
                                              final boolean distinct, final Collection<?> values,
                                              final LogicSymbol slot) {
        return sumNotIn(query, convert(property), distinct, values, slot);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumNotIn(final Query<?> query, final String property, final Collection<?> values) {
        return sumNotIn(query, property, values, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumNotIn(final Query<?> query, final String property, final Collection<?> values
            , final LogicSymbol slot) {
        return sumNotIn(query, property, false, values, slot);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumNotIn(final Query<?> query, final String property, final boolean distinct,
                                          final Collection<?> values) {
        return sumNotIn(query, property, distinct, values, null);
    }

    /**
     * 总和范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving sumNotIn(final Query<?> query, final String property, final boolean distinct,
                                          final Collection<?> values, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, values, Symbol.NOT_IN, slot, AggType.SUM, false);
    }

    /**
     * 总和范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumNotIn(final Query<?> query, final String column, final Collection<?> values) {
        return colSumNotIn(query, column, values, null);
    }

    /**
     * 总和范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumNotIn(final Query<?> query, final String column,
                                             final Collection<?> values, final LogicSymbol slot) {
        return colSumNotIn(query, column, false, values, slot);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumNotIn(final Query<?> query, final String column,
                                             final boolean distinct, final Collection<?> values) {
        return colSumNotIn(query, column, distinct, values, null);
    }

    /**
     * 总和范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colSumNotIn(final Query<?> query, final String column,
                                             final boolean distinct, final Collection<?> values,
                                             final LogicSymbol slot) {
        return havingAccept(query, column, distinct, values, Symbol.NOT_IN, slot, AggType.SUM, true);
    }

    // endregion

    // region Avg

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgNotIn(final Query<T> query, final Property<T, ?> property,
                                              final Collection<?> values) {
        return avgNotIn(query, property, values, null);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgNotIn(final Query<T> query, final Property<T, ?> property,
                                              final Collection<?> values, final LogicSymbol slot) {
        return avgNotIn(query, property, false, values, slot);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgNotIn(final Query<T> query, final Property<T, ?> property,
                                              final boolean distinct, final Collection<?> values) {
        return avgNotIn(query, property, distinct, values, null);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link FunctionHaving}
     */
    public static <T> FunctionHaving avgNotIn(final Query<T> query, final Property<T, ?> property,
                                              final boolean distinct, final Collection<?> values,
                                              final LogicSymbol slot) {
        return avgNotIn(query, convert(property), distinct, values, slot);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgNotIn(final Query<?> query, final String property, final Collection<?> values) {
        return avgNotIn(query, property, values, null);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgNotIn(final Query<?> query, final String property,
                                          final Collection<?> values, final LogicSymbol slot) {
        return avgNotIn(query, property, false, values, slot);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgNotIn(final Query<?> query, final String property, final boolean distinct,
                                          final Collection<?> values) {
        return avgNotIn(query, property, distinct, values, null);
    }

    /**
     * 平均值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving avgNotIn(final Query<?> query, final String property, final boolean distinct,
                                          final Collection<?> values, final LogicSymbol slot) {
        return havingAccept(query, property, distinct, values, Symbol.NOT_IN, slot, AggType.AVG, false);
    }

    /**
     * 平均值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgNotIn(final Query<?> query, final String column, final Collection<?> values) {
        return colAvgNotIn(query, column, values, null);
    }

    /**
     * 平均值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgNotIn(final Query<?> query, final String column,
                                             final Collection<?> values, final LogicSymbol slot) {
        return colAvgNotIn(query, column, false, values, slot);
    }

    /**
     * 平均值范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgNotIn(final Query<?> query, final String column,
                                             final boolean distinct, final Collection<?> values) {
        return colAvgNotIn(query, column, distinct, values, null);
    }

    /**
     * 平均值范围
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colAvgNotIn(final Query<?> query, final String column,
                                             final boolean distinct, final Collection<?> values,
                                             final LogicSymbol slot) {
        return havingAccept(query, column, distinct, values, Symbol.NOT_IN, slot, AggType.AVG, true);
    }

    // endregion

    // region Min

    /**
     * 最小值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving minNotIn(final Query<T> query, final Property<T, V> property,
                                                 final Collection<V> values) {
        return minNotIn(query, property, values, null);
    }

    /**
     * 最小值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving minNotIn(final Query<T> query, final Property<T, V> property,
                                                 final Collection<V> values, final LogicSymbol slot) {
        return minNotIn(query, convert(property), values, slot);
    }

    /**
     * 最小值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minNotIn(final Query<?> query, final String property, final Collection<?> values) {
        return minNotIn(query, property, values, null);
    }

    /**
     * 最小值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving minNotIn(final Query<?> query, final String property, final Collection<?> values,
                                          final LogicSymbol slot) {
        return havingAccept(query, property, false, values, Symbol.NOT_IN, slot, AggType.MIN, false);
    }

    /**
     * 最小值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinNotIn(final Query<?> query, final String column, final Collection<?> values) {
        return colMinNotIn(query, column, values, null);
    }

    /**
     * 最小值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMinNotIn(final Query<?> query, final String column,
                                             final Collection<?> values, final LogicSymbol slot) {
        return havingAccept(query, column, false, values, Symbol.NOT_IN, slot, AggType.MIN, true);
    }

    // endregion

    // region Max

    /**
     * 最大值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving maxNotIn(final Query<T> query, final Property<T, V> property,
                                                 final Collection<V> values) {
        return maxNotIn(query, property, values, null);
    }

    /**
     * 最大值范围
     *
     * @param query    {@link Query}
     * @param property {@link Property}
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link FunctionHaving}
     */
    public static <T, V> FunctionHaving maxNotIn(final Query<T> query, final Property<T, V> property,
                                                 final Collection<V> values, final LogicSymbol slot) {
        return maxNotIn(query, convert(property), values, slot);
    }

    /**
     * 最大值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxNotIn(final Query<?> query, final String property,
                                          final Collection<?> values) {
        return maxNotIn(query, property, values, null);
    }

    /**
     * 最大值范围
     *
     * @param query    {@link Query}
     * @param property 属性
     * @param values   值
     * @param slot     {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving maxNotIn(final Query<?> query, final String property, final Collection<?> values,
                                          final LogicSymbol slot) {
        return havingAccept(query, property, false, values, Symbol.NOT_IN, slot, AggType.MAX, false);
    }

    /**
     * 最大值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxNotIn(final Query<?> query, final String column, final Collection<?> values) {
        return colMaxNotIn(query, column, values, null);
    }

    /**
     * 最大值范围
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @param values 值
     * @param slot   {@link LogicSymbol}
     * @return {@link FunctionHaving}
     */
    public static FunctionHaving colMaxNotIn(final Query<?> query, final String column,
                                             final Collection<?> values, final LogicSymbol slot) {
        return havingAccept(query, column, false, values, Symbol.NOT_IN, slot, AggType.MAX, true);
    }

    // endregion

    // endregion
}
