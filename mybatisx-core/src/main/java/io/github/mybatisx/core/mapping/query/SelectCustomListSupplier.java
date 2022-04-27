package io.github.mybatisx.core.mapping.query;

import io.github.mybatisx.base.config.MyBatisGlobalConfig;
import io.github.mybatisx.base.metadata.Table;

/**
 * selectCustomList方法SQL提供器
 *
 * @author wvkity
 * @created 2022/4/21
 * @since 1.0.0
 */
public class SelectCustomListSupplier extends GenericCriteriaSupplier {

    public SelectCustomListSupplier(MyBatisGlobalConfig mgc, Table table) {
        super(mgc, table);
    }
}
