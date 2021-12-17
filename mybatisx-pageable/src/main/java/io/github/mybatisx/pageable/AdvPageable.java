/*
 * Copyright (c) 2021-2023, wvkity(wvkity@gmail.com).
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

/**
 * 分页
 *
 * @author wvkity
 * @created 2021/12/17
 * @since 1.0.0
 */
public interface AdvPageable extends Pageable {

    /**
     * 显示页码数
     *
     * @return 页码数
     */
    int getDisplay();

    /**
     * 设置显示页码数
     *
     * @param display 页码数
     */
    void setDisplay(final int display);

    /**
     * 起始页码
     *
     * @return 起始页码
     */
    int getStart();

    /**
     * 结束页码
     *
     * @return 结束页码
     */
    int getEnd();

}
