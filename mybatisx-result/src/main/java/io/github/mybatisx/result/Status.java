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
package io.github.mybatisx.result;

/**
 * 状态码
 *
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
public enum Status {

    /**
     * OK
     */
    OK(200, "成功"),
    /**
     * FAILURE
     */
    FAILURE(100000, "失败"),
    /**
     * UNAUTHORIZED
     */
    UNAUTHORIZED(400401, "未授权"),
    /**
     * INVALID TOKEN
     */
    INVALID_TOKEN(400402, "无效token"),
    /**
     * FORBIDDEN
     */
    FORBIDDEN(400403, "无效请求"),
    /**
     * NOT FOUND(
     */
    NOT_FOUND(400404, "未找到资源"),
    /**
     * TIMEOUT
     */
    TIMEOUT(400405, "请求超时"),
    /**
     * SERVER ERROR
     */
    SERVER_ERROR(500500, "服务器发生异常");

    /**
     * 状态码
     */
    final int code;
    /**
     * 描述
     */
    final String desc;

    Status(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
