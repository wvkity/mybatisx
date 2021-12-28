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
package io.github.mybatisx.base.naming;

import io.github.mybatisx.annotation.NamingStrategy;
import io.github.mybatisx.base.convert.Converter;
import io.github.mybatisx.lang.Strings;

/**
 * 策略转换
 *
 * @author wvkity
 * @created 2021/12/28
 * @since 1.0.0
 */
public enum NamingCase implements Converter<NamingStrategy, NamingCase> {


    NORMAL(__ -> false, ""),
    LOWER(Strings::isUpperCase, "") {
        @Override
        String normalized(String word) {
            return Strings.toLowerCase(word);
        }
    },
    UPPER(Strings::isLowerCase, "") {
        @Override
        String normalized(String word) {
            return Strings.toUpperCase(word);
        }
    },
    LOWER_CAMEL(Strings::isUpperCase, "") {
        @Override
        String normalized(String word) {
            return Strings.firstOnlyToUpperCase(word);
        }

        @Override
        String normalizedOfFirst(String word) {
            return Strings.toLowerCase(word);
        }
    },
    UPPER_CAMEL(Strings::isUpperCase, "") {
        @Override
        String normalized(String word) {
            return Strings.firstOnlyToUpperCase(word);
        }
    },
    LOWER_UNDERSCORE(c -> '_' == c, "_") {
        @Override
        String normalized(String word) {
            return Strings.toLowerCase(word);
        }

        @Override
        String convert(NamingCase format, String source) {
            if (format == UPPER_UNDERSCORE) {
                return Strings.toUpperCase(source);
            }
            return super.convert(format, source);
        }
    },
    UPPER_UNDERSCORE(c -> '_' == c, "_") {
        @Override
        String normalized(String word) {
            return Strings.toUpperCase(word);
        }

        @Override
        String convert(NamingCase format, String source) {
            if (format == LOWER_UNDERSCORE) {
                return Strings.toLowerCase(source);
            }
            return super.convert(format, source);
        }
    };

    /**
     * 字符匹配器
     */
    private final CharMatcher matcher;
    /**
     * 分隔符
     */
    private final String separator;

    NamingCase(CharMatcher matcher, String separator) {
        this.matcher = matcher;
        this.separator = separator;
    }

    public String to(final NamingCase format, final String source) {
        if (Strings.isWhitespace(source) || format == null || this == format || format == NORMAL) {
            return source;
        }
        return this.convert(format, source);
    }

    public String to(final NamingStrategy format, final String source) {
        return this.to(this.convert(format), source);
    }

    String convert(final NamingCase format, final String source) {
        StringBuilder out = null;
        int i = 0;
        int j = -1;
        final int len = source.length();
        while (true) {
            ++j;
            if ((j = matches(this.matcher, source, j)) == -1) {
                return i == 0 ? format.normalizedOfFirst(source) :
                        out.append(format.normalized(source.substring(i))).toString();
            }
            if (i == 0) {
                out = new StringBuilder(len + 4 * this.separator.length());
                out.append(format.normalizedOfFirst(source.substring(i, j)));
            } else {
                out.append(format.normalized(source.substring(i, j)));
            }
            out.append(format.separator);
            i = j + this.separator.length();
        }
    }

    int matches(final CharMatcher matcher, final CharSequence chars, final int index) {
        final int size = chars.length();
        this.positionIndex(index, size);
        for (int i = index; i < size; i++) {
            if (matcher.matches(chars.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    void positionIndex(final int index, final int size) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index (" + index + ") must not be negative");
        } else if (size < 0) {
            throw new IllegalArgumentException("negative size: " + size);
        } else if (index > size) {
            throw new IndexOutOfBoundsException("index (" + index + ") must not be greater than size (" + size + ")");
        }
    }

    String normalized(final String word) {
        return word;
    }

    String normalizedOfFirst(final String word) {
        return this.normalized(word);
    }

    @Override
    public NamingCase convert(NamingStrategy src) {
        return NamingCase.to(src);
    }

    public static NamingCase to(final NamingStrategy src) {
        return NamingCase.to(src.name());
    }

    public static NamingCase to(final String target) {
        try {
            return NamingCase.valueOf(target);
        } catch (Exception e) {
            throw new NamingStrategyConvertException("Naming strategy convert failed: " + target);
        }
    }
}
