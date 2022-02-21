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
import io.github.mybatisx.core.criteria.query.Query;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.sql.parsing.SqlParser;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * 子查询条件
 *
 * @author wvkity
 * @created 2022/2/20
 * @since 1.0.0
 */
@Builder
@RequiredArgsConstructor
public class SubQueryCondition implements Criterion {

    private static final long serialVersionUID = -646723666848794851L;
    /**
     * {@link Criteria}
     */
    private final Criteria<?> criteria;
    /**
     * 表别名
     */
    private final String alias;
    /**
     * 字段名
     */
    @Getter
    private final String column;
    /**
     * {@link Query}
     */
    private final Query<?> query;
    /**
     * 条件符号
     */
    @Getter
    private final Symbol symbol;
    /**
     * 逻辑符号
     */
    private final LogicSymbol slot;

    /**
     * 获取表别名
     *
     * @return 表别名
     */
    protected String getAlias() {
        return Objects.nonNull(this.alias) ? this.alias : Optional.ofNullable(criteria).map(it ->
                Objects.ifNull(it.as(), Constants.EMPTY)).orElse(Constants.EMPTY);
    }

    @Override
    public String getFragment(ParameterConverter pc, PlaceholderConverter phc) {
        return this.getFragment();
    }

    @Override
    public String getFragment() {
        final String conditionBody;
        if (this.query != null && Strings.isNotWhitespace(conditionBody = this.query.getFragment())) {
            final StringBuilder sb = new StringBuilder(150);
            if (this.slot != null && this.slot != LogicSymbol.NONE) {
                sb.append(this.slot.getFragment()).append(SqlSymbol.SPACE);
            }
            final String as = this.getAlias();
            if (Strings.isNotWhitespace(as)) {
                sb.append(as).append(SqlSymbol.DOT);
            }
            sb.append(this.column).append(SqlSymbol.SPACE)
                    .append(this.symbol.getFragment()).append(SqlSymbol.SPACE)
                    .append(SqlSymbol.START_BRACKET);
            final SqlParser parser = this.query.getSqlParser();
            if (parser != null) {
                sb.append(parser.smartRemoveOrderBy(conditionBody));
            } else {
                sb.append(conditionBody);
            }
            return sb.append(SqlSymbol.END_BRACKET).toString();
        }
        return Constants.EMPTY;
    }
}
