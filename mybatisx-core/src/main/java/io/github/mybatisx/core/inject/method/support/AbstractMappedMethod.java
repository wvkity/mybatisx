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
package io.github.mybatisx.core.inject.method.support;

import io.github.mybatisx.base.config.MyBatisGlobalConfig;
import io.github.mybatisx.base.config.MyBatisGlobalConfigCache;
import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.core.inject.method.MappedMethod;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.session.MyBatisConfiguration;
import io.github.mybatisx.support.mapping.SqlSupplier;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * 抽象映射方法
 *
 * @author wvkity
 * @created 2021/12/25
 * @since 1.0.0
 */
public abstract class AbstractMappedMethod implements MappedMethod {

    private static final Logger log = LoggerFactory.getLogger(AbstractMappedMethod.class);
    protected MapperBuilderAssistant mba;
    protected Configuration cfg;
    protected LanguageDriver driver;

    @Override
    public void inject(MapperBuilderAssistant mba, Table table, Class<?> mapperInterface, Class<?> returnType) {
        final Configuration config = mba.getConfiguration();
        this.mba = mba;
        this.cfg = config;
        this.driver = config.getDefaultScriptingLanguageInstance();
        if (Objects.isAssignable(MyBatisConfiguration.class, config)) {
            ((MyBatisConfiguration) config).addSupplier(this.getClass());
        }
        this.injectMappedStatement(table, mapperInterface, returnType);
    }

    /**
     * 注入{@link MappedStatement}对象
     *
     * @param mapperInterface Mapper接口
     * @param id              唯一名称
     * @param source          {@link SqlSource}
     * @param command         {@link SqlCommandType}
     * @param keyGenerator    {@link KeyGenerator}
     * @param property        主键属性
     * @param column          主键列
     * @param parameterType   参数类型
     * @param returnType      返回值类型
     * @param resultMap       返回结果集
     */
    protected void addMappedStatement(final Class<?> mapperInterface, final String id, final SqlSource source,
                                      final SqlCommandType command, final KeyGenerator keyGenerator,
                                      final String property, final String column, final Class<?> parameterType,
                                      final Class<?> returnType, final String resultMap) {
        final String realMsName;
        if (Strings.isWhitespace(id)) {
            realMsName = this.getInvokeMethod();
        } else {
            realMsName = id;
        }
        final String msId = mapperInterface.getName() + "." + realMsName;
        if (this.hasStatement(msId)) {
            log.warn("The `{}` MappedStatement object has been loaded into the container by XML or SqlProvider " +
                    "configuration, and the SQL injection is automatically ignored.", msId);
            return;
        }
        final boolean isSelect = command == SqlCommandType.SELECT;
        this.mba.addMappedStatement(realMsName, source, StatementType.PREPARED, command, null, null, null,
                parameterType, resultMap, returnType, null, !isSelect, isSelect, false, keyGenerator, property,
                column, this.cfg.getDatabaseId(), this.driver, null);
    }

    /**
     * 注入{@link MappedStatement}对象(保存类型)
     *
     * @param mapperInterface Mapper接口
     * @param id              唯一名称
     * @param source          {@link SqlSource}
     * @param keyGenerator    {@link KeyGenerator}
     * @param property        主键属性
     * @param column          主键列
     * @param parameterType   参数类型
     */
    protected void addInsertMappedStatement(final Class<?> mapperInterface, final String id, final SqlSource source,
                                            final KeyGenerator keyGenerator, final String property, final String column,
                                            final Class<?> parameterType) {
        this.addMappedStatement(mapperInterface, id, source, SqlCommandType.INSERT, keyGenerator, property, column,
                parameterType, Integer.class, null);
    }

