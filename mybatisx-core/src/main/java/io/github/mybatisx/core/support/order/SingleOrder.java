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

import io.github.mybatisx.base.constant.NullPrecedence;
import io.github.mybatisx.core.criteria.query.Query;
import io.github.mybatisx.lang.Strings;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * 单个字段排序
 *
 * @author wvkity
 * @created 2022/1/25
 * @since 1.0.0
 */
@SuperBuilder
public class SingleOrder extends AbstractOrder {

    private static final long serialVersionUID = -2305893844362978626L;

    /**
     * 字段名
     */
    @Getter
    protected final String column;

    protected SingleOrder(Query<?> query, String alias, String column, boolean ascending, boolean ignoreCase,
                          NullPrecedence precedence) {
        super(query, alias, ascending, ignoreCase, precedence);
        this.column = column;
    }

    @Override
    public String getFragment() {
        return this.render(this.column);
    }

    @Override
    public String toString() {
        return this.column + ' ' + super.toString();
    }

    ///// static methods /////

    // region Asc methods 

    /**
     * 升序
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @return {@link SingleOrder}
     */
    public static SingleOrder asc(final Query<?> query, final String column) {
        return asc(query, column, false);
    }

    /**
     * 升序
     *
     * @param query      {@link Query}
     * @param column     字段名
     * @param precedence {@link NullPrecedence}
     * @return {@link SingleOrder}
     */
    public static SingleOrder asc(final Query<?> query, final String column, final NullPrecedence precedence) {
        return asc(query, column, false, precedence);
    }

