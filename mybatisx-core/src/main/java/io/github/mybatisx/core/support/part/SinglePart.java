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
package io.github.mybatisx.core.support.part;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.ParamMode;
import io.github.mybatisx.base.convert.ParameterConverter;
import io.github.mybatisx.base.convert.PlaceholderConverter;
import io.github.mybatisx.base.part.Part;
import io.github.mybatisx.core.param.TemplateParam;
import io.github.mybatisx.lang.Strings;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

/**
 * 单值类型模板SQL片段
 *
 * @author wvkity
 * @created 2022/4/27
 * @since 1.0.0
 */
@Builder
@RequiredArgsConstructor
public class SinglePart implements Part {

    private static final long serialVersionUID = -396324912893774306L;

    /**
     * 模板参数
     */
    protected final TemplateParam param;

    @Override
    public String getFragment(ParameterConverter pc, PlaceholderConverter phc) {
        return this.param == null ? Constants.EMPTY : this.param.parse(pc, phc, null);
    }

    /**
     * 创建{@link SinglePart}对象
     *
     * @param template 模板
     * @param value    值
     * @return {@link SinglePart}
     */
    public static SinglePart of(final String template, final Object value) {
        if (Strings.isNotWhitespace(template) && value != null) {
            return new SinglePart(TemplateParam.builder().template(template).value(value).paramMode(ParamMode.SINGLE).build());
        }
        return null;
    }
}
