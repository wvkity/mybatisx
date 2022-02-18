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
import io.github.mybatisx.core.criteria.AbstractBaseCriteria;
import io.github.mybatisx.core.sql.QuerySqlManager;
import io.github.mybatisx.lang.Strings;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 子查询条件
 *
 * @author wvkity
 * @created 2022/2/18
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlainSubQueryImplementor extends AbstractPlainSubQuery<PlainSubQueryImplementor> {

    private static final long serialVersionUID = 6152723164656193358L;

    public PlainSubQueryImplementor(final Query<?> outerQuery) {
        this(outerQuery, null);
    }

    public PlainSubQueryImplementor(final Query<?> outerQuery, final String alias) {
        this.outerQuery = outerQuery;
        this.clone((AbstractBaseCriteria<?>) outerQuery, this, false);
        this.defaultAlias = this.genDefaultAlias();
        this.aliasRef = new AtomicReference<>(Strings.isNotWhitespace(alias) ? alias : Constants.EMPTY);
        this.associations = new LinkedHashSet<>();
        this.sqlManager = new QuerySqlManager(this, this.outerQuery, this.associations, this.fragmentManager);
    }

    @Override
    protected PlainSubQueryImplementor newInstance() {
        final PlainSubQueryImplementor it = new PlainSubQueryImplementor();
        it.outerQuery = this.outerQuery;
        it.clone(this);
        return it;
    }

    @Override
    public Query<?> getOuterQuery() {
        return this.outerQuery;
    }

    ///// static methods /////

    /**
     * 创建{@link PlainSubQueryImplementor}对象
     *
     * @param outerQuery {@link Query}
     * @return {@link PlainSubQueryImplementor}
     */
    public static PlainSubQueryImplementor from(final Query<?> outerQuery) {
        return from(outerQuery, (String) null);
    }

    /**
     * 创建{@link PlainSubQueryImplementor}对象
     *
     * @param outerQuery {@link Query}
     * @param alias      别名
     * @return {@link PlainSubQueryImplementor}
     */
    public static PlainSubQueryImplementor from(final Query<?> outerQuery, final String alias) {
        return from(outerQuery, alias, (Consumer<PlainSubQueryImplementor>) null);
    }

    /**
     * 创建{@link PlainSubQueryImplementor}对象
     *
     * @param outerQuery {@link Query}
     * @param action     {@link Consumer}
     * @return {@link PlainSubQueryImplementor}
     */
    public static PlainSubQueryImplementor from(final Query<?> outerQuery, final Consumer<PlainSubQueryImplementor> action) {
        return from(outerQuery, null, action);
    }

    /**
     * 创建{@link PlainSubQueryImplementor}对象
     *
     * @param outerQuery {@link Query}
     * @param alias      别名
     * @param action     {@link Consumer}
     * @return {@link PlainSubQueryImplementor}
     */
    public static PlainSubQueryImplementor from(final Query<?> outerQuery, final String alias,
                                                final Consumer<PlainSubQueryImplementor> action) {
        final PlainSubQueryImplementor it = new PlainSubQueryImplementor(outerQuery, alias);
        if (action != null) {
            action.accept(it);
        }
        return it;
    }

    /**
     * 创建{@link PlainSubQueryImplementor}对象
     *
     * @param outerQuery {@link Query}
     * @param action     {@link BiConsumer}
     * @param <T>        实体类型
     * @param <Q>        {@link Query}
     * @return {@link PlainSubQueryImplementor}
     */
    public static <T, Q extends Query<T>> PlainSubQueryImplementor from(final Q outerQuery, final BiConsumer<Q, PlainSubQueryImplementor> action) {
        return from(outerQuery, null, action);
    }

    /**
     * 创建{@link PlainSubQueryImplementor}对象
     *
     * @param outerQuery {@link Query}
     * @param alias      别名
     * @param action     {@link BiConsumer}
     * @param <T>        实体类型
     * @param <Q>        {@link Query}
     * @return {@link PlainSubQueryImplementor}
     */
    public static <T, Q extends Query<T>> PlainSubQueryImplementor from(final Q outerQuery, final String alias,
                                                                        final BiConsumer<Q, PlainSubQueryImplementor> action) {
        final PlainSubQueryImplementor it = new PlainSubQueryImplementor(outerQuery, alias);
        if (action != null) {
            action.accept(outerQuery, it);
        }
        return it;
    }

}
