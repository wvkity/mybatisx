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

import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.core.param.Param;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * 嵌套表达式
 *
 * @author wvkity
 * @created 2022/1/15
 * @since 1.0.0
 */
@Getter
@ToString
@Builder(toBuilder = true)
public class NestedExpression implements Expression {
    
    /**
     * 是否拼接not
     */
    private final boolean not;
    /**
     * 逻辑符号
     */
    private LogicSymbol slot;
    /**
     * 表达式列表
     */
    private final List<Expression> expressions;

    @Override
    public Criteria<?> getCriteria() {
        // empty
        return null;
    }

    @Override
    public Param getParam() {
        // empty
        return null;
    }

    @Override
    public String getColumn() {
        // empty
        return null;
    }

    @Override
    public String getAlias() {
        // empty
        return null;
    }

    @Override
    public void ifSlotNull(LogicSymbol slot) {
        if (this.slot == null) {
            this.slot = slot;
        }
    }

    @Override
    public void ifCriteriaNull(Criteria<?> criteria) {
        // empty
    }
}
