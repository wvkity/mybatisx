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
package io.github.mybatisx.core.criteria;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.convert.ParameterConverter;
import io.github.mybatisx.base.convert.PlaceholderConverter;
import io.github.mybatisx.base.dialect.Dialect;
import io.github.mybatisx.base.helper.TableHelper;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.core.convert.ConditionConverter;
import io.github.mybatisx.core.convert.DefaultConditionConverter;
import io.github.mybatisx.core.convert.DefaultParameterConverter;
import io.github.mybatisx.core.convert.DefaultPlaceholderConverter;
import io.github.mybatisx.core.fragment.ConditionStorage;
import io.github.mybatisx.core.fragment.DefaultFragmentManager;
import io.github.mybatisx.core.fragment.FragmentManager;
import io.github.mybatisx.core.property.LambdaMetadataWeakCache;
import io.github.mybatisx.core.property.Property;
import io.github.mybatisx.core.sql.SqlManager;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.matcher.Matcher;
import io.github.mybatisx.sql.parsing.JSqlParser;
import io.github.mybatisx.sql.parsing.SqlParser;
import io.github.mybatisx.util.Collections;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 抽象基础条件
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
@Slf4j
@SuppressWarnings({"serial"})
public abstract class AbstractBaseCriteria<T> implements BaseCriteria<T> {

    // region Base fields

    /**
     * 默认表别名前缀
     */
    protected static final String TABLE_ALIAS_PREFIX = "_it_";
    /**
     * 实体类
     */
    protected Class<?> entity;
    /**
     * 数据库方言
     */
    @Getter
    protected Dialect dialect;
    /**
     * 默认表别名
     */
    protected String defaultAlias;
    /**
     * 别名引用
     */
    protected AtomicReference<String> aliasRef = new AtomicReference<>(Constants.EMPTY);
    /**
     * 是否启用别名
     */
    protected AtomicBoolean useAlias;
    /**
     * 参数序列
     */
    protected AtomicInteger parameterSequence;
    /**
     * 参数值映射
     */
    protected Map<String, Object> paramValueMapping;
    /**
     * 参数转换器
     */
    protected transient ParameterConverter parameterConverter;
    /**
     * 条件转换器
     */
    protected transient ConditionConverter conditionConverter;
    /**
     * 占位符参数转换器
     */
    protected transient PlaceholderConverter placeholderConverter;
    /**
     * 片段管理器
     */
    protected transient FragmentManager fragmentManager;
    /**
     * SQL管理器
     */
    protected transient SqlManager sqlManager;
    /**
     * or/and
     */
    protected AtomicReference<LogicSymbol> slotRef = new AtomicReference<>(LogicSymbol.AND);
    /**
     * 属性不匹配则抛出异常
     */
    protected AtomicBoolean nonMatchingThenThrows;
    /**
     * 表别名序列
     */
    protected AtomicInteger tableAliasSequence;
    /**
     * 普通条件/having筛选条件切换
     */
    protected AtomicBoolean conditionToggle = new AtomicBoolean(true);
    /**
     * SQL解析器
     */
    protected AtomicReference<SqlParser> parserRef;
    /**
     * 类别
     */
    protected Category category;

    // ----- update -----

    /**
     * 待更新包装字段
     */
    protected Map<Column, Object> updateWrapColumns;
    /**
     * 待更新字段列表
     */
    protected Set<String> updateColumns;

    // endregion

    // region Protected methods

    /**
     * 属性初始化
     */
    protected void newInit() {
        this.newInit(null, Category.BASIC);
    }

    /**
     * 查询类型对象属性初始化
     *
     * @param alias 表别名
     */
    protected void newQueryInit(final String alias) {
        this.newInit(alias, Category.QUERY);
    }

    /**
     * 更新类型对象属性初始化
     */
    protected void newUpdateInit() {
        this.newInit(null, Category.UPDATE);
    }

    /**
     * 删除类型对象属性初始化
     */
    protected void newDeleteInit() {
        this.newInit(null, Category.DELETE);
    }

