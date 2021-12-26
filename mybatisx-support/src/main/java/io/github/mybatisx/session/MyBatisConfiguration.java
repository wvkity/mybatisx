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
package io.github.mybatisx.session;

import io.github.mybatisx.binding.MyBatisMapperRegistry;
import io.github.mybatisx.executor.MyBatisBatchExecutor;
import io.github.mybatisx.executor.MyBatisReuseExecutor;
import io.github.mybatisx.executor.MyBatisSimpleExecutor;
import io.github.mybatisx.executor.resultset.MyBatisResultSetHandler;
import io.github.mybatisx.scripting.xmltags.MyBatisXMLLanguageDriver;
import io.github.mybatisx.support.mapping.SqlSupplier;
import io.github.mybatisx.support.mapping.SqlSupplierRegistry;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * 重写{@link Configuration}
 *
 * @author wvkity
 * @created 2021/12/24
 * @since 1.0.0
 */
public class MyBatisConfiguration extends Configuration {

    private static final Log log = LogFactory.getLog(MyBatisConfiguration.class);
    protected final SqlSupplierRegistry supplierRegistry = new SqlSupplierRegistry(this);
    protected final MyBatisMapperRegistry myBatisMapperRegistry = new MyBatisMapperRegistry(this);
    protected final Map<String, MappedStatement> mappedStatements = new StrictMap<MappedStatement>("Mapped Statements collection")
            .conflictMessageProducer((savedValue, targetValue) ->
                    ". please check " + savedValue.getResource() + " and " + targetValue.getResource());
    protected final Map<String, Cache> caches = new StrictMap<>("Caches collection");
    protected final Map<String, ResultMap> resultMaps = new StrictMap<>("Result Maps collection");
    protected final Map<String, ParameterMap> parameterMaps = new StrictMap<>("Parameter Maps collection");
    protected final Map<String, KeyGenerator> keyGenerators = new StrictMap<>("Key Generators collection");
    protected final Map<String, XNode> sqlFragments = new StrictMap<>("XML fragments parsed from previous mappers");

    public MyBatisConfiguration(Environment environment) {
        this();
        this.environment = environment;
    }

    public MyBatisConfiguration() {
        super();
        // 默认开启驼峰
        this.mapUnderscoreToCamelCase = true;
        languageRegistry.setDefaultDriverClass(MyBatisXMLLanguageDriver.class);
    }

    @Override
    public void addMappedStatement(MappedStatement ms) {
        final String msId = ms.getId();
        if (this.mappedStatements.containsKey(msId)) {
            log.warn("The mapping method [" + msId + "] has been loaded from the XML file and is " +
                    "automatically ignored by the system");
            return;
        }
        this.mappedStatements.putIfAbsent(msId, ms);
    }

    @Override
    public MapperRegistry getMapperRegistry() {
        return this.myBatisMapperRegistry;
    }

    @Override
    public <T> void addMapper(Class<T> type) {
        this.myBatisMapperRegistry.addMapper(type);
    }

    @Override
    public void addMappers(String packageName, Class<?> superType) {
        this.myBatisMapperRegistry.addMappers(packageName, superType);
    }

