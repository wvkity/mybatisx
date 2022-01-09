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

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.criterion.Criterion;
import io.github.mybatisx.base.expression.Expression;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.core.param.SingleParam;
import io.github.mybatisx.matcher.Matcher;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * 抽象条件
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
@Slf4j
@SuppressWarnings({"serial"})
public abstract class AbstractCriteriaSupport<T, C extends CriteriaWrapper<T, C>> extends AbstractGenericCriteria<T>
        implements CriteriaWrapper<T, C> {

    /**
     * 当前对象
     */
    @SuppressWarnings("unchecked")
    protected final C ctx = (C) this;

    // region Protected  methods

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
        if (this.early(value, matcher)) {
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

    // endregion

    // region Override methods

    @Override
    public String as() {
        return Constants.EMPTY;
    }

    @Override
    public C strict(boolean strict) {
        this.nonMatchingThenThrows.set(strict);
        return this.ctx;
    }

    @Override
    public C setVersion(Object value) {
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

    /**
     * or
     *
     * @return {@code this}
     */
    public C or() {
        this.slotRef.set(LogicSymbol.OR);
        return this.ctx;
    }

    /**
     * and
     *
     * @return {@code this}
     */
    public C and() {
        this.slotRef.set(LogicSymbol.AND);
        return this.ctx;
    }

    /**
     * 获取{@link LogicSymbol}
     *
     * @return {@link LogicSymbol}
     */
    public LogicSymbol slot() {
        return this.slotRef.get();
    }

    // endregion

    // region Protected methods

    @Override
    protected C self() {
        return this.ctx;
    }

    // endregion

}
