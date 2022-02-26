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
package io.github.mybatisx.executor.result;

import org.apache.ibatis.executor.result.DefaultMapResultHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.ResultContext;

import java.util.Map;

/**
 * 重写{@link DefaultMapResultHandler}
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author wvkity
 * @created 2021/12/24
 * @since 1.0.0
 */
public class MyBatisMapResultHandler<K, V> extends DefaultMapResultHandler<K, V> {

    private final Map<K, V> mappedResults;
    private final String mapKey;
    private final ObjectFactory objectFactory;
    private final ObjectWrapperFactory objectWrapperFactory;
    private final ReflectorFactory reflectorFactory;

    @SuppressWarnings({"rawtypes", "unchecked"})
    public MyBatisMapResultHandler(String mapKey, Class<? extends Map> returnType,
                                   ObjectFactory objectFactory,
                                   ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory) {
        super(mapKey, objectFactory, objectWrapperFactory, reflectorFactory);
        this.objectFactory = objectFactory;
        this.objectWrapperFactory = objectWrapperFactory;
        this.reflectorFactory = reflectorFactory;
        if (returnType != null) {
            this.mappedResults = objectFactory.create(returnType);
        } else {
            this.mappedResults = objectFactory.create(Map.class);
        }
        this.mapKey = mapKey;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void handleResult(ResultContext<? extends V> context) {
        final V value = context.getResultObject();
        if (value != null && value.getClass().isArray()) {
            // 处理数组类型
            final int index = Integer.parseInt(this.mapKey);
            this.mappedResults.put((K) ((Object[]) value)[index], value);
        } else {
            final MetaObject mo = MetaObject.forObject(value, objectFactory, objectWrapperFactory, reflectorFactory);
            // TODO is that assignment always true?
            final K key = (K) mo.getValue(mapKey);
            mappedResults.put(key, value);
        }
    }

    @Override
    public Map<K, V> getMappedResults() {
        return mappedResults;
    }

}
