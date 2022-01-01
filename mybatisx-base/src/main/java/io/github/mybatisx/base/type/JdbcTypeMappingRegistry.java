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
package io.github.mybatisx.base.type;

import io.github.mybatisx.lang.Types;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.chrono.JapaneseDate;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JDBC类型映射注册器
 *
 * @author wvkity
 * @created 2021/12/30
 * @since 1.0.0
 */
public final class JdbcTypeMappingRegistry {

    private JdbcTypeMappingRegistry() {
    }

    /**
     * JDBC类型映射缓存
     */
    private static final Map<Class<?>, JdbcType> JDBC_TYPE_MAPPINGS = new ConcurrentHashMap<>(64);

    static {
        // Basic
        JDBC_TYPE_MAPPINGS.put(byte.class, JdbcType.BIT);
        JDBC_TYPE_MAPPINGS.put(Byte.class, JdbcType.BIT);
        JDBC_TYPE_MAPPINGS.put(boolean.class, JdbcType.BIT);
        JDBC_TYPE_MAPPINGS.put(Boolean.class, JdbcType.BIT);
        JDBC_TYPE_MAPPINGS.put(short.class, JdbcType.SMALLINT);
        JDBC_TYPE_MAPPINGS.put(Short.class, JdbcType.SMALLINT);
        JDBC_TYPE_MAPPINGS.put(int.class, JdbcType.INTEGER);
        JDBC_TYPE_MAPPINGS.put(Integer.class, JdbcType.INTEGER);
        JDBC_TYPE_MAPPINGS.put(long.class, JdbcType.BIGINT);
        JDBC_TYPE_MAPPINGS.put(Long.class, JdbcType.BIGINT);
        JDBC_TYPE_MAPPINGS.put(float.class, JdbcType.FLOAT);
        JDBC_TYPE_MAPPINGS.put(Float.class, JdbcType.FLOAT);
        JDBC_TYPE_MAPPINGS.put(double.class, JdbcType.DOUBLE);
        JDBC_TYPE_MAPPINGS.put(Double.class, JdbcType.DOUBLE);
        JDBC_TYPE_MAPPINGS.put(BigDecimal.class, JdbcType.NUMERIC);
        JDBC_TYPE_MAPPINGS.put(BigInteger.class, JdbcType.BIGINT);
        JDBC_TYPE_MAPPINGS.put(char.class, JdbcType.CHAR);
        JDBC_TYPE_MAPPINGS.put(Character.class, JdbcType.CHAR);
        JDBC_TYPE_MAPPINGS.put(String.class, JdbcType.VARCHAR);
        // String
        JDBC_TYPE_MAPPINGS.put(Locale.class, JdbcType.VARCHAR);
        JDBC_TYPE_MAPPINGS.put(Currency.class, JdbcType.VARCHAR);
        JDBC_TYPE_MAPPINGS.put(TimeZone.class, JdbcType.VARCHAR);
        JDBC_TYPE_MAPPINGS.put(Class.class, JdbcType.VARCHAR);
        JDBC_TYPE_MAPPINGS.put(Clob.class, JdbcType.CLOB);
        JDBC_TYPE_MAPPINGS.put(Blob.class, JdbcType.BLOB);
        JDBC_TYPE_MAPPINGS.put(byte[].class, JdbcType.BLOB);
        JDBC_TYPE_MAPPINGS.put(Byte[].class, JdbcType.BLOB);
        // Time
        JDBC_TYPE_MAPPINGS.put(Date.class, JdbcType.TIMESTAMP);
        JDBC_TYPE_MAPPINGS.put(java.sql.Date.class, JdbcType.TIMESTAMP);
        JDBC_TYPE_MAPPINGS.put(Calendar.class, JdbcType.TIMESTAMP);
        JDBC_TYPE_MAPPINGS.put(Timestamp.class, JdbcType.TIMESTAMP);
        JDBC_TYPE_MAPPINGS.put(Instant.class, JdbcType.TIMESTAMP);
        JDBC_TYPE_MAPPINGS.put(LocalDateTime.class, JdbcType.TIMESTAMP);
        JDBC_TYPE_MAPPINGS.put(OffsetDateTime.class, JdbcType.TIMESTAMP);
        JDBC_TYPE_MAPPINGS.put(ZonedDateTime.class, JdbcType.TIMESTAMP);
        JDBC_TYPE_MAPPINGS.put(LocalDate.class, JdbcType.DATE);
        JDBC_TYPE_MAPPINGS.put(JapaneseDate.class, JdbcType.DATE);
        JDBC_TYPE_MAPPINGS.put(OffsetTime.class, JdbcType.TIME);
        JDBC_TYPE_MAPPINGS.put(LocalTime.class, JdbcType.TIME);
    }

    /**
     * 注册JDBC类型映射
     *
     * @param key   类型
     * @param value JDBC类型
     */
    public static void registry(final Class<?> key, final JdbcType value) {
        registry(key, value, false);
    }

    /**
     * 注册JDBC类型映射
     *
     * @param key      类型
     * @param value    JDBC类型
     * @param override 是否覆盖已存在的
     */
    public static void registry(final Class<?> key, final JdbcType value, final boolean override) {
        if (key != null && value != null && value != JdbcType.UNDEFINED) {
            if (override) {
                JDBC_TYPE_MAPPINGS.put(key, value);
            } else {
                JDBC_TYPE_MAPPINGS.putIfAbsent(key, value);
            }
        }
    }

    /**
     * 获取JDBC类型
     *
     * @param className 类名
     * @return {@link JdbcType}
     */
    public static JdbcType getJdbcType(final String className) {
        return getJdbcType(className, null);
    }

    /**
     * 获取JDBC类型
     *
     * @param className       类名
     * @param defaultJdbcType 默认JDBC类型
     * @return {@link JdbcType}
     */
    public static JdbcType getJdbcType(final String className, final JdbcType defaultJdbcType) {
        return getJdbcType(Types.loadClassIgnoreExp(className), defaultJdbcType);
    }

    /**
     * 获取JDBC类型
     *
     * @param clazz 类型
     * @return {@link JdbcType}
     */
    public static JdbcType getJdbcType(final Class<?> clazz) {
        return getJdbcType(clazz, null);
    }

    /**
     * 获取JDBC类型
     *
     * @param clazz           类型
     * @param defaultJdbcType 默认JDBC类型
     * @return {@link JdbcType}
     */
    public static JdbcType getJdbcType(final Class<?> clazz, final JdbcType defaultJdbcType) {
        return Optional.ofNullable(clazz).map(it ->
                JDBC_TYPE_MAPPINGS.getOrDefault(clazz, defaultJdbcType)
        ).orElse(defaultJdbcType);
    }

    /**
     * 检查JDBC类型映射缓存中是否存在指定的类型
     *
     * @param clazz 类型
     * @return boolean
     */
    public static boolean contains(final Class<?> clazz) {
        return clazz != null && JDBC_TYPE_MAPPINGS.containsKey(clazz);
    }

}
