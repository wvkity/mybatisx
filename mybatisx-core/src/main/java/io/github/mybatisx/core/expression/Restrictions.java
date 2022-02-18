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
package io.github.mybatisx.core.expression;

import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.base.constant.MatchMode;
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.base.helper.TableHelper;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.core.param.BetweenParam;
import io.github.mybatisx.core.param.InParam;
import io.github.mybatisx.core.param.LikeParam;
import io.github.mybatisx.core.param.NullParam;
import io.github.mybatisx.core.param.SimpleParam;
import io.github.mybatisx.core.property.LambdaMetadataWeakCache;
import io.github.mybatisx.core.property.Property;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.matcher.Matcher;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 条件表达式工具
 *
 * @author wvkity
 * @created 2022/1/12
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restrictions {

    /**
     * 模式
     */
    public enum Mode {
        /**
         * 属性
         */
        PROPERTY,
        /**
         * 字段
         */
        COLUMN
    }

    /**
     * 准备
     *
     * @param value   值
     * @param matcher 匹配器
     * @param <V>     值类型
     * @return boolean
     */
    protected static <V> boolean early(final V value, final Matcher<V> matcher) {
        return matcher == null || matcher.matches(value);
    }

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
     * 构建{@link SimpleExpression}
     *
     * @param criteria {@link Criteria}
     * @param alias    表别名
     * @param target   属性名/字段名
     * @param value    值
     * @param matcher  匹配器
     * @param mode     {@link Mode}
     * @param symbol   {@link Symbol}
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    protected static <V> SimpleExpression simpleExpression(final Criteria<?> criteria, final String alias,
                                                           final String target, final V value,
                                                           final Matcher<V> matcher, final Mode mode,
                                                           final Symbol symbol, final LogicSymbol slot) {
        if (early(value, matcher)) {
            if (mode == Mode.PROPERTY) {
                final Column column = getColumn(criteria, target);
                if (column != null) {
                    return SimpleExpression.builder().column(column.getColumn())
                            .criteria(criteria)
                            .param(SimpleParam.builder()
                                    .symbol(symbol)
                                    .slot(slot)
                                    .value(value)
                                    .typeHandler(column.getTypeHandler())
                                    .jdbcType(column.getJdbcType())
                                    .javaType(column.getDescriptor().getJavaType())
                                    .spliceJavaType(column.isSpliceJavaType()).build())
                            .build();
                }
            } else {
                if (Strings.isNotWhitespace(target)) {
                    return SimpleExpression.builder()
                            .column(target)
                            .criteria(criteria)
                            .alias(alias)
                            .param(SimpleParam.builder()
                                    .symbol(symbol)
                                    .slot(slot)
                                    .value(value)
                                    .build())
                            .build();
                }
            }
        }
        return null;
    }

    /**
     * 构建{@link LikeExpression}
     *
     * @param criteria   {@link Criteria}
     * @param alias      表别名
     * @param target     属性名/字段名
     * @param value      值
     * @param matchMode  匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param mode       模式
     * @param symbol     {@link Symbol}
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    protected static LikeExpression likeExpression(final Criteria<?> criteria, final String alias,
                                                   final String target, final String value, final MatchMode matchMode,
                                                   final Character escape, final boolean ignoreCase,
                                                   final Mode mode, final Symbol symbol, final LogicSymbol slot) {
        if (mode == Mode.PROPERTY) {
            final Column column = getColumn(criteria, target);
            if (column != null) {
                return LikeExpression.builder()
                        .column(column.getColumn())
                        .criteria(criteria)
                        .param(LikeParam.builder()
                                .symbol(symbol)
                                .slot(slot)
                                .value(value)
                                .matchMode(matchMode)
                                .escape(escape)
                                .ignoreCase(ignoreCase)
                                .typeHandler(column.getTypeHandler())
                                .jdbcType(column.getJdbcType())
                                .javaType(column.getDescriptor().getJavaType())
                                .spliceJavaType(column.isSpliceJavaType()).build())
                        .build();
            }
        } else if (Strings.isNotWhitespace(target)) {
            return LikeExpression.builder()
                    .column(target)
                    .criteria(criteria)
                    .alias(alias)
                    .param(LikeParam.builder()
                            .symbol(symbol)
                            .slot(slot)
                            .value(value)
                            .matchMode(matchMode)
                            .escape(escape)
                            .ignoreCase(ignoreCase)
                            .build())
                    .build();
        }
        return null;
    }

    /**
     * 构建{@link BetweenExpression}
     *
     * @param criteria {@link Criteria}
     * @param alias    表别名
     * @param target   属性名/字段名
     * @param begin    开始值
     * @param end      结束值
     * @param mode     {@link Mode}
     * @param symbol   {@link Symbol}
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@link BetweenExpression}
     */
    protected static <V> BetweenExpression betweenExpression(final Criteria<?> criteria, final String alias,
                                                             final String target, final V begin, final V end,
                                                             final Mode mode, final Symbol symbol,
                                                             final LogicSymbol slot) {
        if (mode == Mode.PROPERTY) {
            final Column column = getColumn(criteria, target);
            if (column != null) {
                return BetweenExpression.builder()
                        .column(column.getColumn())
                        .criteria(criteria)
                        .param(BetweenParam.builder()
                                .symbol(symbol)
                                .slot(slot)
                                .begin(begin)
                                .end(end)
                                .typeHandler(column.getTypeHandler())
                                .jdbcType(column.getJdbcType())
                                .javaType(column.getDescriptor().getJavaType())
                                .spliceJavaType(column.isSpliceJavaType()).build())
                        .build();
            }
        } else if (Strings.isNotWhitespace(target)) {
            return BetweenExpression.builder()
                    .column(target)
                    .criteria(criteria)
                    .alias(alias)
                    .param(BetweenParam.builder()
                            .symbol(symbol)
                            .slot(slot)
                            .begin(begin)
                            .end(end)
                            .build())
                    .build();
        }
        return null;
    }

    /**
     * 构建{@link InExpression}
     *
     * @param criteria {@link Criteria}
     * @param alias    表别名
     * @param target   属性名/字段名
     * @param values   值列表
     * @param mode     {@link Mode}
     * @param symbol   {@link Symbol}
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    protected static <V> InExpression inExpression(final Criteria<?> criteria, final String alias,
                                                   final String target, final Collection<V> values,
                                                   final Mode mode, final Symbol symbol, final LogicSymbol slot) {
        if (mode == Mode.PROPERTY) {
            final Column column = getColumn(criteria, target);
            if (column != null) {
                return InExpression.builder().column(column.getColumn())
                        .criteria(criteria)
                        .param(InParam.builder()
                                .symbol(symbol)
                                .slot(slot)
                                .values(values)
                                .typeHandler(column.getTypeHandler())
                                .jdbcType(column.getJdbcType())
                                .javaType(column.getDescriptor().getJavaType())
                                .spliceJavaType(column.isSpliceJavaType()).build())
                        .build();
            }
        } else if (Strings.isNotWhitespace(target)) {
            return InExpression.builder()
                    .column(target)
                    .criteria(criteria)
                    .alias(alias)
                    .param(InParam.builder()
                            .symbol(symbol)
                            .slot(slot)
                            .values(values)
                            .build())
                    .build();
        }
        return null;
    }

    /**
     * 构建{@link NullExpression}
     *
     * @param criteria {@link Criteria}
     * @param alias    表别名
     * @param target   属性名/字段名
     * @param mode     模式
     * @param symbol   {@link Symbol}
     * @param slot     {@link LogicSymbol}
     * @return {@link NullExpression}
     */
    protected static NullExpression nullExpression(final Criteria<?> criteria, final String alias,
                                                   final String target, final Mode mode, final Symbol symbol,
                                                   final LogicSymbol slot) {
        if (mode == Mode.PROPERTY) {
            final Column column = getColumn(criteria, target);
            if (column != null) {
                return NullExpression.builder()
                        .column(column.getColumn())
                        .criteria(criteria)
                        .param(NullParam.builder()
                                .symbol(symbol)
                                .slot(slot)
                                .build())
                        .build();
            }
        } else if (Strings.isNotWhitespace(target)) {
            return NullExpression.builder()
                    .column(target)
                    .criteria(criteria)
                    .alias(alias)
                    .param(NullParam.builder()
                            .symbol(symbol)
                            .slot(slot)
                            .build())
                    .build();
        }
        return null;
    }

    // region Equal expression methods

    /**
     * 等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression eq(final Criteria<T> criteria, final Property<T, V> property, final V value) {
        return eq(criteria, property, value, (LogicSymbol) null);
    }

    /**
     * 等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression eq(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final LogicSymbol slot) {
        return eq(criteria, property, value, null, slot);
    }

    /**
     * 等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression eq(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher) {
        return eq(criteria, property, value, matcher, null);
    }

    /**
     * 等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression eq(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return eq(criteria, convert(property), value, matcher, slot);
    }

    /**
     * 等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression eq(final Criteria<T> criteria, final String property, final V value) {
        return eq(criteria, property, value, (LogicSymbol) null);
    }

    /**
     * 等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression eq(final Criteria<T> criteria, final String property, final V value,
                                             final LogicSymbol slot) {
        return eq(criteria, property, value, null, slot);
    }

    /**
     * 等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression eq(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher) {
        return eq(criteria, property, value, matcher, null);
    }

    /**
     * 等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression eq(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(criteria, null, property, value, matcher, Mode.PROPERTY, Symbol.EQ, slot);
    }

    /**
     * 等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <V> SimpleExpression colEq(final Criteria<?> criteria, final String column, final V value) {
        return colEq(criteria, column, value, (LogicSymbol) null);
    }

    /**
     * 等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <V> SimpleExpression colEq(final Criteria<?> criteria, final String column, final V value,
                                             final LogicSymbol slot) {
        return colEq(criteria, column, value, null, slot);
    }

    /**
     * 等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <V> SimpleExpression colEq(final Criteria<?> criteria, final String column, final V value,
                                             final Matcher<V> matcher) {
        return colEq(criteria, column, value, matcher, null);
    }

    /**
     * 等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <V> SimpleExpression colEq(final Criteria<?> criteria, final String column, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(criteria, null, column, value, matcher, Mode.COLUMN, Symbol.EQ, slot);
    }

    /**
     * 等于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param <V>    值类型
     * @return {@link SimpleExpression}
     */
    public static <V> SimpleExpression colEq(final String alias, final String column, final V value) {
        return colEq(alias, column, value, null, null);
    }

    /**
     * 等于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@link SimpleExpression}
     */
    public static <V> SimpleExpression colEq(final String alias, final String column, final V value,
                                             final LogicSymbol slot) {
        return colEq(alias, column, value, null, slot);
    }

    /**
     * 等于表达式
     *
     * @param alias   表别名
     * @param column  字段名
     * @param value   值
     * @param matcher {@link Matcher}
     * @param <V>     值类型
     * @return {@link SimpleExpression}
     */
    public static <V> SimpleExpression colEq(final String alias, final String column, final V value,
                                             final Matcher<V> matcher) {
        return colEq(alias, column, value, matcher, null);
    }

    /**
     * 等于表达式
     *
     * @param alias   表别名
     * @param column  字段名
     * @param value   值
     * @param matcher {@link Matcher}
     * @param slot    {@link LogicSymbol}
     * @param <V>     值类型
     * @return {@link SimpleExpression}
     */
    public static <V> SimpleExpression colEq(final String alias, final String column, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(null, alias, column, value, matcher, Mode.COLUMN, Symbol.EQ, slot);
    }

    // endregion

    // region Not equal expression methods

    /**
     * 不等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression ne(final Criteria<T> criteria, final Property<T, V> property, final V value) {
        return ne(criteria, property, value, (LogicSymbol) null);
    }

    /**
     * 不等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression ne(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final LogicSymbol slot) {
        return ne(criteria, property, value, null, slot);
    }

    /**
     * 不等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression ne(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher) {
        return ne(criteria, property, value, matcher, null);
    }

    /**
     * 不等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression ne(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return ne(criteria, convert(property), value, matcher, slot);
    }

    /**
     * 不等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression ne(final Criteria<T> criteria, final String property, final V value) {
        return ne(criteria, property, value, (LogicSymbol) null);
    }

    /**
     * 不等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression ne(final Criteria<T> criteria, final String property, final V value,
                                             final LogicSymbol slot) {
        return ne(criteria, property, value, null, slot);
    }

    /**
     * 不等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression ne(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher) {
        return ne(criteria, property, value, matcher, null);
    }

    /**
     * 不等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression ne(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(criteria, null, property, value, matcher, Mode.PROPERTY, Symbol.NE, slot);
    }

    /**
     * 不等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colNe(final Criteria<T> criteria, final String column, final V value) {
        return colNe(criteria, column, value, (LogicSymbol) null);
    }

    /**
     * 不等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colNe(final Criteria<T> criteria, final String column, final V value,
                                                final LogicSymbol slot) {
        return colNe(criteria, column, value, null, slot);
    }

    /**
     * 不等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colNe(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colNe(criteria, column, value, matcher, null);
    }

    /**
     * 不等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colNe(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(criteria, null, column, value, matcher, Mode.COLUMN, Symbol.NE, slot);
    }

    /**
     * 不等于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param <T>    实体类型
     * @param <V>    值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colNe(final String alias, final String column, final V value) {
        return colNe(alias, column, value, (LogicSymbol) null);
    }

    /**
     * 不等于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @param <T>    实体类型
     * @param <V>    值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colNe(final String alias, final String column, final V value,
                                                final LogicSymbol slot) {
        return colNe(alias, column, value, null, slot);
    }

    /**
     * 不等于表达式
     *
     * @param alias   表别名
     * @param column  字段名
     * @param value   值
     * @param matcher {@link Matcher}
     * @param <T>     实体类型
     * @param <V>     值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colNe(final String alias, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colNe(alias, column, value, matcher, null);
    }

    /**
     * 不等于表达式
     *
     * @param alias   表别名
     * @param column  字段名
     * @param value   值
     * @param matcher {@link Matcher}
     * @param slot    {@link LogicSymbol}
     * @param <T>     实体类型
     * @param <V>     值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colNe(final String alias, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(null, alias, column, value, matcher, Mode.COLUMN, Symbol.NE, slot);
    }

    // endregion

    // region Greater than expression methods

    /**
     * 大于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression gt(final Criteria<T> criteria, final Property<T, V> property, final V value) {
        return gt(criteria, property, value, (LogicSymbol) null);
    }

    /**
     * 大于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression gt(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final LogicSymbol slot) {
        return gt(criteria, property, value, null, slot);
    }

    /**
     * 大于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression gt(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher) {
        return gt(criteria, property, value, matcher, null);
    }

    /**
     * 大于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression gt(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return gt(criteria, convert(property), value, matcher, slot);
    }

    /**
     * 大于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression gt(final Criteria<T> criteria, final String property, final V value) {
        return gt(criteria, property, value, (LogicSymbol) null);
    }

    /**
     * 大于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression gt(final Criteria<T> criteria, final String property, final V value,
                                             final LogicSymbol slot) {
        return gt(criteria, property, value, null, slot);
    }

    /**
     * 大于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression gt(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher) {
        return gt(criteria, property, value, matcher, null);
    }

    /**
     * 大于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression gt(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(criteria, null, property, value, matcher, Mode.PROPERTY, Symbol.GT, slot);
    }

    /**
     * 大于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colGt(final Criteria<T> criteria, final String column, final V value) {
        return colGt(criteria, column, value, (LogicSymbol) null);
    }

    /**
     * 大于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colGt(final Criteria<T> criteria, final String column, final V value,
                                                final LogicSymbol slot) {
        return colGt(criteria, column, value, null, slot);
    }

    /**
     * 大于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colGt(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colGt(criteria, column, value, matcher, null);
    }

    /**
     * 大于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colGt(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(criteria, null, column, value, matcher, Mode.COLUMN, Symbol.GT, slot);
    }

    /**
     * 大于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param <T>    实体类型
     * @param <V>    值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colGt(final String alias, final String column, final V value) {
        return colGt(alias, column, value, (LogicSymbol) null);
    }

    /**
     * 大于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @param <T>    实体类型
     * @param <V>    值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colGt(final String alias, final String column, final V value,
                                                final LogicSymbol slot) {
        return colGt(alias, column, value, null, slot);
    }

    /**
     * 大于表达式
     *
     * @param alias   表别名
     * @param column  字段名
     * @param value   值
     * @param matcher {@link Matcher}
     * @param <T>     实体类型
     * @param <V>     值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colGt(final String alias, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colGt(alias, column, value, matcher, null);
    }

    /**
     * 大于表达式
     *
     * @param alias   表别名
     * @param column  字段名
     * @param value   值
     * @param matcher {@link Matcher}
     * @param slot    {@link LogicSymbol}
     * @param <T>     实体类型
     * @param <V>     值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colGt(final String alias, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(null, alias, column, value, matcher, Mode.COLUMN, Symbol.GT, slot);
    }

    // endregion

    // region Greater than or equal expression methods

    /**
     * 大于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression ge(final Criteria<T> criteria, final Property<T, V> property, final V value) {
        return ge(criteria, property, value, (LogicSymbol) null);
    }

    /**
     * 大于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression ge(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final LogicSymbol slot) {
        return ge(criteria, property, value, null, slot);
    }

    /**
     * 大于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression ge(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher) {
        return ge(criteria, property, value, matcher, null);
    }

    /**
     * 大于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression ge(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return ge(criteria, convert(property), value, matcher, slot);
    }

    /**
     * 大于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression ge(final Criteria<T> criteria, final String property, final V value) {
        return ge(criteria, property, value, (LogicSymbol) null);
    }

    /**
     * 大于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression ge(final Criteria<T> criteria, final String property, final V value,
                                             final LogicSymbol slot) {
        return ge(criteria, property, value, null, slot);
    }

    /**
     * 大于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression ge(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher) {
        return ge(criteria, property, value, matcher, null);
    }

    /**
     * 大于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression ge(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(criteria, null, property, value, matcher, Mode.PROPERTY, Symbol.GE, slot);
    }

    /**
     * 大于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colGe(final Criteria<T> criteria, final String column, final V value) {
        return colGe(criteria, column, value, (LogicSymbol) null);
    }

    /**
     * 大于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colGe(final Criteria<T> criteria, final String column, final V value,
                                                final LogicSymbol slot) {
        return colGe(criteria, column, value, null, slot);
    }

    /**
     * 大于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colGe(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colGe(criteria, column, value, matcher, null);
    }

    /**
     * 大于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colGe(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(criteria, null, column, value, matcher, Mode.COLUMN, Symbol.GE, slot);
    }

    /**
     * 大于或等于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param <T>    实体类型
     * @param <V>    值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colGe(final String alias, final String column, final V value) {
        return colGe(alias, column, value, (LogicSymbol) null);
    }

    /**
     * 大于或等于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @param <T>    实体类型
     * @param <V>    值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colGe(final String alias, final String column, final V value,
                                                final LogicSymbol slot) {
        return colGe(alias, column, value, null, slot);
    }

    /**
     * 大于或等于表达式
     *
     * @param alias   表别名
     * @param column  字段名
     * @param value   值
     * @param matcher {@link Matcher}
     * @param <T>     实体类型
     * @param <V>     值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colGe(final String alias, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colGe(alias, column, value, matcher, null);
    }

    /**
     * 大于或等于表达式
     *
     * @param alias   表别名
     * @param column  字段名
     * @param value   值
     * @param matcher {@link Matcher}
     * @param slot    {@link LogicSymbol}
     * @param <T>     实体类型
     * @param <V>     值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colGe(final String alias, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(null, alias, column, value, matcher, Mode.COLUMN, Symbol.GE, slot);
    }

    // endregion

    // region Less than expression methods

    /**
     * 小于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression lt(final Criteria<T> criteria, final Property<T, V> property, final V value) {
        return lt(criteria, property, value, (LogicSymbol) null);
    }

    /**
     * 小于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression lt(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final LogicSymbol slot) {
        return lt(criteria, property, value, null, slot);
    }

    /**
     * 小于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression lt(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher) {
        return lt(criteria, property, value, matcher, null);
    }

    /**
     * 小于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression lt(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return lt(criteria, convert(property), value, matcher, slot);
    }

    /**
     * 小于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression lt(final Criteria<T> criteria, final String property, final V value) {
        return lt(criteria, property, value, (LogicSymbol) null);
    }

    /**
     * 小于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression lt(final Criteria<T> criteria, final String property, final V value,
                                             final LogicSymbol slot) {
        return lt(criteria, property, value, null, slot);
    }

    /**
     * 小于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression lt(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher) {
        return lt(criteria, property, value, matcher, null);
    }

    /**
     * 小于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression lt(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(criteria, null, property, value, matcher, Mode.PROPERTY, Symbol.LT, slot);
    }

    /**
     * 小于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colLt(final Criteria<T> criteria, final String column, final V value) {
        return colLt(criteria, column, value, (LogicSymbol) null);
    }

    /**
     * 小于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colLt(final Criteria<T> criteria, final String column, final V value,
                                                final LogicSymbol slot) {
        return colLt(criteria, column, value, null, slot);
    }

    /**
     * 小于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colLt(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colLt(criteria, column, value, matcher, null);
    }

    /**
     * 小于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colLt(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(criteria, null, column, value, matcher, Mode.COLUMN, Symbol.LT, slot);
    }

    /**
     * 小于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param <T>    实体类型
     * @param <V>    值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colLt(final String alias, final String column, final V value) {
        return colLt(alias, column, value, (LogicSymbol) null);
    }

    /**
     * 小于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @param <T>    实体类型
     * @param <V>    值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colLt(final String alias, final String column, final V value,
                                                final LogicSymbol slot) {
        return colLt(alias, column, value, null, slot);
    }

    /**
     * 小于表达式
     *
     * @param alias   表别名
     * @param column  字段名
     * @param value   值
     * @param matcher {@link Matcher}
     * @param <T>     实体类型
     * @param <V>     值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colLt(final String alias, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colLt(alias, column, value, matcher, null);
    }

    /**
     * 小于表达式
     *
     * @param alias   表别名
     * @param column  字段名
     * @param value   值
     * @param matcher {@link Matcher}
     * @param slot    {@link LogicSymbol}
     * @param <T>     实体类型
     * @param <V>     值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colLt(final String alias, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(null, alias, column, value, matcher, Mode.COLUMN, Symbol.LT, slot);
    }

    // endregion

    // region Less than or equal expression methods

    /**
     * 小于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression le(final Criteria<T> criteria, final Property<T, V> property, final V value) {
        return le(criteria, property, value, (LogicSymbol) null);
    }

    /**
     * 小于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression le(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final LogicSymbol slot) {
        return le(criteria, property, value, null, slot);
    }

    /**
     * 小于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression le(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher) {
        return le(criteria, property, value, matcher, null);
    }

    /**
     * 小于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression le(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return le(criteria, convert(property), value, matcher, slot);
    }

    /**
     * 小于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression le(final Criteria<T> criteria, final String property, final V value) {
        return le(criteria, property, value, (LogicSymbol) null);
    }

    /**
     * 小于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression le(final Criteria<T> criteria, final String property, final V value,
                                             final LogicSymbol slot) {
        return le(criteria, property, value, null, slot);
    }

    /**
     * 小于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression le(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher) {
        return le(criteria, property, value, matcher, null);
    }

    /**
     * 小于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression le(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(criteria, null, property, value, matcher, Mode.PROPERTY, Symbol.LE, slot);
    }

    /**
     * 小于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colLe(final Criteria<T> criteria, final String column, final V value) {
        return colLe(criteria, column, value, (LogicSymbol) null);
    }

    /**
     * 小于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colLe(final Criteria<T> criteria, final String column, final V value,
                                                final LogicSymbol slot) {
        return colLe(criteria, column, value, null, slot);
    }

    /**
     * 小于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colLe(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colLe(criteria, column, value, matcher, null);
    }

    /**
     * 小于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param matcher  {@link Matcher}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colLe(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(criteria, null, column, value, matcher, Mode.COLUMN, Symbol.LE, slot);
    }

    /**
     * 小于或等于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param <T>    实体类型
     * @param <V>    值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colLe(final String alias, final String column, final V value) {
        return colLe(alias, column, value, (LogicSymbol) null);
    }

    /**
     * 小于或等于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @param <T>    实体类型
     * @param <V>    值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colLe(final String alias, final String column, final V value,
                                                final LogicSymbol slot) {
        return colLe(alias, column, value, null, slot);
    }

    /**
     * 小于或等于表达式
     *
     * @param alias   表别名
     * @param column  字段名
     * @param value   值
     * @param matcher {@link Matcher}
     * @param <T>     实体类型
     * @param <V>     值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colLe(final String alias, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colLe(alias, column, value, matcher, null);
    }

    /**
     * 小于或等于表达式
     *
     * @param alias   表别名
     * @param column  字段名
     * @param value   值
     * @param matcher {@link Matcher}
     * @param slot    {@link LogicSymbol}
     * @param <T>     实体类型
     * @param <V>     值类型
     * @return {@link SimpleExpression}
     */
    public static <T, V> SimpleExpression colLe(final String alias, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return simpleExpression(null, alias, column, value, matcher, Mode.COLUMN, Symbol.LE, slot);
    }

    // endregion

    // region Between expression methods

    /**
     * between表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param <V>      值类型
     * @return {@link BetweenExpression}
     */
    public static <T, V> BetweenExpression between(final Criteria<?> criteria, final Property<T, V> property,
                                                   final V begin, final V end) {
        return between(criteria, property, begin, end, null);
    }

    /**
     * between表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@link BetweenExpression}
     */
    public static <T, V> BetweenExpression between(final Criteria<?> criteria, final Property<T, V> property,
                                                   final V begin, final V end, final LogicSymbol slot) {
        return between(criteria, convert(property), begin, end, slot);
    }

    /**
     * between表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param begin    开始值
     * @param end      结束值
     * @param <V>      值类型
     * @return {@link BetweenExpression}
     */
    public static <V> BetweenExpression between(final Criteria<?> criteria, final String property,
                                                final V begin, final V end) {
        return between(criteria, property, begin, end, null);
    }

    /**
     * between表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@link BetweenExpression}
     */
    public static <V> BetweenExpression between(final Criteria<?> criteria, final String property,
                                                final V begin, final V end, final LogicSymbol slot) {
        return betweenExpression(criteria, null, property, begin, end, Mode.PROPERTY, Symbol.BETWEEN, slot);
    }

    /**
     * between表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param begin    开始值
     * @param end      结束值
     * @param <V>      值类型
     * @return {@link BetweenExpression}
     */
    public static <V> BetweenExpression colBetween(final Criteria<?> criteria, final String column,
                                                   final V begin, final V end) {
        return colBetween(criteria, column, begin, end, null);
    }

    /**
     * between表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@link BetweenExpression}
     */
    public static <V> BetweenExpression colBetween(final Criteria<?> criteria, final String column,
                                                   final V begin, final V end, final LogicSymbol slot) {
        return betweenExpression(criteria, null, column, begin, end, Mode.COLUMN, Symbol.BETWEEN, slot);
    }

    /**
     * between表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param <V>    值类型
     * @return {@link BetweenExpression}
     */
    public static <V> BetweenExpression colBetween(final String alias, final String column,
                                                   final V begin, final V end) {
        return colBetween(alias, column, begin, end, null);
    }

    /**
     * between表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@link BetweenExpression}
     */
    public static <V> BetweenExpression colBetween(final String alias, final String column,
                                                   final V begin, final V end, final LogicSymbol slot) {
        return betweenExpression(null, alias, column, begin, end, Mode.COLUMN, Symbol.BETWEEN, slot);
    }

    // endregion

    // region Not between expression methods

    /**
     * not between表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param <V>      值类型
     * @return {@link BetweenExpression}
     */
    public static <T, V> BetweenExpression notBetween(final Criteria<?> criteria, final Property<T, V> property,
                                                      final V begin, final V end) {
        return notBetween(criteria, property, begin, end, null);
    }

    /**
     * not between表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@link BetweenExpression}
     */
    public static <T, V> BetweenExpression notBetween(final Criteria<?> criteria, final Property<T, V> property,
                                                      final V begin, final V end, final LogicSymbol slot) {
        return notBetween(criteria, convert(property), begin, end, slot);
    }

    /**
     * not between表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param begin    开始值
     * @param end      结束值
     * @param <V>      值类型
     * @return {@link BetweenExpression}
     */
    public static <V> BetweenExpression notBetween(final Criteria<?> criteria, final String property,
                                                   final V begin, final V end) {
        return notBetween(criteria, property, begin, end, null);
    }

    /**
     * not between表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@link BetweenExpression}
     */
    public static <V> BetweenExpression notBetween(final Criteria<?> criteria, final String property,
                                                   final V begin, final V end, final LogicSymbol slot) {
        return betweenExpression(criteria, null, property, begin, end, Mode.PROPERTY, Symbol.NOT_BETWEEN, slot);
    }

    /**
     * not between表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param begin    开始值
     * @param end      结束值
     * @param <V>      值类型
     * @return {@link BetweenExpression}
     */
    public static <V> BetweenExpression colNotBetween(final Criteria<?> criteria, final String column,
                                                      final V begin, final V end) {
        return colNotBetween(criteria, column, begin, end, null);
    }

    /**
     * not between表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param begin    开始值
     * @param end      结束值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@link BetweenExpression}
     */
    public static <V> BetweenExpression colNotBetween(final Criteria<?> criteria, final String column,
                                                      final V begin, final V end, final LogicSymbol slot) {
        return betweenExpression(criteria, null, column, begin, end, Mode.COLUMN, Symbol.NOT_BETWEEN, slot);
    }

    /**
     * not between表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param <V>    值类型
     * @return {@link BetweenExpression}
     */
    public static <V> BetweenExpression colNotBetween(final String alias, final String column,
                                                      final V begin, final V end) {
        return colNotBetween(alias, column, begin, end, null);
    }

    /**
     * not between表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@link BetweenExpression}
     */
    public static <V> BetweenExpression colNotBetween(final String alias, final String column,
                                                      final V begin, final V end, final LogicSymbol slot) {
        return betweenExpression(null, alias, column, begin, end, Mode.COLUMN, Symbol.NOT_BETWEEN, slot);
    }

    // endregion

    // region In expression methods

    /**
     * in表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param values   值列表
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    @SafeVarargs
    public static <T, V> InExpression in(final Criteria<?> criteria, final Property<T, V> property,
                                         final V... values) {
        return in(criteria, property, Arrays.asList(values));
    }

    /**
     * in表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param slot     {@link LogicSymbol}
     * @param values   值列表
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    @SafeVarargs
    public static <T, V> InExpression in(final Criteria<?> criteria, final Property<T, V> property,
                                         final LogicSymbol slot, final V... values) {
        return in(criteria, property, Arrays.asList(values), slot);
    }

    /**
     * in表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param values   值列表
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    public static <T, V> InExpression in(final Criteria<?> criteria, final Property<T, V> property,
                                         final Collection<V> values) {
        return in(criteria, property, values, null);
    }

    /**
     * in表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    public static <T, V> InExpression in(final Criteria<?> criteria, final Property<T, V> property,
                                         final Collection<V> values, final LogicSymbol slot) {
        return in(criteria, convert(property), values, slot);
    }

    /**
     * in表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param values   值列表
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    @SafeVarargs
    public static <V> InExpression in(final Criteria<?> criteria, final String property, final V... values) {
        return in(criteria, property, Arrays.asList(values));
    }

    /**
     * in表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param slot     {@link LogicSymbol}
     * @param values   值列表
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    @SafeVarargs
    public static <V> InExpression in(final Criteria<?> criteria, final String property,
                                      final LogicSymbol slot, final V... values) {
        return in(criteria, property, values, Arrays.asList(values), slot);
    }

    /**
     * in表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param values   值列表
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    public static <V> InExpression in(final Criteria<?> criteria, final String property, final Collection<V> values) {
        return in(criteria, property, values, null);
    }

    /**
     * in表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    public static <V> InExpression in(final Criteria<?> criteria, final String property, final Collection<V> values,
                                      final LogicSymbol slot) {
        return inExpression(criteria, null, property, values, Mode.PROPERTY, Symbol.IN, slot);
    }

    /**
     * in表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param values   值列表
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    @SafeVarargs
    public static <V> InExpression colIn(final Criteria<?> criteria, final String column, final V... values) {
        return colIn(criteria, column, Arrays.asList(values));
    }

    /**
     * in表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param slot     {@link LogicSymbol}
     * @param values   值列表
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    @SafeVarargs
    public static <V> InExpression colIn(final Criteria<?> criteria, final String column,
                                         final LogicSymbol slot, final V... values) {
        return colIn(criteria, column, Arrays.asList(values), slot);
    }

    /**
     * in表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param values   值列表
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    public static <V> InExpression colIn(final Criteria<?> criteria, final String column, final Collection<V> values) {
        return colIn(criteria, column, values, null);
    }

    /**
     * in表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    public static <V> InExpression colIn(final Criteria<?> criteria, final String column,
                                         final Collection<V> values, final LogicSymbol slot) {
        return inExpression(criteria, null, column, values, Mode.COLUMN, Symbol.IN, slot);
    }


    /**
     * in表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param values 值列表
     * @param <V>    值类型
     * @return {@link InExpression}
     */
    @SafeVarargs
    public static <V> InExpression colIn(final String alias, final String column, final V... values) {
        return colIn(alias, column, Arrays.asList(values));
    }

    /**
     * in表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param slot   {@link LogicSymbol}
     * @param values 值列表
     * @param <V>    值类型
     * @return {@link InExpression}
     */
    @SafeVarargs
    public static <V> InExpression colIn(final String alias, final String column,
                                         final LogicSymbol slot, final V... values) {
        return colIn(alias, column, Arrays.asList(values), slot);
    }

    /**
     * in表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param values 值列表
     * @param <V>    值类型
     * @return {@link InExpression}
     */
    public static <V> InExpression colIn(final String alias, final String column, final Collection<V> values) {
        return colIn(alias, column, values, null);
    }

    /**
     * in表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param values 值列表
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@link InExpression}
     */
    public static <V> InExpression colIn(final String alias, final String column,
                                         final Collection<V> values, final LogicSymbol slot) {
        return inExpression(null, alias, column, values, Mode.COLUMN, Symbol.IN, slot);
    }

    // endregion

    // region Not in expression methods

    /**
     * not in表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param values   值列表
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    @SafeVarargs
    public static <T, V> InExpression notIn(final Criteria<?> criteria, final Property<T, V> property,
                                            final V... values) {
        return notIn(criteria, property, Arrays.asList(values));
    }

    /**
     * not in表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param slot     {@link LogicSymbol}
     * @param values   值列表
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    @SafeVarargs
    public static <T, V> InExpression notIn(final Criteria<?> criteria, final Property<T, V> property,
                                            final LogicSymbol slot, final V... values) {
        return notIn(criteria, property, Arrays.asList(values), slot);
    }

    /**
     * not in表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param values   值列表
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    public static <T, V> InExpression notIn(final Criteria<?> criteria, final Property<T, V> property,
                                            final Collection<V> values) {
        return notIn(criteria, property, values, null);
    }

    /**
     * not in表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    public static <T, V> InExpression notIn(final Criteria<?> criteria, final Property<T, V> property,
                                            final Collection<V> values, final LogicSymbol slot) {
        return notIn(criteria, convert(property), values, slot);
    }

    /**
     * not in表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param values   值列表
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    @SafeVarargs
    public static <V> InExpression notIn(final Criteria<?> criteria, final String property, final V... values) {
        return notIn(criteria, property, Arrays.asList(values));
    }

    /**
     * not in表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param slot     {@link LogicSymbol}
     * @param values   值列表
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    @SafeVarargs
    public static <V> InExpression notIn(final Criteria<?> criteria, final String property,
                                         final LogicSymbol slot, final V... values) {
        return notIn(criteria, property, values, Arrays.asList(values), slot);
    }

    /**
     * not in表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param values   值列表
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    public static <V> InExpression notIn(final Criteria<?> criteria, final String property,
                                         final Collection<V> values) {
        return notIn(criteria, property, values, null);
    }

    /**
     * not in表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    public static <V> InExpression notIn(final Criteria<?> criteria, final String property, final Collection<V> values,
                                         final LogicSymbol slot) {
        return inExpression(criteria, null, property, values, Mode.PROPERTY, Symbol.NOT_IN, slot);
    }

    /**
     * not in表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param values   值列表
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    @SafeVarargs
    public static <V> InExpression colNotIn(final Criteria<?> criteria, final String column, final V... values) {
        return colNotIn(criteria, column, Arrays.asList(values));
    }

    /**
     * not in表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param slot     {@link LogicSymbol}
     * @param values   值列表
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    @SafeVarargs
    public static <V> InExpression colNotIn(final Criteria<?> criteria, final String column,
                                            final LogicSymbol slot, final V... values) {
        return colNotIn(criteria, column, Arrays.asList(values), slot);
    }

    /**
     * not in表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param values   值列表
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    public static <V> InExpression colNotIn(final Criteria<?> criteria, final String column,
                                            final Collection<V> values) {
        return colNotIn(criteria, column, values, null);
    }

    /**
     * not in表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param values   值列表
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@link InExpression}
     */
    public static <V> InExpression colNotIn(final Criteria<?> criteria, final String column,
                                            final Collection<V> values, final LogicSymbol slot) {
        return inExpression(criteria, null, column, values, Mode.COLUMN, Symbol.NOT_IN, slot);
    }

    /**
     * not in表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param values 值列表
     * @param <V>    值类型
     * @return {@link InExpression}
     */
    @SafeVarargs
    public static <V> InExpression colNotIn(final String alias, final String column, final V... values) {
        return colNotIn(alias, column, Arrays.asList(values));
    }

    /**
     * not in表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param slot   {@link LogicSymbol}
     * @param values 值列表
     * @param <V>    值类型
     * @return {@link InExpression}
     */
    @SafeVarargs
    public static <V> InExpression colNotIn(final String alias, final String column,
                                            final LogicSymbol slot, final V... values) {
        return colNotIn(alias, column, Arrays.asList(values), slot);
    }

    /**
     * not in表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param values 值列表
     * @param <V>    值类型
     * @return {@link InExpression}
     */
    public static <V> InExpression colNotIn(final String alias, final String column, final Collection<V> values) {
        return colNotIn(alias, column, values, null);
    }

    /**
     * not in表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param values 值列表
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@link InExpression}
     */
    public static <V> InExpression colNotIn(final String alias, final String column,
                                            final Collection<V> values, final LogicSymbol slot) {
        return inExpression(null, alias, column, values, Mode.COLUMN, Symbol.NOT_IN, slot);
    }

    // endregion

    // region Like expression methods

    // region Like left expression methods

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeLeft(final Criteria<T> criteria, final Property<T, String> property,
                                              final String value) {
        return likeLeft(criteria, property, value, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeLeft(final Criteria<T> criteria, final Property<T, String> property,
                                              final String value, final boolean ignoreCase) {
        return likeLeft(criteria, property, value, null, ignoreCase);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeLeft(final Criteria<T> criteria, final Property<T, String> property,
                                              final String value, final Character escape) {
        return likeLeft(criteria, property, value, escape, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeLeft(final Criteria<T> criteria, final Property<T, String> property,
                                              final String value, final Character escape, final boolean ignoreCase) {
        return likeLeft(criteria, property, value, escape, ignoreCase, null);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeLeft(final Criteria<T> criteria, final Property<T, String> property,
                                              final String value, final LogicSymbol slot) {
        return likeLeft(criteria, property, value, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeLeft(final Criteria<T> criteria, final Property<T, String> property,
                                              final String value, final boolean ignoreCase, final LogicSymbol slot) {
        return likeLeft(criteria, property, value, null, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeLeft(final Criteria<T> criteria, final Property<T, String> property,
                                              final String value, final Character escape, final LogicSymbol slot) {
        return likeLeft(criteria, property, value, escape, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeLeft(final Criteria<T> criteria, final Property<T, String> property,
                                              final String value, final Character escape,
                                              final boolean ignoreCase, final LogicSymbol slot) {
        return likeLeft(criteria, convert(property), value, escape, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeLeft(final Criteria<?> criteria, final String property, final String value) {
        return likeLeft(criteria, property, value, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeLeft(final Criteria<?> criteria, final String property, final String value,
                                          final boolean ignoreCase) {
        return likeLeft(criteria, property, value, null, ignoreCase);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param escape   转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeLeft(final Criteria<?> criteria, final String property, final String value,
                                          final Character escape) {
        return likeLeft(criteria, property, value, escape, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeLeft(final Criteria<?> criteria, final String property, final String value,
                                          final Character escape, final boolean ignoreCase) {
        return likeLeft(criteria, property, value, escape, ignoreCase, null);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeLeft(final Criteria<?> criteria, final String property, final String value,
                                          final LogicSymbol slot) {
        return likeLeft(criteria, property, value, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeLeft(final Criteria<?> criteria, final String property, final String value,
                                          final boolean ignoreCase, final LogicSymbol slot) {
        return likeLeft(criteria, property, value, null, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeLeft(final Criteria<?> criteria, final String property, final String value,
                                          final Character escape, final LogicSymbol slot) {
        return likeLeft(criteria, property, value, escape, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeLeft(final Criteria<?> criteria, final String property, final String value,
                                          final Character escape, final boolean ignoreCase, final LogicSymbol slot) {
        return like(criteria, property, value, MatchMode.END, escape, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeLeft(final Criteria<?> criteria, final String column, final String value) {
        return colLikeLeft(criteria, column, value, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeLeft(final Criteria<?> criteria, final String column, final String value,
                                             final boolean ignoreCase) {
        return colLikeLeft(criteria, column, value, null, ignoreCase);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param escape   转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeLeft(final Criteria<?> criteria, final String column, final String value,
                                             final Character escape) {
        return colLikeLeft(criteria, column, value, escape, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeLeft(final Criteria<?> criteria, final String column, final String value,
                                             final Character escape, final boolean ignoreCase) {
        return colLikeLeft(criteria, column, value, escape, ignoreCase, null);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeLeft(final Criteria<?> criteria, final String column, final String value,
                                             final LogicSymbol slot) {
        return colLikeLeft(criteria, column, value, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeLeft(final Criteria<?> criteria, final String column, final String value,
                                             final boolean ignoreCase, final LogicSymbol slot) {
        return colLikeLeft(criteria, column, value, null, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeLeft(final Criteria<?> criteria, final String column, final String value,
                                             final Character escape, final LogicSymbol slot) {
        return colLikeLeft(criteria, column, value, escape, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeLeft(final Criteria<?> criteria, final String column, final String value,
                                             final Character escape, final boolean ignoreCase, final LogicSymbol slot) {
        return colLike(criteria, column, value, MatchMode.END, escape, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeLeft(final String alias, final String column, final String value) {
        return colLikeLeft(alias, column, value, false);
    }

    /**
     * like表达式
     *
     * @param alias      表别名
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeLeft(final String alias, final String column, final String value,
                                             final boolean ignoreCase) {
        return colLikeLeft(alias, column, value, null, ignoreCase);
    }

    /**
     * like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeLeft(final String alias, final String column, final String value,
                                             final Character escape) {
        return colLikeLeft(alias, column, value, escape, false);
    }

    /**
     * like表达式
     *
     * @param alias      表别名
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeLeft(final String alias, final String column, final String value,
                                             final Character escape, final boolean ignoreCase) {
        return colLikeLeft(alias, column, value, escape, ignoreCase, null);
    }

    /**
     * like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeLeft(final String alias, final String column, final String value,
                                             final LogicSymbol slot) {
        return colLikeLeft(alias, column, value, false, slot);
    }

    /**
     * like表达式
     *
     * @param alias      表别名
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeLeft(final String alias, final String column, final String value,
                                             final boolean ignoreCase, final LogicSymbol slot) {
        return colLikeLeft(alias, column, value, null, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @param slot   {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeLeft(final String alias, final String column, final String value,
                                             final Character escape, final LogicSymbol slot) {
        return colLikeLeft(alias, column, value, escape, false, slot);
    }

    /**
     * like表达式
     *
     * @param alias      表别名
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeLeft(final String alias, final String column, final String value,
                                             final Character escape, final boolean ignoreCase,
                                             final LogicSymbol slot) {
        return colLike(alias, column, value, MatchMode.END, escape, ignoreCase, slot);
    }

    // endregion

    // region Like right expression methods

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeRight(final Criteria<T> criteria, final Property<T, String> property,
                                               final String value) {
        return likeRight(criteria, property, value, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeRight(final Criteria<T> criteria, final Property<T, String> property,
                                               final String value, final boolean ignoreCase) {
        return likeRight(criteria, property, value, null, ignoreCase);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeRight(final Criteria<T> criteria, final Property<T, String> property,
                                               final String value, final Character escape) {
        return likeRight(criteria, property, value, escape, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeRight(final Criteria<T> criteria, final Property<T, String> property,
                                               final String value, final Character escape, final boolean ignoreCase) {
        return likeRight(criteria, property, value, escape, ignoreCase, null);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeRight(final Criteria<T> criteria, final Property<T, String> property,
                                               final String value, final LogicSymbol slot) {
        return likeRight(criteria, property, value, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeRight(final Criteria<T> criteria, final Property<T, String> property,
                                               final String value, final boolean ignoreCase, final LogicSymbol slot) {
        return likeRight(criteria, property, value, null, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeRight(final Criteria<T> criteria, final Property<T, String> property,
                                               final String value, final Character escape, final LogicSymbol slot) {
        return likeRight(criteria, property, value, escape, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeRight(final Criteria<T> criteria, final Property<T, String> property,
                                               final String value, final Character escape,
                                               final boolean ignoreCase, final LogicSymbol slot) {
        return likeRight(criteria, convert(property), value, escape, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeRight(final Criteria<?> criteria, final String property, final String value) {
        return likeRight(criteria, property, value, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeRight(final Criteria<?> criteria, final String property, final String value,
                                           final boolean ignoreCase) {
        return likeRight(criteria, property, value, null, ignoreCase);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param escape   转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeRight(final Criteria<?> criteria, final String property, final String value,
                                           final Character escape) {
        return likeRight(criteria, property, value, escape, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeRight(final Criteria<?> criteria, final String property, final String value,
                                           final Character escape, final boolean ignoreCase) {
        return likeRight(criteria, property, value, escape, ignoreCase, null);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeRight(final Criteria<?> criteria, final String property, final String value,
                                           final LogicSymbol slot) {
        return likeRight(criteria, property, value, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeRight(final Criteria<?> criteria, final String property, final String value,
                                           final boolean ignoreCase, final LogicSymbol slot) {
        return likeRight(criteria, property, value, null, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeRight(final Criteria<?> criteria, final String property, final String value,
                                           final Character escape, final LogicSymbol slot) {
        return likeRight(criteria, property, value, escape, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeRight(final Criteria<?> criteria, final String property, final String value,
                                           final Character escape, final boolean ignoreCase, final LogicSymbol slot) {
        return like(criteria, property, value, MatchMode.START, escape, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeRight(final Criteria<?> criteria, final String column, final String value) {
        return colLikeRight(criteria, column, value, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeRight(final Criteria<?> criteria, final String column, final String value,
                                              final boolean ignoreCase) {
        return colLikeRight(criteria, column, value, null, ignoreCase);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param escape   转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeRight(final Criteria<?> criteria, final String column, final String value,
                                              final Character escape) {
        return colLikeRight(criteria, column, value, escape, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeRight(final Criteria<?> criteria, final String column, final String value,
                                              final Character escape, final boolean ignoreCase) {
        return colLikeRight(criteria, column, value, escape, ignoreCase, null);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeRight(final Criteria<?> criteria, final String column, final String value,
                                              final LogicSymbol slot) {
        return colLikeRight(criteria, column, value, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeRight(final Criteria<?> criteria, final String column, final String value,
                                              final boolean ignoreCase, final LogicSymbol slot) {
        return colLikeRight(criteria, column, value, null, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeRight(final Criteria<?> criteria, final String column, final String value,
                                              final Character escape, final LogicSymbol slot) {
        return colLikeRight(criteria, column, value, escape, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeRight(final Criteria<?> criteria, final String column, final String value,
                                              final Character escape, final boolean ignoreCase, final LogicSymbol slot) {
        return colLike(criteria, column, value, MatchMode.START, escape, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeRight(final String alias, final String column, final String value) {
        return colLikeRight(alias, column, value, false);
    }

    /**
     * like表达式
     *
     * @param alias      表别名
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeRight(final String alias, final String column, final String value,
                                              final boolean ignoreCase) {
        return colLikeRight(alias, column, value, null, ignoreCase);
    }

    /**
     * like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeRight(final String alias, final String column, final String value,
                                              final Character escape) {
        return colLikeRight(alias, column, value, escape, false);
    }

    /**
     * like表达式
     *
     * @param alias      表别名
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeRight(final String alias, final String column, final String value,
                                              final Character escape, final boolean ignoreCase) {
        return colLikeRight(alias, column, value, escape, ignoreCase, null);
    }

    /**
     * like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeRight(final String alias, final String column, final String value,
                                              final LogicSymbol slot) {
        return colLikeRight(alias, column, value, false, slot);
    }

    /**
     * like表达式
     *
     * @param alias      表别名
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeRight(final String alias, final String column, final String value,
                                              final boolean ignoreCase, final LogicSymbol slot) {
        return colLikeRight(alias, column, value, null, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @param slot   {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeRight(final String alias, final String column, final String value,
                                              final Character escape, final LogicSymbol slot) {
        return colLikeRight(alias, column, value, escape, false, slot);
    }

    /**
     * like表达式
     *
     * @param alias      表别名
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeRight(final String alias, final String column, final String value,
                                              final Character escape, final boolean ignoreCase,
                                              final LogicSymbol slot) {
        return colLike(alias, column, value, MatchMode.START, escape, ignoreCase, slot);
    }

    // endregion

    // region Like any expression methods

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeAny(final Criteria<T> criteria, final Property<T, String> property,
                                             final String value) {
        return likeAny(criteria, property, value, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeAny(final Criteria<T> criteria, final Property<T, String> property,
                                             final String value, final boolean ignoreCase) {
        return likeAny(criteria, property, value, null, ignoreCase);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeAny(final Criteria<T> criteria, final Property<T, String> property,
                                             final String value, final Character escape) {
        return likeAny(criteria, property, value, escape, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeAny(final Criteria<T> criteria, final Property<T, String> property,
                                             final String value, final Character escape, final boolean ignoreCase) {
        return likeAny(criteria, property, value, escape, ignoreCase, null);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeAny(final Criteria<T> criteria, final Property<T, String> property,
                                             final String value, final LogicSymbol slot) {
        return likeAny(criteria, property, value, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeAny(final Criteria<T> criteria, final Property<T, String> property,
                                             final String value, final boolean ignoreCase, final LogicSymbol slot) {
        return likeAny(criteria, property, value, null, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeAny(final Criteria<T> criteria, final Property<T, String> property,
                                             final String value, final Character escape, final LogicSymbol slot) {
        return likeAny(criteria, property, value, escape, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression likeAny(final Criteria<T> criteria, final Property<T, String> property,
                                             final String value, final Character escape,
                                             final boolean ignoreCase, final LogicSymbol slot) {
        return likeAny(criteria, convert(property), value, escape, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeAny(final Criteria<?> criteria, final String property, final String value) {
        return likeAny(criteria, property, value, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeAny(final Criteria<?> criteria, final String property, final String value,
                                         final boolean ignoreCase) {
        return likeAny(criteria, property, value, null, ignoreCase);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param escape   转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeAny(final Criteria<?> criteria, final String property, final String value,
                                         final Character escape) {
        return likeAny(criteria, property, value, escape, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeAny(final Criteria<?> criteria, final String property, final String value,
                                         final Character escape, final boolean ignoreCase) {
        return likeAny(criteria, property, value, escape, ignoreCase, null);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeAny(final Criteria<?> criteria, final String property, final String value,
                                         final LogicSymbol slot) {
        return likeAny(criteria, property, value, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeAny(final Criteria<?> criteria, final String property, final String value,
                                         final boolean ignoreCase, final LogicSymbol slot) {
        return likeAny(criteria, property, value, null, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeAny(final Criteria<?> criteria, final String property, final String value,
                                         final Character escape, final LogicSymbol slot) {
        return likeAny(criteria, property, value, escape, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression likeAny(final Criteria<?> criteria, final String property, final String value,
                                         final Character escape, final boolean ignoreCase, final LogicSymbol slot) {
        return like(criteria, property, value, MatchMode.ANYWHERE, escape, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeAny(final Criteria<?> criteria, final String column, final String value) {
        return colLikeAny(criteria, column, value, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeAny(final Criteria<?> criteria, final String column, final String value,
                                            final boolean ignoreCase) {
        return colLikeAny(criteria, column, value, null, ignoreCase);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param escape   转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeAny(final Criteria<?> criteria, final String column, final String value,
                                            final Character escape) {
        return colLikeAny(criteria, column, value, escape, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeAny(final Criteria<?> criteria, final String column, final String value,
                                            final Character escape, final boolean ignoreCase) {
        return colLikeAny(criteria, column, value, escape, ignoreCase, null);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeAny(final Criteria<?> criteria, final String column, final String value,
                                            final LogicSymbol slot) {
        return colLikeAny(criteria, column, value, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeAny(final Criteria<?> criteria, final String column, final String value,
                                            final boolean ignoreCase, final LogicSymbol slot) {
        return colLikeAny(criteria, column, value, null, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeAny(final Criteria<?> criteria, final String column, final String value,
                                            final Character escape, final LogicSymbol slot) {
        return colLikeAny(criteria, column, value, escape, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeAny(final Criteria<?> criteria, final String column, final String value,
                                            final Character escape, final boolean ignoreCase, final LogicSymbol slot) {
        return colLike(criteria, column, value, MatchMode.ANYWHERE, escape, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeAny(final String alias, final String column, final String value) {
        return colLikeAny(alias, column, value, false);
    }

    /**
     * like表达式
     *
     * @param alias      表别名
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeAny(final String alias, final String column, final String value,
                                            final boolean ignoreCase) {
        return colLikeAny(alias, column, value, null, ignoreCase);
    }

    /**
     * like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeAny(final String alias, final String column, final String value,
                                            final Character escape) {
        return colLikeAny(alias, column, value, escape, false);
    }

    /**
     * like表达式
     *
     * @param alias      表别名
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeAny(final String alias, final String column, final String value,
                                            final Character escape, final boolean ignoreCase) {
        return colLikeAny(alias, column, value, escape, ignoreCase, null);
    }

    /**
     * like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeAny(final String alias, final String column, final String value,
                                            final LogicSymbol slot) {
        return colLikeAny(alias, column, value, false, slot);
    }

    /**
     * like表达式
     *
     * @param alias      表别名
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeAny(final String alias, final String column, final String value,
                                            final boolean ignoreCase, final LogicSymbol slot) {
        return colLikeAny(alias, column, value, null, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @param slot   {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeAny(final String alias, final String column, final String value,
                                            final Character escape, final LogicSymbol slot) {
        return colLikeAny(alias, column, value, escape, false, slot);
    }

    /**
     * like表达式
     *
     * @param alias      表别名
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLikeAny(final String alias, final String column, final String value,
                                            final Character escape, final boolean ignoreCase,
                                            final LogicSymbol slot) {
        return colLike(alias, column, value, MatchMode.ANYWHERE, escape, ignoreCase, slot);
    }

    // endregion

    // region Like base expression methods

    /**
     * like表达式
     *
     * @param criteria  {@link Criteria}
     * @param property  {@link Property}
     * @param value     值
     * @param matchMode 匹配模式
     * @param <T>       实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression like(final Criteria<T> criteria, final Property<T, String> property,
                                          final String value, final MatchMode matchMode) {
        return like(criteria, property, value, matchMode, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param matchMode  匹配模式
     * @param ignoreCase 是否忽略大小写
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression like(final Criteria<T> criteria, final Property<T, String> property,
                                          final String value, final MatchMode matchMode,
                                          final boolean ignoreCase) {
        return like(criteria, property, value, matchMode, null, ignoreCase);
    }

    /**
     * like表达式
     *
     * @param criteria  {@link Criteria}
     * @param property  {@link Property}
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @param <T>       实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression like(final Criteria<T> criteria, final Property<T, String> property,
                                          final String value, final MatchMode matchMode, final Character escape) {
        return like(criteria, property, value, matchMode, escape, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param matchMode  匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression like(final Criteria<T> criteria, final Property<T, String> property,
                                          final String value, final MatchMode matchMode, final Character escape,
                                          final boolean ignoreCase) {
        return like(criteria, property, value, matchMode, escape, ignoreCase, null);
    }

    /**
     * like表达式
     *
     * @param criteria  {@link Criteria}
     * @param property  {@link Property}
     * @param value     值
     * @param matchMode 匹配模式
     * @param slot      {@link LogicSymbol}
     * @param <T>       实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression like(final Criteria<T> criteria, final Property<T, String> property,
                                          final String value, final MatchMode matchMode, final LogicSymbol slot) {
        return like(criteria, property, value, matchMode, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param matchMode  匹配模式
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression like(final Criteria<T> criteria, final Property<T, String> property,
                                          final String value, final MatchMode matchMode,
                                          final boolean ignoreCase, final LogicSymbol slot) {
        return like(criteria, property, value, matchMode, null, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria  {@link Criteria}
     * @param property  {@link Property}
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @param slot      {@link LogicSymbol}
     * @param <T>       实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression like(final Criteria<T> criteria, final Property<T, String> property,
                                          final String value, final MatchMode matchMode, final Character escape,
                                          final LogicSymbol slot) {
        return like(criteria, property, value, matchMode, escape, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param matchMode  匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression like(final Criteria<T> criteria, final Property<T, String> property,
                                          final String value, final MatchMode matchMode, final Character escape,
                                          final boolean ignoreCase, final LogicSymbol slot) {
        return like(criteria, convert(property), value, matchMode, escape, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria  {@link Criteria}
     * @param property  属性名
     * @param value     值
     * @param matchMode 匹配模式
     * @return {@link LikeExpression}
     */
    public static LikeExpression like(final Criteria<?> criteria, final String property, final String value,
                                      final MatchMode matchMode) {
        return like(criteria, property, value, matchMode, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param matchMode  匹配模式
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression like(final Criteria<?> criteria, final String property, final String value,
                                      final MatchMode matchMode, final boolean ignoreCase) {
        return like(criteria, property, value, matchMode, null, ignoreCase);
    }

    /**
     * like表达式
     *
     * @param criteria  {@link Criteria}
     * @param property  属性名
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression like(final Criteria<?> criteria, final String property, final String value,
                                      final MatchMode matchMode, final Character escape) {
        return like(criteria, property, value, matchMode, escape, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param matchMode  匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression like(final Criteria<?> criteria, final String property, final String value,
                                      final MatchMode matchMode, final Character escape, final boolean ignoreCase) {
        return like(criteria, property, value, matchMode, escape, ignoreCase, null);
    }

    /**
     * like表达式
     *
     * @param criteria  {@link Criteria}
     * @param property  属性名
     * @param value     值
     * @param matchMode 匹配模式
     * @param slot      {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression like(final Criteria<?> criteria, final String property, final String value,
                                      final MatchMode matchMode, final LogicSymbol slot) {
        return like(criteria, property, value, matchMode, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param matchMode  匹配模式
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression like(final Criteria<?> criteria, final String property, final String value,
                                      final MatchMode matchMode, final boolean ignoreCase,
                                      final LogicSymbol slot) {
        return like(criteria, property, value, matchMode, null, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria  {@link Criteria}
     * @param property  属性名
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @param slot      {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression like(final Criteria<?> criteria, final String property, final String value,
                                      final MatchMode matchMode, final Character escape, final LogicSymbol slot) {
        return like(criteria, property, value, matchMode, escape, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param matchMode  匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression like(final Criteria<?> criteria, final String property, final String value,
                                      final MatchMode matchMode, final Character escape, final boolean ignoreCase,
                                      final LogicSymbol slot) {
        return likeExpression(criteria, null, property, value, matchMode, escape, ignoreCase, Mode.PROPERTY,
                Symbol.LIKE, slot);
    }

    /**
     * like表达式
     *
     * @param criteria  {@link Criteria}
     * @param column    字段名
     * @param value     值
     * @param matchMode 匹配模式
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLike(final Criteria<?> criteria, final String column, final String value,
                                         final MatchMode matchMode) {
        return colLike(criteria, column, value, matchMode, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param matchMode  匹配模式
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLike(final Criteria<?> criteria, final String column, final String value,
                                         final MatchMode matchMode, final boolean ignoreCase) {
        return colLike(criteria, column, value, matchMode, null, ignoreCase);
    }

    /**
     * like表达式
     *
     * @param criteria  {@link Criteria}
     * @param column    字段名
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLike(final Criteria<?> criteria, final String column, final String value,
                                         final MatchMode matchMode, final Character escape) {
        return colLike(criteria, column, value, matchMode, escape, false);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param matchMode  匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLike(final Criteria<?> criteria, final String column, final String value,
                                         final MatchMode matchMode, final Character escape,
                                         final boolean ignoreCase) {
        return colLike(criteria, column, value, matchMode, escape, ignoreCase, null);
    }

    /**
     * like表达式
     *
     * @param criteria  {@link Criteria}
     * @param column    字段名
     * @param value     值
     * @param matchMode 匹配模式
     * @param slot      {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLike(final Criteria<?> criteria, final String column, final String value,
                                         final MatchMode matchMode, final LogicSymbol slot) {
        return colLike(criteria, column, value, matchMode, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param matchMode  匹配模式
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLike(final Criteria<?> criteria, final String column, final String value,
                                         final MatchMode matchMode, final boolean ignoreCase,
                                         final LogicSymbol slot) {
        return colLike(criteria, column, value, matchMode, null, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param criteria  {@link Criteria}
     * @param column    字段名
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @param slot      {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLike(final Criteria<?> criteria, final String column, final String value,
                                         final MatchMode matchMode, final Character escape, final LogicSymbol slot) {
        return colLike(criteria, column, value, matchMode, escape, false, slot);
    }

    /**
     * like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param matchMode  匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLike(final Criteria<?> criteria, final String column, final String value,
                                         final MatchMode matchMode, final Character escape, final boolean ignoreCase,
                                         final LogicSymbol slot) {
        return likeExpression(criteria, null, column, value, matchMode, escape, ignoreCase, Mode.COLUMN,
                Symbol.LIKE, slot);
    }

    /**
     * like表达式
     *
     * @param alias     表别名
     * @param column    字段名
     * @param value     值
     * @param matchMode 匹配模式
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLike(final String alias, final String column, final String value,
                                         final MatchMode matchMode) {
        return colLike(alias, column, value, matchMode, false);
    }

    /**
     * like表达式
     *
     * @param alias      表别名
     * @param column     字段名
     * @param value      值
     * @param matchMode  匹配模式
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLike(final String alias, final String column, final String value,
                                         final MatchMode matchMode, final boolean ignoreCase) {
        return colLike(alias, column, value, matchMode, null, ignoreCase);
    }

    /**
     * like表达式
     *
     * @param alias     表别名
     * @param column    字段名
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLike(final String alias, final String column, final String value,
                                         final MatchMode matchMode, final Character escape) {
        return colLike(alias, column, value, matchMode, escape, false);
    }

    /**
     * like表达式
     *
     * @param alias      表别名
     * @param column     字段名
     * @param value      值
     * @param matchMode  匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLike(final String alias, final String column, final String value,
                                         final MatchMode matchMode, final Character escape,
                                         final boolean ignoreCase) {
        return colLike(alias, column, value, matchMode, escape, ignoreCase, null);
    }

    /**
     * like表达式
     *
     * @param alias     表别名
     * @param column    字段名
     * @param value     值
     * @param matchMode 匹配模式
     * @param slot      {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLike(final String alias, final String column, final String value,
                                         final MatchMode matchMode, final LogicSymbol slot) {
        return colLike(alias, column, value, matchMode, false, slot);
    }

    /**
     * like表达式
     *
     * @param alias      表别名
     * @param column     字段名
     * @param value      值
     * @param matchMode  匹配模式
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLike(final String alias, final String column, final String value,
                                         final MatchMode matchMode, final boolean ignoreCase,
                                         final LogicSymbol slot) {
        return colLike(alias, column, value, matchMode, null, ignoreCase, slot);
    }

    /**
     * like表达式
     *
     * @param alias     表别名
     * @param column    字段名
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @param slot      {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLike(final String alias, final String column, final String value,
                                         final MatchMode matchMode, final Character escape, final LogicSymbol slot) {
        return colLike(alias, column, value, matchMode, escape, false, slot);
    }

    /**
     * like表达式
     *
     * @param alias      表别名
     * @param column     字段名
     * @param value      值
     * @param matchMode  匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colLike(final String alias, final String column, final String value,
                                         final MatchMode matchMode, final Character escape, final boolean ignoreCase,
                                         final LogicSymbol slot) {
        return likeExpression(null, alias, column, value, matchMode, escape, ignoreCase, Mode.COLUMN,
                Symbol.LIKE, slot);
    }

    // endregion

    // endregion

    // region Not like expression methods

    // region Not like left expression methods

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeLeft(final Criteria<T> criteria, final Property<T, String> property,
                                                 final String value) {
        return notLikeLeft(criteria, property, value, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeLeft(final Criteria<T> criteria, final Property<T, String> property,
                                                 final String value, final boolean ignoreCase) {
        return notLikeLeft(criteria, property, value, null, ignoreCase);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeLeft(final Criteria<T> criteria, final Property<T, String> property,
                                                 final String value, final Character escape) {
        return notLikeLeft(criteria, property, value, escape, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeLeft(final Criteria<T> criteria, final Property<T, String> property,
                                                 final String value, final Character escape, final boolean ignoreCase) {
        return notLikeLeft(criteria, property, value, escape, ignoreCase, null);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeLeft(final Criteria<T> criteria, final Property<T, String> property,
                                                 final String value, final LogicSymbol slot) {
        return notLikeLeft(criteria, property, value, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeLeft(final Criteria<T> criteria, final Property<T, String> property,
                                                 final String value, final boolean ignoreCase,
                                                 final LogicSymbol slot) {
        return notLikeLeft(criteria, property, value, null, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeLeft(final Criteria<T> criteria, final Property<T, String> property,
                                                 final String value, final Character escape, final LogicSymbol slot) {
        return notLikeLeft(criteria, property, value, escape, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeLeft(final Criteria<T> criteria, final Property<T, String> property,
                                                 final String value, final Character escape,
                                                 final boolean ignoreCase, final LogicSymbol slot) {
        return notLikeLeft(criteria, convert(property), value, escape, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeLeft(final Criteria<?> criteria, final String property, final String value) {
        return notLikeLeft(criteria, property, value, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeLeft(final Criteria<?> criteria, final String property, final String value,
                                             final boolean ignoreCase) {
        return notLikeLeft(criteria, property, value, null, ignoreCase);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param escape   转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeLeft(final Criteria<?> criteria, final String property, final String value,
                                             final Character escape) {
        return notLikeLeft(criteria, property, value, escape, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeLeft(final Criteria<?> criteria, final String property, final String value,
                                             final Character escape, final boolean ignoreCase) {
        return notLike(criteria, property, value, MatchMode.END, escape, ignoreCase);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeLeft(final Criteria<?> criteria, final String property, final String value,
                                             final LogicSymbol slot) {
        return notLikeLeft(criteria, property, value, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeLeft(final Criteria<?> criteria, final String property, final String value,
                                             final boolean ignoreCase, final LogicSymbol slot) {
        return notLikeLeft(criteria, property, value, null, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeLeft(final Criteria<?> criteria, final String property, final String value,
                                             final Character escape, final LogicSymbol slot) {
        return notLikeLeft(criteria, property, value, escape, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeLeft(final Criteria<?> criteria, final String property, final String value,
                                             final Character escape, final boolean ignoreCase,
                                             final LogicSymbol slot) {
        return notLike(criteria, property, value, MatchMode.END, escape, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeLeft(final Criteria<?> criteria, final String column, final String value) {
        return colNotLikeLeft(criteria, column, value, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeLeft(final Criteria<?> criteria, final String column, final String value,
                                                final boolean ignoreCase) {
        return colNotLikeLeft(criteria, column, value, null, ignoreCase);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param escape   转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeLeft(final Criteria<?> criteria, final String column, final String value,
                                                final Character escape) {
        return colNotLikeLeft(criteria, column, value, escape, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeLeft(final Criteria<?> criteria, final String column, final String value,
                                                final Character escape, final boolean ignoreCase) {
        return colNotLikeLeft(criteria, column, value, escape, ignoreCase, null);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeLeft(final Criteria<?> criteria, final String column, final String value,
                                                final LogicSymbol slot) {
        return colNotLikeLeft(criteria, column, value, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeLeft(final Criteria<?> criteria, final String column, final String value,
                                                final boolean ignoreCase, final LogicSymbol slot) {
        return colNotLikeLeft(criteria, column, value, null, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeLeft(final Criteria<?> criteria, final String column, final String value,
                                                final Character escape, final LogicSymbol slot) {
        return colNotLikeLeft(criteria, column, value, escape, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeLeft(final Criteria<?> criteria, final String column, final String value,
                                                final Character escape, final boolean ignoreCase,
                                                final LogicSymbol slot) {
        return colNotLike(criteria, column, value, MatchMode.END, escape, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeLeft(final String alias, final String column, final String value) {
        return colNotLikeLeft(alias, column, value, (Character) null);
    }

    /**
     * not like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeLeft(final String alias, final String column, final String value,
                                                final Character escape) {
        return colNotLikeLeft(alias, column, value, escape, null);
    }

    /**
     * not like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeLeft(final String alias, final String column, final String value,
                                                final LogicSymbol slot) {
        return colNotLikeLeft(alias, column, value, null, slot);
    }

    /**
     * not like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @param slot   {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeLeft(final String alias, final String column, final String value,
                                                final Character escape, final LogicSymbol slot) {
        return colNotLike(alias, column, value, MatchMode.END, escape, slot);
    }

    // endregion

    // region Not like right expression methods

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeRight(final Criteria<T> criteria, final Property<T, String> property,
                                                  final String value) {
        return notLikeRight(criteria, property, value, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeRight(final Criteria<T> criteria, final Property<T, String> property,
                                                  final String value, final boolean ignoreCase) {
        return notLikeRight(criteria, property, value, null, ignoreCase);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeRight(final Criteria<T> criteria, final Property<T, String> property,
                                                  final String value, final Character escape) {
        return notLikeRight(criteria, property, value, escape, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeRight(final Criteria<T> criteria, final Property<T, String> property,
                                                  final String value, final Character escape,
                                                  final boolean ignoreCase) {
        return notLikeRight(criteria, property, value, escape, ignoreCase, null);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeRight(final Criteria<T> criteria, final Property<T, String> property,
                                                  final String value, final LogicSymbol slot) {
        return notLikeRight(criteria, property, value, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeRight(final Criteria<T> criteria, final Property<T, String> property,
                                                  final String value, final boolean ignoreCase,
                                                  final LogicSymbol slot) {
        return notLikeRight(criteria, property, value, null, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeRight(final Criteria<T> criteria, final Property<T, String> property,
                                                  final String value, final Character escape, final LogicSymbol slot) {
        return notLikeRight(criteria, property, value, escape, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeRight(final Criteria<T> criteria, final Property<T, String> property,
                                                  final String value, final Character escape,
                                                  final boolean ignoreCase, final LogicSymbol slot) {
        return notLikeRight(criteria, convert(property), value, escape, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeRight(final Criteria<?> criteria, final String property, final String value) {
        return notLikeRight(criteria, property, value, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeRight(final Criteria<?> criteria, final String property, final String value,
                                              final boolean ignoreCase) {
        return notLikeRight(criteria, property, value, null, ignoreCase);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param escape   转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeRight(final Criteria<?> criteria, final String property, final String value,
                                              final Character escape) {
        return notLikeRight(criteria, property, value, escape, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeRight(final Criteria<?> criteria, final String property, final String value,
                                              final Character escape, final boolean ignoreCase) {
        return notLike(criteria, property, value, MatchMode.START, escape, ignoreCase);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeRight(final Criteria<?> criteria, final String property, final String value,
                                              final LogicSymbol slot) {
        return notLikeRight(criteria, property, value, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeRight(final Criteria<?> criteria, final String property, final String value,
                                              final boolean ignoreCase, final LogicSymbol slot) {
        return notLikeRight(criteria, property, value, null, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeRight(final Criteria<?> criteria, final String property, final String value,
                                              final Character escape, final LogicSymbol slot) {
        return notLikeRight(criteria, property, value, escape, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeRight(final Criteria<?> criteria, final String property, final String value,
                                              final Character escape, final boolean ignoreCase,
                                              final LogicSymbol slot) {
        return notLike(criteria, property, value, MatchMode.START, escape, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeRight(final Criteria<?> criteria, final String column, final String value) {
        return colNotLikeRight(criteria, column, value, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeRight(final Criteria<?> criteria, final String column, final String value,
                                                 final boolean ignoreCase) {
        return colNotLikeRight(criteria, column, value, null, ignoreCase);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param escape   转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeRight(final Criteria<?> criteria, final String column, final String value,
                                                 final Character escape) {
        return colNotLikeRight(criteria, column, value, escape, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeRight(final Criteria<?> criteria, final String column, final String value,
                                                 final Character escape, final boolean ignoreCase) {
        return colNotLikeRight(criteria, column, value, escape, ignoreCase, null);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeRight(final Criteria<?> criteria, final String column, final String value,
                                                 final LogicSymbol slot) {
        return colNotLikeRight(criteria, column, value, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeRight(final Criteria<?> criteria, final String column, final String value,
                                                 final boolean ignoreCase, final LogicSymbol slot) {
        return colNotLikeRight(criteria, column, value, null, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeRight(final Criteria<?> criteria, final String column, final String value,
                                                 final Character escape, final LogicSymbol slot) {
        return colNotLikeRight(criteria, column, value, escape, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeRight(final Criteria<?> criteria, final String column, final String value,
                                                 final Character escape, final boolean ignoreCase,
                                                 final LogicSymbol slot) {
        return colNotLike(criteria, column, value, MatchMode.START, escape, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeRight(final String alias, final String column, final String value) {
        return colNotLikeRight(alias, column, value, (Character) null);
    }

    /**
     * not like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeRight(final String alias, final String column, final String value,
                                                 final Character escape) {
        return colNotLikeRight(alias, column, value, escape, null);
    }

    /**
     * not like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeRight(final String alias, final String column, final String value,
                                                 final LogicSymbol slot) {
        return colNotLikeRight(alias, column, value, null, slot);
    }

    /**
     * not like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @param slot   {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeRight(final String alias, final String column, final String value,
                                                 final Character escape, final LogicSymbol slot) {
        return colNotLike(alias, column, value, MatchMode.START, escape, slot);
    }

    // endregion

    // region Not like any expression methods

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeAny(final Criteria<T> criteria, final Property<T, String> property,
                                                final String value) {
        return notLikeAny(criteria, property, value, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeAny(final Criteria<T> criteria, final Property<T, String> property,
                                                final String value, final boolean ignoreCase) {
        return notLikeAny(criteria, property, value, null, ignoreCase);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeAny(final Criteria<T> criteria, final Property<T, String> property,
                                                final String value, final Character escape) {
        return notLikeAny(criteria, property, value, escape, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeAny(final Criteria<T> criteria, final Property<T, String> property,
                                                final String value, final Character escape,
                                                final boolean ignoreCase) {
        return notLikeAny(criteria, property, value, escape, ignoreCase, null);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeAny(final Criteria<T> criteria, final Property<T, String> property,
                                                final String value, final LogicSymbol slot) {
        return notLikeAny(criteria, property, value, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeAny(final Criteria<T> criteria, final Property<T, String> property,
                                                final String value, final boolean ignoreCase,
                                                final LogicSymbol slot) {
        return notLikeAny(criteria, property, value, null, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeAny(final Criteria<T> criteria, final Property<T, String> property,
                                                final String value, final Character escape, final LogicSymbol slot) {
        return notLikeAny(criteria, property, value, escape, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLikeAny(final Criteria<T> criteria, final Property<T, String> property,
                                                final String value, final Character escape,
                                                final boolean ignoreCase, final LogicSymbol slot) {
        return notLikeAny(criteria, convert(property), value, escape, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeAny(final Criteria<?> criteria, final String property, final String value) {
        return notLikeAny(criteria, property, value, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeAny(final Criteria<?> criteria, final String property, final String value,
                                            final boolean ignoreCase) {
        return notLikeAny(criteria, property, value, null, ignoreCase);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param escape   转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeAny(final Criteria<?> criteria, final String property, final String value,
                                            final Character escape) {
        return notLikeAny(criteria, property, value, escape, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeAny(final Criteria<?> criteria, final String property, final String value,
                                            final Character escape, final boolean ignoreCase) {
        return notLike(criteria, property, value, MatchMode.START, escape, ignoreCase);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeAny(final Criteria<?> criteria, final String property, final String value,
                                            final LogicSymbol slot) {
        return notLikeAny(criteria, property, value, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeAny(final Criteria<?> criteria, final String property, final String value,
                                            final boolean ignoreCase, final LogicSymbol slot) {
        return notLikeAny(criteria, property, value, null, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性名
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeAny(final Criteria<?> criteria, final String property, final String value,
                                            final Character escape, final LogicSymbol slot) {
        return notLikeAny(criteria, property, value, escape, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLikeAny(final Criteria<?> criteria, final String property, final String value,
                                            final Character escape, final boolean ignoreCase,
                                            final LogicSymbol slot) {
        return notLike(criteria, property, value, MatchMode.ANYWHERE, escape, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeAny(final Criteria<?> criteria, final String column, final String value) {
        return colNotLikeAny(criteria, column, value, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeAny(final Criteria<?> criteria, final String column, final String value,
                                               final boolean ignoreCase) {
        return colNotLikeAny(criteria, column, value, null, ignoreCase);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param escape   转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeAny(final Criteria<?> criteria, final String column, final String value,
                                               final Character escape) {
        return colNotLikeAny(criteria, column, value, escape, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeAny(final Criteria<?> criteria, final String column, final String value,
                                               final Character escape, final boolean ignoreCase) {
        return colNotLikeAny(criteria, column, value, escape, ignoreCase, null);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeAny(final Criteria<?> criteria, final String column, final String value,
                                               final LogicSymbol slot) {
        return colNotLikeAny(criteria, column, value, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeAny(final Criteria<?> criteria, final String column, final String value,
                                               final boolean ignoreCase, final LogicSymbol slot) {
        return colNotLikeAny(criteria, column, value, null, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeAny(final Criteria<?> criteria, final String column, final String value,
                                               final Character escape, final LogicSymbol slot) {
        return colNotLikeAny(criteria, column, value, escape, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeAny(final Criteria<?> criteria, final String column, final String value,
                                               final Character escape, final boolean ignoreCase,
                                               final LogicSymbol slot) {
        return colNotLike(criteria, column, value, MatchMode.ANYWHERE, escape, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeAny(final String alias, final String column, final String value) {
        return colNotLikeAny(alias, column, value, (Character) null);
    }

    /**
     * not like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeAny(final String alias, final String column, final String value,
                                               final Character escape) {
        return colNotLikeAny(alias, column, value, escape, null);
    }

    /**
     * not like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeAny(final String alias, final String column, final String value,
                                               final LogicSymbol slot) {
        return colNotLikeAny(alias, column, value, null, slot);
    }

    /**
     * not like表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @param slot   {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLikeAny(final String alias, final String column, final String value,
                                               final Character escape, final LogicSymbol slot) {
        return colNotLike(alias, column, value, MatchMode.ANYWHERE, escape, slot);
    }

    // endregion

    // region Not like base expression methods

    /**
     * not like表达式
     *
     * @param criteria  {@link Criteria}
     * @param property  {@link Property}
     * @param value     值
     * @param matchMode 匹配模式
     * @param <T>       实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLike(final Criteria<T> criteria, final Property<T, String> property,
                                             final String value, final MatchMode matchMode) {
        return notLike(criteria, property, value, matchMode, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param matchMode  匹配模式
     * @param ignoreCase 是否忽略大小写
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLike(final Criteria<T> criteria, final Property<T, String> property,
                                             final String value, final MatchMode matchMode,
                                             final boolean ignoreCase) {
        return notLike(criteria, property, value, matchMode, null, ignoreCase);
    }

    /**
     * not like表达式
     *
     * @param criteria  {@link Criteria}
     * @param property  {@link Property}
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @param <T>       实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLike(final Criteria<T> criteria, final Property<T, String> property,
                                             final String value, final MatchMode matchMode, final Character escape) {
        return notLike(criteria, property, value, matchMode, escape, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param matchMode  匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLike(final Criteria<T> criteria, final Property<T, String> property,
                                             final String value, final MatchMode matchMode, final Character escape,
                                             final boolean ignoreCase) {
        return notLike(criteria, property, value, matchMode, escape, ignoreCase, null);
    }

    /**
     * not like表达式
     *
     * @param criteria  {@link Criteria}
     * @param property  {@link Property}
     * @param value     值
     * @param matchMode 匹配模式
     * @param slot      {@link LogicSymbol}
     * @param <T>       实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLike(final Criteria<T> criteria, final Property<T, String> property,
                                             final String value, final MatchMode matchMode, final LogicSymbol slot) {
        return notLike(criteria, property, value, matchMode, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param matchMode  匹配模式
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLike(final Criteria<T> criteria, final Property<T, String> property,
                                             final String value, final MatchMode matchMode,
                                             final boolean ignoreCase, final LogicSymbol slot) {
        return notLike(criteria, property, value, matchMode, null, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria  {@link Criteria}
     * @param property  {@link Property}
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @param slot      {@link LogicSymbol}
     * @param <T>       实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLike(final Criteria<T> criteria, final Property<T, String> property,
                                             final String value, final MatchMode matchMode, final Character escape,
                                             final LogicSymbol slot) {
        return notLike(criteria, property, value, matchMode, escape, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   {@link Property}
     * @param value      值
     * @param matchMode  匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @param <T>        实体类型
     * @return {@link LikeExpression}
     */
    public static <T> LikeExpression notLike(final Criteria<T> criteria, final Property<T, String> property,
                                             final String value, final MatchMode matchMode, final Character escape,
                                             final boolean ignoreCase, final LogicSymbol slot) {
        return notLike(criteria, convert(property), value, matchMode, escape, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria  {@link Criteria}
     * @param property  属性名
     * @param value     值
     * @param matchMode 匹配模式
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLike(final Criteria<?> criteria, final String property, final String value,
                                         final MatchMode matchMode) {
        return notLike(criteria, property, value, matchMode, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param matchMode  匹配模式
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLike(final Criteria<?> criteria, final String property, final String value,
                                         final MatchMode matchMode, final boolean ignoreCase) {
        return notLike(criteria, property, value, matchMode, null, ignoreCase);
    }

    /**
     * not like表达式
     *
     * @param criteria  {@link Criteria}
     * @param property  属性名
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLike(final Criteria<?> criteria, final String property, final String value,
                                         final MatchMode matchMode, final Character escape) {
        return notLike(criteria, property, value, matchMode, escape, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param matchMode  匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLike(final Criteria<?> criteria, final String property, final String value,
                                         final MatchMode matchMode, final Character escape, final boolean ignoreCase) {
        return notLike(criteria, property, value, matchMode, escape, ignoreCase, null);
    }

    /**
     * not like表达式
     *
     * @param criteria  {@link Criteria}
     * @param property  属性名
     * @param value     值
     * @param matchMode 匹配模式
     * @param slot      {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLike(final Criteria<?> criteria, final String property, final String value,
                                         final MatchMode matchMode, final LogicSymbol slot) {
        return notLike(criteria, property, value, matchMode, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param matchMode  匹配模式
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLike(final Criteria<?> criteria, final String property, final String value,
                                         final MatchMode matchMode, final boolean ignoreCase,
                                         final LogicSymbol slot) {
        return notLike(criteria, property, value, matchMode, null, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria  {@link Criteria}
     * @param property  属性名
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @param slot      {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLike(final Criteria<?> criteria, final String property, final String value,
                                         final MatchMode matchMode, final Character escape, final LogicSymbol slot) {
        return notLike(criteria, property, value, matchMode, escape, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param property   属性名
     * @param value      值
     * @param matchMode  匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression notLike(final Criteria<?> criteria, final String property, final String value,
                                         final MatchMode matchMode, final Character escape, final boolean ignoreCase,
                                         final LogicSymbol slot) {
        return likeExpression(criteria, null, property, value, matchMode, escape, ignoreCase, Mode.PROPERTY,
                Symbol.NOT_LIKE, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria  {@link Criteria}
     * @param column    字段名
     * @param value     值
     * @param matchMode 匹配模式
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLike(final Criteria<?> criteria, final String column, final String value,
                                            final MatchMode matchMode) {
        return colNotLike(criteria, column, value, matchMode, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param matchMode  匹配模式
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLike(final Criteria<?> criteria, final String column, final String value,
                                            final MatchMode matchMode, final boolean ignoreCase) {
        return colNotLike(criteria, column, value, matchMode, null, ignoreCase);
    }

    /**
     * not like表达式
     *
     * @param criteria  {@link Criteria}
     * @param column    字段名
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLike(final Criteria<?> criteria, final String column, final String value,
                                            final MatchMode matchMode, final Character escape) {
        return colNotLike(criteria, column, value, matchMode, escape, false);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param matchMode  匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLike(final Criteria<?> criteria, final String column, final String value,
                                            final MatchMode matchMode, final Character escape,
                                            final boolean ignoreCase) {
        return colNotLike(criteria, column, value, matchMode, escape, ignoreCase, null);
    }

    /**
     * not like表达式
     *
     * @param criteria  {@link Criteria}
     * @param column    字段名
     * @param value     值
     * @param matchMode 匹配模式
     * @param slot      {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLike(final Criteria<?> criteria, final String column, final String value,
                                            final MatchMode matchMode, final LogicSymbol slot) {
        return colNotLike(criteria, column, value, matchMode, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param matchMode  匹配模式
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLike(final Criteria<?> criteria, final String column, final String value,
                                            final MatchMode matchMode, final boolean ignoreCase,
                                            final LogicSymbol slot) {
        return colNotLike(criteria, column, value, matchMode, null, ignoreCase, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria  {@link Criteria}
     * @param column    字段名
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @param slot      {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLike(final Criteria<?> criteria, final String column, final String value,
                                            final MatchMode matchMode, final Character escape, final LogicSymbol slot) {
        return colNotLike(criteria, column, value, matchMode, escape, false, slot);
    }

    /**
     * not like表达式
     *
     * @param criteria   {@link Criteria}
     * @param column     字段名
     * @param value      值
     * @param matchMode  匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLike(final Criteria<?> criteria, final String column, final String value,
                                            final MatchMode matchMode, final Character escape, final boolean ignoreCase,
                                            final LogicSymbol slot) {
        return likeExpression(criteria, null, column, value, matchMode, escape, ignoreCase, Mode.COLUMN,
                Symbol.NOT_LIKE, slot);
    }

    /**
     * not like表达式
     *
     * @param alias     表别名
     * @param column    字段名
     * @param value     值
     * @param matchMode 匹配模式
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLike(final String alias, final String column, final String value,
                                            final MatchMode matchMode) {
        return colNotLike(alias, column, value, matchMode, (Character) null);
    }

    /**
     * not like表达式
     *
     * @param alias     表别名
     * @param column    字段名
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLike(final String alias, final String column, final String value,
                                            final MatchMode matchMode, final Character escape) {
        return colNotLike(alias, column, value, matchMode, escape, null);
    }

    /**
     * not like表达式
     *
     * @param alias     表别名
     * @param column    字段名
     * @param value     值
     * @param matchMode 匹配模式
     * @param slot      {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLike(final String alias, final String column, final String value,
                                            final MatchMode matchMode, final LogicSymbol slot) {
        return colNotLike(alias, column, value, matchMode, null, slot);
    }

    /**
     * not like表达式
     *
     * @param alias     表别名
     * @param column    字段名
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @param slot      {@link LogicSymbol}
     * @return {@link LikeExpression}
     */
    public static LikeExpression colNotLike(final String alias, final String column, final String value,
                                            final MatchMode matchMode, final Character escape,
                                            final LogicSymbol slot) {
        return likeExpression(null, alias, column, value, matchMode, escape, false, Mode.COLUMN,
                Symbol.NOT_LIKE, slot);
    }

    // endregion

    // endregion

    // region Is null expression methods

    /**
     * is null表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param <T>      实体类型
     * @return {@link NullExpression}
     */
    public static <T> NullExpression isNull(final Criteria<T> criteria, final Property<T, ?> property) {
        return isNull(criteria, property, null);
    }

    /**
     * is null表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link NullExpression}
     */
    public static <T> NullExpression isNull(final Criteria<T> criteria, final Property<T, ?> property,
                                            final LogicSymbol slot) {
        return isNull(criteria, convert(property), slot);
    }

    /**
     * is null表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @return {@link NullExpression}
     */
    public static NullExpression isNull(final Criteria<?> criteria, final String property) {
        return isNull(criteria, property, null);
    }

    /**
     * is null表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param slot     {@link LogicSymbol}
     * @return {@link NullExpression}
     */
    public static NullExpression isNull(final Criteria<?> criteria, final String property, final LogicSymbol slot) {
        return nullExpression(criteria, property, property, Mode.PROPERTY, Symbol.NULL, slot);
    }

    /**
     * is null表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @return {@link NullExpression}
     */
    public static NullExpression colIsNull(final Criteria<?> criteria, final String column) {
        return colIsNull(criteria, column, null);
    }

    /**
     * is null表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param slot     {@link LogicSymbol}
     * @return {@link NullExpression}
     */
    public static NullExpression colIsNull(final Criteria<?> criteria, final String column, final LogicSymbol slot) {
        return nullExpression(criteria, null, column, Mode.COLUMN, Symbol.NULL, slot);
    }

    /**
     * is null表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @return {@link NullExpression}
     */
    public static NullExpression colIsNull(final String alias, final String column) {
        return colIsNull(alias, column, null);
    }

    /**
     * is null表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param slot   {@link LogicSymbol}
     * @return {@link NullExpression}
     */
    public static NullExpression colIsNull(final String alias, final String column, final LogicSymbol slot) {
        return nullExpression(null, alias, column, Mode.COLUMN, Symbol.NULL, slot);
    }

    // endregion

    // region Not null expression methods

    /**
     * not null表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param <T>      实体类型
     * @return {@link NullExpression}
     */
    public static <T> NullExpression nonNull(final Criteria<T> criteria, final Property<T, ?> property) {
        return nonNull(criteria, property, null);
    }

    /**
     * not null表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param slot     {@link LogicSymbol}
     * @param <T>      实体类型
     * @return {@link NullExpression}
     */
    public static <T> NullExpression nonNull(final Criteria<T> criteria, final Property<T, ?> property,
                                             final LogicSymbol slot) {
        return nonNull(criteria, convert(property), slot);
    }

    /**
     * not null表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @return {@link NullExpression}
     */
    public static NullExpression nonNull(final Criteria<?> criteria, final String property) {
        return nonNull(criteria, property, null);
    }

    /**
     * not null表达式
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @param slot     {@link LogicSymbol}
     * @return {@link NullExpression}
     */
    public static NullExpression nonNull(final Criteria<?> criteria, final String property, final LogicSymbol slot) {
        return nullExpression(criteria, property, property, Mode.PROPERTY, Symbol.NOT_NULL, slot);
    }

    /**
     * is null表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @return {@link NullExpression}
     */
    public static NullExpression colNonNull(final Criteria<?> criteria, final String column) {
        return colNonNull(criteria, column, null);
    }

    /**
     * not null表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param slot     {@link LogicSymbol}
     * @return {@link NullExpression}
     */
    public static NullExpression colNonNull(final Criteria<?> criteria, final String column, final LogicSymbol slot) {
        return nullExpression(criteria, null, column, Mode.COLUMN, Symbol.NOT_NULL, slot);
    }

    /**
     * not null表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @return {@link NullExpression}
     */
    public static NullExpression colNonNull(final String alias, final String column) {
        return colNonNull(alias, column, null);
    }

    /**
     * not null表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param slot   {@link LogicSymbol}
     * @return {@link NullExpression}
     */
    public static NullExpression colNonNull(final String alias, final String column, final LogicSymbol slot) {
        return nullExpression(null, alias, column, Mode.COLUMN, Symbol.NOT_NULL, slot);
    }

    // endregion

    // region Nested expression methods

    /**
     * and嵌套表达式
     *
     * @param expressions {@link Expression}列表
     * @return {@link NestedExpression}
     */
    public static NestedExpression and(final Expression... expressions) {
        return and(false, expressions);
    }

    /**
     * and嵌套表达式
     *
     * @param expressions {@link Expression}列表
     * @return {@link NestedExpression}
     */
    public static NestedExpression and(final List<Expression> expressions) {
        return and(false, expressions);
    }

    /**
     * and嵌套表达式
     *
     * @param not         是否拼接not
     * @param expressions {@link Expression}列表
     * @return {@link NestedExpression}
     */
    public static NestedExpression and(final boolean not, final Expression... expressions) {
        return and(not, Arrays.asList(expressions));
    }

    /**
     * and嵌套表达式
     *
     * @param not         是否拼接not
     * @param expressions {@link Expression}列表
     * @return {@link NestedExpression}
     */
    public static NestedExpression and(final boolean not, final List<Expression> expressions) {
        return nested(not, expressions, LogicSymbol.AND);
    }

    /**
     * or嵌套表达式
     *
     * @param expressions {@link Expression}列表
     * @return {@link NestedExpression}
     */
    public static NestedExpression or(final Expression... expressions) {
        return or(false, expressions);
    }

    /**
     * or嵌套表达式
     *
     * @param expressions {@link Expression}列表
     * @return {@link NestedExpression}
     */
    public static NestedExpression or(final List<Expression> expressions) {
        return or(false, expressions);
    }

    /**
     * or嵌套表达式
     *
     * @param not         是否拼接not
     * @param expressions {@link Expression}列表
     * @return {@link NestedExpression}
     */
    public static NestedExpression or(final boolean not, final Expression... expressions) {
        return or(not, Arrays.asList(expressions));
    }

    /**
     * or嵌套表达式
     *
     * @param not         是否拼接not
     * @param expressions {@link Expression}列表
     * @return {@link NestedExpression}
     */
    public static NestedExpression or(final boolean not, final List<Expression> expressions) {
        return nested(not, expressions, LogicSymbol.OR);
    }

    /**
     * 嵌套表达式
     *
     * @param not         是否拼接not
     * @param expressions {@link Expression}列表
     * @param slot        {@link LogicSymbol}
     * @return {@link NestedExpression}
     */
    public static NestedExpression nested(final boolean not, final List<Expression> expressions,
                                          final LogicSymbol slot) {
        return NestedExpression.builder().not(not).slot(slot).expressions(expressions).build();
    }

    // endregion

}
