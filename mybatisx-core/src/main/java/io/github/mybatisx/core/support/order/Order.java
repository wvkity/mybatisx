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

import io.github.mybatisx.base.constant.NullPrecedence;
import io.github.mybatisx.base.fragment.Fragment;
import io.github.mybatisx.core.criteria.query.Query;

/**
 * 排序接口
 *
 * @author wvkity
 * @created 2022/1/25
 * @since 1.0.0
 */
public interface Order extends Fragment {

    /**
     * 获取{@link Query}
     *
     * @return {@link Query}
     */
    Query<?> getQuery();

    /**
     * 获取表别名
     *
     * @return 表别名
     */
    String getAlias();

    /**
     * 是否为升序
     *
     * @return boolean
     */
    boolean isAscending();

    /**
     * 是否忽略大小写排序
     *
     * @return boolean
     */
    boolean isIgnoreCase();

    /**
     * 空值排序优先级
     *
     * @return {@link NullPrecedence}
     */
    NullPrecedence getPrecedence();

}
