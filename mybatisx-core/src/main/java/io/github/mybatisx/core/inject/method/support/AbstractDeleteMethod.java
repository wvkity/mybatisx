package io.github.mybatisx.core.inject.method.support;

import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.support.mapping.SqlSupplier;

/**
 * 抽象删除方法
 *
 * @author wvkity
 * @created 2022/5/28
 * @since 1.0.0
 */
public abstract class AbstractDeleteMethod<S extends SqlSupplier> extends AbstractInjectMethod<S> {

    @Override
    public void injectMappedStatement(Table table, Class<?> mapperInterface, Class<?> returnType) {
        this.addDeleteMappedStatement(table, mapperInterface, returnType);
    }
}
