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

import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.base.criterion.Criterion;
import io.github.mybatisx.core.criterion.NestedCondition;
import io.github.mybatisx.core.criterion.StandardCondition;
import io.github.mybatisx.core.expression.Expression;
import io.github.mybatisx.core.expression.NestedExpression;
import io.github.mybatisx.core.param.Param;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认条件转换器
 *
 * @author wvkity
 * @created 2022/1/6
 * @since 1.0.0
 */
@Slf4j
public class DefaultConditionConverter implements ConditionConverter {

    /**
     * {@link Criteria}
     */
    @Getter
    private final Criteria<?> criteria;

    public DefaultConditionConverter(Criteria<?> criteria) {
        this.criteria = criteria;
    }

    @Override
    public Criterion convert(Expression src) {
        if (src != null) {
            if (src instanceof NestedExpression) {
                // 嵌套条件表达式
                final NestedExpression nestedExpr = (NestedExpression) src;
                final List<Expression> expressions = nestedExpr.getExpressions();
                if (Objects.isNotEmpty(expressions)) {
                    final List<Criterion> conditions = new ArrayList<>(expressions.size());
                    for (Expression it : expressions) {
                        final Criterion condition = this.convert(it);
                        if (condition != null) {
                            conditions.add(condition);
                        }
                    }
                    if (Objects.isNotEmpty(conditions)) {
                        return NestedCondition.builder()
                                .not(nestedExpr.isNot())
                                .slot(nestedExpr.getSlot())
                                .conditions(conditions)
                                .build();
                    }
                }
            } else {
                return convert(src.getCriteria(), src.getColumn(), src.getAlias(), src.getParam());
            }
        }
        return null;
    }

    @Override
    public Criterion convert(String column, Param param) {
        return this.convert(this.criteria, column, null, param);
    }

    protected Criterion convert(final Criteria<?> criteria, final String column, final String alias,
                                final Param param) {
        if (Strings.isNotWhitespace(column) && param != null) {
            return StandardCondition.builder()
                    .criteria(criteria)
                    .alias(alias)
                    .column(column)
                    .param(param)
                    .symbol(param.getSymbol())
                    .orgValue(param.getValue())
                    .build();
        }
        return null;
    }

}
