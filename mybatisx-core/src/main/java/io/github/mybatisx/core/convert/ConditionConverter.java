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
package io.github.mybatisx.core.convert;

import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.convert.Converter;
import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.base.criterion.Criterion;
import io.github.mybatisx.base.expression.Expression;
import io.github.mybatisx.core.param.Param;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * 条件转换器
 *
 * @author wvkity
 * @created 2022/1/6
 * @since 1.0.0
 */
public interface ConditionConverter extends Converter<Expression<?>, Criterion>, Consumer<Expression<?>> {

    /**
     * 获取{@link Criteria}
     *
     * @return {@link Criteria}
     */
    Criteria<?> getCriteria();

    @Override
    default void accept(Expression<?> expression) {
        Optional.ofNullable(this.convert(expression)).ifPresent(this.getCriteria()::where);
    }

    /**
     * 转换成条件
     *
     * @param column 字段名
     * @param param  {@link Param}
     * @return {@link Criterion}
     */
    Criterion convert(final String column, final Param param);

    /**
     * 转换成条件并添加到当前条件接口容器中
     *
     * @param column 字段名
     * @param param  {@link Param}
     */
    default void accept(final String column, final Param param) {
        Optional.ofNullable(this.convert(column, param)).ifPresent(this.getCriteria()::where);
    }
    
}
