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
import io.github.mybatisx.base.exception.MyBatisException;
import io.github.mybatisx.base.helper.SqlHelper;
import io.github.mybatisx.core.criteria.AbstractBaseCriteria;
import io.github.mybatisx.core.criteria.query.Joinable;
import io.github.mybatisx.core.criteria.query.Query;
import io.github.mybatisx.core.fragment.FragmentManager;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.util.Collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 查询类型SQL管理器
 *
 * @author wvkity
 * @created 2022/1/20
 * @since 1.0.0
 */
public class QuerySqlManager extends AbstractSqlManager {

    /**
     * {@link Query}
     */
    private final Query<?> rootQuery;
    /**
     * 嵌套/子查询
     */
    private final Query<?> outerQuery;
    /**
     * 联表查询对象
     */
    private final Set<Joinable<?>> associations;

    public QuerySqlManager(Query<?> criteria, Query<?> outerQuery, Set<Joinable<?>> associations,
                           FragmentManager fragmentManager) {
        super(fragmentManager);
        this.rootQuery = criteria;
        this.outerQuery = outerQuery;
        this.associations = associations;
    }

    @Override
    protected Criteria<?> getCriteria() {
        return this.rootQuery;
    }

    @Override
    public boolean hasSort() throws MyBatisException {
        return this.fragmentManager.hasSort();
    }

    @Override
    public String getSelectString() throws MyBatisException {
        return this.fragmentManager.getSelectString();
    }

    @Override
    public String getSelectFragment(boolean self) throws MyBatisException {
        final boolean hasOuter = this.outerQuery != null;
        final boolean hasSelect = this.rootQuery.hasSelect();
        final boolean loadAndReturn = !self || (hasOuter && !hasSelect);
        if (loadAndReturn) {
            this.rootQuery.fetchSelects();
            return this.getSelectString();
        }
        final String ss = this.getSelectString();
        final Set<Joinable<?>> _$associations = this.associations;
        if (Collections.isNotEmpty(_$associations)) {
            final List<String> list = new ArrayList<>();
            if (Strings.isNotWhitespace(ss)) {
                list.add(ss.trim());
            }
            for (Joinable<?> it : _$associations) {
                if (it != null && (it.hasSelect() || it.isFetch())) {
                    final String ass = it.getSelectFragment(false);
                    if (Strings.isNotWhitespace(ass)) {
                        list.add(ass.trim());
                    }
                }
            }
            if (list.size() == 1) {
                return list.get(0);
            }
            return String.join(SqlSymbol.COMMA_SPACE, list);
        }
        return ss;
    }

    @Override
    public String getGroupFragment() throws MyBatisException {
        final Set<Joinable<?>> _$associations = this.associations;
        if (Collections.isNotEmpty(_$associations)) {
            final List<String> it = new ArrayList<>(_$associations.size() + 1);
            final String _$ss = this.fragmentManager.getSelectString(false);
            int i = 0;
            if (Strings.isNotEmpty(_$ss)) {
                it.add(_$ss);
                i += 1;
            }
            for (Joinable<?> jq : _$associations) {
                if (jq.hasSelect() || jq.isFetch()) {
                    final String fss = jq.getGroupFragment();
                    if (Strings.isNotWhitespace(fss)) {
                        it.add(fss);
                        i += 1;
                    }
                }
            }
            return i == 1 ? it.get(0) : String.join(SqlSymbol.COMMA_SPACE, it);
        }
        return this.fragmentManager.getSelectString(false);
    }

    @Override
    public String getWhereString() {
        return this.getWhereString(true, true,
                this.rootQuery.isGroupAll() ? this.getGroupFragment() : null);
    }

    @Override
    public String getWhereString(boolean self, boolean appendWhere, String groupReplacement) {
        return this.mergeCondition(super.getWhereString(self, appendWhere, groupReplacement));
    }

    /**
     * 合并条件
     *
     * @param condition 条件
     * @return 条件
     */
    protected String mergeCondition(final String condition) {
        final Set<Joinable<?>> _$associations = this.associations;
        if (Collections.isNotEmpty(_$associations)) {
            final List<String> conditions = new ArrayList<>(_$associations.size() + 1);
            for (Joinable<?> it : _$associations) {
                if (it instanceof AbstractBaseCriteria) {
                    final AbstractBaseCriteria<?> genericCriteria = (AbstractBaseCriteria<?>) it;
                    final String fws = genericCriteria.getWhereString(false, false, null);
                    if (Strings.isNotWhitespace(fws)) {
                        final String onCondition = it.getJoin().getFragment() +
                                SqlSymbol.SPACE +
                                it.getTableName() +
                                SqlSymbol.SPACE +
                                SqlSymbol.ON +
                                SqlSymbol.SPACE +
                                SqlHelper.startWithsAndOrRemove(fws);
                        conditions.add(onCondition);
                    }
                }
            }
            if (!conditions.isEmpty()) {
                conditions.add(condition);
                return String.join(SqlSymbol.SPACE, conditions);
            }
        }
        return condition;
    }

    @Override
    public String getCompleteString() {
        final Query<?> _$root = this.rootQuery;
        final StringBuilder sb = new StringBuilder(256);
        sb.append(SqlSymbol.SELECT).append(SqlSymbol.SPACE);
        if (_$root.isDistinct()) {
            sb.append(SqlSymbol.DISTINCT).append(SqlSymbol.SPACE);
        }
        sb.append(this.getSelectFragment().trim())
                .append(SqlSymbol.SPACE)
                .append(SqlSymbol.FROM)
                .append(SqlSymbol.SPACE)
                .append(_$root.getTableName().trim());
        final String condition = this.getWhereString();
        if (Strings.isNotWhitespace(condition)) {
            sb.append(SqlSymbol.SPACE).append(condition.trim());
            if (_$root.isKeepOrderly() && this.fragmentManager.hasSort()) {
                sb.append(SqlSymbol.SPACE)
                        .append(Constants.KEEP_ORDER_BY);
            }
        }
        return sb.toString();
    }
}
