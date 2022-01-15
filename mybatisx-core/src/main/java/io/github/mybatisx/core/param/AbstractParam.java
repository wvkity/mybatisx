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
package io.github.mybatisx.core.param;

import io.github.mybatisx.base.constant.LogicSymbol;
import io.github.mybatisx.base.constant.Symbol;
import io.github.mybatisx.core.mapping.Scripts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

/**
 * 抽象参数
 *
 * @author wvkity
 * @created 2022/1/7
 * @since 1.0.0
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractParam {

    /**
     * 条件符号
     */
    @Getter
    protected Symbol symbol;
    /**
     * 逻辑符号
     */
    @Getter
    @Setter
    @Builder.Default
    protected LogicSymbol slot = LogicSymbol.NONE;
    /**
     * 类型处理器
     */
    @Builder.Default
    protected Class<? extends TypeHandler<?>> typeHandler = UnknownTypeHandler.class;
    /**
     * Jdbc类型
     */
    @Builder.Default
    protected JdbcType jdbcType = JdbcType.UNDEFINED;
    /**
     * Java类型
     */
    protected Class<?> javaType;
    /**
     * 是否拼接Java类型
     */
    @Builder.Default
    protected boolean spliceJavaType = false;

    /**
     * 转成条件参数
     *
     * @param placeholders 占位符参数列表
     * @return 条件参数字符串
     */
    protected String toConditionArg(final String... placeholders) {
        return Scripts.toConditionArg(this.symbol, this.slot, this.typeHandler, this.jdbcType, this.spliceJavaType,
                this.javaType, placeholders);
    }

}
