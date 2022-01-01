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

import io.github.mybatisx.annotation.ExecuteType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 主键唯一信息
 *
 * @author wvkity
 * @created 2021/12/23
 * @since 1.0.0
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class UniqueMeta {

    /**
     * 优先标识
     */
    private final boolean priority;
    /**
     * 是否为UUID主键
     */
    private final boolean uuid;
    /**
     * 是否为自增主键
     */
    private final boolean identity;
    /**
     * 是否为雪花算法主键
     */
    private final boolean snowflake;
    /**
     * 获取主键SQL
     */
    private final String generator;
    /**
     * 主键序列生成器(oracle)
     */
    private final String sequence;
    /**
     * SQL执行策略
     */
    private final ExecuteType strategy;
    
}
