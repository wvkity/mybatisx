package io.github.mybatisx.convert.converter;

/**
 * {@link String}类型转换器
 *
 * @author wvkity
 * @created 2023/3/22
 * @since 1.0.0
 */
public class StringConvert implements BasicTypeConverter<String> {

    @Override
    public boolean isTargetType(Object source) {
        return this.isStringType(source);
    }

    @Override
    public String convert(Object source) {
        return this.convertToString(source);
    }
}
