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
import io.github.mybatisx.core.criteria.query.Query;
import io.github.mybatisx.core.mapping.Scripts;
import io.github.mybatisx.lang.Strings;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Optional;

/**
 * 查询字段
 *
 * @author wvkity
 * @created 2022/1/18
 * @since 1.0.0
 */
@Builder
@ToString
@RequiredArgsConstructor
public class StandardSelectable implements Selectable {

    private static final long serialVersionUID = 4484144385876976203L;
    /**
     * {@link Query}
     */
    private final Query<?> query;
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
    /**
     * 属性
     */
    private final String property;
    /**
     * 引用属性
     */
    private final String reference;
    /**
     * 查询列类型
     */
    @Getter
    private final SelectType type;

    /**
     * 获取表别名
     *
     * @return 表别名
     */
    protected String getTableAlias() {
        return Strings.isNotWhitespace(this.tableAlias) ? this.tableAlias :
                this.query == null ? Constants.EMPTY : this.query.as();
    }

    @Override
    public String getReference() {
        return Strings.isNotWhitespace(this.reference) ? this.reference :
                this.query == null ? Constants.EMPTY : Optional.ofNullable(query.getReference())
                        .filter(Strings::isNotWhitespace).orElse(Constants.EMPTY);
    }

    /**
     * 获取别名
     *
     * @return 别名
     */
    protected String as() {
        final String _$ref = this.getReference();
        final boolean hasRef = Strings.isNotWhitespace(_$ref);
        final boolean hasAlias = Strings.isNotWhitespace(this.alias);
        final boolean hasProp = Strings.isNotWhitespace(this.property);
        final boolean propAsAlias = this.query != null && this.query.isPropAsAlias();
        if (hasRef) {
            final String realAlias;
            if (hasAlias) {
                realAlias = this.alias;
            } else {
                if (hasProp && propAsAlias) {
                    realAlias = this.property;
                } else {
                    realAlias = this.column;
                }
            }
            return _$ref + SqlSymbol.DOT + realAlias;
        }
        return hasAlias ? this.alias : (hasProp && propAsAlias ? this.property : Constants.EMPTY);
    }

    @Override
    public String getFragment(boolean isQuery) {
        final String tableAlias = this.getTableAlias();
        return Scripts.toSelectArg(this.column.startsWith(SqlSymbol.START_BRACKET) ? null : tableAlias,
                this.column, this.as());
    }
}
