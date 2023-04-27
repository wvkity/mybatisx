package io.github.mybatisx.convert.converter;

import java.util.List;

/**
 * 列表转换器
 *
 * @param <S> 源类型
 * @param <T> 目标类型
 * @author wvkity
 * @created 2023/4/27
 * @since 1.0.0
 */
public interface ArrayConverter<S, T> extends Converter<List<S>, List<T>> {
}
