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

import io.github.mybatisx.auditable.config.AutomaticAuditPropertyRegistry;
import io.github.mybatisx.auditable.matcher.AuditMatcher;
import io.github.mybatisx.auditable.matcher.NeverMatcher;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Types;
import io.github.mybatisx.reflect.FieldWrapper;

import java.lang.annotation.Annotation;

/**
 * 默认自动识别审计属性解析器
 *
 * @author wvkity
 * @created 2021/12/31
 * @since 1.0.0
 */
public class DefaultAuditPropertyAutoScanParser extends AbstractAuditPropertyParser<Class<? extends Annotation>> 
        implements AuditAutoScanParser {

    @Override
    public AuditMatcher parse(FieldWrapper field) {
        if (Objects.nonNull(field)) {
            final Class<? extends Annotation> clazz = AutomaticAuditPropertyRegistry.get(field.getName());
            if (Objects.nonNull(clazz)) {
                return this.parse(clazz, Class::isAnnotationPresent, Types::is, Class::getAnnotation);
            }
        }
        return NeverMatcher.of();
    }
    
    public static DefaultAuditPropertyAutoScanParser of() {
        return new DefaultAuditPropertyAutoScanParser();
    }
}
