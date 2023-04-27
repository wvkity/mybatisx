package io.github.mybatisx.convert.converter;

/**
 * {@link Object} -> {@code T}类型转换器
 *
 * @param <T> 目标类型
 * @author wvkity
 * @created 2023/4/27
 * @since 1.0.0
 */
public interface ObjectToStructConverter<T> extends GenericConverter<T> {

    @Override
    default boolean isTargetType(final Object source) {
        return true;
    }
}
