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

/**
 * SQL符号常量
 *
 * @author wvkity
 * @created 2022/1/2
 * @since 1.0.0
 */
public interface SqlSymbol {

    /**
     * 空字符串
     */
    String EMPTY = "";
    /**
     * 空格
     */
    String SPACE = " ";
    /**
     * value
     */
    String VALUE = "value";
    /**
     * 点
     */
    String DOT = ".";
    /**
     * 星号
     */
    String STAR = "*";
    /**
     * 问号
     */
    String QUESTION_MARK = "?";
    /**
     * 单引号
     */
    String SINGLE_QUOTES = "'";
    /**
     * 反引号
     */
    String BACK_QUOTES = "`";
    /**
     * 双引号
     */
    String DOUBLE_QUOTES = "\"";
    /**
     * 换行符
     */
    String NEW_LINE = System.lineSeparator();
    /**
     * 大于符号
     */
    String GT = ">";
    /**
     * 逗号
     */
    String COMMA = ",";
    /**
     * 逗号 + 空格
     */
    String COMMA_SPACE = ", ";
    /**
     * (
     */
    String START_BRACKET = "(";
    /**
     * )
     */
    String END_BRACKET = ")";
    /**
     * #{
     */
    String POUND_START_BRACE = "#{";
    /**
     * ${
     */
    String DOLLAR_START_BRACE = "${";
    /**
     * {
     */
    String START_BRACE = "{";
    /**
     * }
     */
    String END_BRACE = "}";
    /**
     * OR
     */
    String OR = "OR";
    /**
     * OR + 空格
     */
    String OR_SPACE = "OR ";
    /**
     * 空格 + OR + 空格
     */
    String OR_SPACE_BOTH = " OR ";
    /**
     * AND
     */
    String AND = "AND";
    /**
     * AND + 空格
     */
    String AND_SPACE = "AND ";
    /**
     * 空格 + AND + 空格
     */
    String AND_SPACE_BOTH = " AND ";
    /**
     * &lt;![CDATA[
     */
    String START_CDATA = "<![CDATA[";
    /**
     * ]]&gt;
     */
    String END_CDATA = "]]>";
    /**
     * SELECT
     */
    String SELECT = "SELECT";
    /**
     * UPDATE
     */
    String UPDATE = "UPDATE";
    /**
     * DELETE
     */
    String DELETE = "DELETE";
    /**
     * FROM
     */
    String FROM = "FROM";
    /**
     * SET
     */
    String SET = "SET";
    /**
     * WHERE
     */
    String WHERE = "WHERE";
    /**
     * WHERE + 空格
     */
    String WHERE_SPACE = "WHERE ";
    /**
     * 分组
     */
    String GROUP_BY = "GROUP BY";
    /**
     * 分组+空格
     */
    String GROUP_BY_SPACE = "GROUP BY ";
    /**
     * 空格 + 分组 + 空格
     */
    String GROUP_BY_SPACE_BOTH = " GROUP BY ";
    /**
     * 排序
     */
    String ORDER_BY = "ORDER BY";
    /**
     * 排序 + 空格
     */
    String ORDER_BY_SPACE = "ORDER BY ";
    /**
     * 空格 + 排序 + 空格
     */
    String ORDER_BY_SPACE_BOTH = " ORDER BY ";
    /**
     * 升序
     */
    String ASC = "ASC";
    /**
     * 降序
     */
    String DESC = "DESC";
    /**
     * 去重
     */
    String DISTINCT = "DISTINCT";
    /**
     * inner join
     */
    String INNER = "INNER JOIN";
    /**
     * left join
     */
    String LEFT = "LEFT JOIN";
    /**
     * right join
     */
    String RIGHT = "RIGHT JOIN";
    /**
     * full join
     */
    String FULL = "FULL JOIN";
    /**
     * on
     */
    String ON = "ON";
    /**
     * as
     */
    String AS = "AS";
    /**
     * count聚合函数名
     */
    String AGG_COUNT = "COUNT";
    /**
     * sum聚合函数名
     */
    String AGG_SUM = "SUM";
    /**
     * avg聚合函数名
     */
    String AGG_AVG = "AVG";
    /**
     * min聚合函数名
     */
    String AGG_MIN = "MIN";
    /**
     * max聚合函数名
     */
    String AGG_MAX = "MAX";
    /**
     * 字符串占位符
     */
    String STRING_PLACEHOLDER = "%s";
    /**
     * 字段名占位符
     */
    String COLUMN_PLACEHOLDER = ":@";
    /**
     * 实体参数
     */
    String PARAMETER_ENTITY = Constants.PARAM_ENTITY;
    /**
     * 乐观锁参数名
     */
    String PARAMETER_OPTIMISTIC_LOCK = "optimisticLockValue";
}
