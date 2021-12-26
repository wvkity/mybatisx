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
package io.github.mybatisx.base.exception;

/**
 * MyBatisX异常
 *
 * @author wvkity
 * @created 2020-10-04
 * @since 1.0.0
 */
public class MyBatisException extends RuntimeException {

    private static final long serialVersionUID = -6444616123358626573L;

    public MyBatisException() {
        super();
    }

    public MyBatisException(String message) {
        super(message);
    }

    public MyBatisException(Throwable cause) {
        super(cause);
    }

    public MyBatisException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyBatisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
