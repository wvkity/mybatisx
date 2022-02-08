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
 * @author wvkity
 * @created 2021/12/17
 * @since 1.0.0
 */
public class ProPageRequest extends AbstractProPageable {

    private static final long serialVersionUID = -3108391406432060982L;

    public ProPageRequest(String page) {
        super(page);
    }

    public ProPageRequest(int page) {
        super(page);
    }

    public ProPageRequest(String page, String size) {
        super(page, size);
    }

    public ProPageRequest(int page, int size) {
        super(page, size);
    }

    ///// static methods /////

    public static ProPageRequest of(final String page) {
        return new ProPageRequest(page);
    }

    public static ProPageRequest of(final int page) {
        return new ProPageRequest(page);
    }

    public static ProPageRequest of(final String page, final String size) {
        return new ProPageRequest(page, size);
    }

    public static ProPageRequest of(final int page, final int size) {
        return new ProPageRequest(page, size);
    }
}
