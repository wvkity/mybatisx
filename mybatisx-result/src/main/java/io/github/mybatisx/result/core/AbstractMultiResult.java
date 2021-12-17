/*
 * Copyright (c) 2022-2023, wvkity(wvkity@gmail.com).
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
package io.github.mybatisx.result.core;

import io.github.mybatisx.lang.Objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 抽象{@link Map}类型响应结果
 *
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public abstract class AbstractMultiResult extends AbstractResult<Map<Object, Object>> implements MapResult {

    @Override
    public boolean isNotEmpty() {
        return Objects.isNotEmpty(this.data);
    }

    @Override
    public Object getObject(Object key) {
        if (Objects.nonNull(key) && this.isNotEmpty()) {
            return this.data.get(key);
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Object key) {
        final Object value = this.getObject(key);
        if (Objects.nonNull(value)) {
            return (T) value;
        }
        return null;
    }

    @Override
    public AbstractMultiResult put(Object key, Object value) {
        if (Objects.nonNull(key)) {
            this.data.put(key, value);
        }
        return this;
    }

    @Override
    public AbstractMultiResult putIfAbsent(Object key, Object value) {
        if (Objects.nonNull(key)) {
            this.data.putIfAbsent(key, value);
        }
        return this;
    }

    @Override
    public AbstractMultiResult putAll(Map<?, ?> data) {
        if (Objects.isNotEmpty(data)) {
            for (Map.Entry<?, ?> entry : data.entrySet()) {
                this.put(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    @Override
    public AbstractMultiResult putAllIfAbsent(Map<?, ?> data) {
        if (Objects.isNotEmpty(data)) {
            for (Map.Entry<?, ?> entry : data.entrySet()) {
                this.putIfAbsent(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    @Override
    public boolean containsKey(Object key) {
        return Objects.nonNull(key) && this.isNotEmpty() && this.data.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.isNotEmpty() && this.data.containsValue(value);
    }

    @Override
    public int getSize() {
        return Objects.size(this.data);
    }

    @Override
    public AbstractMultiResult remove(Object key) {
        if (Objects.nonNull(key) && this.isNotEmpty()) {
            this.data.remove(key);
        }
        return this;
    }

    @Override
    public AbstractMultiResult clear() {
        if (this.isNotEmpty()) {
            this.data.clear();
        }
        return this;
    }

    @Override
    public <T> AbstractMultiResult array(Object key, T... values) {
        return this.put(key, values);
    }

    @Override
    public <T> AbstractMultiResult withArray(Object key, T... values) {
        if (Objects.isNotEmpty(values)) {
            final T[] array = this.getArray(key);
            if (Objects.isNull(array)) {
                this.put(key, values);
            } else {
                final int orgSize = array.length;
                final int addSize = values.length;
                final T[] newArray = Arrays.copyOf(array, orgSize + addSize);
                System.arraycopy(values, 0, newArray, orgSize, addSize);
                this.put(key, newArray);
            }
        }
        return this;
    }

    @Override
    public <T> AbstractMultiResult set(Object key, T... values) {
        if (Objects.isNotEmpty(values)) {
            this.put(key, new HashSet<>(Arrays.asList(values)));
        }
        return this;
    }

    @Override
    public <T> AbstractMultiResult withSet(Object key, T... values) {
        if (Objects.isNotEmpty(values)) {
            this.withSet(key, Arrays.asList(values));
        }
        return this;
    }

    @Override
    public <T> AbstractMultiResult withSet(Object key, Collection<T> values) {
        if (Objects.isNotEmpty(values)) {
            final Set<T> set = this.getSet(key);
            if (Objects.isNull(set)) {
                this.put(key, new HashSet<>(values));
            } else {
                set.addAll(values);
            }
        }
        return this;
    }

    @Override
    public <T> AbstractMultiResult list(Object key, T... values) {
        if (Objects.isNotEmpty(values)) {
            this.put(key, new ArrayList<>(Arrays.asList(values)));
        }
        return this;
    }

    @Override
    public <T> AbstractMultiResult withList(Object key, T... values) {
        if (Objects.isNotEmpty(values)) {
            this.withList(key, Arrays.asList(values));
        }
        return this;
    }

    @Override
    public <T> AbstractMultiResult withList(Object key, Collection<T> values) {
        if (Objects.isNotEmpty(values)) {
            final List<T> list = this.getList(key);
            if (Objects.isNull(list)) {
                this.put(key, new ArrayList<>(values));
            } else {
                list.addAll(values);
            }
        }
        return this;
    }

    @Override
    public <T> AbstractMultiResult map(Object key, Object k, Object v) {
        if (Objects.nonNull(k)) {
            this.put(key, this.newMap(k, v));
        }
        return this;
    }

    @Override
    public <T> AbstractMultiResult withMap(Object key, Object k, Object v) {
        if (Objects.nonNull(k)) {
            final Map<Object, Object> map = this.getMap(key);
            if (Objects.nonNull(map)) {
                map.put(k, v);
            } else {
                this.put(key, this.newMap(k, v));
            }
        }
        return this;
    }

    @Override
    public <T> AbstractMultiResult withMap(Object key, Map<?, ?> values) {
        if (Objects.isNotEmpty(values)) {
            final Map<Object, Object> map = this.getMap(key);
            if (Objects.isNull(map)) {
                this.put(key, values);
            } else {
                map.putAll(values);
            }
        }
        return this;
    }

    @Override
    public <T> AbstractMultiResult mapIfAbsent(Object key, Object k, Object v) {
        if (Objects.nonNull(k)) {
            this.putIfAbsent(key, this.newMap(k, v));
        }
        return this;
    }

    @Override
    public <T> AbstractMultiResult withMapIfAbsent(Object key, Object k, Object v) {
        if (Objects.nonNull(k)) {
            final Map<Object, Object> map = this.getMap(key);
            if (Objects.nonNull(map)) {
                map.putIfAbsent(k, v);
            } else {
                this.putIfAbsent(key, this.newMap(k, v));
            }
        }
        return this;
    }

    @Override
    public <T> AbstractMultiResult withMapIfAbsent(Object key, Map<?, ?> values) {
        if (Objects.isNotEmpty(values)) {
            final Map<Object, Object> map = this.getMap(key);
            if (Objects.isNull(map)) {
                this.putIfAbsent(key, values);
            } else {
                for (Map.Entry<?, ?> entry : values.entrySet()) {
                    final Object k = entry.getKey();
                    if (Objects.nonNull(k)) {
                        map.putIfAbsent(k, entry.getValue());
                    }
                }
            }
        }
        return this;
    }

    Map<Object, Object> newMap(final Object k, final Object v) {
        final Map<Object, Object> map = new HashMap<>();
        map.put(k, v);
        return map;
    }
}
