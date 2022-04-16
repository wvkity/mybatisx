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

import io.github.mybatisx.result.error.Error;

import java.util.HashMap;
import java.util.Map;

/**
 * 状态码
 *
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
public enum Status implements Error {

    /**
     * ok
     */
    OK(200, 200, "成功"),
    /**
     * unauthorized
     */
    UNAUTHORIZED(401, 400401, "未授权"),
    /**
     * invalid token
     */
    INVALID_TOKEN(402, 400402, "无效token"),
    /**
     * forbidden
     */
    FORBIDDEN(403, 400403, "无效请求"),
    /**
     * not found
     */
    NOT_FOUND(404, 400404, "请求资源不存在"),
    /**
     * method not allowed
     */
    NOT_ALLOWED(405, 400405, "请求中存在禁用方法"),
    /**
     * timeout
     */
    TIMEOUT(408, 400408, "请求超时"),
    /**
     * server error
     */
    SERVER_ERROR(500, 500500, "系统繁忙，请稍后再试"),
    /**
     * Service Unavailable
     */
    SERVICE_UNAVAILABLE(503, 500503, "系统正在维护，暂不提供服务，请稍后再试"),
    /**
     * FAILURE
     */
    FAILURE(510, 500510, "请求失败");

    /**
     * http状态
     */
    final int status;
    /**
     * 状态码
     */
    final int code;
    /**
     * 描述
     */
    final String msg;

    Status(int status, int code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private static final Map<Integer, Status> CACHE = new HashMap<>(Status.values().length * 2);

    static {
        for (Status it : Status.values()) {
            CACHE.put(it.getStatus(), it);
            CACHE.put(it.getCode(), it);
        }
    }

    /**
     * 根据状态值获取{@link Status}
     *
     * @param status 状态值
     * @return {@link Status}
     */
    public static Status getStatus(final int status) {
        return CACHE.getOrDefault(status, FAILURE);
    }

}