    /**
     * 注入{@link MappedStatement}对象(更新类型)
     *
     * @param mapperInterface Mapper接口
     * @param id              唯一名称
     * @param source          {@link SqlSource}
     * @param parameterType   参数类型
     */
    protected void addUpdateMappedStatement(final Class<?> mapperInterface, final String id,
                                            final SqlSource source, final Class<?> parameterType) {
        this.addMappedStatement(mapperInterface, id, source, SqlCommandType.UPDATE, new NoKeyGenerator(),
                null, null, parameterType, Integer.class, null);
    }

    /**
     * 注入{@link MappedStatement}对象(删除类型)
     *
     * @param mapperInterface Mapper接口
     * @param id              唯一名称
     * @param source          {@link SqlSource}
     * @param parameterType   参数类型
     */
    protected void addDeleteMappedStatement(final Class<?> mapperInterface, final String id, final SqlSource source,
                                            final Class<?> parameterType) {
        this.addMappedStatement(mapperInterface, id, source, SqlCommandType.DELETE, new NoKeyGenerator(), null, null,
                parameterType, Integer.class, null);
    }

    /**
     * 注入{@link MappedStatement}对象(查询类型)
     *
     * @param mapperInterface Mapper接口
     * @param id              唯一名称
     * @param source          {@link SqlSource}
     * @param parameterType   参数类型
     * @param returnType      返回值类型
     * @param resultMap       返回结果集
     */
    protected void addSelectMappedStatement(final Class<?> mapperInterface, final String id, final SqlSource source,
                                            final Class<?> parameterType, final Class<?> returnType,
                                            final String resultMap) {
        this.addMappedStatement(mapperInterface, id, source, SqlCommandType.INSERT, new NoKeyGenerator(), null, null,
                parameterType, returnType, resultMap);
    }

    /**
     * 创建{@link SqlSource}对象
     *
     * @param supplier      SQL脚本供应器
     * @param parameterType 参数类型
     * @return {@link SqlSource}
     */
    protected SqlSource createSource(final SqlSupplier supplier, final Class<?> parameterType) {
        return this.createSource(this.getScript(supplier), parameterType);
    }

    /**
     * 创建{@link SqlSource}对象
     *
     * @param script        SQL脚本
     * @param parameterType 参数类型
     * @return {@link SqlSource}
     */
    protected SqlSource createSource(final String script, final Class<?> parameterType) {
        return this.driver.createSqlSource(this.cfg, script, parameterType);
    }

    /**
     * 构建完整SQL脚本
     *
     * @param supplier {@link SqlSupplier}
     * @return 完整SQL脚本
     */
    protected String getScript(final SqlSupplier supplier) {
        return this.getScript(supplier.get());
    }

    /**
     * 构建完整SQL脚本
     *
     * @param script 脚本内容
     * @return 完整SQL脚本
     */
    protected String getScript(final String script) {
        if (Strings.isNotWhitespace(script)) {
            if (script.toLowerCase(Locale.ENGLISH).startsWith(Constants.SCRIPT_OPEN)) {
                return script;
            }
            return Constants.SCRIPT_OPEN + script + Constants.SCRIPT_CLOSE;
        }
        return script;
    }

    /**
     * 获取全局配置对象
     *
     * @return 全局配置对象
     */
    protected MyBatisGlobalConfig getGlobalConfig() {
        return MyBatisGlobalConfigCache.getGlobalConfig(this.cfg);
    }

    /**
     * 获取注入映射方法名
     *
     * @return 方法名
     */
    protected String getInvokeMethod() {
        return Strings.firstToLowerCase(this.getClass().getSimpleName());
    }

    /**
     * 检查是否存在指定的{@link MappedStatement}对象
     *
     * @param msId {@link MappedStatement}唯一标识
     * @return boolean
     */
    protected boolean hasStatement(final String msId) {
        return this.cfg.hasStatement(msId, false);
    }

    /**
     * 注入{@link MappedStatement}对象
     *
     * @param table           {@link Table}
     * @param mapperInterface Mapper接口
     * @param returnType      返回值类型
     */
    public abstract void injectMappedStatement(final Table table, final Class<?> mapperInterface,
                                               final Class<?> returnType);
}
