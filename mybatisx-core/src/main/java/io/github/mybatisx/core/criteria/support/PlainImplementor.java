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

import io.github.mybatisx.core.criteria.Category;
import io.github.mybatisx.core.sql.DefaultSqlManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

/**
 * 基础条件
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2022/1/11
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlainImplementor<T> extends AbstractPlainCriteria<T, PlainImplementor<T>> {

    private static final long serialVersionUID = -2467289129956344730L;

    public PlainImplementor(Class<T> entity) {
        this.entity = entity;
        this.newInit();
        this.sqlManager = new DefaultSqlManager(this, this.fragmentManager);
    }

    @Override
    protected PlainImplementor<T> newInstance() {
        final PlainImplementor<T> it = new PlainImplementor<>();
        it.category = Category.BASIC;
        it.clone(this);
        return it;
    }

    /**
     * 创建{@link PlainImplementor}对象
     *
     * @param entity 实体类
     * @param <T>    实体类型
     * @return {@link PlainImplementor}
     */
    public static <T> PlainImplementor<T> from(final Class<T> entity) {
        return new PlainImplementor<>(entity);
    }

    /**
     * 创建{@link PlainImplementor}对象
     *
     * @param entity 实体类
     * @param action {@link Consumer}
     * @param <T>    实体类型
     * @return {@link PlainImplementor}
     */
    public static <T> PlainImplementor<T> from(final Class<T> entity, final Consumer<PlainImplementor<T>> action) {
        final PlainImplementor<T> it = from(entity);
        if (action != null) {
            action.accept(it);
        }
        return it;
    }
}
