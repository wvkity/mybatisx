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
package io.github.mybatisx.core.inject.method;

import io.github.mybatisx.base.metadata.Table;
import org.apache.ibatis.builder.MapperBuilderAssistant;

/**
 * 映射方法
 *
 * @author wvkity
 * @created 2021/12/25
 * @since 1.0.0
 */
@FunctionalInterface
public interface MappedMethod {

    /**
     * 注入SQL语句
     *
     * @param mba             {@link MapperBuilderAssistant}
     * @param table           {@link Table}
     * @param mapperInterface Mapper接口
     * @param returnType      返回值类型
     */
    void inject(final MapperBuilderAssistant mba, final Table table, final Class<?> mapperInterface,
                final Class<?> returnType);
    
}
