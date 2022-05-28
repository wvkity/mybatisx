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
import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.core.criteria.AbstractBaseCriteria;
import io.github.mybatisx.core.criteria.Category;
import io.github.mybatisx.core.sql.QuerySqlManager;
import io.github.mybatisx.lang.Strings;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * 通用查询条件
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2022/1/25
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenericQueryImplementor<T> extends AbstractGenericQueryCriteria<T, GenericQueryImplementor<T>> {

    private static final long serialVersionUID = 6929608063788306116L;

    public GenericQueryImplementor(Class<T> entity) {
        this(entity, null);
    }

    public GenericQueryImplementor(Class<T> entity, String alias) {
        this.entity = entity;
        this.newQueryInit(alias);
        this.associations = new LinkedHashSet<>();
        this.sqlManager = new QuerySqlManager(this, this.outerQuery, this.associations, this.fragmentManager);
    }

    protected GenericQueryImplementor(final Criteria<?> outerCriteria, final Class<T> entity, final String alias) {
        this.entity = entity;
        this.category = Category.QUERY;
        this.clone((AbstractBaseCriteria<?>) outerCriteria, this, false);
        this.defaultAlias = this.genDefaultAlias();
        this.aliasRef = new AtomicReference<>(Strings.isNotWhitespace(alias) ? alias : Constants.EMPTY);
        this.associations = new LinkedHashSet<>();
        this.sqlManager = new QuerySqlManager(this, this.outerQuery, this.associations, this.fragmentManager);
    }

    @Override
    protected GenericQueryImplementor<T> newInstance() {
        final GenericQueryImplementor<T> it = new GenericQueryImplementor<>();
        it.clone(this);
        return it;
    }

    ///// static methods /////

    /**
     * 创建{@link GenericQueryImplementor}
     *
     * @param entity 实体类
     * @param <T>    实体类型
     * @return {@link GenericQueryImplementor}
     */
    public static <T> GenericQueryImplementor<T> from(final Class<T> entity) {
        return from(entity, null, null);
    }

    /**
     * 创建{@link GenericQueryImplementor}
     *
     * @param entity 实体类
     * @param alias  表别名
     * @param <T>    实体类型
     * @return {@link GenericQueryImplementor}
     */
    public static <T> GenericQueryImplementor<T> from(final Class<T> entity, final String alias) {
        return from(entity, alias, null);
    }

    /**
     * 创建{@link GenericQueryImplementor}
     *
     * @param entity 实体类
     * @param action {@link Consumer}
     * @param <T>    实体类型
     * @return {@link GenericQueryImplementor}
     */
    public static <T> GenericQueryImplementor<T> from(final Class<T> entity,
                                                      final Consumer<GenericQueryImplementor<T>> action) {
        return from(entity, null, action);
    }

    /**
     * 创建{@link GenericQueryImplementor}
     *
     * @param entity 实体类
     * @param alias  表别名
     * @param action {@link Consumer}
     * @param <T>    实体类型
     * @return {@link GenericQueryImplementor}
     */
    public static <T> GenericQueryImplementor<T> from(final Class<T> entity, final String alias,
                                                      final Consumer<GenericQueryImplementor<T>> action) {
        final GenericQueryImplementor<T> it = new GenericQueryImplementor<>(entity, alias);
        if (action != null) {
            action.accept(it);
        }
        return it;
    }

    /**
     * 创建{@link GenericQueryImplementor}
     *
     * @param outerCriteria {@link Criteria}
     * @param entity        实体类
     * @param <T>           实体类型
     * @return {@link GenericQueryImplementor}
     */
    public static <T> GenericQueryImplementor<T> from(final Criteria<?> outerCriteria, final Class<T> entity) {
        return from(outerCriteria, entity, null, null);
    }

    /**
     * 创建{@link GenericQueryImplementor}
     *
     * @param outerCriteria {@link Criteria}
     * @param entity        实体类
     * @param alias         别名
     * @param <T>           实体类型
     * @return {@link GenericQueryImplementor}
     */
    public static <T> GenericQueryImplementor<T> from(final Criteria<?> outerCriteria, final Class<T> entity, final String alias) {
        return from(outerCriteria, entity, alias, null);
    }

    /**
     * 创建{@link GenericQueryImplementor}
     *
     * @param outerCriteria {@link Criteria}
     * @param entity        实体类
     * @param action        {@link Consumer}
     * @param <T>           实体类型
     * @return {@link GenericQueryImplementor}
     */
    public static <T> GenericQueryImplementor<T> from(final Criteria<?> outerCriteria, final Class<T> entity,
                                                      final Consumer<GenericQueryImplementor<T>> action) {
        return from(outerCriteria, entity, null, action);
    }

    /**
     * 创建{@link GenericQueryImplementor}
     *
     * @param outerCriteria {@link Criteria}
     * @param entity        实体类
     * @param alias         别名
     * @param action        {@link Consumer}
     * @param <T>           实体类型
     * @return {@link GenericQueryImplementor}
     */
    public static <T> GenericQueryImplementor<T> from(final Criteria<?> outerCriteria, final Class<T> entity, final String alias,
                                                      final Consumer<GenericQueryImplementor<T>> action) {
        final GenericQueryImplementor<T> it = new GenericQueryImplementor<>(outerCriteria, entity, alias);
        if (action != null) {
            action.accept(it);
        }
        return it;
    }
}
