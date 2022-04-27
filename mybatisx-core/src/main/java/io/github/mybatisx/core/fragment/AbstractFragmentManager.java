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
package io.github.mybatisx.core.fragment;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.convert.ParameterConverter;
import io.github.mybatisx.base.convert.PlaceholderConverter;
import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.base.criterion.Criterion;
import io.github.mybatisx.base.part.Part;
import io.github.mybatisx.core.support.group.Group;
import io.github.mybatisx.core.support.having.Having;
import io.github.mybatisx.core.support.order.Order;
import io.github.mybatisx.core.support.select.FunctionSelectable;
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
     * 分组片段存储器
     */
    protected final GroupStorage groupStorage;
    /**
     * 分组筛选片段存储器
     */
    protected final HavingStorage havingStorage;
    /**
     * 排序存储器
     */
    protected final OrderStorage orderStorage;
    /**
     * 尾部SQL片段存储器
     */
    protected final TailPartStorage tailPartStorage;

    public AbstractFragmentManager(Criteria<?> criteria, ParameterConverter parameterConverter,
                                   PlaceholderConverter placeholderConverter) {
        this(criteria, new ConditionStorage(parameterConverter, placeholderConverter), new SelectableStorage(criteria),
                new GroupStorage(), new HavingStorage(parameterConverter, placeholderConverter), new OrderStorage(),
                new TailPartStorage(parameterConverter, placeholderConverter));
    }

    @Override
    public void addCondition(Criterion condition) {
        this.conditionStorage.add(condition);
    }

    @Override
    public void addConditions(Collection<? extends Criterion> conditions) {
        this.conditionStorage.addAll(conditions);
    }

    @Override
    public void addSelect(Selectable selectable) {
        this.selectableStorage.addSelect(selectable);
    }

    @Override
    public void addSelects(Collection<? extends Selectable> selectables) {
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
    public FunctionSelectable getFunction(String alias) {
        if (this.selectableStorage != null) {
            return this.selectableStorage.getFunction(alias);
        }
        return null;
    }

    @Override
    public void addGroup(Group group) {
        this.groupStorage.add(group);
    }

    @Override
    public void addGroups(List<? extends Group> groups) {
        this.groupStorage.addAll(groups);
    }

    @Override
    public void addHaving(Having having) {
        this.havingStorage.add(having);
    }

    @Override
    public void addHaving(List<? extends Having> havingList) {
        this.havingStorage.addAll(havingList);
    }

    @Override
    public void addOrder(Order order) {
        this.orderStorage.add(order);
    }

    @Override
    public void addOrders(List<? extends Order> orders) {
        this.orderStorage.addAll(orders);
    }

    @Override
    public void addPart(Part part) {
        this.tailPartStorage.add(part);
    }

    @Override
    public void addParts(List<? extends Part> tailParts) {
        this.tailPartStorage.addAll(tailParts);
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
    public boolean hasPart() {
        return this.tailPartStorage != null && !this.tailPartStorage.isEmpty();
    }

    @Override
    public boolean isCached() {
        return this.selectableStorage != null && this.selectableStorage.isCached();
    }

    @Override
    public boolean hasFragment() {
        return this.hasCondition() || this.hasSelect() || (this.groupStorage != null && !this.groupStorage.isEmpty())
                || (this.havingStorage != null && !this.havingStorage.isEmpty()) || this.hasSort() || hasPart();
    }

    @Override
    public List<Criterion> getConditions() {
        return this.conditionStorage.getAll();
    }

    @Override
    public List<Selectable> getSelects() {
        if (this.selectableStorage != null) {
            return this.selectableStorage.getSelects();
        }
        return null;
    }

    @Override
    public String getWhereString() {
        return this.conditionStorage.getFragment();
    }

    @Override
    public String getSelectString(boolean isQuery) {
        if (this.selectableStorage != null) {
            return this.selectableStorage.getFragment(isQuery);
        }
        return Constants.EMPTY;
    }

    @Override
    public String getGroupString() {
        if (this.groupStorage != null) {
            return this.groupStorage.getFragment();
        }
        return Constants.EMPTY;
    }

    @Override
    public String getHavingString() {
        if (this.havingStorage != null) {
            return this.havingStorage.getFragment();
        }
        return Constants.EMPTY;
    }

    @Override
    public String getOrderString() {
        if (this.orderStorage != null) {
            return this.orderStorage.getFragment();
        }
        return Constants.EMPTY;
    }

    @Override
    public String getTailString() {
        if (this.tailPartStorage != null) {
            return this.tailPartStorage.getFragment();
        }
        return Constants.EMPTY;
    }

    @Override
    public String getCompleteString(String groupReplacement) {
        if (this.hasFragment()) {
            final StringBuilder sb = new StringBuilder(128);
            Strings.ifNotWhitespaceThen(this.getWhereString(), sb::append);
            if (Strings.isNotWhitespace(groupReplacement)) {
                sb.append(SqlSymbol.GROUP_BY_SPACE_BOTH).append(groupReplacement);
            } else {
                Strings.ifNotWhitespaceThen(this.getGroupString(), v -> sb.append(SqlSymbol.SPACE).append(v));
            }
            Strings.ifNotWhitespaceThen(this.getHavingString(), v -> sb.append(SqlSymbol.SPACE).append(v));
            Strings.ifNotWhitespaceThen(this.getOrderString(), v -> sb.append(SqlSymbol.SPACE).append(v));
            Strings.ifNotWhitespaceThen(this.getTailString(), v -> sb.append(SqlSymbol.SPACE).append(v));
            return sb.toString();
        }
        return Constants.EMPTY;
    }

}
