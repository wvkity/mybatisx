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

import io.github.mybatisx.function.Absence;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 字符串工具
 *
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
public final class StringHelper {

    private StringHelper() {
    }

    public static final String NULL = "null";
    public static final String EMPTY = "";
    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String TRUE = "true";
    public static final String T = "t";
    public static final String YES = "yes";
    public static final String Y = "y";
    public static final String ON = "on";
    public static final String FALSE = "false";
    public static final String F = "f";
    public static final String NO = "no";
    public static final String N = "n";
    public static final String OFF = "off";
    public static final String COMMA = ",";
    public static final char CASE_MASK = 0x20;

    /**
     * 对象转字符串
     *
     * @param arg 对象
     * @return 字符串
     */
    public static String toString(final Object arg) {
        if (ObjectHelper.isNull(arg)) {
            return null;
        }
        if (arg instanceof String) {
            return (String) arg;
        }
        return arg.toString();
    }

    /**
     * 检查字符串是否为空
     *
     * @param arg 字符串
     * @return boolean
     */
    public static boolean isEmpty(final CharSequence arg) {
        return ObjectHelper.isNull(arg) || arg.length() == 0;
    }

    /**
     * 检查字符串是否不为空
     *
     * @param arg 字符串
     * @return boolean
     */
    public static boolean isNotEmpty(final CharSequence arg) {
        return !isEmpty(arg);
    }

