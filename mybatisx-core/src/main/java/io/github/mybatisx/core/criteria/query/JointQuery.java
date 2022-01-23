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

import io.github.mybatisx.base.constant.Join;

/**
 * 联表查询接口
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2022/1/22
 * @since 1.0.0
 */
public interface JointQuery<T> extends Query<T> {

    /**
     * 获取联表方式
     *
     * @return {@link Join}
     */
    Join getJoin();

    /**
     * 是否抓取联表字段
     *
     * @return boolean
     */
    boolean isFetch();
}
