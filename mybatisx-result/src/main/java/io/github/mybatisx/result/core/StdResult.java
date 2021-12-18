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
package io.github.mybatisx.result.core;

import io.github.mybatisx.builder.Builder;
import io.github.mybatisx.builder.ObjectBuilder;
import io.github.mybatisx.result.DataResult;
import io.github.mybatisx.result.Status;

/**
 * 响应结果
 *
 * @param <T> 数据类型
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
public class StdResult<T> extends AbstractResult<T> implements DataResult<T> {

    private static final long serialVersionUID = -2018086332932859068L;

    public StdResult() {
    }

    public StdResult(T data) {
        this.data = data;
    }

    public StdResult(Status status) {
        this.error(status);
    }

    public StdResult(Throwable e) {
        this.error(e);
    }

    public StdResult(Status status, Throwable e) {
        this.error(status, e);
    }

    public StdResult(int code, Throwable e) {
        this.error(code, e);
    }

    public StdResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public StdResult(T data, String msg) {
        this.data = data;
        this.msg = msg;
    }

    public StdResult(T data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "StdResult{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    ///// static methods /////

    public static <T> Builder<StdResult<T>> create() {
        return new ObjectBuilder<>(StdResult::new);
    }

    public static <T> StdResult<T> of() {
        return new StdResult<>();
    }

    public static <T> StdResult<T> ok() {
        return of();
    }

    public static <T> StdResult<T> ok(final T data) {
        return new StdResult<>(data);
    }

    public static <T> StdResult<T> ok(final T data, final String msg) {
        return new StdResult<>(data, msg);
    }

    public static <T> StdResult<T> failure(final Status status) {
        return new StdResult<>(status);
    }

    public static <T> StdResult<T> failure(final Throwable e) {
        return new StdResult<>(e);
    }

    public static <T> StdResult<T> failure(final Status status, final String msg) {
        return new StdResult<>(status.getCode(), msg);
    }

    public static <T> StdResult<T> failure(final Status status, final Throwable e) {
        return new StdResult<>(status, e);
    }

    public static <T> StdResult<T> failure(final int code, final Throwable e) {
        return new StdResult<>(code, e);
    }

    public static <T> StdResult<T> failure(final int code, final String msg) {
        return new StdResult<>(code, msg);
    }

}
