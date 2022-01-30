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

import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.base.constant.ParamMode;
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.convert.ParameterConverter;
import io.github.mybatisx.base.convert.PlaceholderConverter;
import io.github.mybatisx.base.dialect.Dialect;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * null参数
 *
 * @author wvkity
 * @created 2022/1/14
 * @since 1.0.0
 */
@Getter
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class NullParam extends AbstractParam implements Param {
    
    public NullParam(Symbol symbol, LogicSymbol slot, Class<? extends TypeHandler<?>> typeHandler, 
                     JdbcType jdbcType, Class<?> javaType, boolean spliceJavaType) {
        super(symbol, slot, typeHandler, jdbcType, javaType, spliceJavaType);
    }

    @Override
    public ParamMode getParamMode() {
        return ParamMode.SINGLE;
    }

    @Override
    public String parse(ParameterConverter pc, PlaceholderConverter phc, Dialect dialect) {
        final StringBuilder sb = new StringBuilder(30);
        if (slot != null) {
            sb.append(slot.getFragment());
        }
        sb.append(" %s ").append(symbol.getFragment());
        return sb.toString();
    }
}
