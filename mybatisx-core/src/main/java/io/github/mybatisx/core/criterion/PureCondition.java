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
import io.github.mybatisx.lang.Strings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

/**
 * 纯SQL条件
 *
 * @author wvkity
 * @created 2022/2/22
 * @since 1.0.0
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PureCondition implements Criterion {

    private static final long serialVersionUID = -4848439982354733631L;
    /**
     * 条件
     */
    private final String condition;
    /**
     * 逻辑符号
     */
    private final LogicSymbol slot;
    /**
     * 逻辑符号正则表达式
     */
    private final Pattern slotStartWithRegex = Pattern.compile("(?i)^\\s*(and|or)?\\s+(.*)$");

    @Override
    public String getFragment(ParameterConverter pc, PlaceholderConverter phc) {
        return this.getFragment();
    }

    @Override
    public String getFragment() {
        final String _$condition = this.condition;
        if (Strings.isNotWhitespace(_$condition)) {
            if (!this.slotStartWithRegex.matcher(_$condition).matches() && this.slot != null
                    && this.slot != LogicSymbol.NONE) {
                return this.slot.getFragment() + SqlSymbol.SPACE + _$condition;
            }
            return _$condition;
        }
        return Constants.EMPTY;
    }

    /**
     * 创建{@link PureCondition}对象
     *
     * @param condition 条件
     * @param slot      {@link LogicSymbol}
     * @return {@link PureCondition}
     */
    public static PureCondition of(final String condition, final LogicSymbol slot) {
        if (Strings.isNotWhitespace(condition)) {
            return new PureCondition(condition, slot);
        }
        return null;
    }

}
