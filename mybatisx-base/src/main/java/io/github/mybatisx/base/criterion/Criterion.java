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
package io.github.mybatisx.base.criterion;

import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.fragment.Fragment;

/**
 * 条件接口
 *
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
public interface Criterion extends Fragment {

    /**
     * 获取{@link Symbol}值
     *
     * @return {@link Symbol}
     */
    default Symbol getSymbol() {
        return null;
    }

    /**
     * 获取字段名
     *
     * @return 字段名
     */
    default String getColumn() {
        return null;
    }

    /**
     * 获取参数值
     *
     * @return 参数值
     */
    default Object getOrgValue() {
        return null;
    }
}
