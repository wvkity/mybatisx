package io.github.mybatisx.core.criteria.update;

import io.github.mybatisx.core.criteria.Category;
import io.github.mybatisx.core.sql.UpdateSqlManager;

import java.util.function.Consumer;

/**
 * 通用更新条件
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2022/6/26
 * @since 1.0.0
 */
public class GenericUpdateImplementor<T> extends AbstractGenericUpdateCriteria<T, GenericUpdateImplementor<T>> {

    private static final long serialVersionUID = -5356291811547924713L;

    public GenericUpdateImplementor(Class<T> entity) {
        this.entity = entity;
        this.newUpdateInit();
        this.sqlManager = new UpdateSqlManager(this.fragmentManager, this, this.parameterConverter, this.updateWrapColumns);
    }

    @Override
    protected GenericUpdateImplementor<T> newInstance() {
        final GenericUpdateImplementor<T> it = new GenericUpdateImplementor<>();
        this.category = Category.UPDATE;
        it.clone(this);
        return it;
    }

    /**
     * 创建{@link GenericUpdateImplementor}对象
     *
     * @param entity 实体类
     * @param <T>    实体类型
     * @return {@link GenericUpdateImplementor}
     */
    public static <T> GenericUpdateImplementor<T> from(final Class<T> entity) {
        return new GenericUpdateImplementor<>(entity);
    }

    /**
     * 创建{@link GenericUpdateImplementor}对象
     *
     * @param entity 实体类
     * @param action {@link Consumer}
     * @param <T>    实体类型
     * @return {@link GenericUpdateImplementor}
     */
    public static <T> GenericUpdateImplementor<T> from(final Class<T> entity, final Consumer<GenericUpdateImplementor<T>> action) {
        final GenericUpdateImplementor<T> it = from(entity);
        if (action != null) {
            action.accept(it);
        }
        return it;
    }
}
