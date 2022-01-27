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

import io.github.mybatisx.core.criteria.BaseCriteria;
import io.github.mybatisx.core.support.group.Group;
import io.github.mybatisx.core.support.order.Order;
import io.github.mybatisx.core.support.select.Selectable;
import io.github.mybatisx.embedded.EmbeddableResult;

import java.util.List;

/**
 * 查询条件接口
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2021/12/23
 * @since 1.0.0
 */
public interface Query<T> extends BaseCriteria<T>, EmbeddableResult {

    /**
     * 设置表别名
     *
     * @param alias 表别名
     * @return {@code this}
     */
    Query<T> as(final String alias);

    /**
     * 获取表别名
     *
     * @param force 是否强制使用
     * @return 表别名
     */
    String as(final boolean force);

    /**
     * 使用表别名
     *
     * @return {@code this}
     */
    Query<T> useAlias();

    /**
     * 设置是否使用表别名
     *
     * @param using 是否使用
     * @return {@code this}
     */
    Query<T> useAlias(final boolean using);

    /**
     * 设置属性名作为字段别名
     *
     * @return {@code this}
     */
    Query<T> propAsAlias();

    /**
     * 设置属性名是否作为字段别名
     *
     * @param using 是否使用
     * @return {@code this}
     */
    Query<T> propAsAlias(final boolean using);

    /**
     * 属性名是否作为别名
     *
     * @return boolean
     */
    boolean isPropAsAlias();

    /**
     * 设置引用属性
     *
     * @param reference 引用属性
     * @return {@code this}
     */
    Query<T> reference(final String reference);

    /**
     * 设置查询列是否为额外添加
     *
     * @param extra 是否为额外添加
     * @return {@code this}
     */
    Query<T> extra(final boolean extra);

    /**
     * 查询列是否为额外添加
     *
     * @return boolean
     */
    boolean isExtra();

    /**
     * 设置继承属性
     *
     * @return {@code this}
     */
    Query<T> inherit();

    /**
     * 设置是否继承属性
     *
     * @param inherit 是否继承
     * @return {@code this}
     */
    Query<T> inherit(final boolean inherit);

    /**
     * 是否继承属性
     *
     * @return boolean
     */
    boolean isInherit();

    /**
     * 设置是否去重
     *
     * @param distinct 是否去重
     * @return {@code this}
     */
    Query<T> distinct(final boolean distinct);

    /**
     * 是否去重
     *
     * @return boolean
     */
    boolean isDistinct();

    /**
     * 设置只查询聚合函数
     *
     * @return {@code this}
     */
    Query<T> onlyQueryFunction();

    /**
     * 设置是否只查询聚合函数
     *
     * @param only 是否只查询聚合函数
     * @return {@code this}
     */
    Query<T> onlyQueryFunction(final boolean only);

    /**
     * 是否只查询聚合函数
     *
     * @return boolean
     */
    boolean isOnlyQueryFunction();

    /**
     * 设置查询是否包含聚合函数
     *
     * @return {@code this}
     */
    Query<T> containsFunction();

    /**
     * 设置查询是否包含聚合函数
     *
     * @param contains 是否包含聚合函数
     * @return {@code this}
     */
    Query<T> containsFunction(final boolean contains);

    /**
     * 查询是否包含聚合函数
     *
     * @return boolean
     */
    boolean isContainsFunction();

    /**
     * 设置所有查列分组
     *
     * @return {@code this}
     */
    Query<T> groupAll();

    /**
     * 设置所有查列是否分组
     *
     * @param groupAll 是否所有列进行分组
     * @return {@code this}
     */
    Query<T> groupAll(final boolean groupAll);

    /**
     * 所有查询列是否分组
     *
     * @return boolean
     */
    boolean isGroupAll();

    /**
     * 保留排序
     *
     * @return {@code this}
     */
    Query<T> keepOrderly();

    /**
     * 设置是否保留排序
     *
     * @param keep 是否保留
     * @return {@code this}
     */
    Query<T> keepOrderly(final boolean keep);

    /**
     * 是否保留排序
     *
     * @return boolean
     */
    boolean isKeepOrderly();

    /**
     * 添加查询列
     *
     * @param selectable {@link Selectable}
     * @return {@code this}
     */
    Query<T> select(final Selectable selectable);

    /**
     * 添加查询列
     *
     * @param selectables {@link Selectable}列表
     * @return {@code this}
     */
    Query<T> selects(final Selectable... selectables);

    /**
     * 添加查询列
     *
     * @param selectables {@link Selectable}列表
     * @return {@code this}
     */
    Query<T> selects(final List<Selectable> selectables);

    /**
     * 添加分组
     *
     * @param group {@link Group}
     * @return {@code this}
     */
    Query<T> group(final Group group);

    /**
     * 添加多个分组
     *
     * @param groups 分组列表
     * @return {@code this}
     */
    Query<T> groups(final Group... groups);

    /**
     * 添加多个分组
     *
     * @param groups 分组列表
     * @return {@code this}
     */
    Query<T> groups(final List<Group> groups);

    /**
     * 添加排序
     *
     * @param order {@link Order}
     * @return {@code this}
     */
    Query<T> order(final Order order);

    /**
     * 添加多个排序
     *
     * @param orders {@link Order}列表
     * @return {@code this}
     */
    Query<T> orders(final Order... orders);

    /**
     * 添加多个排序
     *
     * @param orders {@link Order}列表
     * @return {@code this}
     */
    Query<T> orders(final List<Order> orders);

    /**
     * 获取查询列
     *
     * @return {@link Selectable}列表
     */
    List<Selectable> fetchSelects();

    /**
     * 是否存在查询列
     *
     * @return boolean
     */
    boolean hasSelect();

    /**
     * 获取查询字段片段
     *
     * @return 查询字段片段
     */
    String getSelectFragment();

    /**
     * 获取查询字段片段
     *
     * @param self 是否自身
     * @return 查询字段片段
     */
    String getSelectFragment(final boolean self);

    /**
     * 获取分组片段
     *
     * @return 分组片段
     */
    String getGroupFragment();

}
