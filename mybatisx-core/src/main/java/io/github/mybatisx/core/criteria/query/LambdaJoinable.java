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

import io.github.mybatisx.core.property.Property;

/**
 * 关联查询接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/2/9
 * @since 1.0.0
 */
public interface LambdaJoinable<T, C extends LambdaJoinable<T, C>> extends Joinable<T>, QueryMixture<T, C>,
        LambdaQueryWrapper<T, C> {

    /**
     * 关联条件
     *
     * @param leftProperty 左表属性
     * @return {@code this}
     */
    default C on(final Property<T, ?> leftProperty) {
        return this.on(this.convert(leftProperty));
    }

    /**
     * 关联条件
     *
     * @param leftProperty 左表属性
     * @return {@code this}
     */
    C on(final String leftProperty);

    /**
     * 关联条件
     *
     * @param leftProperty 左表属性
     * @param rightColumn  右表关联字段
     * @return {@code this}
     */
    default C onWith(final Property<T, ?> leftProperty, final String rightColumn) {
        return this.onWith(this.convert(leftProperty), rightColumn);
    }

    /**
     * 关联条件
     *
     * @param leftProperty 左表属性
     * @param rightColumn  右表关联字段
     * @return {@code this}
     */
    C onWith(final String leftProperty, final String rightColumn);

}
