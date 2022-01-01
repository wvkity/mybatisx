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
package io.github.mybatisx.base.reflect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.github.mybatisx.base.matcher.ClassMatcher;
import io.github.mybatisx.base.matcher.FieldMatcher;
import io.github.mybatisx.base.matcher.GetterMatcher;
import io.github.mybatisx.base.matcher.SetterMatcher;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.reflect.FieldWrapper;
import io.github.mybatisx.reflect.Reflections;
import lombok.experimental.Accessors;
import org.apache.ibatis.reflection.ReflectionException;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 默认反射器
 * <p>
 * 参考{@link org.apache.ibatis.reflection.Reflector}
 *
 * @author wvkity
 * @created 2021/12/27
 * @since 1.0.0
 */
@Accessors(fluent = true, chain = true)
public class DefaultReflector implements Reflector {

    /**
     * 反射目标类
     */
    private final Class<?> type;
    /**
     * 原属性列表
     */
    private final Set<Field> originalFields;
    /**
     * 包装属性列表
     */
    private final List<FieldWrapper> fields;
    /**
     * get方法名列表
     */
    private final String[] readableProperties;
    /**
     * set方法名列表
     */
    private final String[] writableProperties;
    /**
     * 注解集合
     */
    private final Set<? extends Annotation> annotations;
    /**
     * 注解集合
     */
    private final Map<String, ? extends Annotation> annotationMap;
    /**
     * get方法返回值集合
     */
    private final Map<String, Class<?>> getterTypes = new HashMap<>();
    /**
     * get方法集合
     */
    private final Map<String, Method> getterMethods = new HashMap<>();
    /**
     * set方法返回值集合
     */
    private final Map<String, Class<?>> setterTypes = new HashMap<>();
    /**
     * set方法集合
     */
    private final Map<String, Method> setterMethods = new HashMap<>();

    public DefaultReflector(Class<?> type, ClassMatcher classMatcher, FieldMatcher fieldMatcher,
                            GetterMatcher readableMatcher, SetterMatcher writableMatcher) {
        this.type = type;
        this.originalFields = Reflections.getAllFields(type, classMatcher, fieldMatcher);
        this.annotations = Reflections.getAllAnnotations(type, Reflections.METADATA_ANNOTATION_FILTER);
        if (this.annotations.isEmpty()) {
            this.annotationMap = ImmutableMap.of();
        } else {
            this.annotationMap = ImmutableMap.copyOf(this.annotations.stream().collect(Collectors.toMap(it ->
                    it.annotationType().getCanonicalName(), it -> it)));
        }
        final Set<Field> realFields = new LinkedHashSet<>();
        this.parse(type, classMatcher, readableMatcher, writableMatcher, realFields);
        this.readableProperties = this.getterMethods.keySet().toArray(new String[0]);
        this.writableProperties = this.setterMethods.keySet().toArray(new String[0]);
        if (realFields.isEmpty()) {
            this.fields = ImmutableList.of();
        } else {
            this.fields = ImmutableList.copyOf(realFields.stream().map(it -> {
                final String property = it.getName();
                return new FieldWrapper(this.type, it, property, this.getterTypes.get(property),
                        this.getterMethods.get(property), this.setterMethods.get(property));
            }).collect(Collectors.toList()));
        }
    }

    /**
     * 解析
     */
    private void parse(final Class<?> type, final ClassMatcher classMatcher, final GetterMatcher readableMatcher,
                       final SetterMatcher writableMatcher, final Set<Field> realFields) {
        this.addGetterMethods(type, classMatcher, readableMatcher);
        this.addSetterMethods(type, classMatcher, writableMatcher);
        this.addFields(realFields);
    }

    private void addGetterMethods(final Class<?> type, final ClassMatcher classMatcher,
                                  final GetterMatcher readableMatcher) {
        final Set<Method> methods = Reflections.getAllMethods(type, classMatcher, readableMatcher);
        if (!methods.isEmpty()) {
            final Map<String, List<Method>> conflictingGetters = new HashMap<>();
            methods.forEach(it -> this.addMethodConflict(conflictingGetters,
                    PropertyNamer.methodToProperty(it.getName()), it));
            this.resolveGetterConflicts(conflictingGetters);
        }
    }

    private void resolveGetterConflicts(final Map<String, List<Method>> conflictingGetters) {
        for (Map.Entry<String, List<Method>> entry : conflictingGetters.entrySet()) {
            Method winner = null;
            String propName = entry.getKey();
            boolean isAmbiguous = false;
            for (Method candidate : entry.getValue()) {
                if (winner == null) {
                    winner = candidate;
                    continue;
                }
                Class<?> winnerType = winner.getReturnType();
                Class<?> candidateType = candidate.getReturnType();
                if (candidateType.equals(winnerType)) {
                    if (!boolean.class.equals(candidateType)) {
                        isAmbiguous = true;
                        break;
                    } else if (candidate.getName().startsWith("is")) {
                        winner = candidate;
                    }
                } else if (candidateType.isAssignableFrom(winnerType)) {
                    // OK getter type is descendant
                } else if (winnerType.isAssignableFrom(candidateType)) {
                    winner = candidate;
                } else {
                    isAmbiguous = true;
                    break;
                }
            }
            this.addGetMethod(propName, winner, isAmbiguous);
        }
    }

    private void addGetMethod(final String name, final Method method, final boolean isAmbiguous) {
        this.getterMethods.put(name, method);
        Type returnType = TypeParameterResolver.resolveReturnType(method, type);
        this.getterTypes.put(name, Reflections.typeToClass(returnType));
    }

