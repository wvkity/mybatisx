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
import io.github.mybatisx.lang.Strings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 复杂纯SQL查询字段
 *
 * @author wvkity
 * @created 2022/2/18
 * @since 1.0.0
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ComplexSelectable implements Selectable {

    private static final long serialVersionUID = 1904361210081384513L;
    /**
     * 查询语句
     */
    private final String selectBody;

    @Override
    public String getColumn() {
        return null;
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public SelectType getType() {
        return SelectType.PLAIN;
    }

    @Override
    public String getFragment(boolean isQuery) {
        if (isQuery) {
            return this.selectBody;
        }
        return Constants.EMPTY;
    }

    /**
     * 创建{@link ComplexSelectable}对象
     *
     * @param selectBody SQL语句
     * @return {@link ComplexSelectable}
     */
    public static ComplexSelectable of(final String selectBody) {
        if (Strings.isNotWhitespace(selectBody)) {
            return new ComplexSelectable(selectBody);
        }
        return null;
    }
}
