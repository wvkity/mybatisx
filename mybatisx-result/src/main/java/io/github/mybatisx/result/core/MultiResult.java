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
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.result.Result;
import io.github.mybatisx.result.error.Error;

import java.util.HashMap;
import java.util.Map;

/**
 * 多值响应结果
 *
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
public class MultiResult extends AbstractMultiResult implements Result {

    private static final long serialVersionUID = 6504217365619049485L;

    public MultiResult() {
        this(8);
    }

    public MultiResult(int size) {
        if (size < 1) {
            size = 8;
        }
        this.data = new HashMap<>(size);
    }

    public MultiResult(Map<?, ?> data) {
        this(Objects.size(data));
        this.putAll(data);
    }

    public MultiResult(Error error) {
        this();
        this.error(error);
    }

    public MultiResult(Throwable e) {
        this();
        this.error(e);
    }

    public MultiResult(Error error, Throwable e) {
        this();
        this.error(error, e);
    }

    public MultiResult(int code, Throwable e) {
        this();
        this.error(code, e);
    }

    public MultiResult(int code, String msg) {
        this();
        this.code = code;
        this.msg = msg;
    }

    public MultiResult(Map<?, ?> data, String msg) {
        this(Objects.size(data));
        this.msg = msg;
        this.putAll(data);
    }

    public MultiResult(Map<?, ?> data, int code, String msg) {
        this(Objects.size(data));
        this.code = code;
        this.msg = msg;
        this.putAll(data);
    }


    ///// static methods /////

    public static Builder<MultiResult> create() {
        return new ObjectBuilder<>(MultiResult::new);
    }

    public static MultiResult of() {
        return new MultiResult();
    }

    public static MultiResult ok() {
        return of();
    }

    public static MultiResult ok(final Map<?, ?> data) {
        return new MultiResult(data);
    }

    public static MultiResult ok(final Map<?, ?> data, final String msg) {
        return new MultiResult(data, msg);
    }

    public static MultiResult failure(final Error error) {
        return new MultiResult(error);
    }

    public static MultiResult failure(final Throwable e) {
        return new MultiResult(e);
    }

    public static MultiResult failure(final Error error, final String msg) {
        return new MultiResult(error.getCode(), msg);
    }

    public static MultiResult failure(final Error error, final Throwable e) {
        return new MultiResult(error, e);
    }

    public static MultiResult failure(final int code, final Throwable e) {
        return new MultiResult(code, e);
    }

    public static MultiResult failure(final int code, final String msg) {
        return new MultiResult(code, msg);
    }

}
