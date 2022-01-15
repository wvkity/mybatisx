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

import io.github.mybatisx.annotation.ExecuteType;
import io.github.mybatisx.auditable.metadata.AuditMode;
import io.github.mybatisx.auditable.metadata.AuditType;
import io.github.mybatisx.base.metadata.AuditMeta;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.base.metadata.Descriptor;
import io.github.mybatisx.base.metadata.LogicDeleteMeta;
import io.github.mybatisx.base.metadata.UniqueMeta;
import io.github.mybatisx.base.metadata.VersionMeta;
import io.github.mybatisx.builder.AssignConsumer;
import io.github.mybatisx.builder.Builder;
import io.github.mybatisx.keyword.ReservedKeywordRegistry;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Set;

/**
 * 字段映射构建器
 *
 * @author wvkity
 * @created 2021/12/26
 * @since 1.0.0
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ColumnBuilder extends AbstractBuilder implements Builder<Column> {

    /**
     * 属性信息
     */
    @EqualsAndHashCode.Include
    private Field field;
    /**
     * 属性名
     */
    @EqualsAndHashCode.Include
    private String property;
    /**
     * 数据库字段
     */
    private String column;
    /**
     * JAVA类型
     */
    private Class<?> javaType;
    /**
     * JDBC类型
     */
    private JdbcType jdbcType;
    /**
     * 类型处理器
     */
    private Class<? extends TypeHandler<?>> typeHandler;
    /**
     * 主键SQL执行时机
     */
    private ExecuteType executeType;
    /**
     * Get方法
     */
    private Method readable;
    /**
     * Set方法
     */
    private Method writable;
    /**
     * 主键生成方式
     */
    private String generator;
    /**
     * 序列
     */
    private String sequence;
    /**
     * 是否为主键
     */
    private boolean primaryKey;
    /**
     * 优先
     */
    private boolean priority;
    /**
     * 主键是否为UUID
     */
    private boolean uuid;
    /**
     * 主键是否为自增
     */
    private boolean identity;
    /**
     * 主键是否为雪花算法ID
     */
    private boolean snowflake;
    /**
     * 字段是否为BLOB类型
     */
    private boolean blob;
    /**
     * 字段是否可保存
     */
    private boolean insertable = true;
    /**
     * 字段是否可更新
     */
    private boolean updatable = true;
    /**
     * SQL语句是否设置JAVA类型
     */
    private boolean spliceJavaType;
    /**
     * null校验
     */
    private boolean checkNull;
    /**
     * 字符串空值校验
     */
    private boolean checkEmpty;
    /**
     * 是否为乐观锁
     */
    private boolean version;
    /**
     * 保存时乐观锁值是否自动初始化
     */
    private boolean versionInit;
    /**
     * 乐观锁初始化值
     */
    private Integer versionInitValue;
    /**
     * 是否为多租户
     */
    private boolean multiTenant;
    /**
     * 是否为保存操作用户ID自动填充标识
     */
    private boolean createdBy;
    /**
     * 是否为保存操作用户名自动填充标识
     */
    private boolean createdByName;
    /**
     * 是否为保存操作时间自动填充标识
     */
    private boolean createdDate;
    /**
     * 是否为更新操作用户ID自动填充标识
     */
    private boolean lastModifiedBy;
    /**
     * 是否为更新操作用户名自动填充标识
     */
    private boolean lastModifiedByName;
    /**
     * 是否为更新操作时间自动填充标识
     */
    private boolean lastModifiedDate;
    /**
     * 是否为删除操作用户ID自动填充标识
     */
    private boolean deletedBy;
    /**
     * 是否为删除操作用户名自动填充标识
     */
    private boolean deletedByName;
    /**
     * 是否为删除操作时间自动填充标识
     */
    private boolean deletedDate;
    /**
     * 审计类型
     */
    private AuditType auditType;
    /**
     * 审计模式
     */
    private Set<AuditMode> auditModes;
    /**
     * boolean类型属性对应表字段自动添加IS前缀
     */
    private boolean autoAddedIsPrefix;
    /**
     * 是否为逻辑删除标识
     */
    private boolean logicDelete;
    /**
     * 是否初始化
     */
    private boolean logicDeleteInit;
    /**
     * 未删除标识值
     */
    private Object yet;
    /**
     * 已删除标识值
     */
    private Object already;

    public boolean hasPrimaryKeyStrategy() {
        return this.identity || this.uuid || this.snowflake || Strings.isNotWhitespace(this.sequence);
    }

    public boolean canAuditParsing() {
        return (this.insertable || this.updatable) && !(this.primaryKey || this.version || this.logicDelete);
    }

    @Override
    public Column build() {
        // 描述信息
        final Descriptor descriptor = new Descriptor(this.entity, this.property, this.field, this.javaType,
                this.readable, this.writable);
        // 审计信息
        AuditMeta auditMeta = null;
        if (this.auditType != null) {
            auditMeta = new AuditMeta(this.multiTenant, this.createdBy, this.createdByName, this.createdDate,
                    this.lastModifiedBy, this.lastModifiedByName, this.lastModifiedDate, this.deletedBy,
                    this.deletedByName, this.deletedDate, this.auditType, this.auditModes);
        }
        UniqueMeta uniqueMeta = null;
        if (this.primaryKey) {
            uniqueMeta = new UniqueMeta(this.priority, this.uuid, this.identity, this.snowflake, this.generator,
                    this.sequence, this.executeType);
        }
        VersionMeta versionMeta = null;
        if (this.version) {
            versionMeta = new VersionMeta(true, this.versionInit, this.versionInitValue);
        }
        LogicDeleteMeta logicDeleteMeta = null;
        if (this.logicDelete) {
            logicDeleteMeta = new LogicDeleteMeta(true, this.logicDeleteInit, this.yet, this.already);
        }
        return new Column(this.entity, this.property, this.handleColumn(), this.jdbcType, this.typeHandler,
                this.primaryKey, this.blob, this.insertable, this.updatable, this.spliceJavaType, this.checkNull,
                this.checkEmpty, this.version, this.multiTenant, this.logicDelete, this.autoAddedIsPrefix,
                descriptor, uniqueMeta, auditMeta, versionMeta, logicDeleteMeta);
    }

    private String handleColumn() {
        final String realColumn;
        if (Strings.isWhitespace(this.column)) {
            final String columnName;
            if (this.autoAddedIsPrefix && Objects.isAssignable(Boolean.class, this.javaType)) {
                if (Strings.isLowerStartsWith(this.property)) {
                    columnName = "is" + Strings.firstToUpperCase(this.property);
                } else {
                    columnName = "Is" + this.property;
                }
            } else {
                columnName = this.property;
            }
            realColumn = this.namingConverter.convert(columnName);
        } else {
            realColumn = this.column;
        }
        this.autoAddedIsPrefix = this.autoAddedIsPrefix && Objects.isAssignable(Boolean.class, this.javaType);
        if (Strings.isNotWhitespace(this.keywordFormatTemplate) && ReservedKeywordRegistry.contains(realColumn)) {
            return MessageFormat.format(this.keywordFormatTemplate, realColumn);
        }
        return realColumn;
    }

    @Override
    public <V> Builder<Column> with(AssignConsumer<Column, V> consumer, V value) {
        // Empty
        return this;
    }

    @Override
    public void reset() {
        // Empty
    }

    public static ColumnBuilder create() {
        return new ColumnBuilder();
    }

}
