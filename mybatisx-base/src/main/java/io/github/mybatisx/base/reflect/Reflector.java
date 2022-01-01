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

import io.github.mybatisx.reflect.Annotated;
import io.github.mybatisx.reflect.FieldWrapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * 反射器
 *
 * @author wvkity
 * @created 2021/12/26
 * @since 1.0.0
 */
public interface Reflector extends Annotated {

    /**
     * 获取类信息
     *
     * @return 类信息
     */
    Class<?> getType();

    /**
     * 获取所有属性
     *
     * @return 属性集合
     */
    Set<Field> getOrgFields();

    /**
     * 获取所有包装属性
     *
     * @return 包装属性集合
     */
    List<FieldWrapper> getFields();

    /**
     * 获取get方法名列表
     *
     * @return get方法名列表
     */
    String[] getReadableProperties();

    /**
     * 获取set方法名列表
     *
     * @return set方法名列表
     */
    String[] getWritableProperties();

    /**
     * 获取属性get方法的返回值类型
     *
     * @param propertyName 属性
     * @return 返回值类型
     */
    Class<?> getGetterType(String propertyName);

    /**
     * 获取属性的类型
     *
     * @param propertyName 属性
     * @return 属性类型
     */
    Class<?> getSetterType(String propertyName);

    /**
     * 根据属性检查是否存在get方法
     *
     * @param propertyName 属性名
     * @return boolean
     */
    boolean hasGetter(final String propertyName);

    /**
     * 根据属性检查是否存在set方法
     *
     * @param propertyName 属性名
     * @return boolean
     */
    boolean hasSetter(final String propertyName);

}
