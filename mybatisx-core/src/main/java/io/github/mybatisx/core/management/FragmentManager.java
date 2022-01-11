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
package io.github.mybatisx.core.management;

import io.github.mybatisx.base.criterion.Criterion;
import io.github.mybatisx.base.fragment.Fragment;

import java.util.Collection;
import java.util.List;

/**
 * 片段管理器
 *
 * @author wvkity
 * @created 2022/1/7
 * @since 1.0.0
 */
public interface FragmentManager extends Fragment {

    /**
     * 添加条件
     *
     * @param condition {@link Criterion}
     */
    void addCondition(final Criterion condition);

    /**
     * 添加多个条件
     *
     * @param conditions 条件列表
     */
    void addCondition(final Collection<Criterion> conditions);

    /**
     * 检查是否存在条件
     *
     * @return boolean
     */
    boolean hasCondition();

    /**
     * 获取条件列表
     *
     * @return 条件列表
     */
    List<Criterion> getConditions();

    /**
     * 检查是否存在片段
     *
     * @return boolean
     */
    default boolean hasFragment() {
        return this.hasCondition();
    }

    /**
     * 获取条件SQL语句
     *
     * @return 条件语句
     */
    String getWhereString();

    /**
     * 获取完整SQL语句
     *
     * @param groupReplacement 分组替换语句
     * @return 完整SQL语句
     */
    String getCompleteString(final String groupReplacement);
}
