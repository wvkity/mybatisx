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
package io.github.mybatisx.core.management;

import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.fragment.AbstractFragmentList;
import io.github.mybatisx.base.fragment.Fragment;
import io.github.mybatisx.core.support.order.Order;
import io.github.mybatisx.lang.Strings;

import java.util.stream.Collectors;

/**
 * 排序片段管存储
 *
 * @author wvkity
 * @created 2022/1/25
 * @since 1.0.0
 */
public class OrderStorage extends AbstractFragmentList<Order> {

    private static final long serialVersionUID = 9171804614605389219L;

    @Override
    public String getFragment() {
        if (!this.isEmpty()) {
            final String orderStr = this.fragments.stream().map(Fragment::getFragment)
                    .filter(Strings::isNotWhitespace)
                    .collect(Collectors.joining(SqlSymbol.COMMA_SPACE));
            if (Strings.isNotWhitespace(orderStr)) {
                return SqlSymbol.ORDER_BY_SPACE + orderStr;
            }
        }
        return SqlSymbol.EMPTY;
    }
}
