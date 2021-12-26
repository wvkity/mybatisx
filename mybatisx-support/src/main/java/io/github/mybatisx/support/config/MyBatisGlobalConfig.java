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
package io.github.mybatisx.support.config;

import io.github.mybatisx.annotation.NamingStrategy;
import io.github.mybatisx.base.inject.Injector;
import io.github.mybatisx.base.mapper.BaseMapper;
import io.github.mybatisx.base.parser.EntityParser;
import io.github.mybatisx.base.parser.FieldParser;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.matcher.Matcher;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * MyBatisX全局配置
 *
 * @author wvkity
 * @created 2021/12/25
 * @since 1.0.0
 */
@Getter
@Setter
@RequiredArgsConstructor
@SuppressWarnings({"rawtypes"})
public class MyBatisGlobalConfig {

    /**
     * 默认SQL注入Mapper基类
     */
    private static Class<? extends BaseMapper> DEFAULT_INJECT_MAPPER_CLASS = BaseMapper.class;
    /**
     * SQL注入Mapper基类
     */
    @Getter(AccessLevel.NONE)
    private Class<? extends BaseMapper> injectMapperClass = DEFAULT_INJECT_MAPPER_CLASS;
    /**
     * 父类匹配器
     */
    private Matcher<Class<?>> classMatcher;
    /**
     * 属性匹配器
     */
    private Matcher<Field> fieldMatcher;
    /**
     * get方法匹配器
     */
    private Matcher<Method> getterMatcher;
    /**
     * set方法匹配器
     */
    private Matcher<Method> setterMatcher;
    /**
     * 实体解析器
     */
    private EntityParser entityParser;
    /**
     * 属性(字段)解析器
     */
    private FieldParser fieldParser;
    /**
     * SQL注入器
     */
    private Injector injector;
    /**
     * 命名策略
     */
    private NamingStrategy namingStrategy;

    public Class<?> getInjectMapperClass() {
        return Objects.ifNull(this.injectMapperClass, DEFAULT_INJECT_MAPPER_CLASS);
    }
    
}
