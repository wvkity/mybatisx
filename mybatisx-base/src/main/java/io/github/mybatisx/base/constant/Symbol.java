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
package io.github.mybatisx.base.constant;

import io.github.mybatisx.base.fragment.Fragment;

/**
 * SQL条件符号
 *
 * @author wvkity
 * @created 2022/1/2
 * @since 1.0.0
 */
public enum Symbol implements Fragment {
    /**
     * is null
     */
    NULL("IS NULL"),
    /**
     * is not null
     */
    NOT_NULL("IS NOT NULL"),
    /**
     * 等于
     */
    EQ("="),
    /**
     * 不等于
     */
    NE("<>"),
    /**
     * 小于
     */
    LT("<"),
    /**
     * 小于等于
     */
    LE("<="),
    /**
     * 大于
     */
    GT(">"),
    /**
     * 大于等于
     */
    GE(">="),
    /**
     * in
     */
    IN("IN"),
    /**
     * not in
     */
    NOT_IN("NOT IN"),
    /**
     * exists
     */
    EXISTS("EXISTS"),
    /**
     * not exists
     */
    NOT_EXISTS("NOT EXISTS"),
    /**
     * like
     */
    LIKE("LIKE"),
    /**
     * ilike
     */
    ILIKE("ILIKE"),
    /**
     * not like
     */
    NOT_LIKE("NOT LIKE"),
    /**
     * not ilike
     */
    NOT_ILIKE("NOT ILIKE"),
    /**
     * between
     */
    BETWEEN("BETWEEN"),
    /**
     * not between
     */
    NOT_BETWEEN("NOT BETWEEN"),
    /**
     * template
     */
    TEMPLATE("TEMPLATE");

    final String symbol;

    Symbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getFragment() {
        return this.symbol;
    }


    @Override
    public String toString() {
        return this.getFragment();
    }


}
