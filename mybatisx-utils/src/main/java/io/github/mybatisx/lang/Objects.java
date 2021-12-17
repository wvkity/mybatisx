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
package io.github.mybatisx.lang;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Objects工具
 *
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
public final class Objects {

    private Objects() {
    }

    /**
     * 检查对象是否为null
     *
     * @param arg 待检查对象
     * @return boolean
     */
    public static boolean isNull(final Object arg) {
        return arg == null;
    }

    /**
     * 检查对象是否不为null
     *
     * @param arg 待检查对象
     * @return boolean
     */
    public static boolean nonNull(final Object arg) {
        return arg != null;
    }

    /**
     * 检查两个类是否为is关系
     *
     * @param source 源类
     * @param clazz  目标类
     * @return boolean
     */
    public static boolean is(final Class<?> source, final Class<?> clazz) {
        return Objects.nonNull(source) && Objects.nonNull(clazz) && source.isAssignableFrom(clazz);
    }

    /**
     * 检查目标对象是否为is关系
     *
     * @param source 源类
     * @param arg    目标对象
     * @return boolean
     */
    public static boolean is(final Class<?> source, final Object arg) {
        return Objects.nonNull(arg) && is(source, arg.getClass());
    }

    /**
     * 检查对象是否为数组类型
     *
     * @param arg 待检查对象
     * @return boolean
     */
    public static boolean isArray(final Object arg) {
        return nonNull(arg) && arg.getClass().isArray();
    }

    /**
     * 检查对象是否为{@link Collection}类型
     *
     * @param arg 目标对象
     * @return boolean
     */
    public static boolean isCollection(final Object arg) {
        return is(Collection.class, arg);
    }

    /**
     * 检查对象是否为{@link Set}类型
     *
     * @param arg 目标对象
     * @return boolean
     */
    public static boolean isSet(final Object arg) {
        return is(Set.class, arg);
    }

    /**
     * 检查对象是否为{@link List}类型
     *
     * @param arg 目标对象
     * @return boolean
     */
    public static boolean isList(final Object arg) {
        return is(List.class, arg);
    }

    /**
     * 检查对象是否为{@link Map}类型
     *
     * @param arg 目标对象
     * @return boolean
     */
    public static boolean isMap(final Object arg) {
        return is(Map.class, arg);
    }

    /**
     * 检查数组对象是否为空
     *
     * @param arg 待检查数组对象
     * @return boolean
     */
    public static boolean isEmpty(final Object[] arg) {
        return isNull(arg) || arg.length == 0;
    }

    /**
     * 检查集合是否为空
     *
     * @param arg 待检查集合
     * @return boolean
     */
    public static boolean isEmpty(final Collection<?> arg) {
        return isNull(arg) || arg.isEmpty();
    }

    /**
     * 检查Map集合是否为空
     *
     * @param arg 待检查Map集合
     * @return boolean
     */
    public static boolean isEmpty(final Map<?, ?> arg) {
        return isNull(arg) || arg.isEmpty();
    }

    /**
     * 检查数组对象是否不为空
     *
     * @param arg 待检查数组对象
     * @return boolean
     */
    public static boolean isNotEmpty(final Object[] arg) {
        return !isEmpty(arg);
    }

    /**
     * 检查集合是否不为空
     *
     * @param arg 待检查集合
     * @return boolean
     */
    public static boolean isNotEmpty(final Collection<?> arg) {
        return !isEmpty(arg);
    }

    /**
     * 检查Map集合是否不为空
     *
     * @param arg 待检查Map集合
     * @return boolean
     */
    public static boolean isNotEmpty(final Map<?, ?> arg) {
        return !isEmpty(arg);
    }

    /**
     * 获取数组长度
     *
     * @param arg 数组对象
     * @return 数组长度
     */
    public static int size(final Object[] arg) {
        if (isNotEmpty(arg)) {
            return arg.length;
        }
        return 0;
    }

    /**
     * 获取集合元素个数
     *
     * @param arg 集合
     * @return 集合元素个数
     */
    public static int size(final Collection<?> arg) {
        if (isNotEmpty(arg)) {
            return arg.size();
        }
        return 0;
    }

    /**
     * 获取Map集合元素个数
     *
     * @param arg Map集合
     * @return 集合元素个数
     */
    public static int size(final Map<?, ?> arg) {
        if (isNotEmpty(arg)) {
            return arg.size();
        }
        return 0;
    }

}
