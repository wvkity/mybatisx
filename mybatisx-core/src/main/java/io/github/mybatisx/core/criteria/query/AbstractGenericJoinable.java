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
package io.github.mybatisx.core.criteria.query;

import io.github.mybatisx.base.constant.Join;
import io.github.mybatisx.base.exception.MyBatisException;
import io.github.mybatisx.core.property.LambdaMetadataWeakCache;
import io.github.mybatisx.core.property.Property;
import lombok.Getter;

/**
 * 抽象关联查询条件
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/2/11
 * @since 1.0.0
 */
@SuppressWarnings({"serial"})
public abstract class AbstractGenericJoinable<T, C extends GenericJoinable<T, C>> extends AbstractGenericQueryCriteria<T, C>
        implements GenericJoinable<T, C> {

    /**
     * 关联查询
     */
    protected Query<?> reference;
    /**
     * 关联方式
     */
    @Getter
    protected Join join;

    @Override
    public C fetch() {
        return this.fetch(true);
    }

    @Override
    public C fetch(boolean fetch) {
        this.fetch = fetch;
        return this.context;
    }

    @Override
    public boolean isFetch() {
        return this.fetch;
    }

    @Override
    public C join() {
        return this.join(this.reference);
    }

    @Override
    public C join(Query<?> refQuery) {
        if (refQuery == null) {
            throw new MyBatisException("The reference query object cannot be null");
        }
        refQuery.join(this);
        return this.context;
    }

    @Override
    public C on(String leftProperty) {
        return this.joinableConditionAccept(this, leftProperty, true, this.reference);
    }

    @Override
    public <R> C onWith(Property<R, ?> rightProperty) {
        return this.onWith(LambdaMetadataWeakCache.getProperty(rightProperty));
    }

    @Override
    public C onWith(String rightProperty) {
        return this.joinableConditionAccept(this, this.reference, rightProperty, true);
    }

    @Override
    public C onWith(String leftProperty, String rightColumn) {
        return this.joinableConditionAccept(this, leftProperty, true, this.reference, rightColumn, false);
    }

    @Override
    public C colOn(String leftColumn) {
        return this.joinableConditionAccept(this, leftColumn, false, this.reference);
    }

    @Override
    public C colOn(String leftColumn, String rightColumn) {
        return this.joinableConditionAccept(this, leftColumn, false, this.reference, rightColumn, false);
    }

    @Override
    public C colOnWith(String leftColumn, String rightProperty) {
        return this.joinableConditionAccept(this, leftColumn, false, this.reference, rightProperty, true);
    }

}
