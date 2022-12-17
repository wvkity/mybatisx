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
import io.github.mybatisx.batch.extend.service.BatchService;
import io.github.mybatisx.core.mapper.CurdMapper;

import java.io.Serializable;
import java.util.function.Function;

/**
 * MyBatisX通用Service接口
 *
 * @param <M>  Mapper接口
 * @param <T>  实体类型
 * @param <R>  返回值类型
 * @param <ID> 主键类型
 * @author wvkity
 * @created 2021/12/24
 * @since 1.0.0
 */
public interface CurdService<M extends CurdMapper<T, R, ID>, T, R, ID extends Serializable> extends
        SaveService<T>, UpdateService<T>, DeleteService<T, ID>, QueryService<T, R, ID>, BatchService<M, T> {

    /**
     * 消费
     *
     * @param function {@link Function}
     * @param <E>      结果类型
     * @return 处理后的结果
     */
    <E> E chain(Function<CurdService<M, T, R, ID>, E> function);

    /**
     * 获取mapper类型
     *
     * @return mapper类型
     */
    Class<M> getMapperType();

    /**
     * 获取实体类型
     *
     * @return 实体类型
     */
    Class<T> getEntityType();

    /**
     * 获取返回值类型
     *
     * @return 返回值类型
     */
    Class<R> getReturnType();

    /**
     * 获取Mapper接口对象
     *
     * @return Mapper接口对象
     */
    M getMapper();

    /**
     * 设置Mapper接口对象
     *
     * @param mapper Mapper接口对象
     */
    void setMapper(final M mapper);

    /**
     * 创建{@link Criteria}对象
     *
     * @param action {@link Function}
     * @param <U>    {@link Criteria}类型
     * @return {@link Criteria}
     */
    <U extends Criteria<T>> U create(final Function<Class<T>, U> action);
}
