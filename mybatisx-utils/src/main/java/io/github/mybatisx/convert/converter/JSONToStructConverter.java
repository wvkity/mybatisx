package io.github.mybatisx.convert.converter;

/**
 * {@link java.util.Map} -> 实体转换器
 *
 * @param <T> 目标类型
 * @author wvkity
 * @created 2023/4/21
 * @since 1.0.0
 */
@FunctionalInterface
public interface JSONToStructConverter<T> extends JSONObjectConverter<T> {
}
