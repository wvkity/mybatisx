/*
 * Copyright (c) 2021-Now, wvkity(wvkity@gmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.mybatisx.core.mapping;

import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.base.constant.Splicing;
import io.github.mybatisx.base.constant.SqlSymbol;
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * SQL脚本工具
 *
 * @author wvkity
 * @created 2022/1/2
 * @since 1.0.0
 */
public final class Scripts implements SqlSymbol {

    private Scripts() {
    }

    /**
     * 将字段转占位符参数
     *
     * @param argName  方法参数名(@Param("argName"))
     * @param splicing 拼接类型
     * @param column   {@link Column}
     * @return 占位符参数
     */
    public static String toPlaceHolderArg(final String argName, final Splicing splicing, final Column column) {
        return toPlaceHolderArg(argName, Symbol.EQ, splicing, null, column);
    }

    /**
     * 将字段转占位符参数
     *
     * @param argName  方法参数名(@Param("argName"))
     * @param splicing 拼接类型
     * @param alias    表别名
     * @param column   {@link Column}
     * @return 占位符参数
     */
    public static String toPlaceHolderArg(final String argName, final Splicing splicing,
                                          final String alias, final Column column) {
        return toPlaceHolderArg(argName, Symbol.EQ, splicing, alias, column);
    }

    /**
     * 将字段转占位符参数
     *
     * @param argName  方法参数名(@Param("argName"))
     * @param symbol   条件符号
     * @param splicing 拼接类型
     * @param alias    表别名
     * @param column   {@link Column}
     * @return 占位符参数
     */
    public static String toPlaceHolderArg(final String argName, final Symbol symbol, final Splicing splicing,
                                          final String alias, final Column column) {
        return toPlaceHolderArg(argName, symbol, splicing, alias, column, null);
    }

    /**
     * 将字段转占位符参数
     *
     * @param argName   方法参数名(@Param("argName"))
     * @param symbol    条件符号
     * @param splicing  拼接类型
     * @param alias     表别名
     * @param column    {@link Column}
     * @param separator 分隔符
     * @return 占位符参数
     */
    public static String toPlaceHolderArg(final String argName, final Symbol symbol, final Splicing splicing,
                                          final String alias, final Column column, final String separator) {
        return toPlaceHolderArg(argName, symbol, splicing, alias, column.getProperty(), column.getColumn(),
                column.getTypeHandler(), column.getJdbcType(), column.isSpliceJavaType(),
                column.getDescriptor().getJavaType(), separator);
    }

    /**
     * 将字段转占位符参数
     *
     * @param argName        方法参数名(@Param("argName"))
     * @param symbol         条件符号
     * @param splicing       拼接类型
     * @param alias          表别名
     * @param column         {@link Column}
     * @param handler        类型处理器
     * @param jdbcType       Jdbc类型
     * @param spliceJavaType 是否拼接JavaType类型
     * @param javaType       Java类型
     * @param separator      分隔符
     * @return 占位符参数
     */
    public static String toPlaceHolderArg(final String argName, final Symbol symbol, final Splicing splicing,
                                          final String alias, final Column column,
                                          final Class<? extends TypeHandler<?>> handler, final JdbcType jdbcType,
                                          final boolean spliceJavaType, final Class<?> javaType,
                                          final String separator) {
        return toPlaceHolderArg(argName, symbol, splicing, alias, column.getProperty(), column.getColumn(), handler,
                jdbcType, spliceJavaType, javaType, separator);
    }

    /**
     * 将字段转占位符参数
     *
     * @param argName        方法参数名(@Param("argName"))
     * @param symbol         条件符号
     * @param splicing       拼接类型
     * @param alias          表别名
     * @param property       属性
     * @param column         字段名
     * @param handler        类型处理器
     * @param jdbcType       Jdbc类型
     * @param spliceJavaType 是否拼接JavaType类型
     * @param javaType       Java类型
     * @param separator      分隔符
     * @return 占位符参数
     */
    public static String toPlaceHolderArg(final String argName, final Symbol symbol, final Splicing splicing,
                                          final String alias, final String property, final String column,
                                          final Class<? extends TypeHandler<?>> handler, final JdbcType jdbcType,
                                          final boolean spliceJavaType, final Class<?> javaType,
                                          final String separator) {
        final StringBuilder sb = new StringBuilder(60);
        if (splicing != Splicing.INSERT) {
            if (Strings.isNotWhitespace(alias)) {
                sb.append(alias).append(DOT);
            }
            sb.append(column).append(SPACE);
            if (symbol == null) {
                sb.append(Symbol.EQ.getFragment());
            } else {
                sb.append(symbol.getFragment());
            }
            sb.append(SPACE);
        }
        if (splicing == Splicing.NONE) {
            sb.append(safeJoining(Strings.isWhitespace(argName) ? VALUE : argName,
                    concatTypeArg(handler, jdbcType, spliceJavaType, javaType)));
        } else {
            if (Strings.isWhitespace(argName)) {
                sb.append(safeJoining(argName, property, concatTypeArg(handler, jdbcType, spliceJavaType, javaType)));
            } else {
                sb.append(safeJoining(argName, DOT, property, concatTypeArg(handler, jdbcType, spliceJavaType,
                        javaType)));
            }
        }
        if (Strings.isNotEmpty(separator)) {
            sb.append(separator);
        }
        return sb.toString();
    }

