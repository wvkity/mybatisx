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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.github.mybatisx.lang.ObjectHelper;
import io.github.mybatisx.lang.StringHelper;
import io.github.mybatisx.lang.TypeHepler;
import io.github.mybatisx.util.CollectionHelper;
import io.github.mybatisx.util.MapHelper;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
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
     * JDK9+ MethodHandles.Lookup privateLookupIn方法
     */
    private static final String METHOD_PRIVATE_LOOKUP_IN = "privateLookupIn";
    /**
     * 所有访问权限
     */
    public static final int ALLOWED_MODES = MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PACKAGE
            | MethodHandles.Lookup.PROTECTED | MethodHandles.Lookup.PUBLIC;
    /**
     * privateLookupIn方法对象
     */
    private static final Method PRIVATE_LOOKUP_IN_METHOD;
    /**
     * {@link MethodHandles.Lookup}构造方法
     */
    private static final Constructor<MethodHandles.Lookup> LOOKUP_CONSTRUCTOR;
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
    private static final Map<Class<?>, Set<Class<?>>> CLASS_CACHES =
            java.util.Collections.synchronizedMap(new WeakHashMap<>());
    /**
     * 默认超类过滤器
     */
    public static final Predicate<? super Class<?>> SUPER_CLASS_FILTER;
    /**
     * 注解过滤器
     */
    public static final Predicate<? super Method> ANNOTATION_METHOD_FILTER;
    /**
     * 注解过滤器
     */
    public static final Predicate<Annotation> METADATA_ANNOTATION_FILTER;

    static {
        SUPER_CLASS_FILTER = it -> ObjectHelper.nonNull(it) &&
                !(TypeHepler.isObject(it) || TypeHepler.isSerializable(it) || TypeHepler.isAnnotation(it) || ObjectHelper.isCollection(it));
        ANNOTATION_METHOD_FILTER = it -> !TypeHepler.ANNOTATION_METHOD_NAMES.contains(it.getName());
        METADATA_ANNOTATION_FILTER = it -> !TypeHepler.METADATA_ANNOTATION_TYPES.contains(it.annotationType());
        Method privateLookupIn;
        try {
            privateLookupIn = MethodHandles.class.getMethod(METHOD_PRIVATE_LOOKUP_IN,
                    Class.class, MethodHandles.Lookup.class);
        } catch (NoSuchMethodException ignore) {
            privateLookupIn = null;
        }
        PRIVATE_LOOKUP_IN_METHOD = privateLookupIn;
        Constructor<MethodHandles.Lookup> constructor = null;
        if (privateLookupIn == null) {
            // JDK 1.8
            try {
                constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
                constructor.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException("There is neither 'privateLookupIn(Class, Lookup)' nor " +
                        "'Lookup(Class, int)' method in java.lang.invoke.MethodHandles.", e);
            } catch (Throwable ignore) {
                constructor = null;
            }
        }
        LOOKUP_CONSTRUCTOR = constructor;
    }

    public static MethodHandles.Lookup getLookupJava9(final Class<?> clazz) throws
            InvocationTargetException, IllegalAccessException {
        return (MethodHandles.Lookup) PRIVATE_LOOKUP_IN_METHOD.invoke(MethodHandles.class, clazz, MethodHandles.lookup());
    }

    public static MethodHandles.Lookup getLookupJava8(final Class<?> clazz) throws
            InvocationTargetException, InstantiationException, IllegalAccessException {
        return LOOKUP_CONSTRUCTOR.newInstance(clazz, ALLOWED_MODES);
    }

    /**
     * 获取{@link MethodHandles.Lookup}对象
     *
     * @param clazz 被调用类或接口
     * @return {@link MethodHandles.Lookup}
     */
    public static MethodHandles.Lookup getLookup(final Class<?> clazz) {
        if (PRIVATE_LOOKUP_IN_METHOD != null) {
            try {
                return getLookupJava9(clazz);
            } catch (Throwable e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
        try {
            return getLookupJava8(clazz);
        } catch (Throwable e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static MethodHandle getMethodHandleJava9(Method method)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final Class<?> declaringClass = method.getDeclaringClass();
        return ((MethodHandles.Lookup) PRIVATE_LOOKUP_IN_METHOD.invoke(null, declaringClass, MethodHandles.lookup()))
                .findSpecial(declaringClass, method.getName(),
                        MethodType.methodType(method.getReturnType(), method.getParameterTypes()), declaringClass);
    }

    public static MethodHandle getMethodHandleJava8(Method method)
            throws IllegalAccessException, InstantiationException, InvocationTargetException {
        final Class<?> declaringClass = method.getDeclaringClass();
        return LOOKUP_CONSTRUCTOR.newInstance(declaringClass, ALLOWED_MODES).unreflectSpecial(method, declaringClass);
    }

    /**
     * 检查指定类是否为代理类
     *
     * @param clazz 待检查类
     * @return boolean
     */
    public static boolean isProxy(final Class<?> clazz) {
        if (!TypeHepler.isObject(clazz)) {
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
        final Set<Class<?>> classes = MapHelper.computeIfAbsent(CLASS_CACHES, clazz, k -> new LinkedHashSet<>());
        if (CollectionHelper.isEmpty(classes)) {
            classes.addAll(Reflections.getAllSuperTypes(clazz, filters));
        }
        return CollectionHelper.isEmpty(classes) ? new LinkedHashSet<>(0) : new LinkedHashSet<>(classes);
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
        if (ObjectHelper.nonNull(clazz) && !TypeHepler.isObject(clazz)) {
            final Set<Class<?>> superClasses = getSuperTypes(clazz);
            if (CollectionHelper.isNotEmpty(superClasses)) {
                for (Class<?> it : superClasses) {
                    final Set<Class<?>> set = getAllSuperTypes(it);
                    if (CollectionHelper.isNotEmpty(set)) {
                        classes.addAll(set);
                    }
                }
            }
            classes.add(clazz);
        }
        return CollectionHelper.filters(classes, filters);
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
        if (ObjectHelper.isNotEmpty(interfaces)) {
            classes.addAll(Arrays.asList(interfaces));
        }
        if (!TypeHepler.isObject(clazz)) {
            classes.add(superClass);
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
        if (ObjectHelper.isNotEmpty(classes)) {
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
        return CollectionHelper.filters(clazz.isInterface() ? clazz.getMethods() : clazz.getDeclaredMethods(), filters);
    }

    /**
     * 添加唯一方法
     *
     * @param uniqueMethods 方法缓存
     * @param methods       方法集合
     */
    public static void addUniqueMethods(final Map<String, Method> uniqueMethods, final Set<Method> methods) {
        if (CollectionHelper.isNotEmpty(methods)) {
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
        if (ObjectHelper.nonNull(method)) {
            final StringBuilder sb = new StringBuilder(60);
            final Class<?> returnType = method.getReturnType();
            sb.append(returnType.getName()).append("#");
            sb.append(method.getName());
            final Class<?>[] paramTypes = method.getParameterTypes();
            if (ObjectHelper.isNotEmpty(paramTypes)) {
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
        return ObjectHelper.nonNull(method) && Reflections.isGetter(method.getName())
                && !ObjectHelper.isAssignable(Void.class, method.getReturnType())
                && ObjectHelper.isEmpty(method.getParameterTypes());

    }

    /**
     * 检查是否为get方法
     *
     * @param name 方法名
     * @return boolean
     */
    public static boolean isGetter(final String name) {
        return StringHelper.isNotWhitespace(name) && ((name.startsWith(METHOD_PREFIX_GET) && StringHelper.size(name) > 3)
                || name.startsWith(METHOD_PREFIX_IS) && StringHelper.size(name) > 2);
    }

    /**
     * 检查是否为set方法
     *
     * @param method {@link Method}
     * @return boolean
     */
    public static boolean isSetter(final Method method) {
        return ObjectHelper.nonNull(method) && Reflections.isSetter(method.getName())
                && ObjectHelper.size(method.getParameterTypes()) == 1;
    }

    /**
     * 检查是否为set方法
     *
     * @param name 方法名
     * @return boolean
     */
    public static boolean isSetter(final String name) {
        return StringHelper.isNotWhitespace(name) && name.startsWith(METHOD_PREFIX_SET) && StringHelper.size(name) > 3;
    }

    /**
     * 检查属性名是否有效
     *
     * @param property 属性名
     * @return boolean
     */
    public static boolean isValidProperty(final String property) {
        return StringHelper.isNotWhitespace(property) &&
                !(property.startsWith("$") || "serialVersionUID".equals(property) || "class".equals(property));
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
        if (ObjectHelper.isNotEmpty(classes)) {
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
        return CollectionHelper.filters(clazz.getDeclaredFields(), filters);
    }

    /**
     * 检查指定类型是否存在指定注解
     *
     * @param type       指定类型
     * @param annotation 指定注解
     * @return boolean
     */
    public static <T extends AnnotatedElement> boolean isMatches(final T type,
                                                                 final Class<? extends Annotation> annotation) {
        return ObjectHelper.nonNull(type) && Reflections.withAnnotation(annotation).test(type);
    }

    /**
     * 检查指定类型是否存在指定注解
     *
     * @param type      指定类型
     * @param className 注解类全限定名
     * @param filters   过滤器列表
     * @param <T>       泛型类型
     * @return boolean
     */
    @SafeVarargs
    public static <T extends AnnotatedElement> boolean isMatches(final T type, final String className,
                                                                 final Predicate<Annotation>... filters) {
        if (ObjectHelper.nonNull(type) && StringHelper.isNotWhitespace(className)) {
            final Set<Annotation> annotations = Reflections.getAllAnnotations(type, filters);
            return CollectionHelper.isNotEmpty(annotations) && annotations.stream().anyMatch(it ->
                    className.equalsIgnoreCase(it.annotationType().getCanonicalName()));
        }
        return false;
    }

    /**
     * 检查指定类型是否存在指定注解
     *
     * @param type  指定类型
     * @param clazz 注解类
     * @return boolean
     */
    @SafeVarargs
    public static <T extends AnnotatedElement> boolean isMatches(final T type, final Class<? extends Annotation> clazz,
                                                                 final String className,
                                                                 final Predicate<Annotation>... filters) {
        return Reflections.isMatches(type, clazz) || Reflections.isMatches(type, className, filters);
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
        return CollectionHelper.filters(type.getDeclaredAnnotations(), filters);
    }

    /**
     * 获取指定类型的所有注解
     *
     * @param type    指定类
     * @param filters 过滤器列表
     * @param <T>     泛型类型
     * @return 注解集合
     */
    @SafeVarargs
    public static <T extends AnnotatedElement> Set<Annotation> getAllAnnotations(final T type,
                                                                                 final Predicate<Annotation>... filters) {
        return Reflections.getAllAnnotations(type, SUPER_CLASS_FILTER, filters);
    }

    /**
     * 获取指定类型的所有注解
     *
     * @param type    指定类
     * @param filters 过滤器列表
     * @param <T>     泛型类型
     * @return 注解集合
     */
    @SafeVarargs
    public static <T extends AnnotatedElement> Set<Annotation> getAllAnnotations(final T type,
                                                                                 final Predicate<? super Class<?>> classFilter,
                                                                                 final Predicate<Annotation>... filters) {
        final List<AnnotatedElement> types;
        if (type instanceof Class) {
            types = ImmutableList.copyOf(Reflections.getAllTypes((Class<?>) type, classFilter));
        } else {
            types = ImmutableList.of(type);
        }
        return Reflections.getAllAnnotations(types, filters);
    }

    /**
     * 获取指定类型的所有注解
     *
     * @param types   指定多个类型
     * @param filters 过滤器列表
     * @param <T>     泛型类型
     * @return 注解集合
     */
    @SafeVarargs
    public static <T extends AnnotatedElement> Set<Annotation> getAllAnnotations(final List<T> types,
                                                                                 final Predicate<Annotation>... filters) {
        final Set<Annotation> annotations = new LinkedHashSet<>();
        if (CollectionHelper.isNotEmpty(types)) {
            int size = types.size();
            final List<AnnotatedElement> keys = new ArrayList<>(types);
            for (int i = 0; i < size; i++) {
                for (Annotation it : Reflections.getAnnotations(keys.get(i), filters)) {
                    if (annotations.add(it)) {
                        keys.add(i + 1, it.annotationType());
                        size += 1;
                    }
                }
            }
        }
        return annotations;
    }

    /**
     * 生成{@link Annotation}过滤器
     *
     * @param annotation 注解类
     * @return {@link Predicate}
     */
    public static Predicate<AnnotatedElement> withAnnotation(final Class<? extends Annotation> annotation) {
        return it -> ObjectHelper.nonNull(it) && it.isAnnotationPresent(annotation);
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
        if (!ObjectHelper.isAssignable(ParameterizedType.class, type)) {
            return Object.class;
        }
        final Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        if (ObjectHelper.isEmpty(types)) {
            return Object.class;
        }
        final Type it = types[index];
        if (it instanceof Class) {
            return (Class<?>) it;
        }
        return Object.class;
    }

    /**
     * 获取参数类型
     *
     * @param args 参数列表
     * @return 类型列表
     */
    public static Class<?>[] getArgumentTypes(final Object... args) {
        if (ObjectHelper.isEmpty(args)) {
            return new Class<?>[0];
        }
        return Arrays.stream(args).map(it -> ObjectHelper.isNull(it) ? Object.class : it.getClass()).toArray(Class<?>[]::new);
    }

    /**
     * 注解转{@link Map}
     *
     * @param instance 注解实例
     * @param <A>      注解类型
     * @return {@link Map}
     */
    public static <A extends Annotation> Map<String, Object> annotationToMap(final A instance) {
        if (ObjectHelper.nonNull(instance)) {
            final Set<Method> methods = Reflections.getAllMethods(instance.annotationType(), SUPER_CLASS_FILTER,
                    ANNOTATION_METHOD_FILTER);
            if (CollectionHelper.isNotEmpty(methods)) {
                final Map<String, Object> result = new HashMap<>(methods.size());
                for (Method it : methods) {
                    final String property = it.getName();
                    try {
                        final Object value = it.invoke(instance);
                        result.put(property, value);
                    } catch (IllegalAccessException e) {
                        try {
                            it.setAccessible(true);
                            final Object value = it.invoke(instance);
                            result.put(property, value);
                        } catch (Exception ignore) {
                            // ignore
                        }
                    } catch (Exception ignore) {
                        // ignore
                    }
                }
                return result;
            }
        }
        return new HashMap<>(0);
    }

    /**
     * {@link Type}转{@link Class}
     *
     * @param src {@link Type}
     * @return {@link Class}
     */
    public static Class<?> typeToClass(Type src) {
        Class<?> result = null;
        if (src instanceof Class) {
            result = (Class<?>) src;
        } else if (src instanceof ParameterizedType) {
            result = (Class<?>) ((ParameterizedType) src).getRawType();
        } else if (src instanceof GenericArrayType) {
            final Type componentType = ((GenericArrayType) src).getGenericComponentType();
            if (componentType instanceof Class) {
                result = Array.newInstance((Class<?>) componentType, 0).getClass();
            } else {
                final Class<?> componentClass = typeToClass(componentType);
                result = Array.newInstance(componentClass, 0).getClass();
            }
        }
        if (result == null) {
            result = Object.class;
        }
        return result;
    }

    /**
     * 创建指定类实例
     *
     * @param clazz 对象类
     * @param args  参数列表
     * @param <T>   目标类型
     * @return 目标对象
     * @throws NoSuchMethodException     if a matching method is not found.
     * @throws InvocationTargetException if the underlying constructor throws an exception.
     * @throws InstantiationException    if the class that declares the underlying constructor represents an abstract class.
     * @throws IllegalAccessException    if this Constructor object is enforcing Java language access control and the underlying constructor is inaccessible
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T newInstance(final Class<T> clazz, final Object... args) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        final Constructor<T> constructor;
        if (ObjectHelper.isEmpty(args)) {
            constructor = clazz.getDeclaredConstructor();
            try {
                return constructor.newInstance();
            } catch (IllegalAccessException ignore) {
                constructor.setAccessible(true);
                return constructor.newInstance();
            }
        } else {
            final int size = ObjectHelper.size(args);
            final Constructor<?>[] constructors = Arrays.stream(clazz.getDeclaredConstructors()).filter(it ->
                    ObjectHelper.size(it.getParameterTypes()) == size).toArray(Constructor[]::new);
            if (ObjectHelper.isNotEmpty(constructors)) {
                for (Constructor<?> it : constructors) {
                    if (Reflections.compareParameterType(it.getParameterTypes(), args)) {
                        try {
                            return (T) it.newInstance(args);
                        } catch (IllegalAccessException e) {
                            it.setAccessible(true);
                            return (T) it.newInstance(args);
                        }
                    }
                }
            }
            throw new IllegalArgumentException("The constructor for the specified argument list could not be found");
        }
    }

    /**
     * 比较参数类型和值是否一致
     *
     * @param types 参数类型列表
     * @param args  参数值列表
     * @return boolean
     */
    public static boolean compareParameterType(final Class<?>[] types, final Object... args) {
        final int size = ObjectHelper.size(types);
        if (size == ObjectHelper.size(args)) {
            for (Class<?> it : types) {
                if (ObjectHelper.isAssignable(it, args[0])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 加载类
     *
     * @param className    类名
     * @param classLoaders 类加载器列表
     * @return {@link Class}
     * @throws ClassNotFoundException if the class cannot be located by the specified class loader
     */
    public static Class<?> loadClass(final String className, final ClassLoader... classLoaders)
            throws ClassNotFoundException {
        if (StringHelper.isNotWhitespace(className)) {
            if (ObjectHelper.isNotEmpty(classLoaders)) {
                for (ClassLoader it : classLoaders) {
                    if (it != null) {
                        try {
                            return Class.forName(className, true, it);
                        } catch (Exception ignore) {
                            // ignore
                        }
                    }
                }
            } else {
                return Class.forName(className, true, Thread.currentThread().getContextClassLoader());
            }
        }
        throw new ClassNotFoundException("Cannot find class: " + className);
    }

}
