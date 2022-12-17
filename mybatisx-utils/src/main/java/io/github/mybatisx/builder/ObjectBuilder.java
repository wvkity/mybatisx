/*
 * Copyright (c) 2021-Now, wvkity(wvkity@gmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.mybatisx.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 对象构建器
 *
 * @param <T> 待构建对象类型
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
public class ObjectBuilder<T> implements Builder<T> {

    /**
     * {@link Supplier}对象
     */
    private final Supplier<T> supplier;
    /**
     * {@link Consumer}数量
     */
    private final int size;
    /**
     * {@link Consumer}列表
     */
    private List<Consumer<T>> consumers;

    public ObjectBuilder(Supplier<T> supplier) {
        this.supplier = supplier;
        this.size = 0;
        this.consumers = new ArrayList<>();
    }

    public ObjectBuilder(Supplier<T> supplier, int size) {
        this.supplier = supplier;
        this.size = size;
        this.consumers = new ArrayList<>(size);
    }

    @Override
    public <V> ObjectBuilder<T> with(SetterConsumer<T, V> setter, V value) {
        if (Objects.nonNull(setter)) {
            this.consumers.add(it -> setter.accept(it, value));
        }
        return this;
    }

    @Override
    public void reset() {
        if (!this.consumers.isEmpty()) {
            this.consumers.clear();
            this.consumers = new ArrayList<>(this.size);
        }
    }

    @Override
    public T build() {
        final T instance = this.supplier.get();
        if (!this.consumers.isEmpty()) {
            this.consumers.forEach(it -> it.accept(instance));
        }
        return instance;
    }

    /**
     * 创建构建器
     *
     * @param supplier {@link Supplier}
     * @param <T>      目标对象类型
     * @return {@link ObjectBuilder}
     */
    public static <T> ObjectBuilder<T> of(final Supplier<T> supplier) {
        return new ObjectBuilder<>(supplier);
    }

    /**
     * 创建构建器
     *
     * @param supplier {@link Supplier}
     * @param size     {@link Consumer}数目
     * @param <T>      目标对象类型
     * @return {@link ObjectBuilder}
     */
    public static <T> ObjectBuilder<T> of(final Supplier<T> supplier, final int size) {
        return new ObjectBuilder<>(supplier, size);
    }

}
