package io.github.mybatisx.core.criteria.delete;

import io.github.mybatisx.core.criteria.support.AbstractPlainCriteria;

/**
 * 抽象删除条件
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/7/6
 * @since 1.0.0
 */
@SuppressWarnings({"serial"})
public abstract class AbstractPlainDeleteCriteria<T, C extends PlainDeleteCriteria<T, C>> extends AbstractPlainCriteria<T,C>
        implements PlainDeleteCriteria<T, C> {
}
