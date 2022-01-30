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
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.convert.ParameterConverter;
import io.github.mybatisx.base.convert.PlaceholderConverter;
import io.github.mybatisx.base.dialect.Dialect;
import io.github.mybatisx.base.helper.PlaceholderHelper;
import io.github.mybatisx.lang.Strings;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.util.Collection;
import java.util.Map;

/**
 * 模板参数
 *
 * @author wvkity
 * @created 2022/1/10
 * @since 1.0.0
 */
@Getter
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class TemplateParam extends AbstractParam implements Param {

    /**
     * 参数模式
     */
    private final ParamMode paramMode;
    /**
     * 模板
     */
    private final String template;
    /**
     * 单个值
     */
    private final Object value;
    /**
     * 多个值
     */
    private final Collection<?> listValue;
    /**
     * 键值
     */
    private final Map<String, ?> mapValue;

    public TemplateParam(Symbol symbol, LogicSymbol slot, Class<? extends TypeHandler<?>> typeHandler, 
                         JdbcType jdbcType, Class<?> javaType, boolean spliceJavaType, ParamMode paramMode, 
                         String template, Object value, Collection<?> listValue, Map<String, ?> mapValue) {
        super(symbol, slot, typeHandler, jdbcType, javaType, spliceJavaType);
        this.paramMode = paramMode;
        this.template = template;
        this.value = value;
        this.listValue = listValue;
        this.mapValue = mapValue;
    }

    @Override
    public String parse(ParameterConverter pc, PlaceholderConverter phc, Dialect dialect) {
        if (Strings.isNotWhitespace(this.template)) {
            final PlaceholderHelper helper = new PlaceholderHelper();
            final String _$template = this.template;
            final String result;
            switch (paramMode) {
                case MAP:
                    result = helper.replace(_$template, phc.convert(this.mapValue));
                    break;
                case MULTIPLE:
                    if (PlaceholderHelper.isOnlyOnce(_$template)) {
                        result = helper.replace(_$template, phc.convert(this.listValue, this.typeHandler,
                                this.jdbcType, this.javaType, this.spliceJavaType));
                    } else {
                        result = helper.replace(_$template, phc.convert(this.listValue));
                    }
                    break;
                default:
                    result = helper.replace(_$template, super.toConditionArg(dialect, pc.convert(this.value)));
            }
            // 检查是否已替换，没替换则不通过
            if (Strings.isNotWhitespace(result) && !_$template.equals(result)) {
                final LogicSymbol _$slot = this.slot;
                if (_$slot == null || _$slot == LogicSymbol.NONE) {
                    return result;
                }
                return _$slot.getFragment() + SqlSymbol.SPACE + result;
            }
        }
        return null;
    }

}
