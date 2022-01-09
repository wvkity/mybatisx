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
package io.github.mybatisx.core.criteria.support;

import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.core.criteria.AbstractCriteriaSupport;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.matcher.Matcher;

import java.util.Map;

/**
 * 抽象基础条件(支持Lambda表达式)
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
@SuppressWarnings({"serial"})
public abstract class AbstractLambdaCriteria<T, C extends LambdaCriteriaWrapper<T, C>> extends
        AbstractCriteriaSupport<T, C> implements LambdaCriteriaWrapper<T, C> {

    @Override
    public <V> C idEq(V value, Matcher<V> matcher, LogicSymbol slot) {
        this.checkPrimaryKey();
        final Column id = this.getPrimaryKey();
        return this.singleConditionAccept(id, value, matcher, Symbol.EQ, slot);
    }

    @Override
    public <V> C eq(String property, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.singleConditionAccept(property, value, matcher, Symbol.EQ, slot);
    }

    @Override
    public C eq(Map<String, Object> properties, LogicSymbol slot) {
        if (Objects.isNotEmpty(properties)) {
            for (Map.Entry<String, Object> it : properties.entrySet()) {
                this.eq(it.getKey(), it.getValue(), slot);
            }
        }
        return this.self();
    }

    @Override
    public <V> C ne(String property, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.singleConditionAccept(property, value, matcher, Symbol.NE, slot);
    }

    @Override
    public <V> C gt(String property, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.singleConditionAccept(property, value, matcher, Symbol.GT, slot);
    }

    @Override
    public <V> C ge(String property, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.singleConditionAccept(property, value, matcher, Symbol.GE, slot);
    }

    @Override
    public <V> C lt(String property, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.singleConditionAccept(property, value, matcher, Symbol.LT, slot);
    }

    @Override
    public <V> C le(String property, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.singleConditionAccept(property, value, matcher, Symbol.LE, slot);
    }
    
}
