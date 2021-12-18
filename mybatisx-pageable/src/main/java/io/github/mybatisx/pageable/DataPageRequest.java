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
public class DataPageRequest<T> extends AbstractDataPageable<T> {

    private static final long serialVersionUID = 2713050732304073205L;

    public DataPageRequest(String page) {
        super(page);
    }

    public DataPageRequest(int page) {
        super(page);
    }

    public DataPageRequest(String page, String size) {
        super(page, size);
    }

    public DataPageRequest(int page, int size) {
        super(page, size);
    }

    ///// static methods /////

    public static <T> DataPageRequest<T> of(final String page) {
        return new DataPageRequest<>(page);
    }

    public static <T> DataPageRequest<T> of(final int page) {
        return new DataPageRequest<>(page);
    }

    public static <T> DataPageRequest<T> of(final String page, final String size) {
        return new DataPageRequest<>(page, size);
    }

    public static <T> DataPageRequest<T> of(final int page, final int size) {
        return new DataPageRequest<>(page, size);
    }
}
