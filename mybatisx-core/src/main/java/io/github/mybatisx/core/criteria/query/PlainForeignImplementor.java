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
 * 关联查询条件
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2022/2/11
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlainForeignImplementor<T> extends AbstractPlainJoinable<T, PlainForeignImplementor<T>> {

    private static final long serialVersionUID = 7258747642877419163L;

    public PlainForeignImplementor(Query<?> reference, Class<T> entity, Join join) {
        this(reference, entity, null, join);
    }

    public PlainForeignImplementor(Query<?> reference, Class<T> entity, String alias, Join join) {
        Objects.requireNonNull(reference, "The reference query object cannot be null");
        this.entity = entity;
        this.reference = reference;
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
    protected PlainForeignImplementor<T> newInstance() {
        final PlainForeignImplementor<T> it = new PlainForeignImplementor<>();
        it.reference = this.reference;
        it.join = this.join;
        it.clone(this);
        return it;
    }

    ///// static methods /////

    /**
     * 创建{@link PlainForeignImplementor}对象
     *
     * @param reference {@link Query}
     * @param entity    实体类
     * @param join      {@link Join}
     * @param <S>       实体类型
     * @return {@link PlainForeignImplementor}
     */
    public static <S> PlainForeignImplementor<S> from(final Query<?> reference, final Class<S> entity,
                                                      final Join join) {
        return from(reference, entity, null, join);
    }

    /**
     * 创建{@link PlainForeignImplementor}对象
     *
     * @param reference {@link Query}
     * @param entity    实体类
     * @param alias     别名
     * @param join      {@link Join}
     * @param <S>       实体类型
     * @return {@link PlainForeignImplementor}
     */
    public static <S> PlainForeignImplementor<S> from(final Query<?> reference, final Class<S> entity,
                                                      final String alias, final Join join) {
        return from(reference, entity, alias, join, (Consumer<PlainForeignImplementor<S>>) null);
    }

    /**
     * 创建{@link PlainForeignImplementor}对象
     *
     * @param reference  {@link Query}
     * @param entity     实体类
     * @param join       {@link Join}
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@link PlainForeignImplementor}
     */
    public static <S> PlainForeignImplementor<S> from(final Query<?> reference, final Class<S> entity, final Join join,
                                                      final Consumer<PlainForeignImplementor<S>> onConsumer) {
        return from(reference, entity, null, join, onConsumer);
    }

    /**
     * 创建{@link PlainForeignImplementor}对象
     *
     * @param reference  {@link Query}
     * @param entity     实体类
     * @param alias      别名
     * @param join       {@link Join}
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@link PlainForeignImplementor}
     */
    public static <S> PlainForeignImplementor<S> from(final Query<?> reference, final Class<S> entity,
                                                      final String alias, final Join join,
                                                      final Consumer<PlainForeignImplementor<S>> onConsumer) {
        final PlainForeignImplementor<S> it = new PlainForeignImplementor<>(reference, entity, alias, join);
        if (onConsumer != null) {
            onConsumer.accept(it);
        }
        return it;
    }

    /**
     * 创建{@link PlainForeignImplementor}对象
     *
     * @param reference  {@link Query}
     * @param entity     实体类
     * @param join       {@link Join}
     * @param onConsumer {@link BiConsumer}
     * @param <S>        实体类型
     * @param <T>        实体类型
     * @param <R>        {@link Query}
     * @return {@link PlainForeignImplementor}
     */
    public static <S, T, R extends Query<T>> PlainForeignImplementor<S> from(final R reference, final Class<S> entity, final Join join,
                                                                             final BiConsumer<PlainForeignImplementor<S>, R> onConsumer) {
        return from(reference, entity, null, join, onConsumer);
    }

    /**
     * 创建{@link PlainForeignImplementor}对象
     *
     * @param reference  {@link Query}
     * @param entity     实体类
     * @param alias      别名
     * @param join       {@link Join}
     * @param onConsumer {@link BiConsumer}
     * @param <S>        实体类型
     * @param <T>        实体类型
     * @param <R>        {@link Query}
     * @return {@link PlainForeignImplementor}
     */
    public static <S, T, R extends Query<T>> PlainForeignImplementor<S> from(final R reference, final Class<S> entity,
                                                                             final String alias, final Join join,
                                                                             final BiConsumer<PlainForeignImplementor<S>, R> onConsumer) {
        final PlainForeignImplementor<S> it = new PlainForeignImplementor<>(reference, entity, alias, join);
        if (onConsumer != null) {
            onConsumer.accept(it, reference);
        }
        return it;
    }

}
