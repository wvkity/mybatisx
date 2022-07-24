package io.github.mybatisx.biz.reactive.manager.impl;

import io.github.mybatisx.biz.reactive.manager.ReactiveManager;
import io.github.mybatisx.core.mapper.CurdMapper;
import io.github.mybatisx.extend.service.CurdService;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.result.Result;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

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
@Slf4j
public abstract class ReactiveManagerSupport<S extends CurdService<M, T, R, ID>, M extends CurdMapper<T, R, ID>, T, R, ID extends Serializable>
        implements ReactiveManager<S, M, T, R, ID> {

    /**
     * {@link CurdService}接口
     */
    @Getter
    protected S service;

    @Override
    public boolean exists(ID id) {
        return this.service.existsById(id);
    }

    @Override
    public Mono<Result> delete(ID id) {
        if (this.idInvalid(id)) {
            log.error("无效ID: {}", id);
            return this.illegalArgumentJust(false);
        }
        if (this.service.deleteById(id) > 0) {
            return this.trueJust();
        }
        log.error("数据不存在: {}", id);
        return this.illegalArgumentJust(false);
    }

    /**
     * 检查ID是否无效
     *
     * @param id ID
     * @return boolean
     */
    protected boolean idInvalid(final ID id) {
        if (id == null) {
            return true;
        }
        if (id instanceof Long) {
            return Objects.idIsInvalid((Long) id);
        }
        if (id instanceof Integer) {
            return Objects.idIsInvalid((Integer) id);
        }
        if (id instanceof String) {
            return Strings.isWhitespace(id.toString());
        }
        return false;
    }

    @Override
    @Autowired(required = false)
    public void setService(S service) {
        if (service != null) {
            this.service = service;
        }
    }
}
