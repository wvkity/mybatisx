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
package io.github.mybatisx.support.config;

import io.github.mybatisx.base.matcher.ClassMatcher;
import io.github.mybatisx.base.matcher.FieldMatcher;
import io.github.mybatisx.base.matcher.GetterMatcher;
import io.github.mybatisx.base.matcher.SetterMatcher;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 匹配器相关配置
 *
 * @author wvkity
 * @created 2021/12/30
 * @since 1.0.0
 */
@Getter
@Setter
@RequiredArgsConstructor
public class MatcherConfig {
    
    /**
     * 父类匹配器
     */
    private ClassMatcher classMatcher;
    /**
     * 属性匹配器
     */
    private FieldMatcher fieldMatcher;
    /**
     * get方法匹配器
     */
    private GetterMatcher getterMatcher;
    /**
     * set方法匹配器
     */
    private SetterMatcher setterMatcher;
    
    public static MatcherConfig of() {
        return new MatcherConfig();
    }
}
