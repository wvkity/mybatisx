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
package io.github.mybatisx.lang;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 基本数据类型枚举类
 *
 * @author wvkity
 * @created 2022/1/7
 * @since 1.0.0
 */
public enum PrimitiveType {

    BOOLEAN(boolean.class),
    BYTE(byte.class),
    CHAR(char.class),
    SHORT(short.class),
    INTEGER(int.class),
    FLOAT(float.class),
    DOUBLE(double.class),
    LONG(long.class);
    final Class<?> type;

    PrimitiveType(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

    static final Map<Class<?>, PrimitiveType> CACHE = Arrays.stream(PrimitiveType.values())
            .collect(Collectors.toMap(PrimitiveType::getType, Function.identity()));

    public static PrimitiveType getType(final Class<?> type) {
        if (type != null) {
            return CACHE.get(type);
        }
        return null;
    }

}
