package io.github.mybatisx.builder;

/**
 * 赋值消费接口
 *
 * @param <T>  对象类型
 * @param <V1> 值类型
 * @param <V2> 值类型
 * @param <V3> 值类型
 * @param <V4> 值类型
 * @author wvkity
 * @created 2023/3/11
 * @since 1.0.0
 */
@FunctionalInterface
public interface FourArgConsumer<T, V1, V2, V3, V4> {

    /**
     * 消费
     *
     * @param target 对象
     * @param v1     值1
     * @param v2     值2
     * @param v3     值3
     * @param v4     值4
     */
    void accept(final T target, final V1 v1, final V2 v2, final V3 v3, final V4 v4);
}
