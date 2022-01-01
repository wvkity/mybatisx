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
package io.github.mybatisx.base.metadata;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 数据库表映射
 *
 * @author wvkity
 * @created 2021/12/23
 * @since 1.0.0
 */
@Getter
@ToString
@EqualsAndHashCode
public class Table {

    /**
     * 实体类
     */
    @EqualsAndHashCode.Include
    private final Class<?> entity;
    /**
     * 数据库表名
     */
    @EqualsAndHashCode.Include
    private final String name;
    /**
     * Mapper接口命名空间
     */
    @EqualsAndHashCode.Include
    private final String namespace;
    /**
     * 数据库目录
     */
    private final String catalog;
    /**
     * 数据库模式
     */
    private final String schema;
    /**
     * 数据库表名前缀
     */
    private final String prefix;
    /**
     * 排序
     */
    private final String order;
    /**
     * 仅有一个主键
     */
    @EqualsAndHashCode.Include
    private final boolean onlyOnePrimaryKey;
    /**
     * 是否存在主键
     */
    @EqualsAndHashCode.Include
    private final boolean hasPrimaryKey;
    /**
     * 主键字段
     */
    @EqualsAndHashCode.Include
    private final Column primaryKey;
    /**
     * 乐观锁标识字段
     */
    private final Column optimisticLockColumn;
    /**
     * 逻辑删除标识字段
     */
    private final Column logicDeleteColumn;
    /**
     * 多租户标识字段
     */
    private final Column multiTenantColumn;
    /**
     * 复合主键列表
     */
    private final List<Column> primaryKeys;
    /**
     * 所有字段列表
     */
    @EqualsAndHashCode.Include
    private final Set<Column> columns;
    /**
     * 可保存字段列表
     */
    private final List<Column> insertableColumns;
    /**
     * 可更新字段列表
     */
    private final List<Column> updatableColumns;
    /**
     * 逻辑删除审计字段列表
     */
    private final List<Column> logicDeleteAuditColumns;
    /**
     * 属性-字段映射
     */
    private final Map<String, Column> columnMap;

    public Table(Class<?> entity, String name, String namespace, String catalog, String schema, String prefix,
                 String order, boolean onlyOnePrimaryKey, Column primaryKey, Column optimisticLockColumn,
                 Column logicDeleteColumn, Column multiTenantColumn, Set<Column> primaryKeys, Set<Column> columns) {
        this.entity = entity;
        this.name = name;
        this.namespace = namespace;
        this.catalog = catalog;
        this.schema = schema;
        this.prefix = prefix;
        this.order = order;
        this.onlyOnePrimaryKey = onlyOnePrimaryKey;
        this.primaryKey = primaryKey;
        this.primaryKeys = ImmutableList.copyOf(primaryKeys);
        this.hasPrimaryKey = Objects.nonNull(primaryKey) || Objects.isNotEmpty(primaryKeys);
        this.optimisticLockColumn = optimisticLockColumn;
        this.logicDeleteColumn = logicDeleteColumn;
        this.multiTenantColumn = multiTenantColumn;
        this.columns = ImmutableSet.copyOf(columns);
        this.insertableColumns = ImmutableList.copyOf(this.filtrate(Column::isInsertable));
        this.updatableColumns = ImmutableList.copyOf(this.filtrate(Column::isUpdatable));
        this.logicDeleteAuditColumns = ImmutableList.copyOf(this.filtrate(this.updatableColumns, Column::isDeletable));
        this.columnMap = ImmutableMap.copyOf(this.columns.stream().collect(Collectors.toMap(Column::getProperty,
                Function.identity())));
    }

    /**
     * 获取表全名
     *
     * @return 表名
     */
    public String getFullName() {
        if (Strings.isNotWhitespace(this.catalog)) {
            return this.catalog + "." + this.name;
        } else if (Strings.isNotWhitespace(this.schema)) {
            return this.schema + "." + this.name;
        }
        return this.name;
    }

    /**
     * 筛选字段
     *
     * @param filter {@link Predicate}
     * @return 字段集合
     */
    public List<Column> filtrate(final Predicate<Column> filter) {
        return this.filtrate(this.columns, filter);
    }

    /**
     * 筛选字段
     *
     * @param columns 待筛选字段列表
     * @param filter  {@link Predicate}
     * @return 字段集合
     */
    public List<Column> filtrate(final Collection<Column> columns, final Predicate<Column> filter) {
        return columns.stream().filter(filter).collect(Collectors.toList());
    }
}
