package io.github.mybatisx.convert.converter;

/**
 * 相同类型值转换
 *
 * @param <T> 目标类型
 * @author wvkity
 * @created 2023/4/21
 * @since 1.0.0
 */
@FunctionalInterface
public interface InvariantConverter<T> extends Converter<T, T> {
}
