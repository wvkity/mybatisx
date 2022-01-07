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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
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
        if (Objects.nonNull(arg)) {
            if (arg instanceof Class) {
                return Objects.isAssignable(source, (Class<?>) arg);
            }
            return Objects.isAssignable(source, arg.getClass());
        }
        return false;
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
     * 检查对象是否为{@link Iterable}对象
     *
     * @param arg 目标对象
     * @return boolean
     */
    public static boolean isIterable(final Object arg) {
        return nonNull(arg) && isAssignable(Iterable.class, arg);
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
     * 检查参数列表是否包含数组、集合类型
     *
     * @param iterable 参数列表
     * @return boolean
     */
    public static boolean isPureType(final Iterable<?> iterable) {
        if (iterable != null) {
            Class<?> current = null;
            for (Object it : iterable) {
                if (it != null) {
                    final Class<?> clazz = it.getClass();
                    if (clazz.isArray() || isAssignable(Iterable.class, clazz) || isAssignable(Map.class, clazz)) {
                        return false;
                    }
                    if (current != null && !isAssignable(current, clazz)) {
                        return false;
                    }
                    current = clazz;
                }
            }
            return true;
        }
        return false;
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
     * 获取数组类型
     *
     * @param arg 数组对象
     * @return 数组类型
     */
    public static Class<?> getArrayType(final Object arg) {
        return isNull(arg) ? null : getArrayType(arg.getClass());
    }

    /**
     * 获取数组类型
     *
     * @param clazz 数组类
     * @return 数组类型
     */
    public static Class<?> getArrayType(final Class<?> clazz) {
        return nonNull(clazz) && clazz.isArray() ? clazz.getComponentType() : null;
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

    /**
     * 如果值为null则返回默认值
     *
     * @param v            值
     * @param defaultValue 默认值
     * @param <V>          值类型
     * @return 值
     */
    public static <V> V ifNull(final V v, final V defaultValue) {
        return Objects.isNull(v) ? defaultValue : v;
    }

    /**
     * 如果指定值为null则返回默认值
     *
     * @param v            指定值
     * @param defaultValue 默认值
     * @param action       {@link Function}
     * @param <V>          值类型
     * @param <R>          值类型
     * @return 处理后的值
     */
    public static <V, R> R ifNullThen(final V v, final R defaultValue, final Function<V, R> action) {
        if (Objects.nonNull(v) && Objects.nonNull(action)) {
            return action.apply(v);
        }
        return defaultValue;
    }

    /**
     * 如果给定的值不为null则消费
     *
     * @param v        指定值
     * @param consumer {@link Consumer}
     * @param <V>      值类型
     */
    public static <V> void ifNonNullThen(final V v, final Consumer<V> consumer) {
        if (Objects.nonNull(v)) {
            consumer.accept(v);
        }
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

    /**
     * 过滤null值
     *
     * @param factory {@link Supplier}
     * @param values  待过滤值列表
     * @param <U>     值类型
     * @param <C>     集合类型
     * @return 新的列表
     */
    @SafeVarargs
    public static <U, C extends Collection<U>> C filterNull(final Supplier<C> factory, final U... values) {
        return Objects.filterNull(factory, Arrays.asList(values));
    }

    /**
     * 过滤null值
     *
     * @param factory {@link Supplier}
     * @param values  待过滤值列表
     * @param <U>     值类型
     * @param <C>     集合类型
     * @return 新的列表
     */
    public static <U, C extends Collection<U>> C filterNull(final Supplier<C> factory, final Collection<U> values) {
        if (Objects.isNotEmpty(values)) {
            return values.stream().filter(Objects::nonNull).collect(Collectors.toCollection(factory));
        }
        return factory.get();
    }

    /**
     * 过滤null值
     *
     * @param values 待过滤值列表
     * @param <U>    值类型
     * @return 新的列表
     */
    @SafeVarargs
    public static <U> Set<U> filterNull(final U... values) {
        if (Objects.isNotEmpty(values)) {
            return Objects.filterNull(Arrays.asList(values));
        }
        return new HashSet<>(0);
    }

    /**
     * 过滤null值
     *
     * @param values 待过滤值列表
     * @param <U>    值类型
     * @return 新的列表
     */
    public static <U> Set<U> filterNull(final Collection<U> values) {
        return Optional.ofNullable(values).map(it -> it.stream().filter(Objects::nonNull).collect(Collectors.toSet()))
                .orElse(new HashSet<>(0));
    }

    /**
     * 将值转换成byte/char/boolean/short/int/long类型值
     *
     * @param javaType java类型
     * @param value    字符串值
     * @return 值
     */
    public static Object convert(final Class<?> javaType, final String value) {
        if (javaType == null || Strings.isWhitespace(value) || Types.is(String.class, javaType)) {
            return value;
        }
        if (javaType == Long.class || javaType == long.class) {
            return Long.valueOf(value);
        }
        if (javaType == Integer.class || javaType == int.class) {
            return Integer.valueOf(value);
        }
        if (javaType == Short.class || javaType == short.class) {
            return Short.valueOf(value);
        }
        if (javaType == Character.class || javaType == char.class) {
            return value.charAt(0);
        }
        if (javaType == Boolean.class || javaType == boolean.class) {
            return "1".equals(value) || Boolean.parseBoolean(value);
        }
        if (javaType == Byte.class || javaType == byte.class) {
            return Byte.parseByte(value);
        }
        return value;
    }

    /**
     * 布尔数组转集合
     *
     * @param args 参数列表
     * @return 布尔集合
     */
    public static List<Boolean> asList(final boolean... args) {
        if (args != null && args.length > 0) {
            final List<Boolean> list = new ArrayList<>(args.length);
            for (boolean it : args) {
                list.add(it);
            }
            return list;
        }
        return new ArrayList<>(0);
    }

    /**
     * 字符数组转集合
     *
     * @param args 参数列表
     * @return 字符集合
     */
    public static List<Character> asList(final char... args) {
        if (args != null && args.length > 0) {
            final List<Character> list = new ArrayList<>(args.length);
            for (char it : args) {
                list.add(it);
            }
            return list;
        }
        return new ArrayList<>(0);
    }

    /**
     * 整数数组转集合
     *
     * @param args 参数列表
     * @return 整数集合
     */
    public static List<Integer> asList(final int... args) {
        if (args != null && args.length > 0) {
            return Arrays.stream(args).boxed().collect(Collectors.toList());
        }
        return new ArrayList<>(0);
    }

    /**
     * 双精度浮点数数组转集合
     *
     * @param args 参数列表
     * @return 双精度浮点数集合
     */
    public static List<Double> asList(final double... args) {
        if (args != null && args.length > 0) {
            return Arrays.stream(args).boxed().collect(Collectors.toList());
        }
        return new ArrayList<>(0);
    }

    /**
     * 长整数数组转集合
     *
     * @param args 参数列表
     * @return 长整数集合
     */
    public static List<Long> asList(final long... args) {
        if (args != null && args.length > 0) {
            return Arrays.stream(args).boxed().collect(Collectors.toList());
        }
        return new ArrayList<>(0);
    }

    /**
     * 数组转集合
     *
     * @param args 参数列表
     * @param <T>  参数类型
     * @return 集合
     */
    public static <T> List<T> objectAsList(final T... args) {
        if (isNotEmpty(args)) {
            return new ArrayList<>(Arrays.asList(args));
        }
        return new ArrayList<>(0);
    }

    /**
     * 检查指定对象是否为null
     *
     * @param object  待检查对象
     * @param message 异常信息
     * @param <T>     泛型类型
     * @return 指定对象
     */
    public static <T> T requireNonNull(final T object, final String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    /**
     * 检查字符串是否为空值
     *
     * @param value   待检查字符串
     * @param message 异常消息
     * @return 字符串
     */
    public static String requireNonEmpty(final String value, final String message) {
        if (Strings.isWhitespace(requireNonNull(value, message))) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

}
