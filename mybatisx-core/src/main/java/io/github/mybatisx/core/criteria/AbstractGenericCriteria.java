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
import io.github.mybatisx.base.dialect.Dialect;
import io.github.mybatisx.base.exception.MyBatisException;
import io.github.mybatisx.base.helper.TableHelper;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.core.convert.ConditionConverter;
import io.github.mybatisx.core.convert.DefaultConditionConverter;
import io.github.mybatisx.core.convert.DefaultParameterConverter;
import io.github.mybatisx.core.convert.ParameterConverter;
import io.github.mybatisx.core.management.DefaultFragmentManager;
import io.github.mybatisx.core.management.FragmentManager;
import io.github.mybatisx.core.sql.SqlManager;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.matcher.Matcher;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 抽象条件
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
@Slf4j
@SuppressWarnings({"serial"})
public abstract class AbstractGenericCriteria<T> implements GenericCriteria<T> {

    // region Base fields

    /**
     * 实体类
     */
    @Getter
    protected Class<T> entity;
    /**
     * 设置方言
     */
    @Getter
    protected Dialect dialect;
    /**
     * 别名引用
     */
    protected AtomicReference<String> aliasRef = new AtomicReference<>(Constants.EMPTY);
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

    // endregion

    // region Protected methods

    protected void newInit(final String alias) {
        this.parameterSequence = new AtomicInteger(0);
        this.paramValueMapping = new ConcurrentHashMap<>(16);
        this.parameterConverter = new DefaultParameterConverter(this.parameterSequence, this.paramValueMapping);
        this.conditionConverter = new DefaultConditionConverter(this, this.parameterConverter);
        this.fragmentManager = new DefaultFragmentManager();
        this.nonMatchingThenThrows = new AtomicBoolean(true);
        if (Strings.isNotWhitespace(alias)) {
            aliasRef.set(alias.trim());
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
    protected Column getPrimaryKey() {
        return TableHelper.getPrimaryKey(this.getEntity());
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
    protected GenericCriteria<T> self() {
        return this;
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
        if (Strings.isNotWhitespace(property)) {
            final Column column = TableHelper.getByProperty(this.entity, property);
            if (column == null) {
                if (this.nonMatchingThenThrows.get()) {
                    throw new MyBatisException("The field mapping information for the entity class(" +
                            this.entity.getName() + ") cannot be found based on the `" + property + "` " +
                            "attribute. Check to see if the attribute exists or is decorated using the @Transient " +
                            "annotation.");
                } else {
                    log.warn("The field mapping information for the entity class({}) cannot be found based on the " +
                            "`{}` attribute. Check to see if the attribute exists or is decorated using the @Transient " +
                            "annotation.", this.entity.getName(), property);
                }
            }
            return column;
        }
        return null;
    }

    @Override
    public boolean isStrict() {
        return this.nonMatchingThenThrows.get();
    }

    @Override
    public Object getVersionValue() {
        return null;
    }

    @Override
    public String getWhereString() {
        return this.sqlManager.getWhereString();
    }

    @Override
    public String getFragment() {
        return this.getCompleteString();
    }

    // endregion

}
