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
package io.github.mybatisx.uid.snowflake;

import io.github.mybatisx.uid.Sequence;

/**
 * 雪花ID生成器
 *
 * @author wvkity
 * @created 2021/12/19
 * @since 1.0.0
 */
public interface SnowflakeSequence extends Sequence {

    /**
     * 设置是否使用缓存时刻
     *
     * @param useClock 是否使用缓存时刻
     * @return boolean
     */
    boolean setUseClock(boolean useClock);

    /**
     * 解析ID
     *
     * @param id ID
     * @return ID信息
     */
    Unique parse(final long id);

    /**
     * 解析ID
     *
     * @param id ID
     * @return ID信息
     */
    default String parseString(final long id) {
        return this.parse(id).toString();
    }
}
