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
 * 抽象异常
 *
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public abstract class AbstractResultError implements ResultError {

    /**
     * Http状态码
     */
    protected int status = Status.OK.getStatus();
    /**
     * 响应状态码
     */
    protected int code = Status.OK.getCode();
    /**
     * 响应描述信息
     */
    protected String msg = "OK";

    @Override
    public void error(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public void error(int status, int code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
