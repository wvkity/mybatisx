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

import io.github.mybatisx.batch.extend.service.BatchSaveService;

/**
 * MyBatisX保存操作Service接口
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2021/12/24
 * @since 1.0.0
 */
interface SaveService<T> extends BatchSaveService<T> {

    /**
     * 保存
     *
     * @param entity 待保存对象
     * @return 受影响行数
     */
    int save(final T entity);

    /**
     * 保存
     * <p>排除Null值
     *
     * @param entity 待保存对象
     * @return 受影响行数
     */
    int saveWithoutNull(final T entity);

}
