/*
 * Copyright (c) 2022-2023, wvkity(wvkity@gmail.com).
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
package io.github.mybatisx.pageable;

/**
 * 分页
 *
 * @param <T> 数据类型
 * @author wvkity
 * @created 2021/12/17
 * @since 1.0.0
 */
public class AdvDataPageRequest<T> extends AbstractAdvDataPageable<T> {

    private static final long serialVersionUID = -1157327912707500401L;

    public AdvDataPageRequest(String page) {
        super(page);
    }

    public AdvDataPageRequest(int page) {
        super(page);
    }

    public AdvDataPageRequest(String page, String size) {
        super(page, size);
    }

    public AdvDataPageRequest(int page, int size) {
        super(page, size);
    }

    ///// static methods /////

    public static <T> AdvDataPageRequest<T> of(final String page) {
        return new AdvDataPageRequest<>(page);
    }

    public static <T> AdvDataPageRequest<T> of(final int page) {
        return new AdvDataPageRequest<>(page);
    }

    public static <T> AdvDataPageRequest<T> of(final String page, final String size) {
        return new AdvDataPageRequest<>(page, size);
    }

    public static <T> AdvDataPageRequest<T> of(final int page, final int size) {
        return new AdvDataPageRequest<>(page, size);
    }
}
