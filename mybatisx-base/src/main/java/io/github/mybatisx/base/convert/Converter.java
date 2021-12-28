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

import java.util.function.Function;

/**
 * 转换器
 *
 * @param <S> 源类型
 * @param <T> 目标类型
 * @author wvkity
 * @created 2021/12/28
 * @since 1.0.0
 */
@FunctionalInterface
public interface Converter<S, T> extends Function<S, T> {

    /**
     * 转换
     *
     * @param src 源对象
     * @return 目标对象
     */
    T convert(final S src);

    @Override
    default T apply(S s) {
        return this.convert(s);
    }

}
