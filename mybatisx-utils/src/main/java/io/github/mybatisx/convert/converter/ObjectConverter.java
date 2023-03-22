package io.github.mybatisx.convert.converter;

/**
 * {@link Object} -> {@code T}类型转换器
 *
 * @param <T> 目标类型
 * @author wvkity
 * @created 2023/3/22
 * @since 1.0.0
 */
@FunctionalInterface
public interface ObjectConverter<T> extends Converter<Object, T> {

    /**
     * 检查值类型是否为目标类型
     *
     * @param clazz  目标类
     * @param source 值
     * @return boolean
     */
    default boolean isTargetType(final Class<?> clazz, final Object source) {
        return clazz.isAssignableFrom(source.getClass());
    }
}
