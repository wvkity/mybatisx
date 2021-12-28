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

import io.github.mybatisx.annotation.Entity;
import io.github.mybatisx.base.constant.Jpa;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Types;
import io.github.mybatisx.reflect.Reflections;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 类匹配器
 *
 * @author wvkity
 * @created 2021/12/25
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
public class DefaultClassMatcher implements ClassMatcher {

    /**
     * 是否支持JPA注解
     */
    private final boolean jpaSupport;

    @Override
    public boolean matches(Class<?> clazz) {
        if (Objects.nonNull(clazz)) {
            return !(Types.isObject(clazz) || Types.isSerializable(clazz) || Types.isAnnotation(clazz))
                    && (!(Objects.isCollection(clazz) || Objects.isMap(clazz)) 
                    || Reflections.isMatches(clazz, Entity.class)
                    || (this.jpaSupport && Reflections.isMatches(clazz, Jpa.ENTITY, Reflections.METADATA_ANNOTATION_FILTER)));
        }
        return false;
    }

    public static DefaultClassMatcher of(final boolean jpaSupport) {
        return new DefaultClassMatcher(jpaSupport);
    }
}
