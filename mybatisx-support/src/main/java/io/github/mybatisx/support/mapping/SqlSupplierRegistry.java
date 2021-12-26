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
package io.github.mybatisx.support.mapping;

import io.github.mybatisx.base.exception.MyBatisException;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.reflect.Reflections;
import io.github.mybatisx.session.MyBatisConfiguration;
import io.github.mybatisx.support.mapping.proxy.SqlSupplierProxyFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SQL供应注册器
 *
 * @author wvkity
 * @created 2021/12/26
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class SqlSupplierRegistry {

    private static final Logger log = LoggerFactory.getLogger(SqlSupplierRegistry.class);
    @Getter
    private final MyBatisConfiguration configuration;
    private final Map<Class<?>, SqlSupplierProxyFactory<? extends SqlSupplier>> supplierCache =
            new ConcurrentHashMap<>();

    /**
     * 注册代理工厂
     *
     * @param type 类型
     * @param <T>  泛型
     */
    @SuppressWarnings({"unchecked"})
    public <T> void addSupplier(final Class<T> type) {
        if (Objects.nonNull(type) && !this.hasSuppler(type)) {
            try {
                final Class<?> objClass = Reflections.getGenericClass(type, 0);
                if (Objects.isAssignable(SqlSupplier.class, objClass)) {
                    this.supplierCache.putIfAbsent(type,
                            new SqlSupplierProxyFactory<>((Class<? extends SqlSupplier>) objClass));
                }
            } catch (Exception e) {
                log.warn("The SQL Supplier agent factory registration failed: {}", type);
            }
        }
    }

    /**
     * 获取{@link SqlSupplier}对象
     *
     * @param type 类
     * @param args 参数
     * @param <T>  泛型
     * @return {@link SqlSupplier}
     */
    public <T> SqlSupplier getSupplier(final Class<T> type, final Object... args) {
        final SqlSupplierProxyFactory<? extends SqlSupplier> factory = this.supplierCache.get(type);
        if (Objects.isNull(factory)) {
            throw new MyBatisException("Type" + type + " is not known to the SqlSupplierRegistry");
        }
        try {
            return factory.newInstance(this.configuration, args);
        } catch (Exception e) {
            throw new MyBatisException("Error getting supplier instance. Cause: ", e);
        }
    }

    /**
     * 检查指定类是否已注册
     *
     * @param type 类
     * @param <T>  泛型
     * @return boolean
     */
    public <T> boolean hasSuppler(final Class<T> type) {
        return Objects.nonNull(type) && this.supplierCache.containsKey(type);
    }

}
