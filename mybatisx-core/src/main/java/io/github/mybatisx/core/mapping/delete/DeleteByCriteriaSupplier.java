package io.github.mybatisx.core.mapping.delete;

import io.github.mybatisx.base.config.MyBatisGlobalConfig;
import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.core.mapping.AbstractCriteriaSupplier;

/**
 * {@code deleteByCriteria}方法SQL提供器
 *
 * @author wvkity
 * @created 2022/7/6
 * @since 1.0.0
 */
public class DeleteByCriteriaSupplier extends AbstractCriteriaSupplier {

    public DeleteByCriteriaSupplier(MyBatisGlobalConfig mgc, Table table) {
        super(mgc, table);
    }

    @Override
    public String get() {
        return this.criteriaDelete(this.getUpdateConditionFragment());
    }
}
