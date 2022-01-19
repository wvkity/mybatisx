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
package io.github.mybatisx.core.sql;

import io.github.mybatisx.base.exception.MyBatisException;

/**
 * SQL管理器
 *
 * @author wvkity
 * @created 2022/1/7
 * @since 1.0.0
 */
public interface SqlManager {

    /**
     * 获取完整SQL语句
     *
     * @return 完整SQL语句
     */
    default String getCompleteString() {
        return this.getWhereString();
    }

    /**
     * 获取条件SQL语句
     *
     * @return 条件语句
     */
    default String getWhereString() {
        return this.getWhereString(true, true, null);
    }

    /**
     * 获取条件SQL语句
     *
     * @param self             是否自身
     * @param appendWhere      是否拼接条件
     * @param groupReplacement 分组替换语句
     * @return 条件语句
     */
    String getWhereString(final boolean self, final boolean appendWhere, final String groupReplacement);

    /**
     * 获取查询列片段
     *
     * @return 查询列片段
     * @throws MyBatisException 查询类型实现，否则抛异常
     */
    default String getSelectFragment() throws MyBatisException {
        return this.getSelectFragment(true);
    }

    /**
     * 获取查询列片段
     *
     * @param self 是否为自身
     * @return 查询列片段
     * @throws MyBatisException 查询类型实现，否则抛异常
     */
    default String getSelectFragment(final boolean self) throws MyBatisException {
        throw new MyBatisException("This method must be implemented by a query type object to be called");
    }

    /**
     * 获取分组片段
     *
     * @return 分组片段
     * @throws MyBatisException 更新类型实现，否则抛异常
     */
    default String getGroupFragment() throws MyBatisException {
        throw new MyBatisException("This method must be implemented by a query type object to be called");
    }

    /**
     * 获取更新值片段
     *
     * @return 更新值片段
     * @throws MyBatisException 查询类型实现，否则抛异常
     */
    default String getUpdateFragment() throws MyBatisException {
        throw new MyBatisException("This method must be implemented by an update type object to be called");
    }

}
