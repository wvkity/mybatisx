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
package io.github.mybatisx.base.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 逻辑删除配置
 *
 * @author wvkity
 * @created 2021/12/30
 * @since 1.0.0
 */
@Getter
@Setter
@RequiredArgsConstructor
public class LogicDeleteConfig {

    /**
     * 是否自动扫描
     */
    private boolean autoScan;
    /**
     * 是否自动初始化(保存时)
     */
    private boolean initialize;
    /**
     * 未删除标识值
     */
    private String yet = "0";
    /**
     * 已删除标识值
     */
    private String already = "1";
    /**
     * 逻辑删除属性
     */
    private Set<String> properties = new HashSet<>(Collections.singletonList("deleted"));
    
    public static LogicDeleteConfig of() {
        return new LogicDeleteConfig();
    }
}
