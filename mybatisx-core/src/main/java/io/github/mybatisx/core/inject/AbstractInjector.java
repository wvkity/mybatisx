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
package io.github.mybatisx.core.inject;

import io.github.mybatisx.base.inject.Injector;
import io.github.mybatisx.base.mapper.EasilyMapper;
import io.github.mybatisx.base.mapper.IdenticalMapper;
import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.core.inject.method.MappedMethod;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.support.config.MyBatisGlobalConfig;
import io.github.mybatisx.support.config.MyBatisGlobalConfigCache;
import io.github.mybatisx.support.helper.TableHelper;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象SQL注入器
 *
 * @author wvkity
 * @created 2021/12/25
 * @since 1.0.0
 */
public abstract class AbstractInjector implements Injector {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 映射方法缓存
     */
    protected static final Map<Class<?>, Set<MappedMethod>> ALL_MAPPED_METHOD_CACHE = new ConcurrentHashMap<>();
    /**
     * 主键相关方法
     */
    protected static final Map<Class<?>, Set<MappedMethod>> PRIMARY_KEY_MAPPED_METHOD_CACHE = new ConcurrentHashMap<>();
    /**
     * 通用方法
     */
    protected static final Map<Class<?>, Set<MappedMethod>> GENERIC_MAPPED_METHOD_CACHE = new ConcurrentHashMap<>();
    
    @Override
    public void inject(MapperBuilderAssistant mba, Class<?> mapperInterface) {
        final Configuration cfg = mba.getConfiguration();
        final MyBatisGlobalConfig mgc = MyBatisGlobalConfigCache.getGlobalConfig(cfg);
        if (Objects.isAssignable(mgc.getInjectMapperClass(), mapperInterface)) {
            final Class<?>[] genericTypes = this.getGenericTypes(mapperInterface);
            if (Objects.isNotEmpty(genericTypes)) {
                final Class<?> entityClass = genericTypes[0];
                Optional.ofNullable(entityClass).ifPresent(it -> {
                    if (MyBatisGlobalConfigCache.registryInterfaceIfNotExists(cfg, mapperInterface)) {
                        final Table table = TableHelper.parse(mba, entityClass);
                        final Collection<MappedMethod> methods = this.getMappedMethods(table, mapperInterface);
                        if (Objects.isNotEmpty(methods)) {
                            methods.forEach(m -> m.inject(mba, table, mapperInterface,
                                    this.getReturnType(genericTypes, mapperInterface)));
                        }
                    }
                });
            }
        }
    }

    /**
     * 获取查询方法返回值类型
     *
     * @param classes         泛型类列表
     * @param mapperInterface Mapper接口
     * @return 返回值类型
     */
    protected Class<?> getReturnType(final Class<?>[] classes, final Class<?> mapperInterface) {
        return (Objects.isAssignable(IdenticalMapper.class, mapperInterface) || Objects.isAssignable(EasilyMapper.class,
                mapperInterface)) ? classes[0] : classes[1];
    }

    /**
     * 获取泛型类型
     *
     * @param mapperInterface 接口
     * @return 泛型类型列表
     */
    protected Class<?>[] getGenericTypes(final Class<?> mapperInterface) {
        final Type[] interfaces = mapperInterface.getGenericInterfaces();
        for (Type type : interfaces) {
            if (type instanceof ParameterizedType) {
                final Type[] genericTypes = ((ParameterizedType) type).getActualTypeArguments();
                for (Type it : genericTypes) {
                    if (!Objects.isAssignable(TypeVariable.class, it) && !Objects.isAssignable(WildcardType.class, it)) {
                        return this.toClass(genericTypes);
                    }
                }
            }
        }
        return null;
    }

    /**
     * {@link Type}转{@link Class}
     *
     * @param types {@link Type}列表
     * @return 类列表
     */
    protected Class<?>[] toClass(final Type[] types) {
        final Class<?>[] classes = new Class[types.length];
        for (int i = 0, size = types.length; i < size; i++) {
            classes[i] = (Class<?>) types[i];
        }
        return classes;
    }

    /**
     * 获取映射方法列表
     *
     * @param table           表映射对象
     * @param mapperInterface Mapper接口
     * @return {@link MappedMethod}列表
     */
    public abstract Collection<MappedMethod> getMappedMethods(final Table table, final Class<?> mapperInterface);

}
