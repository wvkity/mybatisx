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
package io.github.mybatisx.core.support.order;

import com.google.common.collect.ImmutableList;
import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.NullPrecedence;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.core.criteria.query.Query;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 多个字段排序
 *
 * @author wvkity
 * @created 2022/1/26
 * @since 1.0.0
 */
@SuperBuilder
public class MultiOrder extends AbstractOrder {

    private static final long serialVersionUID = -2142012127287666551L;

    /**
     * 字段列表
     */
    @Getter
    protected final List<String> columns;
    /**
     * 别名
     */
    protected final AtomicReference<String> asRef = new AtomicReference<>(null);

    public MultiOrder(Query<?> query, String alias, List<String> columns, boolean ascending,
                      boolean ignoreCase, NullPrecedence precedence) {
        super(query, alias, ascending, ignoreCase, precedence);
        this.columns = ImmutableList.copyOf(columns);
    }

    @Override
    protected String as() {
        String oldValue = this.asRef.get();
        if (oldValue == null) {
            oldValue = super.as();
            this.asRef.compareAndSet(null, oldValue);
        }
        return oldValue;
    }

    @Override
    public String getFragment() {
        final List<String> _$columns = this.columns;
        if (Objects.isNotEmpty(_$columns)) {
            final List<String> fragments = new ArrayList<>(_$columns.size());
            for (String c : _$columns) {
                final String it = this.render(c);
                if (Strings.isNotWhitespace(it)) {
                    fragments.add(it);
                }
            }
            if (Objects.isNotEmpty(fragments)) {
                return String.join(SqlSymbol.COMMA_SPACE, fragments);
            }
        }
        return Constants.EMPTY;
    }

    @Override
    public String toString() {
        return this.columns.stream().map(it -> it + ' ' + super.toString())
                .collect(Collectors.joining(SqlSymbol.COMMA_SPACE));
    }

    ///// static methods /////

    // region Asc methods

    /**
     * 升序
     *
     * @param query   {@link Query}
     * @param columns 字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder asc(final Query<?> query, final String... columns) {
        return asc(query, false, columns);
    }

    /**
     * 升序
     *
     * @param query      {@link Query}
     * @param precedence {@link NullPrecedence}
     * @param columns    字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder asc(final Query<?> query, final NullPrecedence precedence, final String... columns) {
        return asc(query, false, precedence, columns);
    }

    /**
     * 升序
     *
     * @param query      {@link Query}
     * @param ignoreCase 是否忽略大小写排序
     * @param columns    字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder asc(final Query<?> query, final boolean ignoreCase, final String... columns) {
        return asc(query, ignoreCase, NullPrecedence.NONE, columns);
    }

    /**
     * 升序
     *
     * @param query      {@link Query}
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @param columns    字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder asc(final Query<?> query, final boolean ignoreCase,
                                 final NullPrecedence precedence, final String... columns) {
        return asc(query, Arrays.asList(columns), ignoreCase, precedence);
    }

    /**
     * 升序
     *
     * @param query   {@link Query}
     * @param columns 字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder asc(final Query<?> query, List<String> columns) {
        return asc(query, columns, false);
    }

    /**
     * 升序
     *
     * @param query      {@link Query}
     * @param columns    字段列表
     * @param precedence {@link NullPrecedence}
     * @return {@link MultiOrder}
     */
    public static MultiOrder asc(final Query<?> query, final List<String> columns, final NullPrecedence precedence) {
        return asc(query, columns, false, precedence);
    }

