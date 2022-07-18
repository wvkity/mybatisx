package io.github.mybatisx.biz.reactive.manager;

import io.github.mybatisx.core.mapper.CurdMapper;
import io.github.mybatisx.extend.service.CurdService;

import java.io.Serializable;

/**
 * 反应式业务接口
 *
 * @param <S>  {@link CurdService}类型
 * @param <M>  {@link CurdMapper}类型
 * @param <T>  实体类型
 * @param <R>  返回值类型
 * @param <ID> 主键类型
 * @author wvkity
 * @created 2022/7/18
 * @since 1.0.0
 */
public interface ReactiveManager<S extends CurdService<M, T, R, ID>, M extends CurdMapper<T, R, ID>, T, R, ID extends Serializable> extends GenericManager {

    /**
     * 获取{@link CurdService}对象
     *
     * @return {@link CurdService}对象
     */
    S getService();

    /**
     * 设置{@link CurdService}对象
     *
     * @param service {@link CurdService}对象
     */
    void setService(final S service);
}
