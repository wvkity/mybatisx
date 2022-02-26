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

import io.github.mybatisx.core.criteria.CriteriaWrapper;
import io.github.mybatisx.core.property.Property;

import java.util.Arrays;
import java.util.List;

/**
 * 查询条件接口(支持Lambda表达式)
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
public interface LambdaQueryCriteria<T, C extends LambdaQueryCriteria<T, C>> extends CriteriaWrapper<T, C>,
        QueryMixture<T, C>, LambdaSelect<T, C>, LambdaFunctionSelect<T, C>, LambdaSort<T, C>, GenericHaving<T, C> {

    /**
     * 设置Map类型返回值的键
     *
     * @param property {@link Property}
     * @return {@code this}
     */
    C mapKey(final Property<T, ?> property);

    /**
     * 分组
     *
     * @param property 属性名
     * @return {@code this}
     */
    default C group(final Property<T, ?> property) {
        return this.group(this.convert(property));
    }

    /**
     * 分组
     *
     * @param property 属性名
     * @return {@code this}
     */
    C group(final String property);

    /**
     * 多个分组
     *
     * @param properties 属性列表
     * @return {@code this}
     */
    default C group(final String... properties) {
        return this.group(Arrays.asList(properties));
    }

    /**
     * 多个分组
     *
     * @param properties 属性列表
     * @return {@code this}
     */
    C group(final List<String> properties);
}
