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

import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.sql.OrderByDeleter;
import io.github.mybatisx.sql.ParameterReplacer;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * SQL解析器
 *
 * @author wvkity
 * @created 2022/2/20
 * @since 1.0.0
 */
public class JSqlParser implements SqlParser {

    /**
     * order by正则表达式
     */
    private final Pattern orderByRegex = Pattern.compile("^(?i)(.*(\\s+order\\s+by\\s+)(.*))$");
    /**
     * select 1 from table查询语句正则表达式字符串
     */
    private final String selectOneRegexString = "^(?i)(\\s*select\\s+1\\s+)(from\\s+)(.*)$";
    /**
     * select 1 from table查询语句正则表达式
     */
    private final Pattern selectOneMatcher = Pattern.compile(this.selectOneRegexString);

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

    /**
     * 检查是否为select 1 from table语句
     *
     * @param sql SQL语句
     * @return boolean
     */
    protected boolean isSelectOne(final String sql) {
        return this.selectOneMatcher.matcher(sql).matches();
    }

    /**
     * select 1 from table替换
     *
     * @param source SQL语句
     * @return 替换后的SQL语句
     */
    protected String existsReplace(final String source) {
        if (source != null) {
            if (this.isSelectOne(source)) {
                return source;
            }
            return String.format(source.replaceFirst("^(?i)((\\s*select\\s*)(((?!select).)*)(\\s*from)(\\s*.*))$", "$2%s$4$5$6"), "1");
        }
        return null;
    }

    /**
     * 转成select 1 from table语句
     *
     * @param select   {@link Select}
     * @param replacer {@link ParameterReplacer}
     * @return 处理后的SQL语句
     */
    protected String toSelectOne(final Select select, final ParameterReplacer replacer) {
        final SelectBody body = select.getSelectBody();
        final PlainSelect psl = (PlainSelect) body;
        final List<SelectItem> selectItems = new ArrayList<>(1);
        selectItems.add(new SelectExpressionItem(new Column("1")));
        psl.setSelectItems(selectItems);
        return replacer.restore(select.toString());
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

    @Override
    public String smartParseOfExists(String source) {
        if (Strings.isNotWhitespace(source) && !this.isSelectOne(source)) {
            final ParameterReplacer replacer = new ParameterReplacer(source);
            final Select select;
            final OrderByDeleter deleter;
            try {
                select = this.parse(replacer.replace().isReplaced() ? replacer.getReplaceAfter() : source);
                deleter = new OrderByDeleter(select, source);
            } catch (Exception ignore) {
                return this.existsReplace(source);
            }
            try {
                deleter.tryRemove();
            } catch (Exception ignore) {
                // ignore
            }
            return this.toSelectOne(select, replacer);
        }
        return source;
    }

}
