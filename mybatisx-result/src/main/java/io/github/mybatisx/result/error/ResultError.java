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
package io.github.mybatisx.result.error;

import io.github.mybatisx.result.Status;

/**
 * 异常接口
 *
 * @author wvkity
 * @created 2021/12/15
 * @since 1.0.0
 */
public interface ResultError extends Error {

    /**
     * 设置异常信息
     *
     * @param error {@link Error}
     */
    default void error(final Error error) {
        this.error(error.status(), error.getCode(), error.getMsg());
    }

    /**
     * 设置异常信息
     *
     * @param msg 异常描述信息
     */
    default void error(final String msg) {
        this.error(Status.FAILURE.getCode(), msg);
    }

    /**
     * 设置异常信息
     *
     * @param e {@link Throwable}
     */
    default void error(final Throwable e) {
        this.error(Status.FAILURE, e);
    }

    /**
     * 设置异常信息
     *
     * @param error {@link Error}
     * @param e     {@link Throwable}
     */
    default void error(final Error error, final Throwable e) {
        this.error(error.status(), error.getCode(), e);
    }

    /**
     * 设置异常信息
     *
     * @param code 状态码
     * @param e    {@link Throwable}
     */
    default void error(final int code, final Throwable e) {
        this.error(code, e.getMessage());
    }

    /**
     * 设置异常信息
     *
     * @param code 状态码
     * @param msg  异常详细信息
     */
    void error(final int code, final String msg);

    /**
     * 设置异常信息
     *
     * @param status Http状态码
     * @param code   状态码
     * @param e      {@link Throwable}
     */
    default void error(final int status, final int code, final Throwable e) {
        this.error(status, code, e.getMessage());
    }

    /**
     * 设置异常信息
     *
     * @param status Http状态码
     * @param code   状态码
     * @param msg    异常详细信息
     */
    void error(final int status, final int code, final String msg);

    /**
     * 设置Http状态码
     *
     * @param status Http状态码
     */
    void setStatus(final int status);

    /**
     * 设置响应状态码
     *
     * @param code 状态码
     */
    void setCode(final int code);

    /**
     * 设置异常信息
     *
     * @param msg 异常描述信息
     */
    void setMsg(final String msg);

}
