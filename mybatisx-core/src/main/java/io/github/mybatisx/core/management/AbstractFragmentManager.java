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

import io.github.mybatisx.base.constant.Constants;
import lombok.AllArgsConstructor;

/**
 * 抽象片段管理器
 *
 * @author wvkity
 * @created 2022/1/7
 * @since 1.0.0
 */
@AllArgsConstructor
@SuppressWarnings({"serial"})
public abstract class AbstractFragmentManager implements FragmentManager {

    /**
     * WHERE片段存储器
     */
    protected final WhereStorage whereStorage;

    public AbstractFragmentManager() {
        this(new WhereStorage());
    }

    @Override
    public boolean hasCondition() {
        return !this.whereStorage.isEmpty();
    }

    @Override
    public String getWhereString() {
        return this.whereStorage.getFragment();
    }

    @Override
    public String getCompleteString(String groupReplacement) {
        if (this.hasFragment()) {
            return this.getWhereString();
        }
        return Constants.EMPTY;
    }
}
