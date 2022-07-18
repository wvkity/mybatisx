package io.github.mybatisx.biz.reactive.manager.impl;

import io.github.mybatisx.biz.reactive.manager.ReactiveManager;
import io.github.mybatisx.core.mapper.CurdMapper;
import io.github.mybatisx.extend.service.CurdService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * 抽象业务接口实现
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
public abstract class ReactiveManagerSupport<S extends CurdService<M, T, R, ID>, M extends CurdMapper<T, R, ID>, T, R, ID extends Serializable>
        implements ReactiveManager<S, M, T, R, ID> {

    /**
     * {@link CurdService}接口
     */
    @Getter
    protected S service;

    @Override
    @Autowired(required = false)
    public void setService(S service) {
        if (service != null) {
            this.service = service;
        }
    }
}
