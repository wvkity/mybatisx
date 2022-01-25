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
package io.github.mybatisx.core.support.order;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.NullPrecedence;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.dialect.Dialect;
import io.github.mybatisx.core.criteria.query.Query;
import io.github.mybatisx.lang.Strings;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 抽象排序
 *
 * @author wvkity
 * @created 2022/1/25
 * @since 1.0.0
 */
@Getter
@SuppressWarnings("serial")
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractOrder implements Order {

    /**
     * {@link Query}
     */
    @ToString.Exclude
    protected final Query<?> query;
    /**
     * 表别名
     */
    protected final String alias;
    /**
     * 是否升序
     */
    protected final boolean ascending;
    /**
     * 是否忽略大小写排序
     */
    protected final boolean ignoreCase;
    /**
     * 空值优先级
     */
    protected final NullPrecedence precedence;
    /**
     * 别名
     */
    protected final AtomicReference<String> asRef = new AtomicReference<>(null);

    /**
     * 获取表别名
     *
     * @return 表别名
     */
    protected String as() {
        String oldValue = this.asRef.get();
        if (oldValue == null) {
            if (Strings.isNotWhitespace(this.alias)) {
                oldValue = this.alias + SqlSymbol.DOT;
            } else if (this.query != null) {
                final String _$as = this.query.as();
                if (Strings.isNotWhitespace(_$as)) {
                    oldValue = _$as + SqlSymbol.DOT;
                } else {
                    oldValue = Constants.EMPTY;
                }
            } else {
                oldValue = Constants.EMPTY;
            }
            this.asRef.compareAndSet(null, oldValue);
        }
        return oldValue;
    }

    /**
     * 渲染排序片段
     *
     * @param column 字段名
     * @return 排序片段
     */
    protected String render(final String column) {
        final StringBuilder fragment = new StringBuilder();
        Dialect dialect = null;
        boolean hasDialect = this.query != null && (dialect = this.query.getDialect()) != null;
        boolean lower = hasDialect && ignoreCase;
        if (lower) {
            fragment.append(dialect.getLowercaseFunction()).append(SqlSymbol.START_BRACKET);
        }
        fragment.append(this.as()).append(column);
        final String order = this.isAscending() ? SqlSymbol.ASC : SqlSymbol.DESC;
        if (lower) {
            fragment.append(SqlSymbol.END_BRACKET);
        }
        if (hasDialect) {
            return dialect.renderOrderByElement(fragment.toString(), null, order, this.precedence);
        }
        return fragment.append(SqlSymbol.SPACE).append(order).toString();
    }

    @Override
    public String toString() {
        return (this.ascending ? SqlSymbol.ASC : SqlSymbol.DESC)
                + (this.precedence == null ? Constants.EMPTY : this.precedence.name());
    }
}
