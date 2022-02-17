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
package io.github.mybatisx.core.fragment;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.convert.ParameterConverter;
import io.github.mybatisx.base.convert.PlaceholderConverter;
import io.github.mybatisx.base.fragment.AbstractFragmentList;
import io.github.mybatisx.base.helper.SqlHelper;
import io.github.mybatisx.core.support.having.Having;
import io.github.mybatisx.lang.Strings;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

/**
 * 分组筛选片段存储
 *
 * @author wvkity
 * @created 2022/1/29
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class HavingStorage extends AbstractFragmentList<Having> {

    private static final long serialVersionUID = 8337702498350803152L;

    /**
     * 参数转换器
     */
    private final ParameterConverter parameterConverter;
    /**
     * 占位符参数转换器
     */
    private final PlaceholderConverter placeholderConverter;

    @Override
    public String getFragment() {
        if (!this.isEmpty()) {
            final String fragment = this.fragments.stream()
                    .map(it -> it.getFragment(parameterConverter, placeholderConverter))
                    .filter(Strings::isNotWhitespace)
                    .collect(Collectors.joining(SqlSymbol.SPACE));
            if (Strings.isNotWhitespace(fragment)) {
                return SqlSymbol.HAVING + SqlSymbol.SPACE + SqlHelper.startWithsAndOrRemove(fragment);
            }
        }
        return Constants.EMPTY;
    }
}
