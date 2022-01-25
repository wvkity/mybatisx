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
package io.github.mybatisx.core.criteria;

import io.github.mybatisx.base.constant.LikeMatchMode;
import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.base.constant.ParamMode;
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.criterion.Criterion;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.core.criterion.NestedCondition;
import io.github.mybatisx.core.expression.Expression;
import io.github.mybatisx.core.expression.NestedExpression;
import io.github.mybatisx.core.param.BetweenParam;
import io.github.mybatisx.core.param.InParam;
import io.github.mybatisx.core.param.LikeParam;
import io.github.mybatisx.core.param.NullParam;
import io.github.mybatisx.core.param.SimpleParam;
import io.github.mybatisx.core.param.TemplateParam;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.matcher.Matcher;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 抽象条件处理
 *
 * @author wvkity
 * @created 2022/1/9
 * @since 1.0.0
 */
@SuppressWarnings({"serial"})
public abstract class AbstractConditionAcceptSupport<T, C extends CriteriaWrapper<T, C>> extends
        AbstractBaseCriteria<T> implements CriteriaWrapper<T, C> {

    /**
     * 当前对象
     */
    @SuppressWarnings("unchecked")
    protected final C context = (C) this;

    // region Protected methods

    /**
     * 创建{@link CriteriaWrapper}实例
     *
     * @return {@link CriteriaWrapper}对象
     */
    protected C newInstance() {
        return null;
    }

    /**
     * 添加单值条件
     *
     * @param property 属性名
     * @param value    值
     * @param matcher  匹配器
     * @param symbol   {@link Symbol}
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    protected <V> C simpleConditionAccept(final String property, final V value, final Matcher<V> matcher,
                                          final Symbol symbol, final LogicSymbol slot) {
        return this.simpleConditionAccept(this.convert(property), value, matcher, symbol, slot);
    }

    /**
     * 添加单值条件
     *
     * @param column  {@link Column}
     * @param value   值
     * @param matcher 匹配器
     * @param symbol  {@link Symbol}
     * @param slot    {@link LogicSymbol}
     * @param <V>     值类型
     * @return {@code this}
     */
    protected <V> C simpleConditionAccept(final Column column, final V value, final Matcher<V> matcher,
                                          final Symbol symbol, final LogicSymbol slot) {
        if (column != null && this.early(value, matcher)) {
            this.conditionConverter.accept(column.getColumn(), SimpleParam.builder()
                    .symbol(symbol)
                    .slot(slot)
                    .typeHandler(column.getTypeHandler())
                    .jdbcType(column.getJdbcType())
                    .javaType(column.getDescriptor().getJavaType())
                    .value(value)
                    .build());
        }
        return this.context;
    }

    /**
     * 添加between条件
     *
     * @param property 属性名
     * @param begin    开始值
     * @param end      结束值
     * @param symbol   {@link Symbol}
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    protected <V> C betweenConditionAccept(final String property, final V begin, final V end,
                                           final Symbol symbol, final LogicSymbol slot) {
        return this.betweenConditionAccept(this.convert(property), begin, end, symbol, slot);
    }

    /**
     * 添加between范围条件
     *
     * @param column {@link Column}
     * @param begin  开始值
     * @param end    结束值
     * @param symbol {@link Symbol}
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@code this}
     */
    protected <V> C betweenConditionAccept(final Column column, final V begin, final V end,
                                           final Symbol symbol, final LogicSymbol slot) {
        if (column != null) {
            this.conditionConverter.accept(column.getColumn(), BetweenParam.builder()
                    .symbol(symbol)
                    .slot(slot)
                    .typeHandler(column.getTypeHandler())
                    .jdbcType(column.getJdbcType())
                    .javaType(column.getDescriptor().getJavaType())
                    .begin(begin)
                    .end(end)
                    .build());
        }
        return this.context;
    }

    /**
     * 添加in范围条件
     *
     * @param property 属性名
     * @param values   值列表
     * @param symbol   {@link Symbol}
     * @param slot     {@link LogicSymbol}
     * @param <V>      值类型
     * @return {@code this}
     */
    protected <V> C inConditionAccept(final String property, final Collection<V> values, final Symbol symbol,
                                      final LogicSymbol slot) {
        return this.inConditionAccept(this.convert(property), values, symbol, slot);
    }

    /**
     * 添加in范围条件
     *
     * @param column {@link Column}
     * @param values 值列表
     * @param symbol {@link Symbol}
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@code this}
     */
    protected <V> C inConditionAccept(final Column column, final Collection<V> values, final Symbol symbol,
                                      final LogicSymbol slot) {
        if (column != null) {
            this.conditionConverter.accept(column.getColumn(), InParam.builder()
                    .symbol(symbol)
                    .slot(slot)
                    .typeHandler(column.getTypeHandler())
                    .jdbcType(column.getJdbcType())
                    .javaType(column.getDescriptor().getJavaType())
                    .values(values)
                    .build());
        }
        return this.context;
    }

    /**
     * 添加like模糊匹配条件
     *
     * @param property   属性
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param symbol     {@link Symbol}
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    protected C likeConditionAccept(final String property, final String value, final LikeMatchMode matches,
                                    final Character escape, final boolean ignoreCase,
                                    final Symbol symbol, final LogicSymbol slot) {
        return this.likeConditionAccept(this.convert(property), value, matches, escape, ignoreCase, symbol, slot);
    }

    /**
     * 添加like模糊匹配条件
     *
     * @param column     {@link Column}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param symbol     {@link Symbol}
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    protected C likeConditionAccept(final Column column, final String value, final LikeMatchMode matches,
                                    final Character escape, final boolean ignoreCase,
                                    final Symbol symbol, final LogicSymbol slot) {
        if (column != null) {
            this.conditionConverter.accept(column.getColumn(), LikeParam.builder()
                    .symbol(symbol)
                    .slot(slot)
                    .typeHandler(column.getTypeHandler())
                    .jdbcType(column.getJdbcType())
                    .javaType(column.getDescriptor().getJavaType())
                    .value(value)
                    .matches(matches)
                    .escape(escape)
                    .ignoreCase(ignoreCase)
                    .dialect(this.getDialect())
                    .build());
        }
        return this.context;
    }

    /**
     * 添加is null条件
     *
     * @param property 属性
     * @param symbol   {@link Symbol}
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    protected C nullableConditionAccept(final String property, final Symbol symbol, final LogicSymbol slot) {
        return this.nullableConditionAccept(this.convert(property), symbol, slot);
    }

    /**
     * 添加is null条件
     *
     * @param column {@link Column}
     * @param symbol {@link Symbol}
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    protected C nullableConditionAccept(final Column column, final Symbol symbol, final LogicSymbol slot) {
        if (column != null) {
            this.conditionConverter.accept(column.getColumn(), NullParam.builder()
                    .symbol(symbol)
                    .slot(slot)
                    .build());
        }
        return this.context;
    }

    /**
     * 添加模板条件
     *
     * @param property  属性
     * @param template  模板
     * @param value     值
     * @param values    值列表
     * @param mapValues map值
     * @param paramMode 参数模式
     * @param slot      {@link LogicSymbol}
     * @return {@code this}
     */
    protected C templateConditionAccept(final String property, final String template, final Object value,
                                        final Collection<?> values, final Map<String, ?> mapValues,
                                        final ParamMode paramMode, final LogicSymbol slot) {
        return this.templateConditionAccept(this.convert(property), template, value, values, mapValues,
                paramMode, slot);
    }

    /**
     * 添加模板条件
     *
     * @param column    {@link Column}
     * @param template  模板
     * @param value     值
     * @param values    值列表
     * @param mapValues map值
     * @param paramMode 参数模式
     * @param slot      {@link LogicSymbol}
     * @return {@code this}
     */
    protected C templateConditionAccept(final Column column, final String template, final Object value,
                                        final Collection<?> values, final Map<String, ?> mapValues,
                                        final ParamMode paramMode, final LogicSymbol slot) {
        String columnName = "";
        final TemplateParam.TemplateParamBuilder<?, ?> builder = TemplateParam.builder()
                .template(template)
                .paramMode(paramMode)
                .value(value)
                .listValue(values)
                .mapValue(mapValues)
                .slot(slot)
                .symbol(Symbol.TEMPLATE);
        if (column != null) {
            builder.typeHandler(column.getTypeHandler())
                    .jdbcType(column.getJdbcType())
                    .javaType(column.getDescriptor().getJavaType())
                    .spliceJavaType(column.isSpliceJavaType());
            columnName = column.getColumn();
        }
        this.conditionConverter.accept(columnName, builder.build());
        return this.context;
    }


    /**
     * 添加单值条件
     *
     * @param column  字段名
     * @param value   值
     * @param matcher 匹配器
     * @param symbol  {@link Symbol}
     * @param slot    {@link LogicSymbol}
     * @param <V>     值类型
     * @return {@code this}
     */
    protected <V> C colSimpleConditionAccept(final String column, final V value, final Matcher<V> matcher,
                                             final Symbol symbol, final LogicSymbol slot) {
        if (Strings.isNotWhitespace(column) && this.early(value, matcher)) {
            this.conditionConverter.accept(column, SimpleParam.builder()
                    .symbol(symbol)
                    .slot(slot)
                    .value(value)
                    .build());
        }
        return this.context;
    }


    /**
     * 添加between范围条件
     *
     * @param column 字段名
     * @param begin  开始值
     * @param end    结束值
     * @param symbol {@link Symbol}
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@code this}
     */
    protected <V> C colBetweenConditionAccept(final String column, final V begin, final V end,
                                              final Symbol symbol, final LogicSymbol slot) {
        if (Strings.isNotWhitespace(column)) {
            this.conditionConverter.accept(column, BetweenParam.builder()
                    .symbol(symbol)
                    .slot(slot)
                    .begin(begin)
                    .end(end)
                    .build());
        }
        return this.context;
    }

    /**
     * 添加in范围条件
     *
     * @param column 字段名
     * @param values 值列表
     * @param symbol {@link Symbol}
     * @param slot   {@link LogicSymbol}
     * @param <V>    值类型
     * @return {@code this}
     */
    protected <V> C colInConditionAccept(final String column, final Collection<V> values, final Symbol symbol,
                                         final LogicSymbol slot) {
        if (Strings.isNotWhitespace(column)) {
            this.conditionConverter.accept(column, InParam.builder()
                    .symbol(symbol)
                    .slot(slot)
                    .values(values)
                    .build());
        }
        return this.context;
    }


    /**
     * 添加like模糊匹配条件
     *
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param symbol     {@link Symbol}
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    protected C colLikeConditionAccept(final String column, final String value, final LikeMatchMode matches,
                                       final Character escape, final boolean ignoreCase,
                                       final Symbol symbol, final LogicSymbol slot) {
        if (Strings.isNotWhitespace(column)) {
            this.conditionConverter.accept(column, LikeParam.builder()
                    .symbol(symbol)
                    .slot(slot)
                    .value(value)
                    .matches(matches)
                    .escape(escape)
                    .ignoreCase(ignoreCase)
                    .dialect(this.getDialect())
                    .build());
        }
        return this.context;
    }


    /**
     * 添加is null条件
     *
     * @param column 字段名
     * @param symbol {@link Symbol}
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    protected C colNullableConditionAccept(final String column, final Symbol symbol, final LogicSymbol slot) {
        if (Strings.isNotWhitespace(column)) {
            this.conditionConverter.accept(column, NullParam.builder()
                    .symbol(symbol)
                    .slot(slot)
                    .build());
        }
        return this.context;
    }


    /**
     * 添加模板条件
     *
     * @param column    字段名
     * @param template  模板
     * @param value     值
     * @param values    值列表
     * @param mapValues map值
     * @param paramMode 参数模式
     * @param slot      {@link LogicSymbol}
     * @return {@code this}
     */
    protected C colTemplateConditionAccept(final String column, final String template, final Object value,
                                           final Collection<?> values, final Map<String, ?> mapValues,
                                           final ParamMode paramMode, final LogicSymbol slot) {
        this.conditionConverter.accept(column, TemplateParam.builder()
                .template(template)
                .paramMode(paramMode)
                .value(value)
                .listValue(values)
                .mapValue(mapValues)
                .slot(slot)
                .symbol(Symbol.TEMPLATE)
                .build());
        return this.context;
    }

    /**
     * 处理嵌套条件
     *
     * @param slot  {@link LogicSymbol}
     * @param not   是否拼接not
     * @param apply {@link Function}
     * @return {@code this}
     */
    protected C doIt(final LogicSymbol slot, final boolean not, final Function<C, C> apply) {
        if (apply != null) {
            final C context = this.newInstance();
            if (context != null) {
                final C c = apply.apply(context);
                if (c != null) {
                    final List<Criterion> conditions = ((AbstractBaseCriteria<?>) c).fragmentManager.getConditions();
                    if (Objects.isNotEmpty(conditions)) {
                        this.where(NestedCondition.builder().not(not).slot(slot).conditions(conditions).build());
                    }
                }
            }
        }
        return this.context;
    }

    /**
     * 处理嵌套表达式
     *
     * @param slot        {@link LogicSymbol}
     * @param not         是否拼接not
     * @param expressions 表达式列表
     * @return {@code this}
     */
    protected C doIt(final LogicSymbol slot, final boolean not, final List<Expression> expressions) {
        if (Objects.isNotEmpty(expressions)) {
            final LogicSymbol _$slot = this.slot();
            expressions.forEach(it -> {
                it.ifSlotNull(_$slot);
                it.ifCriteriaNull(this);
            });
            this.where(NestedExpression.builder().slot(slot).not(not).expressions(expressions).build());
        }
        return this.context;
    }

    // endregion

    // region Override methods

    @Override
    protected C self() {
        return this.context;
    }

    /**
     * or
     *
     * @return {@code this}
     */
    public C or() {
        this.slotRef.set(LogicSymbol.OR);
        return this.context;
    }

    /**
     * and
     *
     * @return {@code this}
     */
    public C and() {
        this.slotRef.set(LogicSymbol.AND);
        return this.context;
    }

    /**
     * 获取{@link LogicSymbol}
     *
     * @return {@link LogicSymbol}
     */
    public LogicSymbol slot() {
        return this.slotRef.get();
    }

    @Override
    public C nested(boolean not, Function<C, C> apply) {
        return this.doIt(this.slot(), not, apply);
    }

    @Override
    public C and(boolean not, Function<C, C> apply) {
        return this.doIt(LogicSymbol.AND, not, apply);
    }

    @Override
    public C or(boolean not, Function<C, C> apply) {
        return this.doIt(LogicSymbol.OR, not, apply);
    }

    @Override
    public C nested(boolean not, List<Expression> expressions) {
        return this.doIt(this.slot(), not, expressions);
    }

    @Override
    public C and(boolean not, List<Expression> expressions) {
        return this.doIt(LogicSymbol.AND, not, expressions);
    }

    @Override
    public C or(boolean not, List<Expression> expressions) {
        return this.doIt(LogicSymbol.OR, not, expressions);
    }

    @Override
    public C where(Criterion criterion) {
        this.fragmentManager.addCondition(criterion);
        return this.context;
    }

    @Override
    public C where(Expression expression) {
        if (expression != null) {
            expression.ifCriteriaNull(this);
            expression.ifSlotNull(this.slot());
            this.conditionConverter.accept(expression);
        }
        return this.context;
    }

    @Override
    public C where(Expression... expressions) {
        if (Objects.isNotEmpty(expressions)) {
            for (Expression it : expressions) {
                this.where(it);
            }
        }
        return this.context;
    }

    @Override
    public C where(Collection<Expression> expressions) {
        if (Objects.isNotEmpty(expressions)) {
            for (Expression it : expressions) {
                this.where(it);
            }
        }
        return this.context;
    }

    // endregion
}
