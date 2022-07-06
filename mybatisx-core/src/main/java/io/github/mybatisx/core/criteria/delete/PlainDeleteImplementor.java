package io.github.mybatisx.core.criteria.delete;

import io.github.mybatisx.core.criteria.Category;
import io.github.mybatisx.core.sql.DefaultSqlManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

/**
 * 删除条件
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2022/7/6
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlainDeleteImplementor<T> extends AbstractPlainDeleteCriteria<T, PlainDeleteImplementor<T>> {

    private static final long serialVersionUID = 1956024810361641187L;

    public PlainDeleteImplementor(Class<T> entity) {
        this.entity = entity;
        this.newDeleteInit();
        this.sqlManager = new DefaultSqlManager(this, this.fragmentManager);
    }

    @Override
    protected PlainDeleteImplementor<T> newInstance() {
        final PlainDeleteImplementor<T> it = new PlainDeleteImplementor<>();
        it.category = Category.DELETE;
        it.clone(this);
        return it;
    }

    /**
     * 创建{@link PlainDeleteImplementor}对象
     *
     * @param entity 实体类
     * @param <T>    实体类型
     * @return {@link PlainDeleteImplementor}
     */
    public static <T> PlainDeleteImplementor<T> from(final Class<T> entity) {
        return new PlainDeleteImplementor<>(entity);
    }

    /**
     * 创建{@link PlainDeleteImplementor}对象
     *
     * @param entity 实体类
     * @param action {@link Consumer}
     * @param <T>    实体类型
     * @return {@link PlainDeleteImplementor}
     */
    public static <T> PlainDeleteImplementor<T> from(final Class<T> entity, final Consumer<PlainDeleteImplementor<T>> action) {
        final PlainDeleteImplementor<T> it = from(entity);
        if (action != null) {
            action.accept(it);
        }
        return it;
    }
}
