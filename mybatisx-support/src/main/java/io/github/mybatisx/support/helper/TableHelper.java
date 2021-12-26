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
package io.github.mybatisx.support.helper;

import io.github.mybatisx.base.metadata.Table;
import org.apache.ibatis.builder.MapperBuilderAssistant;

/**
 * 表映射工具
 *
 * @author wvkity
 * @created 2021/12/26
 * @since 1.0.0
 */
public final class TableHelper {

    private TableHelper() {
    }

    /**
     * 解析实体类信息
     *
     * @param mba         {@link MapperBuilderAssistant}
     * @param entityClass 实体类
     * @return {@link Table}
     */
    public synchronized static Table parse(final MapperBuilderAssistant mba, final Class<?> entityClass) {
        return null;
    }
}
