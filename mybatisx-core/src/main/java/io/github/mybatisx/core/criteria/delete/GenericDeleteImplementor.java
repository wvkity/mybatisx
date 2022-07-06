package io.github.mybatisx.core.criteria.delete;

import io.github.mybatisx.core.criteria.Category;
import io.github.mybatisx.core.sql.DefaultSqlManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

/**
 * 通用删除条件
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2022/7/6
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenericDeleteImplementor<T> extends AbstractGenericDeleteCriteria<T, GenericDeleteImplementor<T>> {

    private static final long serialVersionUID = 3233812265120687534L;

    public GenericDeleteImplementor(Class<T> entity) {
        this.entity = entity;
        this.newDeleteInit();
        this.sqlManager = new DefaultSqlManager(this, this.fragmentManager);
    }

    @Override
    protected GenericDeleteImplementor<T> newInstance() {
        final GenericDeleteImplementor<T> it = new GenericDeleteImplementor<>();
        it.category = Category.DELETE;
        it.clone(this);
        return it;
    }

    /**
     * 创建{@link GenericDeleteImplementor}对象
     *
     * @param entity 实体类
     * @param <T>    实体类型
     * @return {@link GenericDeleteImplementor}
     */
    public static <T> GenericDeleteImplementor<T> from(final Class<T> entity) {
        return new GenericDeleteImplementor<>(entity);
    }

    /**
     * 创建{@link GenericDeleteImplementor}对象
     *
     * @param entity 实体类
     * @param action {@link Consumer}
     * @param <T>    实体类型
     * @return {@link GenericDeleteImplementor}
     */
    public static <T> GenericDeleteImplementor<T> from(final Class<T> entity, final Consumer<GenericDeleteImplementor<T>> action) {
        final GenericDeleteImplementor<T> it = from(entity);
        if (action != null) {
            action.accept(it);
        }
        return it;
    }
}
