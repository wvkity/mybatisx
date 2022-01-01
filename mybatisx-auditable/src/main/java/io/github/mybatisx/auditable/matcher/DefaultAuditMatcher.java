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
package io.github.mybatisx.auditable.matcher;

import io.github.mybatisx.auditable.metadata.AuditMode;
import io.github.mybatisx.auditable.metadata.AuditType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * 默认审计匹配结果
 *
 * @author wvkity
 * @created 2021/12/31
 * @since 1.0.0
 */
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
@Builder
public class DefaultAuditMatcher implements AuditMatcher {

    /**
     * 是否匹配
     */
    private final boolean matches;
    /**
     * 创建人标识
     */
    private final boolean createdBy;
    /**
     * 创建人用户名
     */
    private final boolean createdByName;
    /**
     * 创建人注解
     */
    private final boolean createdDate;
    /**
     * 更新人标识
     */
    private final boolean lastModifiedBy;
    /**
     * 更新人用户名
     */
    private final boolean lastModifiedByName;
    /**
     * 更新时间
     */
    private final boolean lastModifiedDate;
    /**
     * 删除人标识
     */
    private final boolean deletedBy;
    /**
     * 删除人用户名
     */
    private final boolean deletedByName;
    /**
     * 删除时间
     */
    private final boolean deletedDate;
    /**
     * 审计类型
     */
    private final AuditType auditType;
    /**
     * 审计模式
     */
    private final Set<AuditMode> auditModes;
}
