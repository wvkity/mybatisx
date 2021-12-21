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

import com.google.common.collect.ImmutableSet;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Types;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 反射工具
 *
 * @author wvkity
 * @created 2021/12/21
 * @since 1.0.0
 */
public final class Reflections {

    private Reflections() {
    }

    /**
     * CGLIB代理类
     */
    public static final String PROXY_CGLIB = "net.sf.cglib.proxy.Factory";
    /**
     * JAVASSIST代理类
     */
    public static final String PROXY_JAVASSIST = "javassist.util.proxy.ProxyObject";
    /**
     * SPRING代理类
     */
    public static final String PROXY_SPRING_CGLIB = "org.springframework.cglib.proxy.Factory";
    /**
     * MYBATIS代理代理
     */
    public static final String PROXY_MYBATIS = "org.apache.ibatis.javassist.util.proxy.ProxyObject";
    /**
     * 代理类集合
     */
    public static final Set<String> PROXY_CLASS_NAMES = ImmutableSet.of(PROXY_CGLIB, PROXY_JAVASSIST,
            PROXY_SPRING_CGLIB, PROXY_MYBATIS);
    /**
     * 类型缓存
     */
    private static final Map<Class<?>, Set<Class<?>>> CLASS_CACHES = Collections.synchronizedMap(new WeakHashMap<>());

    /**
     * 检查指定类是否为代理类
     *
     * @param clazz 待检查类
     * @return boolean
     */
    public static boolean isProxy(final Class<?> clazz) {
        if (!Types.isObject(clazz)) {
            return Arrays.stream(clazz.getInterfaces()).anyMatch(it -> PROXY_CLASS_NAMES.contains(it.getName()));
        }
        return false;
    }

    /**
     * 获取具体类
     *
     * @param clazz 类
     * @return 具体类
     */
    public static Class<?> getRealClass(final Class<?> clazz) {
        return Reflections.isProxy(clazz) ? clazz.getSuperclass() : clazz;
    }

    /**
     * 获取指定类的所有父类(包含自己本身)
     *
     * @param clazz   指定类
     * @param filters 过滤器列表
     * @return 类列表
     */
    @SafeVarargs
    public static Set<Class<?>> getAllTypes(final Class<?> clazz, final Predicate<? super Class<?>>... filters) {
        final Supplier<Set<Class<?>>> supplier = LinkedHashSet::new;
        final Set<Class<?>> classes = Objects.computeIfAbsent(CLASS_CACHES, clazz, supplier);
        if (Objects.isEmpty(classes)) {
            classes.addAll(Reflections.getAllSuperTypes(clazz, filters));
        }
        return Objects.isEmpty(classes) ? new LinkedHashSet<>(0) : new LinkedHashSet<>(classes);
    }

    /**
     * 获取指定类的所有超类(包含自己本身)
     *
     * @param clazz   指定类
     * @param filters 过滤器列表
     * @return 类列表
     */
    @SafeVarargs
    public static Set<Class<?>> getAllSuperTypes(final Class<?> clazz,
                                                 final Predicate<? super Class<?>>... filters) {
        final Set<Class<?>> classes = new LinkedHashSet<>();
        if (Objects.nonNull(clazz) && !Types.isObject(clazz)) {
            classes.add(clazz);
            final Set<Class<?>> superClasses = getSuperTypes(clazz);
            if (Objects.isNotEmpty(superClasses)) {
                for (Class<?> it : superClasses) {
                    final Set<Class<?>> set = getAllSuperTypes(it);
                    if (Objects.isNotEmpty(set)) {
                        classes.addAll(set);
                    }
                }
            }
        }
        return Objects.filters(classes, filters);
    }

    /**
     * 获取指定类的父类和接口
     *
     * @param clazz 指定类
     * @return 类集合
     */
    public static Set<Class<?>> getSuperTypes(final Class<?> clazz) {
        final Set<Class<?>> classes = new LinkedHashSet<>();
        final Class<?> superClass = clazz.getSuperclass();
        final Class<?>[] interfaces = clazz.getInterfaces();
        if (!Types.isObject(clazz)) {
            classes.add(superClass);
        }
        if (Objects.isNotEmpty(interfaces)) {
            classes.addAll(Arrays.asList(interfaces));
        }
        return classes;
    }

}
