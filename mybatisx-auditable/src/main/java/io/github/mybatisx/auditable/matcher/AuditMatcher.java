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

import java.util.Set;

/**
 * 审计匹配结果
 *
 * @author wvkity
 * @created 2021/12/31
 * @since 1.0.0
 */
public interface AuditMatcher {
    
    /**
     * 是否匹配
     *
     * @return boolean
     */
    boolean matches();

    /**
     * 是否为创建人唯一标识审计
     *
     * @return boolean
     */
    default boolean createdBy() {
        return false;
    }

    /**
     * 是否为创建人用户名审计
     *
     * @return boolean
     */
    default boolean createdByName() {
        return false;
    }

    /**
     * 是否为创建时间审计
     *
     * @return boolean
     */
    default boolean createdDate() {
        return false;
    }

    /**
     * 是否为更新人唯一标识审计
     *
     * @return boolean
     */
    default boolean lastModifiedBy() {
        return false;
    }

    /**
     * 是否为更新人用户名审计
     *
     * @return boolean
     */
    default boolean lastModifiedByName() {
        return false;
    }

    /**
     * 是否为更新时间审计
     *
     * @return boolean
     */
    default boolean lastModifiedDate() {
        return false;
    }

    /**
     * 是否为删除人唯一标识审计
     *
     * @return boolean
     */
    default boolean deletedBy() {
        return false;
    }

    /**
     * 是否为删除人用户名审计
     *
     * @return boolean
     */
    default boolean deletedByName() {
        return false;
    }

    /**
     * 是否为删除时间审计
     *
     * @return boolean
     */
    default boolean deletedDate() {
        return false;
    }

    /**
     * 获取审计类型
     *
     * @return 审计类型
     */
    default AuditType auditType() {
        return null;
    }

    /**
     * 获取审计模式
     *
     * @return 审计模式集合
     */
    default Set<AuditMode> auditModes() {
        return null;
    }
}
