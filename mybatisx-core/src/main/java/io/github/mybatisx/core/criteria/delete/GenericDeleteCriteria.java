package io.github.mybatisx.core.criteria.delete;

import io.github.mybatisx.core.criteria.support.GenericCriteriaWrapper;

/**
 * 通用删除条件
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/7/6
 * @since 1.0.0
 */
public interface GenericDeleteCriteria<T, C extends GenericDeleteCriteria<T, C>> extends PlainDeleteCriteria<T, C>,
        LambdaDeleteCriteria<T, C>, GenericCriteriaWrapper<T, C> {
}
