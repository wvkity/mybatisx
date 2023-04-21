package io.github.mybatisx.builder;

import java.util.function.Predicate;

/**
 * 多值构建器
 *
 * @param <T> 对象类型
 * @param <C> 子类
 * @author wvkity
 * @created 2023/3/11
 * @since 1.0.0
 */
public interface ObjectBuilder<T, C extends ObjectBuilder<T, C>> extends Builder<T> {

    /**
     * 设置值
     *
     * @param consumer {@link OneArgConsumer}
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    <V> C with(final OneArgConsumer<T, V> consumer, final V value);

    /**
     * 设置值
     *
     * @param consumer {@link OneArgConsumer}
     * @param value    值
     * @param accept   {@link Predicate}
     * @param <V>      值类型
     * @return {@code this}
     */
    <V> C with(final OneArgConsumer<T, V> consumer, final V value, final Predicate<V> accept);

    /**
     * 设置值
     *
     * @param consumer {@link ThreeArgConsumer}
     * @param v1       值1
     * @param v2       值2
     * @param <V1>     值类型
     * @param <V2>     值类型
     * @return {@code this}
     */
    <V1, V2> C with(final TwoArgConsumer<T, V1, V2> consumer, final V1 v1, final V2 v2);

    /**
     * 设置值
     *
     * @param consumer {@link ThreeArgConsumer}
     * @param v1       值1
     * @param v2       值2
     * @param v3       值3
     * @param <V1>     值类型
     * @param <V2>     值类型
     * @param <V3>     值类型
     * @return {@code this}
     */
    <V1, V2, V3> C with(final ThreeArgConsumer<T, V1, V2, V3> consumer, final V1 v1, final V2 v2, final V3 v3);

    /**
     * 设置值
     *
     * @param consumer {@link ThreeArgConsumer}
     * @param v1       值1
     * @param v2       值2
     * @param v3       值3
     * @param <V1>     值类型
     * @param <V2>     值类型
     * @param <V3>     值类型
     * @param <V4>     值类型
     * @return {@code this}
     */
    <V1, V2, V3, V4> C with(final FourArgConsumer<T, V1, V2, V3, V4> consumer, final V1 v1, final V2 v2, final V3 v3, final V4 v4);

    /**
     * 重置
     * {@code this}
     */
    C reset();

    /**
     * 释放资源
     */
    default void release() {
        // Empty
    }
}
