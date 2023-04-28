package io.github.mybatisx.convert.converter;

import java.util.List;

/**
 * {@link Object} -> {@link List}转换器
 *
 * @param <T> 目标类型
 * @author wvkity
 * @created 2023/4/27
 * @since 1.0.0
 */
public interface ObjectToArrayConverter<T> extends Converter<Object, List<T>> {
}
