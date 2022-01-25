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
import io.github.mybatisx.core.property.Property;
import io.github.mybatisx.core.property.PropertyConverter;
import io.github.mybatisx.matcher.Matcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * 查询字段接口(支持Lambda表达式)
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/18
 * @since 1.0.0
 */
public interface LambdaSelect<T, C extends LambdaSelect<T, C>> extends PropertyConverter<T> {

    /**
     * 添加查询列
     *
     * @param property {@link Property}
     * @return {@code this}
     */
    default C select(final Property<T, ?> property) {
        return this.select(this.convert(property), null);
    }

    /**
     * 添加查询列
     *
     * @param property {@link Property}
     * @param alias    字段别名
     * @return {@code this}
     */
    default C select(final Property<T, ?> property, final String alias) {
        return this.select(this.convert(property), alias);
    }

    /**
     * 添加查询列
     *
     * @param property 属性名
     * @return {@code this}
     */
    default C select(final String property) {
        return this.select(property, null);
    }

    /**
     * 添加查询列
     *
     * @param property 属性名
     * @param alias    字段别名
     * @return {@code this}
     */
    C select(final String property, final String alias);

    /**
     * 筛选查询列
     *
     * @param matcher {@link Matcher}
     * @return {@code this}
     */
    C select(final Matcher<Column> matcher);

    /**
     * 添加查询列
     *
     * @param properties 多个属性(Map<别名, 属性名>)
     * @return {@code this}
     */
    C select(final Map<String, String> properties);

    /**
     * 添加查询列
     *
     * @param p1  属性1
     * @param as1 属性1对应别名
     * @param p2  属性2
     * @param as2 属性2对应别名
     * @return {@code this}
     */
    default C select(final Property<T, ?> p1, final String as1, final Property<T, ?> p2, final String as2) {
        return this.select(p1, as1).select(p2, as2);
    }

    /**
     * 添加查询列
     *
     * @param p1  属性1
     * @param as1 属性1对应别名
     * @param p2  属性2
     * @param as2 属性2对应别名
     * @param p3  属性3
     * @param as3 属性3对应别名
     * @return {@code this}
     */
    default C select(final Property<T, ?> p1, final String as1, final Property<T, ?> p2, final String as2,
                     final Property<T, ?> p3, final String as3) {
        return this.select(p1, as1).select(p2, as2).select(p3, as3);
    }

    /**
     * 添加查询列
     *
     * @param p1  属性1
     * @param as1 属性1对应别名
     * @param p2  属性2
     * @param as2 属性2对应别名
     * @return {@code this}
     */
    default C select(final String p1, final String as1, final String p2, final String as2) {
        return this.select(p1, as1).select(p2, as2);
    }

    /**
     * 添加查询列
     *
     * @param p1  属性1
     * @param as1 属性1对应别名
     * @param p2  属性2
     * @param as2 属性2对应别名
     * @param p3  属性3
     * @param as3 属性3对应别名
     * @return {@code this}
     */
    default C select(final String p1, final String as1, final String p2, final String as2,
                     final String p3, final String as3) {
        return this.select(p1, as1).select(p2, as2).select(p3, as3);
    }

    /**
     * 添加查询列
     *
     * @param properties 多个属性
     * @return {@code this}
     */
    default C selects(final String... properties) {
        return this.selects(Arrays.asList(properties));
    }

    /**
     * 添加查询列
     *
     * @param properties 多个属性
     * @return {@code this}
     */
    C selects(final Collection<String> properties);

    /**
     * 忽略查询列
     *
     * @param property {@link Property}
     * @return {@code this}
     */
    default C excludeProperty(final Property<T, ?> property) {
        return this.excludeProperty(this.convert(property));
    }

    /**
     * 忽略查询列
     *
     * @param property 属性名
     * @return {@code this}
     */
    C excludeProperty(final String property);

    /**
     * 忽略查询列
     *
     * @param properties 属性列表
     * @return {@code this}
     */
    default C excludeProperties(final String... properties) {
        return this.excludeProperties(Arrays.asList(properties));
    }

    /**
     * 忽略查询列
     *
     * @param properties 属性列表
     * @return {@code this}
     */
    C excludeProperties(final Collection<String> properties);
}
