package io.github.mybatisx.core.fragment;

import io.github.mybatisx.base.convert.ParameterConverter;
import io.github.mybatisx.base.convert.PlaceholderConverter;
import io.github.mybatisx.base.criteria.Criteria;

/**
 * 更新操作片段管理器
 *
 * @author wvkity
 * @created 2022/5/14
 * @since 1.0.0
 */
public class UpdateFragmentManager extends AbstractFragmentManager {

    private static final long serialVersionUID = -4537673469888050573L;

    public UpdateFragmentManager(Criteria<?> criteria, ParameterConverter parameterConverter,
                                 PlaceholderConverter placeholderConverter) {
        super(criteria, new ConditionStorage(parameterConverter, placeholderConverter), null, null, null, null,
                new TailPartStorage(parameterConverter, placeholderConverter));
    }

}
