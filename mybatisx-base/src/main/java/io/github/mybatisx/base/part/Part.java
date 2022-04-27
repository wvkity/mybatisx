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
package io.github.mybatisx.base.part;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.convert.ParameterConverter;
import io.github.mybatisx.base.convert.PlaceholderConverter;
import io.github.mybatisx.base.fragment.Fragment;

/**
 * SQL片段
 *
 * @author wvkity
 * @created 2022/4/26
 * @since 1.0.0
 */
public interface Part extends Fragment {

    @Override
    default String getFragment() {
        return Constants.EMPTY;
    }

    /**
     * 解析成SQL片段
     *
     * @param pc  {@link ParameterConverter}
     * @param phc {@link PlaceholderConverter}
     * @return 占位符参数
     */
    String getFragment(final ParameterConverter pc, final PlaceholderConverter phc);
}