    private void addSetterMethods(final Class<?> type, final ClassMatcher classMatcher,
                                  final SetterMatcher writableMatcher) {
        final Set<Method> methods = Reflections.getAllMethods(type, classMatcher, writableMatcher);
        if (!methods.isEmpty()) {
            Map<String, List<Method>> conflictingSetters = new HashMap<>();
            methods.forEach(it -> this.addMethodConflict(conflictingSetters,
                    PropertyNamer.methodToProperty(it.getName()), it));
            this.resolveSetterConflicts(conflictingSetters);
        }
    }

    private void resolveSetterConflicts(Map<String, List<Method>> conflictingSetters) {
        for (String propName : conflictingSetters.keySet()) {
            List<Method> setters = conflictingSetters.get(propName);
            Class<?> getterType = this.getterTypes.get(propName);
            Method match = null;
            ReflectionException exception = null;
            for (Method setter : setters) {
                if (setter.getParameterTypes()[0].equals(getterType)) {
                    // should be the best match
                    match = setter;
                    break;
                }
                if (exception == null) {
                    try {
                        match = this.pickBetterSetter(match, setter, propName);
                    } catch (ReflectionException e) {
                        // there could still be the 'best match'
                        match = null;
                        exception = e;
                    }
                }
            }
            if (match == null && exception != null) {
                throw exception;
            } else {
                this.addSetMethod(propName, match);
            }
        }
    }

    private Method pickBetterSetter(Method setter1, Method setter2, String property) {
        if (setter1 == null) {
            return setter2;
        }
        Class<?> paramType1 = setter1.getParameterTypes()[0];
        Class<?> paramType2 = setter2.getParameterTypes()[0];
        if (paramType1.isAssignableFrom(paramType2)) {
            return setter2;
        } else if (paramType2.isAssignableFrom(paramType1)) {
            return setter1;
        }
        throw new ReflectionException("Ambiguous setters defined for property '" + property + "' in class '"
                + setter2.getDeclaringClass() + "' with types '" + paramType1.getName() + "' and '"
                + paramType2.getName() + "'.");
    }

    private void addSetMethod(String name, Method method) {
        if (Reflections.isValidProperty(name)) {
            this.setterMethods.put(name, method);
            Type[] paramTypes = TypeParameterResolver.resolveParamTypes(method, type);
            this.setterTypes.put(name, Reflections.typeToClass(paramTypes[0]));
        }
    }

    private void addFields(final Set<Field> realFields) {
        if (!this.originalFields.isEmpty()) {
            for (Field field : this.originalFields) {
                final String property = field.getName();
                final int modifiers = field.getModifiers();
                if (!this.setterMethods.containsKey(property)
                        && !Modifier.isFinal(modifiers) && !Modifier.isStatic(modifiers)) {
                    this.addSetField(field);
                }
                if (!this.getterMethods.containsKey(property)) {
                    this.addGetField(field);
                } else {
                    // 属性存在，且get方法存在则认为是有效属性
                    realFields.add(field);
                }
            }
        }
    }

    /**
     * 添加set属性
     *
     * @param field {@link Field}
     */
    private void addSetField(final Field field) {
        final String property;
        if (Reflections.isValidProperty((property = field.getName()))) {
            this.setterMethods.put(property, null);
            this.setterTypes.put(property,
                    Reflections.typeToClass(TypeParameterResolver.resolveFieldType(field, this.type)));
        }
    }

    /**
     * 添加get属性
     *
     * @param field {@link Field}
     */
    private void addGetField(final Field field) {
        final String property;
        if (Reflections.isValidProperty((property = field.getName()))) {
            this.getterMethods.put(property, null);
            this.getterTypes.put(property,
                    Reflections.typeToClass(TypeParameterResolver.resolveFieldType(field, this.type)));
        }
    }

    private void addMethodConflict(final Map<String, List<Method>> conflictingMethods, final String name,
                                   final Method method) {
        Objects.computeIfAbsent(conflictingMethods, name, k -> new ArrayList<>()).add(method);
    }

    @Override
    public Set<? extends Annotation> getAnnotations() {
        return this.annotations;
    }

    @Override
    public Map<String, ? extends Annotation> getAnnotationMap() {
        return this.annotationMap;
    }

    @Override
    public Class<?> getType() {
        return this.type;
    }

    @Override
    public Set<Field> getOrgFields() {
        return this.originalFields;
    }

    @Override
    public List<FieldWrapper> getFields() {
        return this.fields;
    }

    @Override
    public String[] getReadableProperties() {
        return this.readableProperties;
    }

    @Override
    public String[] getWritableProperties() {
        return this.writableProperties;
    }

    @Override
    public Class<?> getGetterType(String propertyName) {
        final Class<?> clazz = this.getterTypes.get(propertyName);
        if (clazz == null) {
            throw new ReflectionException("There is no getter for property named '" + propertyName + "' in '" + type + "'");
        }
        return clazz;
    }

    @Override
    public Class<?> getSetterType(String propertyName) {
        final Class<?> clazz = setterTypes.get(propertyName);
        if (clazz == null) {
            throw new ReflectionException("There is no setter for property named '" + propertyName + "' in '" + type + "'");
        }
        return clazz;
    }

    @Override
    public boolean hasGetter(String propertyName) {
        return this.getterMethods.containsKey(propertyName);
    }

    @Override
    public boolean hasSetter(String propertyName) {
        return this.setterMethods.containsKey(propertyName);
    }
}
