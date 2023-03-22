package io.github.mybatisx.convert.converter;

/**
 * {@link Integer}类型转换器
 *
 * @author wvkity
 * @created 2023/3/22
 * @since 1.0.0
 */
public class IntegerConverter implements BasicTypeConverter<Integer> {

    @Override
    public boolean isTargetType(Object source) {
        return this.isIntType(source);
    }

    @Override
    public Integer convert(Object source) {
        return this.convertToInteger(source);
    }
}
