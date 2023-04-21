package io.github.mybatisx.convert.converter;

import io.github.mybatisx.lang.StringHelper;
import io.github.mybatisx.util.ArrayHelper;

/**
 * 通用类型转换器
 *
 * @param <T> 目标对象类型
 * @author wvkity
 * @created 2023/3/22
 * @since 1.0.0
 */
public interface GenericConverter<T> extends ObjectConverter<T> {

    /**
     * 检查值类型是否为目标类型
     *
     * @param source 值
     * @return boolean
     */
    boolean isTargetType(final Object source);

    /**
     * 是否为{@link String}类型
     *
     * @param source 值
     * @return boolean
     */
    default boolean isStringType(final Object source) {
        return this.isTargetType(CharSequence.class, source);
    }

    /**
     * 是否为{@link Boolean}类型
     *
     * @param source 值
     * @return boolean
     */
    default boolean isBooleanType(final Object source) {
        return this.isTargetType(Boolean.class, source);
    }

    /**
     * 是否为{@link Integer}类型
     *
     * @param source 值
     * @return boolean
     */
    default boolean isIntType(final Object source) {
        return this.isTargetType(Integer.class, source);
    }

    /**
     * 是否为{@link Long}类型
     *
     * @param source 值
     * @return boolean
     */
    default boolean isLongType(final Object source) {
        return this.isTargetType(Long.class, source);
    }

    @Override
    default boolean isTargetType(final Class<?> clazz, final Object source) {
        return clazz == source.getClass();
    }

    /**
     * {@link Object}转{@link String}
     *
     * @param source 值
     * @return {@link String}
     */
    default String convertToString(final Object source) {
        if (source != null) {
            if (this.isTargetType(source)) {
                return source.toString();
            }
            final Class<?> type = source.getClass();
            if (type.isArray()) {
                if (type == byte[].class) {
                    return ArrayHelper.toString((byte[]) source);
                }
                if (type == char[].class) {
                    return ArrayHelper.toString((char[]) source);
                }
                if (type == boolean[].class) {
                    return ArrayHelper.toString((boolean[]) source);
                }
                if (type == short[].class) {
                    return ArrayHelper.toString((short[]) source);
                }
                if (type == int[].class) {
                    return ArrayHelper.toString((int[]) source);
                }
                if (type == long[].class) {
                    return ArrayHelper.toString((long[]) source);
                }
                if (type == float[].class) {
                    return ArrayHelper.toString((float[]) source);
                }
                if (type == double[].class) {
                    return ArrayHelper.toString((double[]) source);
                }
                return ArrayHelper.toString((Object[]) source);
            }
            return source.toString();
        }
        return null;
    }

    /**
     * {@link Object}转{@link Boolean}
     *
     * @param source 值
     * @return {@link Boolean}
     */
    default Boolean convertToBoolean(final Object source) {
        if (source != null) {
            if (this.isTargetType(source)) {
                return (Boolean) source;
            }
            if (source instanceof Number) {
                return ((Number) source).doubleValue() != 0;
            }
            return StringHelper.parseBoolean(source.toString());
        }
        return null;
    }

    /**
     * {@link Object}转{@link Integer}
     *
     * @param source 值
     * @return {@link Integer}
     */
    default Integer convertToInteger(final Object source) {
        if (source != null) {
            if (this.isTargetType(source)) {
                return (Integer) source;
            }
            if (source instanceof Number) {
                return ((Number) source).intValue();
            }
            if (source instanceof Boolean) {
                return ((Boolean) source) ? 1 : 0;
            }
            return StringHelper.parseInteger(source.toString());
        }
        return null;
    }

    /**
     * {@link Object}转{@link Long}
     *
     * @param source 值
     * @return {@link Long}
     */
    default Long convertToLong(final Object source) {
        if (source != null) {
            if (this.isTargetType(source)) {
                return (Long) source;
            }
            if (source instanceof Number) {
                return ((Number) source).longValue();
            }
            if (source instanceof Boolean) {
                return ((Boolean) source) ? 1L : 0L;
            }
            return StringHelper.parseLong(source.toString());
        }
        return null;
    }
}
