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
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.lang.Strings;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 别名排序
 *
 * @author wvkity
 * @created 2022/2/18
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class AliasOrder implements Order {

    private static final long serialVersionUID = -8020631179132436357L;
    /**
     * 别名
     */
    private final String alias;
    /**
     * 是否升序
     */
    @Getter
    protected final boolean ascending;

    @Override
    public String getFragment() {
        if (Strings.isNotWhitespace(this.alias)) {
            return this.alias + SqlSymbol.SPACE + (this.ascending ? SqlSymbol.ASC : SqlSymbol.DESC);
        }
        return Constants.EMPTY;
    }

    ///// static methods /////

    /**
     * 聚合函数升序
     *
     * @param alias 别名
     * @return {@link AliasOrder}
     */
    public static AliasOrder asc(final String alias) {
        if (Strings.isNotWhitespace(alias)) {
            return new AliasOrder(alias, true);
        }
        return null;
    }

    /**
     * 聚合函数降序
     *
     * @param alias 别名
     * @return {@link AliasOrder}
     */
    public static AliasOrder desc(final String alias) {
        if (Strings.isNotWhitespace(alias)) {
            return new AliasOrder(alias, false);
        }
        return null;
    }

}
