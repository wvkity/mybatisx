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
import io.github.mybatisx.lang.Strings;
import lombok.RequiredArgsConstructor;

/**
 * 纯SQL排序
 *
 * @author wvkity
 * @created 2022/2/18
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class PureOrder implements Order {

    private static final long serialVersionUID = -5337169976374296878L;

    /**
     * 排序SQL语句
     */
    private final String orderBody;

    @Override
    public String getFragment() {
        if (Strings.isNotWhitespace(this.orderBody)) {
            return this.orderBody;
        }
        return Constants.EMPTY;
    }

    public static PureOrder of(final String orderBody) {
        if (Strings.isNotWhitespace(orderBody)) {
            return new PureOrder(orderBody);
        }
        return null;
    }
}
