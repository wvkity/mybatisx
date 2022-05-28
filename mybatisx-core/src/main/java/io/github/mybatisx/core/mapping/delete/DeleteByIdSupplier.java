package io.github.mybatisx.core.mapping.delete;

import io.github.mybatisx.base.config.MyBatisGlobalConfig;
import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.Splicing;
import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.core.mapping.AbstractSupplier;
import io.github.mybatisx.core.mapping.Scripts;

/**
 * {@code deleteById}方法SQL提供器
 *
 * @author wvkity
 * @created 2022/5/28
 * @since 1.0.0
 */
public class DeleteByIdSupplier extends AbstractSupplier {

    public DeleteByIdSupplier(MyBatisGlobalConfig mgc, Table table) {
        super(mgc, table);
    }

    @Override
    public String get() {
        if (this.table.isHasPrimaryKey()) {
            return this.delete(WHERE_SPACE + Scripts.toPlaceHolderArg(Constants.PARAM_ID, Splicing.NONE, this.table.getPrimaryKey()));
        }
        return EMPTY;
    }
}
