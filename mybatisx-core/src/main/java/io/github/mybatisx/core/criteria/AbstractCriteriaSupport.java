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
package io.github.mybatisx.core.criteria;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.dialect.Dialect;
import lombok.extern.slf4j.Slf4j;

/**
 * 抽象条件
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
@Slf4j
@SuppressWarnings({"serial"})
public abstract class AbstractCriteriaSupport<T, C extends CriteriaWrapper<T, C>> extends
        AbstractConditionAcceptSupport<T, C> {

    // region Protected  methods

    // endregion

    // region Override methods

    @Override
    public String as() {
        return Constants.EMPTY;
    }

    @Override
    public C strict(boolean strict) {
        this.nonMatchingThenThrows.set(strict);
        return this.ctx;
    }

    @Override
    public C dialect(Dialect dialect) {
        this.dialect = dialect;
        return this.ctx;
    }

    @Override
    public C setVersion(Object value) {
        return this.ctx;
    }

    // endregion

}
