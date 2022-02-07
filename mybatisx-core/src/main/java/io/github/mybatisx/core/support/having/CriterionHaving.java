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
package io.github.mybatisx.core.support.having;

import io.github.mybatisx.base.convert.ParameterConverter;
import io.github.mybatisx.base.convert.PlaceholderConverter;
import io.github.mybatisx.base.criterion.Criterion;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

/**
 * @author wvkity
 * @created 2022/1/30
 * @since 1.0.0
 */
@Builder
@RequiredArgsConstructor
public class CriterionHaving implements Having {

    private static final long serialVersionUID = 1476067640780885692L;

    /**
     * {@link Criterion}
     */
    private final Criterion criterion;

    @Override
    public String getFragment(ParameterConverter pc, PlaceholderConverter phc) {
        return this.criterion.getFragment(pc, phc);
    }
}
