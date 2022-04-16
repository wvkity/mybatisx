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
package io.github.mybatisx.base.builder;

import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.builder.AssignConsumer;
import io.github.mybatisx.builder.Builder;
import io.github.mybatisx.keyword.ReservedKeywordRegistry;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.text.MessageFormat;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 表映射对象构建器
 *
 * @author wvkity
 * @created 2021/12/26
 * @since 1.0.0
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@RequiredArgsConstructor
public class TableBuilder extends AbstractBuilder implements Builder<Table> {

    /**
     * 表名
     */
    private String name;
    /**
     * 命名空间
     */
    private String namespace;
    /**
     * 表名前缀
     */
    private String prefix;
    /**
     * 别名
     */
    private String alias;
    /**
     * 数据库目录
     */
    private String catalog;
    /**
     * 数据库模式
     */
    private String schema;
    /**
     * 排序
     */
    private String order;
    /**
     * 只有一个主键
     */
    private boolean onlyOnePrimaryKey = true;
    /**
     * 是否存在逻辑删除
     */
    private boolean logicDelete;
    /**
     * 是否存在多租户字段
     */
    private boolean multiTenant;
    /**
     * 主键属性
     */
    private String primaryKeyProperty;
    /**
     * 主键字段
     */
    private ColumnBuilder primaryKeyColumn;
    /**
     * 乐观锁字段
     */
    private ColumnBuilder optimisticLockColumn;
    /**
     * 逻辑删除字段
     */
    private ColumnBuilder logicDeleteColumn;
    /**
     * 主键列表
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final Set<ColumnBuilder> primaryKeys = new LinkedHashSet<>(2);
    /**
     * 所有字段
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final Set<ColumnBuilder> columns = new LinkedHashSet<>();

    public boolean hasPrimaryKey() {
        return Objects.nonNull(primaryKeyColumn);
    }

    public void add(final ColumnBuilder column) {
        if (Objects.nonNull(column)) {
            this.columns.add(column);
        }
    }

    public void addPrimaryKey(final ColumnBuilder column) {
        if (Objects.nonNull(column)) {
            this.primaryKeys.add(column);
        }
    }

    @Override
    public Table build() {
        final String realPrefix = Strings.ifNull(this.prefix, Strings.DEFAULT_STR_EMPTY);
        final String tableName;
        if (Strings.isNotWhitespace(this.name)) {
            tableName = realPrefix + this.name;
        } else {
            tableName = realPrefix + this.namingConverter.convert(this.entity.getSimpleName());
        }
        final String realTableName;
        if (ReservedKeywordRegistry.contains(tableName)) {
            if (this.keywordConverter != null) {
                realTableName = this.keywordConverter.convert(this.entity, tableName, true);
            } else if (Strings.isNotWhitespace(this.keywordFormatTemplate)) {
                realTableName = MessageFormat.format(this.keywordFormatTemplate, tableName);
            } else {
                realTableName = tableName;
            }
        } else {
            realTableName = tableName;
        }
        final String realCatalog = Strings.ifNull(this.catalog, Strings.DEFAULT_STR_EMPTY);
        final String realSchema = Strings.ifNull(this.schema, Strings.DEFAULT_STR_EMPTY);
        final Set<Column> columnSet = new LinkedHashSet<>(Objects.size(this.columns));
        final Set<Column> primaryKeySet = new LinkedHashSet<>(Objects.size(this.primaryKeys));
        Column primaryKeyColumn = null;
        Column logicDeleteColumn = null;
        Column versionColumn = null;
        Column multiTenantColumn = null;
        if (Objects.isNotEmpty(this.columns)) {
            for (ColumnBuilder it : this.columns) {
                final Column column = it.build();
                if (column.isPrimaryKey()) {
                    if (primaryKeyColumn == null || column.getUniqueMeta().isPriority()) {
                        primaryKeyColumn = column;
                    }
                    primaryKeySet.add(column);
                } else if (column.isLogicDelete() && logicDeleteColumn == null) {
                    logicDeleteColumn = column;
                } else if (column.isVersion() && versionColumn == null) {
                    versionColumn = column;
                } else if (column.isMultiTenant() && multiTenantColumn == null) {
                    multiTenantColumn = column;
                }
                columnSet.add(column);
            }
        }
        return new Table(this.entity, realTableName, this.namespace, realCatalog, realSchema, this.prefix, null,
                this.onlyOnePrimaryKey, primaryKeyColumn, versionColumn, logicDeleteColumn, multiTenantColumn,
                primaryKeySet, columnSet);
    }

    @Override
    public <V> Builder<Table> with(AssignConsumer<Table, V> consumer, V value) {
        // Empty
        return this;
    }

    @Override
    public void reset() {
        // Empty
    }

    public static TableBuilder create() {
        return new TableBuilder();
    }
}
