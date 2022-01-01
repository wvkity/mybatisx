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

import io.github.mybatisx.auditable.annotation.Date;
import io.github.mybatisx.auditable.annotation.NotAuditing;
import io.github.mybatisx.auditable.annotation.Remark;
import io.github.mybatisx.auditable.annotation.Unique;
import io.github.mybatisx.auditable.matcher.AuditMatcher;
import io.github.mybatisx.auditable.matcher.NeverMatcher;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.reflect.Annotated;
import io.github.mybatisx.reflect.FieldWrapper;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * 默认审计属性解析器
 *
 * @author wvkity
 * @created 2021/12/31
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class DefaultAuditPropertyParser extends AbstractAuditPropertyParser<FieldWrapper> implements AuditParser {

    /**
     * 是否开启自动扫描解析
     */
    private final boolean autoScan;
    /**
     * 自动扫描解析器
     */
    private final AuditAutoScanParser autoScanParser;

    @Override
    public AuditMatcher parse(FieldWrapper field) {
        if (Objects.nonNull(field)) {
            if (field.isMatches(NotAuditing.class)) {
                return NeverMatcher.of();
            } else {
                if (field.isMatches(Arrays.asList(Unique.class, Remark.class, Date.class))) {
                    return this.parse(field, Annotated::isMatches, Annotated::isMatches, Annotated::getAnnotation);
                } else if (this.autoScan && this.autoScanParser != null) {
                    return this.autoScanParser.parse(field);
                }
            }
        }
        return NeverMatcher.of();
    }

    public static DefaultAuditPropertyParser of(final boolean autoScan, final AuditAutoScanParser autoScanParser) {
        return new DefaultAuditPropertyParser(autoScan, autoScanParser);
    }
}
