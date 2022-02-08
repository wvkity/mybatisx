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
package io.github.mybatisx.pageable;

/**
 * 分页
 *
 * @param <T> 数据类型
 * @author wvkity
 * @created 2021/12/17
 * @since 1.0.0
 */
public class ProDataPageRequest<T> extends AbstractProDataPageable<T> {

    private static final long serialVersionUID = -1157327912707500401L;

    public ProDataPageRequest(String page) {
        super(page);
    }

    public ProDataPageRequest(int page) {
        super(page);
    }

    public ProDataPageRequest(String page, String size) {
        super(page, size);
    }

    public ProDataPageRequest(int page, int size) {
        super(page, size);
    }

    ///// static methods /////

    public static <T> ProDataPageRequest<T> of(final String page) {
        return new ProDataPageRequest<>(page);
    }

    public static <T> ProDataPageRequest<T> of(final int page) {
        return new ProDataPageRequest<>(page);
    }

    public static <T> ProDataPageRequest<T> of(final String page, final String size) {
        return new ProDataPageRequest<>(page, size);
    }

    public static <T> ProDataPageRequest<T> of(final int page, final int size) {
        return new ProDataPageRequest<>(page, size);
    }
}
