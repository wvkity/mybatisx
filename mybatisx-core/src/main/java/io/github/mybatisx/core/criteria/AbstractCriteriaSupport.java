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
package io.github.mybatisx.core.criteria;

import com.google.common.collect.ImmutableList;
import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.dialect.Dialect;
import io.github.mybatisx.core.criteria.query.Joinable;
import io.github.mybatisx.core.criteria.query.Query;
import io.github.mybatisx.core.fragment.FragmentManager;
import io.github.mybatisx.core.support.select.SelectType;
import io.github.mybatisx.core.support.select.Selectable;
import io.github.mybatisx.core.support.select.StandardSelectable;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.util.Collections;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * 抽象条件
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
@Slf4j
@SuppressWarnings({"serial"})
public abstract class AbstractCriteriaSupport<T, C extends CriteriaWrapper<T, C>> extends
        AbstractConditionAcceptSupport<T, C> {

    // region Base fields
    /**
     * 属性是否作为字段别名
     */
    protected boolean propertyAsAlias;
    /**
     * 引用属性
     */
    protected AtomicReference<String> propRef = new AtomicReference<>(Constants.EMPTY);
    /**
     * 查询列是否额外添加
     */
    protected boolean extra;
    /**
     * 是否继承属性
     */
    protected boolean inherit;
    /**
     * 是否去重
     */
    protected boolean distinct;
    /**
     * 是否只查询聚合函数
     */
    protected boolean onlyQueryFunction;
    /**
     * 是否包含聚合函数
     */
    protected boolean containsFunction = true;
    /**
     * 所有列是否分组
     */
    protected boolean groupAll;
    /**
     * 是否保留排序
     */
    protected boolean keepOrderly;
    /**
     * 是否抓取关联表字段
     */
    protected boolean fetch;
    /**
     * 嵌套/子查询
     */
    protected Query<?> outerQuery;
    /**
     * 联表查询对象
     */
    protected Set<Joinable<?>> associations;

    // endregion

    // region EmbeddableResult fields
    /**
     * resultMap标识
     */
    protected String resultMap;
    /**
     * 返回值类型
     */
    protected Class<?> returnType;
    /**
     * {@link Map}返回值键
     */
    protected String mapKey;
    /**
     * {@link Map}实现类
     */
    @SuppressWarnings({"rawtypes"})
    protected Class<? extends Map> mapType;
    // endregion

    // region Protected  methods

    /**
     * 获取表别名
     *
     * @param force 表别名
     * @return 表别名
     */
    protected String getAlias(final boolean force) {
        if (force || this.useAlias.get()) {
            final String _$alias = this.aliasRef.get();
            return Strings.isNotWhitespace(_$alias) ? _$alias : this.defaultAlias;
        }
        return Constants.EMPTY;
    }

    /**
     * 获取所有查询列
     *
     * @return {@link Selectable}列表
     */
    protected List<Selectable> getSelects() {
        final boolean hasOuter = this.outerQuery != null;
        final List<Selectable> selects = new ArrayList<>();
        final FragmentManager _$manager = this.fragmentManager;
        if (hasOuter) {
            if (_$manager.hasSelect() && _$manager.isCached()) {
                return _$manager.getSelects();
            }
            final List<Selectable> selectables = this.outerQuery.fetchSelects();
            if (Collections.isNotEmpty(selectables)) {
                selects.addAll(selectables);
            }
        } else {
            final List<Selectable> selectables = _$manager.getSelects();
            if (Collections.isNotEmpty(selectables)) {
                selects.addAll(selectables);
            }
        }
        if (Collections.isNotEmpty(this.associations)) {
            for (Joinable<?> it : this.associations) {
                if (it != null && (it.hasSelect() || it.isFetch())) {
                    final List<Selectable> ass = it.fetchSelects();
                    if (Collections.isNotEmpty(ass)) {
                        selects.addAll(ass);
                    }
                }
            }
        }
        if (hasOuter) {
            return transform((Query<?>) this, selects);
        }
        return ImmutableList.copyOf(selects);
    }

    /**
     * 查询列转换
     *
     * @param to          {@link Query}
     * @param selectables {@link Selectable}列表
     * @return {@link Selectable}列表
     */
    protected List<Selectable> transform(final Query<?> to, final List<Selectable> selectables) {
        final List<Selectable> selects = new ArrayList<>(selectables.size());
        for (Selectable it : selectables) {
            final String as = it.getAlias();
            final String column = it.getColumn();
            final StandardSelectable.StandardSelectableBuilder builder = StandardSelectable.builder()
                    .query(to)
                    .type(SelectType.PLAIN);
            final boolean notAs = Strings.isWhitespace(as);
            if (notAs) {
                final Query<?> query;
                if (Objects.nonNull((query = it.getQuery())) && query.isPropAsAlias()) {
                    builder.column(it.getProperty());
                } else if (this.inherit) {
                    builder.column(column).alias(it.getProperty());
                } else {
                    builder.column(column);
                }
            } else {
                builder.column(as);
            }
            final StandardSelectable newSelectable = builder.build();
            to.select(newSelectable);
            selects.add(newSelectable);
        }
        return ImmutableList.copyOf(selects);
    }

    // endregion

    // region Override methods


    @Override
    public C chain(Consumer<C> action) {
        if (action != null) {
            action.accept(this.context);
        }
        return this.context;
    }

    @Override
    public String as() {
        return this.getAlias(false);
    }

    @Override
    public C strict(boolean strict) {
        this.nonMatchingThenThrows.set(strict);
        return this.context;
    }

    @Override
    public C dialect(Dialect dialect) {
        this.dialect = dialect;
        return this.context;
    }

    @Override
    public C setVersion(Object value) {
        return this.context;
    }

    // endregion

}
