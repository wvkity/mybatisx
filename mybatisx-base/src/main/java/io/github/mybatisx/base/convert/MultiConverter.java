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
package io.github.mybatisx.base.convert;

/**
 * 多参数转换器
 *
 * @param <F> 参数一类型
 * @param <S> 参数二类型
 * @param <T> 参数三类型
 * @param <U> 返回值类型
 * @author wvkity
 * @created 2022/2/24
 * @since 1.0.0
 */
public interface MultiConverter<F, S, T, U> {

    /**
     * 转换
     *
     * @param p1 参数1
     * @param p2 参数2
     * @param p3 参数3
     * @return 转换后的值
     */
    U convert(F p1, S p2, T p3);
}
