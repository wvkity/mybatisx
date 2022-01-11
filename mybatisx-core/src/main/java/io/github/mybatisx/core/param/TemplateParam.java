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
import io.github.mybatisx.base.helper.PlaceholderHelper;
import io.github.mybatisx.core.convert.ParameterConverter;
import io.github.mybatisx.core.convert.PlaceholderConverter;
import io.github.mybatisx.lang.Strings;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

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
@RequiredArgsConstructor
@ToString(callSuper = true)
public class TemplateParam extends AbstractParam implements Param {

    /**
     * 参数模式
     */
    private ParamMode paramMode;
    /**
     * 模板
     */
    private String template;
    /**
     * 单个值
     */
    private Object value;
    /**
     * 多个值
     */
    private Collection<?> listValue;
    /**
     * 键值
     */
    private Map<String, ?> mapValue;

    @Override
    public String parse(ParameterConverter pc, PlaceholderConverter phc) {
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
                    result = helper.replace(_$template, super.toConditionArg(pc.convert(this.value)));
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
