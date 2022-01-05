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
package io.github.mybatisx.base.config;

import io.github.mybatisx.annotation.NamingStrategy;
import io.github.mybatisx.auditable.config.AuditConfig;
import io.github.mybatisx.base.inject.Injector;
import io.github.mybatisx.base.keygen.SequenceGenerator;
import io.github.mybatisx.base.mapper.BaseMapper;
import io.github.mybatisx.base.parsing.EntityParser;
import io.github.mybatisx.base.type.JdbcTypeMappingRegistry;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.id.Sequence;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;

import java.util.Optional;

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
     * 实体解析器
     */
    private EntityParser entityParser;
    /**
     * SQL注入器
     */
    private Injector injector;
    /**
     * 匹配器配置
     */
    private MatcherConfig matchers;
    /**
     * 实体类名原策略
     */
    private NamingStrategy entitySrcNaming = NamingStrategy.UPPER_CAMEL;
    /**
     * 实体类属性名原策略
     */
    private NamingStrategy propertySrcNaming = NamingStrategy.LOWER_CAMEL;
    /**
     * 命名策略
     */
    private NamingStrategy naming = NamingStrategy.UPPER_UNDERSCORE;
    /**
     * 使用简单类型
     */
    private boolean useSimpleType = true;
    /**
     * 枚举类型转成简单类型
     */
    private boolean enumAsSimpleType;
    /**
     * 支持JPA注解
     */
    private boolean jpaSupport;
    /**
     * 关键词格式化模板
     */
    private String keywordFormatTemplate;
    /**
     * 表名前缀
     */
    private String tablePrefix;
    /**
     * 数据库schema属性
     */
    private String schema;
    /**
     * 数据库catalog属性
     */
    private String catalog;
    /**
     * 布尔属性是否自动添加IS前缀
     */
    private boolean autoAddedIsPrefixForBooleanProp;
    /**
     * 是否自动拼接JAVA类名
     */
    private boolean spliceJavaType;
    /**
     * 动态SQL中非空检查
     */
    private boolean checkNull;
    /**
     * 动态SQL中非空值检查
     */
    private boolean checkEmpty;
    /**
     * 是否自动映射JDBC类型
     */
    private boolean autoMappingJdbcType;
    /**
     * 覆盖boolean类型属性默认映射的JDBC类型
     * <p>
     * 当{@code autoMappingJdbcType}等于true时且{@code overrideBooleanJdbcType}不等{@link JdbcType#UNDEFINED}
     * 会覆盖{@link JdbcTypeMappingRegistry}中的默认值，如果不需要覆盖请使用{@link io.github.mybatisx.annotation.Extra#jdbcType()}
     * 指定对应的{@link JdbcType}即可
     */
    private JdbcType overrideBooleanJdbcType = JdbcType.UNDEFINED;
    /**
     * 序列生成器
     */
    private SequenceGenerator sequenceGenerator;
    /**
     * 唯一标识生成器
     */
    private Sequence sequence;
    /**
     * 主键配置
     */
    private PrimaryKeyConfig primaryKey;
    /**
     * 乐观锁配置
     */
    private OptimisticLockConfig optimisticLock;
    /**
     * 逻辑删除配置
     */
    private LogicDeleteConfig logicDelete;
    /**
     * 审计配置
     */
    private AuditConfig audit;

    public Class<?> getInjectMapperClass() {
        return Objects.ifNull(this.injectMapperClass, DEFAULT_INJECT_MAPPER_CLASS);
    }

    /**
     * 缓存当前全局配置
     *
     * @param cfg {@link Configuration}
     */
    public void cacheSelf(final Configuration cfg) {
        Optional.ofNullable(cfg).ifPresent(it -> MyBatisGlobalConfigCache.cacheMyBatisGlobalConfig(cfg, this));
    }
}
