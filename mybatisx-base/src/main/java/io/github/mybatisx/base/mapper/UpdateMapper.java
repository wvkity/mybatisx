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
package io.github.mybatisx.base.mapper;

import io.github.mybatisx.base.constant.Constants;
import org.apache.ibatis.annotations.Param;

/**
 * MyBatisX更新操作接口
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2021/12/24
 * @since 1.0.0
 */
interface UpdateMapper<T> {

    /**
     * 根据主键、乐观锁、逻辑删除标识、多租户标识更新记录
     * <p>
     * 排除逻辑删除标识、保存审计标识、多租户标识等字段
     *
     * @param entity 待更新记录对象
     * @return 受影响行数
     */
    int update(@Param(Constants.PARAM_ENTITY) final T entity);

    /**
     * 根据主键、乐观锁、逻辑删除标识、多租户标识更新记录
     * <p>
     * 排除逻辑删除标识、保存审计标识、多租户标识、空值等字段
     *
     * @param entity 待更新记录对象
     * @return 受影响行数
     */
    int updateWithoutNull(@Param(Constants.PARAM_ENTITY) final T entity);

    /**
     * 根据主键、多租户标识、逻辑删除标识更新记录
     *
     * @param entity 待更新记录对象
     * @return 受影响行数
     */
    int updateWithSpecial(@Param(Constants.PARAM_ENTITY) final T entity);

    /**
     * 根据主键、多租户标识、逻辑删除标识更新记录
     * <p>
     * 不包含null值字段
     *
     * @param entity 待更新记录对象
     * @return 受影响行数
     */
    int updateWithSpecialExcNull(@Param(Constants.PARAM_ENTITY) final T entity);

}
