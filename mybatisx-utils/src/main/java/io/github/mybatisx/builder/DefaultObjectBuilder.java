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

import io.github.mybatisx.util.Collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 对象构建器
 *
 * @param <T> 待构建对象类型
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
public class DefaultObjectBuilder<T> implements ObjectBuilder<T, DefaultObjectBuilder<T>> {

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

    public DefaultObjectBuilder(Supplier<T> supplier) {
        this.supplier = supplier;
        this.size = 0;
        this.consumers = new ArrayList<>();
    }

    public DefaultObjectBuilder(Supplier<T> supplier, int size) {
        this.supplier = supplier;
        this.size = size;
        this.consumers = new ArrayList<>(size);
    }

    @Override
    public <V> DefaultObjectBuilder<T> with(OneArgConsumer<T, V> consumer, V value) {
        if (Objects.nonNull(consumer)) {
            this.consumers.add(it -> consumer.accept(it, value));
        }
        return this;
    }

    @Override
    public <V> DefaultObjectBuilder<T> with(OneArgConsumer<T, V> consumer, V value, Predicate<V> accept) {
        if (consumer != null && accept.test(value)) {
            this.consumers.add(it -> consumer.accept(it, value));
        }
        return this;
    }

    @Override
    public <V1, V2> DefaultObjectBuilder<T> with(TwoArgConsumer<T, V1, V2> consumer, V1 v1, V2 v2) {
        if (consumer != null) {
            this.consumers.add(it -> consumer.accept(it, v1, v2));
        }
        return this;
    }

    @Override
    public <V1, V2, V3> DefaultObjectBuilder<T> with(ThreeArgConsumer<T, V1, V2, V3> consumer, V1 v1, V2 v2, V3 v3) {
        if (consumer != null) {
            this.consumers.add(it -> consumer.accept(it, v1, v2, v3));
        }
        return this;
    }

    @Override
    public <V1, V2, V3, V4> DefaultObjectBuilder<T> with(FourArgConsumer<T, V1, V2, V3, V4> consumer, V1 v1, V2 v2, V3 v3, V4 v4) {
        if (consumer != null) {
            this.consumers.add(it -> consumer.accept(it, v1, v2, v3, v4));
        }
        return this;
    }

    @Override
    public DefaultObjectBuilder<T> reset() {
        if (!this.consumers.isEmpty()) {
            this.consumers.clear();
            this.consumers = new ArrayList<>(this.size);
        }
        return this;
    }

    @Override
    public T build() {
        final T instance = this.supplier.get();
        if (Collections.isNotEmpty(this.consumers)) {
            this.consumers.forEach(it -> it.accept(instance));
        }
        return instance;
    }

    @Override
    public void release() {
        if (this.consumers != null) {
            this.consumers.clear();
            this.consumers = null;
        }
    }

}
