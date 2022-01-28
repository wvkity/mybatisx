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

import io.github.mybatisx.core.property.Property;
import io.github.mybatisx.core.property.PropertyConverter;

/**
 * 聚合函数查询接口(支持Lambda表达式)
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/28
 * @since 1.0.0
 */
public interface LambdaFunctionSelect<T, C extends LambdaFunctionSelect<T, C>> extends PropertyConverter<T> {

    // region Count function methods

    /**
     * count聚合函数
     *
     * @param property {@link Property}
     * @return {@code this}
     */
    default C count(final Property<T, ?> property) {
        return this.count(property, false);
    }

    /**
     * count聚合函数
     *
     * @param property {@link Property}
     * @param distinct 是否去重
     * @return {@code this}
     */
    default C count(final Property<T, ?> property, final boolean distinct) {
        return this.count(property, null, distinct);
    }

    /**
     * count聚合函数
     *
     * @param property {@link Property}
     * @param alias    别名
     * @return {@code this}
     */
    default C count(final Property<T, ?> property, final String alias) {
        return this.count(property, alias, false);
    }

    /**
     * count聚合函数
     *
     * @param property {@link Property}
     * @param alias    别名
     * @param distinct 是否去重
     * @return {@code this}
     */
    default C count(final Property<T, ?> property, final String alias, final boolean distinct) {
        return this.count(this.convert(property), alias, distinct);
    }

    /**
     * count聚合函数
     *
     * @param property 属性名
     * @return {@code this}
     */
    default C count(final String property) {
        return this.count(property, false);
    }

    /**
     * count聚合函数
     *
     * @param property 属性名
     * @param distinct 是否去重
     * @return {@code this}
     */
    default C count(final String property, final boolean distinct) {
        return this.count(property, null, distinct);
    }

    /**
     * count聚合函数
     *
     * @param property 属性名
     * @param alias    别名
     * @return {@code this}
     */
    default C count(final String property, final String alias) {
        return this.count(property, alias, false);
    }

    /**
     * count聚合函数
     *
     * @param property 属性名
     * @param alias    别名
     * @param distinct 是否去重
     * @return {@code this}
     */
    C count(final String property, final String alias, final boolean distinct);

    // endregion

    // region Sum function methods

    /**
     * sum聚合函数
     *
     * @param property {@link Property}
     * @return {@code this}
     */
    default C sum(final Property<T, ?> property) {
        return this.sum(property, false);
    }

    /**
     * sum聚合函数
     *
     * @param property {@link Property}
     * @param distinct 是否去去重
     * @return {@code this}
     */
    default C sum(final Property<T, ?> property, final boolean distinct) {
        return this.sum(property, null, null, distinct);
    }

    /**
     * sum聚合函数
     *
     * @param property {@link Property}
     * @param scale    保留小数位数
     * @return {@code this}
     */
    default C sum(final Property<T, ?> property, final Integer scale) {
        return this.sum(property, scale, false);
    }

    /**
     * sum聚合函数
     *
     * @param property {@link Property}
     * @param scale    保留小数位数
     * @param distinct 是否去去重
     * @return {@code this}
     */
    default C sum(final Property<T, ?> property, final Integer scale, final boolean distinct) {
        return this.sum(property, null, scale, distinct);
    }

    /**
     * sum聚合函数
     *
     * @param property {@link Property}
     * @param alias    别名
     * @return {@code this}
     */
    default C sum(final Property<T, ?> property, final String alias) {
        return this.sum(property, alias, false);
    }

    /**
     * sum聚合函数
     *
     * @param property {@link Property}
     * @param alias    别名
     * @param distinct 是否去去重
     * @return {@code this}
     */
    default C sum(final Property<T, ?> property, final String alias, final boolean distinct) {
        return this.sum(property, alias, null, distinct);
    }

    /**
     * sum聚合函数
     *
     * @param property {@link Property}
     * @param alias    别名
     * @param scale    保留小数位数
     * @return {@code this}
     */
    default C sum(final Property<T, ?> property, final String alias, final Integer scale) {
        return this.sum(property, alias, scale, false);
    }

    /**
     * sum聚合函数
     *
     * @param property {@link Property}
     * @param alias    别名
     * @param scale    保留小数位数
     * @param distinct 是否去去重
     * @return {@code this}
     */
    default C sum(final Property<T, ?> property, final String alias, final Integer scale, final boolean distinct) {
        return this.sum(this.convert(property), alias, scale, distinct);
    }

    /**
     * sum聚合函数
     *
     * @param property 属性名
     * @return {@code this}
     */
    default C sum(final String property) {
        return this.sum(property, false);
    }

    /**
     * sum聚合函数
     *
     * @param property 属性名
     * @param distinct 是否去去重
     * @return {@code this}
     */
    default C sum(final String property, final boolean distinct) {
        return this.sum(property, null, null, distinct);
    }

