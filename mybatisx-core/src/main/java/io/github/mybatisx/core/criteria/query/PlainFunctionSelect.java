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

/**
 * 聚合函数查询接口
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/28
 * @since 1.0.0
 */
public interface PlainFunctionSelect<T, C extends PlainFunctionSelect<T, C>> {

    // region Count function methods

    /**
     * count聚合函数
     *
     * @param column 字段名
     * @return {@code this}
     */
    default C colCount(final String column) {
        return this.colCount(column, false);
    }

    /**
     * count聚合函数
     *
     * @param column 字段名
     * @param alias  别名
     * @return {@code this}
     */
    default C colCount(final String column, final String alias) {
        return this.colCount(column, alias, false);
    }

    /**
     * count聚合函数
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @return {@code this}
     */
    default C colCount(final String column, final boolean distinct) {
        return this.colCount(column, null, distinct);
    }

    /**
     * count聚合函数
     *
     * @param column   字段名
     * @param alias    别名
     * @param distinct 是否去重
     * @return {@code this}
     */
    C colCount(final String column, final String alias, final boolean distinct);

    // endregion

    // region Sum function methods

    /**
     * sum聚合函数
     *
     * @param column 字段名
     * @return {@code this}
     */
    default C colSum(final String column) {
        return this.colSum(column, null, null, false);
    }

    /**
     * sum聚合函数
     *
     * @param column 字段名
     * @param scale  保留小数位数
     * @return {@code this}
     */
    default C colSum(final String column, final Integer scale) {
        return this.colSum(column, null, scale, false);
    }

    /**
     * sum聚合函数
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @return {@code this}
     */
    default C colSum(final String column, final boolean distinct) {
        return this.colSum(column, null, null, distinct);
    }

    /**
     * sum聚合函数
     *
     * @param column   字段名
     * @param scale    保留小数位数
     * @param distinct 是否去重
     * @return {@code this}
     */
    default C colSum(final String column, final Integer scale, final boolean distinct) {
        return this.colSum(column, null, scale, distinct);
    }

    /**
     * sum聚合函数
     *
     * @param column 字段名
     * @param alias  别名
     * @return {@code this}
     */
    default C colSum(final String column, final String alias) {
        return this.colSum(column, alias, false);
    }

    /**
     * sum聚合函数
     *
     * @param column 字段名
     * @param alias  别名
     * @param scale  保留小数位数
     * @return {@code this}
     */
    default C colSum(final String column, final String alias, final Integer scale) {
        return this.colSum(column, alias, scale, false);
    }

    /**
     * sum聚合函数
     *
     * @param column   字段名
     * @param alias    别名
     * @param distinct 是否去重
     * @return {@code this}
     */
    default C colSum(final String column, final String alias, final boolean distinct) {
        return this.colSum(column, alias, null, distinct);
    }

    /**
     * sum聚合函数
     *
     * @param column   字段名
     * @param alias    别名
     * @param scale    保留小数位数
     * @param distinct 是否去重
     * @return {@code this}
     */
    C colSum(final String column, final String alias, final Integer scale, final boolean distinct);

    // endregion

    // region Avg function methods

    /**
     * avg聚合函数
     *
     * @param column 字段名
     * @return {@code this}
     */
    default C colAvg(final String column) {
        return this.colAvg(column, null, null, false);
    }

    /**
     * avg聚合函数
     *
     * @param column 字段名
     * @param scale  保留小数位数
     * @return {@code this}
     */
    default C colAvg(final String column, final Integer scale) {
        return this.colAvg(column, null, scale, false);
    }

    /**
     * avg聚合函数
     *
     * @param column   字段名
     * @param distinct 是否去重
     * @return {@code this}
     */
    default C colAvg(final String column, final boolean distinct) {
        return this.colAvg(column, null, null, distinct);
    }

    /**
     * avg聚合函数
     *
     * @param column   字段名
     * @param scale    保留小数位数
     * @param distinct 是否去重
     * @return {@code this}
     */
    default C colAvg(final String column, final Integer scale, final boolean distinct) {
        return this.colAvg(column, null, scale, distinct);
    }

    /**
     * avg聚合函数
     *
     * @param column 字段名
     * @param alias  别名
     * @return {@code this}
     */
    default C colAvg(final String column, final String alias) {
        return this.colAvg(column, alias, false);
    }

    /**
     * avg聚合函数
     *
     * @param column 字段名
     * @param alias  别名
     * @param scale  保留小数位数
     * @return {@code this}
     */
    default C colAvg(final String column, final String alias, final Integer scale) {
        return this.colAvg(column, alias, scale, false);
    }

    /**
     * avg聚合函数
     *
     * @param column   字段名
     * @param alias    别名
     * @param distinct 是否去重
     * @return {@code this}
     */
    default C colAvg(final String column, final String alias, final boolean distinct) {
        return this.colAvg(column, alias, null, distinct);
    }

    /**
     * avg聚合函数
     *
     * @param column   字段名
     * @param alias    别名
     * @param scale    保留小数位数
     * @param distinct 是否去重
     * @return {@code this}
     */
    C colAvg(final String column, final String alias, final Integer scale, final boolean distinct);

    // endregion

    // region Min function methods

    /**
     * min聚合函数
     *
     * @param column 字段名
     * @return {@code this}
     */
    default C colMin(final String column) {
        return this.colMin(column, null, null);
    }

    /**
     * min聚合函数
     *
     * @param column 字段名
     * @param alias  别名
     * @return {@code this}
     */
    default C colMin(final String column, final String alias) {
        return this.colMin(column, alias, null);
    }

    /**
     * min聚合函数
     *
     * @param column 字段名
     * @param scale  保留小数位数
     * @return {@code this}
     */
    default C colMin(final String column, final Integer scale) {
        return this.colMin(column, null, scale);
    }

    /**
     * min聚合函数
     *
     * @param column 字段名
     * @param alias  别名
     * @param scale  保留小数位数
     * @return {@code this}
     */
    C colMin(final String column, final String alias, final Integer scale);

    // endregion

    // region Max function methods

    /**
     * max聚合函数
     *
     * @param column 字段名
     * @return {@code this}
     */
    default C colMax(final String column) {
        return this.colMax(column, null, null);
    }

    /**
     * max聚合函数
     *
     * @param column 字段名
     * @param alias  别名
     * @return {@code this}
     */
    default C colMax(final String column, final String alias) {
        return this.colMax(column, alias, null);
    }

    /**
     * max聚合函数
     *
     * @param column 字段名
     * @param scale  保留小数位数
     * @return {@code this}
     */
    default C colMax(final String column, final Integer scale) {
        return this.colMax(column, null, scale);
    }

    /**
     * max聚合函数
     *
     * @param column 字段名
     * @param alias  别名
     * @param scale  保留小数位数
     * @return {@code this}
     */
    C colMax(final String column, final String alias, final Integer scale);

    // endregion

}
