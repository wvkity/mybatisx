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
package io.github.mybatisx.binding;

import io.github.mybatisx.builder.annotation.MyBatisMapperAnnotationBuilder;
import io.github.mybatisx.session.MyBatisConfiguration;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 重写{@link MapperRegistry}
 *
 * @author wvkity
 * @created 2021/12/24
 * @since 1.0.0
 */
public class MyBatisMapperRegistry extends MapperRegistry {

    private static final Logger log = LoggerFactory.getLogger(MyBatisMapperRegistry.class);
    private final MyBatisConfiguration config;
    private final Map<Class<?>, MyBatisMapperProxyFactory<?>> knownMappers = new HashMap<>();

    public MyBatisMapperRegistry(MyBatisConfiguration config) {
        super(config);
        this.config = config;
    }

    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        final MyBatisMapperProxyFactory<T> mapperProxyFactory = (MyBatisMapperProxyFactory<T>) knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new BindingException("Type " + type + " is not known to the MapperRegistry.");
        }
        try {
            return mapperProxyFactory.newInstance(sqlSession);
        } catch (Exception e) {
            throw new BindingException("Error getting mapper instance. Cause: " + e, e);
        }
    }

    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }

    public <T> void addMapper(Class<T> type) {
        if (type.isInterface()) {
            if (hasMapper(type)) {
                // throw new BindingException("Type " + type + " is already known to the MapperRegistry.");
                log.warn("Type {} is already known to the MapperRegistry.", type);
                return;
            }
            boolean loadCompleted = false;
            try {
                knownMappers.put(type, new MyBatisMapperProxyFactory<>(type));
                // It's important that the type is added before the parser is run
                // otherwise the binding may automatically be attempted by the
                // mapper parser. If the type is already known, it won't try.
                final MyBatisMapperAnnotationBuilder parser = new MyBatisMapperAnnotationBuilder(config, type);
                parser.parse();
                loadCompleted = true;
            } finally {
                if (!loadCompleted) {
                    knownMappers.remove(type);
                }
            }
        }
    }


    /**
     * @since 3.2.2
     */
    public Collection<Class<?>> getMappers() {
        return Collections.unmodifiableCollection(knownMappers.keySet());
    }

}
