package io.github.mybatisx.convert.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * {@link String}类型转换器
 *
 * @author wvkity
 * @created 2023/3/22
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StringConverter implements BasicTypeConverter<String> {

    @Override
    public boolean isTargetType(Object source) {
        return this.isStringType(source);
    }

    @Override
    public boolean isTargetType(Class<?> clazz, Object source) {
        return clazz.isAssignableFrom(source.getClass());
    }

    @Override
    public String convert(Object source) {
        return this.convertToString(source);
    }

    private static final class StringConvertHolder {
        private static final StringConverter INSTANCE = new StringConverter();
    }

    public static StringConverter getInstance() {
        return StringConvertHolder.INSTANCE;
    }
}
