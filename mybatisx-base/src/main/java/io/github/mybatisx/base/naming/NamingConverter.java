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
package io.github.mybatisx.base.naming;

import io.github.mybatisx.annotation.NamingStrategy;
import io.github.mybatisx.base.convert.Converter;

/**
 * 命名转换器
 *
 * @author wvkity
 * @created 2021/12/28
 * @since 1.0.0
 */
public interface NamingConverter extends Converter<String, String> {

    /**
     * 获取原命名策略
     *
     * @return 原命名策略
     */
    NamingStrategy getSourceStrategy();

    /**
     * 获取目标命名策略
     *
     * @return 目标命名策略
     */
    NamingStrategy getTargetStrategy();

    /**
     * 根据命名策略转换对应字符串
     *
     * @param src  源字符串
     * @param from 原策略
     * @param to   目标策略
     * @return 新的字符串
     */
    String convert(final String src, final NamingStrategy from, final NamingStrategy to);

    @Override
    default String convert(final String src) {
        return this.convert(src, this.getSourceStrategy(), this.getTargetStrategy());
    }
    
}
