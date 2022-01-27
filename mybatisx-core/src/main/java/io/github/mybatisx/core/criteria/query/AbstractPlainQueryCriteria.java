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
import io.github.mybatisx.base.constant.NullPrecedence;
import io.github.mybatisx.core.criteria.support.AbstractPlainCriteria;
import io.github.mybatisx.core.support.group.Group;
import io.github.mybatisx.core.support.group.MultiGroup;
import io.github.mybatisx.core.support.group.SingleGroup;
import io.github.mybatisx.core.support.order.MultiOrder;
import io.github.mybatisx.core.support.order.Order;
import io.github.mybatisx.core.support.order.SingleOrder;
import io.github.mybatisx.core.support.select.SelectType;
import io.github.mybatisx.core.support.select.Selectable;
import io.github.mybatisx.core.support.select.StandardSelectable;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;

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
    public C containsFunction() {
        return this.containsFunction(true);
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
