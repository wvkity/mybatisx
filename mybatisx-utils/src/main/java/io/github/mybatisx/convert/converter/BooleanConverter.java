package io.github.mybatisx.convert.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * {@link Boolean}类型转换器
 *
 * @author wvkity
 * @created 2023/3/22
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BooleanConverter implements BasicTypeConverter<Boolean> {
    @Override
    public boolean isTargetType(Object source) {
        return this.isBooleanType(source);
    }

    @Override
    public Boolean convert(Object source) {
        return this.convertToBoolean(source);
    }

    private static final class BooleanConverterHolder {
        private static final BooleanConverter INSTANCE = new BooleanConverter();
    }

    public static BooleanConverter getInstance() {
        return BooleanConverterHolder.INSTANCE;
    }
}
