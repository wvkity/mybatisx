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
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.lang.Types;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
     * get方法前缀
     */
    public static final String METHOD_PREFIX_GET = "get";
    /**
     * is方法前缀
     */
    public static final String METHOD_PREFIX_IS = "is";
    /**
     * set方法前缀
     */
    public static final String METHOD_PREFIX_SET = "set";
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
     * 默认超类过滤器
     */
    public static final Predicate<? super Class<?>> SUPER_CLASS_FILTER;

    static {
        SUPER_CLASS_FILTER = it ->
                Objects.nonNull(it) && !(Types.isObject(it) || Types.isSerializable(it) || Types.isAnnotation(it)
                        || Objects.isCollection(it));
    }

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
        final Set<Class<?>> classes = Objects.computeIfAbsent(CLASS_CACHES, clazz, k -> new LinkedHashSet<>());
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

    /**
     * 获取指定类的所有方法
     *
     * @param clazz   指定类
     * @param filters 过滤器列表
     * @return 方法集合
     */
    @SafeVarargs
    public static Set<Method> getAllMethods(final Class<?> clazz, final Predicate<? super Method>... filters) {
        return Reflections.getAllMethods(clazz, SUPER_CLASS_FILTER, filters);
    }

    /**
     * 获取指定类的所有方法
     *
     * @param clazz         指定类
     * @param classFilter   类过滤器
     * @param methodFilters 方法过滤器
     * @return 方法集合
     */
    @SafeVarargs
    public static Set<Method> getAllMethods(final Class<?> clazz, final Predicate<? super Class<?>> classFilter,
                                            final Predicate<? super Method>... methodFilters) {
        return Reflections.getAllMethods(Reflections.getAllTypes(clazz, classFilter).toArray(new Class<?>[0]),
                methodFilters);
    }

    /**
     * 获取指定类的所有方法
     *
     * @param classes 类列表
     * @param filters 过滤器
     * @return 方法集合
     */
    @SafeVarargs
    public static Set<Method> getAllMethods(final Class<?>[] classes, final Predicate<? super Method>... filters) {
        final Set<Method> methods = new LinkedHashSet<>();
        if (Objects.isNotEmpty(classes)) {
            final Map<String, Method> uniqueMethods = new HashMap<>();
            for (Class<?> it : classes) {
                Reflections.addUniqueMethods(uniqueMethods, Reflections.getMethods(it, filters));
            }
            methods.addAll(uniqueMethods.values());
        }
        return methods;
    }

    /**
     * 获取指定类的所有方法
     *
     * @param clazz   指定类
     * @param filters 过滤器列表
     * @return 方法集合
     */
    @SafeVarargs
    public static Set<Method> getMethods(final Class<?> clazz, final Predicate<? super Method>... filters) {
        return Objects.filters(clazz.isInterface() ? clazz.getMethods() : clazz.getDeclaredMethods(), filters);
    }

    /**
     * 添加唯一方法
     *
     * @param uniqueMethods 方法缓存
     * @param methods       方法集合
     */
    public static void addUniqueMethods(final Map<String, Method> uniqueMethods, final Set<Method> methods) {
        if (Objects.isNotEmpty(methods)) {
            for (Method it : methods) {
                if (!it.isBridge()) {
                    uniqueMethods.putIfAbsent(Reflections.getMethodSignature(it), it);
                }
            }
        }
    }

    /**
     * 方法签名
     *
     * @param method {@link Method}
     * @return 签名
     */
    public static String getMethodSignature(final Method method) {
        if (Objects.nonNull(method)) {
            final StringBuilder sb = new StringBuilder(60);
            final Class<?> returnType = method.getReturnType();
            sb.append(returnType.getName()).append("#");
            sb.append(method.getName());
            final Class<?>[] paramTypes = method.getParameterTypes();
            if (Objects.isNotEmpty(paramTypes)) {
                sb.append(":");
                sb.append(Arrays.stream(paramTypes).map(Class::getName).collect(Collectors.joining(",")));
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 检查是否为get方法
     *
     * @param method {@link Method}
     * @return boolean
     */
    public static boolean isGetter(final Method method) {
        return Objects.nonNull(method) && Reflections.isGetter(method.getName())
                && Objects.isAssignable(Void.class, method.getReturnType())
                && Objects.isEmpty(method.getParameterTypes());

    }

    /**
     * 检查是否为get方法
     *
     * @param name 方法名
     * @return boolean
     */
    public static boolean isGetter(final String name) {
        final int size;
        return Strings.isNotWhitespace(name) && ((name.startsWith(METHOD_PREFIX_GET) && Strings.size(name) > 3)
                || name.startsWith(METHOD_PREFIX_IS) && Strings.size(name) > 2);
    }

    /**
     * 检查是否为set方法
     *
     * @param method {@link Method}
     * @return boolean
     */
    public static boolean isSetter(final Method method) {
        return Objects.nonNull(method) && Reflections.isSetter(method.getName())
                && Objects.size(method.getParameterTypes()) == 1;
    }

    /**
     * 检查是否为set方法
     *
     * @param name 方法名
     * @return boolean
     */
    public static boolean isSetter(final String name) {
        return Strings.isNotWhitespace(name) && name.startsWith(METHOD_PREFIX_SET) && Strings.size(name) > 3;
    }

    /**
     * 获取指定类的所有属性(字段)
     *
     * @param clazz   指定类
     * @param filters 属性过滤器
     * @return 属性集合
     */
    @SafeVarargs
    public static Set<Field> getAllFields(final Class<?> clazz, final Predicate<? super Field>... filters) {
        return Reflections.getAllFields(clazz, SUPER_CLASS_FILTER, filters);
    }

    /**
     * 获取指定类的所有属性(字段)
     *
     * @param clazz        指定类
     * @param classFilter  类过滤器
     * @param fieldFilters 属性过滤器
     * @return 属性集合
     */
    @SafeVarargs
    public static Set<Field> getAllFields(final Class<?> clazz, final Predicate<? super Class<?>> classFilter,
                                          final Predicate<? super Field>... fieldFilters) {
        return Reflections.getAllFields(Reflections.getAllTypes(clazz, classFilter).toArray(new Class<?>[0]),
                fieldFilters);
    }

    /**
     * 获取指定类的所有属性(字段)
     *
     * @param classes 类数组
     * @param filters 过滤器
     * @return 属性集合
     */
    @SafeVarargs
    public static Set<Field> getAllFields(final Class<?>[] classes, final Predicate<? super Field>... filters) {
        final Set<Field> fields = new LinkedHashSet<>();
        if (Objects.isNotEmpty(classes)) {
            for (Class<?> it : classes) {
                fields.addAll(Reflections.getFields(it, filters));
            }
        }
        return fields;
    }

    /**
     * 获取指定类的所有属性(字段)
     *
     * @param clazz   指定类
     * @param filters 过滤器
     * @return 属性集合
     */
    @SafeVarargs
    public static Set<Field> getFields(final Class<?> clazz, final Predicate<? super Field>... filters) {
        return Objects.filters(clazz.getDeclaredFields(), filters);
    }

    /**
     * 获取所有注解
     *
     * @param type    注解类
     * @param filters 过滤器列表
     * @return 注解集合
     */
    @SafeVarargs
    public static Set<Annotation> getAnnotations(final AnnotatedElement type,
                                                 final Predicate<Annotation>... filters) {
        return Objects.filters(type.getDeclaredAnnotations(), filters);
    }

    /**
     * 检查指定类型是否存在指定注解
     *
     * @param type       指定类型
     * @param annotation 指定注解
     * @return boolean
     */
    public static boolean isAnnotationPresent(final AnnotatedElement type,
                                              final Class<? extends Annotation> annotation) {
        return Objects.nonNull(type) && Reflections.withAnnotation(annotation).test(type);
    }

    /**
     * 生成{@link Annotation}过滤器
     *
     * @param annotation 注解类
     * @return {@link Predicate}
     */
    public static Predicate<AnnotatedElement> withAnnotation(final Class<? extends Annotation> annotation) {
        return it -> Objects.nonNull(it) && it.isAnnotationPresent(annotation);
    }

    /**
     * 检查是否可控制成员访问权限
     *
     * @return boolean
     */
    public static boolean canControlMemberAccessible() {
        try {
            final SecurityManager sm = System.getSecurityManager();
            if (Objects.nonNull(sm)) {
                sm.checkPermission(new RuntimePermission("suppressAccessChecks"));
            }
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     * 获取指定类的泛型类
     *
     * @param clazz 类
     * @param index 索引
     * @return 泛型类
     */
    public static Class<?> getGenericClass(final Class<?> clazz, final int index) {
        return Reflections.getGenericClass(clazz.getGenericSuperclass(), index);
    }

    /**
     * 获取指定类型的泛型类
     *
     * @param type  类型
     * @param index 索引
     * @return 泛型类
     */
    public static Class<?> getGenericClass(final Type type, final int index) {
        if (!Objects.isAssignable(ParameterizedType.class, type)) {
            return Object.class;
        }
        final Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        if (Objects.isEmpty(types)) {
            return Object.class;
        }
        final Type it = types[index];
        if (Objects.isAssignable(Class.class, it)) {
            return (Class<?>) it;
        }
        return Object.class;
    }

}
