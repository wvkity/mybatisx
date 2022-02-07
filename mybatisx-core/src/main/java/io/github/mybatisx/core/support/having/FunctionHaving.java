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
package io.github.mybatisx.core.support.having;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.convert.ParameterConverter;
import io.github.mybatisx.base.convert.PlaceholderConverter;
import io.github.mybatisx.core.criteria.query.Query;
import io.github.mybatisx.core.param.Param;
import io.github.mybatisx.core.support.function.AggFunction;
import io.github.mybatisx.lang.Strings;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

/**
 * @author wvkity
 * @created 2022/1/30
 * @since 1.0.0
 */
@Builder
@RequiredArgsConstructor
public class FunctionHaving implements Having {

    private static final long serialVersionUID = 9041372708644691626L;

    /**
     * 聚合函数
     */
    private final AggFunction function;
    /**
     * 参数
     */
    private final Param param;

    @Override
    public String getFragment(ParameterConverter pc, PlaceholderConverter phc) {
        final Query<?> query = function.getQuery();
        final String template = this.param.parse(pc, phc, query == null ? null : query.getDialect());
        if (Strings.isNotWhitespace(template)) {
            if (template.contains(SqlSymbol.STRING_PLACEHOLDER)) {
                return String.format(template, function.getFragment(false));
            }
            return template;
        }
        return Constants.EMPTY;
    }

}
