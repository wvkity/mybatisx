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
package io.github.mybatisx.core.management;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.base.criterion.Criterion;
import io.github.mybatisx.core.support.order.Order;
import io.github.mybatisx.core.support.select.Selectable;
import io.github.mybatisx.lang.Strings;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;

/**
 * 抽象片段管理器
 *
 * @author wvkity
 * @created 2022/1/7
 * @since 1.0.0
 */
@AllArgsConstructor
@SuppressWarnings({"serial"})
public abstract class AbstractFragmentManager implements FragmentManager {

    /**
     * {@link Criteria}
     */
    protected final Criteria<?> criteria;
    /**
     * 条件片段存储器
     */
    protected final ConditionStorage conditionStorage;
    /**
     * 查询列片段存储器
     */
    protected final SelectableStorage selectableStorage;
    /**
     * 排序存储器
     */
    protected final OrderStorage orderStorage;

    public AbstractFragmentManager(Criteria<?> criteria) {
        this(criteria, new ConditionStorage(), new SelectableStorage(criteria), new OrderStorage());
    }

    @Override
    public void addCondition(Criterion condition) {
        this.conditionStorage.add(condition);
    }

    @Override
    public void addConditions(Collection<Criterion> conditions) {
        this.conditionStorage.addAll(conditions);
    }

    @Override
    public void addSelect(Selectable selectable) {
        this.selectableStorage.addSelect(selectable);
    }

    @Override
    public void addSelects(Collection<Selectable> selectables) {
        this.selectableStorage.addSelects(selectables);
    }

    @Override
    public void addExcludeProperty(String property) {
        this.selectableStorage.addExcludeProperty(property);
    }

    @Override
    public void addExcludeProperties(Collection<String> properties) {
        this.selectableStorage.addExcludeProperties(properties);
    }

    @Override
    public void addExcludeColumn(String column) {
        this.selectableStorage.addExcludeColumn(column);
    }

    @Override
    public void addExcludeColumns(Collection<String> columns) {
        this.selectableStorage.addExcludeColumns(columns);
    }

    @Override
    public void addOrder(Order order) {
        this.orderStorage.add(order);
    }

    @Override
    public void addOrders(List<Order> orders) {
        this.orderStorage.addAll(orders);
    }

    @Override
    public boolean hasCondition() {
        return !this.conditionStorage.isEmpty();
    }

    @Override
    public boolean hasSelect() {
        return !this.selectableStorage.isEmpty();
    }

    @Override
    public boolean hasSort() {
        return this.orderStorage != null && !this.orderStorage.isEmpty();
    }

    @Override
    public boolean isCached() {
        return this.selectableStorage != null && this.selectableStorage.isCached();
    }

    @Override
    public boolean hasFragment() {
        return this.hasCondition() || this.hasSelect();
    }

    @Override
    public List<Criterion> getConditions() {
        return this.conditionStorage.getAll();
    }

    @Override
    public List<Selectable> getSelects() {
        return this.selectableStorage.getSelects();
    }

    @Override
    public String getWhereString() {
        return this.conditionStorage.getFragment();
    }

    @Override
    public String getSelectString(boolean isQuery) {
        return this.selectableStorage.getFragment(isQuery);
    }

    @Override
    public String getGroupString() {
        return null;
    }

    @Override
    public String getHavingString() {
        return null;
    }

    @Override
    public String getOrderString() {
        if (this.orderStorage != null) {
            return this.orderStorage.getFragment();
        }
        return Constants.EMPTY;
    }

    @Override
    public String getCompleteString(String groupReplacement) {
        if (this.hasFragment()) {
            final StringBuilder sb = new StringBuilder(128);
            final String condition = this.getWhereString();
            if (Strings.isNotWhitespace(condition)) {
                sb.append(condition);
            }
            if (Strings.isNotWhitespace(groupReplacement)) {
                sb.append(SqlSymbol.GROUP_BY_SPACE_BOTH).append(groupReplacement);
            } else {
                final String group = this.getGroupString();
                if (Strings.isNotWhitespace(group)) {
                    sb.append(SqlSymbol.SPACE).append(group);
                }
            }
            final String having = this.getHavingString();
            if (Strings.isNotWhitespace(having)) {
                sb.append(SqlSymbol.SPACE).append(having);
            }
            final String order = this.getOrderString();
            if (Strings.isNotWhitespace(order)) {
                sb.append(SqlSymbol.SPACE).append(order);
            }
            return sb.toString();
        }
        return Constants.EMPTY;
    }

}
