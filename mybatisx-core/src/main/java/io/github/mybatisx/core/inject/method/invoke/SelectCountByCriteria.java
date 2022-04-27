package io.github.mybatisx.core.inject.method.invoke;

import io.github.mybatisx.core.inject.method.support.AbstractCriteriaMethod;
import io.github.mybatisx.core.mapping.query.SelectCountByCriteriaSupplier;

/**
 * {@code selectCountByCriteria}方法映射
 *
 * @author wvkity
 * @created 2022/4/26
 * @since 1.0.0
 */
public class SelectCountByCriteria extends AbstractCriteriaMethod<SelectCountByCriteriaSupplier> {

    @Override
    public Class<?> getReturnType() {
        return Long.class;
    }
}
