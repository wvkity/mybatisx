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

import io.github.mybatisx.base.constant.Join;
import io.github.mybatisx.core.support.function.AggFunction;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 查询接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/2/14
 * @since 1.0.0
 */
interface QueryMixture<T, C extends QueryMixture<T, C>> extends Query<T> {

    // region Lambda joinable

    /**
     * 左关联查询
     *
     * @param entity     实体类
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C leftLambda(final Class<S> entity, final Consumer<LambdaForeignImplementor<S>> onConsumer) {
        return this.leftLambda(entity, null, onConsumer);
    }

    /**
     * 左关联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C leftLambda(final Class<S> entity, final String alias,
                             final Consumer<LambdaForeignImplementor<S>> onConsumer) {
        return this.joinLambda(entity, alias, Join.LEFT, onConsumer);
    }

    /**
     * 左关联查询
     *
     * @param entity     实体类
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C leftLambda(final Class<S> entity, final BiConsumer<LambdaForeignImplementor<S>, C> onConsumer) {
        return this.leftLambda(entity, null, onConsumer);
    }

    /**
     * 左关联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C leftLambda(final Class<S> entity, final String alias,
                             final BiConsumer<LambdaForeignImplementor<S>, C> onConsumer) {
        return this.joinLambda(entity, alias, Join.LEFT, onConsumer);
    }


    /**
     * 内联查询
     *
     * @param entity     实体类
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C innerLambda(final Class<S> entity, final Consumer<LambdaForeignImplementor<S>> onConsumer) {
        return this.innerLambda(entity, null, onConsumer);
    }

    /**
     * 内联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C innerLambda(final Class<S> entity, final String alias,
                              final Consumer<LambdaForeignImplementor<S>> onConsumer) {
        return this.joinLambda(entity, alias, Join.INNER, onConsumer);
    }

    /**
     * 内联查询
     *
     * @param entity     实体类
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C innerLambda(final Class<S> entity, final BiConsumer<LambdaForeignImplementor<S>, C> onConsumer) {
        return this.innerLambda(entity, null, onConsumer);
    }

    /**
     * 内联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C innerLambda(final Class<S> entity, final String alias,
                              final BiConsumer<LambdaForeignImplementor<S>, C> onConsumer) {
        return this.joinLambda(entity, alias, Join.INNER, onConsumer);
    }

    /**
     * 关联查询
     *
     * @param entity     实体类
     * @param join       {@link Join}
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C joinLambda(final Class<S> entity, final Join join,
                             final Consumer<LambdaForeignImplementor<S>> onConsumer) {
        return this.joinLambda(entity, null, join, onConsumer);
    }

    /**
     * 关联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param join       {@link Join}
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    @SuppressWarnings("unchecked")
    default <S> C joinLambda(final Class<S> entity, final String alias, final Join join,
                             final Consumer<LambdaForeignImplementor<S>> onConsumer) {
        this.join(LambdaForeignImplementor.from(this, entity, alias, join, onConsumer));
        return (C) this;
    }

    /**
     * 关联查询
     *
     * @param entity     实体类
     * @param join       {@link Join}
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C joinLambda(final Class<S> entity, final Join join,
                             final BiConsumer<LambdaForeignImplementor<S>, C> onConsumer) {
        return this.joinLambda(entity, null, join, onConsumer);
    }

    /**
     * 关联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param join       {@link Join}
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    @SuppressWarnings("unchecked")
    default <S> C joinLambda(final Class<S> entity, final String alias, final Join join,
                             final BiConsumer<LambdaForeignImplementor<S>, C> onConsumer) {
        this.join(LambdaForeignImplementor.from((C) this, entity, alias, join, onConsumer));
        return (C) this;
    }

    // endregion

    // region Plain joinable

    /**
     * 左关联查询
     *
     * @param entity     实体类
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C leftPlain(final Class<S> entity, final Consumer<PlainForeignImplementor<S>> onConsumer) {
        return this.leftPlain(entity, null, onConsumer);
    }

    /**
     * 左关联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C leftPlain(final Class<S> entity, final String alias,
                            final Consumer<PlainForeignImplementor<S>> onConsumer) {
        return this.joinPlain(entity, alias, Join.LEFT, onConsumer);
    }

    /**
     * 左关联查询
     *
     * @param entity     实体类
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C leftPlain(final Class<S> entity, final BiConsumer<PlainForeignImplementor<S>, C> onConsumer) {
        return this.leftPlain(entity, null, onConsumer);
    }

    /**
     * 左关联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C leftPlain(final Class<S> entity, final String alias,
                            final BiConsumer<PlainForeignImplementor<S>, C> onConsumer) {
        return this.joinPlain(entity, alias, Join.LEFT, onConsumer);
    }


    /**
     * 内联查询
     *
     * @param entity     实体类
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C innerPlain(final Class<S> entity, final Consumer<PlainForeignImplementor<S>> onConsumer) {
        return this.innerPlain(entity, null, onConsumer);
    }

    /**
     * 内联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C innerPlain(final Class<S> entity, final String alias,
                             final Consumer<PlainForeignImplementor<S>> onConsumer) {
        return this.joinPlain(entity, alias, Join.INNER, onConsumer);
    }

    /**
     * 内联查询
     *
     * @param entity     实体类
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C innerPlain(final Class<S> entity, final BiConsumer<PlainForeignImplementor<S>, C> onConsumer) {
        return this.innerPlain(entity, null, onConsumer);
    }

    /**
     * 内联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C innerPlain(final Class<S> entity, final String alias,
                             final BiConsumer<PlainForeignImplementor<S>, C> onConsumer) {
        return this.joinPlain(entity, alias, Join.INNER, onConsumer);
    }

    /**
     * 关联查询
     *
     * @param entity     实体类
     * @param join       {@link Join}
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C joinPlain(final Class<S> entity, final Join join,
                            final Consumer<PlainForeignImplementor<S>> onConsumer) {
        return this.joinPlain(entity, null, join, onConsumer);
    }

    /**
     * 关联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param join       {@link Join}
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    @SuppressWarnings("unchecked")
    default <S> C joinPlain(final Class<S> entity, final String alias, final Join join,
                            final Consumer<PlainForeignImplementor<S>> onConsumer) {
        this.join(PlainForeignImplementor.from(this, entity, alias, join, onConsumer));
        return (C) this;
    }

    /**
     * 关联查询
     *
     * @param entity     实体类
     * @param join       {@link Join}
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C joinPlain(final Class<S> entity, final Join join,
                            final BiConsumer<PlainForeignImplementor<S>, C> onConsumer) {
        return this.joinPlain(entity, null, join, onConsumer);
    }

    /**
     * 关联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param join       {@link Join}
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    @SuppressWarnings("unchecked")
    default <S> C joinPlain(final Class<S> entity, final String alias, final Join join,
                            final BiConsumer<PlainForeignImplementor<S>, C> onConsumer) {
        this.join(PlainForeignImplementor.from((C) this, entity, alias, join, onConsumer));
        return (C) this;
    }

    // endregion

    // region Generic joinable

    /**
     * 左关联查询
     *
     * @param entity     实体类
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C leftGeneric(final Class<S> entity, final Consumer<GenericForeignImplementor<S>> onConsumer) {
        return this.leftGeneric(entity, null, onConsumer);
    }

    /**
     * 左关联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C leftGeneric(final Class<S> entity, final String alias,
                              final Consumer<GenericForeignImplementor<S>> onConsumer) {
        return this.joinGeneric(entity, alias, Join.LEFT, onConsumer);
    }

    /**
     * 左关联查询
     *
     * @param entity     实体类
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C leftGeneric(final Class<S> entity, final BiConsumer<GenericForeignImplementor<S>, C> onConsumer) {
        return this.leftGeneric(entity, null, onConsumer);
    }

    /**
     * 左关联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C leftGeneric(final Class<S> entity, final String alias,
                              final BiConsumer<GenericForeignImplementor<S>, C> onConsumer) {
        return this.joinGeneric(entity, alias, Join.LEFT, onConsumer);
    }


    /**
     * 内联查询
     *
     * @param entity     实体类
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C innerGeneric(final Class<S> entity, final Consumer<GenericForeignImplementor<S>> onConsumer) {
        return this.innerGeneric(entity, null, onConsumer);
    }

    /**
     * 内联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C innerGeneric(final Class<S> entity, final String alias,
                               final Consumer<GenericForeignImplementor<S>> onConsumer) {
        return this.joinGeneric(entity, alias, Join.INNER, onConsumer);
    }

    /**
     * 内联查询
     *
     * @param entity     实体类
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C innerGeneric(final Class<S> entity, final BiConsumer<GenericForeignImplementor<S>, C> onConsumer) {
        return this.innerGeneric(entity, null, onConsumer);
    }

    /**
     * 内联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C innerGeneric(final Class<S> entity, final String alias,
                               final BiConsumer<GenericForeignImplementor<S>, C> onConsumer) {
        return this.joinGeneric(entity, alias, Join.INNER, onConsumer);
    }

    /**
     * 关联查询
     *
     * @param entity     实体类
     * @param join       {@link Join}
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C joinGeneric(final Class<S> entity, final Join join,
                              final Consumer<GenericForeignImplementor<S>> onConsumer) {
        return this.joinGeneric(entity, null, join, onConsumer);
    }

    /**
     * 关联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param join       {@link Join}
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    @SuppressWarnings("unchecked")
    default <S> C joinGeneric(final Class<S> entity, final String alias, final Join join,
                              final Consumer<GenericForeignImplementor<S>> onConsumer) {
        this.join(GenericForeignImplementor.from(this, entity, alias, join, onConsumer));
        return (C) this;
    }

    /**
     * 关联查询
     *
     * @param entity     实体类
     * @param join       {@link Join}
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    default <S> C joinGeneric(final Class<S> entity, final Join join,
                              final BiConsumer<GenericForeignImplementor<S>, C> onConsumer) {
        return this.joinGeneric(entity, null, join, onConsumer);
    }

    /**
     * 关联查询
     *
     * @param entity     实体类
     * @param alias      别名
     * @param join       {@link Join}
     * @param onConsumer {@link Consumer}
     * @param <S>        实体类型
     * @return {@code this}
     */
    @SuppressWarnings("unchecked")
    default <S> C joinGeneric(final Class<S> entity, final String alias, final Join join,
                              final BiConsumer<GenericForeignImplementor<S>, C> onConsumer) {
        this.join(GenericForeignImplementor.from((C) this, entity, alias, join, onConsumer));
        return (C) this;
    }

