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
package io.github.mybatisx.sql;

import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数替换器
 *
 * @author wvkity
 * @created 2022/2/20
 * @since 1.0.0
 */
public class ParameterReplacer {

    /**
     * SQL语句
     */
    @Getter
    private final String originalSql;
    /**
     * 参数集合
     */
    private final Map<Integer, String> parameters;
    /**
     * 替换后的SQL
     */
    @Getter
    private String replaceAfter;
    /**
     * 是否已替换
     */
    private final AtomicBoolean replaced = new AtomicBoolean(false);
    /**
     * 参数匹配
     */
    private final Pattern parameterMatcher = Pattern.compile(".*#\\{((?!#\\{).)*}.*");
    /**
     * 参数还原正则表达式
     */
    private final Pattern parameterRestoreRegex = Pattern.compile("((?<!\\\\)(\\?))");
    /**
     * 参数字符串
     */
    private final String parameterPrefix = "(#\\{[^(#{)]+})";
    /**
     * 参数正则表达式
     */
    private final Pattern parameterRegex = Pattern.compile(parameterPrefix);

    public ParameterReplacer(String originalSql) {
        Objects.isTrue(Strings.isNotWhitespace(originalSql), "SQL query statements cannot be null");
        this.originalSql = originalSql;
        this.parameters = new HashMap<>();
    }

    /**
     * #占位符参数替换成问号占位符
     *
     * @return {@code this}
     */
    public ParameterReplacer replace() {
        final String _$originalSql = this.originalSql;
        final StringBuffer buffer = new StringBuffer(_$originalSql.length());
        if (this.parameterMatcher.matcher(_$originalSql).matches()) {
            int normal = -1;
            int index = normal;
            final Matcher matcher = this.parameterRegex.matcher(_$originalSql);
            while (matcher.find()) {
                final String replacement = matcher.group();
                matcher.appendReplacement(buffer, "?");
                index++;
                this.parameters.put(index, replacement);
            }
            matcher.appendTail(buffer);
            this.replaceAfter = buffer.toString();
            this.replaced.compareAndSet(false, index > normal);
        }
        return this;
    }

    /**
     * 还原SQL语句
     *
     * @param replacement 替换后的SQL语句
     * @return 还原后的SQL语句
     */
    public String restore(final String replacement) {
        if (this.hasParameter() && Strings.isNotWhitespace(replacement)) {
            final StringBuffer buffer = new StringBuffer(replacement.length() + 60);
            final Matcher matcher = this.parameterRestoreRegex.matcher(replacement);
            int index = 0;
            while (matcher.find()) {
                final String placeholder = this.parameters.get(index);
                matcher.appendReplacement(buffer, placeholder);
                index++;
            }
            matcher.appendTail(buffer);
            return buffer.toString();
        }
        return replacement;
    }

    /**
     * 是否已替换
     *
     * @return boolean
     */
    public boolean isReplaced() {
        return this.replaced.get();
    }

    /**
     * 是否存在参数
     *
     * @return boolean
     */
    public boolean hasParameter() {
        return !this.parameters.isEmpty();
    }

}
