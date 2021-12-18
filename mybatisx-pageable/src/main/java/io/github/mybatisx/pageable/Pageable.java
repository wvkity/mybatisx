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
package io.github.mybatisx.pageable;

import java.io.Serializable;

/**
 * 分页
 *
 * @author wvkity
 * @created 2021/12/17
 * @since 1.0.0
 */
public interface Pageable extends Serializable {

    /**
     * 当前页
     *
     * @return 当前页
     */
    int getPage();

    /**
     * 每页数目
     *
     * @return 每页数目
     */
    int getSize();

    /**
     * 偏移量值
     *
     * @return 偏移量
     */
    int getOffset();

    /**
     * 获取总页数
     *
     * @return 总页数
     */
    int getTotals();

    /**
     * 总记录数
     *
     * @return 总记录数
     */
    long getRecords();

    /**
     * 设置总记录数
     *
     * @param records 总记录数
     */
    void setRecords(final long records);

    /**
     * 是否存在上一页
     *
     * @return boolean
     */
    boolean isHasPrev();

    /**
     * 是否存在下一页
     *
     * @return boolean
     */
    boolean isHasNext();

}
