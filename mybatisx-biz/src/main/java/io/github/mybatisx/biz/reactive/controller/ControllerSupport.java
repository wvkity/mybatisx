package io.github.mybatisx.biz.reactive.controller;

import io.github.mybatisx.biz.reactive.manager.ReactiveManager;
import io.github.mybatisx.core.mapper.CurdMapper;
import io.github.mybatisx.extend.service.CurdService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * 抽象Controller
 *
 * @param <MGR> {@link ReactiveManager}类型
 * @param <S>   {@link CurdService}类型
 * @param <M>   {@link CurdMapper}类型
 * @param <T>   实体类型
 * @param <R>   返回值类型
 * @param <ID>  主键类型
 * @author wvkity
 * @created 2022/7/18
 * @since 1.0.0
 */
public abstract class ControllerSupport<MGR extends ReactiveManager<S, M, T, R, ID>, S extends CurdService<M, T, R, ID>, M extends CurdMapper<T, R, ID>, T, R, ID extends Serializable> {

    /**
     * {@link ReactiveManager}对象
     */
    protected MGR manager;

    @Autowired(required = false)
    public void setManager(MGR manager) {
        this.manager = manager;
    }
}
