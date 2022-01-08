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
package io.github.mybatisx.lambda;

/**
 * Lambda对象元数据接口
 *
 * @author wvkity
 * @created 2022/1/8
 * @since 1.0.0
 */
public interface LambdaMetadata {

    /**
     * 获取实现方法的名称
     *
     * @return 实现方法的名称
     */
    String getImplMethodName();

    /**
     * 获取实例化该方法的类
     *
     * @return 实例化该方法的类
     */
    Class<?> getImplClass();
}
