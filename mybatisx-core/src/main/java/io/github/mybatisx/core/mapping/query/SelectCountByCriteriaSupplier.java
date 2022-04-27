package io.github.mybatisx.core.mapping.query;

import io.github.mybatisx.base.config.MyBatisGlobalConfig;
import io.github.mybatisx.base.metadata.Table;

/**
 * {@code selectCountByCriteria}方法SQL提供器
 * @author wvkity
 * @created 2022/4/26
 * @since 1.0.0
 */
public class SelectCountByCriteriaSupplier extends GenericCriteriaSupplier {

    public SelectCountByCriteriaSupplier(MyBatisGlobalConfig mgc, Table table) {
        super(mgc, table);
    }

    @Override
    public String get() {
        return this.criteriaSelect("COUNT(*) AS RECORDS", this.getSelectConditionFragment());
    }
    
    
}