    /**
     * 检查字符串是否为空白
     *
     * @param arg 字符串
     * @return boolean
     */
    public static boolean isWhitespace(final CharSequence arg) {
        if (isNotEmpty(arg)) {
            final int size = arg.length();
            for (int i = 0; i < size; i++) {
                if (!Character.isWhitespace(arg.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检查字符串是否不为空白
     *
     * @param arg 字符串
     * @return boolean
     */
    public static boolean isNotWhitespace(final CharSequence arg) {
        return !isWhitespace(arg);
    }

    /**
     * 检查字符串是否为空或在为"null"字符串
     *
     * @param arg 字符串
     * @return boolean
     */
    public static boolean isNull(final CharSequence arg) {
        return isEmpty(arg) || NULL.equalsIgnoreCase(arg.toString());
    }

    /**
     * 如果给定的值是空白值，则返回默认值
     *
     * @param value        指定值
     * @param defaultValue 默认值
     * @return 字符串
     */
    public static String ifNull(final String value, final String defaultValue) {
        return StringHelper.isWhitespace(value) ? defaultValue : value;
    }

    /**
     * 获取字符串长度
     *
     * @param arg 字符串
     * @return 字符串长度
     */
    public static int size(final String arg) {
        if (isNotEmpty(arg)) {
            return arg.length();
        }
        return 0;
    }

    /**
     * 去除空格
     *
     * @param arg 字符串
     * @return 新字符串
     */
    public static String trim(final Object arg) {
        if (ObjectHelper.isAssignable(String.class, arg)) {
            return trim(arg.toString());
        }
        return null;
    }

    /**
     * 去除空格
     *
     * @param arg 字符串
     * @return 新字符串
     */
    public static String trim(final String arg) {
        if (isNotEmpty(arg)) {
            return arg.trim();
        }
        return null;
    }

    /**
     * 比较两个字符串是否一致
     *
     * @param a 字符串a
     * @param b 字符串b
     * @return boolean
     */
    public static boolean equals(final String a, final String b) {
        return ObjectHelper.nonNull(a) && size(a) == size(b) && a.equals(b);
    }

    /**
     * 忽略大小写比较两个字符串是否一致
     *
     * @param a 字符串a
     * @param b 字符串b
     * @return boolean
     */
    public static boolean equalsIC(final String a, final String b) {
        return ObjectHelper.nonNull(a) && size(a) == size(b) && a.equalsIgnoreCase(b);
    }

    /**
     * 检查字符串是否表示true
     *
     * @param arg 字符串
     * @return boolean
     */
    public static boolean isTrue(final String arg) {
        return equalsIC(TRUE, arg) || equalsIC(Y, arg) || equalsIC(T, arg)
                || equals(ONE, arg);
    }

    /**
     * 检查字符串是否表示false
     *
     * @param arg 字符串
     * @return boolean
     */
    public static boolean isFalse(final String arg) {
        return equalsIC(FALSE, arg) || equalsIC(N, arg) || equalsIC(F, arg)
                || equals(ZERO, arg);
    }

    /**
     * 检查字符是否为小写字母
     *
     * @param c 字符
     * @return boolean
     */
    public static boolean isLowerCase(final char c) {
        return Character.isLowerCase(c);
    }

    /**
     * 检查字符是否为大写字母
     *
     * @param c 字符
     * @return boolean
     */
    public static boolean isUpperCase(final char c) {
        return Character.isUpperCase(c);
    }

    /**
     * 大写字母转小写字母
     *
     * @param c 字符
     * @return 新字符
     */
    public static char toLowerCase(final char c) {
        return StringHelper.isLowerCase(c) ? c : Character.toLowerCase(c);
    }

    /**
     * 小写字母转大写字母
     *
     * @param c 字符
     * @return 新字符
     */
    public static char toUpperCase(final char c) {
        return StringHelper.isUpperCase(c) ? c : Character.toUpperCase(c);
    }

    /**
     * 检查字符串是否为小写字母开头
     *
     * @param source 待检查字符串
     * @return boolean
     */
    public static boolean isLowerStartsWith(final String source) {
        if (StringHelper.isNotWhitespace(source)) {
            return StringHelper.isLowerCase(source.charAt(0));
        }
        return false;
    }

    /**
     * 检查字符串是否为大写字母开头
     *
     * @param source 待检查字符串
     * @return boolean
     */
    public static boolean isUpperStartsWith(final String source) {
        if (StringHelper.isNotWhitespace(source)) {
            return StringHelper.isUpperCase(source.charAt(0));
        }
        return false;
    }

    /**
     * 字符串中的大写字母转小写字母
     *
     * @param source 字符串
     * @return 新的字符串
     */
    public static String toLowerCase(final CharSequence source) {
        if (StringHelper.isNotWhitespace(source)) {
            final String str = source.toString();
            for (int i = 0, size = str.length(); i < size; i++) {
                if (StringHelper.isUpperCase(str.charAt(i))) {
                    final char[] array = str.toCharArray();
                    for (; i < size; i++) {
                        final char c = array[i];
                        if (StringHelper.isUpperCase(c)) {
                            array[i] = (char) (c ^ CASE_MASK);
                        }
                    }
                    return String.valueOf(array);
                }
            }
            return str;
        }
        return ObjectHelper.isNull(source) ? null : source.toString();
    }

    /**
     * 字符串中的小写字母转大写字母
     *
     * @param source 字符串
     * @return 新的字符串
     */
    public static String toUpperCase(final CharSequence source) {
        if (StringHelper.isNotWhitespace(source)) {
            final String str = source.toString();
            for (int i = 0, size = str.length(); i < size; i++) {
                if (StringHelper.isLowerCase(str.charAt(i))) {
                    final char[] array = str.toCharArray();
                    for (; i < size; i++) {
                        final char c = array[i];
                        if (StringHelper.isLowerCase(c)) {
                            array[i] = (char) (c ^ CASE_MASK);
                        }
                    }
                    return String.valueOf(array);
                }
            }
            return str;
        }
        return ObjectHelper.isNull(source) ? null : source.toString();
    }

    /**
     * 首字母转小写
     *
     * @param source 字符串
     * @return 新字符串
     */
    public static String firstToLowerCase(final String source) {
        if (StringHelper.isNotWhitespace(source)) {
            return StringHelper.toLowerCase(source.charAt(0)) + source.substring(1);
        }
        return source;
    }

    /**
     * 首字母转大写
     *
     * @param source 字符串
     * @return 新字符串
     */
    public static String firstToUpperCase(final String source) {
        if (StringHelper.isNotWhitespace(source)) {
            return StringHelper.toUpperCase(source.charAt(0)) + source.substring(1);
        }
        return source;
    }

    /**
     * 仅仅首字母转大写其余转小写
     *
     * @param source 字符串
     * @return 新字符串
     */
    public static String firstOnlyToUpperCase(final String source) {
        if (StringHelper.isNotWhitespace(source)) {
            return StringHelper.toUpperCase(source.charAt(0)) + StringHelper.toLowerCase(source.substring(1));
        }
        return source;
    }

    /**
     * 如果给定的值为null则消费
     *
     * @param v        指定字符串值
     * @param consumer {@link Absence}
     */
    public static void ifNullThen(final String v, final Absence consumer) {
        if (v == null) {
            consumer.accept();
        }
    }

    /**
     * 如果给定的值为null则消费
     *
     * @param v            指定字符串值
     * @param defaultValue 默认值
     * @param consumer     {@link Absence}
     */
    public static void ifNullThen(final String v, final String defaultValue, final Consumer<String> consumer) {
        if (v == null) {
            consumer.accept(defaultValue);
        }
    }

    /**
     * 如果给定的值为null则消费
     *
     * @param v        指定字符串值
     * @param consumer {@link Absence}
     * @param source   {@link Supplier}
     */
    public static void ifNullThen(final String v, final Consumer<String> consumer, final Supplier<String> source) {
        if (v == null && source != null) {
            consumer.accept(source.get());
        }
    }

    /**
     * 如果给定的值不为null则消费
     *
     * @param v        指定字符串值
     * @param consumer {@link Consumer}
     */
    public static void ifNonNullThen(final String v, final Consumer<String> consumer) {
        if (v != null) {
            consumer.accept(v);
        }
    }

    /**
     * 如果指定值为空则消费
     *
     * @param v        指定字符串值
     * @param consumer {@link Absence}
     */
    public static void ifWhitespaceThen(final String v, final Absence consumer) {
        if (StringHelper.isWhitespace(v)) {
            consumer.accept();
        }
    }

    /**
     * 如果指定值为空则消费
     *
     * @param v            指定字符串值
     * @param defaultValue 默认值
     * @param consumer     {@link Consumer}
     */
    public static void ifWhitespaceThen(final String v, final String defaultValue, final Consumer<String> consumer) {
        if (StringHelper.isWhitespace(v)) {
            consumer.accept(defaultValue);
        }
    }

    /**
     * 如果指定值为空则消费
     *
     * @param v        指定字符串值
     * @param consumer {@link Consumer}
     * @param source   {@link Supplier}
     */
    public static void ifWhitespaceThen(final String v, final Consumer<String> consumer, final Supplier<String> source) {
        if (StringHelper.isWhitespace(v) && source != null) {
            consumer.accept(source.get());
        }
    }

    /**
     * 如果给定的值不为空值则消费
     *
     * @param v        指定字符串值
     * @param consumer {@link Consumer}
     */
    public static void ifNotWhitespaceThen(final String v, final Consumer<String> consumer) {
        if (isNotWhitespace(v)) {
            consumer.accept(v);
        }
    }

    /**
     * 字符串转整数
     *
     * @param arg 字符串
     * @return 整数
     */
    public static int parseInt(final String arg) {
        return parseInt(arg, 0);
    }

    /**
     * 字符串转整数
     *
     * @param arg          字符串
     * @param defaultValue 默认值
     * @return 整数
     */
    public static int parseInt(final String arg, final int defaultValue) {
        if (RegexHelper.isInteger(arg)) {
            return Integer.parseInt(arg.replaceAll("_", ""));
        }
        return defaultValue;
    }

    /**
     * 字符串转正整数
     *
     * @param arg 字符串
     * @return 整数
     */
    public static int parsePositiveInt(final String arg) {
        return parsePositiveInt(arg, 0);
    }

    /**
     * 字符串转正整数
     *
     * @param arg          字符串
     * @param defaultValue 默认值
     * @return 整数
     */
    public static int parsePositiveInt(final String arg, final int defaultValue) {
        if (StringHelper.isNotEmpty(arg) && RegexHelper.isPositiveInteger(arg)) {
            return Integer.parseInt(arg.replaceAll("_", ""));
        }
        return defaultValue;
    }

    /**
     * {@link String}转{@link Boolean}
     *
     * @param arg 字符串值
     * @return {@link Boolean}
     */
    public static Boolean parseBoolean(final String arg) {
        return StringHelper.parseBoolean(arg, null);
    }

    /**
     * {@link String}转{@link Boolean}
     *
     * @param arg          字符串值
     * @param defaultValue 默认值
     * @return {@link Boolean}
     */
    public static Boolean parseBoolean(final String arg, final Boolean defaultValue) {
        if (StringHelper.isNotWhitespace(arg)) {
            final String _value = arg.toLowerCase(Locale.ENGLISH);
            if (_value.equals(ONE) ||
                    _value.equals(ON) ||
                    _value.equals(TRUE) ||
                    _value.equals(T) ||
                    _value.equals(YES) ||
                    _value.equals(Y)) {
                return Boolean.TRUE;
            }
            if (_value.equals(ZERO) ||
                    _value.equals(OFF) ||
                    _value.equals(FALSE) ||
                    _value.equals(F) ||
                    _value.equals(NO) ||
                    _value.equals(N)) {
                return Boolean.FALSE;
            }
        }
        return defaultValue;
    }

    /**
     * {@link String}转{@link Integer}
     *
     * @param arg 字符串数字
     * @return {@link Integer}
     */
    public static Integer parseInteger(final String arg) {
        return StringHelper.parseInteger(arg, null);
    }

    /**
     * {@link String}转{@link Integer}
     *
     * @param arg          字符串数字
     * @param defaultValue 默认值
     * @return {@link Integer}
     */
    public static Integer parseInteger(final String arg, final Integer defaultValue) {
        if (RegexHelper.isInteger(arg)) {
            return Integer.valueOf(arg.replaceAll("_", ""));
        }
        if (RegexHelper.isDecimal(arg)) {
            return Double.valueOf(arg.replaceAll("_", "")).intValue();
        }
        return defaultValue;
    }

    /**
     * {@link String}转{@link Long}
     *
     * @param arg 字符串数字
     * @return {@link Long}
     */
    public static Long parseLong(final String arg) {
        return StringHelper.parseLong(arg, null);
    }

    /**
     * {@link String}转{@link Long}
     *
     * @param arg          字符串数字
     * @param defaultValue 默认值
     * @return {@link Long}
     */
    public static Long parseLong(final String arg, final Long defaultValue) {
        if (RegexHelper.isInteger(arg)) {
            return Long.valueOf(arg.replaceAll("_", ""));
        }
        if (RegexHelper.isDecimal(arg)) {
            return Double.valueOf(arg.replaceAll("_", "")).longValue();
        }
        return defaultValue;
    }

}
