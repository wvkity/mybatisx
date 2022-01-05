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
package io.github.mybatisx.base.parsing;

import com.google.common.collect.ImmutableSet;
import io.github.mybatisx.annotation.Column;
import io.github.mybatisx.annotation.Entity;
import io.github.mybatisx.annotation.ExecuteType;
import io.github.mybatisx.annotation.Extra;
import io.github.mybatisx.annotation.GeneratedValue;
import io.github.mybatisx.annotation.GenerationType;
import io.github.mybatisx.annotation.Id;
import io.github.mybatisx.annotation.IdType;
import io.github.mybatisx.annotation.Identity;
import io.github.mybatisx.annotation.LogicDelete;
import io.github.mybatisx.annotation.Naming;
import io.github.mybatisx.annotation.NamingStrategy;
import io.github.mybatisx.annotation.Necessary;
import io.github.mybatisx.annotation.Priority;
import io.github.mybatisx.annotation.Snowflake;
import io.github.mybatisx.annotation.Version;
import io.github.mybatisx.auditable.config.AuditConfig;
import io.github.mybatisx.auditable.matcher.AuditMatcher;
import io.github.mybatisx.auditable.parsing.AuditAutoScanParser;
import io.github.mybatisx.auditable.parsing.AuditParser;
import io.github.mybatisx.auditable.parsing.DefaultAuditPropertyAutoScanParser;
import io.github.mybatisx.auditable.parsing.DefaultAuditPropertyParser;
import io.github.mybatisx.base.builder.ColumnBuilder;
import io.github.mybatisx.base.builder.TableBuilder;
import io.github.mybatisx.base.config.LogicDeleteConfig;
import io.github.mybatisx.base.config.MatcherConfig;
import io.github.mybatisx.base.config.MyBatisGlobalConfig;
import io.github.mybatisx.base.config.MyBatisGlobalConfigCache;
import io.github.mybatisx.base.config.OptimisticLockConfig;
import io.github.mybatisx.base.config.PrimaryKeyConfig;
import io.github.mybatisx.base.constant.Jpa;
import io.github.mybatisx.base.exception.MyBatisParserException;
import io.github.mybatisx.base.matcher.ClassMatcher;
import io.github.mybatisx.base.matcher.DefaultClassMatcher;
import io.github.mybatisx.base.matcher.DefaultFieldMatcher;
import io.github.mybatisx.base.matcher.FieldMatcher;
import io.github.mybatisx.base.matcher.GetterMatcher;
import io.github.mybatisx.base.matcher.ReadableMatcher;
import io.github.mybatisx.base.matcher.SetterMatcher;
import io.github.mybatisx.base.matcher.WritableMatcher;
import io.github.mybatisx.base.metadata.Table;
import io.github.mybatisx.base.naming.ColumnNamingConverter;
import io.github.mybatisx.base.naming.NamingConverter;
import io.github.mybatisx.base.naming.TableNamingConverter;
import io.github.mybatisx.base.reflect.DefaultReflector;
import io.github.mybatisx.base.reflect.Reflector;
import io.github.mybatisx.base.type.JdbcTypeMappingRegistry;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.lang.Types;
import io.github.mybatisx.reflect.FieldWrapper;
import io.github.mybatisx.reflect.Metadata;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.UnknownTypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