    @Override
    public void addMappers(String packageName) {
        this.myBatisMapperRegistry.addMappers(packageName);
    }

    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return this.myBatisMapperRegistry.getMapper(type, sqlSession);
    }

    @Override
    public boolean hasMapper(Class<?> type) {
        return this.myBatisMapperRegistry.hasMapper(type);
    }

    public SqlSupplierRegistry getSupplierRegistry() {
        return this.supplierRegistry;
    }
    
    public void addSupplier(final Class<?> type) {
        this.supplierRegistry.addSupplier(type);
    }

    public <T> SqlSupplier getSupplier(final Class<T> type, final Object... args) {
        return this.supplierRegistry.getSupplier(type, args);
    }

    public <T> boolean hasSupplier(final Class<T> type) {
        return this.supplierRegistry.hasSuppler(type);
    }

    @Override
    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement,
                                                RowBounds rowBounds, ParameterHandler parameterHandler,
                                                ResultHandler resultHandler, BoundSql boundSql) {
        final ResultSetHandler rsh = new MyBatisResultSetHandler(executor, mappedStatement, parameterHandler,
                resultHandler, boundSql, rowBounds);
        return (ResultSetHandler) this.interceptorChain.pluginAll(rsh);
    }

    @Override
    public void setDefaultScriptingLanguage(Class<? extends LanguageDriver> driver) {
        if (driver == null) {
            super.setDefaultScriptingLanguage(MyBatisXMLLanguageDriver.class);
        } else {
            super.setDefaultScriptingLanguage(driver);
        }
    }

    @Override
    public void addKeyGenerator(String id, KeyGenerator keyGenerator) {
        this.keyGenerators.put(id, keyGenerator);
    }

    @Override
    public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
        executorType = executorType == null ? defaultExecutorType : executorType;
        executorType = executorType == null ? ExecutorType.SIMPLE : executorType;
        Executor executor;
        if (ExecutorType.BATCH == executorType) {
            executor = new MyBatisBatchExecutor(this, transaction);
        } else if (ExecutorType.REUSE == executorType) {
            executor = new MyBatisReuseExecutor(this, transaction);
        } else {
            executor = new MyBatisSimpleExecutor(this, transaction);
        }
        if (cacheEnabled) {
            executor = new CachingExecutor(executor);
        }
        executor = (Executor) interceptorChain.pluginAll(executor);
        return executor;
    }

    @Override
    public Collection<String> getKeyGeneratorNames() {
        return this.keyGenerators.keySet();
    }

    @Override
    public Collection<KeyGenerator> getKeyGenerators() {
        return this.keyGenerators.values();
    }

    @Override
    public KeyGenerator getKeyGenerator(String id) {
        return this.keyGenerators.get(id);
    }

    @Override
    public boolean hasKeyGenerator(String id) {
        return this.keyGenerators.containsKey(id);
    }

    @Override
    public void addCache(Cache cache) {
        this.caches.put(cache.getId(), cache);
    }

    @Override
    public Collection<String> getCacheNames() {
        return this.caches.keySet();
    }

    @Override
    public Collection<Cache> getCaches() {
        return this.caches.values();
    }

    @Override
    public Cache getCache(String id) {
        return this.caches.get(id);
    }

    @Override
    public boolean hasCache(String id) {
        return this.caches.containsKey(id);
    }

    @Override
    public void addResultMap(ResultMap rm) {
        this.resultMaps.put(rm.getId(), rm);
        this.checkLocallyForDiscriminatedNestedResultMaps(rm);
        this.checkGloballyForDiscriminatedNestedResultMaps(rm);
    }

    @Override
    public Collection<String> getResultMapNames() {
        return this.resultMaps.keySet();
    }

    @Override
    public Collection<ResultMap> getResultMaps() {
        return this.resultMaps.values();
    }

    @Override
    public ResultMap getResultMap(String id) {
        return this.resultMaps.get(id);
    }

    @Override
    public boolean hasResultMap(String id) {
        return this.resultMaps.containsKey(id);
    }

    @Override
    public void addParameterMap(ParameterMap pm) {
        this.parameterMaps.put(pm.getId(), pm);
    }

    @Override
    public Collection<String> getParameterMapNames() {
        return this.parameterMaps.keySet();
    }

    @Override
    public Collection<ParameterMap> getParameterMaps() {
        return this.parameterMaps.values();
    }

    @Override
    public ParameterMap getParameterMap(String id) {
        return this.parameterMaps.get(id);
    }

    @Override
    public boolean hasParameterMap(String id) {
        return this.parameterMaps.containsKey(id);
    }

    @Override
    public Collection<String> getMappedStatementNames() {
        this.buildAllStatements();
        return this.mappedStatements.keySet();
    }

    @Override
    public Collection<MappedStatement> getMappedStatements() {
        this.buildAllStatements();
        return this.mappedStatements.values();
    }

    @Override
    public MappedStatement getMappedStatement(String id) {
        return this.mappedStatements.get(id);
    }

    @Override
    public MappedStatement getMappedStatement(String id, boolean validateIncompleteStatements) {
        if (validateIncompleteStatements) {
            this.buildAllStatements();
        }
        return this.mappedStatements.get(id);
    }

    @Override
    public Map<String, XNode> getSqlFragments() {
        return this.sqlFragments;
    }

    @Override
    public boolean hasStatement(String statementName) {
        return this.hasStatement(statementName, true);
    }

    @Override
    public boolean hasStatement(String statementName, boolean validateIncompleteStatements) {
        if (validateIncompleteStatements) {
            this.buildAllStatements();
        }
        return this.mappedStatements.containsKey(statementName);
    }

    @Override
    protected void checkGloballyForDiscriminatedNestedResultMaps(ResultMap rm) {
        if (rm.hasNestedResultMaps()) {
            for (Map.Entry<String, ResultMap> entry : this.resultMaps.entrySet()) {
                final ResultMap entryResultMap = entry.getValue();
                if (entryResultMap != null) {
                    if (!entryResultMap.hasNestedResultMaps() && entryResultMap.getDiscriminator() != null) {
                        Collection<String> discriminatedResultMapNames = entryResultMap.getDiscriminator().getDiscriminatorMap().values();
                        if (discriminatedResultMapNames.contains(rm.getId())) {
                            entryResultMap.forceNestedResultMaps();
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void checkLocallyForDiscriminatedNestedResultMaps(ResultMap rm) {
        if (!rm.hasNestedResultMaps() && rm.getDiscriminator() != null) {
            for (Map.Entry<String, String> entry : rm.getDiscriminator().getDiscriminatorMap().entrySet()) {
                String discriminatedResultMapName = entry.getValue();
                if (hasResultMap(discriminatedResultMapName)) {
                    ResultMap discriminatedResultMap = this.resultMaps.get(discriminatedResultMapName);
                    if (discriminatedResultMap.hasNestedResultMaps()) {
                        rm.forceNestedResultMaps();
                        break;
                    }
                }
            }
        }
    }

    protected static class StrictMap<V> extends HashMap<String, V> {

        private static final long serialVersionUID = -4950446264854982944L;
        private final String name;
        private BiFunction<V, V, String> conflictMessageProducer;

        public StrictMap(String name, int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
            this.name = name;
        }

        public StrictMap(String name, int initialCapacity) {
            super(initialCapacity);
            this.name = name;
        }

        public StrictMap(String name) {
            super();
            this.name = name;
        }

        public StrictMap(String name, Map<String, ? extends V> m) {
            super(m);
            this.name = name;
        }

        /**
         * Assign a function for producing a conflict error message when contains value with the same key.
         * <p>
         * function arguments are 1st is saved value and 2nd is target value.
         *
         * @param conflictMessageProducer A function for producing a conflict error message
         * @return a conflict error message
         * @since 3.5.0
         */
        public StrictMap<V> conflictMessageProducer(BiFunction<V, V, String> conflictMessageProducer) {
            this.conflictMessageProducer = conflictMessageProducer;
            return this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public V put(String key, V value) {
            if (containsKey(key)) {
                throw new IllegalArgumentException(name + " already contains value for " + key
                        + (conflictMessageProducer == null ? "" : conflictMessageProducer.apply(super.get(key), value)));
            }
            if (key.contains(".")) {
                final String shortKey = getShortName(key);
                if (super.get(shortKey) == null) {
                    super.put(shortKey, value);
                } else {
                    super.put(shortKey, (V) new StrictMap.Ambiguity(shortKey));
                }
            }
            return super.put(key, value);
        }

        @Override
        public V get(Object key) {
            V value = super.get(key);
            if (value == null) {
                throw new IllegalArgumentException(name + " does not contain value for " + key);
            }
            if (value instanceof StrictMap.Ambiguity) {
                throw new IllegalArgumentException(((StrictMap.Ambiguity) value).getSubject() + " is ambiguous in " + name
                        + " (try using the full name including the namespace, or rename one of the entries)");
            }
            return value;
        }

        protected static class Ambiguity {
            final private String subject;

            public Ambiguity(String subject) {
                this.subject = subject;
            }

            public String getSubject() {
                return subject;
            }
        }

        private String getShortName(String key) {
            final String[] keyParts = key.split("\\.");
            return keyParts[keyParts.length - 1];
        }
    }
}
