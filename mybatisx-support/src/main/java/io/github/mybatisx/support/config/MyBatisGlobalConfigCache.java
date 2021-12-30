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
import io.github.mybatisx.base.exception.MyBatisException;
import io.github.mybatisx.base.inject.Injector;
import io.github.mybatisx.lang.Objects;
import org.apache.ibatis.session.Configuration;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * MyBatisX全局配置缓存
 *
 * @author wvkity
 * @created 2021/12/25
 * @since 1.0.0
 */
public class MyBatisGlobalConfigCache {

    private MyBatisGlobalConfigCache() {
    }

    /**
     * 默认SQL注入器
     */
    private static final String DEFAULT_SQL_INJECTOR_CLASS = "io.github.mybatisx.core.inject.DefaultInjector";
    /**
     * 全局配置缓存
     */
    private static final Map<String, MyBatisGlobalConfig> MY_BATIS_GLOBAL_CONFIG_CACHE = new ConcurrentHashMap<>();
    /**
     * 已注册Mapper接口缓存
     */
    private static final Map<String, Set<String>> MAPPER_INTERFACE_REGISTRY_CACHE = new ConcurrentHashMap<>();

    /**
     * 创建全局配置对象
     *
     * @return {@link MyBatisGlobalConfig}
     */
    public static MyBatisGlobalConfig newInstance() {
        final MyBatisGlobalConfig mgc = new MyBatisGlobalConfig();
        mgc.setNaming(NamingStrategy.UPPER_UNDERSCORE);
        return mgc;
    }

    /**
     * 获取全局配置对象
     *
     * @param cfg {@link Configuration}
     * @return 全局配置对象
     */
    public static MyBatisGlobalConfig getGlobalConfig(final Configuration cfg) {
        return getGlobalConfig(cfg.toString());
    }

    /**
     * 获取全局配置对象
     *
     * @param id 唯一标识
     * @return 全局配置对象
     */
    public static MyBatisGlobalConfig getGlobalConfig(final String id) {
        return Objects.computeIfAbsent(MY_BATIS_GLOBAL_CONFIG_CACHE, id, it -> newInstance());
    }

    /**
     * 缓存全局配置对象
     *
     * @param cfg {@link Configuration}
     * @param mgc {@link MyBatisGlobalConfig}
     */
    public static void cacheMyBatisGlobalConfig(final Configuration cfg, final MyBatisGlobalConfig mgc) {
        Objects.isTrue(Objects.nonNull(cfg), "Mybatis configuration object cannot be null, please initialize it first");
        Objects.isTrue(Objects.nonNull(mgc), "Mybatis global configuration object cannot be null");
        MY_BATIS_GLOBAL_CONFIG_CACHE.putIfAbsent(cfg.toString(), mgc);
    }

    /**
     * 获取SQL注入器
     *
     * @param cfg {@link Configuration}
     * @return {@link Injector}
     */
    public static Injector getInjector(final Configuration cfg) {
        final MyBatisGlobalConfig mgc = getGlobalConfig(cfg);
        return Optional.ofNullable(mgc.getInjector()).orElseGet(() -> {
            final Injector injector = newInjector();
            mgc.setInjector(injector);
            return injector;
        });
    }

    /**
     * 创建{@link Injector}对象
     *
     * @return {@link Injector}
     */
    public static Injector newInjector() {
        try {
            final Class<?> clazz = Class.forName(DEFAULT_SQL_INJECTOR_CLASS);
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            return (Injector) lookup.findConstructor(clazz, MethodType.methodType(void.class)).invokeWithArguments();
        } catch (Throwable e) {
            throw new MyBatisException(e.getMessage(), e);
        }
    }

    /**
     * 注册Mapper接口到缓存中(如果缓存中不存在)
     *
     * @param cfg             {@link Configuration}
     * @param mapperInterface Mapper接口
     * @return boolean
     */
    public static boolean registryInterfaceIfNotExists(final Configuration cfg, final Class<?> mapperInterface) {
        final String key = cfg.toString();
        final String value = mapperInterface.getName();
        return Objects.computeIfAbsent(MAPPER_INTERFACE_REGISTRY_CACHE, key,
                k -> new ConcurrentSkipListSet<>()).add(value);
    }

}
