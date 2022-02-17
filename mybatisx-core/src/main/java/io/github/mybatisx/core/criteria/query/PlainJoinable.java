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

import io.github.mybatisx.core.property.LambdaMetadataWeakCache;
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
public interface PlainJoinable<T, C extends PlainJoinable<T, C>> extends Joinable<T>, QueryMixture<T, C>,
        PlainQueryWrapper<T, C> {

    /**
     * 关联条件
     *
     * @param leftColumn 左表关联字段
     * @return {@code this}
     */
    C colOn(final String leftColumn);

    /**
     * 关联条件
     *
     * @param leftColumn  左表关联字段
     * @param rightColumn 右表关联字段
     * @return {@code this}
     */
    C colOn(final String leftColumn, final String rightColumn);

    /**
     * 关联条件
     *
     * @param leftColumn    左表关联字段
     * @param rightProperty 右表属性
     * @param <R>           实体类型
     * @return {@code this}
     */
    default <R> C colOnWith(final String leftColumn, final Property<R, ?> rightProperty) {
        return this.colOnWith(leftColumn, LambdaMetadataWeakCache.getProperty(rightProperty));
    }

    /**
     * 关联条件
     *
     * @param leftColumn    左表关联字段
     * @param rightProperty 右表属性
     * @return {@code this}
     */
    C colOnWith(final String leftColumn, final String rightProperty);

}
