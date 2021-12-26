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
package io.github.mybatisx.base.fragment;

import io.github.mybatisx.lang.Objects;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 抽象片段列表
 *
 * @author wvkity
 * @created 2021/12/23
 * @since 1.0.0
 */
@SuppressWarnings({"serial"})
public abstract class AbstractFragmentSet<T> implements Fragments<T> {

    /**
     * 片段列表
     */
    protected final Set<T> fragments = new HashSet<>();

    @Override
    public void add(T t) {
        if (Objects.nonNull(t)) {
            this.fragments.add(t);
        }
    }

    @Override
    public void addAll(Collection<? extends T> c) {
        if (Objects.isNotEmpty(c)) {
            for (T it : c) {
                this.add(it);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.fragments.isEmpty();
    }

}
