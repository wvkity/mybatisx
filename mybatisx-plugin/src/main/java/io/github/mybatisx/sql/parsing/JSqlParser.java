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
package io.github.mybatisx.sql.parsing;

import io.github.mybatisx.sql.OrderByDeleter;
import io.github.mybatisx.sql.ParameterReplacer;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.Select;

import java.util.regex.Pattern;

/**
 * SQL解析器
 *
 * @author wvkity
 * @created 2022/2/20
 * @since 1.0.0
 */
public class JSqlParser implements SqlParser {

    private final Pattern orderByRegex = Pattern.compile("^(?i)(.*(\\s+order\\s+by\\s+)(.*))$");

    /**
     * 解析查询语句
     *
     * @param sql SQL语句
     * @return {@link Select}
     * @throws JSQLParserException 非完整查询语句将抛出异常
     */
    protected Select parse(final String sql) throws JSQLParserException {
        return (Select) CCJSqlParserUtil.parse(sql);
    }

    @Override
    public String smartRemoveOrderBy(String source) {
        if (this.orderByRegex.matcher(source).matches()) {
            final ParameterReplacer replacer = new ParameterReplacer(source);
            final Select select;
            try {
                select = this.parse(replacer.replace().isReplaced() ? replacer.getReplaceAfter() : source);
                new OrderByDeleter(select, source).tryRemove();
            } catch (Exception ignore) {
                return source;
            }
            return replacer.restore(select.toString());
        }
        return source;
    }

}
