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
import io.github.mybatisx.result.error.Error;

/**
 * 响应结果
 *
 * @param <T> 数据类型
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
public class PlainResult<T> extends AbstractResult<T> implements DataResult<T> {

    private static final long serialVersionUID = -2018086332932859068L;

    public PlainResult() {
    }

    public PlainResult(T data) {
        this.data = data;
    }

    public PlainResult(Error error) {
        this.error(error);
    }

    public PlainResult(Throwable e) {
        this.error(e);
    }

    public PlainResult(Error error, Throwable e) {
        this.error(error, e);
    }

    public PlainResult(int code, Throwable e) {
        this.error(code, e);
    }

    public PlainResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public PlainResult(T data, String msg) {
        this.data = data;
        this.msg = msg;
    }

    public PlainResult(T data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "PlainResult{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    ///// static methods /////

    public static <T> Builder<PlainResult<T>> create() {
        return new ObjectBuilder<>(PlainResult::new);
    }

    public static <T> PlainResult<T> of() {
        return new PlainResult<>();
    }

    public static <T> PlainResult<T> ok() {
        return of();
    }

    public static <T> PlainResult<T> ok(final T data) {
        return new PlainResult<>(data);
    }

    public static <T> PlainResult<T> ok(final T data, final String msg) {
        return new PlainResult<>(data, msg);
    }

    public static <T> PlainResult<T> failure(final Error error) {
        return new PlainResult<>(error);
    }

    public static <T> PlainResult<T> failure(final Throwable e) {
        return new PlainResult<>(e);
    }

    public static <T> PlainResult<T> failure(final Error error, final String msg) {
        return new PlainResult<>(error.getCode(), msg);
    }

    public static <T> PlainResult<T> failure(final Error error, final Throwable e) {
        return new PlainResult<>(error, e);
    }

    public static <T> PlainResult<T> failure(final int code, final Throwable e) {
        return new PlainResult<>(code, e);
    }

    public static <T> PlainResult<T> failure(final int code, final String msg) {
        return new PlainResult<>(code, msg);
    }

}
