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

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.ParamMode;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Regex;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.util.Collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 占位符工具
 *
 * @author wvkity
 * @created 2022/1/10
 * @since 1.0.0
 */
public class PlaceholderHelper {

    /**
     * 数字占位符正则表达式字符串
     */
    public static final String REGEX_DIGIT_STRING = "((?<!\\\\)\\?(\\d+))";
    /**
     * 数字占位符参数正则表达式
     */
    public static final Pattern REGEX_DIGIT = Pattern.compile(REGEX_DIGIT_STRING);
    /**
     * 检查数字占位符参数正则表达式
     */
    public static final Pattern REGEX_DIGIT_CHECK = Pattern.compile(".*((?<!\\\\)\\?(\\d+)).*");
    /**
     * 单词占位符参数正则表达式
     */
    public static final Pattern REGEX_WORD = Pattern.compile("((?<!\\\\):(\\w+))");
    /**
     * 检查单词占位符参数正则表达式
     */
    public static final Pattern REGEX_WORD_CHECK = Pattern.compile(".*((?<!\\\\):(\\w+)).*");

    /**
     * 占位符替换
     *
     * @param template 模板
     * @param arg      参数
     * @return 替换结果
     */
    public String replace(final String template, final Object arg) {
        return this.replace(template, ParamMode.SINGLE, arg);
    }

    /**
     * 占位符替换
     *
     * @param template 模板
     * @param args     参数
     * @return 替换结果
     */
    public String replace(final String template, final Object... args) {
        return this.replace(template, Objects.objectAsList(args));
    }

    /**
     * 占位符替换
     *
     * @param template 模板
     * @param args     参数
     * @return 替换结果
     */
    public String replace(final String template, final Iterable<?> args) {
        return this.replace(template, ParamMode.MULTIPLE, args);
    }

    /**
     * 占位符替换
     *
     * @param template 模板
     * @param args     参数
     * @return 替换结果
     */
    public String replace(final String template, final Map<String, ?> args) {
        return this.replace(template, ParamMode.MAP, args);
    }

    /**
     * 占位符替换
     *
     * @param template  模板
     * @param paramMode 参数类型
     * @param arg       参数
     * @return 替换结果
     */
    protected String replace(final String template, final ParamMode paramMode, final Object arg) {
        if (Strings.isWhitespace(template) || arg == null) {
            return template;
        }
        final boolean isDigitMatches = REGEX_DIGIT_CHECK.matcher(template).matches();
        final boolean isWordMatches = REGEX_WORD_CHECK.matcher(template).matches();
        if (isDigitMatches || isWordMatches) {
            final List<Object> args = Objects.toList(arg);
            if (Collections.isEmpty(args)) {
                return template;
            }
            final boolean isMap = paramMode == ParamMode.MAP;
            String source = template;
            final Matcher matcher;
            if (!isMap || isDigitMatches) {
                if (paramMode == ParamMode.MULTIPLE && PlaceholderHelper.isOnlyOnce(source)) {
                    return source.replaceAll(REGEX_DIGIT_STRING,
                            this.toString(Collections.isPureType(args) ? args : args.get(0)));
                } else {
                    final int size = args.size() - 1;
                    matcher = REGEX_DIGIT.matcher(source);
                    while (matcher.find()) {
                        final int i = this.toInt(matcher.group(2));
                        final Object value = (i < 0 || i > size) ? null : args.get(i);
                        source = this.replace(matcher, source, value);
                    }
                }
            } else {
                final int size = args.size() - 1;
                matcher = REGEX_WORD.matcher(source);
                final Map<?, ?> map = (Map<?, ?>) arg;
                while (matcher.find()) {
                    final String key = matcher.group(2);
                    Object value = null;
                    if (map.containsKey(key)) {
                        value = map.get(key);
                    } else if (Regex.isInteger(key)) {
                        final int i = this.toInt(matcher.group(2));
                        value = (i < 0 || i > size) ? null : args.get(i);
                    }
                    source = this.replace(matcher, source, value);
                }
            }
            return source;
        }
        return template;
    }

    /**
     * 替换
     *
     * @param matcher {@link Matcher}
     * @param source  字符串
     * @param arg     参数值
     * @return 替换后的字符串
     */
    protected String replace(final Matcher matcher, final String source, final Object arg) {
        final String group = matcher.group();
        final String regex;
        if (group.startsWith(SqlSymbol.QUESTION_MARK)) {
            regex = Constants.DOUBLE_BACKSLASH + group;
        } else {
            regex = group;
        }
        return source.replaceFirst(regex, this.toString(arg));
    }

    /**
     * 将值转成字符串值
     *
     * @param arg 值
     * @return 字符串值
     */
    protected String toString(final Object arg) {
        if (arg != null) {
            final Class<?> clazz = arg.getClass();
            if (clazz.isArray()) {
                return Arrays.stream((Object[]) arg).map(this::ifNull)
                        .collect(Collectors.joining(SqlSymbol.COMMA_SPACE));
            } else if (Objects.isAssignable(Iterable.class, clazz)) {
                if (arg instanceof Collection) {
                    return ((Collection<?>) arg).stream().map(this::ifNull)
                            .collect(Collectors.joining(SqlSymbol.COMMA_SPACE));
                }
                return StreamSupport.stream(((Iterable<?>) arg).spliterator(), false)
                        .map(this::ifNull).collect(Collectors.joining(SqlSymbol.COMMA_SPACE));
            } else if (Objects.isAssignable(Map.class, clazz)) {
                return ((Map<?, ?>) arg).values().stream().map(this::ifNull)
                        .collect(Collectors.joining(SqlSymbol.COMMA_SPACE));
            }
            return arg.toString();
        }
        return Constants.NULL;
    }

    /**
     * 如果参数为null则返回"null"
     *
     * @param arg 参数值
     * @return 字符串
     */
    protected String ifNull(final Object arg) {
        return arg == null ? Constants.NULL : arg.toString();
    }

    /**
     * 字符串数字转整数
     *
     * @param arg 字符串数字
     * @return 整数
     */
    protected int toInt(final String arg) {
        return Strings.isNotWhitespace(arg) && Regex.isInteger(arg) ? Integer.parseInt(arg) : -1;
    }

    /**
     * 检查数字占位符是否只出现一次
     *
     * @param source 字符串
     * @return boolean
     */
    public static boolean isOnlyOnce(final String source) {
        String temp = source;
        final Matcher matcher = REGEX_DIGIT.matcher(temp);
        int count = 0;
        final StringBuilder sb = new StringBuilder(6);
        sb.append(Constants.DOUBLE_BACKSLASH);
        while (matcher.find()) {
            final String group = sb.append(matcher.group()).toString();
            temp = temp.replaceFirst(group, SqlSymbol.QUESTION_MARK);
            count++;
            if (count > 1) {
                return false;
            }
            sb.delete(2, sb.length());
        }
        return true;
    }
}
