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
package io.github.mybatisx.base.constant;

import io.github.mybatisx.lang.Strings;

/**
 * 字符串常量
 *
 * @author wvkity
 * @created 2021/12/25
 * @since 1.0.0
 */
public interface Constants {

    /**
     * 空字符串
     */
    String EMPTY = "";

    /**
     * 字符串null
     */
    String NULL = Strings.DEFAULT_STR_NULL;

    /**
     * 双反斜杠
     */
    String DOUBLE_BACKSLASH = "\\";

    /**
     * 下划线
     */
    String UNDER_LINE = "_";
    /**
     * 保持排序
     */
    String KEEP_ORDER_BY = "/*keep orderly*/";

    /**
     * 主键参数名
     */
    String PARAM_ID = "id";

    /**
     * 实体参数名
     */
    String PARAM_ENTITY = "entity";

    /**
     * 实体列表参数名
     */
    String PARAM_ENTITIES = "entities";

    /**
     * 条件参数名
     */
    String PARAM_CRITERIA = "criteria";

    /**
     * script标签开始标记
     */
    String SCRIPT_OPEN = "<script>";

    /**
     * script标签结束标记
     */
    String SCRIPT_CLOSE = "</script>";

}
