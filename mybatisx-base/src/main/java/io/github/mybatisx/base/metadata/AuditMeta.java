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

import io.github.mybatisx.auditable.metadata.AuditMode;
import io.github.mybatisx.auditable.metadata.AuditType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * 审计信息
 *
 * @author wvkity
 * @created 2021/12/23
 * @since 1.0.0
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class AuditMeta {

    /**
     * 是否为多租户标识
     */
    private final boolean multiTenant;
    /**
     * 是否为保存操作用户ID自动填充标识
     */
    private final boolean createdBy;
    /**
     * 是否为保存操作用户名自动填充标识
     */
    private final boolean createdByName;
    /**
     * 是否为保存操作时间自动填充标识
     */
    private final boolean createdDate;
    /**
     * 是否为更新操作用户ID自动填充标识
     */
    private final boolean lastModifiedBy;
    /**
     * 是否为更新操作用户名自动填充标识
     */
    private final boolean lastModifiedByName;
    /**
     * 是否为更新操作时间自动填充标识
     */
    private final boolean lastModifiedDate;
    /**
     * 是否为删除操作用户ID自动填充标识
     */
    private final boolean deletedBy;
    /**
     * 是否为删除操作用户名自动填充标识
     */
    private final boolean deletedByName;
    /**
     * 是否为删除操作时间自动填充标识
     */
    private final boolean deletedDate;
    /**
     * 审计类型
     */
    private final AuditType auditType;
    /**
     * 审计策略列表
     */
    private final Set<AuditMode> auditModes;

    /**
     * 检查是否为{@link AuditMode#INSERTABLE INSERTABLE}审计类型
     *
     * @return boolean
     */
    public boolean isInsertable() {
        return this.auditModes.contains(AuditMode.INSERTABLE);
    }

    /**
     * 检查是否为{@link AuditMode#UPDATABLE UPDATABLE}审计类型
     *
     * @return boolean
     */
    public boolean isUpdatable() {
        return this.auditModes.contains(AuditMode.UPDATABLE);
    }

    /**
     * 检查是否为{@link AuditMode#DELETABLE DELETABLE}审计类型
     *
     * @return boolean
     */
    public boolean isDeletable() {
        return this.auditModes.contains(AuditMode.DELETABLE);
    }
}
