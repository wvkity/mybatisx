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
package io.github.mybatisx.result.core;

import io.github.mybatisx.result.DataResult;

import java.util.Collection;
import java.util.Map;

/**
 * {@link Map}类型响应结果
 *
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
public interface MapResult extends DataResult<Map<Object, Object>>, VAware {

    /**
     * 获取值
     *
     * @param key 键
     * @param <T> 值类型
     * @return 值
     */
    <T> T get(final Object key);

    /**
     * 添加值
     *
     * @param key   键
     * @param value 值
     * @return {@code this}
     */
    MapResult put(final Object key, final Object value);

    /**
     * 添加值
     *
     * @param key   键
     * @param value 值
     * @return {code this}
     */
    MapResult putIfAbsent(final Object key, final Object value);

    /**
     * 添加多个值
     *
     * @param data 多个值
     * @return {@code this}
     */
    MapResult putAll(final Map<?, ?> data);

    /**
     * 添加多个值
     *
     * @param data 多个值
     * @return {@code this}
     */
    MapResult putAllIfAbsent(final Map<?, ?> data);

    /**
     * 检查是否包含指定键
     *
     * @param key 指定键
     * @return boolean
     */
    boolean containsKey(final Object key);

    /**
     * 检查是否包含指定值
     *
     * @param value 指定值
     * @return boolean
     */
    boolean containsValue(final Object value);

    /**
     * 获取元素个数
     *
     * @return 元素个数
     */
    int getSize();

    /**
     * 移除元素
     *
     * @param key 键
     * @return {@code this}
     */
    MapResult remove(final Object key);

    /**
     * 清空元素
     *
     * @return {@code this}
     */
    MapResult clear();

    /**
     * 添加数组元素
     *
     * @param key    键
     * @param values 元素列表
     * @param <T>    值类型
     * @return {@code this}
     */
    <T> MapResult array(final Object key, final T... values);

    /**
     * 追加元素到指定数组
     *
     * @param key    键
     * @param values 元素列表
     * @param <T>    值类型
     * @return {@code this}
     */
    <T> MapResult withArray(final Object key, final T... values);

    /**
     * 添加{@link java.util.Set}元素
     *
     * @param key    键
     * @param values 元素列表
     * @param <T>    值类型
     * @return {@code this}
     */
    <T> MapResult set(final Object key, final T... values);

    /**
     * 追加元素到指定的{@link java.util.Set}集合
     *
     * @param key    键
     * @param values 元素列表
     * @param <T>    值类型
     * @return {@code this}
     */
    <T> MapResult withSet(final Object key, final T... values);

    /**
     * 追加元素到指定的{@link java.util.Set}集合
     *
     * @param key    键
     * @param values 元素列表
     * @param <T>    值类型
     * @return {@code this}
     */
    <T> MapResult withSet(final Object key, final Collection<T> values);

    /**
     * 添加{@link java.util.List}元素
     *
     * @param key    键
     * @param values 元素列表
     * @param <T>    值类型
     * @return {@code this}
     */
    <T> MapResult list(final Object key, final T... values);

    /**
     * 追加元素到指定的{@link java.util.List}集合
     *
     * @param key    键
     * @param values 元素列表
     * @param <T>    值类型
     * @return {@code this}
     */
    <T> MapResult withList(final Object key, final T... values);

    /**
     * 追加元素到指定的{@link java.util.List}集合
     *
     * @param key    键
     * @param values 元素列表
     * @param <T>    值类型
     * @return {@code this}
     */
    <T> MapResult withList(final Object key, final Collection<T> values);

    /**
     * 添加{@link java.util.Map}元素
     *
     * @param key 键
     * @param k   键
     * @param v   值
     * @param <T> 值类型
     * @return {@code this}
     */
    <T> MapResult map(final Object key, final Object k, final Object v);

    /**
     * 追加元素到指定的{@link java.util.Map}集合
     *
     * @param key 键
     * @param k   键
     * @param v   值
     * @param <T> 值类型
     * @return {@code this}
     */
    <T> MapResult withMap(final Object key, final Object k, final Object v);

    /**
     * 追加元素到指定的{@link java.util.Map}集合
     *
     * @param key    键
     * @param values 元素集合
     * @param <T>    值类型
     * @return {@code this}
     */
    <T> MapResult withMap(final Object key, final Map<?, ?> values);

    /**
     * 添加{@link java.util.Map}元素
     *
     * @param key 键
     * @param k   键
     * @param v   值
     * @param <T> 值类型
     * @return {@code this}
     */
    <T> MapResult mapIfAbsent(final Object key, final Object k, final Object v);

    /**
     * 追加元素到指定的{@link java.util.Map}集合
     *
     * @param key 键
     * @param k   键
     * @param v   值
     * @param <T> 值类型
     * @return {@code this}
     */
    <T> MapResult withMapIfAbsent(final Object key, final Object k, final Object v);

    /**
     * 追加元素到指定的{@link java.util.Map}集合
     *
     * @param key    键
     * @param values 元素集合
     * @param <T>    值类型
     * @return {@code this}
     */
    <T> MapResult withMapIfAbsent(final Object key, final Map<?, ?> values);

}