    /**
     * 将字段转占位符参数
     *
     * @param argName        方法参数名(@Param("argName"))
     * @param symbol         条件符号
     * @param splicing       拼接类型
     * @param alias          表别名
     * @param column         字段名
     * @param forceCheckNull 强制检查Null值
     * @param isQuery        是否为查询
     * @param toValue        是否转成值
     * @param logic          逻辑符号
     * @param separator      分隔符
     * @return 占位符参数
     */
    public static String toIfTag(final String argName, final Symbol symbol, final Splicing splicing,
                                 final String alias, final Column column, final boolean forceCheckNull,
                                 final boolean isQuery, final boolean toValue, final LogicSymbol logic,
                                 final String separator) {
        final StringBuilder script = new StringBuilder(60);
        if (toValue) {
            if (Objects.nonNull(logic)) {
                script.append(logic.getFragment()).append(SPACE);
            }
            script.append(toPlaceHolderArg(argName, symbol, splicing, isQuery ? alias : null, column, separator));
        } else {
            script.append(column.getColumn()).append(COMMA_SPACE);
        }
        final boolean isCheckNull = column.isCheckNull();
        final boolean isCheckEmpty = column.isCheckEmpty();
        if (!isCheckNull && !isCheckEmpty && !forceCheckNull) {
            return script.toString();
        }
        final StringBuilder ifTag = new StringBuilder(60);
        final boolean hasArg = Strings.isNotWhitespace(argName);
        final String property = column.getProperty();
        if (hasArg) {
            ifTag.append(argName).append(DOT);
        }
        ifTag.append(property).append(" != null");
        if (isCheckEmpty && Objects.isAssignable(String.class, column.getDescriptor().getJavaType())) {
            ifTag.append(" and ");
            if (hasArg) {
                ifTag.append(argName).append(DOT);
            }
            ifTag.append(property).append(" != ''");
        }
        return toIfTag(script.toString(), ifTag.toString(), true);
    }

    /**
     * 转成if条件标签脚本
     *
     * @param script    标签体
     * @param condition 条件
     * @param newLine   是否换行
     * @return if条件标签脚本
     */
    public static String toIfTag(final String script, final String condition, final boolean newLine) {
        if (Strings.isNotWhitespace(script) && Strings.isNotWhitespace(condition)) {
            final String body;
            if (newLine) {
                body = NEW_LINE + SPACE + SPACE + script + NEW_LINE;
            } else {
                body = script;
            }
            return "<if test=\"" + condition + "\">" + body + "</if>";
        }
        return null;
    }

    /**
     * 转成trim标签脚本
     *
     * @param script          SQL语句
     * @param prefix          前缀
     * @param prefixOverrides 去除最前一个
     * @param suffix          后缀
     * @param suffixOverrides 去除最后一个
     * @return trim标签脚本
     */
    public static String toTrimTag(final String script, final String prefix, final String prefixOverrides,
                                   final String suffix, final String suffixOverrides) {
        if (Strings.isNotWhitespace(script)) {
            final StringBuilder sb = new StringBuilder(80);
            sb.append("<trim");
            if (Strings.isNotWhitespace(prefix)) {
                sb.append(SPACE).append("prefix=").append(DOUBLE_QUOTES).append(prefix).append(DOUBLE_QUOTES);
            }
            if (Strings.isNotWhitespace(prefixOverrides)) {
                sb.append(SPACE).append("prefixOverrides=").append(DOUBLE_QUOTES).append(prefixOverrides)
                        .append(DOUBLE_QUOTES);
            }
            if (Strings.isNotWhitespace(suffix)) {
                sb.append(SPACE).append("suffix=").append(DOUBLE_QUOTES).append(suffix).append(DOUBLE_QUOTES);
            }
            if (Strings.isNotWhitespace(suffixOverrides)) {
                sb.append(SPACE).append("suffixOverrides=").append(DOUBLE_QUOTES).append(suffixOverrides)
                        .append(DOUBLE_QUOTES);
            }
            sb.append(GT).append(NEW_LINE).append(script).append(NEW_LINE);
            return sb.append("</trim>").toString();
        }
        return null;
    }