    /**
     * sum聚合函数
     *
     * @param property 属性名
     * @param scale    保留小数位数
     * @return {@code this}
     */
    default C sum(final String property, final Integer scale) {
        return this.sum(property, scale, false);
    }

    /**
     * sum聚合函数
     *
     * @param property 属性名
     * @param scale    保留小数位数
     * @param distinct 是否去去重
     * @return {@code this}
     */
    default C sum(final String property, final Integer scale, final boolean distinct) {
        return this.sum(property, null, scale, distinct);
    }

    /**
     * sum聚合函数
     *
     * @param property 属性名
     * @param alias    别名
     * @return {@code this}
     */
    default C sum(final String property, final String alias) {
        return this.sum(property, alias, false);
    }

    /**
     * sum聚合函数
     *
     * @param property 属性名
     * @param alias    别名
     * @param distinct 是否去去重
     * @return {@code this}
     */
    default C sum(final String property, final String alias, final boolean distinct) {
        return this.sum(property, alias, null, distinct);
    }

    /**
     * sum聚合函数
     *
     * @param property 属性名
     * @param alias    别名
     * @param scale    保留小数位数
     * @return {@code this}
     */
    default C sum(final String property, final String alias, final Integer scale) {
        return this.sum(property, alias, scale, false);
    }

    /**
     * sum聚合函数
     *
     * @param property 属性名
     * @param alias    别名
     * @param scale    保留小数位数
     * @param distinct 是否去去重
     * @return {@code this}
     */
    C sum(final String property, final String alias, final Integer scale, final boolean distinct);

    // endregion

    // region Avg function methods

    /**
     * avg聚合函数
     *
     * @param property {@link Property}
     * @return {@code this}
     */
    default C avg(final Property<T, ?> property) {
        return this.avg(property, false);
    }

    /**
     * avg聚合函数
     *
     * @param property {@link Property}
     * @param distinct 是否去去重
     * @return {@code this}
     */
    default C avg(final Property<T, ?> property, final boolean distinct) {
        return this.avg(property, null, null, distinct);
    }

    /**
     * avg聚合函数
     *
     * @param property {@link Property}
     * @param scale    保留小数位数
     * @return {@code this}
     */
    default C avg(final Property<T, ?> property, final Integer scale) {
        return this.avg(property, scale, false);
    }

    /**
     * avg聚合函数
     *
     * @param property {@link Property}
     * @param scale    保留小数位数
     * @param distinct 是否去去重
     * @return {@code this}
     */
    default C avg(final Property<T, ?> property, final Integer scale, final boolean distinct) {
        return this.avg(property, null, scale, distinct);
    }

    /**
     * avg聚合函数
     *
     * @param property {@link Property}
     * @param alias    别名
     * @return {@code this}
     */
    default C avg(final Property<T, ?> property, final String alias) {
        return this.avg(property, alias, false);
    }

    /**
     * avg聚合函数
     *
     * @param property {@link Property}
     * @param alias    别名
     * @param distinct 是否去去重
     * @return {@code this}
     */
    default C avg(final Property<T, ?> property, final String alias, final boolean distinct) {
        return this.avg(property, alias, null, distinct);
    }

    /**
     * avg聚合函数
     *
     * @param property {@link Property}
     * @param alias    别名
     * @param scale    保留小数位数
     * @return {@code this}
     */
    default C avg(final Property<T, ?> property, final String alias, final Integer scale) {
        return this.avg(property, alias, scale, false);
    }

    /**
     * avg聚合函数
     *
     * @param property {@link Property}
     * @param alias    别名
     * @param scale    保留小数位数
     * @param distinct 是否去去重
     * @return {@code this}
     */
    default C avg(final Property<T, ?> property, final String alias, final Integer scale, final boolean distinct) {
        return this.avg(this.convert(property), alias, scale, distinct);
    }

    /**
     * avg聚合函数
     *
     * @param property 属性名
     * @return {@code this}
     */
    default C avg(final String property) {
        return this.avg(property, false);
    }

    /**
     * avg聚合函数
     *
     * @param property 属性名
     * @param distinct 是否去去重
     * @return {@code this}
     */
    default C avg(final String property, final boolean distinct) {
        return this.avg(property, (Integer) null, distinct);
    }

    /**
     * avg聚合函数
     *
     * @param property 属性名
     * @param scale    保留小数位数
     * @return {@code this}
     */
    default C avg(final String property, final Integer scale) {
        return this.avg(property, scale, false);
    }

    /**
     * avg聚合函数
     *
     * @param property 属性名
     * @param scale    保留小数位数
     * @param distinct 是否去去重
     * @return {@code this}
     */
    default C avg(final String property, final Integer scale, final boolean distinct) {
        return this.avg(property, null, scale, distinct);
    }

