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

import com.google.common.collect.ImmutableList;
import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.base.fragment.AbstractFragmentList;
import io.github.mybatisx.base.helper.TableHelper;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.core.criteria.query.Query;
import io.github.mybatisx.core.support.select.FunctionSelectable;
import io.github.mybatisx.core.support.select.SelectType;
import io.github.mybatisx.core.support.select.Selectable;
import io.github.mybatisx.core.support.select.StandardSelectable;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 查询列存储
 *
 * @author wvkity
 * @created 2022/1/18
 * @since 1.0.0
 */
public class SelectableStorage extends AbstractFragmentList<Selectable> {

    private static final long serialVersionUID = -5897670109565177537L;

    /**
     * {@link Query}
     */
    private final Query<?> query;
    /**
     * 聚合函数列表
     */
    private final List<Selectable> functionList = new ArrayList<>(6);
    /**
     * 普通查询列
     */
    private final List<Selectable> plainList = new ArrayList<>(6);
    /**
     * 聚合函数(Map<alias, AggFunction>)
     */
    private final Map<String, FunctionSelectable> aliasFunctionMap = new HashMap<>(8);
    /**
     * 忽略查询属性名
     */
    private final Set<String> excludeProperties = new HashSet<>(6);
    /**
     * 忽略查询字段名
     */
    private final Set<String> excludeColumns = new HashSet<>(6);
    /**
     * 是否已缓存
     */
    private final AtomicBoolean cached = new AtomicBoolean(false);
    /**
     * 片段缓存
     */
    private String fragmentCache = Constants.EMPTY;
    /**
     * 缓存
     */
    private List<Selectable> selectableCache;

    public SelectableStorage(Criteria<?> criteria) {
        this.query = (Query<?>) criteria;
    }

    /**
     * 处理新增查询列
     *
     * @param selectable {@link Selectable}
     */
    void handle(final Selectable selectable) {
        if (selectable != null) {
            final SelectType type = selectable.getType();
            if (type == SelectType.FUNCTION) {
                final FunctionSelectable it = (FunctionSelectable) selectable;
                final String funcAlias;
                if (Strings.isNotWhitespace((funcAlias = it.getAlias()))) {
                    this.aliasFunctionMap.putIfAbsent(funcAlias, it);
                }
                this.functionList.add(it);
            } else {
                this.plainList.add(selectable);
            }
            this.fragments.add(selectable);
            this.cached.set(false);
        }
    }

    /**
     * 添加查询列
     *
     * @param selectable {@link Selectable}
     */
    public void addSelect(final Selectable selectable) {
        this.handle(selectable);
    }

    /**
     * 添加多个查询列
     *
     * @param selectables {@link Selectable}列表
     */
    public void addSelects(final Collection<Selectable> selectables) {
        if (Objects.isNotEmpty(selectables)) {
            for (Selectable it : selectables) {
                this.addSelect(it);
            }
        }
    }

    @Override
    public void add(Selectable selectable) {
        this.addSelect(selectable);
    }

    @Override
    public void addAll(Collection<Selectable> c) {
        this.addSelects(c);
    }

    @Override
    public boolean isEmpty() {
        return Objects.isEmpty(this.fragments) && Objects.isEmpty(this.selectableCache);
    }

    /**
     * 检查是否已缓存
     *
     * @return boolean
     */
    public boolean isCached() {
        return this.cached.get();
    }

    /**
     * 添加排除查询属性
     *
     * @param property 属性名
     */
    public void addExcludeProperty(final String property) {
        if (Strings.isNotWhitespace(property)) {
            this.excludeProperties.add(property);
            this.cached.set(false);
        }
    }

    /**
     * 添加排除查询属性
     *
     * @param properties 属性名列表
     */
    public void addExcludeProperties(final Collection<String> properties) {
        if (Objects.isNotEmpty(properties)) {
            this.excludeProperties.addAll(properties);
            this.cached.set(false);
        }
    }

    /**
     * 添加排除查询字段
     *
     * @param column 字段名
     */
    public void addExcludeColumn(final String column) {
        if (Strings.isNotWhitespace(column)) {
            this.excludeColumns.add(column);
            this.cached.set(false);
        }
    }

