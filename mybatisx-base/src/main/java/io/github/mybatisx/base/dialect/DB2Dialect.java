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
package io.github.mybatisx.base.dialect;

import io.github.mybatisx.base.constant.NullPrecedence;
import io.github.mybatisx.base.constant.SqlSymbol;

/**
 * DB2方言
 *
 * @author wvkity
 * @created 2022/1/10
 * @since 1.0.0
 */
public class DB2Dialect implements Dialect {

    @Override
    public String getLowercaseFunction() {
        return "LCASE";
    }

    @Override
    public String renderOrderByElement(String expression, String collation, String order, NullPrecedence precedence) {
        if (precedence == null || precedence == NullPrecedence.NONE) {
            return Dialect.super.renderOrderByElement(expression, collation, order, precedence);
        }
        if ((precedence == NullPrecedence.FIRST && SqlSymbol.DESC.equalsIgnoreCase(order))
                || (precedence == NullPrecedence.LAST && SqlSymbol.ASC.equalsIgnoreCase(order))) {
            return Dialect.super.renderOrderByElement(expression, collation, order, precedence);
        }
        final StringBuilder it = new StringBuilder(45);
        it.append("CASE WHEN ").append(expression).append(" IS NULL THEN ");
        if (precedence == NullPrecedence.FIRST) {
            it.append("0 ELSE 1");
        } else {
            it.append("1 ELSE 0");
        }
        it.append(" END, ").append(expression).append(SqlSymbol.SPACE)
                .append(order == null ? SqlSymbol.ASC : order);
        return it.toString();
    }

    private static class DB2DialectInstanceHolder {
        private static final DB2Dialect INSTANCE = new DB2Dialect();
    }

    public static DB2Dialect getInstance() {
        return DB2DialectInstanceHolder.INSTANCE;
    }
}
