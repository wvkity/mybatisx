/*
 * Copyright (c) 2020, wvkity(wvkity@gmail.com).
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
package io.github.mybatisx.base.naming;

/**
 * 命名策略转换异常
 *
 * @author wvkity
 * @created 2020-10-04
 * @since 1.0.0
 */
public class NamingStrategyConvertException extends RuntimeException {

    private static final long serialVersionUID = 6224300994770030115L;

    public NamingStrategyConvertException() {
        super();
    }

    public NamingStrategyConvertException(String message) {
        super(message);
    }

    public NamingStrategyConvertException(Throwable cause) {
        super(cause);
    }

    public NamingStrategyConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public NamingStrategyConvertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
