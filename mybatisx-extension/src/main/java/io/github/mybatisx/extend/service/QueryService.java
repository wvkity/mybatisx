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
package io.github.mybatisx.extend.service;

import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.core.criteria.query.Query;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * MyBatisX查询操作Service接口
 *
 * @param <T>  实体类型
 * @param <R>  返回值类型
 * @param <ID> 主键类型
 * @author wvkity
 * @created 2021/12/24
 * @since 1.0.0
 */
interface QueryService<T, R, ID extends Serializable> {

    /**
     * 根据主键检查记录是否存在
     *
     * @param id 主键值
     * @return boolean
     */
    boolean existsById(final ID id);

    /**
     * 根据实体对象检查记录是否存在
     *
     * @param entity 实体对象
     * @return boolean
     */
    boolean exists(final T entity);

    /**
     * 根据{@link Query}对象检查记录是否存在
     *
     * @param query {@link Query}对象
     * @return boolean
     */
    boolean exists(final Query<T> query);

    /**
     * 查询总记录数
     *
     * @return 总记录数
     */
    long selectTotal();

    /**
     * 根据实体对象查询记录数
     *
     * @param entity 实体对象
     * @return 记录数
     */
    long selectCount(final T entity);

    /**
     * 根据{@link Query}对象查询记录数
     *
     * @param query {@link Query}对象
     * @return 记录数
     */
    long selectCount(final Query<T> query);

    /**
     * 查询所有记录
     *
     * @return 记录列表
     */
    List<R> selectAll();

    /**
     * 根据主键查询唯一记录
     *
     * @param id 主键值
     * @return {@link Optional}
     */
    Optional<R> selectOneById(final ID id);

    /**
     * 根据实体对象查询唯一记录
     *
     * @param entity 实体对象
     * @return {@link Optional}
     */
    Optional<R> selectOneByEntity(final T entity);

    /**
     * 根据实体对象查询记录
     *
     * @param entity 实体对象
     * @param <K>    键类型
     * @return {@link Map}数据
     */
    <K> Map<K, R> selectMap(final T entity);

    /**
     * 根据{@link Criteria}对象查询记录
     *
     * @param query {@link Criteria}对象
     * @param <K>   键类型
     * @return {@link Map}数据
     */
    <K> Map<K, R> selectMap(final Query<T> query);

    /**
     * 根据{@link Query}对象查询记录
     *
     * @param query {@link Query}对象
     * @param <K>   键类型
     * @param <V>   值类型
     * @return {@link Map}数据
     */
    default <K, V> Map<K, V> selectMap(final Query<T> query, final Class<V> returnType) {
        query.returnType(returnType);
        return this.selectCustomMap(query);
    }

    /**
     * 根据{@link Query}对象查询记录
     *
     * @param query {@link Query}对象
     * @param <K>   键类型
     * @param <V>   值类型
     * @return {@link Map}数据
     */
    <K, V> Map<K, V> selectCustomMap(final Query<T> query);

    /**
     * 根据主键列表查询记录
     *
     * @param ids 主键值列表
     * @return 多条记录
     */
    List<R> selectList(final Collection<ID> ids);

    /**
     * 根据实体对象查询记录
     *
     * @param entity 实体对象
     * @return 多条记录
     */
    List<R> selectList(final T entity);

    /**
     * 根据{@link Query}对象查询记录
     *
     * @param query {@link Query}对象
     * @return 多条记录
     */
    List<R> selectList(final Query<T> query);

    /**
     * 根据{@link Query}对象查询记录
     *
     * @param query {@link Query}对象
     * @return 多条记录
     */
    List<Object> selectObjects(final Query<T> query);

    /**
     * 根据{@link Query}对象查询记录
     *
     * @param query {@link Query}对象
     * @return 多条记录
     */
    List<Object[]> selectArrays(final Query<T> query);


}
