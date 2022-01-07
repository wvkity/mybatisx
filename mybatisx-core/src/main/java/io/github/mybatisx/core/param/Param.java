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
import io.github.mybatisx.core.convert.ParameterConverter;
import io.github.mybatisx.core.convert.PlaceholderConverter;

/**
 * 参数接口
 *
 * @author wvkity
 * @created 2022/1/7
 * @since 1.0.0
 */
public interface Param {

    /**
     * 获取参数值
     *
     * @return 参数值
     */
    default Object getValue() {
        return null;
    }

    /**
     * 获取条件符号
     *
     * @return {@link Symbol}
     */
    Symbol getSymbol();

    /**
     * 获取逻辑符号
     *
     * @return {@link LogicSymbol}
     */
    LogicSymbol getSlot();

    /**
     * 获取参数模式
     *
     * @return {@link ParamMode}
     */
    ParamMode getParamMode();

    /**
     * 解析参数成占位符参数
     *
     * @param pc  {@link ParameterConverter}
     * @param phc {@link PlaceholderConverter}
     * @return 占位符参数
     */
    String parse(final ParameterConverter pc, final PlaceholderConverter phc);

}
