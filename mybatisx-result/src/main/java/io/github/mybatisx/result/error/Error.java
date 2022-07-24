package io.github.mybatisx.result.error;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 异常接口
 *
 * @author wvkity
 * @created 2021/12/15
 * @since 1.0.0
 */
public interface Error extends Serializable {

    /**
     * 获取Http状态码
     *
     * @return http状态码
     */
    int status();

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    int getCode();

    /**
     * 获取异常描述信息
     *
     * @return 异常详细描述
     */
    String getMsg();

    /**
     * 转换成{@link Throwable}对象
     *
     * @return {@link RuntimeException}
     */
    default RuntimeException sneakyThrow() {
        return null;
    }

    /**
     * 转换成{@link Throwable}对象
     *
     * @param action {@link Function}
     * @return {@link RuntimeException}
     */
    default RuntimeException sneakyThrow(final Function<Error, Throwable> action) {
        return null;
    }

}
