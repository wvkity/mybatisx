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
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.criterion.Criterion;
import io.github.mybatisx.base.expression.Expression;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.core.param.BetweenParam;
import io.github.mybatisx.core.param.InParam;
import io.github.mybatisx.core.param.LikeParam;
import io.github.mybatisx.core.param.SingleParam;
import io.github.mybatisx.matcher.Matcher;

import java.util.Collection;

/**
 * 抽象条件处理
 *
 * @author wvkity
 * @created 2022/1/9
 * @since 1.0.0
 */
@SuppressWarnings({"serial"})
public abstract class AbstractConditionAcceptSupport<T, C extends CriteriaWrapper<T, C>> extends
        AbstractGenericCriteria<T> implements CriteriaWrapper<T, C> {

    /**
     * 当前对象
     */
    @SuppressWarnings("unchecked")
    protected final C ctx = (C) this;

    // region Protected methods

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
    protected <V> C singleConditionAccept(final String property, final V value, final Matcher<V> matcher,
                                          final Symbol symbol, final LogicSymbol slot) {
        return this.singleConditionAccept(this.convert(property), value, matcher, symbol, slot);
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
    protected <V> C singleConditionAccept(final Column column, final V value, final Matcher<V> matcher,
                                          final Symbol symbol, final LogicSymbol slot) {
        if (column != null && this.early(value, matcher)) {
            this.conditionConverter.accept(column.getColumn(), SingleParam.builder()
                    .symbol(symbol)
                    .slot(slot)
                    .typeHandler(column.getTypeHandler())
                    .jdbcType(column.getJdbcType())
                    .javaType(column.getDescriptor().getJavaType())
                    .value(value)
                    .build());
        }
        return this.ctx;
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
        return this.ctx;
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
        return this.ctx;
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
        return this.ctx;
    }

    // endregion

    // region Override methods

    @Override
    protected C self() {
        return this.ctx;
    }

    @Override
    public C where(Criterion criterion) {
        this.fragmentManager.addCondition(criterion);
        return this.ctx;
    }

    @Override
    public C where(Expression<?> expression) {
        return this.ctx;
    }

    @Override
    public C where(Collection<Expression<?>> expressions) {
        return this.ctx;
    }

    // endregion
}
