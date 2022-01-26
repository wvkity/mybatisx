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

import io.github.mybatisx.base.constant.NullPrecedence;

import java.util.Arrays;
import java.util.List;

/**
 * 排序接口
 *
 * @author wvkity
 * @created 2022/1/26
 * @since 1.0.0
 */
public interface PlainSort<T, C extends PlainSort<T, C>> {

    // region Asc methods

    /**
     * 升序
     *
     * @param column 字段名
     * @return {@code this}
     */
    default C colAsc(final String column) {
        return this.colAsc(column, false);
    }

    /**
     * 升序
     *
     * @param column     字段名
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    default C colAsc(final String column, final NullPrecedence precedence) {
        return this.colAsc(column, false, precedence);
    }

    /**
     * 升序
     *
     * @param column     字段名
     * @param ignoreCase 是否忽略大小写排序
     * @return {@code this}
     */
    default C colAsc(final String column, final boolean ignoreCase) {
        return this.colAsc(column, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 升序
     *
     * @param columns 字段名列表
     * @return {@code this}
     */
    default C colAsc(final String... columns) {
        return this.colAsc(false, columns);
    }

    /**
     * 升序
     *
     * @param precedence {@link NullPrecedence}
     * @param columns    字段名列表
     * @return {@code this}
     */
    default C colAsc(final NullPrecedence precedence, final String... columns) {
        return this.colAsc(false, precedence, columns);
    }

    /**
     * 升序
     *
     * @param ignoreCase 是否忽略大小写排序
     * @param columns    字段名列表
     * @return {@code this}
     */
    default C colAsc(final boolean ignoreCase, final String... columns) {
        return this.colAsc(ignoreCase, NullPrecedence.NONE, columns);
    }

    /**
     * 升序
     *
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @param columns    字段名列表
     * @return {@code this}
     */
    default C colAsc(final boolean ignoreCase, final NullPrecedence precedence, final String... columns) {
        return this.colAsc(Arrays.asList(columns), ignoreCase, precedence);
    }

    /**
     * 升序
     *
     * @param columns 字段名列表
     * @return {@code this}
     */
    default C colAsc(final List<String> columns) {
        return this.colAsc(columns, false);
    }

    /**
     * 升序
     *
     * @param column     字段名
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    C colAsc(final String column, final boolean ignoreCase, final NullPrecedence precedence);

    /**
     * 升序
     *
     * @param columns    字段名列表
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    default C colAsc(final List<String> columns, final NullPrecedence precedence) {
        return this.colAsc(columns, false, precedence);
    }

    /**
     * 升序
     *
     * @param columns    字段名列表
     * @param ignoreCase 是否忽略大小写排序
     * @return {@code this}
     */
    default C colAsc(final List<String> columns, final boolean ignoreCase) {
        return this.colAsc(columns, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 升序
     *
     * @param columns    字段名列表
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    C colAsc(final List<String> columns, final boolean ignoreCase, final NullPrecedence precedence);

    // endregion

    // region Desc methods

    /**
     * 降序
     *
     * @param column 字段名
     * @return {@code this}
     */
    default C colDesc(final String column) {
        return this.colDesc(column, false);
    }

    /**
     * 降序
     *
     * @param column     字段名
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    default C colDesc(final String column, final NullPrecedence precedence) {
        return this.colDesc(column, false, precedence);
    }

    /**
     * 降序
     *
     * @param column     字段名
     * @param ignoreCase 是否忽略大小写排序
     * @return {@code this}
     */
    default C colDesc(final String column, final boolean ignoreCase) {
        return this.colDesc(column, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 降序
     *
     * @param columns 字段名列表
     * @return {@code this}
     */
    default C colDesc(final String... columns) {
        return this.colDesc(false, columns);
    }

    /**
     * 降序
     *
     * @param precedence {@link NullPrecedence}
     * @param columns    字段名列表
     * @return {@code this}
     */
    default C colDesc(final NullPrecedence precedence, final String... columns) {
        return this.colDesc(false, precedence, columns);
    }

    /**
     * 降序
     *
     * @param ignoreCase 是否忽略大小写排序
     * @param columns    字段名列表
     * @return {@code this}
     */
    default C colDesc(final boolean ignoreCase, final String... columns) {
        return this.colDesc(ignoreCase, NullPrecedence.NONE, columns);
    }

    /**
     * 降序
     *
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @param columns    字段名列表
     * @return {@code this}
     */
    default C colDesc(final boolean ignoreCase, final NullPrecedence precedence, final String... columns) {
        return this.colDesc(Arrays.asList(columns), ignoreCase, precedence);
    }

    /**
     * 降序
     *
     * @param columns 字段名列表
     * @return {@code this}
     */
    default C colDesc(final List<String> columns) {
        return this.colDesc(columns, false);
    }

    /**
     * 降序
     *
     * @param column     字段名
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    C colDesc(final String column, final boolean ignoreCase, final NullPrecedence precedence);

    /**
     * 降序
     *
     * @param columns    字段名列表
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    default C colDesc(final List<String> columns, final NullPrecedence precedence) {
        return this.colDesc(columns, false, precedence);
    }

    /**
     * 降序
     *
     * @param columns    字段名列表
     * @param ignoreCase 是否忽略大小写排序
     * @return {@code this}
     */
    default C colDesc(final List<String> columns, final boolean ignoreCase) {
        return this.colDesc(columns, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 降序
     *
     * @param columns    字段名列表
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    C colDesc(final List<String> columns, final boolean ignoreCase, final NullPrecedence precedence);

    // endregion

}
