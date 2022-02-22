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
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.core.criteria.AbstractCriteriaSupport;
import io.github.mybatisx.core.criteria.query.Query;
import io.github.mybatisx.core.criterion.ExistsCondition;
import io.github.mybatisx.core.criterion.PureCondition;
import io.github.mybatisx.core.property.Property;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.matcher.Matcher;

import java.util.Collection;
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
        final Column id = this.getPrimaryKey();
        return this.simpleConditionAccept(id, value, matcher, Symbol.EQ, slot);
    }

    @Override
    public <V> C eq(String property, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.simpleConditionAccept(property, value, matcher, Symbol.EQ, slot);
    }

    @Override
    public C eq(Map<String, ?> properties, LogicSymbol slot) {
        if (Objects.isNotEmpty(properties)) {
            for (Map.Entry<String, ?> it : properties.entrySet()) {
                this.eq(it.getKey(), it.getValue(), slot);
            }
        }
        return this.self();
    }

    @Override
    public C subEq(String property, Query<?> query, LogicSymbol slot) {
        return this.subConditionAccept(property, query, Symbol.EQ, slot, false);
    }

    @Override
    public <V> C ne(String property, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.simpleConditionAccept(property, value, matcher, Symbol.NE, slot);
    }

    @Override
    public C subNe(String property, Query<?> query, LogicSymbol slot) {
        return this.subConditionAccept(property, query, Symbol.NE, slot, false);
    }

    @Override
    public <V> C gt(String property, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.simpleConditionAccept(property, value, matcher, Symbol.GT, slot);
    }

    @Override
    public C subGt(String property, Query<?> query, LogicSymbol slot) {
        return this.subConditionAccept(property, query, Symbol.GT, slot, false);
    }

    @Override
    public <V> C ge(String property, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.simpleConditionAccept(property, value, matcher, Symbol.GE, slot);
    }

    @Override
    public C subGe(String property, Query<?> query, LogicSymbol slot) {
        return this.subConditionAccept(property, query, Symbol.GE, slot, false);
    }

    @Override
    public <V> C lt(String property, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.simpleConditionAccept(property, value, matcher, Symbol.LT, slot);
    }

    @Override
    public C subLt(String property, Query<?> query, LogicSymbol slot) {
        return this.subConditionAccept(property, query, Symbol.LT, slot, false);
    }

    @Override
    public <V> C le(String property, V value, Matcher<V> matcher, LogicSymbol slot) {
        return this.simpleConditionAccept(property, value, matcher, Symbol.LE, slot);
    }

    @Override
    public C subLe(String property, Query<?> query, LogicSymbol slot) {
        return this.subConditionAccept(property, query, Symbol.LE, slot, false);
    }

    @Override
    public <V> C between(String property, V begin, V end, LogicSymbol slot) {
        return this.betweenConditionAccept(property, begin, end, Symbol.BETWEEN, slot);
    }

    @Override
    public <V> C notBetween(String property, V begin, V end, LogicSymbol slot) {
        return this.betweenConditionAccept(property, begin, end, Symbol.NOT_BETWEEN, slot);
    }

    @Override
    public <V> C in(String property, Collection<V> values, LogicSymbol slot) {
        return this.inConditionAccept(property, values, Symbol.IN, slot);
    }

    @Override
    public C subIn(String property, Query<?> query, LogicSymbol slot) {
        return this.subConditionAccept(property, query, Symbol.IN, slot, false);
    }

    @Override
    public <V> C notIn(String property, Collection<V> values, LogicSymbol slot) {
        return this.inConditionAccept(property, values, Symbol.NOT_IN, slot);
    }

    @Override
    public C subNotIn(String property, Query<?> query, LogicSymbol slot) {
        return this.subConditionAccept(property, query, Symbol.NOT_IN, slot, false);
    }

    @Override
    public C like(String property, String value, MatchMode matchMode, Character escape,
                  boolean ignoreCase, LogicSymbol slot) {
        return this.likeConditionAccept(property, value, matchMode, escape, ignoreCase, Symbol.LIKE, slot);
    }

    @Override
    public C notLike(String property, String value, MatchMode matchMode, Character escape,
                     boolean ignoreCase, LogicSymbol slot) {
        return this.likeConditionAccept(property, value, matchMode, escape, ignoreCase, Symbol.NOT_LIKE, slot);
    }

    @Override
    public C isNull(String property, LogicSymbol slot) {
        return this.nullableConditionAccept(property, Symbol.NULL, slot);
    }

    @Override
    public C nonNull(String property, LogicSymbol slot) {
        return this.nullableConditionAccept(property, Symbol.NOT_NULL, slot);
    }

    @Override
    public C template(String template, Object value, LogicSymbol slot) {
        return this.colTemplateConditionAccept(null, template, value, null, null, ParamMode.SINGLE, slot);
    }

    @Override
    public C template(String template, Collection<Object> values, LogicSymbol slot) {
        return this.colTemplateConditionAccept(null, template, null, values, null, ParamMode.MULTIPLE, slot);
    }

    @Override
    public C template(String template, Map<String, Object> values, LogicSymbol slot) {
        return this.colTemplateConditionAccept(null, template, null, null, values, ParamMode.MAP, slot);
    }

    @Override
    public C template(String template, Property<T, ?> property, Object value, LogicSymbol slot) {
        return this.templateConditionAccept(this.convert(property), template, value, null, null,
                ParamMode.SINGLE, slot);
    }

    @Override
    public C template(String template, String property, Collection<Object> values, LogicSymbol slot) {
        return this.templateConditionAccept(property, template, null, values, null,
                ParamMode.MULTIPLE, slot);
    }

    @Override
    public C template(String template, String property, Map<String, Object> values, LogicSymbol slot) {
        return this.templateConditionAccept(property, template, null, null, values,
                ParamMode.MAP, slot);
    }

    @Override
    public C on(String leftProperty, Criteria<?> rc) {
        return this.joinableConditionAccept(this, leftProperty, true, rc);
    }

    @Override
    public C on(Criteria<?> rc, String rightProperty) {
        return this.joinableConditionAccept(this, rc, rightProperty, true);
    }

    @Override
    public C on(String leftProperty, Criteria<?> rc, String rightProperty) {
        return this.joinableConditionAccept(this, leftProperty, true, rc, rightProperty, true);
    }

    @Override
    public C onWith(String leftProperty, Criteria<?> rc, String rightColumn) {
        return this.joinableConditionAccept(this, leftProperty, true, rc, rightColumn, false);
    }

    @Override
    public C exists(Query<?> query, LogicSymbol slot) {
        return this.where(ExistsCondition.exists(query, slot));
    }

    @Override
    public C notExists(Query<?> query, LogicSymbol slot) {
        return this.where(ExistsCondition.notExists(query, slot));
    }

    @Override
    public C condition(String condition, LogicSymbol slot) {
        return this.where(PureCondition.of(condition, slot));
    }

}
