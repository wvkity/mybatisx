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
package io.github.mybatisx.core.criteria.update;

import io.github.mybatisx.core.criteria.support.AbstractLambdaCriteria;
import io.github.mybatisx.matcher.Matcher;

/**
 * 抽象更新条件(支持Lambda表达式)
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
@SuppressWarnings({"serial"})
public abstract class AbstractLambdaUpdateCriteria<T, C extends LambdaUpdateCriteria<T, C>> extends
        AbstractLambdaCriteria<T, C> implements LambdaUpdateCriteria<T, C> {

    @Override
    public <V> C set(String property, V value, Matcher<V> matcher) {
        if (matcher == null || matcher.matches(value)) {
            this.update(property, value, true, false);
        }
        return this.context;
    }

    @Override
    public <V> C setIfAbsent(String property, V value, Matcher<V> matcher) {
        if (matcher == null || matcher.matches(value)) {
            this.update(property, value, true, true);
        }
        return this.context;
    }

    @Override
    public String getUpdateFragment() {
        return this.sqlManager.getUpdateFragment();
    }
}
