package io.github.mybatisx.builder;

import java.util.function.Supplier;

/**
 * 构建器工厂
 *
 * @author wvkity
 * @created 2023/3/11
 * @since 1.0.0
 */
public abstract class BuilderFactory {

    /**
     * 创建{@link DefaultObjectBuilder}对象
     *
     * @param supplier {@link Supplier}对象
     * @param <T>      对象类型
     * @return {@link DefaultObjectBuilder}对象
     */
    public static <T> DefaultObjectBuilder<T> create(final Supplier<T> supplier) {
        return new DefaultObjectBuilder<>(supplier);
    }

    /**
     * 创建{@link DefaultObjectBuilder}对象
     *
     * @param supplier {@link Supplier}对象
     * @param size     {@link java.util.function.Consumer}数量
     * @param <T>      对象类型
     * @return {@link DefaultObjectBuilder}对象
     */
    public static <T> DefaultObjectBuilder<T> create(final Supplier<T> supplier, final int size) {
        return new DefaultObjectBuilder<>(supplier, size);
    }
}
