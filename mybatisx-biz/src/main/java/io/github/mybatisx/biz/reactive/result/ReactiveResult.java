package io.github.mybatisx.biz.reactive.result;

import io.github.mybatisx.result.Result;
import io.github.mybatisx.result.Status;
import io.github.mybatisx.result.core.MultiResult;
import io.github.mybatisx.result.core.PlainResult;
import io.github.mybatisx.result.error.Error;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 反应式结果
 *
 * @author wvkity
 * @created 2022/11/16
 * @since 1.0.0
 */
public interface ReactiveResult {

    /**
     * 多值成功结果
     *
     * @return {@link MultiResult}
     */
    default MultiResult multiOk() {
        return MultiResult.ok();
    }

    /**
     * 单值成功结果集
     *
     * @param <T> 数据类型
     * @return {@link PlainResult}
     */
    default <T> PlainResult<T> ok() {
        return PlainResult.ok();
    }

    /**
     * 失败结果集
     *
     * @param error {@link Error}
     * @return {@link Result}
     */
    default Result failure(final Error error) {
        return new PlainResult<>(error == null ? Status.FAILURE : error);
    }

    /**
     * 失败结果集
     *
     * @param data  数据
     * @param error {@link Error}
     * @param <T>   数据类型
     * @return {@link Result}
     */
    default <T> PlainResult<T> failure(final T data, final Error error) {
        final PlainResult<T> result = new PlainResult<>(error == null ? Status.FAILURE : error);
        result.setData(data);
        return result;
    }

    /**
     * 成功
     *
     * @param key   键
     * @param value 值
     * @param <K>   键类型
     * @param <V>   值类型
     * @return {@link Mono}
     */
    default <K, V> Mono<Result> okJust(final K key, final V value) {
        return this.just(MultiResult.ok().put(key, value));
    }

    /**
     * 成功
     *
     * @param data 数据
     * @param <K>  键
     * @param <V>  值
     * @return {@link Mono}
     */
    default <K, V> Mono<Result> okMapJust(final Map<K, V> data) {
        return this.just(MultiResult.ok(data));
    }

    /**
     * 成功
     *
     * @return {@link Mono}
     */
    default Mono<Result> trueJust() {
        return this.okJust(true);
    }

    /**
     * 成功
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return {@link Mono}
     */
    default <T> Mono<Result> okJust(final T data) {
        return Mono.just(PlainResult.ok(data));
    }

    /**
     * 成功
     *
     * @return {@link Mono}
     */
    default Mono<Result> okJust() {
        return this.just(this.ok());
    }

    /**
     * 非法参数
     *
     * @return {@link Mono}
     */
    default Mono<Result> illegalArgumentJust() {
        return this.failureJust(Status.ILLEGAL_ARGUMENT);
    }

    /**
     * 非法参数
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return {@link Mono}
     */
    default <T> Mono<Result> illegalArgumentJust(final T data) {
        return this.failureJust(data, Status.ILLEGAL_ARGUMENT);
    }

    /**
     * 非法参数
     *
     * @param data  数据
     * @param error 错误消息
     * @param <T>   数据类型
     * @return {@link Mono}
     */
    default <T> Mono<Result> illegalArgumentJust(final T data, final String error) {
        return this.failureJust(data, Status.ILLEGAL_ARGUMENT, error);
    }

    /**
     * 失败
     *
     * @param error {@link Error}
     * @return {@link Mono}
     */
    default Mono<Result> failureJust(final Error error) {
        return this.just(this.failure(error));
    }

    /**
     * 失败
     *
     * @param data  数据
     * @param error {@link Error}
     * @param <T>   数据类型
     * @return {@link Mono}
     */
    default <T> Mono<Result> failureJust(final T data, final Error error) {
        return this.just(this.failure(data, error));
    }

    /**
     * 失败
     *
     * @param data    数据
     * @param error   {@link Error}
     * @param message 错误信息
     * @param <T>     数据类型
     * @return {@link Mono}
     */
    default <T> Mono<Result> failureJust(final T data, final Error error, final String message) {
        final PlainResult<T> result = PlainResult.failure(error, message);
        result.setData(data);
        return this.just(result);
    }

    /**
     * 结果
     *
     * @param result {@link Result}
     * @return {@link Mono}
     */
    default Mono<Result> just(final Result result) {
        return Mono.just(result);
    }

}
