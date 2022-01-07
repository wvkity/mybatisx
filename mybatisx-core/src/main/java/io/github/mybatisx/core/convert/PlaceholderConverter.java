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
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.util.List;
import java.util.Map;

/**
 * 占位符转换器
 *
 * @author wvkity
 * @created 2022/1/6
 * @since 1.0.0
 */
public interface PlaceholderConverter extends Converter<Object, Object> {

    /**
     * 参数列表转占位符参数
     *
     * @param iterable 参数列表
     * @return 占位符参数
     */
    List<?> convert(final Iterable<?> iterable);

    /**
     * 参数列表转占位符参数
     *
     * @param iterable 参数列表
     * @return 占位符参数
     */
    List<?> convert(final Iterable<?> iterable, final Class<? extends TypeHandler<?>> typeHandler,
                     final JdbcType jdbcType, final Class<?> javaType, final boolean spliceJavaType);

    /**
     * 参数集合转占位符参数
     *
     * @param args 参数集合
     * @return 占位符参数集合
     */
    Map<String, ?> convert(final Map<String, ?> args);
    
}
