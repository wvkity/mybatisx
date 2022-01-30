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

import io.github.mybatisx.base.convert.ParameterConverter;
import io.github.mybatisx.base.convert.PlaceholderConverter;
import io.github.mybatisx.core.mapping.Scripts;
import io.github.mybatisx.lang.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 默认占位符转换器
 *
 * @author wvkity
 * @created 2022/1/6
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class DefaultPlaceholderConverter implements PlaceholderConverter {

    /**
     * 参数转换
     */
    private final ParameterConverter converter;

    @Override
    @SuppressWarnings({"unchecked"})
    public Object convert(Object src) {
        if (src != null) {
            final Class<?> clazz = src.getClass();
            if (Objects.isAssignable(Iterable.class, clazz)) {
                return this.convert((Iterable<?>) src);
            } else if (Objects.isAssignable(Map.class, clazz)) {
                return this.convert((Map<String, ?>) src);
            } else if (clazz.isArray()) {
                return this.convert(Arrays.asList((Object[]) src));
            }
            return Scripts.safeJoining(this.converter.convert(src));
        }
        return "null";
    }

    @Override
    public List<?> convert(Iterable<?> iterable) {
        if (iterable != null) {
            final Stream<?> stream = StreamSupport.stream(iterable.spliterator(), false);
            if (Objects.isPureType(iterable)) {
                return stream.map(it -> Scripts.safeJoining(this.converter.convert(it))).collect(Collectors.toList());
            }
            return stream.map(this::convert).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<?> convert(Iterable<?> iterable, Class<? extends TypeHandler<?>> typeHandler,
                           JdbcType jdbcType, Class<?> javaType, boolean spliceJavaType) {
        if (iterable != null) {
            final Stream<?> stream = StreamSupport.stream(iterable.spliterator(), false);
            if (Objects.isPureType(iterable)) {
                return stream.map(it -> Scripts.safeJoining(this.converter.convert(it),
                                Scripts.concatTypeArg(typeHandler, jdbcType, spliceJavaType, javaType)))
                        .collect(Collectors.toList());
            }
            return stream.map(this::convert).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Map<String, ?> convert(Map<String, ?> args) {
        if (Objects.isNotEmpty(args)) {
            return args.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, 
                    it -> this.convert(it.getValue()), (o, n) -> n, LinkedHashMap::new));
        }
        return null;
    }
}
