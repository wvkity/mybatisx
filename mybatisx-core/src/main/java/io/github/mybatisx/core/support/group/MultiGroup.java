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
package io.github.mybatisx.core.support.group;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.core.criteria.query.Query;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.util.Collections;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 多字段分组
 *
 * @author wvkity
 * @created 2022/1/26
 * @since 1.0.0
 */
@SuperBuilder
public class MultiGroup extends AbstractGroup {

    private static final long serialVersionUID = -4491595337415206318L;

    @Getter
    protected final List<String> columns;

    public MultiGroup(Query<?> query, String alias, List<String> columns) {
        super(query, alias);
        this.columns = columns;
    }

    @Override
    public String getFragment() {
        if (Collections.isNotEmpty(this.columns)) {
            return this.columns.stream().filter(Strings::isNotWhitespace).map(this::render)
                    .collect(Collectors.joining(SqlSymbol.COMMA_SPACE));
        }
        return Constants.EMPTY;
    }

    ///// static methods /////

    /**
     * 分组
     *
     * @param columns 字段列表
     * @return {@link MultiGroup}
     */
    public static MultiGroup group(final String[] columns) {
        return group(Arrays.asList(columns));
    }

    /**
     * 分组
     *
     * @param columns 字段列表
     * @return {@link MultiGroup}
     */
    public static MultiGroup group(final List<String> columns) {
        if (Collections.isNotEmpty(columns)) {
            return new MultiGroup(null, null, columns);
        }
        return null;
    }

    /**
     * 分组
     *
     * @param query   {@link Query}
     * @param columns 字段列表
     * @return {@link MultiGroup}
     */
    public static MultiGroup group(final Query<?> query, final String... columns) {
        return group(query, Arrays.asList(columns));
    }

    /**
     * 分组
     *
     * @param query   {@link Query}
     * @param columns 字段列表
     * @return {@link MultiGroup}
     */
    public static MultiGroup group(final Query<?> query, final List<String> columns) {
        if (Collections.isNotEmpty(columns)) {
            return new MultiGroup(query, null, columns);
        }
        return null;
    }

    /**
     * 分组
     *
     * @param alias   表别名
     * @param columns 字段列表
     * @return {@link MultiGroup}
     */
    public static MultiGroup group(final String alias, final String... columns) {
        return group(alias, Arrays.asList(columns));
    }

    /**
     * 分组
     *
     * @param alias   表别名
     * @param columns 字段列表
     * @return {@link MultiGroup}
     */
    public static MultiGroup group(final String alias, final List<String> columns) {
        if (Collections.isNotEmpty(columns)) {
            return new MultiGroup(null, alias, columns);
        }
        return null;
    }

}
