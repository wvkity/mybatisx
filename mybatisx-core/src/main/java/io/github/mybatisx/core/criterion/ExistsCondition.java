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
import io.github.mybatisx.base.criterion.Criterion;
import io.github.mybatisx.core.criteria.query.Query;
import io.github.mybatisx.sql.parsing.SqlParser;
import lombok.Getter;

/**
 * exists条件
 *
 * @author wvkity
 * @created 2022/2/22
 * @since 1.0.0
 */
public class ExistsCondition implements Criterion {

    private static final long serialVersionUID = 7210812453781128758L;
    /**
     * {@link Query}
     */
    @Getter
    private final Query<?> query;
    /**
     * {@link Symbol}
     */
    @Getter
    private final Symbol symbol;
    /**
     * {@link LogicSymbol}
     */
    private final LogicSymbol slot;

    protected ExistsCondition(Query<?> query, LogicSymbol slot, boolean not) {
        this.query = query;
        this.slot = slot;
        this.symbol = not ? Symbol.NOT_EXISTS : Symbol.EXISTS;
    }

    @Override
    public String getFragment(ParameterConverter pc, PlaceholderConverter phc) {
        return this.getFragment();
    }

    @Override
    public String getFragment() {
        if (this.query != null) {
            final StringBuilder sb = new StringBuilder(150);
            if (this.slot != null && this.slot != LogicSymbol.NONE) {
                sb.append(this.slot.getFragment()).append(SqlSymbol.SPACE);
            }
            sb.append(this.symbol.getFragment()).append(SqlSymbol.SPACE).append(SqlSymbol.START_BRACKET);
            final SqlParser parser = this.query.getSqlParser();
            final String selectBody = this.query.getFragment();
            if (parser != null) {
                sb.append(parser.smartParseOfExists(selectBody));
            } else {
                sb.append(selectBody);
            }
            return sb.append(SqlSymbol.END_BRACKET).toString();
        }
        return Constants.EMPTY;
    }

    /**
     * 创建{@link ExistsCondition}对象
     *
     * @param query {@link Query}
     * @param slot  {@link LogicSymbol}
     * @return {@link ExistsCondition}
     */
    public static ExistsCondition exists(final Query<?> query, final LogicSymbol slot) {
        if (query != null) {
            return new ExistsCondition(query, slot, false);
        }
        return null;
    }

    /**
     * 创建{@link ExistsCondition}对象
     *
     * @param query {@link Query}
     * @param slot  {@link LogicSymbol}
     * @return {@link ExistsCondition}
     */
    public static ExistsCondition notExists(final Query<?> query, final LogicSymbol slot) {
        if (query != null) {
            return new ExistsCondition(query, slot, true);
        }
        return null;
    }

}
