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
package io.github.mybatisx.core.mapping.update;

import io.github.mybatisx.base.config.MyBatisGlobalConfig;
import io.github.mybatisx.base.constant.Splicing;
import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.core.mapping.AbstractSupplier;
import io.github.mybatisx.core.mapping.Scripts;

import java.util.stream.Collectors;

/**
 * updateWithSpecial sql供应器
 *
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
public class UpdateWithSpecialSupplier extends AbstractSupplier {

    public UpdateWithSpecialSupplier(MyBatisGlobalConfig mgc, Table table) {
        super(mgc, table);
    }

    @Override
    public String get() {
        final Table _$table = this.table;
        final String script =
                _$table.filtrate(_$table.getUpdatableColumns(), it -> !it.isMultiTenant() && !it.isLogicDelete())
                        .stream()
                        .map(it -> SPACE + Scripts.toPlaceHolderArg(PARAMETER_ENTITY, Splicing.REPLACE, it))
                        .collect(Collectors.joining(COMMA + NEW_LINE));
        //  noinspection DuplicatedCode
        final StringBuilder condition = new StringBuilder(120);
        this.primaryKeyWithWhereThen(condition::append);
        this.multiTenantWithWhereThen(condition::append);
        this.logicDeleteWithWhereThen(condition::append);
        return this.update((NEW_LINE + Scripts.toTrimTag(script, SET, null, null, COMMA_SPACE)),
                (NEW_LINE + Scripts.toTrimTag(condition.toString(), WHERE, "AND |OR", null, null)));
    }
}
