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

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.criteria.Criteria;
import io.github.mybatisx.base.helper.SqlHelper;
import io.github.mybatisx.core.fragment.FragmentManager;
import io.github.mybatisx.lang.Strings;
import lombok.RequiredArgsConstructor;

/**
 * 抽象SQL管理器
 *
 * @author wvkity
 * @created 2022/1/7
 * @since 1.0.0
 */
@RequiredArgsConstructor
public abstract class AbstractSqlManager implements SqlManager {

    /**
     * 片段管理器
     */
    protected final FragmentManager fragmentManager;

    @Override
    public String getWhereString(boolean self, boolean appendWhere, String groupReplacement) {
        final String condition = this.fragmentManager.getCompleteString(groupReplacement);
        if (Strings.isNotWhitespace(condition)) {
            if (this.fragmentManager.hasCondition()) {
                final String crc = SqlHelper.startWithsAndOrRemove(condition);
                return (appendWhere ? SqlSymbol.WHERE_SPACE : SqlSymbol.EMPTY) + crc;
            }
            return condition;
        }
        return Constants.EMPTY;
    }

    /**
     * 获取{@link Criteria}
     *
     * @return {@link Criteria}
     */
    protected abstract Criteria<?> getCriteria();
}
