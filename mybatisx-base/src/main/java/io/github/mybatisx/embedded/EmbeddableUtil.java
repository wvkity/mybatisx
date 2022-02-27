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
package io.github.mybatisx.embedded;

import io.github.mybatisx.base.constant.Constants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * @author wvkity
 * @created 2022/2/27
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmbeddableUtil {

    /**
     * 获取{@link EmbeddableResult}对象
     *
     * @param parameterObject 参数对象
     * @return {@link Optional}
     */
    @SuppressWarnings({"unchecked"})
    public static Optional<EmbeddableResult> optional(final Object parameterObject) {
        if (parameterObject instanceof Map) {
            final Map<String, Object> paramMap = (Map<String, Object>) parameterObject;
            if (paramMap.containsKey(Constants.PARAM_CRITERIA)) {
                final Object criteriaObject = paramMap.get(Constants.PARAM_CRITERIA);
                if (criteriaObject instanceof EmbeddableResult) {
                    return Optional.of((EmbeddableResult) criteriaObject);
                }
            }
            final Collection<Object> values = paramMap.values();
            for (Object it : values) {
                if (it instanceof EmbeddableResult) {
                    return Optional.of((EmbeddableResult) it);
                }
            }
        } else if (parameterObject instanceof EmbeddableResult) {
            return Optional.of((EmbeddableResult) parameterObject);
        }
        return Optional.empty();
    }
}
