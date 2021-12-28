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
package io.github.mybatisx.base.reflect;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 字段包装类
 *
 * @author wvkity
 * @created 2021/12/27
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
public class FieldWrapper {
    /**
     * 属性信息
     */
    private final Field field;
    /**
     * JAVA类型
     */
    private final String name;
    /**
     * 属性名称
     */
    private final Class<?> javaType;
    /**
     * get方法
     */
    private final Method getter;
    /**
     * set方法
     */
    private final Method setter;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldWrapper that = (FieldWrapper) o;
        return Objects.equals(field, that.field) && Objects.equals(name, that.name)
                && Objects.equals(javaType, that.javaType) && Objects.equals(getter, that.getter)
                && Objects.equals(setter, that.setter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, name, javaType, getter, setter);
    }

    @Override
    public String toString() {
        return "FieldWrapper{" +
                "field=" + field +
                ", name='" + name + '\'' +
                ", javaType=" + javaType +
                ", getter=" + getter +
                ", setter=" + setter +
                '}';
    }
}
