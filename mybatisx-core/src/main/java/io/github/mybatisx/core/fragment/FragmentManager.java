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

import io.github.mybatisx.base.criterion.Criterion;
import io.github.mybatisx.base.fragment.Fragment;
import io.github.mybatisx.core.support.group.Group;
import io.github.mybatisx.core.support.having.Having;
import io.github.mybatisx.core.support.order.Order;
import io.github.mybatisx.core.support.select.FunctionSelectable;
import io.github.mybatisx.core.support.select.Selectable;

import java.util.Collection;
import java.util.List;

/**
 * 片段管理器
 *
 * @author wvkity
 * @created 2022/1/7
 * @since 1.0.0
 */
public interface FragmentManager extends Fragment {

    /**
     * 添加条件
     *
     * @param condition {@link Criterion}
     */
    void addCondition(final Criterion condition);

    /**
     * 添加多个条件
     *
     * @param conditions 条件列表
     */
    void addConditions(final Collection<Criterion> conditions);

    /**
     * 添加查询列
     *
     * @param selectable {@link Selectable}
     */
    void addSelect(final Selectable selectable);

    /**
     * 添加多个查询列
     *
     * @param selectables {@link Selectable}列表
     */
    void addSelects(final Collection<Selectable> selectables);

    /**
     * 添加排除查询属性
     *
     * @param property 属性名
     */
    void addExcludeProperty(final String property);

    /**
     * 添加排除查询属性
     *
     * @param properties 属性名列表
     */
    void addExcludeProperties(final Collection<String> properties);

    /**
     * 添加排除查询字段
     *
     * @param column 字段名
     */
    void addExcludeColumn(final String column);

    /**
     * 添加排除查询字段
     *
     * @param columns 字段名列表
     */
    void addExcludeColumns(final Collection<String> columns);

    /**
     * 根据聚合函数别名获取{@link FunctionSelectable}对象
     *
     * @param alias 聚合函数别名
     * @return {@link FunctionSelectable}
     */
    FunctionSelectable getFunction(final String alias);

    /**
     * 添加分组字段
     *
     * @param group {@link Group}
     */
    void addGroup(final Group group);

    /**
     * 添加多个分组字段
     *
     * @param groups {@link Group}列表
     */
    void addGroups(final List<Group> groups);

    /**
     * 添加分组筛选条件
     *
     * @param having {@link Having}
     */
    void addHaving(final Having having);

    /**
     * 添加多个分组筛选条件
     *
     * @param havingList {@link Having}列表
     */
    void addHaving(final List<Having> havingList);

    /**
     * 添加排序
     *
     * @param order {@link Order}
     */
    void addOrder(final Order order);

    /**
     * 添加多个排序
     *
     * @param orders {@link Order}列表
     */
    void addOrders(final List<Order> orders);

    /**
     * 检查是否存在条件
     *
     * @return boolean
     */
    boolean hasCondition();

    /**
     * 是否存在查询列
     *
     * @return boolean
     */
    boolean hasSelect();

    /**
     * 检查是否存在排序
     *
     * @return boolean
     */
    boolean hasSort();

    /**
     * 是否已缓存
     *
     * @return boolean
     */
    boolean isCached();

    /**
     * 获取条件列表
     *
     * @return 条件列表
     */
    List<Criterion> getConditions();

    /**
     * 获取所有查询列
     *
     * @return {@link Selectable}列表
     */
    List<Selectable> getSelects();

    /**
     * 检查是否存在片段
     *
     * @return boolean
     */
    default boolean hasFragment() {
        return this.hasCondition();
    }

    /**
     * 获取条件SQL语句
     *
     * @return 条件语句
     */
    String getWhereString();

    /**
     * 获取查询列语句
     *
     * @return 查询列语句
     */
    default String getSelectString() {
        return this.getSelectString(true);
    }

    /**
     * 获取查询列语句
     *
     * @param isQuery 是否为查询
     * @return 查询列语句
     */
    String getSelectString(final boolean isQuery);

    /**
     * 获取分组语句
     *
     * @return 分组语句
     */
    String getGroupString();

    /**
     * 获取分组筛选语句
     *
     * @return 分组筛选语句
     */
    String getHavingString();

    /**
     * 获取排序语句
     *
     * @return 排序语句
     */
    String getOrderString();

    @Override
    default String getFragment() {
        return this.getCompleteString(null);
    }

    /**
     * 获取完整SQL语句
     *
     * @param groupReplacement 分组替换语句
     * @return 完整SQL语句
     */
    String getCompleteString(final String groupReplacement);
}
