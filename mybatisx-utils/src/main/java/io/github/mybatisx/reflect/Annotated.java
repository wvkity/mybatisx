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
import io.github.mybatisx.lang.Strings;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 注解数据接口
 *
 * @author wvkity
 * @created 2021/12/27
 * @since 1.0.0
 */
@SuppressWarnings({"unused"})
public interface Annotated {

    /**
     * 根据注解类获取对应注解实例
     *
     * @param clazz 注解类
     * @param <A>   注解类型
     * @return 注解实例
     */
    default <A extends Annotation> A getAnnotation(final Class<A> clazz) {
        if (Objects.nonNull(clazz)) {
            return this.getAnnotation(clazz.getName());
        }
        return null;
    }

    /**
     * 根据注解类名获取对应注解实例
     *
     * @param className 注解类名
     * @param <A>       注解类型
     * @return 注解实例
     */
    @SuppressWarnings({"unchecked"})
    default <A extends Annotation> A getAnnotation(final String className) {
        if (Strings.isNotWhitespace(className)) {
            return (A) this.getAnnotationMap().get(className);
        }
        return null;
    }

    /**
     * 检查是否存在指定注解
     *
     * @param clazz 注解类
     * @return boolean
     */
    default boolean isMatches(final Class<? extends Annotation> clazz) {
        final Set<? extends Annotation> annotations;
        if (Objects.nonNull(clazz) && Objects.isNotEmpty((annotations = this.getAnnotations()))) {
            return annotations.stream().anyMatch(it -> clazz.equals(it.annotationType()));
        }
        return false;
    }

    /**
     * 检查是否存在指定注解
     *
     * @param classes 注解列表
     * @return boolean
     */
    @SuppressWarnings({"unchecked"})
    default boolean isMatches(final Class<? extends Annotation>... classes) {
        return this.isMatches(Objects.filterNull(classes));
    }

    /**
     * 检查是否存在指定注解
     *
     * @param classes 注解列表
     * @return boolean
     */
    default boolean isMatches(final Collection<Class<? extends Annotation>> classes) {
        final Set<? extends Annotation> annotations;
        final Set<Class<? extends Annotation>> annotationClasses;
        if (Objects.isNotEmpty((annotationClasses = Objects.filterNull(classes)))
                && Objects.isNotEmpty((annotations = this.getAnnotations()))) {
            return annotations.stream().anyMatch(it -> annotationClasses.contains(it.annotationType()));
        }
        return false;
    }

    /**
     * 检查是否存在指定注解
     *
     * @param className 注解类名
     * @return boolean
     */
    default boolean isMatches(final String className) {
        return Strings.isNotWhitespace(className) && this.getAnnotationMap().containsKey(className);
    }

    /**
     * 获取注解元数据
     *
     * @param clazz 注解类
     * @param <A>   注解类型
     * @return {@link Metadata}
     */
    default <A extends Annotation> Metadata getMetadata(final Class<A> clazz) {
        return ReflectMetadata.of(this.getAnnotation(clazz));
    }

    /**
     * 获取注解元数据
     *
     * @param className 注解类名
     * @return {@link Metadata}
     */
    default Metadata getMetadata(final String className) {
        return ReflectMetadata.of(this.getAnnotation(className));
    }

    /**
     * 检查是否存在注解
     *
     * @return boolean
     */
    default boolean isEmpty() {
        return Objects.isEmpty(this.getAnnotations());
    }

    /**
     * 获取所有注解对象
     *
     * @return 注解对象集合
     */
    Set<? extends Annotation> getAnnotations();

    /**
     * 获取所有注解(注解名-注解对象)对象集合
     *
     * @return 注解对象集合
     */
    Map<String, ? extends Annotation> getAnnotationMap();

}
