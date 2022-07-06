package io.github.mybatisx.core.criteria.delete;

import io.github.mybatisx.core.criteria.support.AbstractLambdaCriteria;

/**
 * 抽象删除条件(支持Lambda表达式)
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/7/6
 * @since 1.0.0
 */
@SuppressWarnings({"serial"})
public abstract class AbstractLambdaDeleteCriteria<T, C extends LambdaDeleteCriteria<T, C>> extends AbstractLambdaCriteria<T, C>
        implements LambdaDeleteCriteria<T, C> {
}