    /**
     * avg聚合函数
     *
     * @param property 属性名
     * @param alias    别名
     * @return {@code this}
     */
    default C avg(final String property, final String alias) {
        return this.avg(property, alias, false);
    }

    /**
     * avg聚合函数
     *
     * @param property 属性名
     * @param alias    别名
     * @param distinct 是否去去重
     * @return {@code this}
     */
    default C avg(final String property, final String alias, final boolean distinct) {
        return this.avg(property, alias, null, distinct);
    }

    /**
     * avg聚合函数
     *
     * @param property 属性名
     * @param alias    别名
     * @param scale    保留小数位数
     * @return {@code this}
     */
    default C avg(final String property, final String alias, final Integer scale) {
        return this.avg(property, alias, scale, false);
    }

    /**
     * avg聚合函数
     *
     * @param property 属性名
     * @param alias    别名
     * @param scale    保留小数位数
     * @param distinct 是否去去重
     * @return {@code this}
     */
    C avg(final String property, final String alias, final Integer scale, final boolean distinct);

    // endregion

    // region Min function methods

    /**
     * min聚合函数
     *
     * @param property {@link Property}
     * @return {@code this}
     */
    default C min(final Property<T, ?> property) {
        return this.min(property, null, null);
    }

    /**
     * min聚合函数
     *
     * @param property {@link Property}
     * @param scale    保留小数位数
     * @return {@code this}
     */
    default C min(final Property<T, ?> property, final Integer scale) {
        return this.min(property, null, scale);
    }

    /**
     * min聚合函数
     *
     * @param property {@link Property}
     * @param alias    别名
     * @return {@code this}
     */
    default C min(final Property<T, ?> property, final String alias) {
        return this.min(property, alias, null);
    }

    /**
     * min聚合函数
     *
     * @param property {@link Property}
     * @param alias    别名
     * @param scale    保留小数位数
     * @return {@code this}
     */
    default C min(final Property<T, ?> property, final String alias, final Integer scale) {
        return this.min(this.convert(property), alias, scale);
    }

    /**
     * min聚合函数
     *
     * @param property 属性名
     * @return {@code this}
     */
    default C min(final String property) {
        return this.min(property, (Integer) null);
    }

    /**
     * min聚合函数
     *
     * @param property 属性名
     * @param scale    保留小数位数
     * @return {@code this}
     */
    default C min(final String property, final Integer scale) {
        return this.min(property, null, scale);
    }

    /**
     * min聚合函数
     *
     * @param property 属性名
     * @param alias    别名
     * @return {@code this}
     */
    default C min(final String property, final String alias) {
        return this.min(property, alias, null);
    }

    /**
     * min聚合函数
     *
     * @param property 属性名
     * @param alias    别名
     * @param scale    保留小数位数
     * @return {@code this}
     */
    C min(final String property, final String alias, final Integer scale);

    // endregion

    // region Max function methods

    /**
     * max聚合函数
     *
     * @param property {@link Property}
     * @return {@code this}
     */
    default C max(final Property<T, ?> property) {
        return this.max(property, null, null);
    }

    /**
     * max聚合函数
     *
     * @param property {@link Property}
     * @param scale    保留小数位数
     * @return {@code this}
     */
    default C max(final Property<T, ?> property, final Integer scale) {
        return this.max(property, null, scale);
    }

    /**
     * max聚合函数
     *
     * @param property {@link Property}
     * @param alias    别名
     * @return {@code this}
     */
    default C max(final Property<T, ?> property, final String alias) {
        return this.max(property, alias, null);
    }

    /**
     * max聚合函数
     *
     * @param property {@link Property}
     * @param alias    别名
     * @param scale    保留小数位数
     * @return {@code this}
     */
    default C max(final Property<T, ?> property, final String alias, final Integer scale) {
        return this.max(this.convert(property), alias, scale);
    }

    /**
     * max聚合函数
     *
     * @param property 属性名
     * @return {@code this}
     */
    default C max(final String property) {
        return this.max(property, (Integer) null);
    }

    /**
     * max聚合函数
     *
     * @param property 属性名
     * @param scale    保留小数位数
     * @return {@code this}
     */
    default C max(final String property, final Integer scale) {
        return this.max(property, null, scale);
    }

    /**
     * max聚合函数
     *
     * @param property 属性名
     * @param alias    别名
     * @return {@code this}
     */
    default C max(final String property, final String alias) {
        return this.max(property, alias, null);
    }

    /**
     * max聚合函数
     *
     * @param property 属性名
     * @param alias    别名
     * @param scale    保留小数位数
     * @return {@code this}
     */
    C max(final String property, final String alias, final Integer scale);

    // endregion
}
