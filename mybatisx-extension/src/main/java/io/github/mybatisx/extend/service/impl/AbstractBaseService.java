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
package io.github.mybatisx.extend.service.impl;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.core.criteria.query.Query;
import io.github.mybatisx.core.mapper.BaseMapper;
import io.github.mybatisx.extend.service.BaseService;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.reflect.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * MyBatisX抽象通用Service接口
 *
 * @param <M>  Mapper接口
 * @param <T>  实体类型
 * @param <R>  返回值类型
 * @param <ID> 主键类型
 * @author wvkity
 * @created 2021/12/24
 * @since 1.0.0
 */
public abstract class AbstractBaseService<M extends BaseMapper<T, R, ID>, T, R, ID extends Serializable> implements
        BaseService<M, T, R, ID> {


    private final Class<?>[] genericClasses = this.genericClasses();
    /**
     * mapper类型
     */
    protected final Class<M> currentMapperType = this.getGenericType(0);
    /**
     * 实体类型
     */
    protected final Class<T> currentEntityType = this.getGenericType(1);
    /**
     * 返回值类型
     */
    protected final Class<R> currentReturnType = this.getGenericType(2);

    /**
     * 获取当前实体类型
     *
     * @return 实体类型
     */
    @SuppressWarnings({"unchecked"})
    protected <E> Class<E> getGenericType(final int index) {
        if (index < 0 || index >= this.genericClasses.length) {
            return null;
        }
        return Objects.isEmpty(this.genericClasses) ? null : (Class<E>) this.genericClasses[index];
    }

    /**
     * 获取泛型列表
     *
     * @return 泛型列表
     */
    protected Class<?>[] genericClasses() {
        return GenericTypeResolver.resolveTypeArguments(Reflections.getRealClass(this.getClass()), BaseService.class);
    }
    
    /**
     * 通用Mapper接口
     */
    protected M mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(T entity) {
        return this.mapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveWithoutNull(T entity) {
        return this.mapper.insertWithoutNull(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(T entity) {
        return this.mapper.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateWithoutNull(T entity) {
        return this.mapper.updateWithoutNull(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateWithSpecial(T entity) {
        return this.mapper.updateWithSpecial(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateWithSpecialExcNull(T entity) {
        return this.mapper.updateWithSpecialExcNull(entity);
    }

    @Override
    public boolean existsById(ID id) {
        return this.mapper.existsById(id) == Constants.EXISTS;
    }

    @Override
    public boolean exists(T entity) {
        return this.mapper.existsByEntity(entity);
    }

    @Override
    public boolean exists(Query<T> query) {
        return this.mapper.existsByCriteria(query);
    }

    @Override
    public long selectTotal() {
        return this.mapper.selectTotal();
    }

    @Override
    public long selectCount(T entity) {
        return this.mapper.selectCountByEntity(entity);
    }

    @Override
    public long selectCount(Query<T> query) {
        return this.mapper.selectCountByCriteria(query);
    }

    @Override
    public List<R> selectAll() {
        return this.mapper.selectAll();
    }

    @Override
    public Optional<R> selectOneById(ID id) {
        return this.mapper.selectOneById(id);
    }

    @Override
    public Optional<R> selectOneByEntity(T entity) {
        return this.mapper.selectOneByEntity(entity);
    }

    @Override
    public <K> Map<K, R> selectMap(T entity) {
        return this.mapper.selectMapByEntity(entity);
    }

    @Override
    public <K> Map<K, R> selectMap(Query<T> query) {
        return this.mapper.selectMapByCriteria(query);
    }

    @Override
    public <K, V> Map<K, V> selectCustomMap(Query<T> query) {
        return this.mapper.selectCustomMap(query);
    }

    @Override
    public List<R> selectList(Collection<ID> ids) {
        return this.mapper.selectListByIds(ids);
    }

    @Override
    public List<R> selectList(T entity) {
        return this.mapper.selectListByEntity(entity);
    }

    @Override
    public List<R> selectList(Query<T> query) {
        return this.mapper.selectListByCriteria(query);
    }

    @Override
    public <E> List<E> selectCustomList(Query<T> query) {
        return this.mapper.selectCustomList(query);
    }

    @Override
    public List<Object> selectObjects(Query<T> query) {
        return this.mapper.selectObjects(query);
    }

    @Override
    public List<Object[]> selectArrays(Query<T> query) {
        return this.mapper.selectArrays(query);
    }

    @Override
    public Class<M> getMapperType() {
        return this.currentMapperType;
    }

    @Override
    public Class<T> getEntityType() {
        return this.currentEntityType;
    }

    @Override
    public Class<R> getReturnType() {
        return this.currentReturnType;
    }

    @Override
    public M getMapper() {
        return this.mapper;
    }

    @Override
    @Autowired(required = false)
    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

}
