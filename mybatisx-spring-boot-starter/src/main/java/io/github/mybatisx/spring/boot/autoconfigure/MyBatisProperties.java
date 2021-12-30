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
package io.github.mybatisx.spring.boot.autoconfigure;

import io.github.mybatisx.session.MyBatisConfiguration;
import io.github.mybatisx.support.config.LogicDeleteConfig;
import io.github.mybatisx.support.config.MatcherConfig;
import io.github.mybatisx.support.config.MyBatisGlobalConfig;
import io.github.mybatisx.support.config.OptimisticLockConfig;
import io.github.mybatisx.support.config.PrimaryKeyConfig;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.ExecutorType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * MyBatisX配置
 * <p>
 * see: mybatis-spring-boot-starter
 *
 * @author wvkity
 * @created 2021/12/25
 * @since 1.0.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = MyBatisProperties.MYBATISX_PREFIX)
public class MyBatisProperties {

    public static final String MYBATISX_PREFIX = "mybatisx";


    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    /**
     * Location of MyBatis xml config file.
     */
    private String configLocation;

    /**
     * Locations of MyBatis mapper files.
     */
    private String[] mapperLocations = new String[]{"classpath*:/mapper/**/*.xml",
            "classpath*:/mybatis/mapper/**/*.xml"};

    /**
     * Packages to search type aliases. (Package delimiters are ",; \t\n")
     */
    private String typeAliasesPackage;

    /**
     * The super class for filtering type alias. If this not specifies, the MyBatis deal as type alias all classes that
     * searched from typeAliasesPackage.
     */
    private Class<?> typeAliasesSuperType;

    /**
     * Packages to search for type handlers. (Package delimiters are ",; \t\n")
     */
    private String typeHandlersPackage;

    /**
     * Indicates whether perform presence check of the MyBatis xml config file.
     */
    private boolean checkConfigLocation = false;

    /**
     * Execution mode for {@link org.mybatis.spring.SqlSessionTemplate}.
     */
    private ExecutorType executorType;

    /**
     * The default scripting language driver class. (Available when use together with mybatis-spring 2.0.2+)
     */
    private Class<? extends LanguageDriver> defaultScriptingLanguageDriver;

    /**
     * Externalized properties for MyBatis configuration.
     */
    private Properties configurationProperties;

    /**
     * A Configuration object for customize default settings. If {@link #configLocation} is specified, this property is
     * not used.
     */
    @NestedConfigurationProperty
    private MyBatisConfiguration configuration;

    /**
     * 全局配置
     */
    @NestedConfigurationProperty
    private MyBatisGlobalConfig globalConfig;

    /**
     * 匹配器
     */
    @NestedConfigurationProperty
    private MatcherConfig matchers = MatcherConfig.of();

    /**
     * 主键配置
     */
    @NestedConfigurationProperty
    private PrimaryKeyConfig primaryKey = PrimaryKeyConfig.of();

    /**
     * 乐观锁配置
     */
    @NestedConfigurationProperty
    private OptimisticLockConfig optimisticLock = OptimisticLockConfig.of();

    /**
     * 逻辑删除配置
     */
    @NestedConfigurationProperty
    private LogicDeleteConfig logicDelete = LogicDeleteConfig.of();

    public Resource[] resolveMapperLocations() {
        return Stream.of(Optional.ofNullable(this.mapperLocations).orElse(new String[0]))
                .flatMap(location -> Stream.of(getResources(location))).toArray(Resource[]::new);
    }

    private Resource[] getResources(String location) {
        try {
            return resourceResolver.getResources(location);
        } catch (IOException e) {
            return new Resource[0];
        }
    }

}
