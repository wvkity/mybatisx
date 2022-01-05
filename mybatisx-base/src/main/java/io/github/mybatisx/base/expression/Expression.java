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
package io.github.mybatisx.base.expression;

import io.github.mybatisx.base.criteria.Criteria;

/**
 * 条件表达式
 *
 * @param <T> 字段类型
 * @author wvkity
 * @created 2021/12/23
 * @since 1.0.0
 */
public interface Expression<T> {

    /**
     * 获取{@link Criteria}
     *
     * @return {@link Criteria}
     */
    Criteria<?> getCriteria();

}
