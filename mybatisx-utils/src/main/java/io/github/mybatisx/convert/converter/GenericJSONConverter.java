package io.github.mybatisx.convert.converter;

/**
 * 通用{@link java.util.Map}转换器
 *
 * @param <V> 值类型
 * @param <T> 目标类型
 * @author wvkity
 * @created 2023/4/21
 * @since 1.0.0
 */
@FunctionalInterface
public interface GenericJSONConverter<V, T> extends JSONConvert<String, V, T> {
}
