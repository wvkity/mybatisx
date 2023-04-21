package io.github.mybatisx.convert.converter;

import java.util.Map;

/**
 * {@link Map}转换器
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @param <T> 目标类型
 * @author wvkity
 * @created 2023/4/21
 * @since 1.0.0
 */
@FunctionalInterface
public interface JSONConvert<K, V, T> extends Converter<Map<K, V>, T> {

    /**
     * 检查{@link Map}对象中是否包含某个键
     *
     * @param source 源对象
     * @param key    键
     * @return boolean
     */
    default boolean containsKey(final Map<K, V> source, final K key) {
        return source.containsKey(key);
    }
}
