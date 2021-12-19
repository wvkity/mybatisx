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
package io.github.mybatisx.uid.snowflake;

/**
 * 雪花算法ID异常
 *
 * @author wvkity
 * @created 2021/12/18
 * @since 1.0.0
 */
public class SnowflakeException extends RuntimeException {

    private static final long serialVersionUID = 1733911707194079179L;

    public SnowflakeException() {
        super();
    }

    public SnowflakeException(String message) {
        super(message);
    }

    public SnowflakeException(String message, Object... args) {
        super(String.format(message, args));
    }

    public SnowflakeException(Throwable cause) {
        super(cause);
    }

    public SnowflakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SnowflakeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
