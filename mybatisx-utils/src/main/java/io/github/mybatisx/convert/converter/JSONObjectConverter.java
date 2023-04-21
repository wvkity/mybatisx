package io.github.mybatisx.convert.converter;

/**
 * {@link java.util.Map}转换器
 *
 * @param <T> 目标类型
 * @author wvkity
 * @created 2023/4/21
 * @since 1.0.0
 */
@FunctionalInterface
public interface JSONObjectConverter<T> extends GenericJSONConverter<Object, T> {
}
