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

import io.github.mybatisx.result.DataResult;
import io.github.mybatisx.result.error.AbstractError;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 抽象响应结果集
 *
 * @param <T> 数据类型
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public abstract class AbstractResult<T> extends AbstractError implements DataResult<T> {

    /**
     * 数据
     */
    protected T data;
    /**
     * 当前时间戳
     */
    protected final long timestamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

    @Override
    public T getData() {
        return this.data;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 获取时间戳
     *
     * @return 时间戳
     */
    public long getTimestamp() {
        return this.timestamp;
    }


    public String getCreateAt() {
        return Long.toString(this.timestamp);
    }

}
