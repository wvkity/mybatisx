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

import io.github.mybatisx.core.sql.DefaultSqlManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

/**
 * 通用基础条件
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2022/1/11
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenericImplementor<T> extends AbstractBaseCriteria<T, GenericImplementor<T>> {

    private static final long serialVersionUID = -8709011796466994919L;

    public GenericImplementor(Class<T> entity) {
        this.entity = entity;
        this.newInit();
        this.sqlManager = new DefaultSqlManager(this, this.fragmentManager);
    }

    @Override
    protected GenericImplementor<T> newInstance() {
        final GenericImplementor<T> it = new GenericImplementor<>();
        it.clone(this);
        return it;
    }

    /**
     * 创建{@link GenericImplementor}对象
     *
     * @param entity 实体类
     * @param <T>    实体类型
     * @return {@link GenericImplementor}
     */
    public static <T> GenericImplementor<T> from(final Class<T> entity) {
        return new GenericImplementor<>(entity);
    }

    /**
     * 创建{@link GenericImplementor}对象
     *
     * @param entity 实体类
     * @param action {@link Consumer}
     * @param <T>    实体类型
     * @return {@link GenericImplementor}
     */
    public static <T> GenericImplementor<T> from(final Class<T> entity, final Consumer<GenericImplementor<T>> action) {
        final GenericImplementor<T> it = from(entity);
        if (action != null) {
            action.accept(it);
        }
        return it;
    }
}
