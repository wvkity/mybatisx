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
import io.github.mybatisx.core.criteria.CriteriaWrapper;
import io.github.mybatisx.core.property.Property;

/**
 * 基础条件接口(支持Lambda表达式)
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
public interface LambdaCriteriaWrapper<T, C extends LambdaCriteriaWrapper<T, C>> extends CriteriaWrapper<T, C>,
        GenericCondition<T, C>, LambdaCompare<T, C>, LambdaRange<T, C>, LambdaFuzzy<T, C>, LambdaTemplate<T, C> {

    // region Is null methods

    /**
     * is null
     *
     * @param property {@link Property}
     * @return {@code this}
     */
    default C isNull(final Property<T, ?> property) {
        return this.isNull(property, this.slot());
    }

    /**
     * is null
     *
     * @param property {@link Property}
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C isNull(final Property<T, ?> property, final LogicSymbol slot) {
        return this.isNull(this.convert(property), slot);
    }

    /**
     * is null
     *
     * @param property 属性
     * @return {@code this}
     */
    default C isNull(final String property) {
        return this.isNull(property, this.slot());
    }

    /**
     * is null
     *
     * @param property 属性
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C isNull(final String property, final LogicSymbol slot);

    // endregion

    // region Is not null methods

    /**
     * is not null
     *
     * @param property {@link Property}
     * @return {@code this}
     */
    default C nonNull(final Property<T, ?> property) {
        return this.nonNull(property, this.slot());
    }

    /**
     * is not null
     *
     * @param property {@link Property}
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    default C nonNull(final Property<T, ?> property, final LogicSymbol slot) {
        return this.nonNull(this.convert(property), slot);
    }

    /**
     * is not null
     *
     * @param property 属性
     * @return {@code this}
     */
    default C nonNull(final String property) {
        return this.nonNull(property, this.slot());
    }

    /**
     * is not null
     *
     * @param property 属性
     * @param slot     {@link LogicSymbol}
     * @return {@code this}
     */
    C nonNull(final String property, final LogicSymbol slot);

    // endregion
}
