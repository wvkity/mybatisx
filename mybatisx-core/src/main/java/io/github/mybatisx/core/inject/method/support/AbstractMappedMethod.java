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
package io.github.mybatisx.core.inject.method.support;

import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.core.inject.method.MappedMethod;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.session.MyBatisConfiguration;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;

/**
 * 抽象映射方法
 *
 * @author wvkity
 * @created 2021/12/25
 * @since 1.0.0
 */
public abstract class AbstractMappedMethod implements MappedMethod {

    protected MapperBuilderAssistant mba;
    protected Configuration cfg;
    protected LanguageDriver driver;

    @Override
    public void inject(MapperBuilderAssistant mba, Table table, Class<?> mapperInterface, Class<?> returnType) {
        final Configuration config = mba.getConfiguration();
        this.mba = mba;
        this.cfg = config;
        this.driver = config.getDefaultScriptingLanguageInstance();
        if (Objects.isAssignable(MyBatisConfiguration.class, config)) {
            ((MyBatisConfiguration) config).getSupplierRegistry().addSupplier(this.getClass());
        }
        //
    }

    /**
     * 注入{@link org.apache.ibatis.mapping.MappedStatement}对象
     *
     * @param table           {@link Table}
     * @param mapperInterface Mapper接口
     * @param returnType      返回值类型
     */
    public abstract void injectMappedStatement(final Table table, final Class<?> mapperInterface,
                                               final Class<?> returnType);
}
