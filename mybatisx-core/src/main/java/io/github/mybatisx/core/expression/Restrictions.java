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
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.base.helper.TableHelper;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.core.param.SingleParam;
import io.github.mybatisx.core.property.LambdaMetadataWeakCache;
import io.github.mybatisx.core.property.Property;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.matcher.Matcher;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 条件表达式工具
 *
 * @author wvkity
 * @created 2022/1/12
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Restrictions {

    /**
     * 模式
     */
    private enum Mode {
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
    private static <V> boolean early(final V value, final Matcher<V> matcher) {
        return matcher == null || matcher.matches(value);
    }

    /**
     * 获取{@link LogicSymbol}
     *
     * @param criteria {@link Criteria}
     * @return {@link LogicSymbol}
     */
    private static LogicSymbol slot(final Criteria<?> criteria) {
        return criteria == null ? LogicSymbol.AND : criteria.slot();
    }

    /**
     * Lambda属性转字符串属性
     *
     * @param property {@link Property}
     * @param <T>      实体类型
     * @return 字符串属性
     */
    private static <T> String convert(final Property<T, ?> property) {
        return LambdaMetadataWeakCache.getProperty(property);
    }

    /**
     * 获取{@link Column}
     *
     * @param criteria {@link Criteria}
     * @param property 属性
     * @return {@link Column}
     */
    private static Column getColumn(final Criteria<?> criteria, final String property) {
        return TableHelper.getColumnByProperty(criteria.getEntity(), property, criteria.isStrict(), true);
    }

    /**
     * 构建{@link SingleExpression}
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
     * @return {@link SingleExpression}
     */
    private static <V> SingleExpression singleExpressionBuild(final Criteria<?> criteria, final String alias,
                                                              final String target, final V value,
                                                              final Matcher<V> matcher, final Mode mode,
                                                              final Symbol symbol, final LogicSymbol slot) {
        if (early(value, matcher)) {
            if (mode == Mode.PROPERTY) {
                final Column column = getColumn(criteria, target);
                if (column != null) {
                    return SingleExpression.builder().column(column.getColumn())
                            .criteria(criteria)
                            .param(SingleParam.builder()
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
                    return SingleExpression.builder()
                            .column(target)
                            .criteria(criteria)
                            .alias(alias)
                            .param(SingleParam.builder()
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

    // region Equal expression methods

    /**
     * 等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression eq(final Criteria<T> criteria, final Property<T, V> property, final V value) {
        return eq(criteria, property, value, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression eq(final Criteria<T> criteria, final Property<T, V> property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression eq(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher) {
        return eq(criteria, property, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression eq(final Criteria<T> criteria, final Property<T, V> property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression eq(final Criteria<T> criteria, final String property, final V value) {
        return eq(criteria, property, value, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression eq(final Criteria<T> criteria, final String property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression eq(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher) {
        return eq(criteria, property, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression eq(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(criteria, null, property, value, matcher, Mode.PROPERTY, Symbol.EQ, slot);
    }

    /**
     * 等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param <V>      值类型
     * @return {@link SingleExpression}
     */
    public static <V> SingleExpression colEq(final Criteria<?> criteria, final String column, final V value) {
        return colEq(criteria, column, value, slot(criteria));
    }

    /**
     * 等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@link SingleExpression}
     */
    public static <V> SingleExpression colEq(final Criteria<?> criteria, final String column, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <V> SingleExpression colEq(final Criteria<?> criteria, final String column, final V value,
                                             final Matcher<V> matcher) {
        return colEq(criteria, column, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <V> SingleExpression colEq(final Criteria<?> criteria, final String column, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(criteria, null, column, value, matcher, Mode.COLUMN, Symbol.EQ, slot);
    }

    /**
     * 等于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param <V>    值类型
     * @return {@link SingleExpression}
     */
    public static <V> SingleExpression colEq(final String alias, final String column, final V value) {
        return colEq(alias, column, value, null, LogicSymbol.AND);
    }

    /**
     * 等于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@link SingleExpression}
     */
    public static <V> SingleExpression colEq(final String alias, final String column, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <V> SingleExpression colEq(final String alias, final String column, final V value,
                                             final Matcher<V> matcher) {
        return colEq(alias, column, value, matcher, LogicSymbol.AND);
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
     * @return {@link SingleExpression}
     */
    public static <V> SingleExpression colEq(final String alias, final String column, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(null, alias, column, value, matcher, Mode.COLUMN, Symbol.EQ, slot);
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression ne(final Criteria<T> criteria, final Property<T, V> property, final V value) {
        return ne(criteria, property, value, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression ne(final Criteria<T> criteria, final Property<T, V> property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression ne(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher) {
        return ne(criteria, property, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression ne(final Criteria<T> criteria, final Property<T, V> property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression ne(final Criteria<T> criteria, final String property, final V value) {
        return ne(criteria, property, value, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression ne(final Criteria<T> criteria, final String property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression ne(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher) {
        return ne(criteria, property, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression ne(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(criteria, null, property, value, matcher, Mode.PROPERTY, Symbol.NE, slot);
    }

    /**
     * 不等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colNe(final Criteria<T> criteria, final String column, final V value) {
        return colNe(criteria, column, value, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colNe(final Criteria<T> criteria, final String column, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colNe(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colNe(criteria, column, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colNe(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(criteria, null, column, value, matcher, Mode.COLUMN, Symbol.NE, slot);
    }

    /**
     * 不等于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param <T>    实体类型
     * @param <V>    值类型
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colNe(final String alias, final String column, final V value) {
        return colNe(alias, column, value, LogicSymbol.AND);
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colNe(final String alias, final String column, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colNe(final String alias, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colNe(alias, column, value, matcher, LogicSymbol.AND);
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colNe(final String alias, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(null, alias, column, value, matcher, Mode.COLUMN, Symbol.NE, slot);
    }

    // endregion

    // region Greater than methods

    /**
     * 大于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression gt(final Criteria<T> criteria, final Property<T, V> property, final V value) {
        return gt(criteria, property, value, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression gt(final Criteria<T> criteria, final Property<T, V> property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression gt(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher) {
        return gt(criteria, property, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression gt(final Criteria<T> criteria, final Property<T, V> property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression gt(final Criteria<T> criteria, final String property, final V value) {
        return gt(criteria, property, value, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression gt(final Criteria<T> criteria, final String property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression gt(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher) {
        return gt(criteria, property, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression gt(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(criteria, null, property, value, matcher, Mode.PROPERTY, Symbol.GT, slot);
    }

    /**
     * 大于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colGt(final Criteria<T> criteria, final String column, final V value) {
        return colGt(criteria, column, value, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colGt(final Criteria<T> criteria, final String column, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colGt(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colGt(criteria, column, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colGt(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(criteria, null, column, value, matcher, Mode.COLUMN, Symbol.GT, slot);
    }

    /**
     * 大于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param <T>    实体类型
     * @param <V>    值类型
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colGt(final String alias, final String column, final V value) {
        return colGt(alias, column, value, LogicSymbol.AND);
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colGt(final String alias, final String column, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colGt(final String alias, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colGt(alias, column, value, matcher, LogicSymbol.AND);
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colGt(final String alias, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(null, alias, column, value, matcher, Mode.COLUMN, Symbol.GT, slot);
    }

    // endregion

    // region Greater than or equal methods

    /**
     * 大于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression ge(final Criteria<T> criteria, final Property<T, V> property, final V value) {
        return ge(criteria, property, value, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression ge(final Criteria<T> criteria, final Property<T, V> property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression ge(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher) {
        return ge(criteria, property, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression ge(final Criteria<T> criteria, final Property<T, V> property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression ge(final Criteria<T> criteria, final String property, final V value) {
        return ge(criteria, property, value, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression ge(final Criteria<T> criteria, final String property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression ge(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher) {
        return ge(criteria, property, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression ge(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(criteria, null, property, value, matcher, Mode.PROPERTY, Symbol.GE, slot);
    }

    /**
     * 大于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colGe(final Criteria<T> criteria, final String column, final V value) {
        return colGe(criteria, column, value, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colGe(final Criteria<T> criteria, final String column, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colGe(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colGe(criteria, column, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colGe(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(criteria, null, column, value, matcher, Mode.COLUMN, Symbol.GE, slot);
    }

    /**
     * 大于或等于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param <T>    实体类型
     * @param <V>    值类型
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colGe(final String alias, final String column, final V value) {
        return colGe(alias, column, value, LogicSymbol.AND);
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colGe(final String alias, final String column, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colGe(final String alias, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colGe(alias, column, value, matcher, LogicSymbol.AND);
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colGe(final String alias, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(null, alias, column, value, matcher, Mode.COLUMN, Symbol.GE, slot);
    }

    // endregion

    // region Less than methods

    /**
     * 小于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression lt(final Criteria<T> criteria, final Property<T, V> property, final V value) {
        return lt(criteria, property, value, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression lt(final Criteria<T> criteria, final Property<T, V> property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression lt(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher) {
        return lt(criteria, property, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression lt(final Criteria<T> criteria, final Property<T, V> property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression lt(final Criteria<T> criteria, final String property, final V value) {
        return lt(criteria, property, value, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression lt(final Criteria<T> criteria, final String property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression lt(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher) {
        return lt(criteria, property, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression lt(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(criteria, null, property, value, matcher, Mode.PROPERTY, Symbol.LT, slot);
    }

    /**
     * 小于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colLt(final Criteria<T> criteria, final String column, final V value) {
        return colLt(criteria, column, value, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colLt(final Criteria<T> criteria, final String column, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colLt(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colLt(criteria, column, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colLt(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(criteria, null, column, value, matcher, Mode.COLUMN, Symbol.LT, slot);
    }

    /**
     * 小于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param <T>    实体类型
     * @param <V>    值类型
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colLt(final String alias, final String column, final V value) {
        return colLt(alias, column, value, LogicSymbol.AND);
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colLt(final String alias, final String column, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colLt(final String alias, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colLt(alias, column, value, matcher, LogicSymbol.AND);
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colLt(final String alias, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(null, alias, column, value, matcher, Mode.COLUMN, Symbol.LT, slot);
    }

    // endregion

    // region Less than or equal methods

    /**
     * 小于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param property {@link Property}
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression le(final Criteria<T> criteria, final Property<T, V> property, final V value) {
        return le(criteria, property, value, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression le(final Criteria<T> criteria, final Property<T, V> property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression le(final Criteria<T> criteria, final Property<T, V> property, final V value,
                                             final Matcher<V> matcher) {
        return le(criteria, property, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression le(final Criteria<T> criteria, final Property<T, V> property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression le(final Criteria<T> criteria, final String property, final V value) {
        return le(criteria, property, value, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression le(final Criteria<T> criteria, final String property, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression le(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher) {
        return le(criteria, property, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression le(final Criteria<T> criteria, final String property, final V value,
                                             final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(criteria, null, property, value, matcher, Mode.PROPERTY, Symbol.LE, slot);
    }

    /**
     * 小于或等于表达式
     *
     * @param criteria {@link Criteria}
     * @param column   字段名
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colLe(final Criteria<T> criteria, final String column, final V value) {
        return colLe(criteria, column, value, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colLe(final Criteria<T> criteria, final String column, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colLe(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colLe(criteria, column, value, matcher, slot(criteria));
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colLe(final Criteria<T> criteria, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(criteria, null, column, value, matcher, Mode.COLUMN, Symbol.LE, slot);
    }

    /**
     * 小于或等于表达式
     *
     * @param alias  表别名
     * @param column 字段名
     * @param value  值
     * @param <T>    实体类型
     * @param <V>    值类型
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colLe(final String alias, final String column, final V value) {
        return colLe(alias, column, value, LogicSymbol.AND);
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colLe(final String alias, final String column, final V value,
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colLe(final String alias, final String column, final V value,
                                                final Matcher<V> matcher) {
        return colLe(alias, column, value, matcher, LogicSymbol.AND);
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
     * @return {@link SingleExpression}
     */
    public static <T, V> SingleExpression colLe(final String alias, final String column, final V value,
                                                final Matcher<V> matcher, final LogicSymbol slot) {
        return singleExpressionBuild(null, alias, column, value, matcher, Mode.COLUMN, Symbol.LE, slot);
    }

    // endregion

}
