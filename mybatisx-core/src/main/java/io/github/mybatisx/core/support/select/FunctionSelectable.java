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

import io.github.mybatisx.core.support.function.AggFunction;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 聚合函数查询列
 *
 * @author wvkity
 * @created 2022/1/18
 * @since 1.0.0
 */
@Builder
@ToString
@RequiredArgsConstructor
public class FunctionSelectable implements Selectable {

    private static final long serialVersionUID = -6859021427874169676L;
    /**
     * 查询类型
     */
    @Getter
    private final SelectType type;
    /**
     * 聚合函数
     */
    private final AggFunction function;

    @Override
    public String getColumn() {
        return this.function.getColumn();
    }

    @Override
    public String getAlias() {
        return this.function.getAlias();
    }

    @Override
    public String getFragment(boolean isQuery) {
        return this.function.getFragment(isQuery);
    }
}
