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
package io.github.mybatisx.sql;

import com.google.common.collect.ImmutableSet;
import io.github.mybatisx.lang.Objects;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.WithItem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * order by删除器
 *
 * @author wvkity
 * @created 2022/2/20
 * @since 1.0.0
 */
public class OrderByDeleter {

    /**
     * {@link Select}
     */
    @Getter
    private final Select select;
    /**
     * 原SQL语句
     */
    @Getter
    private final String originalSql;
    /**
     * 是否存在order by语句
     */
    @Getter
    @Accessors(fluent = true)
    private boolean hasOrderBy;
    /**
     * order by出现次数
     */
    @Getter
    private int orderBySize = 0;
    /**
     * 是否存在keep orderly注释
     */
    @Getter
    @Accessors(fluent = true)
    private boolean hasKeepOrderly;
    /**
     * keep orderly次数
     */
    @Getter
    private int keepOrderlySize = 0;
    /**
     * 保持order by字段列表
     */
    private final Set<String> keepOrderlyElements = new HashSet<>();
    /**
     * order by正则表达式
     */
    private final Pattern orderByRegex = Pattern.compile("(?i)(((?<!/\\*)(\\s+order\\s+by))\\s*)",
            Pattern.CASE_INSENSITIVE);
    /**
     * order by匹配器
     */
    private final Pattern orderByMatcher = Pattern.compile("(?i)(.*(\\s+order\\s+by\\s+).*)", Pattern.CASE_INSENSITIVE);
    /**
     * keep orderly正则表达式
     */
    private final Pattern keepOrderlyRegex =
            Pattern.compile("(?i)((?<!/\\*)(\\s+((order\\s+by\\s+)(((?<!/\\*\\s{0,20}keep orderly).)*)\\s*(/\\*keep\\s+orderly\\*/)\\s*)))", Pattern.CASE_INSENSITIVE);
    /**
     * keep orderly匹配器
     */
    private final Pattern keepOrderlyMatcher =
            Pattern.compile("(?i)(.*((\\s+order\\s+by\\s+.*)\\s+/\\*keep\\s+orderly\\*/).*?)$", Pattern.CASE_INSENSITIVE);
    /**
     * 逗号+空格
     */
    private final String commaSpace = ",";

    public OrderByDeleter(Select select, String originalSql) {
        this.select = select;
        this.originalSql = originalSql;
        this.parse();
    }

    /**
     * 解析SQL
     */
    protected void parse() {
        final String _$sql = this.originalSql;
        this.hasOrderBy = this.orderByMatcher.matcher(_$sql).matches();
        if (this.hasOrderBy) {
            final Matcher matcher = this.orderByRegex.matcher(_$sql);
            int orderByCount = 0;
            while (matcher.find()) {
                orderByCount++;
            }
            this.orderBySize = orderByCount;
            this.hasKeepOrderly = this.keepOrderlyMatcher.matcher(_$sql).matches();
            if (this.hasKeepOrderly) {
                final Matcher keepMatcher = this.keepOrderlyRegex.matcher(_$sql);
                final int groupIndex = 5;
                int keepCount = 0;
                while (keepMatcher.find()) {
                    this.cacheKeepOrderlyElements(keepMatcher.group(groupIndex));
                    keepCount++;
                }
                this.keepOrderlySize = keepCount;
            }
        }
    }

    /**
     * 缓存保持排序字段
     *
     * @param orderBy 排序子句
     */
    protected void cacheKeepOrderlyElements(final String orderBy) {
        this.keepOrderlyElements.add(Arrays.stream(orderBy.split(",")).map(String::trim)
                .collect(Collectors.joining(this.commaSpace)));
    }

    /**
     * 尝试删除排序子句
     */
    public void tryRemove() {
        if (this.hasOrderBy && (!this.hasKeepOrderly || this.orderBySize != this.keepOrderlySize)) {
            final SelectBody selectBody = this.select.getSelectBody();
            this.tryRemoveFromSelectBody(selectBody);
        }
    }

    /**
     * 尝试去除{@link SelectBody}中的排序子句
     *
     * @param selectBody {@link SelectBody}
     */
    protected void tryRemoveFromSelectBody(final SelectBody selectBody) {
        if (selectBody instanceof PlainSelect) {
            this.tryRemoveFromPlainSelect((PlainSelect) selectBody);
        } else if (selectBody instanceof WithItem) {
            Optional.ofNullable(((WithItem) selectBody).getSubSelect()).ifPresent(this::tryRemoveFromItem);
        } else {
            final SetOperationList sol = (SetOperationList) selectBody;
            final List<SelectBody> selects = sol.getSelects();
            if (Objects.isNotEmpty(selects)) {
                selects.forEach(this::tryRemoveFromSelectBody);
            }
            if (this.canRemove(sol.getOrderByElements())) {
                sol.setOrderByElements(null);
            }
        }
    }

    /**
     * 尝试去除{@link PlainSelect}中的排序子句
     *
     * @param select {@link Select}
     */
    protected void tryRemoveFromPlainSelect(final PlainSelect select) {
        if (this.canRemove(select.getOrderByElements())) {
            select.setOrderByElements(null);
        }
        final List<Join> joins = select.getJoins();
        if (Objects.isNotEmpty(joins)) {
            joins.stream().filter(Objects::nonNull).map(Join::getRightItem).forEach(this::tryRemoveFromItem);
        }
    }

    /**
     * 尝试去除{@link FromItem}中的排序子句
     *
     * @param item {@link FromItem}
     */
    protected void tryRemoveFromItem(final FromItem item) {
        if (item instanceof SubJoin) {
            final SubJoin subJoin = ((SubJoin) item);
            final List<Join> joins = subJoin.getJoinList();
            if (Objects.isNotEmpty(joins)) {
                joins.stream().filter(Objects::nonNull).map(Join::getRightItem).forEach(this::tryRemoveFromItem);
            }
            Optional.ofNullable(subJoin.getLeft()).ifPresent(this::tryRemoveFromItem);
        } else if (item instanceof SubSelect) {
            Optional.ofNullable(((SubSelect) item).getSelectBody()).ifPresent(this::tryRemoveFromSelectBody);
        } else if (item instanceof LateralSubSelect) {
            Optional.ofNullable(((LateralSubSelect) item).getSubSelect()).map(SubSelect::getSelectBody)
                    .ifPresent(this::tryRemoveFromSelectBody);
        }
    }

    /**
     * 检查是否可移除排序子句
     *
     * @param items 排序字段集合
     * @return boolean
     */
    protected boolean canRemove(final List<OrderByElement> items) {
        if (Objects.isNotEmpty(items)) {
            final String it = items.stream().map(oe -> oe.toString().trim()).collect(Collectors.joining(this.commaSpace));
            return !this.keepOrderlyElements.contains(it);
        }
        return false;
    }

    public Set<String> getKeepOrderlyElements() {
        return ImmutableSet.copyOf(this.keepOrderlyElements);
    }
}
