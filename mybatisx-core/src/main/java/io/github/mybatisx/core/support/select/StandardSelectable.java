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

import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.core.criteria.query.Query;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

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
     * {@link Criteria}
     */
    private final Query<?> criteria;
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

    @Override
    public String getFragment(boolean isQuery) {
        return null;
    }
}
