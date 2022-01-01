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

/**
 * 乐观锁信息
 *
 * @author wvkity
 * @created 2021/12/31
 * @since 1.0.0
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class VersionMeta {

    /**
     * 是否为乐观锁标识
     */
    private final boolean version;
    /**
     * 是否自动初始化
     */
    private final boolean initialize;
    /**
     * 乐观锁初始值
     */
    private final Integer initValue;
}
