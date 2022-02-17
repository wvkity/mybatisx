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

import io.github.mybatisx.base.constant.Join;
import io.github.mybatisx.core.property.Property;

/**
 * 关联查询接口
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2022/1/22
 * @since 1.0.0
 */
public interface Joinable<T> extends Query<T> {

    /**
     * 获取联表方式
     *
     * @return {@link Join}
     */
    Join getJoin();

    /**
     * 设置抓取关联表字段
     *
     * @return {@code this}
     */
    Joinable<T> fetch();

    /**
     * 设置是否抓取关联表字段
     *
     * @param fetch 是否抓取
     * @return {@code this}
     */
    Joinable<T> fetch(final boolean fetch);

    /**
     * 是否抓取联表字段
     *
     * @return boolean
     */
    boolean isFetch();

    /**
     * 将当前对象添加到关联列表中
     *
     * @return {@code this}
     */
    Joinable<T> join();

    /**
     * 将当前对象添加到指定的关联查询对象中
     *
     * @param refQuery {@link Query}
     * @return {@code this}
     */
    Joinable<T> join(final Query<?> refQuery);

    /**
     * 关联条件
     *
     * @param rightProperty 右表属性
     * @param <R>           实体类型
     * @return {@code this}
     */
    <R> Joinable<T> onWithRight(final Property<R, ?> rightProperty);

    /**
     * 关联条件
     *
     * @param rightProperty 右表属性
     * @return {@code this}
     */
    Joinable<T> onWithRight(final String rightProperty);

    /**
     * 关联条件
     *
     * @param leftColumn    左表关联字段
     * @param rightProperty 右表属性
     * @param <R>           实体类型
     * @return {@code this}
     */
    <R> Joinable<T> onWithRight(final String leftColumn, final Property<R, ?> rightProperty);

    /**
     * 关联条件
     *
     * @param leftColumn    左表关联字段
     * @param rightProperty 右表属性
     * @return {@code this}
     */
    Joinable<T> onWithRight(final String leftColumn, final String rightProperty);

}
