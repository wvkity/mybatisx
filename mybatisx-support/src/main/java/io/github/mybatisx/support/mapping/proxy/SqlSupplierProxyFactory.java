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
package io.github.mybatisx.support.mapping.proxy;

import io.github.mybatisx.session.MyBatisConfiguration;
import io.github.mybatisx.support.mapping.SqlSupplier;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Proxy;

/**
 * {@link SqlSupplier}代理工厂
 *
 * @author wvkity
 * @created 2021/12/26
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class SqlSupplierProxyFactory<T extends SqlSupplier> {

    private final Class<T> supplierInterface;

    @SuppressWarnings({"unchecked"})
    public T newInstance(final SqlSupplierProxy<T> supplierProxy) {
        return (T) Proxy.newProxyInstance(this.supplierInterface.getClassLoader(), new Class[]{this.supplierInterface},
                supplierProxy);
    }

    public T newInstance(final MyBatisConfiguration config, final Object... args) {
        return newInstance(new SqlSupplierProxy<>(config, this.supplierInterface, args));
    }
}
