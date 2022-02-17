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
package io.github.mybatisx.core.fragment;

import io.github.mybatisx.base.convert.ParameterConverter;
import io.github.mybatisx.base.convert.PlaceholderConverter;
import io.github.mybatisx.base.criteria.Criteria;

/**
 * 默认片段管理器
 *
 * @author wvkity
 * @created 2022/1/7
 * @since 1.0.0
 */
public class DefaultFragmentManager extends AbstractFragmentManager {

    private static final long serialVersionUID = 2257490025235571458L;

    public DefaultFragmentManager(Criteria<?> criteria, ParameterConverter parameterConverter,
                                  PlaceholderConverter placeholderConverter) {
        super(criteria, parameterConverter, placeholderConverter);
    }

    public DefaultFragmentManager(Criteria<?> criteria, ConditionStorage conditionStorage) {
        super(criteria, conditionStorage, null, null, null, null);
    }

    public DefaultFragmentManager(Criteria<?> criteria, ConditionStorage conditionStorage,
                                  SelectableStorage selectableStorage, GroupStorage groupStorage,
                                  HavingStorage havingStorage, OrderStorage orderStorage) {
        super(criteria, conditionStorage, selectableStorage, groupStorage, havingStorage, orderStorage);
    }
}
