package io.github.mybatisx.biz.reactive.manager;

import io.github.mybatisx.core.mapper.CurdMapper;
import io.github.mybatisx.extend.service.CurdService;
import io.github.mybatisx.result.Result;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;

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
     * 根据ID检查记录是否存在
     *
     * @param id ID
     * @return boolean
     */
    boolean exists(final ID id);

    /**
     * 根据ID删除记录
     *
     * @param id ID
     * @return {@link Mono}
     */
    Mono<Result> delete(final ID id);

    /**
     * 批量删除记录
     *
     * @param idList ID列表
     * @return {@link Mono}
     */
    Mono<Result> batchDelete(final List<ID> idList);

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
