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
package io.github.mybatisx.support.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 乐观锁
 *
 * @author wvkity
 * @created 2021/12/30
 * @since 1.0.0
 */
@Getter
@Setter
@RequiredArgsConstructor
public class OptimisticLockConfig {
    
    /**
     * 自动扫描
     */
    private boolean autoScan;
    /**
     * 是否自动初始化(保存时)
     */
    private boolean initialize;
    /**
     * 默认初始化值
     */
    private int initValue = 1;
    /**
     * 乐观锁属性列表
     */
    private Set<String> properties = new HashSet<>(Collections.singletonList("version"));
    
    public static OptimisticLockConfig of() {
        return new OptimisticLockConfig();
    }

}
