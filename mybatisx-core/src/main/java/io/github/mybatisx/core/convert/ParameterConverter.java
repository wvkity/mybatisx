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
package io.github.mybatisx.core.convert;

import io.github.mybatisx.base.convert.Converter;

import java.util.Arrays;
import java.util.Map;

/**
 * 参数转换器
 *
 * @author wvkity
 * @created 2022/1/6
 * @since 1.0.0
 */
public interface ParameterConverter extends Converter<Object, String> {

    String PARAM_PLACEHOLDER_ZERO = "{0}";

    @Override
    default String convert(final Object src) {
        return this.convert(PARAM_PLACEHOLDER_ZERO, src);
    }

    /**
     * 参数值转占位符参数
     *
     * @param args 参数列表
     * @return 占位符参数
     */
    default String convert(final Object... args) {
        return this.convert(PARAM_PLACEHOLDER_ZERO, args);
    }

    /**
     * 参数值转占位符参数
     *
     * @param template 模板
     * @param args     参数列表
     * @return 占位符参数
     */
    String convert(final String template, final Object... args);

    /**
     * 参数值转占位符参数
     *
     * @param args 参数列表
     * @return 占位符参数列表
     */
    default String[] converts(final Object... args) {
        return this.converts(PARAM_PLACEHOLDER_ZERO, args);
    }

    /**
     * 参数值转占位符参数
     *
     * @param template 模板
     * @param args     参数列表
     * @return 占位符参数列表
     */
    default String[] converts(final String template, final Object... args) {
        return this.converts(template, Arrays.asList(args));
    }

    /**
     * 参数值转占位符参数
     *
     * @param iterable 参数列表
     * @return 占位符参数列表
     */
    default String[] converts(final Iterable<?> iterable) {
        return this.converts(PARAM_PLACEHOLDER_ZERO, iterable);
    }

    /**
     * 参数值转占位符参数
     *
     * @param template 模板
     * @param iterable 参数列表
     * @return 占位符参数列表
     */
    String[] converts(final String template, final Iterable<?> iterable);

    /**
     * 参数值转占位符参数
     *
     * @param args 参数集合
     * @return 占位符参数集合
     */
    default Map<String, String> converts(final Map<String, ?> args) {
        return this.converts(PARAM_PLACEHOLDER_ZERO, args);
    }

    /**
     * 参数值转占位符参数
     *
     * @param template 模板
     * @param args     参数集合
     * @return 占位符参数集合
     */
    Map<String, String> converts(final String template, final Map<String, ?> args);

}