    /**
     * 属性初始化
     *
     * @param alias    表别名
     * @param category {@link Category}
     */
    protected void newInit(final String alias, final Category category) {
        final boolean hasAlias = Strings.isNotWhitespace(alias);
        this.parameterSequence = new AtomicInteger(0);
        this.paramValueMapping = new HashMap<>(16);
        this.parameterConverter = new DefaultParameterConverter(this.parameterSequence, this.paramValueMapping);
        this.placeholderConverter = new DefaultPlaceholderConverter(this.parameterConverter);
        this.conditionConverter = new DefaultConditionConverter(this);
        this.parserRef = new AtomicReference<>();
        this.category = category;
        if (category == Category.QUERY) {
            this.fragmentManager = new DefaultFragmentManager(this, this.parameterConverter, this.placeholderConverter);
            this.parserRef.set(new JSqlParser());
        } else {
            this.fragmentManager = new DefaultFragmentManager(this,
                    new ConditionStorage(this.parameterConverter, this.placeholderConverter));
            if (category == Category.UPDATE) {
                this.updateWrapColumns = new HashMap<>(16);
                this.updateColumns = new HashSet<>(8);
            }
        }
        this.nonMatchingThenThrows = new AtomicBoolean(true);
        this.tableAliasSequence = new AtomicInteger(0);
        this.useAlias = new AtomicBoolean(hasAlias);
        this.defaultAlias = this.genDefaultAlias();
        if (hasAlias) {
            aliasRef.set(alias.trim());
        }
    }

    /**
     * 复制
     *
     * @param source 源对象
     */
    protected void clone(final AbstractBaseCriteria<?> source) {
        this.clone(source, true);
    }

    /**
     * 复制
     *
     * @param source 源对象
     * @param deep   是否深度拷贝
     */
    protected void clone(final AbstractBaseCriteria<?> source, final boolean deep) {
        this.clone(source, this, deep);
    }

    /**
     * 复制
     *
     * @param source 源对象
     * @param target 目标对象
     * @param deep   是否深度拷贝
     */
    protected void clone(final AbstractBaseCriteria<?> source, final AbstractBaseCriteria<?> target,
                         final boolean deep) {
        if (source != null && target != null) {
            target.parserRef = source.parserRef;
            target.parameterSequence = source.parameterSequence;
            target.paramValueMapping = source.paramValueMapping;
            target.placeholderConverter = source.placeholderConverter;
            target.tableAliasSequence = source.tableAliasSequence;
            target.nonMatchingThenThrows = source.nonMatchingThenThrows;
            target.parameterConverter = source.parameterConverter;
            target.fragmentManager = new DefaultFragmentManager(this, this.parameterConverter, this.placeholderConverter);
            target.conditionConverter = new DefaultConditionConverter(this);
            target.useAlias = source.useAlias;
            if (deep) {
                target.dialect = source.dialect;
                target.entity = source.entity;
                target.aliasRef = source.aliasRef;
                target.defaultAlias = source.defaultAlias;
                target.sqlManager = source.sqlManager;
            }
        }
    }

    /**
     * 检查是否存在主键，不存在则抛出异常
     */
    protected void checkPrimaryKey() {
        TableHelper.checkPrimaryKey(this.entity);
    }

    /**
     * 获取主键
     *
     * @return 主键
     */
    public Column getPrimaryKey() {
        return TableHelper.getPrimaryKey(this.getEntity(), this.isStrict(), true);
    }

    /**
     * 预备处理
     *
     * @param value   值
     * @param matcher 匹配器
     * @param <V>     值类型
     * @return boolean
     */
    protected <V> boolean early(final V value, final Matcher<V> matcher) {
        return Objects.isNull(matcher) || matcher.matches(value);
    }

    /**
     * 返回当前对象
     *
     * @return {@code this}
     */
    protected BaseCriteria<T> self() {
        return this;
    }

    /**
     * 生成默认表别名
     *
     * @return 默认表别名
     */
    protected String genDefaultAlias() {
        return TABLE_ALIAS_PREFIX + this.tableAliasSequence.incrementAndGet() + Constants.UNDER_LINE;
    }

    /**
     * 字符串属性列表转字段列表
     *
     * @param properties 属性列表
     * @return 字段列表
     */
    protected List<String> stringConvert(final List<String> properties) {
        if (Collections.isNotEmpty(properties)) {
            final Table table = TableHelper.getTable(this.entity);
            if (table != null) {
                final List<String> columns = new ArrayList<>(properties.size());
                for (String property : properties) {
                    if (Strings.isNotWhitespace(property)) {
                        final Column column = table.getByProperty(property);
                        if (column == null) {
                            log.warn("The field mapping information for the entity class({}) cannot be found based on the " +
                                    "`{}` attribute. Check to see if the attribute exists or is decorated using the @Transient " +
                                    "annotation.", this.entity.getName(), property);
                        } else {
                            columns.add(column.getColumn());
                        }
                    }
                }
                return columns;
            }
        }
        return null;
    }

