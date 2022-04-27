package io.github.mybatisx.core.inject.method.invoke;

import io.github.mybatisx.core.inject.method.support.AbstractCriteriaMethod;
import io.github.mybatisx.core.mapping.query.SelectCustomListSupplier;

/**
 * {@code  selectCustomList}方法映射
 *
 * @author wvkity
 * @created 2022/4/21
 * @since 1.0.0
 */
public class SelectCustomList extends AbstractCriteriaMethod<SelectCustomListSupplier> {

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }
}
