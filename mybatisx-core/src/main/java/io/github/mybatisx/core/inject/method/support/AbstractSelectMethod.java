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
package io.github.mybatisx.core.inject.method.support;

import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.support.mapping.SqlSupplier;

/**
 * 抽象查询方法
 *
 * @param <S> {@link SqlSupplier}类型
 * @author wvkity
 * @created 2022/2/24
 * @since 1.0.0
 */
public abstract class AbstractSelectMethod<S extends SqlSupplier> extends AbstractInjectMethod<S> {

    @Override
    public void injectMappedStatement(Table table, Class<?> mapperInterface, Class<?> returnType) {
        this.addSelectMappedStatement(table, mapperInterface, returnType);
    }
    
}
