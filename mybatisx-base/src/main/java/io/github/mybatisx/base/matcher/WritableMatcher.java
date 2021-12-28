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

import io.github.mybatisx.reflect.Reflections;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.lang.reflect.Method;

/**
 * set方法匹配器
 *
 * @author wvkity
 * @created 2021/12/25
 * @since 1.0.0
 */
public class WritableMatcher extends MethodMatcher implements SetterMatcher {

    @Override
    public boolean matches(Method method) {
        return super.matches(method) && Reflections.isSetter(method) 
                && Reflections.isValidProperty(PropertyNamer.methodToProperty(method.getName()));
    }
}
