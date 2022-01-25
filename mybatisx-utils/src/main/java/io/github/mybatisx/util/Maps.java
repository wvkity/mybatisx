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
package io.github.mybatisx.util;

import io.github.mybatisx.lang.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Map工具
 *
 * @author wvkity
 * @created 2022/1/11
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Maps {

    /**
     * 创建有序{@link Map}
     *
     * @param k1  键1
     * @param v1  值1
     * @param <K> 键类型
     * @param <V> 值类型
     * @return {@link Map}
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1) {
        final Map<K, V> map = new LinkedHashMap<>();
        map.put(k1, v1);
        return map;
    }

    /**
     * 创建有序{@link Map}
     *
     * @param k1  键1
     * @param v1  值1
     * @param k2  键2
     * @param v2  值1
     * @param <K> 键类型
     * @param <V> 值类型
     * @return {@link Map}
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2) {
        return ofArray(k1, v1, k2, v2);
    }

    /**
     * 创建有序{@link Map}
     *
     * @param k1  键1
     * @param v1  值1
     * @param k2  键2
     * @param v2  值1
     * @param k3  键3
     * @param v3  值3
     * @param <K> 键类型
     * @param <V> 值类型
     * @return {@link Map}
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3) {
        return ofArray(k1, v1, k2, v2, k3, v3);
    }

    /**
     * 创建有序{@link Map}
     *
     * @param k1  键1
     * @param v1  值1
     * @param k2  键2
     * @param v2  值1
     * @param k3  键3
     * @param v3  值3
     * @param k4  键4
     * @param v4  值4
     * @param <K> 键类型
     * @param <V> 值类型
     * @return {@link Map}
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3,
                                      final K k4, final V v4) {
        return ofArray(k1, v1, k2, v2, k3, v3, k4, v4);
    }

    /**
     * 创建有序{@link Map}
     *
     * @param k1  键1
     * @param v1  值1
     * @param k2  键2
     * @param v2  值1
     * @param k3  键3
     * @param v3  值3
     * @param k4  键4
     * @param v4  值4
     * @param k5  键5
     * @param v5  值5
     * @param <K> 键类型
     * @param <V> 值类型
     * @return {@link Map}
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3,
                                      final K k4, final V v4, final K k5, final V v5) {
        return ofArray(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
    }

    /**
     * 创建有序{@link Map}
     *
     * @param k1  键1
     * @param v1  值1
     * @param k2  键2
     * @param v2  值1
     * @param k3  键3
     * @param v3  值3
     * @param k4  键4
     * @param v4  值4
     * @param k5  键5
     * @param v5  值5
     * @param k6  键6
     * @param v6  值6
     * @param <K> 键类型
     * @param <V> 值类型
     * @return {@link Map}
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3,
                                      final K k4, final V v4, final K k5, final V v5, final K k6, final V v6) {
        return ofArray(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6);
    }

    /**
     * 创建有序{@link Map}
     *
     * @param k1  键1
     * @param v1  值1
     * @param k2  键2
     * @param v2  值1
     * @param k3  键3
     * @param v3  值3
     * @param k4  键4
     * @param v4  值4
     * @param k5  键5
     * @param v5  值5
     * @param k6  键6
     * @param v6  值6
     * @param k7  键7
     * @param v7  值7
     * @param <K> 键类型
     * @param <V> 值类型
     * @return {@link Map}
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3,
                                      final K k4, final V v4, final K k5, final V v5, final K k6, final V v6,
                                      final K k7, final V v7) {
        return ofArray(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7);
    }

    /**
     * 创建有序{@link Map}
     *
     * @param k1  键1
     * @param v1  值1
     * @param k2  键2
     * @param v2  值1
     * @param k3  键3
     * @param v3  值3
     * @param k4  键4
     * @param v4  值4
     * @param k5  键5
     * @param v5  值5
     * @param k6  键6
     * @param v6  值6
     * @param k7  键7
     * @param v7  值7
     * @param k8  键8
     * @param v8  值8
     * @param <K> 键类型
     * @param <V> 值类型
     * @return {@link Map}
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3,
                                      final K k4, final V v4, final K k5, final V v5, final K k6, final V v6,
                                      final K k7, final V v7, final K k8, final V v8) {
        return ofArray(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8);
    }

    /**
     * 创建有序{@link Map}
     *
     * @param k1  键1
     * @param v1  值1
     * @param k2  键2
     * @param v2  值1
     * @param k3  键3
     * @param v3  值3
     * @param k4  键4
     * @param v4  值4
     * @param k5  键5
     * @param v5  值5
     * @param k6  键6
     * @param v6  值6
     * @param k7  键7
     * @param v7  值7
     * @param k8  键8
     * @param v8  值8
     * @param <K> 键类型
     * @param <V> 值类型
     * @return {@link Map}
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3,
                                      final K k4, final V v4, final K k5, final V v5, final K k6, final V v6,
                                      final K k7, final V v7, final K k8, final V v8, final Object... args) {
        final List<Object> list = Objects.objectAsList(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8);
        list.addAll(Arrays.asList(args));
        return ofList(list);
    }

    /**
     * 根据参数列表创建{@link Map}
     *
     * @param args 参数列表
     * @param <K>  键类型
     * @param <V>  值类型
     * @return {@link Map}
     */
    public static <K, V> Map<K, V> ofList(final List<?> args) {
        return ofArray(args.toArray());
    }

    /**
     * 根据参数列表创建{@link Map}
     *
     * @param args 参数列表
     * @param <K>  键类型
     * @param <V>  值类型
     * @return {@link Map}
     */
    @SuppressWarnings({"unchecked"})
    public static <K, V> Map<K, V> ofArray(final Object... args) {
        final int length = args.length;
        if (length == 0) {
            throw new IllegalArgumentException("The parameter cannot be empty");
        }
        if ((length & 1) != 0) {
            throw new IllegalArgumentException("The parameter length must be even");
        }
        final Set<Object> set = new HashSet<>();
        for (int i = 0; i < length; i += 2) {
            set.add(args[i]);
        }
        final int size = set.size();
        final Map<K, V> map = new LinkedHashMap<>(size);
        for (int i = 0; i < length; i += 2) {
            final K k = (K) args[i];
            final V v = (V) args[i + 1];
            map.put(k, v);
        }
        return map;
    }
}
