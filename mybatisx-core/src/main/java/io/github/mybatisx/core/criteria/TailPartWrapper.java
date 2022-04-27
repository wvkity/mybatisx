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
    C singleTail(final String template, final Object value);

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
     * @param key      占位符参数
     * @param value    值
     * @return {@code  this}
     */
    default C tail(final String template, final String key, final Object value) {
        return this.tail(template, Maps.of(key, value));
    }

    /**
     * 尾部SQL片段
     *
     * @param template 模板
     * @param k1       占位符参数1
     * @param v1       参数1对应值
     * @param k2       占位符参数2
     * @param v2       参数2对应值
     * @return {@code  this}
     */
    default C tail(final java.lang.String template, final String k1, final Object v1, final String k2, final Object v2) {
        return this.tail(template, Maps.of(k1, v1, k2, v2));
    }

    /**
     * 尾部SQL片段
     *
     * @param template 模板
     * @param k1       占位符参数1
     * @param v1       参数1对应值
     * @param k2       占位符参数2
     * @param v2       参数2对应值
     * @param k3       占位符参数3
     * @param v3       参数3对应值
     * @return {@code  this}
     */
    default C tail(final java.lang.String template, final String k1, final Object v1,
                   final String k2, final Object v2, final String k3, final Object v3) {
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
