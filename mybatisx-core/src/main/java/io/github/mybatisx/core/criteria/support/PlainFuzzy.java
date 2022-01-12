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
package io.github.mybatisx.core.criteria.support;

import io.github.mybatisx.base.constant.LikeMatchMode;
import io.github.mybatisx.base.constant.LogicSymbol;

/**
 * 模糊匹配条件接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/11
 * @since 1.0.0
 */
public interface PlainFuzzy<T, C extends PlainFuzzy<T, C>> extends Slot<T, C> {

    // region Like methods

    // region Like left methods

    /**
     * like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colLikeLeft(final String column, final String value) {
        return this.colLikeLeft(column, value, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colLikeLeft(final String column, final String value, final LogicSymbol slot) {
        return this.colLikeLeft(column, value, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C colLikeLeft(final String column, final String value, final boolean ignoreCase) {
        return this.colLikeLeft(column, value, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C colLikeLeft(final String column, final String value, final boolean ignoreCase,
                          final LogicSymbol slot) {
        return this.colLikeLeft(column, value, null, ignoreCase, slot);
    }

    /**
     * like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    default C colLikeLeft(final String column, final String value, final Character escape) {
        return this.colLikeLeft(column, value, escape, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colLikeLeft(final String column, final String value, final Character escape, final LogicSymbol slot) {
        return this.colLikeLeft(column, value, escape, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C colLikeLeft(final String column, final String value, final Character escape,
                          final boolean ignoreCase) {
        return this.colLikeLeft(column, value, escape, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C colLikeLeft(final String column, final String value, final Character escape,
                          final boolean ignoreCase, final LogicSymbol slot) {
        return this.colLike(column, value, LikeMatchMode.END, escape, ignoreCase, slot);
    }

    // endregion

    // region Like right methods

    /**
     * like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colLikeRight(final String column, final String value) {
        return this.colLikeRight(column, value, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colLikeRight(final String column, final String value, final LogicSymbol slot) {
        return this.colLikeRight(column, value, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C colLikeRight(final String column, final String value, final boolean ignoreCase) {
        return this.colLikeRight(column, value, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C colLikeRight(final String column, final String value, final boolean ignoreCase,
                           final LogicSymbol slot) {
        return this.colLikeRight(column, value, null, ignoreCase, slot);
    }

    /**
     * like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    default C colLikeRight(final String column, final String value, final Character escape) {
        return this.colLikeRight(column, value, escape, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colLikeRight(final String column, final String value, final Character escape, final LogicSymbol slot) {
        return this.colLikeRight(column, value, escape, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C colLikeRight(final String column, final String value, final Character escape,
                           final boolean ignoreCase) {
        return this.colLikeRight(column, value, escape, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C colLikeRight(final String column, final String value, final Character escape,
                           final boolean ignoreCase, final LogicSymbol slot) {
        return this.colLike(column, value, LikeMatchMode.START, escape, ignoreCase, slot);
    }

    // endregion

    // region Like any methods

    /**
     * like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colLikeAny(final String column, final String value) {
        return this.colLikeAny(column, value, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colLikeAny(final String column, final String value, final LogicSymbol slot) {
        return this.colLikeAny(column, value, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C colLikeAny(final String column, final String value, final boolean ignoreCase) {
        return this.colLikeAny(column, value, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C colLikeAny(final String column, final String value, final boolean ignoreCase, final LogicSymbol slot) {
        return this.colLikeAny(column, value, null, ignoreCase, slot);
    }

    /**
     * like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    default C colLikeAny(final String column, final String value, final Character escape) {
        return this.colLikeAny(column, value, escape, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colLikeAny(final String column, final String value, final Character escape, final LogicSymbol slot) {
        return this.colLikeAny(column, value, escape, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C colLikeAny(final String column, final String value, final Character escape, final boolean ignoreCase) {
        return this.colLikeAny(column, value, escape, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C colLikeAny(final String column, final String value, final Character escape,
                         final boolean ignoreCase, final LogicSymbol slot) {
        return this.colLike(column, value, LikeMatchMode.ANYWHERE, escape, ignoreCase, slot);
    }

    // endregion

    // region Like base methods

    /**
     * like模糊匹配
     *
     * @param column  字段名
     * @param value   值
     * @param matches 匹配模式
     * @return {@code this}
     */
    default C colLike(final String column, final String value, final LikeMatchMode matches) {
        return this.colLike(column, value, matches, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param column  字段名
     * @param value   值
     * @param matches 匹配模式
     * @param slot    {@link LogicSymbol}
     * @return {@code this}
     */
    default C colLike(final String column, final String value, final LikeMatchMode matches, final LogicSymbol slot) {
        return this.colLike(column, value, matches, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param matches    匹配模式
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C colLike(final String column, final String value, final LikeMatchMode matches,
                      final boolean ignoreCase) {
        return this.colLike(column, value, matches, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param matches    匹配模式
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C colLike(final String column, final String value, final LikeMatchMode matches,
                      final boolean ignoreCase, final LogicSymbol slot) {
        return this.colLike(column, value, matches, null, ignoreCase, slot);
    }

    /**
     * like模糊匹配
     *
     * @param column  字段名
     * @param value   值
     * @param matches 匹配模式
     * @param escape  转义字符
     * @return {@code this}
     */
    default C colLike(final String column, final String value, final LikeMatchMode matches, final Character escape) {
        return this.colLike(column, value, matches, escape, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param column  字段名
     * @param value   值
     * @param matches 匹配模式
     * @param escape  转义字符
     * @param slot    {@link LogicSymbol}
     * @return {@code this}
     */
    default C colLike(final String column, final String value, final LikeMatchMode matches,
                      final Character escape, final LogicSymbol slot) {
        return this.colLike(column, value, matches, escape, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param matches    匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C colLike(final String column, final String value, final LikeMatchMode matches,
                      final Character escape, final boolean ignoreCase) {
        return this.colLike(column, value, matches, escape, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param matches    匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    C colLike(final String column, final String value, final LikeMatchMode matches, final Character escape,
              final boolean ignoreCase, final LogicSymbol slot);

    // endregion

    // endregion

    // region Not like methods

    // region Not like left methods

    /**
     * not like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colNotLikeLeft(final String column, final String value) {
        return this.colNotLikeLeft(column, value, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colNotLikeLeft(final String column, final String value, final LogicSymbol slot) {
        return this.colNotLikeLeft(column, value, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C colNotLikeLeft(final String column, final String value, final boolean ignoreCase) {
        return this.colNotLikeLeft(column, value, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C colNotLikeLeft(final String column, final String value, final boolean ignoreCase,
                             final LogicSymbol slot) {
        return this.colNotLikeLeft(column, value, null, ignoreCase, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    default C colNotLikeLeft(final String column, final String value, final Character escape) {
        return this.colNotLikeLeft(column, value, escape, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colNotLikeLeft(final String column, final String value, final Character escape,
                             final LogicSymbol slot) {
        return this.colNotLikeLeft(column, value, escape, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C colNotLikeLeft(final String column, final String value, final Character escape,
                             final boolean ignoreCase) {
        return this.colNotLikeLeft(column, value, escape, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C colNotLikeLeft(final String column, final String value, final Character escape,
                             final boolean ignoreCase, final LogicSymbol slot) {
        return this.colNotLike(column, value, LikeMatchMode.END, escape, ignoreCase, slot);
    }

    // endregion

    // region Not like right methods

    /**
     * not like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colNotLikeRight(final String column, final String value) {
        return this.colNotLikeRight(column, value, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colNotLikeRight(final String column, final String value, final LogicSymbol slot) {
        return this.colNotLikeRight(column, value, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C colNotLikeRight(final String column, final String value, final boolean ignoreCase) {
        return this.colNotLikeRight(column, value, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C colNotLikeRight(final String column, final String value, final boolean ignoreCase,
                              final LogicSymbol slot) {
        return this.colNotLikeRight(column, value, null, ignoreCase, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    default C colNotLikeRight(final String column, final String value, final Character escape) {
        return this.colNotLikeRight(column, value, escape, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colNotLikeRight(final String column, final String value, final Character escape,
                              final LogicSymbol slot) {
        return this.colNotLikeRight(column, value, escape, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C colNotLikeRight(final String column, final String value, final Character escape,
                              final boolean ignoreCase) {
        return this.colNotLikeRight(column, value, escape, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C colNotLikeRight(final String column, final String value, final Character escape,
                              final boolean ignoreCase, final LogicSymbol slot) {
        return this.colNotLike(column, value, LikeMatchMode.START, escape, ignoreCase, slot);
    }

    // endregion

    // region Not like any methods

    /**
     * not like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    default C colNotLikeAny(final String column, final String value) {
        return this.colNotLikeAny(column, value, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colNotLikeAny(final String column, final String value, final LogicSymbol slot) {
        return this.colNotLikeAny(column, value, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C colNotLikeAny(final String column, final String value, final boolean ignoreCase) {
        return this.colNotLikeAny(column, value, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C colNotLikeAny(final String column, final String value, final boolean ignoreCase,
                            final LogicSymbol slot) {
        return this.colNotLikeAny(column, value, null, ignoreCase, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    default C colNotLikeAny(final String column, final String value, final Character escape) {
        return this.colNotLikeAny(column, value, escape, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param column 字段名
     * @param value  值
     * @param escape 转义字符
     * @param slot   {@link LogicSymbol}
     * @return {@code this}
     */
    default C colNotLikeAny(final String column, final String value, final Character escape, final LogicSymbol slot) {
        return this.colNotLikeAny(column, value, escape, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C colNotLikeAny(final String column, final String value, final Character escape,
                            final boolean ignoreCase) {
        return this.colNotLikeAny(column, value, escape, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C colNotLikeAny(final String column, final String value, final Character escape,
                            final boolean ignoreCase, final LogicSymbol slot) {
        return this.colNotLike(column, value, LikeMatchMode.ANYWHERE, escape, ignoreCase, slot);
    }

    // endregion

    // region Not like base methods

    /**
     * not like模糊匹配
     *
     * @param column  字段名
     * @param value   值
     * @param matches 匹配模式
     * @return {@code this}
     */
    default C colNotLike(final String column, final String value, final LikeMatchMode matches) {
        return this.colNotLike(column, value, matches, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param column  字段名
     * @param value   值
     * @param matches 匹配模式
     * @param slot    {@link LogicSymbol}
     * @return {@code this}
     */
    default C colNotLike(final String column, final String value, final LikeMatchMode matches,
                         final LogicSymbol slot) {
        return this.colNotLike(column, value, matches, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param matches    匹配模式
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C colNotLike(final String column, final String value, final LikeMatchMode matches,
                         final boolean ignoreCase) {
        return this.colNotLike(column, value, matches, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param matches    匹配模式
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C colNotLike(final String column, final String value, final LikeMatchMode matches,
                         final boolean ignoreCase, final LogicSymbol slot) {
        return this.colNotLike(column, value, matches, null, ignoreCase, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param column  字段名
     * @param value   值
     * @param matches 匹配模式
     * @param escape  转义字符
     * @return {@code this}
     */
    default C colNotLike(final String column, final String value, final LikeMatchMode matches,
                         final Character escape) {
        return this.colNotLike(column, value, matches, escape, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param column  字段名
     * @param value   值
     * @param matches 匹配模式
     * @param escape  转义字符
     * @param slot    {@link LogicSymbol}
     * @return {@code this}
     */
    default C colNotLike(final String column, final String value, final LikeMatchMode matches,
                         final Character escape, final LogicSymbol slot) {
        return this.colNotLike(column, value, matches, escape, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param matches    匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C colNotLike(final String column, final String value, final LikeMatchMode matches,
                         final Character escape, final boolean ignoreCase) {
        return this.colNotLike(column, value, matches, escape, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param column     字段名
     * @param value      值
     * @param matches    匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    C colNotLike(final String column, final String value, final LikeMatchMode matches, final Character escape,
                 final boolean ignoreCase, final LogicSymbol slot);

    // endregion

    // endregion
}
