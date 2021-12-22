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

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Objects工具
 *
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
public final class Objects {

    private Objects() {
    }

    /**
     * JAVA8正则表达式
     */
    public static final Pattern REGEX_JAVA_VERSION_8 = Pattern.compile("^1\\.8\\.(.*?)$");
    /**
     * 是否为JAVA_8
     */
    public static final boolean JAVA_VERSION_8;

    static {
        JAVA_VERSION_8 = REGEX_JAVA_VERSION_8.matcher(System.getProperty("java.version")).matches();
    }

    /**
     * 检查对象是否为null
     *
     * @param arg 待检查对象
     * @return boolean
     */
    public static boolean isNull(final Object arg) {
        return arg == null;
    }

    /**
     * 检查对象是否不为null
     *
     * @param arg 待检查对象
     * @return boolean
     */
    public static boolean nonNull(final Object arg) {
        return arg != null;
    }

    /**
     * 检查两个类是否为is关系
     *
     * @param source 源类
     * @param clazz  目标类
     * @return boolean
     */
    public static boolean isAssignable(final Class<?> source, final Class<?> clazz) {
        return Objects.nonNull(source) && Objects.nonNull(clazz) && source.isAssignableFrom(clazz);
    }

    /**
     * 检查目标对象是否为is关系
     *
     * @param source 源类
     * @param arg    目标对象
     * @return boolean
     */
    public static boolean isAssignable(final Class<?> source, final Object arg) {
        return Objects.nonNull(arg) && isAssignable(source, arg.getClass());
    }

    /**
     * 检查目标对象是否为{@link Object}类型
     *
     * @param arg 目标对象
     * @return boolean
     */
    public static boolean isObject(final Object arg) {
        return Types.isObject(arg.getClass());
    }

    /**
     * 检查目标对象是否为{@link Annotation}类型
     *
     * @param arg 目标对象
     * @return boolean
     */
    public static boolean isAnnotation(final Object arg) {
        return Objects.isAssignable(Annotation.class, arg);
    }

    /**
     * 检查对象是否为数组类型
     *
     * @param arg 待检查对象
     * @return boolean
     */
    public static boolean isArray(final Object arg) {
        return nonNull(arg) && arg.getClass().isArray();
    }

    /**
     * 检查对象是否为{@link Collection}类型
     *
     * @param arg 目标对象
     * @return boolean
     */
    public static boolean isCollection(final Object arg) {
        return isAssignable(Collection.class, arg);
    }

    /**
     * 检查对象是否为{@link Set}类型
     *
     * @param arg 目标对象
     * @return boolean
     */
    public static boolean isSet(final Object arg) {
        return isAssignable(Set.class, arg);
    }

    /**
     * 检查对象是否为{@link List}类型
     *
     * @param arg 目标对象
     * @return boolean
     */
    public static boolean isList(final Object arg) {
        return isAssignable(List.class, arg);
    }

    /**
     * 检查对象是否为{@link Map}类型
     *
     * @param arg 目标对象
     * @return boolean
     */
    public static boolean isMap(final Object arg) {
        return isAssignable(Map.class, arg);
    }

    /**
     * 检查数组对象是否为空
     *
     * @param arg 待检查数组对象
     * @return boolean
     */
    public static boolean isEmpty(final Object[] arg) {
        return isNull(arg) || arg.length == 0;
    }

    /**
     * 检查集合是否为空
     *
     * @param arg 待检查集合
     * @return boolean
     */
    public static boolean isEmpty(final Collection<?> arg) {
        return isNull(arg) || arg.isEmpty();
    }

    /**
     * 检查Map集合是否为空
     *
     * @param arg 待检查Map集合
     * @return boolean
     */
    public static boolean isEmpty(final Map<?, ?> arg) {
        return isNull(arg) || arg.isEmpty();
    }

    /**
     * 检查数组对象是否不为空
     *
     * @param arg 待检查数组对象
     * @return boolean
     */
    public static boolean isNotEmpty(final Object[] arg) {
        return !isEmpty(arg);
    }

    /**
     * 检查集合是否不为空
     *
     * @param arg 待检查集合
     * @return boolean
     */
    public static boolean isNotEmpty(final Collection<?> arg) {
        return !isEmpty(arg);
    }

    /**
     * 检查Map集合是否不为空
     *
     * @param arg 待检查Map集合
     * @return boolean
     */
    public static boolean isNotEmpty(final Map<?, ?> arg) {
        return !isEmpty(arg);
    }

