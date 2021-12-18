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

import java.math.BigDecimal;
import java.util.regex.Matcher;

/**
 * 类型工具
 *
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
public final class Types {

    private Types() {
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
