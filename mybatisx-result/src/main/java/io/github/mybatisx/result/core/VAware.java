/*
 * Copyright (c) 2021-2023, wvkity(wvkity@gmail.com).
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
import io.github.mybatisx.lang.Types;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 取值接口
 *
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
public interface VAware extends Serializable {

    /**
     * 获取指定类型值
     *
     * @param key   键
     * @param clazz 值类型
     * @param <T>   值类型
     * @return 值
     */
    @SuppressWarnings("unchecked")
    default <T> T get(final Object key, final Class<T> clazz) {
        if (Objects.nonNull(clazz)) {
            final Object value = this.getObject(key);
            if (Objects.is(clazz, value)) {
                return (T) value;
            }
        }
        return null;
    }

    /**
     * 获取数组值
     *
     * @param key 键
     * @param <T> 值类型
     * @return 数组
     */
    @SuppressWarnings("unchecked")
    default <T> T[] getArray(final Object key) {
        final Object value = this.getObject(key);
        if (Objects.isArray(value)) {
            return (T[]) value;
        }
        return null;
    }

    /**
     * 获取{@link Set}集合
     *
     * @param key 键
     * @param <T> 值类型
     * @return {@link Set}集合
     */
    @SuppressWarnings("unchecked")
    default <T> Set<T> getSet(final Object key) {
        final Object value = this.getObject(key);
        if (Objects.isSet(value)) {
            return (Set<T>) value;
        }
        return null;
    }

    /**
     * 获取{@link List}集合
     *
     * @param key 键
     * @param <T> 值类型
     * @return {@link List}集合
     */
    @SuppressWarnings("unchecked")
    default <T> List<T> getList(final Object key) {
        final Object value = this.getObject(key);
        if (Objects.isList(value)) {
            return (List<T>) value;
        }
        return null;
    }

    /**
     * 获取{@link Collection}集合
     *
     * @param key 键
     * @param <T> 值类型
     * @return {@link Collection}集合
     */
    @SuppressWarnings("unchecked")
    default <T> Collection<T> getCollection(final Object key) {
        final Object value = this.getObject(key);
        if (Objects.isCollection(value)) {
            return (Collection<T>) value;
        }
        return null;
    }

    /**
     * 获取{@link Map}集合
     *
     * @param key 键
     * @return {@link Map}集合
     */
    @SuppressWarnings("unchecked")
    default Map<Object, Object> getMap(final Object key) {
        final Object value = this.getObject(key);
        if (Objects.isMap(value)) {
            return (Map<Object, Object>) value;
        }
        return null;
    }

    /**
     * 获取{@link String}值
     *
     * @param key 键
     * @return {@link String}
     */
    default String getString(final Object key) {
        return Types.toString(this.getObject(key));
    }

    /**
     * 获取{@link Character}值
     *
     * @param key 键
     * @return {@link Character}
     */
    default Character getChar(final Object key) {
        return Types.toChar(this.getObject(key));
    }

    /**
     * 获取{@link Boolean}值
     *
     * @param key 键
     * @return {@link Boolean}
     */
    default Boolean getBoolean(final Object key) {
        return Types.toBoolean(this.getObject(key));
    }

    /**
     * 获取boolean值
     *
     * @param key 键
     * @return boolean
     */
    default boolean booleanValue(final Object key) {
        final Boolean value = this.getBoolean(key);
        return !Objects.isNull(value) && value;
    }

    /**
     * 获取{@link Short}值
     *
     * @param key 键
     * @return {@link Short}
     */
    default Short getShort(final Object key) {
        return Types.toShort(this.getObject(key));
    }

    /**
     * 获取short值
     *
     * @param key 键
     * @return short值
     */
    default short shortValue(final Object key) {
        final Short value = this.getShort(key);
        return Objects.isNull(value) ? 0 : value;
    }

    /**
     * 获取{@link Integer}值
     *
     * @param key 键
     * @return {@link Integer}
     */
    default Integer getInt(final Object key) {
        return Types.toInt(this.getObject(key));
    }

    /**
     * 获取int值
     *
     * @param key 键
     * @return int值
     */
    default int intValue(final Object key) {
        final Integer value = this.getInt(key);
        return Objects.isNull(value) ? 0 : value;
    }

    /**
     * 获取{@link Long}值
     *
     * @param key 键
     * @return {@link Long}
     */
    default Long getLong(final Object key) {
        return Types.toLong(this.getObject(key));
    }

    /**
     * 获取long值
     *
     * @param key 键
     * @return long值
     */
    default long longValue(final Object key) {
        final Long value = this.getLong(key);
        return Objects.isNull(value) ? 0L : value;
    }

    /**
     * 获取{@link Float}值
     *
     * @param key 键
     * @return {@link Float}
     */
    default Float getFloat(final Object key) {
        return Types.toFloat(this.getObject(key));
    }

    /**
     * 获取float值
     *
     * @param key 键
     * @return float值
     */
    default float floatValue(final Object key) {
        final Float value = this.getFloat(key);
        return Objects.isNull(value) ? 0.f : value;
    }

    /**
     * 获取{@link Double}值
     *
     * @param key 键
     * @return {@link Double}
     */
    default Double getDouble(final Object key) {
        return Types.toDouble(this.getObject(key));
    }

    /**
     * 获取double值
     *
     * @param key 键
     * @return double值
     */
    default double doubleValue(final Object key) {
        final Double value = this.getDouble(key);
        return Objects.isNull(value) ? 0.d : value;
    }

    /**
     * 获取值
     *
     * @param key 键
     * @return 值
     */
    Object getObject(final Object key);

}
