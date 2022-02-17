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
import io.github.mybatisx.base.constant.MatchMode;
import io.github.mybatisx.base.constant.ParamMode;
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.matcher.Matcher;

import java.util.Collection;
import java.util.Map;

/**
 * 抽象通用基础条件
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
@SuppressWarnings({"serial"})
public abstract class AbstractGenericCriteria<T, C extends GenericCriteriaWrapper<T, C>> extends
        AbstractLambdaCriteria<T, C> implements GenericCriteriaWrapper<T, C> {

    @Override
    public <V> C colEq(String column, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.colSimpleConditionAccept(column, value, matcher, Symbol.EQ, slot);
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
        return this.colSimpleConditionAccept(column, value, matcher, Symbol.NE, slot);
    }

    @Override
    public <V> C colGt(String column, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.colSimpleConditionAccept(column, value, matcher, Symbol.GT, slot);
    }

    @Override
    public <V> C colGe(String column, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.colSimpleConditionAccept(column, value, matcher, Symbol.GE, slot);
    }

    @Override
    public <V> C colLt(String column, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.colSimpleConditionAccept(column, value, matcher, Symbol.LT, slot);
    }

    @Override
    public <V> C colLe(String column, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.colSimpleConditionAccept(column, value, matcher, Symbol.LE, slot);
    }

    @Override
    public <V> C colBetween(String column, V begin, V end, LogicSymbol slot) {
        return this.colBetweenConditionAccept(column, begin, end, Symbol.BETWEEN, slot);
    }

    @Override
    public <V> C colNotBetween(String column, V begin, V end, LogicSymbol slot) {
        return this.colBetweenConditionAccept(column, begin, end, Symbol.NOT_BETWEEN, slot);
    }

    @Override
    public <V> C colIn(String column, Collection<V> values, LogicSymbol slot) {
        return this.colInConditionAccept(column, values, Symbol.IN, slot);
    }

    @Override
    public <V> C colNotIn(String column, Collection<V> values, LogicSymbol slot) {
        return this.colInConditionAccept(column, values, Symbol.NOT_IN, slot);
    }

    @Override
    public C colLike(String column, String value, MatchMode matchMode, Character escape,
                     boolean ignoreCase, LogicSymbol slot) {
        return this.colLikeConditionAccept(column, value, matchMode, escape, ignoreCase, Symbol.LIKE, slot);
    }

    @Override
    public C colNotLike(String column, String value, MatchMode matchMode, Character escape,
                        boolean ignoreCase, LogicSymbol slot) {
        return this.colLikeConditionAccept(column, value, matchMode, escape, ignoreCase, Symbol.NOT_LIKE, slot);
    }

    @Override
    public C colIsNull(String column, LogicSymbol slot) {
        return this.colNullableConditionAccept(column, Symbol.NULL, slot);
    }

    @Override
    public C colNonNull(String column, LogicSymbol slot) {
        return this.colNullableConditionAccept(column, Symbol.NOT_NULL, slot);
    }

    @Override
    public C colTemplate(String template, String column, Collection<Object> values, LogicSymbol slot) {
        return this.colTemplateConditionAccept(column, template, null, values, null, ParamMode.MULTIPLE, slot);
    }

    @Override
    public C colTemplate(String template, String column, Map<String, Object> values, LogicSymbol slot) {
        return this.colTemplateConditionAccept(column, template, null, null, values, ParamMode.MAP, slot);
    }

    @Override
    public C colOn(Criteria<?> rc, String rightColumn) {
        return this.joinableConditionAccept(this, rc, rightColumn, false);
    }

    @Override
    public C colOn(String leftColumn, Criteria<?> rc) {
        return this.joinableConditionAccept(this, leftColumn, false, rc);
    }

    @Override
    public C colOn(String leftColumn, Criteria<?> rc, String rightColumn) {
        return this.joinableConditionAccept(this, leftColumn, false, rc, rightColumn, false);
    }

    public C colOnWith(String leftColumn, Criteria<?> rc, String rightProperty) {
        return this.joinableConditionAccept(this, leftColumn, false, rc, rightProperty, true);
    }

}
