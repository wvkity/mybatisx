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
import io.github.mybatisx.core.property.Property;
import io.github.mybatisx.core.property.PropertyConverter;

/**
 * 模糊匹配条件接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/10
 * @since 1.0.0
 */
public interface LambdaFuzzy<T, C extends LambdaFuzzy<T, C>> extends Slot<T, C>, PropertyConverter<T> {

    // region Like methods

    // region Like left methods

    /**
     * like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C likeLeft(final Property<T, String> property, final String value) {
        return this.likeLeft(property, value, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeLeft(final Property<T, String> property, final String value, final LogicSymbol slot) {
        return this.likeLeft(property, value, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C likeLeft(final Property<T, String> property, final String value, final boolean ignoreCase) {
        return this.likeLeft(property, value, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeLeft(final Property<T, String> property, final String value,
                       final boolean ignoreCase, final LogicSymbol slot) {
        return this.likeLeft(property, value, null, ignoreCase, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default C likeLeft(final Property<T, String> property, final String value, final Character escape) {
        return this.likeLeft(property, value, escape, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeLeft(final Property<T, String> property, final String value,
                       final Character escape, final LogicSymbol slot) {
        return this.likeLeft(property, value, escape, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C likeLeft(final Property<T, String> property, final String value,
                       final Character escape, final boolean ignoreCase) {
        return this.likeLeft(property, value, escape, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeLeft(final Property<T, String> property, final String value,
                       final Character escape, final boolean ignoreCase, final LogicSymbol slot) {
        return this.likeLeft(this.convert(property), value, escape, ignoreCase, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C likeLeft(final String property, final String value) {
        return this.likeLeft(property, value, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeLeft(final String property, final String value, final LogicSymbol slot) {
        return this.likeLeft(property, value, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C likeLeft(final String property, final String value, final boolean ignoreCase) {
        return this.likeLeft(property, value, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeLeft(final String property, final String value, final boolean ignoreCase, final LogicSymbol slot) {
        return this.likeLeft(property, value, null, ignoreCase, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default C likeLeft(final String property, final String value, final Character escape) {
        return this.likeLeft(property, value, escape, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeLeft(final String property, final String value, final Character escape, final LogicSymbol slot) {
        return this.likeLeft(property, value, escape, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C likeLeft(final String property, final String value, final Character escape, final boolean ignoreCase) {
        return this.likeLeft(property, value, escape, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeLeft(final String property, final String value, final Character escape,
                       final boolean ignoreCase, final LogicSymbol slot) {
        return this.like(property, value, LikeMatchMode.END, escape, ignoreCase, slot);
    }

    // endregion

    // region Like right methods

    /**
     * like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C likeRight(final Property<T, String> property, final String value) {
        return this.likeRight(property, value, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeRight(final Property<T, String> property, final String value, final LogicSymbol slot) {
        return this.likeRight(property, value, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C likeRight(final Property<T, String> property, final String value, final boolean ignoreCase) {
        return this.likeRight(property, value, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeRight(final Property<T, String> property, final String value,
                        final boolean ignoreCase, final LogicSymbol slot) {
        return this.likeRight(property, value, null, ignoreCase, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default C likeRight(final Property<T, String> property, final String value, final Character escape) {
        return this.likeRight(property, value, escape, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeRight(final Property<T, String> property, final String value,
                        final Character escape, final LogicSymbol slot) {
        return this.likeRight(property, value, escape, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C likeRight(final Property<T, String> property, final String value,
                        final Character escape, final boolean ignoreCase) {
        return this.likeRight(property, value, escape, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeRight(final Property<T, String> property, final String value,
                        final Character escape, final boolean ignoreCase, final LogicSymbol slot) {
        return this.likeRight(this.convert(property), value, escape, ignoreCase, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C likeRight(final String property, final String value) {
        return this.likeRight(property, value, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeRight(final String property, final String value, final LogicSymbol slot) {
        return this.likeRight(property, value, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C likeRight(final String property, final String value, final boolean ignoreCase) {
        return this.likeRight(property, value, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeRight(final String property, final String value, final boolean ignoreCase, final LogicSymbol slot) {
        return this.likeRight(property, value, null, ignoreCase, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default C likeRight(final String property, final String value, final Character escape) {
        return this.likeRight(property, value, escape, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeRight(final String property, final String value, final Character escape, final LogicSymbol slot) {
        return this.likeRight(property, value, escape, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C likeRight(final String property, final String value, final Character escape, final boolean ignoreCase) {
        return this.likeRight(property, value, escape, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeRight(final String property, final String value, final Character escape,
                        final boolean ignoreCase, final LogicSymbol slot) {
        return this.like(property, value, LikeMatchMode.START, escape, ignoreCase, slot);
    }

    // endregion

    // region Like any methods

    /**
     * like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C likeAny(final Property<T, String> property, final String value) {
        return this.likeAny(property, value, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeAny(final Property<T, String> property, final String value, final LogicSymbol slot) {
        return this.likeAny(property, value, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C likeAny(final Property<T, String> property, final String value, final boolean ignoreCase) {
        return this.likeAny(property, value, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeAny(final Property<T, String> property, final String value,
                      final boolean ignoreCase, final LogicSymbol slot) {
        return this.likeAny(property, value, null, ignoreCase, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default C likeAny(final Property<T, String> property, final String value, final Character escape) {
        return this.likeAny(property, value, escape, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeAny(final Property<T, String> property, final String value,
                      final Character escape, final LogicSymbol slot) {
        return this.likeAny(property, value, escape, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C likeAny(final Property<T, String> property, final String value,
                      final Character escape, final boolean ignoreCase) {
        return this.likeAny(property, value, escape, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeAny(final Property<T, String> property, final String value,
                      final Character escape, final boolean ignoreCase, final LogicSymbol slot) {
        return this.likeAny(this.convert(property), value, escape, ignoreCase, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C likeAny(final String property, final String value) {
        return this.likeAny(property, value, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeAny(final String property, final String value, final LogicSymbol slot) {
        return this.likeAny(property, value, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C likeAny(final String property, final String value, final boolean ignoreCase) {
        return this.likeAny(property, value, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeAny(final String property, final String value, final boolean ignoreCase, final LogicSymbol slot) {
        return this.likeAny(property, value, null, ignoreCase, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default C likeAny(final String property, final String value, final Character escape) {
        return this.likeAny(property, value, escape, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeAny(final String property, final String value, final Character escape, final LogicSymbol slot) {
        return this.likeAny(property, value, escape, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C likeAny(final String property, final String value, final Character escape, final boolean ignoreCase) {
        return this.likeAny(property, value, escape, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C likeAny(final String property, final String value, final Character escape,
                      final boolean ignoreCase, final LogicSymbol slot) {
        return this.like(property, value, LikeMatchMode.ANYWHERE, escape, ignoreCase, slot);
    }

    // endregion

    // region Like base methods

    /**
     * like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param matches  匹配模式
     * @return {@code this}
     */
    default C like(final Property<T, String> property, final String value, final LikeMatchMode matches) {
        return this.like(property, value, matches, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param matches  匹配模式
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C like(final Property<T, String> property, final String value, final LikeMatchMode matches,
                   final LogicSymbol slot) {
        return this.like(property, value, matches, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param matches    匹配模式
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C like(final Property<T, String> property, final String value, final LikeMatchMode matches,
                   final boolean ignoreCase) {
        return this.like(property, value, matches, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param matches    匹配模式
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C like(final Property<T, String> property, final String value, final LikeMatchMode matches,
                   final boolean ignoreCase, final LogicSymbol slot) {
        return this.like(property, value, matches, null, ignoreCase, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param matches  匹配模式
     * @param escape   转义字符
     * @return {@code this}
     */
    default C like(final Property<T, String> property, final String value, final LikeMatchMode matches,
                   final Character escape) {
        return this.like(property, value, matches, escape, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param matches  匹配模式
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C like(final Property<T, String> property, final String value, final LikeMatchMode matches,
                   final Character escape, final LogicSymbol slot) {
        return this.like(property, value, matches, escape, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param matches    匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C like(final Property<T, String> property, final String value, final LikeMatchMode matches,
                   final Character escape, final boolean ignoreCase) {
        return this.like(property, value, matches, escape, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param matches    匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C like(final Property<T, String> property, final String value, final LikeMatchMode matches,
                   final Character escape, final boolean ignoreCase, final LogicSymbol slot) {
        return this.like(this.convert(property), value, matches, escape, ignoreCase, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param matches  匹配模式
     * @return {@code this}
     */
    default C like(final String property, final String value, final LikeMatchMode matches) {
        return this.like(property, value, matches, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param matches  匹配模式
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C like(final String property, final String value, final LikeMatchMode matches, final LogicSymbol slot) {
        return this.like(property, value, matches, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param matches    匹配模式
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C like(final String property, final String value, final LikeMatchMode matches,
                   final boolean ignoreCase) {
        return this.like(property, value, matches, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param matches    匹配模式
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C like(final String property, final String value, final LikeMatchMode matches,
                   final boolean ignoreCase, final LogicSymbol slot) {
        return this.like(property, value, matches, null, ignoreCase, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param matches  匹配模式
     * @param escape   转义字符
     * @return {@code this}
     */
    default C like(final String property, final String value, final LikeMatchMode matches, final Character escape) {
        return this.like(property, value, matches, escape, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param matches  匹配模式
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C like(final String property, final String value, final LikeMatchMode matches,
                   final Character escape, final LogicSymbol slot) {
        return this.like(property, value, matches, escape, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param matches    匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C like(final String property, final String value, final LikeMatchMode matches,
                   final Character escape, final boolean ignoreCase) {
        return this.like(property, value, matches, escape, ignoreCase, this.slot());
    }

    /**
     * like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param matches    匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    C like(final String property, final String value, final LikeMatchMode matches, final Character escape,
           final boolean ignoreCase, final LogicSymbol slot);

    // endregion

    // endregion

    // region Not like methods

    // region Not like left methods

    /**
     * not like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C notLikeLeft(final Property<T, String> property, final String value) {
        return this.notLikeLeft(property, value, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeLeft(final Property<T, String> property, final String value, final LogicSymbol slot) {
        return this.notLikeLeft(property, value, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C notLikeLeft(final Property<T, String> property, final String value, final boolean ignoreCase) {
        return this.notLikeLeft(property, value, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeLeft(final Property<T, String> property, final String value,
                          final boolean ignoreCase, final LogicSymbol slot) {
        return this.notLikeLeft(property, value, null, ignoreCase, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default C notLikeLeft(final Property<T, String> property, final String value, final Character escape) {
        return this.notLikeLeft(property, value, escape, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeLeft(final Property<T, String> property, final String value,
                          final Character escape, final LogicSymbol slot) {
        return this.notLikeLeft(property, value, escape, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C notLikeLeft(final Property<T, String> property, final String value,
                          final Character escape, final boolean ignoreCase) {
        return this.notLikeLeft(property, value, escape, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeLeft(final Property<T, String> property, final String value,
                          final Character escape, final boolean ignoreCase, final LogicSymbol slot) {
        return this.notLikeLeft(this.convert(property), value, escape, ignoreCase, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C notLikeLeft(final String property, final String value) {
        return this.notLikeLeft(property, value, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeLeft(final String property, final String value, final LogicSymbol slot) {
        return this.notLikeLeft(property, value, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C notLikeLeft(final String property, final String value, final boolean ignoreCase) {
        return this.notLikeLeft(property, value, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeLeft(final String property, final String value, final boolean ignoreCase, final LogicSymbol slot) {
        return this.notLikeLeft(property, value, null, ignoreCase, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default C notLikeLeft(final String property, final String value, final Character escape) {
        return this.notLikeLeft(property, value, escape, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeLeft(final String property, final String value, final Character escape, final LogicSymbol slot) {
        return this.notLikeLeft(property, value, escape, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C notLikeLeft(final String property, final String value, final Character escape,
                          final boolean ignoreCase) {
        return this.notLikeLeft(property, value, escape, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeLeft(final String property, final String value, final Character escape,
                          final boolean ignoreCase, final LogicSymbol slot) {
        return this.notLike(property, value, LikeMatchMode.END, escape, ignoreCase, slot);
    }

    // endregion

    // region Not like right methods

    /**
     * not like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C notLikeRight(final Property<T, String> property, final String value) {
        return this.notLikeRight(property, value, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeRight(final Property<T, String> property, final String value, final LogicSymbol slot) {
        return this.notLikeRight(property, value, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C notLikeRight(final Property<T, String> property, final String value, final boolean ignoreCase) {
        return this.notLikeRight(property, value, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeRight(final Property<T, String> property, final String value,
                           final boolean ignoreCase, final LogicSymbol slot) {
        return this.notLikeRight(property, value, null, ignoreCase, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default C notLikeRight(final Property<T, String> property, final String value, final Character escape) {
        return this.notLikeRight(property, value, escape, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeRight(final Property<T, String> property, final String value,
                           final Character escape, final LogicSymbol slot) {
        return this.notLikeRight(property, value, escape, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C notLikeRight(final Property<T, String> property, final String value,
                           final Character escape, final boolean ignoreCase) {
        return this.notLikeRight(property, value, escape, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeRight(final Property<T, String> property, final String value,
                           final Character escape, final boolean ignoreCase, final LogicSymbol slot) {
        return this.notLikeRight(this.convert(property), value, escape, ignoreCase, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C notLikeRight(final String property, final String value) {
        return this.notLikeRight(property, value, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeRight(final String property, final String value, final LogicSymbol slot) {
        return this.notLikeRight(property, value, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C notLikeRight(final String property, final String value, final boolean ignoreCase) {
        return this.notLikeRight(property, value, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeRight(final String property, final String value, final boolean ignoreCase, final LogicSymbol slot) {
        return this.notLikeRight(property, value, null, ignoreCase, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default C notLikeRight(final String property, final String value, final Character escape) {
        return this.notLikeRight(property, value, escape, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeRight(final String property, final String value, final Character escape, final LogicSymbol slot) {
        return this.notLikeRight(property, value, escape, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C notLikeRight(final String property, final String value, final Character escape,
                           final boolean ignoreCase) {
        return this.notLikeRight(property, value, escape, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeRight(final String property, final String value, final Character escape,
                           final boolean ignoreCase, final LogicSymbol slot) {
        return this.notLike(property, value, LikeMatchMode.START, escape, ignoreCase, slot);
    }

    // endregion

    // region Not like any methods

    /**
     * not like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @return {@code this}
     */
    default C notLikeAny(final Property<T, String> property, final String value) {
        return this.notLikeAny(property, value, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeAny(final Property<T, String> property, final String value, final LogicSymbol slot) {
        return this.notLikeAny(property, value, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C notLikeAny(final Property<T, String> property, final String value, final boolean ignoreCase) {
        return this.notLikeAny(property, value, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeAny(final Property<T, String> property, final String value,
                         final boolean ignoreCase, final LogicSymbol slot) {
        return this.notLikeAny(property, value, null, ignoreCase, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default C notLikeAny(final Property<T, String> property, final String value, final Character escape) {
        return this.notLikeAny(property, value, escape, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeAny(final Property<T, String> property, final String value,
                         final Character escape, final LogicSymbol slot) {
        return this.notLikeAny(property, value, escape, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C notLikeAny(final Property<T, String> property, final String value,
                         final Character escape, final boolean ignoreCase) {
        return this.notLikeAny(property, value, escape, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeAny(final Property<T, String> property, final String value,
                         final Character escape, final boolean ignoreCase, final LogicSymbol slot) {
        return this.notLikeAny(this.convert(property), value, escape, ignoreCase, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default C notLikeAny(final String property, final String value) {
        return this.notLikeAny(property, value, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeAny(final String property, final String value, final LogicSymbol slot) {
        return this.notLikeAny(property, value, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C notLikeAny(final String property, final String value, final boolean ignoreCase) {
        return this.notLikeAny(property, value, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeAny(final String property, final String value, final boolean ignoreCase, final LogicSymbol slot) {
        return this.notLikeAny(property, value, null, ignoreCase, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default C notLikeAny(final String property, final String value, final Character escape) {
        return this.notLikeAny(property, value, escape, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeAny(final String property, final String value, final Character escape, final LogicSymbol slot) {
        return this.notLikeAny(property, value, escape, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C notLikeAny(final String property, final String value, final Character escape,
                         final boolean ignoreCase) {
        return this.notLikeAny(property, value, escape, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLikeAny(final String property, final String value, final Character escape,
                         final boolean ignoreCase, final LogicSymbol slot) {
        return this.notLike(property, value, LikeMatchMode.ANYWHERE, escape, ignoreCase, slot);
    }

    // endregion

    // region Not like base methods

    /**
     * not like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param matches  匹配模式
     * @return {@code this}
     */
    default C notLike(final Property<T, String> property, final String value, final LikeMatchMode matches) {
        return this.notLike(property, value, matches, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param matches  匹配模式
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLike(final Property<T, String> property, final String value, final LikeMatchMode matches,
                      final LogicSymbol slot) {
        return this.notLike(property, value, matches, false, slot);
    }

    /**
     * like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param matches    匹配模式
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C notLike(final Property<T, String> property, final String value, final LikeMatchMode matches,
                      final boolean ignoreCase) {
        return this.notLike(property, value, matches, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param matches    匹配模式
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLike(final Property<T, String> property, final String value, final LikeMatchMode matches,
                      final boolean ignoreCase, final LogicSymbol slot) {
        return this.notLike(property, value, matches, null, ignoreCase, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param matches  匹配模式
     * @param escape   转义字符
     * @return {@code this}
     */
    default C notLike(final Property<T, String> property, final String value, final LikeMatchMode matches,
                      final Character escape) {
        return this.notLike(property, value, matches, escape, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property {@link Property}
     * @param value    值
     * @param matches  匹配模式
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLike(final Property<T, String> property, final String value, final LikeMatchMode matches,
                      final Character escape, final LogicSymbol slot) {
        return this.notLike(property, value, matches, escape, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param matches    匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C notLike(final Property<T, String> property, final String value, final LikeMatchMode matches,
                      final Character escape, final boolean ignoreCase) {
        return this.notLike(property, value, matches, escape, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property   {@link Property}
     * @param value      值
     * @param matches    匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLike(final Property<T, String> property, final String value, final LikeMatchMode matches,
                      final Character escape, final boolean ignoreCase, final LogicSymbol slot) {
        return this.notLike(this.convert(property), value, matches, escape, ignoreCase, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param matches  匹配模式
     * @return {@code this}
     */
    default C notLike(final String property, final String value, final LikeMatchMode matches) {
        return this.notLike(property, value, matches, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param matches  匹配模式
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLike(final String property, final String value, final LikeMatchMode matches, final LogicSymbol slot) {
        return this.notLike(property, value, matches, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param matches    匹配模式
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C notLike(final String property, final String value, final LikeMatchMode matches,
                      final boolean ignoreCase) {
        return this.notLike(property, value, matches, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param matches    匹配模式
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLike(final String property, final String value, final LikeMatchMode matches,
                      final boolean ignoreCase, final LogicSymbol slot) {
        return this.notLike(property, value, matches, null, ignoreCase, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param matches  匹配模式
     * @param escape   转义字符
     * @return {@code this}
     */
    default C notLike(final String property, final String value, final LikeMatchMode matches, final Character escape) {
        return this.notLike(property, value, matches, escape, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property 属性
     * @param value    值
     * @param matches  匹配模式
     * @param escape   转义字符
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C notLike(final String property, final String value, final LikeMatchMode matches,
                      final Character escape, final LogicSymbol slot) {
        return this.notLike(property, value, matches, escape, false, slot);
    }

    /**
     * not like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param matches    匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @return {@code this}
     */
    default C notLike(final String property, final String value, final LikeMatchMode matches,
                      final Character escape, final boolean ignoreCase) {
        return this.notLike(property, value, matches, escape, ignoreCase, this.slot());
    }

    /**
     * not like模糊匹配
     *
     * @param property   属性
     * @param value      值
     * @param matches    匹配模式
     * @param escape     转义字符
     * @param ignoreCase 是否忽略大小写
     * @param slot       {@link LogicSymbol}
     * @return {@code this}
     */
    C notLike(final String property, final String value, final LikeMatchMode matches, final Character escape,
              final boolean ignoreCase, final LogicSymbol slot);

    // endregion

    // endregion
}
