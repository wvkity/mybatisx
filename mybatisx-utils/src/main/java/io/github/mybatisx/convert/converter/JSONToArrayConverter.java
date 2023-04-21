package io.github.mybatisx.convert.converter;

import java.util.List;

/**
 * {@link java.util.Map} -> {@link List}转换器
 *
 * @param <T> 目标类型
 * @author wvkity
 * @created 2023/4/21
 * @since 1.0.0
 */
@FunctionalInterface
public interface JSONToArrayConverter<T> extends JSONObjectConverter<List<T>> {
}
