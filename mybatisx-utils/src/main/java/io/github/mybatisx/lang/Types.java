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

import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * 类型工具
 *
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
public final class Types {

    private static final Logger log = LoggerFactory.getLogger(Types.class);

    private Types() {
    }

    /**
     * 基本数据类型类名
     */
    public static final Set<String> PRI_TYPE_NAMES;
    /**
     * 基本数据类型
     */
    public static final Set<Class<?>> PRI_TYPES;
    /**
     * 基本数据类型描述符
     */
    public static final Set<String> PRI_TYPE_DES;
    /**
     * 基本数据类型包装类名
     */
    public static final Set<String> PRI_WRAPPER_TYPE_NAMES;
    /**
     * 基本数据类型包装类
     */
    public static final Set<Class<?>> PRI_WRAPPER_TYPES;
    /**
     * 新时间API类名
     */
    public static final Set<String> NEW_TIME_TYPE_NAMES;
    /**
     * 新时间API类
     */
    public static final Set<Class<?>> NEW_TIME_TYPES;
    /**
     * 简单类型
     */
    private static final Set<Class<?>> SIMPLE_TYPES;
    /**
     * 元注解
     */
    public static final Set<Class<? extends Annotation>> METADATA_ANNOTATION_TYPES;
    /**
     * 注解实例方法名列表
     */
    public static final Set<String> ANNOTATION_METHOD_NAMES;

    static {
        PRI_TYPE_NAMES = ImmutableSet.of("boolean", "char", "byte", "short", "int",
                "long", "float", "double", "void");
        PRI_TYPES = ImmutableSet.of(boolean.class, char.class, byte.class, short.class, int.class,
                long.class, float.class, double.class, void.class);
        PRI_TYPE_DES = ImmutableSet.of("Z", "C", "B", "S", "I", "J", "F", "D", "V");
        PRI_WRAPPER_TYPE_NAMES = ImmutableSet.of("Boolean", "Character", "Byte", "Short", "Integer",
                "Long", "Float", "Double", "Void");
        PRI_WRAPPER_TYPES = ImmutableSet.of(Boolean.class, Character.class, Byte.class, Short.class,
                Integer.class, Long.class, Float.class, Double.class, Void.class);
        NEW_TIME_TYPE_NAMES = ImmutableSet.of("java.time.Instant", "java.time.LocalDateTime",
                "java.time.LocalDate", "java.time.LocalTime", "java.time.OffsetDateTime", "java.time.OffsetTime",
                "java.time.ZonedDateTime", "java.time.Year", "java.time.Month", "java.time.YearMonth");
        NEW_TIME_TYPES = ImmutableSet.copyOf(NEW_TIME_TYPE_NAMES.stream().map(Types::loadClassIgnoreExp)
                .filter(Objects::nonNull).collect(Collectors.toSet()));
        SIMPLE_TYPES = new HashSet<>(64);
        SIMPLE_TYPES.addAll(Types.PRI_TYPES);
        SIMPLE_TYPES.addAll(PRI_WRAPPER_TYPES);
        SIMPLE_TYPES.addAll(Arrays.asList(Date.class, Timestamp.class, Calendar.class, Class.class, BigInteger.class,
                BigDecimal.class, String.class));
        SIMPLE_TYPES.addAll(NEW_TIME_TYPES);
        METADATA_ANNOTATION_TYPES = ImmutableSet.of(Inherited.class, Documented.class, Target.class, Retention.class
                , SuppressWarnings.class, Override.class, SafeVarargs.class);
        ANNOTATION_METHOD_NAMES = ImmutableSet.of("annotationType", "toString", "hashCode", "equals");
    }

