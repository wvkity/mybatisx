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

import io.github.mybatisx.matcher.Matcher;

import java.lang.reflect.Method;

/**
 * 方法匹配器
 *
 * @author wvkity
 * @created 2021/12/25
 * @since 1.0.0
 */
public class MethodMatcher implements Matcher<Method> {

    @Override
    public boolean matches(Method method) {
        return false;
    }
}