    /**
     * 转成条件参数
     *
     * @param column       字段名
     * @param symbol       {@link Symbol}
     * @param slot         {@link LogicSymbol}
     * @param placeholders 占位符参数列表
     * @return 条件参数
     */
    public static String toConditionArg(final Column column, final Symbol symbol,
                                        final LogicSymbol slot, final String... placeholders) {
        return toConditionArg(null, column, symbol, slot, placeholders);
    }

    /**
     * 转成条件参数
     *
     * @param alias        表别名
     * @param column       字段名
     * @param symbol       {@link Symbol}
     * @param slot         {@link LogicSymbol}
     * @param placeholders 占位符参数列表
     * @return 条件参数
     */
    public static String toConditionArg(final String alias, final Column column, final Symbol symbol,
                                        final LogicSymbol slot, final String... placeholders) {
        return toConditionArg(alias, column.getColumn(), symbol, slot, column.getTypeHandler(), column.getJdbcType(),
                column.isSpliceJavaType(), column.getDescriptor().getJavaType(), placeholders);
    }

    /**
     * 转成条件参数
     *
     * @param column       字段名
     * @param symbol       {@link Symbol}
     * @param slot         {@link LogicSymbol}
     * @param placeholders 占位符参数列表
     * @return 条件参数
     */
    public static String toConditionArg(final String column, final Symbol symbol,
                                        final LogicSymbol slot, final String... placeholders) {
        return toConditionArg(null, column, symbol, slot, placeholders);
    }

    /**
     * 转成条件参数
     *
     * @param alias        表别名
     * @param column       字段名
     * @param symbol       {@link Symbol}
     * @param slot         {@link LogicSymbol}
     * @param placeholders 占位符参数列表
     * @return 条件参数
     */
    public static String toConditionArg(final String alias, final String column, final Symbol symbol,
                                        final LogicSymbol slot, final String... placeholders) {
        return toConditionArg(alias, column, symbol, slot, null, null, false, null, placeholders);
    }

    /**
     * 转成条件参数
     *
     * @param alias          表别名
     * @param column         字段名
     * @param symbol         {@link Symbol}
     * @param slot           {@link LogicSymbol}
     * @param handler        类型处理器
     * @param jdbcType       Jdbc类型
     * @param spliceJavaType 是否拼接Java类型
     * @param javaType       Java类型
     * @param placeholders   占位符参数列表
     * @return 条件参数
     */
    public static String toConditionArg(final String alias, final String column, final Symbol symbol,
                                        final LogicSymbol slot, final Class<? extends TypeHandler<?>> handler,
                                        final JdbcType jdbcType, final boolean spliceJavaType,
                                        final Class<?> javaType, final String... placeholders) {
        final StringBuilder sb = new StringBuilder(120);
        if (slot != null) {
            sb.append(slot.getFragment());
        }
        if (Strings.isNotWhitespace(alias)) {
            sb.append(alias).append(DOT);
        }
        sb.append(column).append(SPACE);
        appendPlaceholderArg(sb, symbol, handler, jdbcType, spliceJavaType, javaType, placeholders);
        return sb.toString();
    }

    /**
     * 转成条件参数
     *
     * @param symbol       {@link Symbol}
     * @param slot         {@link LogicSymbol}
     * @param column       {@link Column}
     * @param placeholders 占位符参数列表
     * @return 条件参数
     */
    public static String toConditionArg(final Symbol symbol, final LogicSymbol slot,
                                        final Column column, final String... placeholders) {
        return toConditionArg(symbol, slot, column.getTypeHandler(), column.getJdbcType(), column.isSpliceJavaType(),
                column.getDescriptor().getJavaType(), placeholders);
    }

    /**
     * 转成条件参数
     *
     * @param symbol       {@link Symbol}
     * @param slot         {@link LogicSymbol}
     * @param placeholders 占位符参数列表
     * @return 条件参数
     */
    public static String toConditionArg(final Symbol symbol, final LogicSymbol slot, final String... placeholders) {
        return toConditionArg(symbol, slot, null, null, false, null, placeholders);
    }

