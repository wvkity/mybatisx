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
package io.github.mybatisx.executor;

import io.github.mybatisx.base.config.MyBatisGlobalConfigContext;
import io.github.mybatisx.embedded.EmbeddableResult;
import io.github.mybatisx.embedded.EmbeddableUtil;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.BaseExecutor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

import java.util.Optional;

/**
 * 重写{@link BaseExecutor}
 *
 * @author wvkity
 * @created 2021/12/25
 * @since 1.0.0
 */
public abstract class AbstractMyBatisBaseExecutor extends BaseExecutor {

    public AbstractMyBatisBaseExecutor(Configuration configuration, Transaction transaction) {
        super(configuration, transaction);
    }

    @Override
    public CacheKey createCacheKey(MappedStatement ms, Object parameterObject,
                                   RowBounds rowBounds, BoundSql boundSql) {
        final CacheKey cacheKey = super.createCacheKey(ms, parameterObject, rowBounds, boundSql);
        if (ms.getSqlCommandType() == SqlCommandType.SELECT && MyBatisGlobalConfigContext.isEmbeddableMethod(ms.getId())) {
            final Optional<EmbeddableResult> optional = EmbeddableUtil.optional(parameterObject);
            if (optional.isPresent()) {
                final EmbeddableResult er = optional.get();
                Optional.ofNullable(er.getResultMap()).ifPresent(cacheKey::update);
                Optional.ofNullable(er.getReturnType()).ifPresent(cacheKey::update);
                Optional.ofNullable(er.getMapKey()).ifPresent(cacheKey::update);
                Optional.ofNullable(er.getMapType()).ifPresent(cacheKey::update);
            }
        }
        return cacheKey;
    }

}
