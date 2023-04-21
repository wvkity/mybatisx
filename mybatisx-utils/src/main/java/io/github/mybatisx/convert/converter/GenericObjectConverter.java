package io.github.mybatisx.convert.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author wvkity
 * @created 2023/4/21
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenericObjectConverter implements GenericConverter<Object> {

    @Override
    public Object convert(Object source) {
        return source;
    }

    @Override
    public boolean isTargetType(Object source) {
        return true;
    }

    private static final class GenericObjectConverterHolder {
        private static final GenericObjectConverter INSTANCE = new GenericObjectConverter();
    }

    /**
     * 获取{@link GenericObjectConverter}对象
     *
     * @return {@link GenericObjectConverter}对象
     */
    public static GenericObjectConverter getInstance() {
        return GenericObjectConverterHolder.INSTANCE;
    }
}