    /**
     * 转成条件参数
     *
     * @param symbol         {@link Symbol}
     * @param slot           {@link LogicSymbol}
     * @param handler        类型处理器
     * @param jdbcType       Jdbc类型
     * @param spliceJavaType 是否拼接Java类型
     * @param javaType       Java类型
     * @param placeholders   占位符参数列表
     * @return 条件参数
     */
    public static String toConditionArg(final Symbol symbol, final LogicSymbol slot,
                                        final Class<? extends TypeHandler<?>> handler,
                                        final JdbcType jdbcType, final boolean spliceJavaType,
                                        final Class<?> javaType, final String... placeholders) {
        final StringBuilder sb = new StringBuilder(120);
        if (slot != null) {
            sb.append(slot.getFragment());
        }
        sb.append(" %s ");
        appendPlaceholderArg(sb, symbol, handler, jdbcType, spliceJavaType, javaType, placeholders);
        return sb.toString();
    }

    /**
     * 拼接占位符参数
     *
     * @param sb             {@link StringBuilder}
     * @param symbol         {@link Symbol}
     * @param handler        类型处理器
     * @param jdbcType       Jdbc类型
     * @param spliceJavaType 是否拼接Java类型
     * @param javaType       Java类型
     * @param placeholders   占位符参数列表
     */
    public static void appendPlaceholderArg(final StringBuilder sb, final Symbol symbol,
                                            final Class<? extends TypeHandler<?>> handler,
                                            final JdbcType jdbcType, final boolean spliceJavaType,
                                            final Class<?> javaType, final String... placeholders) {
        final Symbol realSymbol = Objects.ifNull(symbol, Symbol.EQ);
        if (Objects.isNotEmpty(placeholders)) {
            String typeArg;
            switch (realSymbol) {
                case EQ:
                case NE:
                case LT:
                case LE:
                case GT:
                case GE:
                case LIKE:
                case ILIKE:
                case NOT_LIKE:
                case NOT_ILIKE:
                    typeArg = concatTypeArg(handler, jdbcType, spliceJavaType, javaType);
                    sb.append(realSymbol.getFragment()).append(SPACE).append(safeJoining(placeholders[0], typeArg));
                    break;
                case IN:
                case NOT_IN:
                    typeArg = concatTypeArg(handler, jdbcType, spliceJavaType, javaType);
                    sb.append(realSymbol.getFragment())
                            .append(SPACE)
                            .append(Arrays.stream(placeholders).map(it -> safeJoining(it, typeArg))
                                    .collect(Collectors.joining(COMMA_SPACE, START_BRACKET, END_BRACKET)));
                    break;
                case BETWEEN:
                case NOT_BETWEEN:
                    typeArg = concatTypeArg(handler, jdbcType, spliceJavaType, javaType);
                    sb.append(realSymbol.getFragment())
                            .append(SPACE)
                            .append(safeJoining(placeholders[0], typeArg))
                            .append(AND_SPACE_BOTH)
                            .append(safeJoining(placeholders[1], typeArg));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 拼接完整类型参数
     *
     * @param handler        类型处理器
     * @param jdbcType       Jdbc类型
     * @param spliceJavaType 是否拼接Java类型
     * @param javaType       Java类型
     * @return 参数
     */
    public static String concatTypeArg(final Class<? extends TypeHandler<?>> handler, final JdbcType jdbcType,
                                       final boolean spliceJavaType, final Class<?> javaType) {
        final StringBuilder sb = new StringBuilder(60);
        if (Objects.nonNull(jdbcType) && jdbcType != JdbcType.UNDEFINED) {
            sb.append(", jdbcType=").append(jdbcType);
        }
        if (Objects.nonNull(handler) && handler != UnknownTypeHandler.class) {
            sb.append(", typeHandler=").append(handler.getName());
        }
        if (spliceJavaType && Objects.nonNull(javaType)) {
            sb.append(", javaType=").append(javaType.getName());
        }
        return sb.length() > 0 ? sb.toString() : EMPTY;
    }

    /**
     * 安全占位符
     *
     * @param args 参数列表
     * @return 占位符
     */
    public static String safeJoining(final String... args) {
        final String str = Arrays.stream(args).filter(Objects::nonNull).collect(Collectors.joining(EMPTY));
        return Strings.isWhitespace(str) ? EMPTY : (POUND_START_BRACE + str + END_BRACE);
    }

    /**
     * 不安全占位符
     *
     * @param args 参数列表
     * @return 占位符
     */
    public static String unsafeJoining(final String... args) {
        final String str = Arrays.stream(args).filter(Objects::nonNull).collect(Collectors.joining(EMPTY));
        return Strings.isWhitespace(str) ? EMPTY : (DOLLAR_START_BRACE + str + END_BRACE);
    }
}
