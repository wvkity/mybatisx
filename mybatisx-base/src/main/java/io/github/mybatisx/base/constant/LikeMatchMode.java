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

import io.github.mybatisx.base.convert.Converter;

/**
 * Like模糊匹配策略
 *
 * @author wvkity
 * @created 2022/1/10
 * @since 1.0.0
 */
public enum LikeMatchMode implements Converter<String, String> {

    /**
     * Match the entire string to the pattern
     */
    EXACT() {
        @Override
        public String convert(String pattern) {
            return pattern;
        }
    },

    /**
     * Match the start of the string to the pattern
     */
    START() {
        @Override
        public String convert(String pattern) {
            return pattern + '%';
        }
    },

    /**
     * Match the end of the string to the pattern
     */
    END() {
        @Override
        public String convert(String pattern) {
            return '%' + pattern;
        }
    },

    /**
     * Match the pattern anywhere in the string
     */
    ANYWHERE() {
        @Override
        public String convert(String pattern) {
            return '%' + pattern + '%';
        }
    }
    
}
