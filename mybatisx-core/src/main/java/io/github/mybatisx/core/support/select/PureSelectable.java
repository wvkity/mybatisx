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
package io.github.mybatisx.core.support.select;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.core.mapping.Scripts;
import io.github.mybatisx.lang.Strings;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单纯SQL查询字段
 *
 * @author wvkity
 * @created 2022/2/18
 * @since 1.0.0
 */
public class PureSelectable implements Selectable {

    private static final long serialVersionUID = -4502018463556863966L;

    /**
     * 查询语句
     */
    private final String selectBody;
    /**
     * 表别名
     */
    private final String tableAlias;
    /**
     * 字段名
     */
    @Getter
    private final String column;
    /**
     * 字段别名
     */
    @Getter
    private final String alias;

    protected PureSelectable(String selectBody) {
        this.selectBody = selectBody;
        final PureSQLParser parser = new PureSQLParser(selectBody);
        this.tableAlias = parser.getTableAlias();
        this.column = parser.getColumn();
        this.alias = parser.getAlias();
    }

    protected PureSelectable(final String column, final String alias) {
        this.selectBody = null;
        this.tableAlias = null;
        this.column = column;
        this.alias = alias;
    }

    @Override
    public SelectType getType() {
        return SelectType.PLAIN;
    }

    @Override
    public String getFragment(boolean isQuery) {
        if (Strings.isNotWhitespace(this.column)) {
            if (isQuery) {
                if (Strings.isNotWhitespace(this.selectBody)) {
                    return this.selectBody;
                }
                return Scripts.toSelectArg(this.tableAlias, this.column, this.alias);
            }
            return Scripts.toSelectArg(this.tableAlias, this.column, null);
        }
        return Constants.EMPTY;
    }

    @Getter(AccessLevel.MODULE)
    private static class PureSQLParser {
        private final String sql;
        private String tableAlias;
        private String column;
        private String alias;

        public PureSQLParser(String sql) {
            this.sql = sql;
            this.parse();
        }

        void parse() {
            final String _$sql = this.sql;
            if (Strings.isNotWhitespace(_$sql)) {
                final String[] array = _$sql.split("\\s+");
                final int size = array.length;
                if (size > 1) {
                    final String columnName = array[0];
                    if (columnName.contains(SqlSymbol.DOT) && !columnName.contains(SqlSymbol.START_BRACKET)) {
                        final String[] mix = columnName.split("\\.");
                        this.tableAlias = mix[0];
                        this.column = mix[1];
                    } else {
                        this.column = columnName;
                    }
                    final String aliasName;
                    if (size >= 3) {
                        aliasName = array[2];
                    } else {
                        aliasName = array[1];
                    }
                    this.alias = aliasName;
                } else {
                    if (_$sql.contains(SqlSymbol.DOT) && !_$sql.contains(SqlSymbol.START_BRACKET)) {
                        final String[] mix = _$sql.split("\\.");
                        this.tableAlias = mix[0];
                        this.column = mix[1];
                    } else {
                        this.column = _$sql;
                    }
                }
            }
        }
    }

    /**
     * 创建{@link PureSelectable}
     *
     * @param column 字段名
     * @param alias  别名
     * @return {@link PureSelectable}
     */
    public static PureSelectable of(final String column, final String alias) {
        if (Strings.isNotWhitespace(column)) {
            return new PureSelectable(column, alias);
        }
        return null;
    }

    /**
     * 创建{@link PureSelectable}列表
     *
     * @param selectBody 多个字段
     * @return {@link PureSelectable}列表
     */
    public static List<PureSelectable> of(final String selectBody) {
        if (Strings.isNotWhitespace(selectBody)) {
            final String[] array = selectBody.split(",(\\s*)?");
            final int size = array.length;
            if (size > 0) {
                final List<PureSelectable> selectables = new ArrayList<>(size);
                for (String it : array) {
                    if (Strings.isNotWhitespace(it)) {
                        selectables.add(new PureSelectable(it));
                    }
                }
                return selectables;
            }
        }
        return null;
    }
}
