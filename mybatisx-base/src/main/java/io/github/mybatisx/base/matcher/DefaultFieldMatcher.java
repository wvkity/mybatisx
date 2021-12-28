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
package io.github.mybatisx.base.matcher;

import io.github.mybatisx.annotation.Column;
import io.github.mybatisx.annotation.Extra;
import io.github.mybatisx.annotation.Transient;
import io.github.mybatisx.base.constant.Jpa;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Types;
import io.github.mybatisx.reflect.Reflections;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

/**
 * 属性匹配器
 *
 * @author wvkity
 * @created 2021/12/25
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
public class DefaultFieldMatcher implements FieldMatcher {

    /**
     * 使用简单类型
     */
    private final boolean useSimpleType;
    /**
     * 枚举类转简单类型
     */
    private final boolean enumAsSimpleType;
    /**
     * 支持JPA注解
     */
    private final boolean jpaSupport;

    @Override
    public boolean matches(Field field) {
        if (Objects.nonNull(field)) {
            return this.notMatchStaticAndFinal(field) && !Reflections.isMatches(field, Transient.class) &&
                    !(this.jpaSupport && Reflections.isMatches(field, Jpa.TRANSIENT)) &&
                    ((this.useSimpleType && Types.isSimpleType(field.getType())) ||
                            (Reflections.isMatches(field, Column.class) || Reflections.isMatches(field, Extra.class) ||
                                    (this.jpaSupport && Reflections.isMatches(field, Jpa.COLUMN, Reflections.METADATA_ANNOTATION_FILTER))) ||
                            (this.enumAsSimpleType && Objects.isAssignable(Enum.class, field.getType())));
        }
        return false;
    }

    private boolean notMatchStaticAndFinal(final Member member) {
        return !Modifier.isStatic(member.getModifiers()) && !Modifier.isFinal(member.getModifiers());
    }

    public static DefaultFieldMatcher of(final boolean useSimpleType, final boolean enumAsSimpleType,
                                         final boolean jpaSupport) {
        return new DefaultFieldMatcher(useSimpleType, enumAsSimpleType, jpaSupport);
    }
}
