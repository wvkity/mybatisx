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

import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.matcher.Matcher;

/**
 * 比较条件接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
public interface LambdaCompare<T, C extends LambdaCompare<T, C>> extends Slot<T, C> {

    /**
     * 主键等于
     *
     * @param value 值
     * @param <V>   值类型
     * @return {@code this}
     */
    default <V> C idEq(final V value) {
        return this.idEq(this.slot(), value, null);
    }

    /**
     * 主键等于
     *
     * @param value   值
     * @param matcher {@link Matcher}
     * @param <V>     值类型
     * @return {@code this}
     */
    default <V> C idEq(final V value, final Matcher<V> matcher) {
        return this.idEq(this.slot(), value, matcher);
    }

    /**
     * 主键等于
     *
     * @param slot  {@link LogicSymbol}
     * @param value 值
     * @param <V>   值类型
     * @return {@code this}
     */
    default <V> C idEq(final LogicSymbol slot, final V value) {
        return this.idEq(slot, value, null);
    }

    /**
     * 主键等于
     *
     * @param slot    {@link LogicSymbol}
     * @param value   值
     * @param matcher {@link Matcher}
     * @param <V>     值类型
     * @return {@code this}
     */
    <V> C idEq(final LogicSymbol slot, final V value, final Matcher<V> matcher);

}
