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

/**
 * 字符串工具
 *
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
public final class Strings {

    private Strings() {
    }

    public static final String DEFAULT_STR_NULL = "null";
    public static final String DEFAULT_STR_EMPTY = "";
    public static final String DEFAULT_STR_ZERO = "0";
    public static final String DEFAULT_STR_ONE = "1";
    public static final String DEFAULT_STR_FALSE = "false";
    public static final String DEFAULT_STR_F = "F";
    public static final String DEFAULT_STR_N = "N";
    public static final String DEFAULT_STR_TRUE = "true";
    public static final String DEFAULT_STR_Y = "Y";
    public static final String DEFAULT_STR_T = "T";
    public static final String DEFAULT_STR_COMMA = ",";

    /**
     * 对象转字符串
     *
     * @param arg 对象
     * @return 字符串
     */
    public static String toString(final Object arg) {
        if (Objects.isNull(arg)) {
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
        return Objects.isNull(arg) || arg.length() == 0;
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
        return isEmpty(arg) || DEFAULT_STR_NULL.equalsIgnoreCase(arg.toString());
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
        if (Objects.isAssignable(String.class, arg)) {
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
        return Objects.nonNull(a) && size(a) == size(b) && a.equals(b);
    }

    /**
     * 忽略大小写比较两个字符串是否一致
     *
     * @param a 字符串a
     * @param b 字符串b
     * @return boolean
     */
    public static boolean equalsIC(final String a, final String b) {
        return Objects.nonNull(a) && size(a) == size(b) && a.equalsIgnoreCase(b);
    }

    /**
     * 检查字符串是否表示true
     *
     * @param arg 字符串
     * @return boolean
     */
    public static boolean isTrue(final String arg) {
        return equalsIC(DEFAULT_STR_TRUE, arg) || equalsIC(DEFAULT_STR_Y, arg) || equalsIC(DEFAULT_STR_T, arg)
                || equals(DEFAULT_STR_ONE, arg);
    }

    /**
     * 检查字符串是否表示false
     *
     * @param arg 字符串
     * @return boolean
     */
    public static boolean isFalse(final String arg) {
        return equalsIC(DEFAULT_STR_FALSE, arg) || equalsIC(DEFAULT_STR_N, arg) || equalsIC(DEFAULT_STR_F, arg)
                || equals(DEFAULT_STR_ZERO, arg);
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
        if (Regex.isInteger(arg)) {
            return Integer.parseInt(arg);
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
        if (Regex.isPositiveInteger(arg)) {
            return Integer.parseInt(arg);
        }
        return defaultValue;
    }

}
