package io.github.mybatisx.convert.converter;

/**
 * 转换器
 *
 * @param <S> 源类型
 * @param <T> 目标类型
 * @author wvkity
 * @created 2021/12/28
 * @since 1.0.0
 */
@FunctionalInterface
public interface Converter<S, T> {

    /**
     * 将类型为{@code S}的源对象转换成目标类型{@code T}
     *
     * @param source 源对象
     * @return 目标对象
     */
    T convert(final S source);

    default T apply(S s) {
        return this.convert(s);
    }

    default <V> Converter<S, V> andThen(Converter<? super T, ? extends V> after) {
        if (after == null) {
            throw new IllegalArgumentException("'after' Converter must not be null");
        }
        return (S s) -> {
            final T result = this.convert(s);
            return (result != null ? after.convert(result) : null);
        };
    }
}
