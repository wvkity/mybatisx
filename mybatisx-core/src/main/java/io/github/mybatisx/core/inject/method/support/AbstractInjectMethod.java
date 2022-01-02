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

import io.github.mybatisx.annotation.ExecuteType;
import io.github.mybatisx.base.keygen.SequenceGenerator;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.base.metadata.Descriptor;
import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.base.metadata.UniqueMeta;
import io.github.mybatisx.core.mapping.SupplierBuilder;
import io.github.mybatisx.core.mapping.SupplierWeakCache;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.session.MyBatisConfiguration;
import io.github.mybatisx.support.config.MyBatisGlobalConfig;
import io.github.mybatisx.support.config.MyBatisGlobalConfigCache;
import io.github.mybatisx.support.mapping.SqlSupplier;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;

import java.util.Optional;

/**
 * 抽象注入方法
 *
 * @param <T> SQL供应器
 * @author wvkity
 * @created 2022/1/1
 * @since 1.0.0
 */
public abstract class AbstractInjectMethod<T extends SqlSupplier> extends AbstractMappedMethod implements
        SupplierBuilder<T> {

    /**
     * 添加{@link MappedStatement}对象到容器中(保持操作)
     *
     * @param table           {@link Table}
     * @param mapperInterface Mapper接口
     * @param returnType      返回值类型
     */
    protected void addInsertMappedStatement(final Table table, final Class<?> mapperInterface,
                                            final Class<?> returnType) {
        final String msName = this.getInvokeMethod();
        final boolean hasPrimaryKey = table.isHasPrimaryKey();
        final Class<?> entity = table.getEntity();
        final KeyGenerator kg;
        if (hasPrimaryKey) {
            final Column id = table.getPrimaryKey();
            final UniqueMeta unique = id.getUniqueMeta();
            if (table.isOnlyOnePrimaryKey() || unique.isPriority()) {
                final String msId = mapperInterface.getName() + "." + msName;
                this.addInsertMappedStatement(mapperInterface, msName,
                        this.createSource(this.build(this.getGlobalConfig(), table), entity),
                        this.createKeyGenerator(table, msId), id.getProperty(), id.getColumn(), entity);
            }
        } else {
            kg = new NoKeyGenerator();
            this.addInsertMappedStatement(mapperInterface, msName, this.createSource(this.build(), entity), kg, null,
                    null, entity);
        }
    }

    /**
     * 添加{@link MappedStatement}对象到容器中(更新操作)
     *
     * @param table           {@link Table}
     * @param mapperInterface Mapper接口
     * @param returnType      返回值类型
     */
    protected void addUpdateMappedStatement(final Table table, final Class<?> mapperInterface,
                                            final Class<?> returnType) {
        final Class<?> entity = table.getEntity();
        this.addUpdateMappedStatement(mapperInterface, this.getInvokeMethod(),
                this.createSource(this.build(this.getGlobalConfig(), table), entity), entity);
    }

    /**
     * 添加{@link MappedStatement}对象到容器中(删除操作)
     *
     * @param table           {@link Table}
     * @param mapperInterface Mapper接口
     * @param returnType      返回值类型
     */
    protected void addDeleteMappedStatement(final Table table, final Class<?> mapperInterface,
                                            final Class<?> returnType) {
        final Class<?> entity = table.getEntity();
        this.addDeleteMappedStatement(mapperInterface, this.getInvokeMethod(),
                this.createSource(this.build(this.getGlobalConfig(), table), entity), entity);
    }

    /**
     * 添加{@link MappedStatement}对象到容器中(查询操作)
     *
     * @param table           {@link Table}
     * @param mapperInterface Mapper接口
     * @param returnType      返回值类型
     */
    protected void addSelectMappedStatement(final Table table, final Class<?> mapperInterface,
                                            final Class<?> returnType) {
        final Class<?> entity = table.getEntity();
        this.addSelectMappedStatement(mapperInterface, this.getInvokeMethod(),
                this.createSource(this.build(this.getGlobalConfig(), table), entity), entity, returnType, null);
    }

    /**
     * 创建主键生成器
     *
     * @param table {@link Table}
     * @param msId  {@link MappedStatement}唯一标识
     * @return {@link KeyGenerator}
     */
    protected KeyGenerator createKeyGenerator(final Table table, final String msId) {
        KeyGenerator kg = new NoKeyGenerator();
        if (table.isHasPrimaryKey()) {
            final Column id = table.getPrimaryKey();
            final UniqueMeta unique = id.getUniqueMeta();
            final String sequence;
            if (table.isOnlyOnePrimaryKey() || unique.isPriority()) {
                if (unique.isIdentity()) {
                    kg = new Jdbc3KeyGenerator();
                    this.cfg.setUseGeneratedKeys(true);
                } else if (Strings.isNotWhitespace((sequence = unique.getSequence()))) {
                    final MyBatisGlobalConfig mgc = MyBatisGlobalConfigCache.getGlobalConfig(this.cfg);
                    final SequenceGenerator sg;
                    if (mgc != null && (Objects.nonNull((sg = mgc.getSequenceGenerator())))) {
                        final Descriptor descriptor = id.getDescriptor();
                        final String script = sg.getScript(sequence);
                        final SqlSource source = this.driver.createSqlSource(this.cfg, script, null);
                        this.mba.addMappedStatement((msId + SelectKeyGenerator.SELECT_KEY_SUFFIX), source,
                                StatementType.PREPARED, SqlCommandType.SELECT, null, null, null, null, null,
                                descriptor.getJavaType(), null, false, false, false, new NoKeyGenerator(),
                                descriptor.getName(), id.getColumn(), null, this.driver, null);
                        final String newMsId = this.mba.applyCurrentNamespace(msId, false);
                        final MappedStatement ms = this.cfg.getMappedStatement(newMsId, false);
                        kg = new SelectKeyGenerator(ms, Optional.ofNullable(unique.getStrategy())
                                .map(ExecuteType::isBefore).orElse(false));
                        this.mba.getConfiguration().addKeyGenerator(newMsId, kg);
                        this.mba.getConfiguration().setUseGeneratedKeys(true);
                    }
                }
            }
        }
        return kg;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public T build(Object... args) {
        if (this.cfg instanceof MyBatisConfiguration) {
            return (T) ((MyBatisConfiguration) this.cfg).getSupplier(getClass(), args);
        }
        return (T) SupplierWeakCache.newInstance(this.getClass(), args);
    }

}
