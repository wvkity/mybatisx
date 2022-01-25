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
import io.github.mybatisx.lang.Strings;

/**
 * 数据库方言
 *
 * @author wvkity
 * @created 2022/1/10
 * @since 1.0.0
 */
public interface Dialect {

    /**
     * 是否支持不区分大小比较like限制
     *
     * @return boolean
     */
    default boolean supportsCaseInsensitiveLike() {
        return false;
    }

    /**
     * 不区分大小写比较函数名称
     *
     * @return 函数名称
     */
    default String getCaseInsensitiveLike() {
        return "LIKE";
    }

    /**
     * 将字符串转成小写函数名称
     *
     * @return 小写字符串函数名称
     */
    default String getLowercaseFunction() {
        return "LOWER";
    }

    /**
     * 渲染排序片段
     *
     * @param expression 排序表达式
     * @param collation  排序规则
     * @param order      排序方式
     * @param precedence 空值优先级
     * @return 排序片段
     */
    default String renderOrderByElement(final String expression, final String collation, final String order,
                                        final NullPrecedence precedence) {
        final StringBuilder it = new StringBuilder(expression);
        if (Strings.isNotWhitespace(collation)) {
            it.append(SqlSymbol.SPACE).append(collation);
        }
        if (Strings.isNotWhitespace(order)) {
            it.append(SqlSymbol.SPACE).append(order);
        }
        if (precedence != null && precedence != NullPrecedence.NONE) {
            it.append(" NULLS ").append(precedence.name());
        }
        return it.toString();
    }
}
