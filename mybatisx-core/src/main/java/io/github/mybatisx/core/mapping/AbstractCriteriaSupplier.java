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
package io.github.mybatisx.core.mapping;

import io.github.mybatisx.base.config.MyBatisGlobalConfig;
import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.metadata.Table;

/**
 * 抽象{@link io.github.mybatisx.base.criteria.Criteria Criteria}相关SQL供应器
 *
 * @author wvkity
 * @created 2022/2/25
 * @since 1.0.0
 */
public abstract class AbstractCriteriaSupplier extends AbstractSupplier {

    protected final String hasConditionCheck =
            Constants.PARAM_CRITERIA + " != null and " + Constants.PARAM_CRITERIA + ".hasCondition";
    protected final String hasFragmentCheck =
            Constants.PARAM_CRITERIA + " != null and " + Constants.PARAM_CRITERIA + ".hasFragment";
    protected final String whereFragment = DOLLAR_START_BRACE + Constants.PARAM_CRITERIA + DOT + "whereFragment" + END_BRACE;

    public AbstractCriteriaSupplier(MyBatisGlobalConfig mgc, Table table) {
        super(mgc, table);
    }

    /**
     * {@link io.github.mybatisx.base.criteria.Criteria Criteria}条件查询语句
     *
     * @return 完整查询SQL语句
     */
    protected String criteriaSelect() {
        return DOLLAR_START_BRACE + Constants.PARAM_CRITERIA + ".fragment" + END_BRACE;
    }

    /**
     * {@link io.github.mybatisx.base.criteria.Criteria Criteria}条件查询语句
     *
     * @param whereFragment 条件片段
     * @return 完整查询SQL语句
     */
    protected String criteriaSelect(final String whereFragment) {
        return this.criteriaSelect(DOLLAR_START_BRACE + Constants.PARAM_CRITERIA + ".selectFragment" + END_BRACE,
                whereFragment);
    }

    /**
     * {@link io.github.mybatisx.base.criteria.Criteria Criteria}条件查询语句
     *
     * @param selectFragment 查询字段片段
     * @param whereFragment  条件片段
     * @return 完整查询SQL语句
     */
    protected String criteriaSelect(final String selectFragment, final String whereFragment) {
        return SELECT + START_CDATA + selectFragment + END_CDATA + SPACE + FROM + DOLLAR_START_BRACE + Constants.PARAM_CRITERIA + ".tableName" + END_BRACE + SPACE + whereFragment;
    }

    /**
     * 获取查询条件片段
     *
     * @return 条件片段
     */
    protected String getSelectConditionFragment() {
        return Scripts.toIfTag(this.whereFragment, this.hasFragmentCheck, true);
    }

    /**
     * 获取更新/删除条件片段
     *
     * @return 条件片段
     */
    protected String getUpdateConditionFragment() {
        return Scripts.toIfTag(this.whereFragment, this.hasConditionCheck, true);
    }

}
