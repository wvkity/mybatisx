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
package io.github.mybatisx.auditable.metadata;

import io.github.mybatisx.auditable.annotation.Date;
import io.github.mybatisx.auditable.annotation.Remark;
import io.github.mybatisx.auditable.annotation.Unique;

import java.lang.annotation.Annotation;

/**
 * 审计类型
 *
 * @author wvkity
 * @created 2021/12/31
 * @since 1.0.0
 */
public enum AuditType {

    /**
     * 唯一标识
     */
    UNIQUE(Unique.class),
    /**
     * 备注
     */
    REMARK(Remark.class),
    /**
     * 时间
     */
    DATE(Date.class);
    final Class<? extends Annotation> type;

    AuditType(Class<? extends Annotation> type) {
        this.type = type;
    }

    public Class<? extends Annotation> getType() {
        return type;
    }
}
