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

import io.github.mybatisx.base.mapper.BaseMapper;
import io.github.mybatisx.extend.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

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
    public M getMapper() {
        return this.mapper;
    }

    @Override
    @Autowired(required = false)
    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

}
