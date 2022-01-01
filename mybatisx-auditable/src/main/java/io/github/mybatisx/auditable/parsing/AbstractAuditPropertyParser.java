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
package io.github.mybatisx.auditable.parsing;

import io.github.mybatisx.auditable.annotation.CreatedBy;
import io.github.mybatisx.auditable.annotation.CreatedByName;
import io.github.mybatisx.auditable.annotation.CreatedDate;
import io.github.mybatisx.auditable.annotation.Deletable;
import io.github.mybatisx.auditable.annotation.DeletedBy;
import io.github.mybatisx.auditable.annotation.DeletedByName;
import io.github.mybatisx.auditable.annotation.DeletedDate;
import io.github.mybatisx.auditable.annotation.Insertable;
import io.github.mybatisx.auditable.annotation.LastModifiedBy;
import io.github.mybatisx.auditable.annotation.LastModifiedByName;
import io.github.mybatisx.auditable.annotation.LastModifiedDate;
import io.github.mybatisx.auditable.annotation.Remark;
import io.github.mybatisx.auditable.annotation.Unique;
import io.github.mybatisx.auditable.annotation.Updatable;
import io.github.mybatisx.auditable.matcher.AuditMatcher;
import io.github.mybatisx.auditable.matcher.DefaultAuditMatcher;
import io.github.mybatisx.auditable.matcher.NeverMatcher;
import io.github.mybatisx.auditable.metadata.AuditMode;
import io.github.mybatisx.auditable.metadata.AuditType;
import io.github.mybatisx.lang.Objects;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * 抽象审计属性解析器
 *
 * @param <T> 泛型
 * @author wvkity
 * @created 2021/12/31
 * @since 1.0.0
 */
public abstract class AbstractAuditPropertyParser<T> {

    /**
     * 解析
     *
     * @param target  目标对象
     * @param matcher 匹配器
     * @param action  对比
     * @return {@link AuditMatcher}
     */
    protected AuditMatcher parse(final T target, final BiFunction<T, Class<? extends Annotation>, Boolean> matcher,
                                 BiFunction<T, Class<? extends Annotation>, Boolean> action,
                                 BiFunction<T, Class<? extends Annotation>, Annotation> converter) {
        if (Objects.nonNull(target)) {
            final DefaultAuditMatcher.DefaultAuditMatcherBuilder amb = DefaultAuditMatcher.builder();
            amb.matches(true);
            if (matcher.apply(target, Unique.class)) {
                amb.auditType(AuditType.UNIQUE)
                        .createdBy(action.apply(target, CreatedBy.class))
                        .lastModifiedBy(action.apply(target, LastModifiedBy.class))
                        .deletedBy(action.apply(target, DeletedBy.class));
            } else if (matcher.apply(target, Remark.class)) {
                amb.auditType(AuditType.REMARK)
                        .createdByName(action.apply(target, CreatedByName.class))
                        .lastModifiedByName(action.apply(target, LastModifiedByName.class))
                        .deletedByName(action.apply(target, DeletedByName.class));
            } else {
                amb.auditType(AuditType.DATE)
                        .createdDate(action.apply(target, CreatedDate.class))
                        .lastModifiedDate(action.apply(target, LastModifiedDate.class))
                        .deletedDate(action.apply(target, DeletedDate.class));
            }
            final Set<AuditMode> modes = new HashSet<>(3);
            Objects.ifNonNullThen(AuditMode.get(converter.apply(target, Insertable.class)), modes::add);
            Objects.ifNonNullThen(AuditMode.get(converter.apply(target, Updatable.class)), modes::add);
            Objects.ifNonNullThen(AuditMode.get(converter.apply(target, Deletable.class)), modes::add);
            amb.auditModes(modes);
            return amb.build();
        }
        return NeverMatcher.of();
    }
}
