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
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.core.criteria.AbstractCriteriaSupport;
import io.github.mybatisx.core.param.SingleParam;
import io.github.mybatisx.matcher.Matcher;

/**
 * 抽象基础条件(支持Lambda表达式)
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
@SuppressWarnings({"serial"})
public abstract class AbstractLambdaCriteria<T, C extends LambdaCriteriaWrapper<T, C>> extends
        AbstractCriteriaSupport<T, C> implements LambdaCriteriaWrapper<T, C> {

    @Override
    public <V> C idEq(LogicSymbol slot, V value, Matcher<V> matcher) {
        if (this.early(value, matcher)) {
            this.checkPrimaryKey();
            final Column id = this.getPrimaryKey();
            this.conditionConverter.accept(id.getColumn(), SingleParam.builder()
                    .symbol(Symbol.EQ)
                    .slot(slot)
                    .typeHandler(id.getTypeHandler())
                    .jdbcType(id.getJdbcType())
                    .javaType(id.getDescriptor().getJavaType())
                    .value(value)
                    .build());
        }
        return this.self();
    }

}
