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
package io.github.mybatisx.core.mapping.insert;

import io.github.mybatisx.base.config.MyBatisGlobalConfig;
import io.github.mybatisx.base.constant.Splicing;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.core.mapping.AbstractSupplier;
import io.github.mybatisx.core.mapping.Scripts;

import java.util.List;
import java.util.stream.Collectors;

/**
 * insert sql供应器
 *
 * @author wvkity
 * @created 2022/1/2
 * @since 1.0.0
 */
public class InsertSupplier extends AbstractSupplier {

    public InsertSupplier(MyBatisGlobalConfig mgc, Table table) {
        super(mgc, table);
    }

    @Override
    public String get() {
        final List<Column> columns = this.table.getInsertableColumns();
        final String columnFragment = columns.stream()
                .map(Column::getColumn).collect(Collectors.joining(COMMA_SPACE, START_BRACKET, END_BRACKET));
        final String valueFragment = columns.stream()
                .map(it -> Scripts.toPlaceHolderArg(PARAMETER_ENTITY, Splicing.INSERT, it))
                .collect(Collectors.joining(COMMA_SPACE, START_BRACKET, END_BRACKET));
        return this.insert(columnFragment, valueFragment);
    }
}
