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
package io.github.mybatisx.base.naming;

import io.github.mybatisx.annotation.NamingStrategy;

/**
 * 抽象命名策略
 *
 * @author wvkity
 * @created 2021/12/28
 * @since 1.0.0
 */
public abstract class AbstractNamingConverter implements NamingConverter {

    private final NamingStrategy sourceStrategy;
    private final NamingStrategy targetStrategy;

    public AbstractNamingConverter(NamingStrategy sourceStrategy, NamingStrategy targetStrategy) {
        this.sourceStrategy = sourceStrategy;
        this.targetStrategy = targetStrategy;
    }

    @Override
    public NamingStrategy getSourceStrategy() {
        return this.sourceStrategy;
    }

    @Override
    public NamingStrategy getTargetStrategy() {
        return this.targetStrategy;
    }

    @Override
    public String convert(String src, NamingStrategy from, NamingStrategy to) {
        return NamingCase.to(from).to(to, src);
    }

}
