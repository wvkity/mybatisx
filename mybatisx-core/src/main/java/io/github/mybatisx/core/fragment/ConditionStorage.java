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

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.convert.ParameterConverter;
import io.github.mybatisx.base.convert.PlaceholderConverter;
import io.github.mybatisx.base.criterion.Criterion;
import io.github.mybatisx.base.fragment.AbstractFragmentList;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.util.Collections;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 条件存储
 *
 * @author wvkity
 * @created 2022/1/7
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class ConditionStorage extends AbstractFragmentList<Criterion> {

    private static final long serialVersionUID = -4544342979966126485L;

    /**
     * 参数转换器
     */
    @Getter
    private final ParameterConverter parameterConverter;
    /**
     * 占位符转换器
     */
    @Getter
    private final PlaceholderConverter placeholderConverter;
    /**
     * 条件引用
     */
    private SoftReference<String> reference;

    @Override
    public String getFragment() {
        if (!isEmpty()) {
            if (this.reference == null) {
                final List<String> conditions = new ArrayList<>(this.fragments.size());
                for (Criterion it : this.fragments) {
                    Strings.ifNotWhitespaceThen(it.getFragment(this.parameterConverter, this.placeholderConverter),
                            conditions::add);
                }
                if (Collections.isNotEmpty(conditions)) {
                    final String fragment = String.join(SqlSymbol.SPACE, conditions).trim();
                    this.reference = new SoftReference<>(fragment);
                    return fragment;
                }
            } else {
                return this.reference.get();
            }
        }
        return Constants.EMPTY;
    }
}
