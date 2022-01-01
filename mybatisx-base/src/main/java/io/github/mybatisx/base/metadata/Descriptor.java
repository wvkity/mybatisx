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
package io.github.mybatisx.base.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 字段信息
 *
 * @author wvkity
 * @created 2021/12/31
 * @since 1.0.0
 */
@Getter
@ToString
@EqualsAndHashCode
public class Descriptor {

    /**
     * 所属类
     */
    @EqualsAndHashCode.Include
    private final Class<?> type;
    /**
     * 属性名称
     */
    @EqualsAndHashCode.Include
    private final String name;
    /**
     * 属性信息
     */
    @EqualsAndHashCode.Include
    private final Field field;
    /**
     * JAVA类型
     */
    @EqualsAndHashCode.Include
    private final Class<?> javaType;
    /**
     * get方法
     */
    private final Method readable;
    /**
     * set方法
     */
    private final Method writable;

    public Descriptor(Class<?> type, String name, Field field, Class<?> javaType, Method readable, Method writable) {
        this.type = type;
        this.name = name;
        this.field = field;
        this.javaType = javaType;
        this.readable = readable;
        this.writable = writable;
    }
}
