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
package io.github.mybatisx.reflect;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.ToString;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 字段包装类
 *
 * @author wvkity
 * @created 2021/12/27
 * @since 1.0.0
 */
@Getter
@ToString
public class FieldWrapper implements Annotated {
    /**
     * 实体类
     */
    private final Class<?> type;
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
    /**
     * 注解集合
     */
    private final Set<? extends Annotation> annotations;
    /**
     * 注解集合
     */
    private final Map<String, ? extends Annotation> annotationMap;

    public FieldWrapper(Class<?> type, Field field, String name, Class<?> javaType, Method getter, Method setter) {
        this.type = type;
        this.field = field;
        this.name = name;
        this.javaType = javaType;
        this.getter = getter;
        this.setter = setter;
        this.annotations = ImmutableSet.copyOf(Reflections.getAllAnnotations(field,
                Reflections.METADATA_ANNOTATION_FILTER));
        if (this.annotations.isEmpty()) {
            this.annotationMap = ImmutableMap.of();
        } else {
            this.annotationMap = ImmutableMap.copyOf(this.annotations.stream()
                    .collect(Collectors.toMap(it -> it.annotationType().getCanonicalName(), it -> it)));
        }

    }

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

}
