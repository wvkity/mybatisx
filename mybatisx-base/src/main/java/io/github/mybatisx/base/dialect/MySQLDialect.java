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

/**
 * MySQL方言
 *
 * @author wvkity
 * @created 2022/1/25
 * @since 1.0.0
 */
public class MySQLDialect implements Dialect {

    @Override
    public String renderOrderByElement(String expression, String collation, String order, NullPrecedence precedence) {
        //  noinspection DuplicatedCode
        final StringBuilder it = new StringBuilder(45);
        if (precedence != NullPrecedence.NONE) {
            it.append("CASE WHEN ").append(expression).append(" IS NULL THEN ");
            if (precedence == NullPrecedence.FIRST) {
                it.append("0 ELSE 1 ");
            } else {
                it.append("1 ELSE 0 ");
            }
            it.append("END, ");
        }
        it.append(Dialect.super.renderOrderByElement(expression, collation, order, NullPrecedence.NONE));
        return it.toString();
    }

    private static class MySQLInstanceHolder {
        private static final MySQLDialect INSTANCE = new MySQLDialect();
    }

    public static MySQLDialect getInstance() {
        return MySQLDialect.MySQLInstanceHolder.INSTANCE;
    }
}
