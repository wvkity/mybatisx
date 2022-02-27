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
package io.github.mybatisx.core.mapper;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.embedded.Embeddable;
import io.github.mybatisx.embedded.ReturnsMap;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * MyBatisX查询操作接口
 *
 * @param <T>  实体类型
 * @param <R>  返回值类型
 * @param <ID> 主键类型
 * @author wvkity
 * @created 2021/12/24
 * @since 1.0.0
 */
interface QueryMapper<T, R, ID extends Serializable> {

    /**
     * 根据主键检查记录是否存在
     *
     * @param id 主键值
     * @return 1: 存在, 0: 不存在
     */
    int existsById(final @Param(Constants.PARAM_ID) ID id);

    /**
     * 根据实体对象检查记录是否存在
     *
     * @param entity 实体对象
     * @return boolean
     */
    default boolean existsByEntity(final @Param(Constants.PARAM_ENTITY) T entity) {
        return this.selectCountByEntity(entity) >= Constants.EXISTS;
    }

    /**
     * 根据{@link Criteria}对象检查记录是否存在
     *
     * @param criteria {@link Criteria}
     * @return 0: 不存在, 1: 存在
     */
    default boolean existsByCriteria(final @Param(Constants.PARAM_CRITERIA) Criteria<T> criteria) {
        return this.selectCountByCriteria(criteria) >= Constants.EXISTS;
    }

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
    long selectCountByEntity(final @Param(Constants.PARAM_ENTITY) T entity);

    /**
     * 根据{@link Criteria}对象查询记录数
     *
     * @param criteria {@link Criteria}对象
     * @return 记录数
     */
    long selectCountByCriteria(final @Param(Constants.PARAM_CRITERIA) Criteria<T> criteria);

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
    Optional<R> selectOneById(final @Param(Constants.PARAM_ID) ID id);

    /**
     * 根据实体对象查询唯一记录
     *
     * @param entity 实体对象
     * @return {@link Optional}
     */
    Optional<R> selectOneByEntity(final @Param(Constants.PARAM_ENTITY) T entity);

    /**
     * 根据实体对象查询记录
     *
     * @param entity 实体对象
     * @param <K>    键类型
     * @return {@link Map}数据
     */
    @ReturnsMap
    <K> Map<K, R> selectMapByEntity(final @Param(Constants.PARAM_ENTITY) T entity);

    /**
     * 根据{@link Criteria}对象查询记录
     *
     * @param criteria {@link Criteria}对象
     * @param <K>      键类型
     * @return {@link Map}数据
     */
    @ReturnsMap
    <K> Map<K, R> selectMapByCriteria(final @Param(Constants.PARAM_CRITERIA) Criteria<T> criteria);

    /**
     * 根据{@link Criteria}对象查询记录
     *
     * @param criteria {@link Criteria}对象
     * @param <K>      键类型
     * @param <V>      值类型
     * @return {@link Map}数据
     */
    @ReturnsMap
    @Embeddable
    <K, V> Map<K, V> selectCustomMap(final @Param(Constants.PARAM_CRITERIA) Criteria<T> criteria);

    /**
     * 根据主键列表查询记录
     *
     * @param ids 主键值列表
     * @return 多条记录
     */
    List<R> selectListByIds(final @Param(Constants.PARAM_ID_LIST) Collection<ID> ids);

    /**
     * 根据实体对象查询记录
     *
     * @param entity 实体对象
     * @return 多条记录
     */
    List<R> selectListByEntity(final @Param(Constants.PARAM_ENTITY) T entity);

    /**
     * 根据{@link Criteria}对象查询记录
     *
     * @param criteria {@link Criteria}对象
     * @return 多条记录
     */
    List<R> selectListByCriteria(final @Param(Constants.PARAM_CRITERIA) Criteria<T> criteria);

    /**
     * 根据{@link Criteria}对象查询记录
     *
     * @param criteria {@link Criteria}对象
     * @return 多条记录
     */
    @Embeddable
    <E> List<E> selectCustomList(final @Param(Constants.PARAM_CRITERIA) Criteria<T> criteria);

    /**
     * 根据{@link Criteria}对象查询记录
     *
     * @param criteria {@link Criteria}对象
     * @return 多条记录
     */
    List<Object> selectObjects(final @Param(Constants.PARAM_CRITERIA) Criteria<T> criteria);

    /**
     * 根据{@link Criteria}对象查询记录
     *
     * @param criteria {@link Criteria}对象
     * @return 多条记录
     */
    List<Object[]> selectArrays(final @Param(Constants.PARAM_CRITERIA) Criteria<T> criteria);

}
