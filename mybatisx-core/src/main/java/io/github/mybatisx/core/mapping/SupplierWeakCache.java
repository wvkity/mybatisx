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
package io.github.mybatisx.core.mapping;

import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.reflect.Reflections;
import io.github.mybatisx.support.mapping.SqlSupplier;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * {@link SqlSupplier}缓存
 *
 * @author wvkity
 * @created 2022/1/2
 * @since 1.0.0
 */
public final class SupplierWeakCache {

    /**
     * {@link SqlSupplier}缓存
     */
    private static final Map<String, Class<? extends SqlSupplier>> SUPPLIER_CACHE =
            Collections.synchronizedMap(new WeakHashMap<>());

    /**
     * 获取SQL供应器类
     *
     * @param clazz 目标类
     * @return SQL供应器类
     */
    @SuppressWarnings({"unchecked"})
    public static Class<? extends SqlSupplier> get(final Class<?> clazz) {
        if (Objects.nonNull(clazz)) {
            final String key = clazz.getName();
            Class<? extends SqlSupplier> it = SUPPLIER_CACHE.get(key);
            if (Objects.isNull(it)) {
                final Class<?> target = Reflections.getGenericClass(clazz, 0);
                if (Objects.isAssignable(SqlSupplier.class, target)) {
                    it = (Class<? extends SqlSupplier>) target;
                    final Class<? extends SqlSupplier> ov = SUPPLIER_CACHE.putIfAbsent(key, it);
                    return Objects.ifNull(ov, it);
                }
            }
            return it;
        }
        return null;
    }

    /**
     * 创建{@link SqlSupplier}对象
     *
     * @param clazz 指定类
     * @param args  参数列表
     * @return {@link SqlSupplier}对象
     */
    public static SqlSupplier newInstance(final Class<?> clazz, final Object... args) {
        final Class<? extends SqlSupplier> target = get(clazz);
        if (Objects.nonNull(target)) {
            try {
                return Reflections.newInstance(target, args);
            } catch (Exception ignore) {
                // ignore
            }
        }
        return null;
    }

}
