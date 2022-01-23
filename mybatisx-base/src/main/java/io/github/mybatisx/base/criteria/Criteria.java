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
package io.github.mybatisx.base.criteria;

import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.base.criterion.Criterion;
import io.github.mybatisx.base.dialect.Dialect;
import io.github.mybatisx.base.fragment.Fragment;

/**
 * 条件接口
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2021/12/23
 * @since 1.0.0
 */
public interface Criteria<T> extends Fragment {

    /**
     * 获取实体类
     *
     * @return 实体类
     */
    Class<T> getEntity();

    /**
     * 获取表别名
     *
     * @return 表别名
     */
    String as();
    
    /**
     * 获取引用属性
     *
     * @return 引用属性
     */
    default String getReference() {
        return null;
    }

    /**
     * 设置是否使用严格模式(默认为严格模式)
     * <p>
     * 校验属性是否一致，不一致则抛出异常
     *
     * @param strict 是否使用严格模式
     * @return {@code this}
     */
    Criteria<T> strict(final boolean strict);

    /**
     * 获取是否使用严格模式
     *
     * @return boolean
     */
    boolean isStrict();

    /**
     * 设置方言
     *
     * @param dialect {@link Dialect}
     * @return {@code this}
     */
    Criteria<T> dialect(final Dialect dialect);

    /**
     * 获取{@link Dialect}对象
     *
     * @return {@link Dialect}
     */
    Dialect getDialect();

    /**
     * 获取乐观锁条件值
     *
     * @return 乐观锁值
     */
    Object getVersionValue();

    /**
     * 设置乐观锁值
     *
     * @param value 乐观锁值
     * @return {@code this}
     */
    Criteria<T> setVersion(final Object value);

    /**
     * 逻辑符号
     *
     * @return {@link LogicSymbol}
     */
    LogicSymbol slot();

    /**
     * 获取条件片段
     *
     * @return 条件片段
     */
    String getWhereString();

    /**
     * 添加条件
     *
     * @param criterion {@link Criterion}
     * @return {@code this}
     */
    Criteria<T> where(final Criterion criterion);

    /**
     * 获取完整SQL语句
     *
     * @return 完整SQL语句
     */
    default String completeString() {
        return null;
    }

    /**
     * 是否存在片段(where/group/having/order/)
     *
     * @return boolean
     */
    default boolean isHasFragment() {
        return false;
    }

}