    /**
     * 添加排除查询字段
     *
     * @param columns 字段名列表
     */
    public void addExcludeColumns(final Collection<String> columns) {
        if (Objects.isNotEmpty(columns)) {
            this.excludeColumns.addAll(columns);
            this.cached.set(false);
        }
    }

    /**
     * 获取所有查询列
     *
     * @return {@link Selectable}列表
     */
    public List<Selectable> getSelects() {
        if (this.cached.get()) {
            return ImmutableList.copyOf(this.selectableCache);
        }
        final List<Selectable> selects;
        final Query<?> _$query = this.query;
        if (!this.isEmpty()) {
            if (_$query.isOnlyQueryFunction()) {
                selects = new ArrayList<>(this.functionList);
            } else {
                if (_$query.isExtra()) {
                    selects = this.filtrate();
                } else {
                    selects = new ArrayList<>();
                }
                if (_$query.isContainsFunction()) {
                    selects.addAll(this.fragments);
                } else {
                    selects.addAll(this.plainList);
                }
            }
        } else {
            selects = this.filtrate();
        }
        if (Objects.isNotEmpty(selects)) {
            this.selectableCache = ImmutableList.copyOf(selects);
            this.cached.compareAndSet(false, true);
            return this.selectableCache;
        }
        return ImmutableList.of();
    }

    /**
     * 筛选
     *
     * @return {@link Selectable}列表
     */
    protected List<Selectable> filtrate() {
        final Table table = TableHelper.getTable(this.query.getEntity());
        if (table != null) {
            final List<Column> columns = table.getColumns();
            if (Objects.isNotEmpty(columns)) {
                return columns.stream()
                        .filter(it -> this.matches(this.excludeProperties, it.getProperty(), false)
                                && this.matches(this.excludeColumns, it.getColumn(), true))
                        .map(it -> StandardSelectable.builder()
                                .query(this.query)
                                .type(SelectType.STANDARD)
                                .property(it.getProperty())
                                .column(it.getColumn())
                                .build())
                        .collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }

    /**
     * 匹配
     *
     * @param sources    源数据
     * @param target     目标数据
     * @param ignoreCase 是否忽略大小写
     * @return boolean
     */
    protected boolean matches(final Collection<String> sources, final String target, final boolean ignoreCase) {
        if (Objects.isNotEmpty(sources)) {
            if (ignoreCase) {
                return sources.stream().noneMatch(target::equalsIgnoreCase);
            }
            return sources.stream().noneMatch(target::equals);
        }
        return true;
    }

    /**
     * 根据聚合函数别名获取{@link FunctionSelectable}
     *
     * @param alias 聚合函数别名
     * @return {@link FunctionSelectable}
     */
    public FunctionSelectable getFunction(final String alias) {
        if (Strings.isNotWhitespace(alias) && !this.aliasFunctionMap.isEmpty()) {
            return this.aliasFunctionMap.get(alias);
        }
        return null;
    }

    @Override
    public String getFragment() {
        if (this.cached.get() && Strings.isNotWhitespace(this.fragmentCache)) {
            return this.fragmentCache;
        }
        this.fragmentCache = this.getFragment(true);
        return this.fragmentCache;
    }

    /**
     * 获取查询列片段
     *
     * @param isQuery 是否为查询
     * @return 查询列片段
     */
    public String getFragment(final boolean isQuery) {
        final List<Selectable> selects = this.getSelects();
        if (!selects.isEmpty()) {
            if (isQuery) {
                return selects.stream()
                        .map(Selectable::getFragment)
                        .filter(Strings::isNotWhitespace)
                        .collect(Collectors.joining(SqlSymbol.COMMA_SPACE));
            } else {
                return selects.stream()
                        .filter(it -> it.getType() != SelectType.FUNCTION)
                        .map(it -> it.getFragment(false))
                        .filter(Strings::isNotWhitespace)
                        .collect(Collectors.joining(SqlSymbol.COMMA_SPACE));
            }
        }
        return Constants.EMPTY;
    }
}
