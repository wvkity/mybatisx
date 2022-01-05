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
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.core.mapping.AbstractSupplier;
import io.github.mybatisx.core.mapping.Scripts;

import java.util.Iterator;
import java.util.List;

/**
 * update sql供应器
 *
 * @author wvkity
 * @created 2022/1/2
 * @since 1.0.0
 */
public class UpdateSupplier extends AbstractSupplier {

    public UpdateSupplier(MyBatisGlobalConfig mgc, Table table) {
        super(mgc, table);
    }

    @Override
    public String get() {
        final List<Column> columns = this.table.getUpdateColumnWithoutSpecial();
        final StringBuilder script = new StringBuilder(120);
        final Iterator<Column> iterator = columns.iterator();
        boolean hasNext = iterator.hasNext();
        while (hasNext) {
            final Column it = iterator.next();
            script.append(SPACE).append(Scripts.toPlaceHolderArg(PARAMETER_ENTITY, Splicing.REPLACE, it)).append(COMMA_SPACE);
            hasNext = iterator.hasNext();
            if (hasNext) {
                script.append(NEW_LINE);
            }
        }
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
