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
package io.github.mybatisx.base.helper;

import com.google.common.collect.ImmutableList;
import io.github.mybatisx.base.config.MyBatisGlobalConfig;
import io.github.mybatisx.base.config.MyBatisGlobalConfigCache;
import io.github.mybatisx.base.exception.MyBatisException;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.base.parsing.DefaultEntityParser;
import io.github.mybatisx.base.parsing.EntityParser;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.matcher.Matcher;
import io.github.mybatisx.reflect.Reflections;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 表映射工具
 *
 * @author wvkity
 * @created 2021/12/26
 * @since 1.0.0
 */
public final class TableHelper {

    private static final Logger log = LoggerFactory.getLogger(TableHelper.class);

    /**
     * 表映射缓存
     */
    private static final Map<String, Table> TABLE_MAPPED_CACHE = new ConcurrentHashMap<>();
    /**
     * 默认实体类解析器
     */
    private static final EntityParser DEFAULT_ENTITY_PARSER = DefaultEntityParser.of();

    private TableHelper() {
    }

    /**
     * 解析实体类信息
     *
     * @param mba         {@link MapperBuilderAssistant}
     * @param entityClass 实体类
     * @return {@link Table}
     */
    public synchronized static Table parse(final MapperBuilderAssistant mba, final Class<?> entityClass) {
        if (Objects.nonNull(entityClass)) {
            final String entityName = Reflections.getRealClass(entityClass).getName();
            final Table oldTable = TABLE_MAPPED_CACHE.get(entityName);
            if (Objects.isNull(oldTable)) {
                log.debug("Parsing the entity class corresponding table mapping information: {}", entityName);
                final Configuration cfg = mba.getConfiguration();
                final MyBatisGlobalConfig globalConfig = MyBatisGlobalConfigCache.getGlobalConfig(cfg);
                final Table table = TableHelper.getEntityParser(globalConfig).parse(cfg, entityClass,
                        mba.getCurrentNamespace());
                if (Objects.nonNull(table) && !TABLE_MAPPED_CACHE.containsKey(entityName)) {
                    TABLE_MAPPED_CACHE.putIfAbsent(entityName, table);
                }
                return table;
            }
            return oldTable;
        }
        return null;
    }

    /**
     * 根据实体类获取其对应的表对象
     *
     * @param entity 实体类
     * @return {@link Table}
     */
    public static Table getTable(final Class<?> entity) {
        if (Objects.nonNull(entity)) {
            return TableHelper.getTable(Reflections.getRealClass(entity).getName());
        }
        return null;
    }

    /**
     * 根据实体类全限定名获取其对应的表对象
     *
     * @param entityName 实体类名
     * @return {@link Table}
     */
    public static Table getTable(final String entityName) {
        if (Strings.isNotWhitespace(entityName)) {
            return TABLE_MAPPED_CACHE.get(entityName);
        }
        return null;
    }

    /**
     * 验证是否存在主键，不存在则抛出异常
     *
     * @param entity 实体类
     */
    public static void checkPrimaryKey(final Class<?> entity) {
        final Table table = TableHelper.getTable(entity);
        if (table == null) {
            throw new MyBatisException("The corresponding table mapping object cannot be found according to " +
                    "the entity class name(" + (entity == null ? "null" : entity.getName()) + ")");
        }
        if (!table.isHasPrimaryKey()) {
            throw new MyBatisException("The table mapping object of entity class(" + entity.getName() + ") does " +
                    "not have a primary key");
        }
    }

    /**
     * 根据实体类获取表主键
     *
     * @param entity 实体类
     * @return 主键
     */
    public static Column getPrimaryKey(final Class<?> entity, final boolean nonMatchesThrowing,
                                       final boolean printWarning) {
        if (entity == null) {
            return null;
        }
        final Table table = TableHelper.getTable(entity);
        final Column pk = Optional.ofNullable(table).map(Table::getPrimaryKey).orElse(null);
        if (pk == null) {
            if (nonMatchesThrowing) {
                throw new MyBatisException("The table mapping object of entity class(" + entity.getName() + ") does " +
                        "not have a primary key");
            } else {
                if (printWarning) {
                    log.warn("The table mapping object of entity class({}) does not have a primary key", entity.getName());
                }
            }
        } else if (!table.isOnlyOnePrimaryKey()) {
            throw new MyBatisException("The entity class '" + entity.getName() + "' has multiple primary keys");
        }
        return pk;
    }

    /**
     * 根据实体类、属性名获取{@link Column}对象
     *
     * @param entity             实体类
     * @param property           属性名
     * @param nonMatchesThrowing 忽略查找失败
     * @param printWarning       是否打印警告信息
     * @return {@link Column}
     */
    public static Column getColumnByProperty(final Class<?> entity, final String property,
                                             final boolean nonMatchesThrowing, final boolean printWarning) {
        if (entity == null || Strings.isWhitespace(property)) {
            return null;
        }
        final Column column = Optional.ofNullable(getTable(entity)).map(it ->
                it.getByProperty(property)).orElse(null);
        if (column == null) {
            if (nonMatchesThrowing) {
                throw new MyBatisException("The field mapping information for the entity class(" +
                        entity.getName() + ") cannot be found based on the `" + property + "` " +
                        "attribute. Check to see if the attribute exists or is decorated using the @Transient " +
                        "annotation.");
            } else {
                if (printWarning) {
                    log.warn("The field mapping information for the entity class({}) cannot be found based on the " +
                            "`{}` attribute. Check to see if the attribute exists or is decorated using the @Transient " +
                            "annotation.", entity.getName(), property);
                }
            }
        }
        return column;
    }

    /**
     * 根据实体类、字段名获取{@link Column}对象
     *
     * @param entity 实体类
     * @param column 字段名(忽略大小写)
     * @return {@link Column}
     */
    public static Column getColumnByName(final Class<?> entity, final String column) {
        if (entity == null || Strings.isWhitespace(column)) {
            return null;
        }
        return Optional.ofNullable(getTable(entity)).map(it -> it.getByColumn(column)).orElse(null);
    }

    /**
     * 获取字段列表
     *
     * @param clazz   实体类
     * @param matcher {@link Matcher}
     * @return 字段列表
     */
    public static List<Column> getColumns(final Class<?> clazz, final Matcher<Column> matcher) {
        final Table table = getTable(clazz);
        if (table != null) {
            return ImmutableList.copyOf(table.filtrate(matcher));
        }
        return ImmutableList.of();
    }

    /**
     * 获取{@link EntityParser}
     *
     * @param globalConfig {@link MyBatisGlobalConfig}
     * @return {@link EntityParser}
     */
    private static EntityParser getEntityParser(final MyBatisGlobalConfig globalConfig) {
        return Optional.ofNullable(globalConfig).map(it -> {
            final EntityParser parser = it.getEntityParser();
            if (Objects.isNull(parser)) {
                it.setEntityParser(DEFAULT_ENTITY_PARSER);
                return DEFAULT_ENTITY_PARSER;
            }
            return parser;
        }).orElse(DEFAULT_ENTITY_PARSER);
    }
}
