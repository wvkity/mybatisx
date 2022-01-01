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
package io.github.mybatisx.reflect;

import io.github.mybatisx.lang.Objects;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 注解元数据接口
 *
 * @author wvkity
 * @created 2021/12/26
 * @since 1.0.0
 */
public interface Metadata {

    /**
     * value属性
     */
    String DEFAULT_VALUE_PROP = "value";

    /**
     * 获取字符串类型值
     *
     * @return 字符串值
     */
    default boolean boolValue() {
        return this.boolValue(DEFAULT_VALUE_PROP);
    }

    /**
     * 获取字符串类型值
     *
     * @param property 属性
     * @return 字符串值
     */
    default boolean boolValue(final String property) {
        return this.boolValue(property, false);
    }

    /**
     * 获取字符串类型值
     *
     * @param property     属性
     * @param defaultValue 默认值
     * @return 字符串值
     */
    default boolean boolValue(final String property, final boolean defaultValue) {
        return this.getValue(property, boolean.class, defaultValue);
    }

    /**
     * 获取字符串类型值
     *
     * @return 字符串值
     */
    default int intValue() {
        return this.intValue(DEFAULT_VALUE_PROP);
    }

    /**
     * 获取字符串类型值
     *
     * @param property 属性
     * @return 字符串值
     */
    default int intValue(final String property) {
        return this.intValue(property, 0);
    }

    /**
     * 获取字符串类型值
     *
     * @param property     属性
     * @param defaultValue 默认值
     * @return 字符串值
     */
    default int intValue(final String property, final int defaultValue) {
        return this.getValue(property, int.class, defaultValue);
    }

    /**
     * 获取字符串类型值
     *
     * @return 字符串值
     */
    default long longValue() {
        return this.longValue(DEFAULT_VALUE_PROP);
    }

    /**
     * 获取字符串类型值
     *
     * @param property 属性
     * @return 字符串值
     */
    default long longValue(final String property) {
        return this.longValue(property, 0L);
    }

    /**
     * 获取字符串类型值
     *
     * @param property     属性
     * @param defaultValue 默认值
     * @return 字符串值
     */
    default long longValue(final String property, final long defaultValue) {
        return this.getValue(property, long.class, defaultValue);
    }

    /**
     * 获取字符串类型值
     *
     * @return 字符串值
     */
    default String stringValue() {
        return this.stringValue(DEFAULT_VALUE_PROP);
    }

    /**
     * 获取字符串类型值
     *
     * @param property 属性
     * @return 字符串值
     */
    default String stringValue(final String property) {
        return this.stringValue(property, "");
    }

    /**
     * 获取字符串类型值
     *
     * @param property     属性
     * @param defaultValue 默认值
     * @return 字符串值
     */
    default String stringValue(final String property, final String defaultValue) {
        return this.getValue(property, String.class, defaultValue);
    }

    /**
     * 获取枚举类值
     *
     * @return 值
     */
    default Enum<? extends Enum<?>> enumValue() {
        return this.enumValue(DEFAULT_VALUE_PROP);
    }

    /**
     * 获取枚举类值
     *
     * @param property 属性
     * @return 值
     */
    @SuppressWarnings({"unchecked"})
    default Enum<? extends Enum<?>> enumValue(final String property) {
        return this.getValue(property, Enum.class, null);
    }

    /**
     * 获取枚举类值
     *
     * @param property     属性
     * @param defaultValue 默认值
     * @return 值
     */
    @SuppressWarnings({"unchecked"})
    default Enum<? extends Enum<?>> enumValue(final String property, final Enum<? extends Enum<?>> defaultValue) {
        return this.getValue(property, Enum.class, defaultValue);
    }

    /**
     * 获取值
     *
     * @param clazz 类型
     * @param <T>   类型
     * @return 值
     */
    default <T> T getValue(final Class<T> clazz) {
        return this.getValue(DEFAULT_VALUE_PROP, clazz);
    }

    /**
     * 获取值
     *
     * @param property 属性
     * @param clazz    类型
     * @param <T>      类型
     * @return 值
     */
    default <T> T getValue(final String property, final Class<T> clazz) {
        return this.getValue(property, clazz, null);
    }

    /**
     * 检查指定注解属性是否存在
     *
     * @param property 属性
     * @return boolean
     */
    default boolean containsKey(final String property) {
        return !this.isEmpty() && this.getData().containsKey(property);
    }

    /**
     * 是否存在数据
     *
     * @return boolean
     */
    default boolean isEmpty() {
        return Objects.isEmpty(this.getData());
    }

    /**
     * 获取注解实例
     *
     * @param <T> 注解类型
     * @return 注解实例
     */
    <T extends Annotation> T getAnnotation();

    /**
     * 获取注解数据
     *
     * @return 注解数据
     */
    Map<String, Object> getData();

    /**
     * 获取值
     *
     * @param property     属性
     * @param clazz        类型
     * @param defaultValue 默认值
     * @param <T>          类型
     * @return 值
     */
    <T> T getValue(final String property, final Class<T> clazz, final T defaultValue);

}
