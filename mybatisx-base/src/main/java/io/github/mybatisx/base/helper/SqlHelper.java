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
package io.github.mybatisx.base.helper;

import io.github.mybatisx.lang.Strings;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

/**
 * SQL语句工具
 *
 * @author wvkity
 * @created 2022/1/7
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SqlHelper {

    /**
     * and/or关键字开头正则表达式字符串
     */
    public static final String REGEX_AND_OR_STRING = "^(?i)(\\s*and\\s+|\\s*or\\s+)(.*)";
    /**
     * and/or关键字正则表达式
     */
    public static final Pattern REGEX_AND_OR = Pattern.compile(REGEX_AND_OR_STRING, Pattern.CASE_INSENSITIVE);

    /**
     * 检查字符串是否以and/or开头
     *
     * @param arg 待检查字符串
     * @return boolean
     */
    public static boolean startWithsAndOr(final String arg) {
        return Strings.isNotWhitespace(arg) && REGEX_AND_OR.matcher(arg).matches();
    }

    /**
     * 以AND或OR开头的字符串移除其AND或OR字符串
     *
     * @param arg 待检查移除字符串
     * @return 新的字符串
     */
    public static String startWithsAndOrRemove(final String arg) {
        if (startWithsAndOr(arg)) {
            return arg.replaceFirst(REGEX_AND_OR_STRING, "$2");
        }
        return arg;
    }

}
