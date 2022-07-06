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
import io.github.mybatisx.base.constant.Join;
import io.github.mybatisx.core.criteria.AbstractBaseCriteria;
import io.github.mybatisx.core.criteria.Category;
import io.github.mybatisx.core.sql.QuerySqlManager;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 子外关联查询条件
 *
 * @author wvkity
 * @created 2022/2/22
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlainSubForeignImplementor extends AbstractPlainSubJoinable<PlainSubForeignImplementor> {

    private static final long serialVersionUID = -6667438445277553745L;

    public PlainSubForeignImplementor(Query<?> reference, Query<?> right, Join join) {
        this(reference, right, null, join);
    }

    public PlainSubForeignImplementor(Query<?> reference, Query<?> right, String alias, Join join) {
        Objects.requireNonNull(reference, "The reference query object cannot be null");
        Objects.requireNonNull(right, "The right query object cannot be null");
        this.reference = reference;
        this.outerQuery = right;
        this.join = join;
        this.category = Category.QUERY;
        this.clone((AbstractBaseCriteria<?>) reference, false);
        this.useAlias();
        this.defaultAlias = this.genDefaultAlias();
        this.aliasRef = new AtomicReference<>(Strings.isNotWhitespace(alias) ? alias : Constants.EMPTY);
        this.associations = new LinkedHashSet<>();
        this.sqlManager = new QuerySqlManager(this, this.outerQuery, this.associations, this.fragmentManager);
    }

    @Override
    protected PlainSubForeignImplementor newInstance() {
        final PlainSubForeignImplementor it = new PlainSubForeignImplementor();
        it.category = Category.QUERY;
        it.reference = this.reference;
        it.outerQuery = this.outerQuery;
        it.join = this.join;
        it.clone(this);
        return it;
    }

    ///// static methods /////

    /**
     * 创建{@link PlainSubForeignImplementor}对象
     *
     * @param reference {@link Query}
     * @param right     {@link Query}
     * @param join      {@link Join}
     * @return {@link PlainSubForeignImplementor}
     */
    public static PlainSubForeignImplementor from(final Query<?> reference, final Query<?> right, final Join join) {
        return from(reference, right, null, join);
    }

    /**
     * 创建{@link PlainSubForeignImplementor}对象
     *
     * @param reference {@link Query}
     * @param right     {@link Query}
     * @param alias     别名
     * @param join      {@link Join}
     * @return {@link PlainSubForeignImplementor}
     */
    public static PlainSubForeignImplementor from(final Query<?> reference, final Query<?> right, final String alias,
                                                  final Join join) {
        return new PlainSubForeignImplementor(reference, right, alias, join);
    }

    /**
     * 创建{@link PlainSubForeignImplementor}对象
     *
     * @param reference  {@link Query}
     * @param right      {@link Query}
     * @param join       {@link Join}
     * @param onConsumer {@link Consumer}
     * @return {@link PlainSubForeignImplementor}
     */
    public static PlainSubForeignImplementor from(final Query<?> reference, final Query<?> right, final Join join,
                                                  final Consumer<PlainSubForeignImplementor> onConsumer) {
        return from(reference, right, null, join, onConsumer);
    }

    /**
     * 创建{@link PlainSubForeignImplementor}对象
     *
     * @param reference  {@link Query}
     * @param right      {@link Query}
     * @param alias      别名
     * @param join       {@link Join}
     * @param onConsumer {@link Consumer}
     * @return {@link PlainSubForeignImplementor}
     */
    public static PlainSubForeignImplementor from(final Query<?> reference, final Query<?> right, final String alias,
                                                  final Join join, final Consumer<PlainSubForeignImplementor> onConsumer) {
        final PlainSubForeignImplementor it = from(reference, right, alias, join);
        if (onConsumer != null) {
            onConsumer.accept(it);
        }
        return it;
    }

    /**
     * 创建{@link PlainSubForeignImplementor}对象
     *
     * @param reference  {@link Query}
     * @param right      {@link Query}
     * @param join       {@link Join}
     * @param onConsumer {@link BiConsumer}
     * @param <T>        实体类型
     * @param <R>        {@link Query}
     * @return {@link PlainSubForeignImplementor}
     */
    public static <T, R extends Query<T>> PlainSubForeignImplementor from(final R reference, final Query<?> right, final Join join,
                                                                          final BiConsumer<PlainSubForeignImplementor, R> onConsumer) {
        return from(reference, right, null, join, onConsumer);
    }

    /**
     * 创建{@link PlainSubForeignImplementor}对象
     *
     * @param reference  {@link Query}
     * @param right      {@link Query}
     * @param alias      别名
     * @param join       {@link Join}
     * @param onConsumer {@link BiConsumer}
     * @param <T>        实体类型
     * @param <R>        {@link Query}
     * @return {@link PlainSubForeignImplementor}
     */
    public static <T, R extends Query<T>> PlainSubForeignImplementor from(final R reference, final Query<?> right, final String alias, final Join join,
                                                                          final BiConsumer<PlainSubForeignImplementor, R> onConsumer) {
        final PlainSubForeignImplementor it = from(reference, right, alias, join);
        if (onConsumer != null) {
            onConsumer.accept(it, reference);
        }
        return it;
    }

}
