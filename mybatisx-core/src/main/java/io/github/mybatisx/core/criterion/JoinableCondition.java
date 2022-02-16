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
package io.github.mybatisx.core.criterion;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.convert.ParameterConverter;
import io.github.mybatisx.base.convert.PlaceholderConverter;
import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.base.criterion.Criterion;
import io.github.mybatisx.lang.Strings;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 联表条件
 *
 * @author wvkity
 * @created 2022/2/16
 * @since 1.0.0
 */
@Builder
@RequiredArgsConstructor
public class JoinableCondition implements Criterion {

    private static final long serialVersionUID = -5833473504696401918L;

    /**
     * {@link Criteria}
     */
    private final Criteria<?> leftCriteria;
    /**
     * 左表别名
     */
    private final String leftAlias;
    /**
     * 左表关联字段名
     */
    private final String leftColumn;
    /**
     * {@link Criteria}
     */
    private final Criteria<?> rightCriteria;
    /**
     * 右表别名
     */
    private final String rightAlias;
    /**
     * 右表关联字段名
     */
    private final String rightColumn;
    /**
     * 条件符号
     */
    @Getter
    private final Symbol symbol = Symbol.EQ;
    /**
     * 逻辑符号
     */
    private final LogicSymbol slot = LogicSymbol.AND;

    /**
     * 获取表别名
     *
     * @param criteria {@link Criteria}
     * @param alias    表别名
     * @return 表别名
     */
    protected String getAlias(final Criteria<?> criteria, final String alias) {
        final String as;
        return Strings.isNotWhitespace(alias) ? (alias + SqlSymbol.DOT) : criteria == null ? Constants.EMPTY :
                Strings.isNotWhitespace((as = criteria.as())) ? (as + SqlSymbol.DOT) : Constants.EMPTY;
    }

    @Override
    public String getFragment(ParameterConverter pc, PlaceholderConverter phc) {
        return this.getFragment();
    }

    @Override
    public String getFragment() {
        final String ls = this.getAlias(leftCriteria, leftAlias);
        final String rs = this.getAlias(rightCriteria, rightAlias);
        return LogicSymbol.AND.getFragment() + SqlSymbol.SPACE + ls + this.leftColumn + SqlSymbol.SPACE +
                this.symbol.getFragment() + SqlSymbol.SPACE + rs + this.rightColumn;
    }
}
