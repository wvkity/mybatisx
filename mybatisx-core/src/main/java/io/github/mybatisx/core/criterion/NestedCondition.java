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
import io.github.mybatisx.base.convert.ParameterConverter;
import io.github.mybatisx.base.convert.PlaceholderConverter;
import io.github.mybatisx.base.criterion.Criterion;
import io.github.mybatisx.base.helper.SqlHelper;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.util.Collections;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 嵌套条件
 *
 * @author wvkity
 * @created 2022/1/11
 * @since 1.0.0
 */
@Builder
@RequiredArgsConstructor
public class NestedCondition implements Criterion {

    private static final long serialVersionUID = 7317729939000346083L;

    /**
     * 是否拼接not
     */
    private final boolean not;
    /**
     * 逻辑符号
     */
    private final LogicSymbol slot;
    /**
     * 条件列表
     */
    private final List<Criterion> conditions;

    @Override
    public String getFragment() {
        return Constants.EMPTY;
    }

    @Override
    public String getFragment(ParameterConverter pc, PlaceholderConverter phc) {
        if (Collections.isNotEmpty(this.conditions)) {
            final String fragment = this.conditions.stream().map(it -> it.getFragment(pc, phc))
                    .filter(Strings::isNotWhitespace).collect(Collectors.joining(SqlSymbol.SPACE));
            if (Strings.isNotWhitespace(fragment)) {
                final StringBuilder sb = new StringBuilder(120);
                if (this.slot != null) {
                    sb.append(this.slot.getFragment());
                }
                if (this.not) {
                    sb.append(SqlSymbol.SPACE).append("NOT");
                }
                sb.append(SqlSymbol.SPACE).append(SqlSymbol.START_BRACKET)
                        .append(SqlHelper.startWithsAndOrRemove(fragment).trim()).append(SqlSymbol.END_BRACKET);
                return sb.toString();
            }
        }
        return Constants.EMPTY;
    }
}
