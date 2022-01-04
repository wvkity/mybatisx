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
package io.github.mybatisx.core.mapping;

import io.github.mybatisx.base.constant.Splicing;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.lang.Types;
import io.github.mybatisx.support.config.MyBatisGlobalConfig;
import io.github.mybatisx.support.mapping.SqlSupplier;
import lombok.Getter;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 抽象SQL供应器
 *
 * @author wvkity
 * @created 2021/12/26
 * @since 1.0.0
 */
public abstract class AbstractSupplier implements SqlSupplier, SqlSymbol {

    /**
     * {@link Table}
     */
    @Getter
    protected final Table table;
    /**
     * 实体类
     */
    @Getter
    protected final Class<?> entityClass;
    /**
     * 去阿奴配置
     */
    protected final MyBatisGlobalConfig globalConfig;

    public AbstractSupplier(MyBatisGlobalConfig mgc, Table table) {
        this.table = table;
        this.entityClass = null;
        this.globalConfig = mgc;
    }

    /**
     * 拼接完整保存SQL语句
     *
     * @param columnFragment 字段部分
     * @param valueFragment  值部分
     * @return 完整SQL语句
     */
    protected String insert(final String columnFragment, final String valueFragment) {
        return "INSERT INTO " + this.table.getFullName() + SPACE + columnFragment + " VALUES " + valueFragment;
    }

    /**
     * 拼接完整更新SQL语句
     *
     * @param setFragment   set部分
     * @param whereFragment 条件部分
     * @return 完整SQL语句
     */
    protected String update(final String setFragment, final String whereFragment) {
        return "UPDATE " + this.table.getFullName() + SPACE + setFragment + (Strings.isWhitespace(whereFragment) ?
                EMPTY : SPACE + whereFragment);
    }

    /**
     * 拼接乐观锁更新值
     *
     * @param consumer {@link Consumer}
     */
    protected void optimisticLockWithSetThen(final Consumer<String> consumer) {
        if (Objects.nonNull(consumer)) {
            Optional.ofNullable(this.table.getOptimisticLockColumn()).ifPresent(it -> {
                final StringBuilder condition = new StringBuilder(80);
                condition.append("_parameter.containsKey('").append(PARAMETER_OPTIMISTIC_LOCK).append("')");
                final Class<?> javaType = it.getDescriptor().getJavaType();
                if (!javaType.isPrimitive()) {
                    condition.append(" and ").append(PARAMETER_OPTIMISTIC_LOCK).append(" != null");
                }
                if (Types.is(String.class, javaType)) {
                    condition.append(" and ").append(PARAMETER_OPTIMISTIC_LOCK).append(" !=''");
                }
                final String script = "  " + it.getColumn() + " = " + Scripts.safeJoining(PARAMETER_OPTIMISTIC_LOCK,
                        Scripts.concatTypeArg(it.getTypeHandler(), it.getJdbcType(), it.isSpliceJavaType(),
                                javaType)) + COMMA_SPACE;
                consumer.accept((NEW_LINE + Scripts.toIfTag(script, condition.toString(), true)));
            });
        }
    }

    /**
     * 拼接主键条件
     *
     * @param consumer {@link Consumer}
     */
    protected void primaryKeyWithWhereThen(final Consumer<String> consumer) {
        if (Objects.nonNull(consumer) && this.table.isHasPrimaryKey()) {
            if (this.table.isOnlyOnePrimaryKey()) {
                final Column it = this.table.getPrimaryKey();
                consumer.accept((AND_SPACE_BOTH + Scripts.toPlaceHolderArg(PARAMETER_ENTITY, Splicing.REPLACE, it)));
            } else {
                consumer.accept((AND_SPACE_BOTH + this.table.getPrimaryKeys().stream()
                        .map(it -> Scripts.toPlaceHolderArg(PARAMETER_ENTITY, Splicing.REPLACE, it))
                        .collect(Collectors.joining(AND_SPACE_BOTH))));
            }
        }
    }

    /**
     * 逻辑删除条件
     *
     * @param consumer {@link Consumer}
     */
    protected void logicDeleteWithWhereThen(final Consumer<String> consumer) {
        if (Objects.nonNull(consumer)) {
            Optional.ofNullable(this.table.getLogicDeleteColumn()).ifPresent(it -> consumer.accept((AND_SPACE_BOTH +
                    Scripts.toPlaceHolderArg(PARAMETER_ENTITY, Splicing.REPLACE, it))));
        }
    }

    /**
     * 拼接乐观锁条件
     *
     * @param consumer {@link Consumer}
     */
    protected void optimisticLockWithWhereThen(final Consumer<String> consumer) {
        if (Objects.nonNull(consumer)) {
            Optional.ofNullable(this.table.getOptimisticLockColumn()).ifPresent(it -> consumer.accept((AND_SPACE_BOTH +
                    Scripts.toPlaceHolderArg(PARAMETER_ENTITY, Splicing.REPLACE, it))));
        }
    }

    /**
     * 拼接多租户条件
     *
     * @param consumer {@link Consumer}
     */
    protected void multiTenantWithWhereThen(final Consumer<String> consumer) {
        if (Objects.nonNull(consumer)) {
            Optional.ofNullable(this.table.getMultiTenantColumn()).ifPresent(it -> consumer.accept((AND_SPACE_BOTH +
                    Scripts.toPlaceHolderArg(PARAMETER_ENTITY, Splicing.REPLACE, it))));
        }
    }

}
