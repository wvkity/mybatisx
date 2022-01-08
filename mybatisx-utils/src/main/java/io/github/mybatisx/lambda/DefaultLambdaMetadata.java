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
package io.github.mybatisx.lambda;

import io.github.mybatisx.reflect.Reflections;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;

/**
 * 默认Lambda对象元数据
 *
 * @author wvkity
 * @created 2022/1/8
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class DefaultLambdaMetadata implements LambdaMetadata {

    /**
     * capturingClass属性
     */
    private static final Field FIELD_CAPTURING_CLASS;
    private final java.lang.invoke.SerializedLambda serializedLambda;

    static {
        Field capturingClassField;
        try {
            capturingClassField = java.lang.invoke.SerializedLambda.class.getDeclaredField("capturingClass");
            capturingClassField.setAccessible(true);
        } catch (Throwable ignore) {
            capturingClassField = null;
        }
        FIELD_CAPTURING_CLASS = capturingClassField;
    }

    @Override
    public String getImplMethodName() {
        return this.serializedLambda.getImplMethodName();
    }

    @Override
    public Class<?> getImplClass() {
        final String implMethodType = this.serializedLambda.getInstantiatedMethodType();
        final String implType = implMethodType.substring(2, implMethodType.indexOf(";")).replace("/", ".");
        try {
            return Reflections.loadClass(implType, this.getClassLoader(),
                    DefaultLambdaMetadata.class.getClassLoader(),
                    Thread.currentThread().getContextClassLoader(),
                    ClassLoader.getSystemClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 获取类加载器
     *
     * @return {@link ClassLoader}
     */
    private ClassLoader getClassLoader() {
        if (FIELD_CAPTURING_CLASS == null) {
            return null;
        }
        try {
            return ((Class<?>) FIELD_CAPTURING_CLASS.get(this.serializedLambda)).getClassLoader();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
