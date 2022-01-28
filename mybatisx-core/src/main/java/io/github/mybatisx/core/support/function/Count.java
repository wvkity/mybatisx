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
package io.github.mybatisx.core.support.function;

import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.core.criteria.query.Query;

/**
 * count聚合函数
 *
 * @author wvkity
 * @created 2022/1/28
 * @since 1.0.0
 */
public class Count extends AbstractAggFunction {

    private static final long serialVersionUID = 5450065348429843465L;

    public Count(Query<?> query, String column, String alias) {
        this(query, column, alias, false);
    }

    public Count(Query<?> query, String column, String alias, boolean distinct) {
        this(query, null, column, alias, distinct);
    }

    public Count(String tableAlias, String column, boolean distinct) {
        this(tableAlias, column, null, distinct);
    }

    public Count(String tableAlias, String column, String alias) {
        this(tableAlias, column, alias, false);
    }

    public Count(String tableAlias, String column, String alias, boolean distinct) {
        this(null, tableAlias, column, alias, distinct);
    }

    public Count(Query<?> query, String tableAlias, String column, String alias, boolean distinct) {
        super(query, tableAlias, column, SqlSymbol.AGG_COUNT, alias, distinct, null);
    }

}
