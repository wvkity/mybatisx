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
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.convert.ParameterConverter;
import io.github.mybatisx.base.convert.PlaceholderConverter;
import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.base.criterion.Criterion;
import io.github.mybatisx.core.param.Param;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * 条件
 *
 * @author wvkity
 * @created 2022/1/7
 * @since 1.0.0
 */
@Builder
@RequiredArgsConstructor
public class StandardCondition implements Criterion {

    private static final long serialVersionUID = -3900260012885564204L;
    /**
     * {@link Criteria}对象
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
     * 条件符号
     */
    @Getter
    private final Symbol symbol;
    /**
     * 参数值
     */
    @Getter
    private final Object orgValue;
    /**
     * 条件片段
     */
    private final String fragment;
    /**
     * 参数
     */
    private final Param param;

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
    public String getFragment() {
        return Constants.EMPTY;
    }

    @Override
    public String getFragment(ParameterConverter pc, PlaceholderConverter phc) {
        final StringBuilder sb = new StringBuilder(30);
        Strings.ifNotWhitespaceThen(this.getAlias(), it -> sb.append(it).append(SqlSymbol.DOT));
        Strings.ifNotWhitespaceThen(this.column, sb::append);
        final String replacement = sb.toString();
        final String template = this.param.parse(pc, phc, this.criteria == null ? null : this.criteria.getDialect());
        if (template.contains(SqlSymbol.COLUMN_PLACEHOLDER)) {
            return template.replaceAll(SqlSymbol.COLUMN_PLACEHOLDER, replacement);
        } else if (template.contains(SqlSymbol.STRING_PLACEHOLDER)) {
            return String.format(template, replacement);
        }
        return template;
    }
}
