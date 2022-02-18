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
package io.github.mybatisx.core.criteria.query;

/**
 * 抽象子查询条件
 *
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/2/18
 * @since 1.0.0
 */
@SuppressWarnings({"serial"})
public abstract class AbstractPlainSubQuery<C extends PlainSubQuery<C>> extends AbstractPlainQueryCriteria<Object, C>
        implements PlainSubQuery<C> {

    @Override
    public String getTableName(boolean jointAs) {
        return this.getTableName(this.outerQuery.getFragment(), jointAs);
    }
}