    /**
     * 升序
     *
     * @param query      {@link Query}
     * @param columns    字段列表
     * @param ignoreCase 是否忽略大小写排序
     * @return {@link MultiOrder}
     */
    public static MultiOrder asc(final Query<?> query, final List<String> columns, final boolean ignoreCase) {
        return asc(query, columns, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 升序
     *
     * @param query      {@link Query}
     * @param columns    字段列表
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @return {@link MultiOrder}
     */
    public static MultiOrder asc(final Query<?> query, final List<String> columns, final boolean ignoreCase,
                                 final NullPrecedence precedence) {
        if (Objects.isNotEmpty(columns)) {
            return new MultiOrder(query, null, columns, true, ignoreCase, precedence);
        }
        return null;
    }

    /**
     * 升序
     *
     * @param alias   表别名
     * @param columns 字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder asc(final String alias, final String... columns) {
        return asc(alias, false, columns);
    }

    /**
     * 升序
     *
     * @param alias      表别名
     * @param precedence {@link NullPrecedence}
     * @param columns    字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder asc(final String alias, final NullPrecedence precedence, final String... columns) {
        return asc(alias, false, precedence, columns);
    }

    /**
     * 升序
     *
     * @param alias      表别名
     * @param ignoreCase 是否忽略大小写排序
     * @param columns    字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder asc(final String alias, final boolean ignoreCase, final String... columns) {
        return asc(alias, ignoreCase, NullPrecedence.NONE, columns);
    }

    /**
     * 升序
     *
     * @param alias      表别名
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @param columns    字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder asc(final String alias, final boolean ignoreCase,
                                 final NullPrecedence precedence, final String... columns) {
        return asc(alias, Arrays.asList(columns), ignoreCase, precedence);
    }

    /**
     * 升序
     *
     * @param alias   表别名
     * @param columns 字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder asc(final String alias, final List<String> columns) {
        return asc(alias, columns, false);
    }

    /**
     * 升序
     *
     * @param alias      表别名
     * @param columns    字段列表
     * @param precedence {@link NullPrecedence}
     * @return {@link MultiOrder}
     */
    public static MultiOrder asc(final String alias, final List<String> columns, final NullPrecedence precedence) {
        return asc(alias, columns, false, precedence);
    }

    /**
     * 升序
     *
     * @param alias      表别名
     * @param columns    字段列表
     * @param ignoreCase 是否忽略大小写排序
     * @return {@link MultiOrder}
     */
    public static MultiOrder asc(final String alias, final List<String> columns, final boolean ignoreCase) {
        return asc(alias, columns, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 升序
     *
     * @param alias      表别名
     * @param columns    字段列表
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @return {@link MultiOrder}
     */
    public static MultiOrder asc(final String alias, final List<String> columns, final boolean ignoreCase,
                                 final NullPrecedence precedence) {
        if (Objects.isNotEmpty(columns)) {
            return new MultiOrder(null, alias, columns, true, ignoreCase, precedence);
        }
        return null;
    }

    // endregion

    /**
     * 降序
     *
     * @param query   {@link Query}
     * @param columns 字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder desc(final Query<?> query, final String... columns) {
        return desc(query, false, columns);
    }

    /**
     * 降序
     *
     * @param query      {@link Query}
     * @param precedence {@link NullPrecedence}
     * @param columns    字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder desc(final Query<?> query, final NullPrecedence precedence, final String... columns) {
        return desc(query, false, precedence, columns);
    }

    /**
     * 降序
     *
     * @param query      {@link Query}
     * @param ignoreCase 是否忽略大小写排序
     * @param columns    字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder desc(final Query<?> query, final boolean ignoreCase, final String... columns) {
        return desc(query, ignoreCase, NullPrecedence.NONE, columns);
    }

    /**
     * 降序
     *
     * @param query      {@link Query}
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @param columns    字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder desc(final Query<?> query, final boolean ignoreCase,
                                  final NullPrecedence precedence, final String... columns) {
        return desc(query, Arrays.asList(columns), ignoreCase, precedence);
    }

    /**
     * 降序
     *
     * @param query   {@link Query}
     * @param columns 字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder desc(final Query<?> query, List<String> columns) {
        return desc(query, columns, false);
    }

    /**
     * 降序
     *
     * @param query      {@link Query}
     * @param columns    字段列表
     * @param precedence {@link NullPrecedence}
     * @return {@link MultiOrder}
     */
    public static MultiOrder desc(final Query<?> query, final List<String> columns, final NullPrecedence precedence) {
        return desc(query, columns, false, precedence);
    }

    /**
     * 降序
     *
     * @param query      {@link Query}
     * @param columns    字段列表
     * @param ignoreCase 是否忽略大小写排序
     * @return {@link MultiOrder}
     */
    public static MultiOrder desc(final Query<?> query, final List<String> columns, final boolean ignoreCase) {
        return desc(query, columns, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 降序
     *
     * @param query      {@link Query}
     * @param columns    字段列表
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @return {@link MultiOrder}
     */
    public static MultiOrder desc(final Query<?> query, final List<String> columns, final boolean ignoreCase,
                                  final NullPrecedence precedence) {
        if (Objects.isNotEmpty(columns)) {
            return new MultiOrder(query, null, columns, false, ignoreCase, precedence);
        }
        return null;
    }

    /**
     * 降序
     *
     * @param alias   表别名
     * @param columns 字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder desc(final String alias, final String... columns) {
        return desc(alias, false, columns);
    }

    /**
     * 降序
     *
     * @param alias      表别名
     * @param precedence {@link NullPrecedence}
     * @param columns    字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder desc(final String alias, final NullPrecedence precedence, final String... columns) {
        return desc(alias, false, precedence, columns);
    }

    /**
     * 降序
     *
     * @param alias      表别名
     * @param ignoreCase 是否忽略大小写排序
     * @param columns    字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder desc(final String alias, final boolean ignoreCase, final String... columns) {
        return desc(alias, ignoreCase, NullPrecedence.NONE, columns);
    }

    /**
     * 降序
     *
     * @param alias      表别名
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @param columns    字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder desc(final String alias, final boolean ignoreCase,
                                  final NullPrecedence precedence, final String... columns) {
        return desc(alias, Arrays.asList(columns), ignoreCase, precedence);
    }

    /**
     * 降序
     *
     * @param alias   表别名
     * @param columns 字段列表
     * @return {@link MultiOrder}
     */
    public static MultiOrder desc(final String alias, final List<String> columns) {
        return desc(alias, columns, false);
    }

    /**
     * 降序
     *
     * @param alias      表别名
     * @param columns    字段列表
     * @param precedence {@link NullPrecedence}
     * @return {@link MultiOrder}
     */
    public static MultiOrder desc(final String alias, final List<String> columns, final NullPrecedence precedence) {
        return desc(alias, columns, false, precedence);
    }

    /**
     * 降序
     *
     * @param alias      表别名
     * @param columns    字段列表
     * @param ignoreCase 是否忽略大小写排序
     * @return {@link MultiOrder}
     */
    public static MultiOrder desc(final String alias, final List<String> columns, final boolean ignoreCase) {
        return desc(alias, columns, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 降序
     *
     * @param alias      表别名
     * @param columns    字段列表
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @return {@link MultiOrder}
     */
    public static MultiOrder desc(final String alias, final List<String> columns, final boolean ignoreCase,
                                  final NullPrecedence precedence) {
        if (Objects.isNotEmpty(columns)) {
            return new MultiOrder(null, alias, columns, false, ignoreCase, precedence);
        }
        return null;
    }

    // region Desc methods


}
