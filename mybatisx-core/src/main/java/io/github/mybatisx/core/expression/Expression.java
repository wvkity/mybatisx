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
package io.github.mybatisx.core.expression;

import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.core.param.Param;

/**
 * 条件表达式
 *
 * @author wvkity
 * @created 2021/12/23
 * @since 1.0.0
 */
public interface Expression {

    /**
     * 获取{@link Criteria}
     *
     * @return {@link Criteria}
     */
    Criteria<?> getCriteria();

    /**
     * 获取{@link Param}
     *
     * @return {@link Param}
     */
    Param getParam();

    /**
     * 获取字段名
     *
     * @return 字段名
     */
    String getColumn();

    /**
     * 获取表别名
     *
     * @return 表别名
     */
    String getAlias();

    /**
     * 设置{@link Criteria}
     *
     * @param criteria {@link Criteria}
     */
    void ifCriteriaNull(final Criteria<?> criteria);

    /**
     * 设置{@link LogicSymbol}
     *
     * @param slot {@link LogicSymbol}
     */
    default void ifSlotNull(final LogicSymbol slot) {
        final Param param = this.getParam();
        if (param != null && param.getSlot() == null) {
            param.setSlot(slot);
        }
    }
}