    /**
     * 升序
     *
     * @param query      {@link Query}
     * @param column     字段名
     * @param ignoreCase 是否忽略大小写
     * @return {@link SingleOrder}
     */
    public static SingleOrder asc(final Query<?> query, final String column, final boolean ignoreCase) {
        return asc(query, column, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 升序
     *
     * @param query      {@link Query}
     * @param column     字段名
     * @param ignoreCase 是否忽略大小写
     * @param precedence {@link NullPrecedence}
     * @return {@link SingleOrder}
     */
    public static SingleOrder asc(final Query<?> query, final String column, final boolean ignoreCase,
                                  final NullPrecedence precedence) {
        if (Strings.isNotWhitespace(column)) {
            return new SingleOrder(query, null, column, true, ignoreCase, precedence);
        }
        return null;
    }

    /**
     * 升序
     *
     * @param column 字段名
     * @return {@link SingleOrder}
     */
    public static SingleOrder asc(final String column) {
        return asc(column, false);
    }

    /**
     * 升序
     *
     * @param column     字段名
     * @param precedence {@link NullPrecedence}
     * @return {@link SingleOrder}
     */
    public static SingleOrder asc(final String column, final NullPrecedence precedence) {
        return asc(column, false, precedence);
    }

    /**
     * 升序
     *
     * @param column     字段名
     * @param ignoreCase 是否忽略大小写
     * @return {@link SingleOrder}
     */
    public static SingleOrder asc(final String column, final boolean ignoreCase) {
        return asc(column, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 升序
     *
     * @param column     字段名
     * @param ignoreCase 是否忽略大小写
     * @param precedence {@link NullPrecedence}
     * @return {@link SingleOrder}
     */
    public static SingleOrder asc(final String column, final boolean ignoreCase,
                                  final NullPrecedence precedence) {
        if (Strings.isNotWhitespace(column)) {
            return new SingleOrder(null, null, column, true, ignoreCase, precedence);
        }
        return null;
    }

    /**
     * 升序
     *
     * @param alias  表别名
     * @param column 字段名
     * @return {@link SingleOrder}
     */
    public static SingleOrder asc(final String alias, final String column) {
        return asc(alias, column, false);
    }

    /**
     * 升序
     *
     * @param alias      表别名
     * @param column     字段名
     * @param precedence {@link NullPrecedence}
     * @return {@link SingleOrder}
     */
    public static SingleOrder asc(final String alias, final String column, final NullPrecedence precedence) {
        return asc(alias, column, false, precedence);
    }

    /**
     * 升序
     *
     * @param alias      表别名
     * @param column     字段名
     * @param ignoreCase 是否忽略大小写
     * @return {@link SingleOrder}
     */
    public static SingleOrder asc(final String alias, final String column, final boolean ignoreCase) {
        return asc(alias, column, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 升序
     *
     * @param alias      表别名
     * @param column     字段名
     * @param ignoreCase 是否忽略大小写
     * @param precedence {@link NullPrecedence}
     * @return {@link SingleOrder}
     */
    public static SingleOrder asc(final String alias, final String column, final boolean ignoreCase,
                                  final NullPrecedence precedence) {
        if (Strings.isNotWhitespace(column)) {
            return new SingleOrder(null, alias, column, true, ignoreCase, precedence);
        }
        return null;
    }

    // endregion

    // region Desc methods

    /**
     * 降序
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @return {@link SingleOrder}
     */
    public static SingleOrder desc(final Query<?> query, final String column) {
        return desc(query, column, false);
    }

    /**
     * 降序
     *
     * @param query      {@link Query}
     * @param column     字段名
     * @param precedence {@link NullPrecedence}
     * @return {@link SingleOrder}
     */
    public static SingleOrder desc(final Query<?> query, final String column, final NullPrecedence precedence) {
        return desc(query, column, false, precedence);
    }

    /**
     * 降序
     *
     * @param query      {@link Query}
     * @param column     字段名
     * @param ignoreCase 是否忽略大小写
     * @return {@link SingleOrder}
     */
    public static SingleOrder desc(final Query<?> query, final String column, final boolean ignoreCase) {
        return desc(query, column, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 降序
     *
     * @param query      {@link Query}
     * @param column     字段名
     * @param ignoreCase 是否忽略大小写
     * @param precedence {@link NullPrecedence}
     * @return {@link SingleOrder}
     */
    public static SingleOrder desc(final Query<?> query, final String column, final boolean ignoreCase,
                                   final NullPrecedence precedence) {
        if (Strings.isNotWhitespace(column)) {
            return new SingleOrder(query, null, column, false, ignoreCase, precedence);
        }
        return null;
    }

    /**
     * 降序
     *
     * @param column 字段名
     * @return {@link SingleOrder}
     */
    public static SingleOrder desc(final String column) {
        return desc(column, false);
    }

    /**
     * 降序
     *
     * @param column     字段名
     * @param precedence {@link NullPrecedence}
     * @return {@link SingleOrder}
     */
    public static SingleOrder desc(final String column, final NullPrecedence precedence) {
        return desc(column, false, precedence);
    }

    /**
     * 降序
     *
     * @param column     字段名
     * @param ignoreCase 是否忽略大小写
     * @return {@link SingleOrder}
     */
    public static SingleOrder desc(final String column, final boolean ignoreCase) {
        return desc(column, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 降序
     *
     * @param column     字段名
     * @param ignoreCase 是否忽略大小写
     * @param precedence {@link NullPrecedence}
     * @return {@link SingleOrder}
     */
    public static SingleOrder desc(final String column, final boolean ignoreCase,
                                   final NullPrecedence precedence) {
        if (Strings.isNotWhitespace(column)) {
            return new SingleOrder(null, null, column, false, ignoreCase, precedence);
        }
        return null;
    }

    /**
     * 降序
     *
     * @param alias  表别名
     * @param column 字段名
     * @return {@link SingleOrder}
     */
    public static SingleOrder desc(final String alias, final String column) {
        return desc(alias, column, false);
    }

    /**
     * 降序
     *
     * @param alias      表别名
     * @param column     字段名
     * @param precedence {@link NullPrecedence}
     * @return {@link SingleOrder}
     */
    public static SingleOrder desc(final String alias, final String column, final NullPrecedence precedence) {
        return desc(alias, column, false, precedence);
    }

    /**
     * 降序
     *
     * @param alias      表别名
     * @param column     字段名
     * @param ignoreCase 是否忽略大小写
     * @return {@link SingleOrder}
     */
    public static SingleOrder desc(final String alias, final String column, final boolean ignoreCase) {
        return desc(alias, column, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 降序
     *
     * @param alias      表别名
     * @param column     字段名
     * @param ignoreCase 是否忽略大小写
     * @param precedence {@link NullPrecedence}
     * @return {@link SingleOrder}
     */
    public static SingleOrder desc(final String alias, final String column, final boolean ignoreCase,
                                   final NullPrecedence precedence) {
        if (Strings.isNotWhitespace(column)) {
            return new SingleOrder(null, alias, column, false, ignoreCase, precedence);
        }
        return null;
    }

    // endregion
}
