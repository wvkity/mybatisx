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
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.matcher.Matcher;

import java.util.Map;

/**
 * 抽象基础条件
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
@SuppressWarnings({"serial"})
public abstract class AbstractBaseCriteria<T, C extends BaseCriteriaWrapper<T, C>> extends
        AbstractLambdaCriteria<T, C> implements BaseCriteriaWrapper<T, C> {

    @Override
    public <V> C colEq(String column, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.colSingleConditionAccept(column, value, matcher, Symbol.EQ, slot);
    }

    @Override
    public C colEq(Map<String, ?> columns, LogicSymbol slot) {
        if (Objects.isNotEmpty(columns)) {
            for (Map.Entry<String, ?> it : columns.entrySet()) {
                this.colEq(it.getKey(), it.getValue(), slot);
            }
        }
        return this.self();
    }

    @Override
    public <V> C colNe(String column, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.colSingleConditionAccept(column, value, matcher, Symbol.NE, slot);
    }

    @Override
    public <V> C colGt(String column, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.colSingleConditionAccept(column, value, matcher, Symbol.GT, slot);
    }

    @Override
    public <V> C colGe(String column, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.colSingleConditionAccept(column, value, matcher, Symbol.GE, slot);
    }

    @Override
    public <V> C colLt(String column, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.colSingleConditionAccept(column, value, matcher, Symbol.LT, slot);
    }

    @Override
    public <V> C colLe(String column, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.colSingleConditionAccept(column, value, matcher, Symbol.LE, slot);
    }
}
