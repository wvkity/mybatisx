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
package io.github.mybatisx.auditable.config;

import io.github.mybatisx.auditable.annotation.CreatedBy;
import io.github.mybatisx.auditable.annotation.CreatedByName;
import io.github.mybatisx.auditable.annotation.CreatedDate;
import io.github.mybatisx.auditable.annotation.DeletedBy;
import io.github.mybatisx.auditable.annotation.DeletedByName;
import io.github.mybatisx.auditable.annotation.DeletedDate;
import io.github.mybatisx.auditable.annotation.LastModifiedBy;
import io.github.mybatisx.auditable.annotation.LastModifiedByName;
import io.github.mybatisx.auditable.annotation.LastModifiedDate;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 自动审计属性注册器
 *
 * @author wvkity
 * @created 2021/12/31
 * @since 1.0.0
 */
public final class AutomaticAuditPropertyRegistry {

    private AutomaticAuditPropertyRegistry() {
    }

    private static final Set<String> PROP_INSERT_IDS = new CopyOnWriteArraySet<>();
    private static final Set<String> PROP_INSERT_NAMES = new CopyOnWriteArraySet<>();
    private static final Set<String> PROP_INSERT_DATES = new CopyOnWriteArraySet<>();
    private static final Set<String> PROP_UPDATE_IDS = new CopyOnWriteArraySet<>();
    private static final Set<String> PROP_UPDATE_NAMES = new CopyOnWriteArraySet<>();
    private static final Set<String> PROP_UPDATE_DATES = new CopyOnWriteArraySet<>();
    private static final Set<String> PROP_DELETE_IDS = new CopyOnWriteArraySet<>();
    private static final Set<String> PROP_DELETE_NAMES = new CopyOnWriteArraySet<>();
    private static final Set<String> PROP_DELETE_DATES = new CopyOnWriteArraySet<>();
    private static final Map<String, Class<? extends Annotation>> PROPS_CACHE = new ConcurrentHashMap<>();

    static {
        add(PROP_INSERT_IDS, "createdBy", "createdById", "createdUserId");
        add(PROP_INSERT_NAMES, "createdByName", "createdUserName");
        add(PROP_INSERT_DATES, "createdDate", "createdTime", "createdDateTime", "gmtCreated");
        add(PROP_UPDATE_IDS, "updateBy", "lastModifiedBy", "lastModifiedById", "lastModifiedUserId");
        add(PROP_UPDATE_NAMES, "updateByName", "lastModifiedByName", "lastModifiedUserName");
        add(PROP_UPDATE_DATES, "updateDate", "updateTime", "updateDateTime", "gmtLastModifiedDate");
        add(PROP_DELETE_IDS, "deletedBy", "deleteById", "deletedUserId");
        add(PROP_DELETE_NAMES, "deletedByName", "deletedUserName");
        add(PROP_DELETE_DATES, "deletedDate", "deletedTime", "deletedDateTime", "gmtDeleted");
        registry(CreatedBy.class, PROP_INSERT_IDS);
        registry(CreatedByName.class, PROP_INSERT_NAMES);
        registry(CreatedDate.class, PROP_INSERT_DATES);
        registry(LastModifiedBy.class, PROP_UPDATE_IDS);
        registry(LastModifiedByName.class, PROP_UPDATE_NAMES);
        registry(LastModifiedDate.class, PROP_UPDATE_DATES);
        registry(DeletedBy.class, PROP_DELETE_IDS);
        registry(DeletedByName.class, PROP_DELETE_NAMES);
        registry(DeletedDate.class, PROP_DELETE_DATES);
    }

    private static void add(final Set<String> target, final String... properties) {
        target.addAll(Arrays.asList(properties));
    }

    /**
     * 注册审计属性
     *
     * @param clazz      注解类
     * @param properties 属性列表
     */
    public static void registry(final Class<? extends Annotation> clazz, final String... properties) {
        if (Objects.nonNull(clazz) && Objects.isNotEmpty(properties)) {
            registry(clazz, Arrays.asList(properties));
        }
    }

    /**
     * 注册审计属性
     *
     * @param clazz    注解类
     * @param iterable 属性列表
     */
    public static void registry(final Class<? extends Annotation> clazz, final Iterable<String> iterable) {
        if (Objects.nonNull(clazz) && Objects.nonNull(iterable)) {
            for (String it : iterable) {
                if (Strings.isNotWhitespace(it)) {
                    PROPS_CACHE.putIfAbsent(it, clazz);
                }
            }
        }
    }

    /**
     * 根据属性检查是否已注册
     *
     * @param property 属性名
     * @return boolean
     */
    public static boolean contains(final String property) {
        return Strings.isNotWhitespace(property) && PROPS_CACHE.containsKey(property);
    }

    /**
     * 根据属性名获取注册的注解类
     *
     * @param property 属性
     * @return 注解类
     */
    public static Class<? extends Annotation> get(final String property) {
        if (Strings.isNotWhitespace(property)) {
            return PROPS_CACHE.get(property);
        }
        return null;
    }

}
