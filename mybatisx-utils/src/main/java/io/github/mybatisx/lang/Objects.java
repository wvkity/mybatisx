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
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        return source != null && source.isAssignableFrom(clazz);
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
     * @param t            值
     * @param defaultValue 默认值
     * @param <T>          值类型
     * @return 值
     */
    public static <T> T ifNull(final T t, final T defaultValue) {
        return Objects.isNull(t) ? defaultValue : t;
    }

    /**
     * 如果给定值为不为null则返回v，否则返回默认值
     *
     * @param t            指定值
     * @param defaultValue 默认值
     * @param <T>          值类型
     * @return 值
     */
    public static <T> T ifNonNull(final T t, final T defaultValue) {
        return Objects.nonNull(t) ? t : defaultValue;
    }

    /**
     * 如果给定值为不为null则返回v1，否则返回v2
     *
     * @param t   指定值
     * @param t1  真返回值
     * @param t2  假返回值
     * @param <T> 值类型
     * @return 值
     */
    public static <T> T ifNonNull(final T t, final T t1, final T t2) {
        return Objects.nonNull(t) ? t1 : t2;
    }

    /**
     * 如果指定值为null则返回默认值
     *
     * @param t            指定值
     * @param defaultValue 默认值
     * @param action       {@link Function}
     * @param <T>          值类型
     * @param <R>          值类型
     * @return 处理后的值
     */
    public static <T, R> R ifNullThen(final T t, final R defaultValue, final Function<T, R> action) {
        if (Objects.nonNull(t) && Objects.nonNull(action)) {
            return action.apply(t);
        }
        return defaultValue;
    }

    /**
     * 如果给定的值不为null则消费
     *
     * @param t        指定值
     * @param consumer {@link Consumer}
     * @param <T>      值类型
     */
    public static <T> void ifNonNullThen(final T t, final Consumer<T> consumer) {
        Objects.ifTrueThen(t, Objects::nonNull, consumer);
    }

    /**
     * 指定值符合要求并消费
     *
     * @param t         指定值
     * @param predicate {@link  Predicate}
     * @param consumer  {@link  Consumer}
     * @param <T>       值类型
     */
    public static <T> void ifTrueThen(final T t, final Predicate<T> predicate, final Consumer<T> consumer) {
        if (Objects.nonNull(predicate) && Objects.nonNull(consumer) && predicate.test(t)) {
            consumer.accept(t);
        }
    }

    /**
     * 指定值符合要求则设定该值，否则设定默认值
     *
     * @param t            指定值
     * @param defaultValue 默认值
     * @param predicate    {@link  Predicate}
     * @param consumer     {@link  Consumer}
     * @param <T>          值类型
     */
    public static <T> void ifTrueThen(final T t, final T defaultValue, final Predicate<T> predicate,
                                      final Consumer<T> consumer) {
        if (Objects.nonNull(predicate) && Objects.nonNull(consumer)) {
            if (predicate.test(t)) {
                consumer.accept(t);
            } else {
                consumer.accept(defaultValue);
            }
        }
    }

    /**
     * 指定值符合要求，则执行预期消费，否则执行默认消费
     *
     * @param expectValue   预期值
     * @param defaultValue  默认值
     * @param predicate     {@link  Predicate}
     * @param expectAction  {@link  Consumer}
     * @param defaultAction {@link  Consumer}
     * @param <T>           值类型
     */
    public static <T> void ifTrueThen(final T expectValue, final T defaultValue, final Predicate<T> predicate,
                                      final Consumer<T> expectAction, final Consumer<T> defaultAction) {
        if (Objects.nonNull(predicate)) {
            if (predicate.test(expectValue) && Objects.nonNull(expectAction)) {
                expectAction.accept(expectValue);
            } else if (Objects.nonNull(defaultAction)) {
                defaultAction.accept(defaultValue);
            }
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
    @SafeVarargs
    public static <T> List<T> objectAsList(final T... args) {
        if (isNotEmpty(args)) {
            return new ArrayList<>(Arrays.asList(args));
        }
        return new ArrayList<>(0);
    }

    /**
     * 参数转{@link List}
     *
     * @param arg 参数
     * @param <T> 类型
     * @return {@link List}
     */
    @SuppressWarnings({"unchecked"})
    public static <T> List<T> toList(final Object arg) {
        if (arg != null) {
            final Class<?> clazz = arg.getClass();
            if (Objects.isAssignable(Map.class, clazz)) {
                return new ArrayList<>((Collection<? extends T>) ((Map<?, ?>) arg).values());
            } else if (Objects.isAssignable(Iterable.class, clazz)) {
                if (arg instanceof Collection) {
                    return new ArrayList<>((Collection<? extends T>) arg);
                } else {
                    return StreamSupport.stream(((Iterable<T>) arg).spliterator(), false).collect(Collectors.toList());
                }
            } else if (clazz.isArray()) {
                return Objects.objectAsList((T[]) arg);
            }
            return new ArrayList<>(Collections.singletonList((T) arg));
        }
        return new ArrayList<>(0);
    }

    /**
     * 基本整型数组转包装数组
     *
     * @param args 值列表
     * @return 包装数组
     */
    public static Integer[] asArray(final int... args) {
        if (args == null || args.length == 0) {
            return new Integer[0];
        }
        return asList(args).toArray(new Integer[0]);
    }

    /**
     * 基本长整型数组转包装数组
     *
     * @param args 值列表
     * @return 包装数组
     */
    public static Long[] asArray(final long... args) {
        if (args == null || args.length == 0) {
            return new Long[0];
        }
        return asList(args).toArray(new Long[0]);
    }

    /**
     * 基本长整型数组转长整型数组
     *
     * @param args 值列表
     * @return 长整型数组
     */
    public static Long[] asLongArray(final int... args) {
        final int len;
        if (args == null || (len = args.length) == 0) {
            return new Long[0];
        }
        final Long[] array = new Long[len];
        for (int i = 0; i < len; i++) {
            array[i] = (long) args[i];
        }
        return array;
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

    /**
     * 检查集合元素是否为空，如果为空，则返回null
     *
     * @param origin 待检查集合
     * @param <C>    {@link Collection}
     * @param <T>    元素类型
     * @return 集合
     */
    public static <C extends Collection<T>, T> C checkNull(final C origin) {
        return checkNull(origin, null);
    }

    /**
     * 检查集合元素是否为空，如果为空则返回默认值
     *
     * @param origin       待检查集合
     * @param defaultValue 默认值
     * @param <C>          {@link Collection}
     * @param <T>          元素类型
     * @return 集合
     */
    public static <C extends Collection<T>, T> C checkNull(final C origin, final C defaultValue) {
        if (isNotEmpty(origin)) {
            for (T it : origin) {
                if (it != null) {
                    return origin;
                }
            }
        }
        return defaultValue;
    }

    /**
     * 字符串元素转成小写
     *
     * @param data 字符串集合
     * @return 处理后的数据
     */
    public static List<String> lowerCase(final Collection<String> data) {
        return transform(data, Objects::nonNull, it -> it.toLowerCase(Locale.ENGLISH));
    }

    /**
     * 字符串元素转成大写
     *
     * @param data 字符串集合
     * @return 处理后的字符串集合
     */
    public static List<String> upperCase(final Collection<String> data) {
        return transform(data, Objects::nonNull, it -> it.toUpperCase(Locale.ENGLISH));
    }

    /**
     * 数据转换
     *
     * @param origin 原数据
     * @param filter {@link Predicate}
     * @param mapper {@link Function}
     * @param <C>    {@link Collection}
     * @param <T>    数据类型
     * @param <R>    返回值类型
     * @return 处理后的数据集合
     */
    public static <C extends Collection<T>, T, R> List<R> transform(final C origin, final Predicate<T> filter,
                                                                    final Function<T, R> mapper) {
        if (isNotEmpty(origin)) {
            return origin.stream().filter(filter).map(mapper).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 数据合并
     *
     * @param array 多个集合
     * @param <T>   数据类型
     * @param <C>   集合类型
     * @return 合并后的数据
     */
    @SafeVarargs
    public static <T, C extends Iterable<T>> List<T> combine(final C... array) {
        return combine(null, array);
    }

    /**
     * 数据合并
     *
     * @param filter 过滤器
     * @param array  多个集合
     * @param <T>    数据类型
     * @param <C>    集合类型
     * @return 合并后的数据
     */
    @SafeVarargs
    public static <T, C extends Iterable<T>> List<T> combine(final Predicate<T> filter, final C... array) {
        final Predicate<T> ipt = filter == null ? __ -> true : filter;
        if (isNotEmpty(array)) {
            final List<T> result = new ArrayList<>();
            for (C c : array) {
                if (nonNull(c)) {
                    for (T it : c) {
                        if (ipt.test(it)) {
                            result.add(it);
                        }
                    }
                }
            }
            return result;
        }
        return new ArrayList<>(0);
    }
}
