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
package io.github.mybatisx.core.support.function;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.core.criteria.query.Query;
import io.github.mybatisx.lang.Strings;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 抽象聚合函数
 *
 * @author wvkity
 * @created 2022/1/28
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
@SuppressWarnings({"serial"})
public abstract class AbstractAggFunction implements AggFunction {

    /**
     * {@link Query}
     */
    protected final Query<?> query;
    /**
     * 表别名
     */
    protected final String tableAlias;
    /**
     * 字段名
     */
    protected final String column;
    /**
     * 函数名
     */
    protected final String name;
    /**
     * 别名
     */
    protected final String alias;
    /**
     * 是否去重
     */
    protected final boolean distinct;
    /**
     * 小数位数
     */
    protected final Integer scale;

    /**
     * 获取表别名
     *
     * @return 表别名
     */
    protected String as() {
        String ias = "";
        if (Strings.isNotWhitespace(this.tableAlias)) {
            ias = this.tableAlias + SqlSymbol.DOT;
        } else if (this.query != null) {
            final String _$as = this.query.as();
            if (Strings.isNotWhitespace(_$as)) {
                ias = _$as + SqlSymbol.DOT;
            } else {
                ias = Constants.EMPTY;
            }
        }
        return ias;
    }

    /**
     * 渲染SQL片段
     *
     * @return SQL片段
     */
    protected String render() {
        final StringBuilder sb = new StringBuilder(45);
        final String ias = this.as();
        final String _$column = this.column;
        final boolean isScale = this.scale != null && this.scale > -1;
        final boolean isDistinct = this.distinct && !SqlSymbol.STAR.equals(_$column)
                && !"0".equals(_$column) && !"1".equals(_$column);
        if (isScale) {
            sb.append("CAST(");
        }
        sb.append(this.name).append(SqlSymbol.START_BRACKET);
        if (isDistinct) {
            sb.append(SqlSymbol.DISTINCT);
            if (Strings.isNotWhitespace(ias)) {
                sb.append(ias);
            }
        }
        sb.append(this.column).append(SqlSymbol.END_BRACKET);
        if (isScale) {
            sb.append(" AS DECIMAL(38, ").append(this.scale).append("))");
        }
        return sb.toString();
    }

    @Override
    public String getFragment(boolean isQuery) {
        if (isQuery) {
            final String _$alias = this.alias;
            if (Strings.isNotWhitespace(_$alias)) {
                final StringBuilder fragment = new StringBuilder(this.render())
                        .append(SqlSymbol.SPACE)
                        .append(SqlSymbol.AS)
                        .append(SqlSymbol.SPACE);
                if (_$alias.contains(SqlSymbol.DOT)) {
                    fragment.append(SqlSymbol.DOUBLE_QUOTES)
                            .append(this.alias)
                            .append(SqlSymbol.DOUBLE_QUOTES);
                } else {
                    final String refProp = Optional.ofNullable(this.query)
                            .map(Query::getReference).orElse(Constants.EMPTY);
                    if (Strings.isNotWhitespace(refProp)) {
                        fragment.append(SqlSymbol.DOUBLE_QUOTES)
                                .append(refProp)
                                .append(SqlSymbol.DOT)
                                .append(this.alias)
                                .append(SqlSymbol.DOUBLE_QUOTES);
                    } else {
                        fragment.append(this.alias);
                    }
                }
                return fragment.toString();
            }
        }
        return this.render();
    }
}
