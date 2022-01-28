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
 * max聚合函数
 *
 * @author wvkity
 * @created 2022/1/28
 * @since 1.0.0
 */
public class Max extends AbstractAggFunction {

    private static final long serialVersionUID = -1074859419157278485L;

    public Max(Query<?> query, String column, boolean distinct) {
        this(query, column, distinct, null);
    }

    public Max(Query<?> query, String column, Integer scale) {
        this(query, column, false, scale);
    }

    public Max(Query<?> query, String column, boolean distinct, Integer scale) {
        this(query, column, null, distinct, scale);
    }

    public Max(Query<?> query, String column, String alias, boolean distinct) {
        this(query, null, column, alias, distinct, null);
    }

    public Max(Query<?> query, String column, String alias, Integer scale) {
        this(query, column, alias, false, scale);
    }

    public Max(Query<?> query, String column, String alias, boolean distinct, Integer scale) {
        this(query, null, column, alias, distinct, scale);
    }

    public Max(String tableAlias, String column, boolean distinct) {
        this(tableAlias, column, distinct, null);
    }

    public Max(String tableAlias, String column, Integer scale) {
        this(tableAlias, column, false, scale);
    }

    public Max(String tableAlias, String column, boolean distinct, Integer scale) {
        this(tableAlias, column, null, distinct, scale);
    }

    public Max(String tableAlias, String column, String alias, Integer scale) {
        this(tableAlias, column, alias, false, scale);
    }

    public Max(String tableAlias, String column, String alias, boolean distinct) {
        this(tableAlias, column, alias, distinct, null);
    }

    public Max(String tableAlias, String column, String alias, boolean distinct, Integer scale) {
        this(null, tableAlias, column, alias, distinct, scale);
    }

    public Max(Query<?> query, String tableAlias, String column, String alias, boolean distinct, Integer scale) {
        super(query, tableAlias, column, SqlSymbol.AGG_MAX, alias, distinct, scale);
    }
}
