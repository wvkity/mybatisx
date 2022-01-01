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
package io.github.mybatisx.support.helper;

import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.base.parsing.EntityParser;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.reflect.Reflections;
import io.github.mybatisx.support.config.MyBatisGlobalConfig;
import io.github.mybatisx.support.config.MyBatisGlobalConfigCache;
import io.github.mybatisx.support.parsing.DefaultEntityParser;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                log.info("Parsing the entity class corresponding table mapping information: {}", entityName);
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
