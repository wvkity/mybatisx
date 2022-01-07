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
package io.github.mybatisx.core.criteria.support;

import io.github.mybatisx.core.sql.DefaultSqlManager;

/**
 * 基础条件(支持Lambda表达式)
 *
 * @author wvkity
 * @created 2022/1/6
 * @since 1.0.0
 */
public class LambdaCriteriaImpl<T> extends AbstractLambdaCriteria<T, LambdaCriteriaImpl<T>> {

    private static final long serialVersionUID = -2981849275689397498L;

    public LambdaCriteriaImpl(Class<T> entity) {
        this.entity = entity;
        this.newInit(null);
        this.sqlManager = new DefaultSqlManager(this, this.fragmentManager);
    }

    public static <T> LambdaCriteriaImpl<T> from(final Class<T> entity) {
        return new LambdaCriteriaImpl<>(entity);
    }
    
}
