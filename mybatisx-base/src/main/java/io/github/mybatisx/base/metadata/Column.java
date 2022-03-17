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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * 数据库表字段映射
 *
 * @author wvkity
 * @created 2021/12/23
 * @since 1.0.0
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@SuppressWarnings({"unused"})
public class Column {

    /**
     * 实体类
     */
    @EqualsAndHashCode.Include
    private final Class<?> entity;
    /**
     * 属性
     */
    @EqualsAndHashCode.Include
    private final String property;
    /**
     * 数据库字段(关键字格式化后的字段名)
     */
    private final String column;
    /**
     * 数据库字段
     */
    private final String orgColumn;
    /**
     * {@link JdbcType}类型
     */
    private final JdbcType jdbcType;
    /**
     * 类型处理器
     */
    private final Class<? extends TypeHandler<?>> typeHandler;
    /**
     * 是否为主键
     */
    private final boolean primaryKey;
    /**
     * 是否为blob类型
     */
    private final boolean blob;
    /**
     * 是否可保存
     */
    private final boolean insertable;
    /**
     * 是否可更新
     */
    private final boolean updatable;
    /**
     * SQL语句是否设置JAVA类型
     */
    private final boolean spliceJavaType;
    /**
     * null校验
     */
    private final boolean checkNull;
    /**
     * 字符串空值校验
     */
    private final boolean checkEmpty;
    /**
     * 是否为乐观锁
     */
    private final boolean version;
    /**
     * 是否为多租户
     */
    private final boolean multiTenant;
    /**
     * 是否为逻辑删除标识
     */
    private final boolean logicDelete;
    /**
     * boolean类型属性对应表字段自动添加IS前缀
     */
    private final boolean autoAddedIsPrefix;
    /**
     * 字段信息
     */
    @EqualsAndHashCode.Include
    private final Descriptor descriptor;
    /**
     * 主键元数据
     */
    private final UniqueMeta uniqueMeta;
    /**
     * 审计信息
     */
    private final AuditMeta auditMeta;
    /**
     * 乐观锁元数据
     */
    private final VersionMeta versionMeta;
    /**
     * 逻辑删除元数据
     */
    private final LogicDeleteMeta logicDeleteMeta;

    public boolean isAuditInsertable() {
        return this.insertable && this.auditMeta != null && this.auditMeta.isInsertable();
    }

    public boolean isAuditUpdatable() {
        return this.updatable && this.auditMeta != null && this.auditMeta.isUpdatable();
    }

    public boolean isAuditDeletable() {
        return this.updatable && this.auditMeta != null && this.auditMeta.isDeletable();
    }

}
