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
package io.github.mybatisx.core.support.group;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.core.criteria.query.Query;
import io.github.mybatisx.lang.Strings;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 抽象分组
 *
 * @author wvkity
 * @created 2022/1/26
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
@SuppressWarnings({"serial"})
public abstract class AbstractGroup implements Group {

    /**
     * {@link Query}
     */
    protected final Query<?> query;
    /**
     * 表别名
     */
    protected final String alias;

    /**
     * 获取表别名
     *
     * @return 表别名
     */
    protected String as() {
        // noinspection DuplicatedCode
        String ias = "";
        if (Strings.isNotWhitespace(this.alias)) {
            ias = this.alias + SqlSymbol.DOT;
        } else if (this.query != null) {
            final String _$as = this.query.as();
            if (Strings.isNotWhitespace(_$as)) {
                ias = _$as + SqlSymbol.DOT;
            } else {
                ias = Constants.EMPTY;
            }
        }
        return ias;
    }

    /**
     * 渲染分组片段
     *
     * @param column 字段名
     * @return 分组片段
     */
    protected String render(final String column) {
        return this.as() + column;
    }
}
