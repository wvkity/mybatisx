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
package io.github.mybatisx.core.mapping;

import io.github.mybatisx.support.mapping.SqlSupplier;

/**
 * SQL供应器对象构建器
 *
 * @param <T> SQL供应器
 * @author wvkity
 * @created 2022/1/1
 * @since 1.0.0
 */
public interface SupplierBuilder<T extends SqlSupplier> {

    /**
     * 创建{@link SqlSupplier}对象
     *
     * @param args 参数
     * @return {@link SqlSupplier}对象
     */
    T build(final Object... args);
}
