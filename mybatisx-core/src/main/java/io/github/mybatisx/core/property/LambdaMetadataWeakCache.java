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

import io.github.mybatisx.lambda.LambdaMetadata;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

/**
 * Lambda元数据缓存
 *
 * @author wvkity
 * @created 2022/1/9
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LambdaMetadataWeakCache {

    /**
     * Lambda元数据缓存
     */
    private static final Map<Class<?>, WeakReference<LambdaMetadata>> LAMBDA_METADATA_CACHE = new WeakHashMap<>();

    /**
     * 获取{@link LambdaMetadata}对象
     *
     * @param property {@link Property}
     * @param <T>      实体类型
     * @return {@link LambdaMetadata}
     */
    public static <T> LambdaMetadata get(final Property<T, ?> property) {
        final Class<?> clazz = property.getClass();
        return Optional.ofNullable(LAMBDA_METADATA_CACHE.get(clazz)).map(WeakReference::get).orElseGet(() -> {
            final LambdaMetadata it = LambdaMetadataFactory.get(property);
            final WeakReference<LambdaMetadata> ref = new WeakReference<>(it);
            return Objects.ifNonNull(LAMBDA_METADATA_CACHE.putIfAbsent(clazz, ref), ref).get();
        });
    }

    /**
     * 获取属性名
     *
     * @param property {@link Property}
     * @param <T>      实体类型
     * @return 属性名
     */
    public static <T> String getProperty(final Property<T, ?> property) {
        return getProperty(Optional.ofNullable(get(property)).map(LambdaMetadata::getImplMethodName).orElse(null));
    }

    /**
     * 获取属性名
     *
     * @param methodName Get/Set方法名
     * @return 属性名
     */
    public static String getProperty(final String methodName) {
        return Optional.ofNullable(methodName).filter(Strings::isNotWhitespace)
                .map(PropertyNamer::methodToProperty).orElse(null);
    }

}
