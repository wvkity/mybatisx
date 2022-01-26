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
import io.github.mybatisx.core.criteria.query.Query;
import io.github.mybatisx.lang.Strings;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * 单字段分组
 *
 * @author wvkity
 * @created 2022/1/26
 * @since 1.0.0
 */
@SuperBuilder
public class SingleGroup extends AbstractGroup {

    private static final long serialVersionUID = -1893854387726463686L;

    /**
     * 字段名
     */
    @Getter
    protected final String column;

    public SingleGroup(Query<?> query, String alias, String column) {
        super(query, alias);
        this.column = column;
    }

    @Override
    public String getFragment() {
        if (Strings.isNotWhitespace(this.column)) {
            return this.render(this.column);
        }
        return Constants.EMPTY;
    }

    ///// static methods /////

    /**
     * 分组
     *
     * @param column 字段名
     * @return {@link SingleGroup}
     */
    public static SingleGroup group(final String column) {
        if (Strings.isNotWhitespace(column)) {
            return new SingleGroup(null, null, column);
        }
        return null;
    }

    /**
     * 分组
     *
     * @param query  {@link Query}
     * @param column 字段名
     * @return {@link SingleGroup}
     */
    public static SingleGroup group(final Query<?> query, final String column) {
        if (Strings.isNotWhitespace(column)) {
            return new SingleGroup(query, null, column);
        }
        return null;
    }

    /**
     * 分组
     *
     * @param alias  表别名
     * @param column 字段名
     * @return {@link SingleGroup}
     */
    public static SingleGroup group(final String alias, final String column) {
        if (Strings.isNotWhitespace(column)) {
            return new SingleGroup(null, alias, column);
        }
        return null;
    }

}
