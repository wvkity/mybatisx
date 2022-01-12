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
package io.github.mybatisx.core.expression;

import io.github.mybatisx.base.criteria.Criteria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 抽象表达式
 *
 * @author wvkity
 * @created 2022/1/12
 * @since 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class AbstractExpression implements Expression {

    /**
     * {@link Criteria}
     */
    protected Criteria<?> criteria;
    /**
     * 表别名
     */
    protected final String alias;
    /**
     * 字段名
     */
    protected final String column;

    @Override
    public void setIfNecessary(Criteria<?> criteria) {
        if (this.criteria == null) {
            this.criteria = criteria;
        }
    }
}
