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

import java.io.Serializable;

/**
 * 响应结果
 *
 * @author wvkity
 * @created 2021/12/15
 * @since 1.0.0
 */
public interface Result extends Serializable {

    /**
     * 是否成功
     *
     * @return boolean
     */
    default boolean isSuccess() {
        return this.getCode() == Status.OK.getCode();
    }

    /**
     * Http状态码
     *
     * @return 状态码
     */
    int status();

    /**
     * 状态码
     *
     * @return 状态码
     */
    int getCode();

    /**
     * 描述信息
     *
     * @return 描述信息
     */
    String getMsg();

}
