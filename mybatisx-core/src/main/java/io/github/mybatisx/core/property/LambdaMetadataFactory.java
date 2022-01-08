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
package io.github.mybatisx.core.property;

import io.github.mybatisx.lambda.CustomLambdaMetadata;
import io.github.mybatisx.lambda.DefaultLambdaMetadata;
import io.github.mybatisx.lambda.LambdaMetadata;
import io.github.mybatisx.lambda.SerializedLambda;
import io.github.mybatisx.reflect.Reflections;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * Lambda元数据工厂
 *
 * @author wvkity
 * @created 2022/1/8
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LambdaMetadataFactory {

    /**
     * 获取{@link LambdaMetadata}对象
     *
     * @param property {@link Property}
     * @param <T>      实体类型
     * @return {@link LambdaMetadata}对象
     */
    public static <T> LambdaMetadata get(final Property<T, ?> property) {
        try {
            final Method method = property.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            return new DefaultLambdaMetadata((java.lang.invoke.SerializedLambda) method.invoke(property));
        } catch (Throwable ignore) {
            return new CustomLambdaMetadata(SerializedLambda.resolve(property));
        }
    }
}
