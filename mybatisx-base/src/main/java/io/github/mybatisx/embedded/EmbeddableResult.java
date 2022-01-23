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

import java.util.Map;

/**
 * 嵌入结果
 *
 * @author wvkity
 * @created 2022/1/16
 * @since 1.0.0
 */
public interface EmbeddableResult {

    /**
     * 获取定义的resultMap标识
     *
     * @return resultMap标识
     */
    String getResultMap();

    /**
     * 设置resultMap标识
     *
     * @param resultMap resultMap标识
     * @return {@code this}
     */
    EmbeddableResult setResultMap(final String resultMap);

    /**
     * 获取自定义返回值类型
     *
     * @return 返回值类型
     */
    Class<?> getResultType();

    /**
     * 设置返回值类型
     *
     * @param resultType 返回值类型
     * @return {@code this}
     */
    EmbeddableResult setResultType(final Class<?> resultType);

    /**
     * 获取Map类型返回值指定的键
     *
     * @return 键
     */
    String getMapKey();

    /**
     * 设置Map类型返回值的键
     *
     * @param mapKey 键
     * @return {@code this}
     */
    EmbeddableResult setMapKey(final String mapKey);

    /**
     * 获取Map实现类
     *
     * @return Map实现类
     */
    @SuppressWarnings({"rawtypes"})
    Class<? extends Map> getMapType();

    /**
     * 设置Map实现类
     *
     * @param mapType Map实现类
     * @return {@code this}
     */
    @SuppressWarnings({"rawtypes"})
    EmbeddableResult setMapType(final Class<? extends Map> mapType);

}
