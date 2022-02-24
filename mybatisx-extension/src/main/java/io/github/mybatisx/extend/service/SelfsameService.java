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

import io.github.mybatisx.base.mapper.SelfsameMapper;

import java.io.Serializable;

/**
 * MyBatisX通用Service接口
 * <p>
 * 实体/返回值一致
 *
 * @param <M>  Mapper接口类型
 * @param <T>  实体类型
 * @param <ID> 主键类型
 * @author wvkity
 * @created 2021/12/24
 * @since 1.0.0
 */
public interface SelfsameService<M extends SelfsameMapper<T, ID>, T, ID extends Serializable> extends BaseService<M, T, T, ID> {

}