/**
 * 默认实体类解析器
 *
 * @author wvkity
 * @created 2021/12/26
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class DefaultEntityParser implements EntityParser {

    private static final Logger log = LoggerFactory.getLogger(DefaultEntityParser.class);
    public static final String PRIMARY_KEY_SNOWFLAKE = "SNOWFLAKE";
    public static final String PRIMARY_KEY_IDENTITY = "IDENTITY";
    public static final String PRIMARY_KEY_SEQUENCE = "SEQUENCE";
    public static final String PRIMARY_KEY_UUID = "UUID";
    public static final String PRIMARY_KEY_JDBC = "JDBC";
    public static final Set<Class<?>> OPTIMISTIC_LOCK_SUPPORT_CLASSES = ImmutableSet.of(int.class,
            Integer.class, long.class, Long.class, Date.class, LocalDateTime.class, OffsetDateTime.class,
            Instant.class, Timestamp.class);
    private static final String LOCK_CLASSES_NAME = Arrays.toString(OPTIMISTIC_LOCK_SUPPORT_CLASSES.stream()
            .map(Class::getName).toArray());

    @Override
    public Table parse(Configuration cfg, Class<?> entityClass, String namespace) {
        final MyBatisGlobalConfig mgc = MyBatisGlobalConfigCache.getGlobalConfig(cfg);
        final TableBuilder tb = TableBuilder.create();
        tb.entity(entityClass);
        tb.namespace(namespace);
        tb.keywordFormatTemplate(mgc.getKeywordFormatTemplate());
        this.initConfig(mgc);
        // 反射器
        final MatcherConfig mc = mgc.getMatchers();
        final ClassMatcher classMatcher = this.getClassMatcher(mgc, mc);
        final FieldMatcher fieldMatcher = this.getFieldMatcher(mgc, mc);
        final GetterMatcher readableMatcher = this.getReadableMatcher(mgc, mc);
        final SetterMatcher writableMatcher = this.getWritableMatcher(mgc, mc);
        final DefaultReflector reflector = new DefaultReflector(entityClass, classMatcher, fieldMatcher,
                readableMatcher, writableMatcher);

        // 命名策略
        final NamingStrategy format = this.naming(mgc, reflector);
        tb.naming(format).namingConverter(TableNamingConverter.of(getEntitySrcNaming(mgc), format));

        this.handleEntityAnnotation(mgc, reflector, tb);
        this.handleEntityProperties(mgc, reflector, tb, format,
                ColumnNamingConverter.of(getPropertySrcNaming(mgc), format));
        return tb.build();
    }

    /**
     * 初始化配置
     *
     * @param mgc {@link MyBatisGlobalConfig}
     */
    private void initConfig(final MyBatisGlobalConfig mgc) {
        // 匹配器配置
        if (Objects.isNull(mgc.getMatchers())) {
            mgc.setMatchers(new MatcherConfig());
        }
        // 主键配置
        if (Objects.isNull(mgc.getPrimaryKey())) {
            mgc.setPrimaryKey(PrimaryKeyConfig.of());
        }
        // 乐观锁配置
        if (Objects.isNull(mgc.getOptimisticLock())) {
            mgc.setOptimisticLock(OptimisticLockConfig.of());
        }
        // 逻辑删除配置
        if (Objects.isNull(mgc.getLogicDelete())) {
            mgc.setLogicDelete(LogicDeleteConfig.of());
        }
        // 审计配置
        if (Objects.isNull(mgc.getAudit())) {
            mgc.setAudit(AuditConfig.of());
        }
    }

    /**
     * 处理实体类上的注解
     *
     * @param mgc       {@link MyBatisGlobalConfig}
     * @param reflector {@link Reflector}
     * @param tb        {@link TableBuilder}
     */
    private void handleEntityAnnotation(final MyBatisGlobalConfig mgc, final Reflector reflector,
                                        final TableBuilder tb) {
        final io.github.mybatisx.annotation.Table table;
        if (Objects.nonNull((table = reflector.getAnnotation(io.github.mybatisx.annotation.Table.class)))) {
            tb.name(table.name()).schema(table.schema()).catalog(table.catalog()).prefix(table.prefix());
        } else if (mgc.isJpaSupport() && reflector.isMatches(Jpa.TABLE)) {
            final Metadata metadata = reflector.getMetadata(Jpa.TABLE);
            tb.name(metadata.stringValue(Jpa.TABLE_PROP_NAME));
            tb.catalog(metadata.stringValue(Jpa.TABLE_PROP_CATALOG));
            tb.schema(metadata.stringValue(Jpa.TABLE_PROP_SCHEMA));
        }
        if (Strings.isWhitespace(tb.name())) {
            final Entity entity;
            if (Objects.nonNull((entity = reflector.getAnnotation(Entity.class)))) {
                tb.name(entity.name());
            } else if (mgc.isJpaSupport() && reflector.isMatches(Jpa.ENTITY)) {
                tb.name(reflector.getMetadata(Jpa.ENTITY).stringValue(Jpa.ENTITY_PROP_NAME));
            }
        }
        if (Strings.isWhitespace(tb.prefix())) {
            tb.prefix(mgc.getTablePrefix());
        }
        if (Strings.isWhitespace(tb.schema())) {
            tb.schema(mgc.getSchema());
        }
        if (Strings.isWhitespace(tb.catalog())) {
            tb.catalog(mgc.getCatalog());
        }
    }

    /**
     * 处理实体类属性
     *
     * @param mgc       {@link MyBatisGlobalConfig}
     * @param reflector {@link Reflector}
     * @param tb        {@link TableBuilder}
     * @param naming    {@link NamingStrategy}
     * @param converter {@link NamingStrategy}
     */
    private void handleEntityProperties(final MyBatisGlobalConfig mgc, final Reflector reflector,
                                        final TableBuilder tb, final NamingStrategy naming,
                                        final NamingConverter converter) {
        final List<FieldWrapper> fields = reflector.getFields();
        if (!fields.isEmpty()) {
            final Class<?> entityClass = reflector.getType();
            final boolean autoAddedIsPrefix = mgc.isAutoAddedIsPrefixForBooleanProp();
            fields.forEach(it -> {
                final ColumnBuilder cb = ColumnBuilder.create();
                cb.entity(entityClass).naming(naming).namingConverter(converter);
                cb.keywordFormatTemplate(mgc.getKeywordFormatTemplate());
                cb.property(it.getName()).autoAddedIsPrefix(autoAddedIsPrefix);
                cb.field(it.getField()).javaType(it.getJavaType());
                cb.readable(it.getGetter()).writable(it.getSetter());
                this.handlePropertyAnnotation(mgc, reflector, tb, cb, it);
                tb.add(cb);
            });
        }
    }

    /**
     * 处理属性
     *
     * @param mgc       {@link MyBatisGlobalConfig}
     * @param reflector {@link Reflector}
     * @param tb        {@link TableBuilder}
     * @param cb        {@link ColumnBuilder}
     * @param field     {@link FieldWrapper}
     */
    private void handlePropertyAnnotation(final MyBatisGlobalConfig mgc, final Reflector reflector,
                                          final TableBuilder tb, final ColumnBuilder cb,
                                          final FieldWrapper field) {
        this.handlePrimaryKeyAnnotation(mgc, reflector, cb, field);
        this.handleColumnAnnotation(mgc, reflector, cb, field);
        this.handleAutoDiscernPrimaryKey(mgc, reflector, cb, field);
        if (cb.primaryKey()) {
            cb.insertable(true).updatable(false);
            final boolean hasPrimaryKey = tb.hasPrimaryKey();
            final boolean isPriority = field.isMatches(Priority.class);
            if (hasPrimaryKey) {
                tb.onlyOnePrimaryKey(false);
            }
            if (!hasPrimaryKey || isPriority) {
                tb.primaryKeyColumn(cb).primaryKeyProperty(cb.property());
            }
            cb.priority(isPriority);
            this.handlePrimaryKeyGenerated(mgc, reflector, tb, cb, field);
            tb.addPrimaryKey(cb);
        } else {
            this.handleVersionAnnotation(mgc, reflector, tb, cb, field);
            this.handleLogicDeleteAnnotation(mgc, reflector, tb, cb, field);
            this.handleAuditAnnotation(mgc, reflector, tb, cb, field);
        }
    }

    /**
     * 处理主键注解
     *
     * @param mgc       {@link MyBatisGlobalConfig}
     * @param reflector {@link Reflector}
     * @param cb        {@link ColumnBuilder}
     * @param field     {@link FieldWrapper}
     */
    private void handlePrimaryKeyAnnotation(final MyBatisGlobalConfig mgc, final Reflector reflector,
                                            final ColumnBuilder cb, final FieldWrapper field) {
        cb.primaryKey((field.isMatches(Arrays.asList(Id.class, Identity.class, Snowflake.class))
                || (mgc.isJpaSupport() && field.isMatches(Jpa.ID))));
    }

    /**
     * 处理@Column、@Extra注解
     *
     * @param mgc       {@link MyBatisGlobalConfig}
     * @param reflector {@link Reflector}
     * @param cb        {@link ColumnBuilder}
     * @param field     {@link FieldWrapper}
     */
    private void handleColumnAnnotation(final MyBatisGlobalConfig mgc, final Reflector reflector,
                                        final ColumnBuilder cb, final FieldWrapper field) {
        final Column column;
        if (Objects.nonNull((column = field.getAnnotation(Column.class)))) {
            cb.column(column.name()).insertable(column.insertable()).updatable(column.updatable());
        } else if (mgc.isJpaSupport() && field.isMatches(Jpa.COLUMN)) {
            final Metadata metadata = field.getMetadata(Jpa.COLUMN);
            cb.column(metadata.stringValue(Jpa.COLUMN_PROP_NAME));
            cb.insertable(metadata.boolValue(Jpa.COLUMN_PROP_INSERTABLE));
            cb.updatable(metadata.boolValue(Jpa.COLUMN_PROP_UPDATABLE));
        }
        final Extra extra;
        if (Objects.nonNull((extra = field.getAnnotation(Extra.class)))) {
            cb.blob(extra.blob()).jdbcType(extra.jdbcType());
            final Necessary spliceJavaType = extra.spliceJavaType();
            final Necessary notNull = extra.checkNull();
            final Necessary notEmpty = extra.checkEmpty();
            cb.spliceJavaType(spliceJavaType == Necessary.REQUIRE);
            cb.checkNull(notNull == Necessary.REQUIRE);
            cb.checkEmpty(notEmpty == Necessary.REQUIRE);
            if (Strings.isWhitespace(cb.column())) {
                cb.column(extra.name());
            }
            if (extra.handler() != UnknownTypeHandler.class) {
                cb.typeHandler(extra.handler());
            }
            if (spliceJavaType == Necessary.UNKNOWN) {
                cb.spliceJavaType(mgc.isSpliceJavaType());
            }
            if (notNull == Necessary.REQUIRE) {
                cb.checkNull(mgc.isCheckNull());
            }
            if (notEmpty == Necessary.UNKNOWN) {
                cb.checkEmpty(mgc.isCheckEmpty());
            }
        } else {
            cb.spliceJavaType(mgc.isSpliceJavaType());
            cb.checkNull(mgc.isCheckNull());
            cb.checkEmpty(mgc.isCheckEmpty());
        }
        if (cb.checkEmpty()) {
            if (Types.is(String.class, cb.javaType())) {
                cb.checkNull(true);
            } else {
                cb.checkEmpty(false);
            }
        }
        final JdbcType jdbcType;
        if ((Objects.isNull((jdbcType = cb.jdbcType())) || jdbcType == JdbcType.UNDEFINED)
                && mgc.isAutoMappingJdbcType()) {
            cb.jdbcType(JdbcTypeMappingRegistry.getJdbcType(cb.javaType()));
        }
        if (cb.javaType().isPrimitive()) {
            cb.checkNull(false).checkEmpty(false);
            log.warn("The \"{}\" attribute in the entity class \"{}\" is defined as a primitive type. " +
                            "The primitive type is not null at any time in dynamic SQL because it has a default " +
                            "value. It is recommended to modify the primitive type to the corresponding wrapper type.",
                    cb.property(), cb.entity().getName());
        }
    }

    /**
     * 自动识别主键
     *
     * @param mgc       {@link MyBatisGlobalConfig}
     * @param reflector {@link Reflector}
     * @param cb        {@link ColumnBuilder}
     * @param field     {@link FieldWrapper}
     */
    private void handleAutoDiscernPrimaryKey(final MyBatisGlobalConfig mgc, final Reflector reflector,
                                             final ColumnBuilder cb, final FieldWrapper field) {
        final PrimaryKeyConfig primaryKey = mgc.getPrimaryKey();
        if (!cb.primaryKey() && primaryKey.isAutoScan()) {
            final String primaryKeyProp;
            cb.primaryKey(Strings.isNotWhitespace((primaryKeyProp = primaryKey.getProperty()))
                    && cb.property().equals(primaryKeyProp));
        }
    }

    /**
     * 处理主键生成策略
     *
     * @param mgc       {@link MyBatisGlobalConfig}
     * @param reflector {@link Reflector}
     * @param tb        {@link TableBuilder}
     * @param cb        {@link ColumnBuilder}
     * @param field     {@link FieldWrapper}
     */
    private void handlePrimaryKeyGenerated(final MyBatisGlobalConfig mgc, final Reflector reflector,
                                           final TableBuilder tb, final ColumnBuilder cb, final FieldWrapper field) {
        if (field.isMatches(Identity.class)) {
            this.handleIdentityAnnotation(mgc, tb, cb, field);
        } else if (field.isMatches(GeneratedValue.class) || (mgc.isJpaSupport()
                && field.isMatches(Jpa.GENERATED_VALUE))) {
            this.handleGeneratedValueAnnotation(mgc, tb, cb, field);
        } else if (field.isMatches(Snowflake.class)) {
            cb.snowflake(true).executeType(ExecuteType.BEFORE);
        }
        final IdType strategy;
        if (!cb.hasPrimaryKeyStrategy() && ((strategy = mgc.getPrimaryKey().getStrategy()) != IdType.UNKNOWN)) {
            cb.identity(strategy == IdType.JDBC || strategy == IdType.IDENTITY);
            cb.uuid(strategy == IdType.UUID);
            cb.snowflake(strategy == IdType.SNOWFLAKE);
        }
    }

    /**
     * 处理{@link Identity @Identity}注解
     *
     * @param mgc   {@link MyBatisGlobalConfig}
     * @param tb    {@link TableBuilder}
     * @param cb    {@link ColumnBuilder}
     * @param field {@link FieldWrapper}
     */
    private void handleIdentityAnnotation(final MyBatisGlobalConfig mgc, final TableBuilder tb,
                                          final ColumnBuilder cb, final FieldWrapper field) {
        final Identity identity = field.getAnnotation(Identity.class);
        if (identity.jdbc()) {
            cb.identity(true).executeType(ExecuteType.AFTER).generator("JDBC");
        } else {
            final ExecuteType strategy = identity.strategy();
            final boolean isAfter = strategy == ExecuteType.AFTER ||
                    (strategy == ExecuteType.UNKNOWN && mgc.getPrimaryKey().isAfter());
            if (isAfter) {
                cb.identity(true).executeType(ExecuteType.AFTER).generator("");
            } else {
                final String sequence;
                if (Strings.isWhitespace((sequence = identity.sequence()))) {
                    throw new MyBatisParserException("Invalid \"@Identity\" annotation exists on the \"" +
                            cb.property() + "\" attribute of the entity class \"" + tb.entity().getName() + "\".");
                }
                cb.identity(true).executeType(ExecuteType.BEFORE).generator(sequence);
            }
        }
    }

    /**
     * 处理{@link GeneratedValue @GeneratedValue}注解
     *
     * @param mgc   {@link MyBatisGlobalConfig}
     * @param tb    {@link TableBuilder}
     * @param cb    {@link ColumnBuilder}
     * @param field {@link FieldWrapper}
     */
    private void handleGeneratedValueAnnotation(final MyBatisGlobalConfig mgc, final TableBuilder tb,
                                                final ColumnBuilder cb, final FieldWrapper field) {
        final String generator;
        final boolean isIdentity;
        final boolean isSequence;
        final GeneratedValue generatedValue;
        if (Objects.nonNull((generatedValue = field.getAnnotation(GeneratedValue.class)))) {
            final GenerationType strategy = generatedValue.strategy();
            generator = generatedValue.generator();
            isIdentity = strategy == GenerationType.IDENTITY;
            isSequence = !isIdentity && strategy == GenerationType.SEQUENCE;
        } else {
            final Metadata metadata = field.getMetadata(Jpa.GENERATED_VALUE);
            generator = metadata.stringValue(Jpa.GV_PROP_GENERATOR);
            final Enum<?> enumValue = metadata.enumValue(Jpa.GV_PROP_STRATEGY);
            if (Objects.nonNull(enumValue)) {
                isIdentity = PRIMARY_KEY_IDENTITY.equals(enumValue.name().toUpperCase(Locale.ENGLISH));
                isSequence = !isIdentity && PRIMARY_KEY_SEQUENCE.equals(enumValue.name().toUpperCase(Locale.ENGLISH));
            } else {
                isIdentity = false;
                isSequence = false;
            }
        }
        if (PRIMARY_KEY_UUID.equalsIgnoreCase(generator)) {
            cb.uuid(true);
        } else if (PRIMARY_KEY_JDBC.equalsIgnoreCase(generator)) {
            cb.identity(true).generator(generator.toLowerCase(Locale.ENGLISH));
        } else if (PRIMARY_KEY_SNOWFLAKE.equalsIgnoreCase(generator)) {
            cb.snowflake(true).executeType(ExecuteType.BEFORE);
        } else {
            if (isIdentity) {
                cb.identity(true).generator(generator);
            }
            if (isSequence && Strings.isNotWhitespace(generator)) {
                cb.sequence(generator);
            } else {
                throw new MyBatisParserException("Invalid \"@GeneratedValue\" annotation exists on the \"" +
                        cb.property() + "\" attribute of the entity class \"" + tb.entity().getName() + "\", " +
                        "The \"@GeneratedValue\" annotation supports the following form: " +
                        "\n\t\t\t 1.@GeneratedValue(generator = \"UUID\")" +
                        "\n\t\t\t 2.@GeneratedValue(generator = \"JDBC\")" +
                        "\n\t\t\t 3.@GeneratedValue(generator = \"SNOWFLAKE\")" +
                        "\n\t\t\t 4.@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = \"SequenceName\")" +
                        "\n\t\t\t 5.@GeneratedValue(strategy = GenerationType.IDENTITY, generator = \"[MySql, MSSQL.." +
                        ".]\")");
            }
        }
    }

    /**
     * 处理{@link Version @Version}注解
     *
     * @param mgc       {@link MyBatisGlobalConfig}
     * @param reflector {@link Reflector}
     * @param tb        {@link TableBuilder}
     * @param cb        {@link ColumnBuilder}
     * @param field     {@link FieldWrapper}
     */
    private void handleVersionAnnotation(final MyBatisGlobalConfig mgc, final Reflector reflector,
                                         final TableBuilder tb, final ColumnBuilder cb, final FieldWrapper field) {
        final OptimisticLockConfig lock = mgc.getOptimisticLock();
        boolean isVersion = false;
        int initValue = lock.getInitValue();
        boolean isInit = lock.isInitialize();
        final Version version;
        if (Objects.nonNull((version = field.getAnnotation(Version.class)))) {
            final Necessary init = version.initialize();
            initValue = version.value() <= 0 ? lock.getInitValue() : version.value();
            isVersion = true;
            isInit = init == Necessary.REQUIRE || (init == Necessary.UNKNOWN && lock.isInitialize());
        } else if (mgc.isJpaSupport() && field.isMatches(Jpa.VERSION)) {
            isVersion = true;
        } else if (lock.isAutoScan() && Objects.isNotEmpty(lock.getProperties())
                && lock.getProperties().contains(cb.property())) {
            isVersion = true;
        }
        if (isVersion) {
            if (cb.primaryKey()) {
                throw new MyBatisParserException("The attribute \"" + cb.property() + "\" of the entity class \"" +
                        tb.entity().getName() + "\" is the primary key. The primary key is not updatable. " +
                        "Adding \"@version\" annotation is not supported.");
            }
            if (!cb.updatable()) {
                throw new MyBatisParserException("The attribute \"" + cb.property() + "\" of the entity class \"" +
                        tb.entity().getName() + "\" is not updatable and adding \"@version\" annotation is not supported.");
            }
            if (!OPTIMISTIC_LOCK_SUPPORT_CLASSES.contains(cb.javaType())) {
                throw new MyBatisParserException("The type of the \"" + cb.property() + "\" attribute of the " +
                        "entity class \"" + tb.entity().getName() + "\" is not within the type range specified(" +
                        LOCK_CLASSES_NAME + ") by the optimistic lock. Adding the \"@version\" annotation is not supported.");
            }
            if (Objects.nonNull(tb.optimisticLockColumn())) {
                throw new MyBatisParserException("The entity class \"" + tb.entity().getName() + "\" already has " +
                        "the optimistic lock attribute \"" + tb.optimisticLockColumn().property() + "\". " +
                        "The entity class supports an optimistic lock attribute, so attribute \"" + cb.property() +
                        "\" does not support adding the \"@version\" annotation.");
            }
            cb.version(true).versionInit(isInit);
            tb.optimisticLockColumn(cb);
            if (Types.isPrimitiveOrWrapType(cb.javaType())) {
                cb.versionInitValue(initValue);
            }
        }
    }

    /**
     * 处理{@link LogicDelete @Logicdelete}注解
     *
     * @param mgc       {@link MyBatisGlobalConfig}
     * @param reflector {@link Reflector}
     * @param tb        {@link TableBuilder}
     * @param cb        {@link ColumnBuilder}
     * @param field     {@link FieldWrapper}
     */
    private void handleLogicDeleteAnnotation(final MyBatisGlobalConfig mgc, final Reflector reflector,
                                             final TableBuilder tb, final ColumnBuilder cb, final FieldWrapper field) {
        final LogicDelete ld;
        final boolean hasAnnotation;
        final LogicDeleteConfig ldc = mgc.getLogicDelete();
        final Set<String> properties;
        if ((hasAnnotation = Objects.nonNull((ld = field.getAnnotation(LogicDelete.class))))
                || (ldc.isAutoScan() && Objects.isNotEmpty((properties = ldc.getProperties()))
                && properties.contains(cb.property()))) {
            if (cb.primaryKey()) {
                throw new MyBatisParserException("The attribute \"" + cb.property() + "\" of the entity class \"" +
                        tb.entity().getName() + "\" is the primary key. The primary key is not updatable. " +
                        "Adding \"@LogicDelete\" annotation is not supported.");
            }
            if (!cb.updatable()) {
                throw new MyBatisParserException("The attribute \"" + cb.property() + "\" of the entity class \"" +
                        tb.entity().getName() + "\" is not updatable and adding \"@LogicDelete\" annotation is not " +
                        "supported.");
            }
            if (cb.version()) {
                throw new MyBatisParserException("The attribute \"" + cb.property() + "\" of the entity class \"" +
                        tb.entity().getName() + "\" has \"@Version\" annotation and adding \"@LogicDelete\" " +
                        "annotation is not supported.");
            }
            if (tb.logicDelete()) {
                throw new MyBatisParserException("There are already \"" + tb.logicDeleteColumn().property()
                        + "\" attributes in \"" + tb.entity().getName()
                        + "\" entity class identified as logical deleted. Only one deleted attribute " +
                        "can exist in an entity class. Please check the entity class attributes.");
            }
            cb.logicDelete(true).logicDeleteInit(ldc.isInitialize());
            tb.logicDelete(true).logicDeleteColumn(cb);
            final String yet;
            final String already;
            if (hasAnnotation) {
                yet = Strings.isWhitespace(ld.yet()) ? ldc.getYet() : ld.yet();
                already = Strings.isWhitespace(ld.already()) ? ldc.getAlready() : ld.already();
            } else {
                yet = ldc.getYet();
                already = ldc.getAlready();
            }
            Objects.requireNonEmpty(yet, "The undeleted value cannot be null.");
            Objects.requireNonEmpty(already, "The deleted value cannot be null.");
            cb.yet(Objects.convert(cb.javaType(), yet)).already(Objects.convert(cb.javaType(), already));
        }
    }

    /**
     * 处理审计注解
     *
     * @param mgc       {@link MyBatisGlobalConfig}
     * @param reflector {@link Reflector}
     * @param tb        {@link TableBuilder}
     * @param cb        {@link ColumnBuilder}
     * @param field     {@link FieldWrapper}
     */
    private void handleAuditAnnotation(final MyBatisGlobalConfig mgc, final Reflector reflector,
                                       final TableBuilder tb, final ColumnBuilder cb, final FieldWrapper field) {
        if (cb.canAuditParsing() && mgc.getAudit().isEnable()) {
            final AuditMatcher am = this.getAuditParser(mgc).parse(field);
            if (Objects.nonNull(am) && am.matches()) {
                cb.createdBy(am.createdBy()).createdByName(am.createdByName()).createdDate(am.createdDate());
                cb.lastModifiedBy(am.lastModifiedBy()).lastModifiedByName(am.lastModifiedByName())
                        .lastModifiedDate(am.lastModifiedDate());
                cb.deletedBy(am.deletedBy()).deletedByName(am.deletedByName()).deletedDate(am.deletedDate());
                cb.auditType(am.auditType()).auditModes(am.auditModes());
            }
        }
    }

    /**
     * 获取实体原名命策略
     *
     * @param mgc {@link MyBatisGlobalConfig}
     * @return {@link NamingStrategy}
     */
    private NamingStrategy getEntitySrcNaming(final MyBatisGlobalConfig mgc) {
        NamingStrategy naming;
        if (Objects.isNull((naming = mgc.getEntitySrcNaming()))) {
            naming = NamingStrategy.UPPER_CAMEL;
            mgc.setEntitySrcNaming(naming);
        }
        return naming;
    }

    /**
     * 获取属性原名命策略
     *
     * @param mgc {@link MyBatisGlobalConfig}
     * @return {@link NamingStrategy}
     */
    private NamingStrategy getPropertySrcNaming(final MyBatisGlobalConfig mgc) {
        NamingStrategy naming;
        if (Objects.isNull((naming = mgc.getPropertySrcNaming()))) {
            naming = NamingStrategy.LOWER_CAMEL;
            mgc.setPropertySrcNaming(naming);
        }
        return naming;
    }

    /**
     * 获取命名策略
     *
     * @param mgc       {@link MyBatisGlobalConfig}
     * @param reflector {@link Reflector}
     * @return {@link NamingStrategy}
     */
    private NamingStrategy naming(final MyBatisGlobalConfig mgc, final Reflector reflector) {
        return Optional.ofNullable(reflector.getAnnotation(Naming.class)).map(Naming::value)
                .orElse(Optional.ofNullable(mgc.getNaming()).orElse(NamingStrategy.UPPER_UNDERSCORE));
    }

    /**
     * 获取类匹配器
     *
     * @param mgc {@link MyBatisGlobalConfig}
     * @param mc  {@link MatcherConfig}
     * @return {@link ClassMatcher}
     */
    private ClassMatcher getClassMatcher(final MyBatisGlobalConfig mgc, final MatcherConfig mc) {
        ClassMatcher classMatcher = mc.getClassMatcher();
        if (Objects.isNull(classMatcher)) {
            classMatcher = new DefaultClassMatcher(mgc.isJpaSupport());
            mc.setClassMatcher(classMatcher);
        }
        return classMatcher;
    }

    /**
     * 获取字段匹配器
     *
     * @param mgc {@link MyBatisGlobalConfig}
     * @param mc  {@link MatcherConfig}
     * @return {@link FieldMatcher}
     */
    private FieldMatcher getFieldMatcher(final MyBatisGlobalConfig mgc, final MatcherConfig mc) {
        FieldMatcher fieldMatcher = mc.getFieldMatcher();
        if (Objects.isNull(fieldMatcher)) {
            fieldMatcher = DefaultFieldMatcher.of(mgc.isUseSimpleType(), mgc.isEnumAsSimpleType(), mgc.isJpaSupport());
            mc.setFieldMatcher(fieldMatcher);
        }
        return fieldMatcher;
    }

    /**
     * 获取get方法匹配器
     *
     * @param mgc {@link MyBatisGlobalConfig}
     * @param mc  {@link MatcherConfig}
     * @return {@link GetterMatcher}
     */
    private GetterMatcher getReadableMatcher(final MyBatisGlobalConfig mgc, final MatcherConfig mc) {
        GetterMatcher methodMatcher = mc.getGetterMatcher();
        if (Objects.isNull(methodMatcher)) {
            methodMatcher = new ReadableMatcher();
            mc.setGetterMatcher(methodMatcher);
        }
        return methodMatcher;
    }

    /**
     * 获取get方法匹配器
     *
     * @param mgc {@link MyBatisGlobalConfig}
     * @param mc  {@link MatcherConfig}
     * @return {@link SetterMatcher}
     */
    private SetterMatcher getWritableMatcher(final MyBatisGlobalConfig mgc, final MatcherConfig mc) {
        SetterMatcher methodMatcher = mc.getSetterMatcher();
        if (Objects.isNull(methodMatcher)) {
            methodMatcher = new WritableMatcher();
            mc.setSetterMatcher(methodMatcher);
        }
        return methodMatcher;
    }

    /**
     * 获取审计属性解析器
     *
     * @param mgc {@link MyBatisGlobalConfig}
     * @return {@link AuditParser}
     */
    private AuditParser getAuditParser(final MyBatisGlobalConfig mgc) {
        final AuditConfig ac = mgc.getAudit();
        AuditParser auditParser = ac.getAuditParser();
        AuditAutoScanParser autoScanParser = ac.getAutoScanParser();
        if (Objects.isNull(autoScanParser)) {
            autoScanParser = new DefaultAuditPropertyAutoScanParser();
            ac.setAutoScanParser(autoScanParser);
        }
        if (Objects.isNull(auditParser)) {
            auditParser = DefaultAuditPropertyParser.of(ac.isAutoScan(), autoScanParser);
            ac.setAuditParser(auditParser);
        }
        return auditParser;
    }

    public static DefaultEntityParser of() {
        return new DefaultEntityParser();
    }
}
