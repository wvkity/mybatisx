package io.github.mybatisx.core.criteria;

import io.github.mybatisx.util.Maps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 通用尾部SQL片段
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/4/26
 * @since 1.0.0
 */
public interface TailPartWrapper<T, C extends TailPartWrapper<T, C>> {

    /**
     * 尾部纯SQL片段
     *
     * @param sql SQL语句
     * @return {@code this}
     */
    C tail(final String sql);

    /**
     * 尾部SQL片段
     *
     * @param template 模板
     * @param value    值
     * @return {@code this}
     */
    C tail(final String template, final Object value);

    /**
     * 尾部SQL片段
     *
     * @param template 模板
     * @param values   多个值
     * @return {@code this}
     */
    C tail(final String template, final List<?> values);

    /**
     * 尾部SQL片段
     *
     * @param template 模板
     * @param values   多个参数值
     * @return {@code this}
     */
    C tail(final String template, final Map<String, Object> values);

    /**
     * 尾部SQL片段
     *
     * @param template 模板
     * @param values   多个值
     * @return {@code this}
     */
    default C tail(final String template, final Object[] values) {
        return this.tail(template, Arrays.asList(values));
    }

    /**
     * 尾部SQL片段
     *
     * @param template 模板
     * @param key      键
     * @param value    值
     * @param <K>      键类型
     * @param <V>      值类型
     * @return {@code  this}
     */
    default <K, V> C tail(final String template, final K key, final V value) {
        return this.tail(template, Maps.of(key, value));
    }

    /**
     * 尾部SQL片段
     *
     * @param template 模板
     * @param k1       键1
     * @param v1       k1对应值
     * @param k2       键2
     * @param v2       k2对应值
     * @param <K>      键类型
     * @param <V>      值类型
     * @return {@code  this}
     */
    default <K, V> C tail(final String template, final K k1, final V v1, final K k2, final V v2) {
        return this.tail(template, Maps.of(k1, v1, k2, v2));
    }

    /**
     * 尾部SQL片段
     *
     * @param template 模板
     * @param k1       键1
     * @param v1       k1对应值
     * @param k2       键2
     * @param v2       k2对应值
     * @param k3       键3
     * @param v3       k3对应值
     * @param <K>      键类型
     * @param <V>      值类型
     * @return {@code  this}
     */
    default <K, V> C tail(final String template, final K k1, final V v1, final K k2, final V v2, final K k3,
                          final V v3) {
        return this.tail(template, Maps.of(k1, v1, k2, v2, k3, v3));
    }

    /**
     * 尾部SQL片段
     *
     * @param template 模板
     * @param values   多个参数值
     * @return {@code this}
     */
    default C mapTail(final String template, final Object[] values) {
        return this.tail(template, Maps.ofArray(values));
    }

    /**
     * 尾部SQL片段
     *
     * @param template 模板
     * @param values   多个参数值
     * @return {@code this}
     */
    default C mapTail(final String template, final List<?> values) {
        return this.tail(template, Maps.ofList(values));
    }

}
