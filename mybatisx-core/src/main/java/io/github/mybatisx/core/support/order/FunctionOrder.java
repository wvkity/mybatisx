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
import io.github.mybatisx.core.criteria.query.Query;
import io.github.mybatisx.core.support.function.AggFunction;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 聚合函数排序
 *
 * @author wvkity
 * @created 2022/2/18
 * @since 1.0.0
 */
@Getter
@Builder
@RequiredArgsConstructor
public class FunctionOrder implements Order {

    private static final long serialVersionUID = -501921153999147366L;
    /**
     * 聚合函数
     */
    protected final AggFunction function;
    /**
     * 是否升序
     */
    protected final boolean ascending;

    @Override
    public Query<?> getQuery() {
        return this.function.getQuery();
    }

    @Override
    public String getFragment() {
        if (this.function != null) {
            return this.function.getFragment(false) + SqlSymbol.SPACE + (this.ascending ? SqlSymbol.ASC : SqlSymbol.DESC);
        }
        return Constants.EMPTY;
    }

    ///// static methods /////

    /**
     * 聚合函数升序
     *
     * @param function {@link AggFunction}
     * @return {@link FunctionOrder}
     */
    public static FunctionOrder asc(final AggFunction function) {
        if (function != null) {
            return new FunctionOrder(function, true);
        }
        return null;
    }

    /**
     * 聚合函数降序
     *
     * @param function {@link AggFunction}
     * @return {@link FunctionOrder}
     */
    public static FunctionOrder desc(final AggFunction function) {
        if (function != null) {
            return new FunctionOrder(function, false);
        }
        return null;
    }

}
