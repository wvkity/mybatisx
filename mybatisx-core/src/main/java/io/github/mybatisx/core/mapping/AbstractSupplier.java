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

import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.support.config.MyBatisGlobalConfig;
import io.github.mybatisx.support.mapping.SqlSupplier;
import lombok.Getter;

/**
 * 抽象SQL供应器
 *
 * @author wvkity
 * @created 2021/12/26
 * @since 1.0.0
 */
public abstract class AbstractSupplier implements SqlSupplier, SqlSymbol {

    /**
     * {@link Table}
     */
    @Getter
    protected final Table table;
    /**
     * 实体类
     */
    @Getter
    protected final Class<?> entityClass;
    /**
     * 去阿奴配置
     */
    protected final MyBatisGlobalConfig globalConfig;

    public AbstractSupplier(MyBatisGlobalConfig mgc, Table table) {
        this.table = table;
        this.entityClass = null;
        this.globalConfig = mgc;
    }

    /**
     * 拼接完整保存SQL语句
     *
     * @param columnFragment 字段部分
     * @param valueFragment  值部分
     * @return 完整SQL语句
     */
    protected String insert(final String columnFragment, final String valueFragment) {
        return "INSERT INTO " + this.table.getFullName() + SPACE + columnFragment + " VALUES " + valueFragment;
    }
}
