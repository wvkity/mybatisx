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
package io.github.mybatisx.core.criteria.query;

import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.core.criteria.support.AbstractBaseCriteria;
import io.github.mybatisx.core.property.Property;

import java.util.Map;

/**
 * 抽象查询条件
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
@SuppressWarnings({"serial"})
public abstract class AbstractGenericQueryCriteria<T, C extends GenericQueryCriteria<T, C>> extends
        AbstractBaseCriteria<T, C> implements GenericQueryCriteria<T, C> {

    @Override
    public C propAsAlias(boolean using) {
        this.propertyAsAlias = using;
        return this.context;
    }

    @Override
    public String getResultMap() {
        return this.resultMap;
    }

    @Override
    public C setResultMap(String resultMap) {
        this.resultMap = resultMap;
        return this.context;
    }

    @Override
    public Class<?> getResultType() {
        return this.resultType;
    }

    @Override
    public C setResultType(Class<?> resultType) {
        this.resultType = resultType;
        return this.context;
    }

    @Override
    public String getMapKey() {
        return this.mapKey;
    }

    @Override
    public C setMapKey(Property<T, ?> property) {
        //  noinspection DuplicatedCode
        if (property != null) {
            final String prop = this.convert(property);
            if (this.propertyAsAlias) {
                this.setMapKey(prop);
            } else {
                final Column column = this.convert(this.convert(property));
                if (column != null) {
                    this.setMapKey(column.getColumn());
                }
            }
        }
        return this.context;
    }

    @Override
    public C setMapKey(String mapKey) {
        this.mapKey = mapKey;
        return this.context;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class<? extends Map> getMapType() {
        return this.mapType;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public C setMapType(Class<? extends Map> mapType) {
        this.mapType = mapType;
        return this.context;
    }
}
