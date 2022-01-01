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
package io.github.mybatisx.base.constant;

/**
 * JPA相关注解类名常量
 *
 * @author wvkity
 * @created 2021/12/27
 * @since 1.0.0
 */
public interface Jpa {

    /**
     * 表注解({@literal @}Table)
     */
    String TABLE = "javax.persistence.Table";
    /**
     * 表注解name属性
     * <p>
     * {@literal @}Table(name = "")
     */
    String TABLE_PROP_NAME = "name";
    /**
     * 表注解catalog属性
     * <p>
     * {@literal @}Table(catalog = "")
     */
    String TABLE_PROP_CATALOG = "catalog";
    /**
     * 表注解schema属性
     * <p>
     * {@literal @}Table(schema = "")
     */
    String TABLE_PROP_SCHEMA = "schema";
    /**
     * 实体注解({@literal @}Entity)
     */
    String ENTITY = "javax.persistence.Entity";
    /**
     * 实体注解name属性
     * <p>
     * {@literal @}Entity(name = "")
     */
    String ENTITY_PROP_NAME = "name";
    /**
     * {@literal @}Column
     */
    String COLUMN = "javax.persistence.Column";
    /**
     * 字段注解name属性
     * <p>
     * {@literal @}Column(name = "")
     */
    String COLUMN_PROP_NAME = "name";
    /**
     * 字段注解insertable属性
     * <p>
     * {@literal @}Column(insertable = "")
     */
    String COLUMN_PROP_INSERTABLE = "insertable";
    /**
     * 字段注解updatable属性
     * <p>
     * {@literal @}Column(updatable = "")
     */
    String COLUMN_PROP_UPDATABLE = "updatable";
    /**
     * {@literal @}Transient
     */
    String TRANSIENT = "javax.persistence.Transient";
    /**
     * {@literal @}Id
     */
    String ID = "javax.persistence.Id";
    /**
     * {@literal @}GeneratedValue
     */
    String GENERATED_VALUE = "javax.persistence.GeneratedValue";
    /**
     * 主键生成策略注解generator属性
     * <p>
     * {@literal @}GeneratedValue(generator = "")
     */
    String GV_PROP_GENERATOR = "generator";
    /**
     * J主键生成策略注解strategy属性
     * <p>
     * {@literal @}GeneratedValue(strategy = "")
     */
    String GV_PROP_STRATEGY = "strategy";
    /**
     * {@literal @}Version
     */
    String VERSION = "javax.persistence.Version";
}
