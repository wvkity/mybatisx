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
package io.github.mybatisx.lang;

import java.util.regex.Pattern;

/**
 * 正则表达式工具
 *
 * @author wvkity
 * @created 2021/12/16
 * @since 1.0.0
 */
public final class RegexHelper {

    private RegexHelper() {
    }

    public static final Pattern REGEX_NUMBER_WITH_TRAILING_ZEROS = Pattern.compile("\\.0*$");
    public static final Pattern REGEX_INTEGER = Pattern.compile("^([\\-+])?(0|[1-9]((_)?\\d)*)$");
    public static final Pattern REGEX_DECIMAL = Pattern.compile("^([\\-+])?(0|[1-9]((_)?\\d)*)(\\.\\d+)$");
    public static final Pattern REGEX_POSITIVE_INTEGER = Pattern.compile("^\\+?(0|[1-9]((_)?\\d)*)$");

    /**
     * 检查是否为整数字符串
     *
     * @param arg 字符串
     * @return boolean
     */
    public static boolean isInteger(final String arg) {
        return REGEX_INTEGER.matcher(arg).matches();
    }

    /**
     * 检查是否为正整数字符串
     *
     * @param arg 字符串
     * @return boolean
     */
    public static boolean isPositiveInteger(final String arg) {
        return REGEX_POSITIVE_INTEGER.matcher(arg).matches();
    }

    /**
     * 检查是否为小数字符串
     *
     * @param arg 字符串
     * @return boolean
     */
    public static boolean isDecimal(final String arg) {
        return REGEX_DECIMAL.matcher(arg).matches();
    }

}
