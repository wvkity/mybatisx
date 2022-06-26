package io.github.mybatisx.core.mapping.update;

import io.github.mybatisx.base.config.MyBatisGlobalConfig;
import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.core.mapping.AbstractCriteriaSupplier;

/**
 * {@code updateByCriteria}方法SQL
 *
 * @author wvkity
 * @created 2022/6/26
 * @since 1.0.0
 */
public class UpdateByCriteriaSupplier extends AbstractCriteriaSupplier {

    public UpdateByCriteriaSupplier(MyBatisGlobalConfig mgc, Table table) {
        super(mgc, table);
    }

    @Override
    public String get() {
        return this.update(SqlSymbol.SET + " " + SqlSymbol.DOLLAR_START_BRACE + Constants.PARAM_CRITERIA +
                SqlSymbol.DOT + "updateFragment" + SqlSymbol.END_BRACE, this.getUpdateConditionFragment());
    }
}
