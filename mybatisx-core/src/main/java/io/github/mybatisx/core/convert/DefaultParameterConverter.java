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
package io.github.mybatisx.core.convert;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 默认参数转换器
 *
 * @author wvkity
 * @created 2022/1/6
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class DefaultParameterConverter implements ParameterConverter {

    /**
     * 参数名前缀
     */
    protected static final String PARAM_NAME_PREFIX = "_v_idx_";
    /**
     * 参数名前缀长度
     */
    protected static final int PARAM_NAME_PREFIX_SIZE = PARAM_NAME_PREFIX.length();
    /**
     * 参数别名
     */
    protected static final String PARAM_ALIAS = Constants.PARAM_CRITERIA;
    /**
     * 参数值映射前缀
     */
    protected static final String PARAM_MAPPING_PREFIX = PARAM_ALIAS + ".paramValueMapping.";
    /**
     * 参数值映射前缀长度
     */
    protected static final int PARAM_MAPPING_PREFIX_SIZE = PARAM_MAPPING_PREFIX.length();
    /**
     * 序列
     */
    protected final AtomicInteger sequence;
    /**
     * 值映射
     */
    protected final Map<String, Object> valueMapping;

    @Override
    public String convert(String template, Object... args) {
        if (Strings.isNotWhitespace(template) && Objects.isNotEmpty(args)) {
            final int size = args.length;
            String source = template;
            if (size == 1) {
                final Object arg = args[0];
                final String paramName = PARAM_NAME_PREFIX + this.sequence.incrementAndGet();
                this.valueMapping.put(paramName, arg);
                return source.replace(PARAM_PLACEHOLDER_ZERO, (PARAM_MAPPING_PREFIX + paramName));
            } else {
                final StringBuilder paramNameSb = new StringBuilder(PARAM_NAME_PREFIX_SIZE + 3);
                paramNameSb.append(PARAM_NAME_PREFIX);
                final StringBuilder placeholderSb = new StringBuilder(7);
                placeholderSb.append("\\").append(SqlSymbol.START_BRACE);
                final StringBuilder mappingSb = new StringBuilder(PARAM_MAPPING_PREFIX_SIZE + PARAM_NAME_PREFIX_SIZE + 3);
                mappingSb.append(PARAM_MAPPING_PREFIX);
                for (int i = 0; i < size; i++) {
                    paramNameSb.append(this.sequence.incrementAndGet());
                    final String paramName = paramNameSb.toString();
                    placeholderSb.append(i).append(SqlSymbol.END_BRACE);
                    mappingSb.append(paramName);
                    source = source.replaceAll(placeholderSb.toString(), mappingSb.toString());
                    this.valueMapping.put(paramName, args[i]);
                    paramNameSb.delete(PARAM_NAME_PREFIX_SIZE, paramNameSb.length());
                    placeholderSb.delete(2, placeholderSb.length());
                    mappingSb.delete(PARAM_MAPPING_PREFIX_SIZE, mappingSb.length());
                }
            }
            return source;
        }
        return null;
    }

    @Override
    public List<String> converts(String template, Iterable<?> iterable) {
        if (Strings.isNotWhitespace(template) && Objects.nonNull(iterable)) {
            return StreamSupport.stream(iterable.spliterator(), false).map(it -> {
                final String paramName = PARAM_NAME_PREFIX + this.sequence.incrementAndGet();
                final Object value = it == null ? "null" : it;
                this.valueMapping.put(paramName, value);
                return template.replace(PARAM_PLACEHOLDER_ZERO, (PARAM_MAPPING_PREFIX + paramName));
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Map<String, String> converts(String template, Map<String, ?> args) {
        if (Strings.isNotWhitespace(template) && Objects.isNotEmpty(args)) {
            final Map<String, String> newArgs = new HashMap<>(args.size());
            for (Map.Entry<String, ?> it : args.entrySet()) {
                newArgs.put(it.getKey(), this.convert(template, it.getValue()));
            }
            return newArgs;
        }
        return new HashMap<>(0);
    }
}