    /**
     * Lambda属性列表转字段列表
     *
     * @param properties 属性列表
     * @return 字段列表
     */
    protected List<String> lambdaConvert(final List<Property<T, ?>> properties) {
        if (Collections.isNotEmpty(properties)) {
            final Table table = TableHelper.getTable(this.entity);
            if (table != null) {
                final List<String> columns = new ArrayList<>(properties.size());
                for (Property<T, ?> it : properties) {
                    final String property = LambdaMetadataWeakCache.getProperty(it);
                    if (Strings.isNotWhitespace(property)) {
                        final Column column = table.getByProperty(property);
                        if (column == null) {
                            log.warn("The field mapping information for the entity class({}) cannot be found based on the " +
                                    "`{}` attribute. Check to see if the attribute exists or is decorated using the @Transient " +
                                    "annotation.", this.entity.getName(), property);
                        } else {
                            columns.add(column.getColumn());
                        }
                    }
                }
                return columns;
            }

        }
        return null;
    }

    /**
     * 获取完整SQL语句
     *
     * @return SQL语句
     */
    protected String getCompleteString() {
        return this.sqlManager.getCompleteString();
    }

    /**
     * 获取条件SQL语句
     *
     * @param self             是否自身
     * @param appendWhere      是否拼接条件
     * @param groupReplacement 分组替换语句
     * @return 条件语句
     */
    public String getWhereString(final boolean self, final boolean appendWhere, final String groupReplacement) {
        return this.sqlManager.getWhereString(self, appendWhere, groupReplacement);
    }

    // endregion

    // region Override methods

    /**
     * 根据属性名获取{@link Column}对象
     *
     * @param property 属性名
     * @return {@link Column}
     */
    public Column convert(final String property) {
        return this.convert(property, true);
    }

    /**
     * 获取{@link Column}对象
     *
     * @param target     字段名/属性
     * @param isProperty 是否为属性
     * @return {@link Column}对象
     */
    protected Column convert(final String target, final boolean isProperty) {
        if (isProperty) {
            return TableHelper.getColumnByProperty(this.entity, target, this.nonMatchingThenThrows.get(), true);
        }
        return TableHelper.getColumnByName(this.entity, target, this.nonMatchingThenThrows.get(), true);
    }

    @Override
    public boolean isStrict() {
        return this.nonMatchingThenThrows.get();
    }

    @Override
    public String getTableName(boolean jointAs) {
        final String as = this.as();
        final boolean hasAs = Strings.isNotWhitespace(as);
        if (this.entity != null) {
            final Table table = TableHelper.getTable(this.entity);
            if (table != null) {
                final String tableName = table.getFullName();
                if (hasAs) {
                    if (jointAs) {
                        return tableName + SqlSymbol.SPACE + SqlSymbol.AS + SqlSymbol.SPACE + as;
                    }
                    return tableName + SqlSymbol.SPACE + as;
                }
                return tableName;
            }
        }
        return hasAs ? as : Constants.EMPTY;
    }

    /**
     * 获取表名
     *
     * @param fragment sql片段
     * @param jointAs  是否拼接'AS'
     * @return 表名
     */
    protected String getTableName(final String fragment, final boolean jointAs) {
        final StringBuilder sb = new StringBuilder(120);
        sb.append(SqlSymbol.START_BRACKET).append(fragment).append(SqlSymbol.END_BRACKET);
        final String as = this.as();
        if (Strings.isNotWhitespace(as)) {
            sb.append(SqlSymbol.SPACE);
            if (jointAs) {
                sb.append(SqlSymbol.AS).append(SqlSymbol.SPACE);
            }
            sb.append(as);
        }
        return sb.toString();
    }

    /**
     * 添加更新字段
     *
     * @param target     字段/属性
     * @param value      值
     * @param isProperty 是否为属性
     * @param isAbsent   如果存在是否覆盖
     */
    protected void update(final String target, final Object value, final boolean isProperty, final boolean isAbsent) {
        final Column column = this.convert(target, isProperty);
        if (column != null) {
            final String columnName = column.getColumn();
            if (isAbsent && this.updateIsExists(columnName)) {
                return;
            }
            this.updateColumns.add(columnName.toUpperCase(Locale.ENGLISH));
            this.updateWrapColumns.put(column, value);
        }
    }

    /**
     * 更新字段是否已存在
     *
     * @param column 字段名
     * @return boolean
     */
    protected boolean updateIsExists(final String column) {
        return this.updateColumns.contains(column.toLowerCase(Locale.ENGLISH));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<T> getEntity() {
        return this.entity == null ? null : (Class<T>) this.entity;
    }

    @Override
    public Object getVersionValue() {
        return null;
    }

    @Override
    public boolean isHasCondition() {
        return this.fragmentManager.hasCondition();
    }

    @Override
    public boolean isHasFragment() {
        return this.fragmentManager.hasFragment();
    }

    @Override
    public String getWhereFragment() {
        return this.sqlManager.getWhereString();
    }

    @Override
    public String getFragment() {
        return this.getCompleteString();
    }

    @Override
    public String completeString() {
        return this.sqlManager.getCompleteString();
    }

    // endregion

}