    /**
     * 获取数组长度
     *
     * @param arg 数组对象
     * @return 数组长度
     */
    public static int size(final Object[] arg) {
        if (isNotEmpty(arg)) {
            return arg.length;
        }
        return 0;
    }

    /**
     * 获取集合元素个数
     *
     * @param arg 集合
     * @return 集合元素个数
     */
    public static int size(final Collection<?> arg) {
        if (isNotEmpty(arg)) {
            return arg.size();
        }
        return 0;
    }

    /**
     * 获取Map集合元素个数
     *
     * @param arg Map集合
     * @return 集合元素个数
     */
    public static int size(final Map<?, ?> arg) {
        if (isNotEmpty(arg)) {
            return arg.size();
        }
        return 0;
    }

    /**
     * 合并{@link Predicate}
     *
     * @param predicates {@link Predicate}数组
     * @param <T>        泛型
     * @return {@link Predicate}
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> Predicate<T> and(final Predicate... predicates) {
        final int size;
        if ((size = Objects.size(predicates)) > 0) {
            if (size == 1) {
                return predicates[0];
            }
            return Arrays.stream(predicates).reduce(__ -> true, Predicate::and);
        }
        return null;
    }

    /**
     * 过滤
     *
     * @param values 待过滤集合
     * @param filter 过滤器
     * @param <T>    值类型
     * @return 过滤后的列表
     */
    public static <T> Set<T> filter(final Collection<T> values, final Predicate<? super T> filter) {
        if (Objects.isEmpty(values)) {
            return new LinkedHashSet<>(0);
        }
        if (Objects.isNull(filter)) {
            return new LinkedHashSet<>(values);
        }
        return values.stream().filter(filter).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * 过滤
     *
     * @param values  待过滤值列表
     * @param filters 过滤器列表
     * @param <T>     值类型
     * @return 过滤后的列表
     */
    @SafeVarargs
    public static <T> Set<T> filters(final T[] values, final Predicate<? super T>... filters) {
        if (Objects.isEmpty(values)) {
            return new LinkedHashSet<>(0);
        }
        final Predicate<T> filter = and(filters);
        if (Objects.isNull(filter)) {
            return new LinkedHashSet<>(Arrays.asList(values));
        }
        return Arrays.stream(values).filter(filter).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * 过滤
     *
     * @param values  待过滤集合
     * @param filters 过滤器列表
     * @param <T>     值类型
     * @return 过滤后的列表
     */
    @SafeVarargs
    public static <T> Set<T> filters(final Collection<T> values, final Predicate<? super T>... filters) {
        if (Objects.isEmpty(values)) {
            return new LinkedHashSet<>(0);
        }
        final Predicate<T> filter = and(filters);
        if (Objects.isNull(filter)) {
            return new LinkedHashSet<>(values);
        }
        return values.stream().filter(filter).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * 修复Jdk8版本下{@link Map#computeIfAbsent(Object, Function)}存在的性能问题
     *
     * @param map      {@link Map}
     * @param k        键
     * @param supplier {@link Supplier}
     * @param <K>      键类型
     * @param <V>      值类型
     * @return 值
     */
    public static <K, V> V computeIfAbsent(final Map<K, V> map, final K k, final Supplier<V> supplier) {
        final V v = map.get(k);
        if (Objects.nonNull(v)) {
            return v;
        }
        final V newValue = supplier.get();
        return Objects.ifNull(map.putIfAbsent(k, newValue), newValue);
    }

    /**
     * 修复Jdk8版本下{@link Map#computeIfAbsent(Object, Function)}存在的性能问题
     *
     * @param map             {@link Map}
     * @param k               键
     * @param mappingFunction {@link Function}
     * @param <K>             键类型
     * @param <V>             值类型
     * @return 值
     */
    public static <K, V> V computeIfAbsent(final Map<K, V> map, final K k, final Function<K, V> mappingFunction) {
        if (JAVA_VERSION_8) {
            final V v = map.get(k);
            if (Objects.nonNull(v)) {
                return v;
            }
        }
        return map.computeIfAbsent(k, mappingFunction);
    }

    public static <V> V ifNull(final V v, final V defaultValue) {
        return Objects.isNull(v) ? defaultValue : v;
    }

    /**
     * 检查表达式结果是否为真
     *
     * @param expression 表达式结果
     * @param message    异常信息
     */
    public static void isTrue(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

}
