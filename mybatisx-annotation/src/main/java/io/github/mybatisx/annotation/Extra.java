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
package io.github.mybatisx.annotation;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段额外注解
 *
 * @author wvkity
 * @created 2021/12/20
 * @since 1.0.0
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Extra {

    /**
     * 字段名
     *
     * @return 字段名
     */
    String name() default "";

    /**
     * 是否为BLOB类型
     *
     * @return boolean
     */
    boolean blob() default false;

    /**
     * JDBC类型
     *
     * @return {@link JdbcType}
     */
    JdbcType jdbcType() default JdbcType.UNDEFINED;

    /**
     * 类型处理器
     *
     * @return {@link TypeHandler}
     */
    Class<? extends TypeHandler<?>> handler() default UnknownTypeHandler.class;

    /**
     * 拼接SQL是否需要加上JAVA类型
     *
     * @return {@link Necessary}
     */
    Necessary spliceJavaType() default Necessary.UNKNOWN;

    /**
     * SQL条件中是否非空校验
     *
     * @return {@link Necessary}
     */
    Necessary checkNull() default Necessary.UNKNOWN;

    /**
     * SQL条件中字符串是否空值校验
     *
     * @return {@link Necessary}
     */
    Necessary checkEmpty() default Necessary.UNKNOWN;

}
