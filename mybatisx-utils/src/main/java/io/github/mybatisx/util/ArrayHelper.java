package io.github.mybatisx.util;

import io.github.mybatisx.lang.StringHelper;

/**
 * 数组工具类
 *
 * @author wvkity
 * @created 2023/3/22
 * @since 1.0.0
 */
public final class ArrayHelper {

    /**
     * 检查是否为数组类型
     *
     * @param value 值
     * @return boolean
     */
    public static boolean isArray(final Object value) {
        return value.getClass().isArray();
    }

    /**
     * {@code char}数组转{@link String}
     *
     * @param array {@code char}数组
     * @return 字符串
     */
    public static String toString(final char... array) {
        if (array == null) {
            return null;
        }
        return new String(array);
    }

    /**
     * {@code byte}数组转{@link String}
     *
     * @param array {@code byte}数组
     * @return 字符串
     */
    public static String toString(final byte... array) {
        if (array == null) {
            return null;
        }
        final int length = array.length;
        if (length == 0) {
            return StringHelper.EMPTY;
        }
        final StringBuilder sb = new StringBuilder((length << 1) - 1);
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(StringHelper.COMMA);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }

    /**
     * {@code short}数组转{@link String}
     *
     * @param array {@code short}数组
     * @return 字符串
     */
    public static String toString(final short... array) {
        if (array == null) {
            return null;
        }
        final int length = array.length;
        if (length == 0) {
            return StringHelper.EMPTY;
        }
        final StringBuilder sb = new StringBuilder((length << 1) - 1);
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(StringHelper.COMMA);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }

    /**
     * {@code int}数组转{@link String}
     *
     * @param array {@code int}数组
     * @return 字符串
     */
    public static String toString(final int... array) {
        if (array == null) {
            return null;
        }
        final int length = array.length;
        if (length == 0) {
            return StringHelper.EMPTY;
        }
        final StringBuilder sb = new StringBuilder((length << 1) - 1);
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(StringHelper.COMMA);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }

    /**
     * {@code long}数组转{@link String}
     *
     * @param array {@code int}数组
     * @return 字符串
     */
    public static String toString(final long... array) {
        if (array == null) {
            return null;
        }
        final int length = array.length;
        if (length == 0) {
            return StringHelper.EMPTY;
        }
        final StringBuilder sb = new StringBuilder((length << 1) - 1);
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(StringHelper.COMMA);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }

    /**
     * {@code float}数组转{@link String}
     *
     * @param array {@code int}数组
     * @return 字符串
     */
    public static String toString(final float... array) {
        if (array == null) {
            return null;
        }
        final int length = array.length;
        if (length == 0) {
            return StringHelper.EMPTY;
        }
        final StringBuilder sb = new StringBuilder((length << 1) - 1);
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(StringHelper.COMMA);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }

    /**
     * {@code double}数组转{@link String}
     *
     * @param array {@code int}数组
     * @return 字符串
     */
    public static String toString(final double... array) {
        if (array == null) {
            return null;
        }
        final int length = array.length;
        if (length == 0) {
            return StringHelper.EMPTY;
        }
        final StringBuilder sb = new StringBuilder((length << 1) - 1);
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(StringHelper.COMMA);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }

    /**
     * {@code boolean}数组转{@link String}
     *
     * @param array {@code int}数组
     * @return 字符串
     */
    public static String toString(final boolean... array) {
        if (array == null) {
            return null;
        }
        final int length = array.length;
        if (length == 0) {
            return StringHelper.EMPTY;
        }
        final StringBuilder sb = new StringBuilder((length << 1) - 1);
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(StringHelper.COMMA);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }

    /**
     * {@link String}数组转{@link String}
     *
     * @param array {@code int}数组
     * @return 字符串
     */
    public static String toString(final String... array) {
        if (array == null) {
            return null;
        }
        final int length = array.length;
        if (length == 0) {
            return StringHelper.EMPTY;
        }
        final StringBuilder sb = new StringBuilder((length << 1) - 1);
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(StringHelper.COMMA);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }

    /**
     * {@link Object}数组转{@link String}
     *
     * @param array {@code int}数组
     * @return 字符串
     */
    public static String toString(final Object[] array) {
        if (array == null) {
            return null;
        }
        final int length = array.length;
        if (length == 0) {
            return StringHelper.EMPTY;
        }
        final StringBuilder sb = new StringBuilder((length << 1) - 1);
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(StringHelper.COMMA);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }
}
