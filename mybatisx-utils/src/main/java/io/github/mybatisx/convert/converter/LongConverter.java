package io.github.mybatisx.convert.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * {@link Long}类型转换器
 *
 * @author wvkity
 * @created 2023/3/22
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LongConverter implements GenericConverter<Long> {

    @Override
    public boolean isTargetType(Object source) {
        return this.isLongType(source);
    }

    @Override
    public Long convert(Object source) {
        return this.convertToLong(source);
    }

    private static final class LongConverterHolder {
        private static final LongConverter INSTANCE = new LongConverter();
    }

    /**
     * 获取{@link LongConverter}对象
     *
     * @return {@link LongConverter}对象
     */
    public static LongConverter getInstance() {
        return LongConverterHolder.INSTANCE;
    }
}
