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

import io.github.mybatisx.base.constant.NullPrecedence;
import io.github.mybatisx.core.property.Property;
import io.github.mybatisx.core.property.PropertyConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 排序接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/25
 * @since 1.0.0
 */
public interface LambdaSort<T, C extends LambdaSort<T, C>> extends PropertyConverter<T> {

    // region Asc methods

    /**
     * 升序
     *
     * @param property {@link Property}
     * @return {@code this}
     */
    default C asc(final Property<T, ?> property) {
        return this.asc(property, false);
    }

    /**
     * 升序
     *
     * @param property   {@link Property}
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    default C asc(final Property<T, ?> property, final NullPrecedence precedence) {
        return this.asc(property, false, precedence);
    }

    /**
     * 升序
     *
     * @param property   {@link Property}
     * @param ignoreCase 是否忽略大小写排序
     * @return {@code this}
     */
    default C asc(final Property<T, ?> property, final boolean ignoreCase) {
        return this.asc(property, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 升序
     *
     * @param property   {@link Property}
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    default C asc(final Property<T, ?> property, final boolean ignoreCase, final NullPrecedence precedence) {
        return this.asc(this.convert(property), ignoreCase, precedence);
    }

    /**
     * 升序
     *
     * @param property 属性名
     * @return {@code this}
     */
    default C asc(final String property) {
        return this.asc(property, false);
    }

    /**
     * 升序
     *
     * @param property   属性名
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    default C asc(final String property, final NullPrecedence precedence) {
        return this.asc(property, false, precedence);
    }

    /**
     * 升序
     *
     * @param property   属性名
     * @param ignoreCase 是否忽略大小写排序
     * @return {@code this}
     */
    default C asc(final String property, final boolean ignoreCase) {
        return this.asc(property, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 升序
     *
     * @param property   属性名
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    C asc(final String property, final boolean ignoreCase, final NullPrecedence precedence);

    /**
     * 升序
     *
     * @param properties 属性名列表
     * @return {@code this}
     */
    default C asc(final String... properties) {
        return this.asc(false, properties);
    }

    /**
     * 升序
     *
     * @param precedence {@link NullPrecedence}
     * @param properties 属性名列表
     * @return {@code this}
     */
    default C asc(final NullPrecedence precedence, final String... properties) {
        return this.asc(false, precedence, properties);
    }

    /**
     * 升序
     *
     * @param ignoreCase 是否忽略大小写排序
     * @param properties 属性名列表
     * @return {@code this}
     */
    default C asc(final boolean ignoreCase, final String... properties) {
        return this.asc(ignoreCase, NullPrecedence.NONE, properties);
    }

    /**
     * 升序
     *
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @param properties 属性名列表
     * @return {@code this}
     */
    default C asc(final boolean ignoreCase, final NullPrecedence precedence, final String... properties) {
        return this.asc(Arrays.asList(properties), ignoreCase, precedence);
    }

    /**
     * 升序
     *
     * @param properties 属性名列表
     * @return {@code this}
     */
    default C asc(final List<String> properties) {
        return this.asc(properties, false);
    }

    /**
     * 升序
     *
     * @param precedence {@link NullPrecedence}
     * @param properties 属性名列表
     * @return {@code this}
     */
    default C asc(final List<String> properties, final NullPrecedence precedence) {
        return this.asc(properties, false, precedence);
    }

    /**
     * 升序
     *
     * @param properties 属性名列表
     * @param ignoreCase 是否忽略大小写排序
     * @return {@code this}
     */
    default C asc(final List<String> properties, final boolean ignoreCase) {
        return this.asc(properties, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 升序
     *
     * @param properties 属性名列表
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    C asc(final List<String> properties, final boolean ignoreCase, final NullPrecedence precedence);

    /**
     * 升序
     *
     * @param properties {@link Property}列表
     * @return {@code this}
     */
    default C asc(Collection<Property<T, ?>> properties) {
        return this.asc(false, new ArrayList<>(properties));
    }

    /**
     * 升序
     *
     * @param precedence {@link NullPrecedence}
     * @param properties {@link Property}列表
     * @return {@code this}
     */
    default C asc(final NullPrecedence precedence, List<Property<T, ?>> properties) {
        return this.asc(false, precedence, properties);
    }

    /**
     * 升序
     *
     * @param ignoreCase 是否忽略大小写排序
     * @param properties {@link Property}列表
     * @return {@code this}
     */
    default C asc(final boolean ignoreCase, final List<Property<T, ?>> properties) {
        return this.asc(ignoreCase, NullPrecedence.NONE, properties);
    }

    /**
     * 升序
     *
     * @param properties {@link Property}列表
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    C asc(final boolean ignoreCase, final NullPrecedence precedence, final List<Property<T, ?>> properties);

    // endregion

    // region Desc methods

    /**
     * 降序
     *
     * @param property {@link Property}
     * @return {@code this}
     */
    default C desc(final Property<T, ?> property) {
        return this.desc(property, false);
    }

    /**
     * 降序
     *
     * @param property   {@link Property}
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    default C desc(final Property<T, ?> property, final NullPrecedence precedence) {
        return this.desc(property, false, precedence);
    }

    /**
     * 降序
     *
     * @param property   {@link Property}
     * @param ignoreCase 是否忽略大小写排序
     * @return {@code this}
     */
    default C desc(final Property<T, ?> property, final boolean ignoreCase) {
        return this.desc(property, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 降序
     *
     * @param property   {@link Property}
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    default C desc(final Property<T, ?> property, final boolean ignoreCase, final NullPrecedence precedence) {
        return this.desc(this.convert(property), ignoreCase, precedence);
    }

    /**
     * 降序
     *
     * @param property 属性名
     * @return {@code this}
     */
    default C desc(final String property) {
        return this.desc(property, false);
    }

    /**
     * 降序
     *
     * @param property   属性名
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    default C desc(final String property, final NullPrecedence precedence) {
        return this.desc(property, false, precedence);
    }

    /**
     * 降序
     *
     * @param property   属性名
     * @param ignoreCase 是否忽略大小写排序
     * @return {@code this}
     */
    default C desc(final String property, final boolean ignoreCase) {
        return this.desc(property, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 降序
     *
     * @param property   属性名
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    C desc(final String property, final boolean ignoreCase, final NullPrecedence precedence);

    /**
     * 降序
     *
     * @param properties 属性名列表
     * @return {@code this}
     */
    default C desc(final String... properties) {
        return this.desc(false, properties);
    }

    /**
     * 降序
     *
     * @param precedence {@link NullPrecedence}
     * @param properties 属性名列表
     * @return {@code this}
     */
    default C desc(final NullPrecedence precedence, final String... properties) {
        return this.desc(false, precedence, properties);
    }

    /**
     * 降序
     *
     * @param ignoreCase 是否忽略大小写排序
     * @param properties 属性名列表
     * @return {@code this}
     */
    default C desc(final boolean ignoreCase, final String... properties) {
        return this.desc(ignoreCase, NullPrecedence.NONE, properties);
    }

    /**
     * 降序
     *
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @param properties 属性名列表
     * @return {@code this}
     */
    default C desc(final boolean ignoreCase, final NullPrecedence precedence, final String... properties) {
        return this.desc(Arrays.asList(properties), ignoreCase, precedence);
    }

    /**
     * 降序
     *
     * @param properties 属性名列表
     * @return {@code this}
     */
    default C desc(final List<String> properties) {
        return this.desc(properties, false);
    }

    /**
     * 降序
     *
     * @param precedence {@link NullPrecedence}
     * @param properties 属性名列表
     * @return {@code this}
     */
    default C desc(final List<String> properties, final NullPrecedence precedence) {
        return this.desc(properties, false, precedence);
    }

    /**
     * 降序
     *
     * @param properties 属性名列表
     * @param ignoreCase 是否忽略大小写排序
     * @return {@code this}
     */
    default C desc(final List<String> properties, final boolean ignoreCase) {
        return this.desc(properties, ignoreCase, NullPrecedence.NONE);
    }

    /**
     * 降序
     *
     * @param properties 属性名列表
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    C desc(final List<String> properties, final boolean ignoreCase, final NullPrecedence precedence);

    /**
     * 降序
     *
     * @param properties {@link Property}列表
     * @return {@code this}
     */
    default C desc(Collection<Property<T, ?>> properties) {
        return this.desc(false, new ArrayList<>(properties));
    }

    /**
     * 降序
     *
     * @param precedence {@link NullPrecedence}
     * @param properties {@link Property}列表
     * @return {@code this}
     */
    default C desc(final NullPrecedence precedence, List<Property<T, ?>> properties) {
        return this.desc(false, precedence, properties);
    }

    /**
     * 降序
     *
     * @param ignoreCase 是否忽略大小写排序
     * @param properties {@link Property}列表
     * @return {@code this}
     */
    default C desc(final boolean ignoreCase, final List<Property<T, ?>> properties) {
        return this.desc(ignoreCase, NullPrecedence.NONE, properties);
    }

    /**
     * 降序
     *
     * @param properties {@link Property}列表
     * @param ignoreCase 是否忽略大小写排序
     * @param precedence {@link NullPrecedence}
     * @return {@code this}
     */
    C desc(final boolean ignoreCase, final NullPrecedence precedence, final List<Property<T, ?>> properties);

    // endregion

}
