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
package io.github.mybatisx.auditable.config;

import io.github.mybatisx.auditable.parsing.AuditAutoScanParser;
import io.github.mybatisx.auditable.parsing.AuditParser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 审计配置
 *
 * @author wvkity
 * @created 2021/12/31
 * @since 1.0.0
 */
@Getter
@Setter
@RequiredArgsConstructor
public class AuditConfig {

    /**
     * 是否开启审计(默认开启)
     */
    private boolean enable = true;
    /**
     * 是否开启自动识别
     */
    private boolean autoScan;
    /**
     * 审计属性解析
     */
    private AuditParser auditParser;
    /**
     * 自动识别审计属性解析器
     */
    private AuditAutoScanParser autoScanParser;
    
    public static AuditConfig of() {
        return new AuditConfig();
    }
}