    // endregion

    // region SubQuery

    /**
     * 创建{@link PlainSubQueryImplementor}对象
     *
     * @return {@link PlainSubQueryImplementor}
     */
    default PlainSubQueryImplementor newSubQuery() {
        return this.newSubQuery((String) null);
    }

    /**
     * 创建{@link PlainSubQueryImplementor}对象
     *
     * @param alias 别名
     * @return {@link PlainSubQueryImplementor}
     */
    default PlainSubQueryImplementor newSubQuery(final String alias) {
        return PlainSubQueryImplementor.from(this, alias);
    }

    /**
     * 创建{@link PlainSubQueryImplementor}对象
     *
     * @param action {@link Consumer}
     * @return {@link PlainSubQueryImplementor}
     */
    default PlainSubQueryImplementor newSubQuery(Consumer<PlainSubQueryImplementor> action) {
        return this.newSubQuery(null, action);
    }

    /**
     * 创建{@link PlainSubQueryImplementor}对象
     *
     * @param alias  别名
     * @param action {@link Consumer}
     * @return {@link PlainSubQueryImplementor}
     */
    default PlainSubQueryImplementor newSubQuery(final String alias, Consumer<PlainSubQueryImplementor> action) {
        return PlainSubQueryImplementor.from(this, alias, action);
    }

