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

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.base.constant.NullPrecedence;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.core.criteria.support.AbstractPlainCriteria;
import io.github.mybatisx.core.expression.Restrictions;
import io.github.mybatisx.core.param.Param;
import io.github.mybatisx.core.support.function.AggFunction;
import io.github.mybatisx.core.support.function.Avg;
import io.github.mybatisx.core.support.function.Count;
import io.github.mybatisx.core.support.function.Max;
import io.github.mybatisx.core.support.function.Min;
import io.github.mybatisx.core.support.function.Sum;
import io.github.mybatisx.core.support.group.Group;
import io.github.mybatisx.core.support.group.MultiGroup;
import io.github.mybatisx.core.support.group.SingleGroup;
import io.github.mybatisx.core.support.having.FunctionHaving;
import io.github.mybatisx.core.support.having.Having;
import io.github.mybatisx.core.support.order.MultiOrder;
import io.github.mybatisx.core.support.order.Order;
import io.github.mybatisx.core.support.order.SingleOrder;
import io.github.mybatisx.core.support.select.FunctionSelectable;
import io.github.mybatisx.core.support.select.SelectType;
import io.github.mybatisx.core.support.select.Selectable;
import io.github.mybatisx.core.support.select.StandardSelectable;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 抽象查询条件
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
@Slf4j
@SuppressWarnings({"serial"})
public abstract class AbstractPlainQueryCriteria<T, C extends PlainQueryWrapper<T, C>> extends
        AbstractPlainCriteria<T, C> implements PlainQueryWrapper<T, C> {

    // region Basic methods 

    @Override
    public C as(String alias) {
        final String oldValue = this.aliasRef.get();
        this.aliasRef.compareAndSet(oldValue, Strings.isWhitespace(alias) ? Constants.EMPTY : alias);
        return this.context;
    }

    @Override
    public String as(boolean force) {
        return this.getAlias(force);
    }

    @Override
    public C useAlias() {
        return this.useAlias(true);
    }

    @Override
    public C useAlias(boolean using) {
        this.useAlias.set(using);
        return this.context;
    }

    @Override
    public C propAsAlias() {
        return this.propAsAlias(true);
    }

    @Override
    public C propAsAlias(boolean using) {
        this.propertyAsAlias = using;
        return this.context;
    }

    @Override
    public boolean isPropAsAlias() {
        return this.propertyAsAlias;
    }

    @Override
    public C reference(String reference) {
        final String oldValue = this.propRef.get();
        this.propRef.compareAndSet(oldValue, Strings.isWhitespace(reference) ? Constants.EMPTY : reference);
        return this.context;
    }

    @Override
    public C extra() {
        return this.extra(true);
    }

    @Override
    public C extra(boolean extra) {
        this.extra = extra;
        return this.context;
    }

    @Override
    public boolean isExtra() {
        return this.extra;
    }

    @Override
    public C inherit() {
        return this.inherit(true);
    }

    @Override
    public C inherit(boolean inherit) {
        this.inherit = inherit;
        return this.context;
    }

    @Override
    public boolean isInherit() {
        return this.inherit;
    }

    @Override
    public C distinct(boolean distinct) {
        this.distinct = distinct;
        return this.context;
    }

    @Override
    public boolean isDistinct() {
        return this.distinct;
    }

    @Override
    public C onlyQueryFunction() {
        return this.onlyQueryFunction(true);
    }

    @Override
    public C onlyQueryFunction(boolean only) {
        this.onlyQueryFunction = only;
        return this.context;
    }

    @Override
    public boolean isOnlyQueryFunction() {
        return this.onlyQueryFunction;
    }

    @Override
    public C containsFunction(boolean contains) {
        this.containsFunction = contains;
        return this.context;
    }

    @Override
    public boolean isContainsFunction() {
        return this.containsFunction;
    }

    @Override
    public C groupAll() {
        return this.groupAll(true);
    }

    @Override
    public C groupAll(boolean groupAll) {
        this.groupAll = groupAll;
        return this.context;
    }

    @Override
    public boolean isGroupAll() {
        return this.groupAll;
    }

    @Override
    public C keepOrderly() {
        return this.keepOrderly(true);
    }

    @Override
    public C keepOrderly(boolean keep) {
        this.keepOrderly = keep;
        return this.context;
    }

    @Override
    public boolean isKeepOrderly() {
        return this.keepOrderly;
    }

    @Override
    public C having() {
        return this.conditionToggling();
    }

    @Override
    public C condition() {
        return this.conditionToggling();
    }

    // endregion

    // region Embeddable result methods

    @Override
    public String getResultMap() {
        return this.resultMap;
    }

    @Override
    public C setResultMap(String resultMap) {
        this.resultMap = resultMap;
        return this.context;
    }

    @Override
    public Class<?> getResultType() {
        return this.resultType;
    }

    @Override
    public C setResultType(Class<?> resultType) {
        this.resultType = resultType;
        return this.context;
    }

    @Override
    public String getMapKey() {
        return this.mapKey;
    }

    @Override
    public C setMapKey(String mapKey) {
        this.mapKey = mapKey;
        return this.context;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class<? extends Map> getMapType() {
        return this.mapType;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public C setMapType(Class<? extends Map> mapType) {
        this.mapType = mapType;
        return this.context;
    }

    // endregion

    // region Selectable methods

    @Override
    public C colSelect(String column, String alias) {
        if (Strings.isNotWhitespace(column)) {
            this.select(StandardSelectable.builder()
                    .query(this)
                    .type(SelectType.PLAIN)
                    .column(column)
                    .alias(alias)
                    .build());
        }
        return this.context;
    }

    @Override
    public C colSelect(Map<String, String> columns) {
        if (Objects.isNotEmpty(columns)) {
            for (Map.Entry<String, String> it : columns.entrySet()) {
                this.colSelect(it.getValue(), it.getKey());
            }
        }
        return this.context;
    }

    @Override
    public C colSelects(Collection<String> columns) {
        if (Objects.isNotEmpty(columns)) {
            for (String it : columns) {
                this.colSelect(it, null);
            }
        }
        return this.context;
    }

    @Override
    public C select(Selectable selectable) {
        this.fragmentManager.addSelect(selectable);
        return this.context;
    }

    @Override
    public C selects(Selectable... selectables) {
        return this.selects(Arrays.asList(selectables));
    }

    @Override
    public C selects(List<Selectable> selectables) {
        this.fragmentManager.addSelects(selectables);
        return this.context;
    }

    @Override
    public C excludeColumn(String column) {
        this.fragmentManager.addExcludeColumn(column);
        return this.context;
    }

    @Override
    public C excludeColumns(Collection<String> columns) {
        this.fragmentManager.addExcludeColumns(columns);
        return this.context;
    }

    @Override
    public boolean hasSelect() {
        return this.fragmentManager.hasSelect();
    }

    @Override
    public List<Selectable> fetchSelects() {
        return this.getSelects();
    }

    // endregion

    // region Aggregate function methods

    @Override
    public C count() {
        return this.countWithAlias(null);
    }

    @Override
    public C countWithAlias(String alias) {
        return this.function(new Count(this, "*", alias));
    }

    @Override
    public C colCount(String column, String alias, boolean distinct) {
        if (Strings.isNotWhitespace(column)) {
            this.function(new Count(this, column, alias, distinct));
        }
        return this.context;
    }

    @Override
    public C colSum(String column, String alias, Integer scale, boolean distinct) {
        if (Strings.isNotWhitespace(column)) {
            this.function(new Sum(this, column, alias, distinct));
        }
        return this.context;
    }

    @Override
    public C colAvg(String column, String alias, Integer scale, boolean distinct) {
        if (Strings.isNotWhitespace(column)) {
            this.function(new Avg(this, column, alias, distinct));
        }
        return this.context;
    }

    @Override
    public C colMin(String column, String alias, Integer scale) {
        if (Strings.isNotWhitespace(column)) {
            this.function(new Min(this, column, alias, false));
        }
        return this.context;
    }

    @Override
    public C colMax(String column, String alias, Integer scale) {
        if (Strings.isNotWhitespace(column)) {
            this.function(new Max(this, column, alias, false));
        }
        return this.context;
    }

    @Override
    public C function(AggFunction function) {
        if (function != null) {
            this.select(new FunctionSelectable(SelectType.FUNCTION, function));
        }
        return this.context;
    }

    @Override
    public C functions(AggFunction... functions) {
        return this.functions(Arrays.asList(functions));
    }

    @Override
    public C functions(List<AggFunction> functions) {
        if (Objects.isNotEmpty(functions)) {
            for (AggFunction it : functions) {
                this.function(it);
            }
        }
        return this.context;
    }

    // endregion

    // region Group by methods

    @Override
    public C colGroup(String column) {
        return this.group(SingleGroup.group(this, column));
    }

    @Override
    public C colGroups(List<String> columns) {
        return this.group(MultiGroup.group(this, columns));
    }

    @Override
    public C group(Group group) {
        this.fragmentManager.addGroup(group);
        return this.context;
    }

    @Override
    public C groups(Group... groups) {
        return this.groups(Arrays.asList(groups));
    }

    @Override
    public C groups(List<Group> groups) {
        this.fragmentManager.addGroups(groups);
        return this.context;
    }

    // endregion

    // region Having methods

    // region Equal

    @Override
    public C countEq(long value, LogicSymbol slot) {
        return this.havingAccept(this, SqlSymbol.STAR, false, value, Symbol.EQ, slot,
                AggType.COUNT, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colCountEq(String column, boolean distinct, long value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.EQ, slot, AggType.COUNT,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colSumEq(String column, boolean distinct, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.EQ, slot, AggType.SUM,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colAvgEq(String column, boolean distinct, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.EQ, slot, AggType.AVG,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMinEq(String column, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, false, value, Symbol.EQ, slot, AggType.MIN,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMaxEq(String column, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, false, value, Symbol.EQ, slot, AggType.MAX,
                Restrictions.Mode.COLUMN);
    }

    // endregion

    // region Not equal

    @Override
    public C countNe(long value, LogicSymbol slot) {
        return this.havingAccept(this, SqlSymbol.STAR, false, value, Symbol.NE, slot,
                AggType.COUNT, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colCountNe(String column, boolean distinct, long value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.NE, slot, AggType.COUNT,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colSumNe(String column, boolean distinct, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.NE, slot, AggType.SUM,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colAvgNe(String column, boolean distinct, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.NE, slot, AggType.AVG,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMinNe(String column, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, false, value, Symbol.NE, slot, AggType.MIN,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMaxNe(String column, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, false, value, Symbol.NE, slot, AggType.MAX,
                Restrictions.Mode.COLUMN);
    }

    // endregion

    // region Greater than

    @Override
    public C countGt(long value, LogicSymbol slot) {
        return this.havingAccept(this, SqlSymbol.STAR, false, value, Symbol.GT, slot,
                AggType.COUNT, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colCountGt(String column, boolean distinct, long value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.GT, slot, AggType.COUNT, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colSumGt(String column, boolean distinct, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.GT, slot, AggType.SUM, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colAvgGt(String column, boolean distinct, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.GT, slot, AggType.AVG, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMinGt(String column, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, false, value, Symbol.GT, slot, AggType.MIN, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMaxGt(String column, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, false, value, Symbol.GT, slot, AggType.MAX, Restrictions.Mode.COLUMN);
    }

    // endregion

    // region Greater than or equal to

    @Override
    public C countGe(long value, LogicSymbol slot) {
        return this.havingAccept(this, SqlSymbol.STAR, false, value, Symbol.GE, slot,
                AggType.COUNT, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colCountGe(String column, boolean distinct, long value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.GE, slot, AggType.COUNT,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colSumGe(String column, boolean distinct, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.GE, slot, AggType.SUM,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colAvgGe(String column, boolean distinct, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.GE, slot, AggType.AVG,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMinGe(String column, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, false, value, Symbol.GE, slot, AggType.MIN,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMaxGe(String column, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, false, value, Symbol.GE, slot, AggType.MAX,
                Restrictions.Mode.COLUMN);
    }

    // endregion

    // region Less than

    @Override
    public C countLt(long value, LogicSymbol slot) {
        return this.havingAccept(this, SqlSymbol.STAR, false, value, Symbol.LT, slot,
                AggType.COUNT, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colCountLt(String column, boolean distinct, long value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.LT, slot, AggType.COUNT,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colSumLt(String column, boolean distinct, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.LT, slot, AggType.SUM,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colAvgLt(String column, boolean distinct, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.LT, slot, AggType.AVG,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMinLt(String column, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, false, value, Symbol.LT, slot, AggType.MIN,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMaxLt(String column, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, false, value, Symbol.LT, slot, AggType.MAX,
                Restrictions.Mode.COLUMN);
    }

    // endregion

    // region Less than or equal to

    @Override
    public C countLe(long value, LogicSymbol slot) {
        return this.havingAccept(this, SqlSymbol.STAR, false, value, Symbol.LE, slot, AggType.COUNT, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colCountLe(String column, boolean distinct, long value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.LE, slot, AggType.COUNT, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colSumLe(String column, boolean distinct, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.LE, slot, AggType.SUM, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colAvgLe(String column, boolean distinct, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, value, Symbol.LE, slot, AggType.AVG, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMinLe(String column, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, false, value, Symbol.LE, slot, AggType.MIN, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMaxLe(String column, Object value, LogicSymbol slot) {
        return this.havingAccept(this, column, false, value, Symbol.LE, slot, AggType.MAX, Restrictions.Mode.COLUMN);
    }

    // endregion

    // region Between

    @Override
    public C countBetween(long begin, long end, LogicSymbol slot) {
        return this.havingAccept(this, SqlSymbol.STAR, false, new Object[]{begin, end}, Symbol.BETWEEN,
                slot, AggType.COUNT, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colCountBetween(String column, boolean distinct, long begin, long end, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, new Object[]{begin, end}, Symbol.BETWEEN, slot,
                AggType.COUNT, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colSumBetween(String column, boolean distinct, Object begin, Object end, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, new Object[]{begin, end}, Symbol.BETWEEN, slot,
                AggType.SUM, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colAvgBetween(String column, boolean distinct, Object begin, Object end, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, new Object[]{begin, end}, Symbol.BETWEEN, slot,
                AggType.AVG, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMinBetween(String column, Object begin, Object end, LogicSymbol slot) {
        return this.havingAccept(this, column, false, new Object[]{begin, end}, Symbol.BETWEEN, slot,
                AggType.MIN, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMaxBetween(String column, Object begin, Object end, LogicSymbol slot) {
        return this.havingAccept(this, column, false, new Object[]{begin, end}, Symbol.BETWEEN, slot,
                AggType.MAX, Restrictions.Mode.COLUMN);
    }

    // endregion

    // region Not between

    @Override
    public C countNotBetween(long begin, long end, LogicSymbol slot) {
        return this.havingAccept(this, SqlSymbol.STAR, false, new Object[]{begin, end}, Symbol.NOT_BETWEEN,
                slot, AggType.COUNT, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colCountNotBetween(String column, boolean distinct, long begin, long end, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, new Object[]{begin, end}, Symbol.NOT_BETWEEN, slot,
                AggType.COUNT, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colSumNotBetween(String column, boolean distinct, Object begin, Object end, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, new Object[]{begin, end}, Symbol.NOT_BETWEEN, slot,
                AggType.SUM, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colAvgNotBetween(String column, boolean distinct, Object begin, Object end, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, new Object[]{begin, end}, Symbol.NOT_BETWEEN, slot,
                AggType.AVG, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMinNotBetween(String column, Object begin, Object end, LogicSymbol slot) {
        return this.havingAccept(this, column, false, new Object[]{begin, end}, Symbol.NOT_BETWEEN, slot,
                AggType.MIN, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMaxNotBetween(String column, Object begin, Object end, LogicSymbol slot) {
        return this.havingAccept(this, column, false, new Object[]{begin, end}, Symbol.NOT_BETWEEN, slot,
                AggType.MAX, Restrictions.Mode.COLUMN);
    }

    // endregion

    // region In

    @Override
    public C countIn(Collection<?> values, LogicSymbol slot) {
        return this.havingAccept(this, SqlSymbol.STAR, false, values, Symbol.IN,
                slot, AggType.COUNT, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colCountIn(String column, boolean distinct, Collection<?> values, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, values, Symbol.IN, slot, AggType.COUNT, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colSumIn(String column, boolean distinct, Collection<?> values, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, values, Symbol.IN, slot, AggType.SUM,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colAvgIn(String column, boolean distinct, Collection<?> values, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, values, Symbol.IN, slot, AggType.AVG,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMinIn(String column, Collection<?> values, LogicSymbol slot) {
        return this.havingAccept(this, column, false, values, Symbol.IN, slot, AggType.MIN, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMaxIn(String column, Collection<?> values, LogicSymbol slot) {
        return this.havingAccept(this, column, false, values, Symbol.IN, slot, AggType.MAX, Restrictions.Mode.COLUMN);
    }

    // endregion

    // region Not in

    @Override
    public C countNotIn(Collection<?> values, LogicSymbol slot) {
        return this.havingAccept(this, SqlSymbol.STAR, false, values, Symbol.NOT_IN,
                slot, AggType.COUNT, Restrictions.Mode.COLUMN);
    }

    @Override
    public C colCountNotIn(String column, boolean distinct, Collection<?> values, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, values, Symbol.NOT_IN, slot, AggType.COUNT,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colSumNotIn(String column, boolean distinct, Collection<?> values, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, values, Symbol.NOT_IN, slot, AggType.SUM,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colAvgNotIn(String column, boolean distinct, Collection<?> values, LogicSymbol slot) {
        return this.havingAccept(this, column, distinct, values, Symbol.NOT_IN, slot, AggType.AVG,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMinNotIn(String column, Collection<?> values, LogicSymbol slot) {
        return this.havingAccept(this, column, false, values, Symbol.NOT_IN, slot, AggType.MIN,
                Restrictions.Mode.COLUMN);
    }

    @Override
    public C colMaxNotIn(String column, Collection<?> values, LogicSymbol slot) {
        return this.havingAccept(this, column, false, values, Symbol.NOT_IN, slot, AggType.MAX,
                Restrictions.Mode.COLUMN);
    }

    // endregion 

    @Override
    public C having(String alias, Param param) {
        final FunctionSelectable function = this.fragmentManager.getFunction(alias);
        if (function != null) {
            return this.having(function.getFunction(), param);
        } else {
            log.warn("The specified aggregate function object cannot be found by alias({})", alias);
        }
        return this.context;
    }

    @Override
    public C having(AggFunction function, Param param) {
        return this.having(new FunctionHaving(function, param));
    }

    @Override
    public C having(Having having) {
        this.fragmentManager.addHaving(having);
        return this.context;
    }

    @Override
    public C having(Having... havingArray) {
        return this.having(Arrays.asList(havingArray));
    }

    @Override
    public C having(List<Having> havingList) {
        this.fragmentManager.addHaving(havingList);
        return this.context;
    }

    // endregion

    // region Order by methods

    @Override
    public C colAsc(String column, boolean ignoreCase, NullPrecedence precedence) {
        return this.order(SingleOrder.asc(this, column, ignoreCase, precedence));
    }

    @Override
    public C colAsc(List<String> columns, boolean ignoreCase, NullPrecedence precedence) {
        return this.order(MultiOrder.asc(this, columns, ignoreCase, precedence));
    }

    @Override
    public C colDesc(String column, boolean ignoreCase, NullPrecedence precedence) {
        return this.order(SingleOrder.desc(this, column, ignoreCase, precedence));
    }

    @Override
    public C colDesc(List<String> columns, boolean ignoreCase, NullPrecedence precedence) {
        return this.order(MultiOrder.desc(this, columns, ignoreCase, precedence));
    }

    @Override
    public C order(Order order) {
        this.fragmentManager.addOrder(order);
        return this.context;
    }

    @Override
    public C orders(Order... orders) {
        return this.orders(Arrays.asList(orders));
    }

    @Override
    public C orders(List<Order> orders) {
        this.fragmentManager.addOrders(orders);
        return this.context;
    }

    // endregion

    @Override
    public C join(Joinable<?> joinable) {
        if (joinable != null) {
            this.associations.add(joinable);
        } else {
            log.warn("The associated query object cannot be null");
        }
        return this.context;
    }

    @Override
    public String getSelectFragment() {
        return this.sqlManager.getSelectFragment();
    }

    @Override
    public String getSelectFragment(boolean self) {
        return this.sqlManager.getSelectFragment(self);
    }

    @Override
    public String getGroupFragment() {
        return this.sqlManager.getGroupFragment();
    }

}
