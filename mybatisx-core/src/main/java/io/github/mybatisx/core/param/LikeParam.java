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
package io.github.mybatisx.core.param;

import io.github.mybatisx.base.constant.MatchMode;
import io.github.mybatisx.base.constant.ParamMode;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.dialect.Dialect;
import io.github.mybatisx.core.convert.ParameterConverter;
import io.github.mybatisx.core.convert.PlaceholderConverter;
import io.github.mybatisx.core.mapping.Scripts;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Like参数
 *
 * @author wvkity
 * @created 2022/1/10
 * @since 1.0.0
 */
@Getter
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
@ToString(callSuper = true)
public class LikeParam extends AbstractParam implements Param {

    /**
     * 值
     */
    private String value;
    /**
     * 匹配模式
     */
    private MatchMode matchMode;
    /**
     * 转义字符
     */
    private Character escape;
    /**
     * 是否忽略大小写
     */
    private boolean ignoreCase;
    /**
     * 方言
     */
    private Dialect dialect;

    @Override
    public ParamMode getParamMode() {
        return ParamMode.SINGLE;
    }

    @Override
    public String parse(ParameterConverter pc, PlaceholderConverter phc) {
        return this.toConditionArg(pc.convert((matchMode == null ? MatchMode.EXACT : matchMode).convert(this.value)));
    }

    @Override
    protected String toConditionArg(String... placeholders) {
        final Dialect _$dialect;
        final StringBuilder sb = new StringBuilder(120);
        if (this.ignoreCase && (_$dialect = this.dialect) != null) {
            if (_$dialect.supportsCaseInsensitiveLike()) {
                this.symbol = this.symbol == Symbol.LIKE ? Symbol.ILIKE : Symbol.NOT_ILIKE;
                sb.append(super.toConditionArg(placeholders));
            } else {
                // 使用小写函数忽略大小写比较
                if (this.slot != null) {
                    sb.append(this.slot.getFragment()).append(SqlSymbol.SPACE);
                }
                sb.append(_$dialect.getLowercaseFunction()).append(SqlSymbol.START_BRACKET).append("%s")
                        .append(SqlSymbol.END_BRACKET).append(SqlSymbol.SPACE);
                sb.append(Scripts.toConditionValueArg(this.symbol, this.typeHandler, this.jdbcType, this.spliceJavaType,
                        this.javaType, placeholders));
            }
        } else {
            sb.append(super.toConditionArg(placeholders));
        }
        if (this.escape != null && !Character.isWhitespace(this.escape)) {
            sb.append(" ESCAPE ").append(SqlSymbol.SINGLE_QUOTES).append(this.escape).append(SqlSymbol.SINGLE_QUOTES);
        }
        return sb.toString();
    }
}
