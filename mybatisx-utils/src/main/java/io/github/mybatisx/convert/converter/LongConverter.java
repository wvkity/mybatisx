package io.github.mybatisx.convert.converter;

/**
 * {@link Long}类型转换器
 *
 * @author wvkity
 * @created 2023/3/22
 * @since 1.0.0
 */
public class LongConverter implements BasicTypeConverter<Long> {

    @Override
    public boolean isTargetType(Object source) {
        return this.isLongType(source);
    }

    @Override
    public Long convert(Object source) {
        return this.convertToLong(source);
    }
}
