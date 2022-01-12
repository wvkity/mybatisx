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
package io.github.mybatisx.core.criteria;

import io.github.mybatisx.core.expression.Expression;

import java.util.Collection;
import java.util.function.Function;

/**
 * 条件包装接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
public interface CriteriaWrapper<T, C extends CriteriaWrapper<T, C>> extends GenericCriteria<T> {

    /**
     * 嵌套条件
     *
     * @param apply {@link Function}
     * @return {@code this}
     */
    default C nested(final Function<C, C> apply) {
        return this.nested(false, apply);
    }

    /**
     * 嵌套条件
     *
     * @param apply {@link Function}
     * @return {@code this}
     */
    C nested(final boolean not, final Function<C, C> apply);

    /**
     * and嵌套条件
     *
     * @param apply {@link Function}
     * @return {@code this}
     */
    default C and(final Function<C, C> apply) {
        return this.and(false, apply);
    }

    /**
     * and嵌套条件
     *
     * @param not   是否拼接not
     * @param apply {@link Function}
     * @return {@code this}
     */
    C and(final boolean not, final Function<C, C> apply);

    /**
     * or嵌套条件
     *
     * @param apply {@link Function}
     * @return {@code this}
     */
    default C or(final Function<C, C> apply) {
        return this.or(false, apply);
    }

    /**
     * or嵌套条件
     *
     * @param not   是否拼接not
     * @param apply {@link Function}
     * @return {@code this}
     */
    C or(final boolean not, final Function<C, C> apply);

    /**
     * 条件条件
     *
     * @param expression 条件表达式
     * @return {@code this}
     */
    C where(final Expression expression);

    /**
     * 条件条件
     *
     * @param expressions 条件表达式列表
     * @return {@code this}
     */
    C where(final Expression... expressions);

    /**
     * 添加多个条件
     *
     * @param expressions 条件表达式列表
     * @return {@code this}
     */
    C where(final Collection<Expression> expressions);
}