    /**
     * 创建{@link PlainSubQueryImplementor}对象
     *
     * @param action {@link BiConsumer}
     * @return {@link PlainSubQueryImplementor}
     */
    default PlainSubQueryImplementor newSubQuery(BiConsumer<C, PlainSubQueryImplementor> action) {
        return this.newSubQuery(null, action);
    }

    /**
     * 创建{@link PlainSubQueryImplementor}对象
     *
     * @param alias  别名
     * @param action {@link BiConsumer}
     * @return {@link PlainSubQueryImplementor}
     */
    @SuppressWarnings("unchecked")
    default PlainSubQueryImplementor newSubQuery(final String alias, BiConsumer<C, PlainSubQueryImplementor> action) {
        return PlainSubQueryImplementor.from((C) this, alias, action);
    }

    // endregion

    // region Selectable

    /**
     * 纯SQL查询字段
     *
     * @param selectBody 查询字段SQL语句
     * @return {@code this}
     */
    C selectWithPure(final String selectBody);

    /**
     * 纯SQL查询字段
     *
     * @param column 字段名
     * @param alias  别名
     * @return {@code this}
     */
    C selectWithPure(final String column, final String alias);

    /**
     * 纯SQL查询字段
     *
     * @param selectBody 查询字段SQL语句
     * @return {@code this}
     */
    C selectWithComplex(final String selectBody);

    // endregion

    // region Order by

    /**
     * 聚合函数升序
     *
     * @param alias 聚合函数别名
     * @return {@code this}
     */
    default C funcAsc(final String alias) {
        return this.funcAsc(this.getFunction(alias));
    }

    /**
     * 聚合函数升序
     *
     * @param function {@link AggFunction}
     * @return {@code this}
     */
    C funcAsc(final AggFunction function);

    /**
     * 升序
     *
     * @param alias 别名
     * @return {@code this}
     */
    C aliasAsc(final String alias);

    /**
     * 聚合函数降序
     *
     * @param alias 聚合函数别名
     * @return {@code this}
     */
    default C funcDesc(final String alias) {
        return this.funcDesc(this.getFunction(alias));
    }

    /**
     * 聚合函数降序
     *
     * @param function {@link AggFunction}
     * @return {@code this}
     */
    C funcDesc(final AggFunction function);

    /**
     * 降序
     *
     * @param alias 别名
     * @return {@code this}
     */
    C aliasDesc(final String alias);

    /**
     * 纯SQL排序
     *
     * @param orderBody SQL语句
     * @return {@code this}
     */
    C orderWithPure(final String orderBody);

    // endregion

}
