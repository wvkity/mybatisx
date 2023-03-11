package io.github.mybatisx.function;

/**
 * 空值消费接口
 *
 * @author wvkity
 * @created 2023/2/19
 * @since 1.0.0
 */
@FunctionalInterface
public interface Absence {

    /**
     * 执行操作
     */
    void accept();
}
