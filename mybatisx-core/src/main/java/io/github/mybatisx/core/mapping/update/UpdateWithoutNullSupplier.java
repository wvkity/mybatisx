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

import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.base.constant.Splicing;
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.core.mapping.AbstractSupplier;
import io.github.mybatisx.core.mapping.Scripts;
import io.github.mybatisx.support.config.MyBatisGlobalConfig;

import java.util.List;
import java.util.stream.Collectors;

/**
 * updateWithoutNull sql供应器
 *
 * @author wvkity
 * @created 2022/1/4
 * @since 1.0.0
 */
public class UpdateWithoutNullSupplier extends AbstractSupplier {

    public UpdateWithoutNullSupplier(MyBatisGlobalConfig mgc, Table table) {
        super(mgc, table);
    }

    @Override
    public String get() {
        final List<Column> columns = this.table.getUpdateColumnWithoutSpecial();
        final StringBuilder script = new StringBuilder(120);
        script.append(columns.stream()
                .map(it -> Scripts.toIfTag(PARAMETER_ENTITY, Symbol.EQ, Splicing.REPLACE,
                null, it, true, false, true, LogicSymbol.NONE, COMMA_SPACE))
                .collect(Collectors.joining(NEW_LINE)));
        //  noinspection DuplicatedCode
        this.optimisticLockWithSetThen(script::append);
        final StringBuilder condition = new StringBuilder(120);
        this.primaryKeyWithWhereThen(condition::append);
        this.optimisticLockWithWhereThen(condition::append);
        this.multiTenantWithWhereThen(condition::append);
        this.logicDeleteWithWhereThen(condition::append);
        return this.update((NEW_LINE + Scripts.toTrimTag(script.toString(), SET, null, null, COMMA_SPACE)),
                (NEW_LINE + Scripts.toTrimTag(condition.toString(), WHERE, "AND |OR", null, null)));
    }
}
