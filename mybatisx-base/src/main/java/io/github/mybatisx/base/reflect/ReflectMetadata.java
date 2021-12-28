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

import com.google.common.collect.ImmutableMap;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Types;
import io.github.mybatisx.reflect.Reflections;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 注解元数据(反射获取)
 *
 * @author wvkity
 * @created 2021/12/27
 * @since 1.0.0
 */
public class ReflectMetadata implements Metadata {

    /**
     * 注解实例
     */
    private final Annotation instance;
    /**
     * 元数据
     */
    private final Map<String, Object> data;

    public ReflectMetadata(Annotation instance) {
        this.instance = instance;
        final Map<String, Object> result = Reflections.annotationToMap(instance);
        if (result.isEmpty()) {
            this.data = ImmutableMap.of();
        } else {
            this.data = ImmutableMap.copyOf(result);
        }
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <T extends Annotation> T getAnnotation() {
        return (T) this.instance;
    }

    @Override
    public Map<String, Object> getData() {
        return this.data;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <T> T getValue(String property, Class<T> clazz, T defaultValue) {
        if (Objects.nonNull(clazz) && this.data.containsKey(property)) {
            final Object value = this.data.get(property);
            if (Objects.nonNull(value) && Types.is(clazz, value.getClass())) {
                return (T) value;
            }
        }
        return defaultValue;
    }


    ///// static methods /////

    public static ReflectMetadata of(final Annotation annotation) {
        return new ReflectMetadata(annotation);
    }

}