    /**
     * 加载类(忽略异常)
     *
     * @param className 类名
     * @return 类对象
     */
    public static Class<?> loadClassIgnoreExp(final String className) {
        if (Strings.isNotWhitespace(className)) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                log.warn("Class loading failed: " + e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 注册简单类
     *
     * @param classes 多个全限定类名([,:;]分隔)
     */
    public static void registrySimpleTypeLicence(final String classes) {
        if (Strings.isNotWhitespace(classes)) {
            Arrays.stream(classes.split("([,:;])(\\s*)?")).forEach(Types::registrySimpleType);
        }
    }

    /**
     * 注册简单类
     *
     * @param className 全限定类名
     */
    public static void registrySimpleType(final String className) {
        Types.registrySimpleType(Types.loadClassIgnoreExp(className));
    }

    /**
     * 注册简单类
     *
     * @param clazz 类
     */
    public static void registrySimpleType(final Class<?> clazz) {
        if (Objects.nonNull(clazz)) {
            SIMPLE_TYPES.add(clazz);
        }
    }

    /**
     * 获取所有简单类型
     *
     * @return 简单类型集合
     */
    public static Set<Class<?>> getSimpleTypes() {
        return new HashSet<>(Types.SIMPLE_TYPES);
    }

    /**
     * 检查指定类是否为简单类型
     *
     * @param clazz 待检查类
     * @return boolean
     */
    public static boolean isSimpleType(final Class<?> clazz) {
        return Objects.nonNull(clazz) && !Types.isObject(clazz) && Types.SIMPLE_TYPES.contains(clazz);
    }

    /**
     * 检查两个类是否一致
     *
     * @param source 源类
     * @param clazz  目标类
     * @return boolean
     */
    public static boolean is(final Class<?> source, final Class<?> clazz) {
        return Objects.nonNull(source) && source.equals(clazz);
    }

    /**
     * 检查指定类是否为{@link Object}类型
     *
     * @param clazz 待检查类
     * @return boolean
     */
    public static boolean isObject(final Class<?> clazz) {
        return Types.is(Object.class, clazz);
    }

    /**
     * 检查指定类是否为{@link Serializable}类型
     *
     * @param clazz 待检查类
     * @return boolean
     */
    public static boolean isSerializable(final Class<?> clazz) {
        return Types.is(Serializable.class, clazz);
    }

    /**
     * 检查指定类是否为{@link Annotation}类型
     *
     * @param clazz 待检查类
     * @return boolean
     */
    public static boolean isAnnotation(final Class<?> clazz) {
        return Types.is(Annotation.class, clazz);
    }

    /**
     * 检查指定类是否为基本数据类型或包装类型
     *
     * @param clazz 待检查类
     * @return boolean
     */
    public static boolean isPrimitiveOrWrapType(final Class<?> clazz) {
        return Objects.nonNull(clazz) && (Types.PRI_TYPES.contains(clazz) || Types.PRI_WRAPPER_TYPES.contains(clazz));
    }

    /**
     * 转成字符串
     *
     * @param arg 对象
     * @return 字符串
     */
    public static String toString(final Object arg) {
        return Strings.toString(arg);
    }

    /**
     * 转成{@link Character}
     *
     * @param arg 对象
     * @return {@link Character}
     */
    public static Character toChar(final Object arg) {
        if (Objects.isNull(arg)) {
            return null;
        }
        if (arg instanceof Character) {
            return (Character) arg;
        }
        if (arg instanceof String) {
            final String str = ((String) arg).trim();
            final int size = str.length();
            if (size == 0) {
                return null;
            }
            if (size == 1) {
                return str.charAt(0);
            }
        }
        throw new IllegalArgumentException("Can not cast to char, value: " + arg);
    }

    /**
     * 转成{@link Short}
     *
     * @param arg 对象
     * @return {@link Short}
     */
    public static Short toShort(final Object arg) {
        if (Objects.isNull(arg)) {
            return null;
        }
        if (arg instanceof Short) {
            return (Short) arg;
        }
        if (arg instanceof BigDecimal) {
            return shortValue((BigDecimal) arg);
        }
        if (arg instanceof Number) {
            return ((Number) arg).shortValue();
        }
        final String str = Strings.trim(arg);
        if (Objects.nonNull(str)) {
            if (Strings.isNull(str)) {
                return 0;
            }
            return Short.parseShort(str);
        }
        throw new IllegalArgumentException("Can not cast to short, value: " + arg);
    }

    /**
     * 转成{@link Integer}
     *
     * @param arg 对象
     * @return {@link Integer}
     */
    public static Integer toInt(final Object arg) {
        if (Objects.isNull(arg)) {
            return null;
        }
        if (arg instanceof Integer) {
            return (Integer) arg;
        }
        if (arg instanceof BigDecimal) {
            return intValue((BigDecimal) arg);
        }
        if (arg instanceof Number) {
            return ((Number) arg).intValue();
        }
        if (arg instanceof Boolean) {
            return (Boolean) arg ? 1 : 0;
        }
        final String str = Strings.trim(arg);
        if (Objects.nonNull(str)) {
            if (Strings.isNull(str)) {
                return 0;
            }
            if (Regex.REGEX_INTEGER.matcher(str).matches()) {
                return Integer.parseInt(str);
            }
        }
        throw new IllegalArgumentException("Can not cast to integer, value: " + arg);
    }

    /**
     * 转成{@link Long}
     *
     * @param arg 对象
     * @return {@link Long}
     */
    public static Long toLong(final Object arg) {
        if (Objects.isNull(arg)) {
            return null;
        }
        if (arg instanceof Long) {
            return (Long) arg;
        }
        if (arg instanceof BigDecimal) {
            return longValue((BigDecimal) arg);
        }
        if (arg instanceof Boolean) {
            return (Boolean) arg ? 1L : 0L;
        }
        final String str = Strings.trim(arg);
        if (Objects.nonNull(str)) {
            if (Strings.isNull(str)) {
                return 0L;
            }
            if (Regex.isInteger(str)) {
                return Long.parseLong(str);
            }
        }
        throw new IllegalArgumentException("Can not cast to long, value: " + arg);
    }

    /**
     * 转成{@link Float}
     *
     * @param arg 对象
     * @return {@link Float}
     */
    public static Float toFloat(final Object arg) {
        if (Objects.isNull(arg)) {
            return null;
        }
        if (arg instanceof Float) {
            return (Float) arg;
        }
        if (arg instanceof Boolean) {
            return (Boolean) arg ? 1.f : 0.f;
        }
        final String str = Strings.trim(arg);
        if (Objects.nonNull(str)) {
            if (Strings.isNull(str)) {
                return 0.f;
            }
            if (Regex.isInteger(str)) {
                return Float.parseFloat(str);
            }
        }
        throw new IllegalArgumentException("Can not cast to float, value: " + arg);
    }

    /**
     * 转成{@link Double}
     *
     * @param arg 对象
     * @return {@link Double}
     */
    public static Double toDouble(final Object arg) {
        if (Objects.isNull(arg)) {
            return null;
        }
        if (arg instanceof Double) {
            return (Double) arg;
        }
        if (arg instanceof Boolean) {
            return (Boolean) arg ? 1.d : 0.d;
        }
        final String str = Strings.trim(arg);
        if (Objects.nonNull(str)) {
            if (Strings.isNull(str)) {
                return 0.d;
            }
            if (Regex.isInteger(str)) {
                return Double.parseDouble(str);
            }
        }
        throw new IllegalArgumentException("Can not cast to double, value: " + arg);
    }

    /**
     * 转成{@link Boolean}
     *
     * @param arg 对象
     * @return {@link Boolean}
     */
    public static Boolean toBoolean(final Object arg) {
        if (Objects.isNull(arg)) {
            return null;
        }
        if (arg instanceof BigDecimal) {
            return intValue((BigDecimal) arg) != 0;
        }
        if (arg instanceof Number) {
            return ((Number) arg).intValue() != 0;
        }
        if (arg instanceof Boolean) {
            return (Boolean) arg;
        }
        final String str = Strings.trim(arg);
        if (Objects.nonNull(str)) {
            if (Strings.isNull(str)) {
                return false;
            }
            if (Strings.isTrue(str)) {
                return Boolean.TRUE;
            }
            if (Strings.isFalse(str)) {
                return Boolean.FALSE;
            }
        }
        throw new IllegalArgumentException("Can not cast to boolean, value: " + arg);
    }

    /**
     * {@link BigDecimal}转short
     *
     * @param decimal {@link BigDecimal}
     * @return short
     */
    public static short shortValue(final BigDecimal decimal) {
        if (Objects.isNull(decimal)) {
            return 0;
        }
        return checkScale(decimal.scale()) ? decimal.shortValue() : decimal.shortValueExact();
    }

    /**
     * {@link BigDecimal}转int
     *
     * @param decimal {@link BigDecimal}
     * @return int
     */
    public static int intValue(final BigDecimal decimal) {
        if (Objects.isNull(decimal)) {
            return 0;
        }
        return checkScale(decimal.scale()) ? decimal.intValue() : decimal.intValueExact();
    }

    /**
     * {@link BigDecimal}转long
     *
     * @param decimal {@link BigDecimal}
     * @return long
     */
    public static long longValue(final BigDecimal decimal) {
        if (Objects.isNull(decimal)) {
            return 0;
        }
        return checkScale(decimal.scale()) ? decimal.longValue() : decimal.longValueExact();
    }

    private static String trim(final Object arg) {
        String str = toString(arg);
        if (Strings.isWhitespace(str) || Strings.DEFAULT_STR_NULL.equalsIgnoreCase(str)) {
            return null;
        }
        if (str.contains(Strings.DEFAULT_STR_COMMA)) {
            str = str.replaceAll(Strings.DEFAULT_STR_COMMA, Strings.DEFAULT_STR_EMPTY);
        }
        final Matcher matcher = Regex.REGEX_NUMBER_WITH_TRAILING_ZEROS.matcher(str);
        if (matcher.find()) {
            str = matcher.replaceAll(Strings.DEFAULT_STR_EMPTY);
        }
        return str;
    }

    private static boolean checkScale(final int scale) {
        return scale >= -100 && scale <= 100;
    }

}
