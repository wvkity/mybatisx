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
package io.github.mybatisx.auditable.metadata;

import io.github.mybatisx.auditable.annotation.Deletable;
import io.github.mybatisx.auditable.annotation.Insertable;
import io.github.mybatisx.auditable.annotation.Updatable;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 审计模式
 *
 * @author wvkity
 * @created 2021/12/31
 * @since 1.0.0
 */
public enum AuditMode {

    /**
     * 保存
     */
    INSERTABLE(Insertable.class),
    /**
     * 更新
     */
    UPDATABLE(Updatable.class),
    /**
     * 删除
     */
    DELETABLE(Deletable.class);

    final Class<? extends Annotation> mode;

    AuditMode(Class<? extends Annotation> target) {
        this.mode = target;
    }

    public Class<? extends Annotation> getMode() {
        return mode;
    }

    private static final Map<Class<? extends Annotation>, AuditMode> CACHE = Arrays.stream(AuditMode.values())
            .collect(Collectors.toMap(AuditMode::getMode, Function.identity()));

    public static <T extends Annotation> AuditMode get(final T target) {
        if (target != null) {
            return CACHE.get(target.annotationType());
        }
        return null;
    }
    
    public static AuditMode get(final Class<? extends Annotation> clazz) {
        if (clazz != null) {
            return CACHE.get(clazz);
        }
        return null;
    }

}
