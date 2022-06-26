package io.github.mybatisx.core.criteria.update;

import io.github.mybatisx.core.criteria.Category;
import io.github.mybatisx.core.sql.UpdateSqlManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

/**
 * 更新条件
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2022/6/26
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlainUpdateImplementor<T> extends AbstractPlainUpdateCriteria<T, PlainUpdateImplementor<T>> {

    private static final long serialVersionUID = -8209718400337220596L;

    public PlainUpdateImplementor(Class<T> entity) {
        this.entity = entity;
        this.newUpdateInit();
        this.sqlManager = new UpdateSqlManager(this.fragmentManager, this, this.parameterConverter, this.updateWrapColumns);
    }

    @Override
    protected PlainUpdateImplementor<T> newInstance() {
        final PlainUpdateImplementor<T> it = new PlainUpdateImplementor<>();
        this.category = Category.UPDATE;
        it.clone(this);
        return it;
    }

    /**
     * 创建{@link PlainUpdateImplementor}对象
     *
     * @param entity 实体类
     * @param <T>    实体类型
     * @return {@link PlainUpdateImplementor}
     */
    public static <T> PlainUpdateImplementor<T> from(final Class<T> entity) {
        return new PlainUpdateImplementor<>(entity);
    }

    /**
     * 创建{@link PlainUpdateImplementor}对象
     *
     * @param entity 实体类
     * @param action {@link Consumer}
     * @param <T>    实体类型
     * @return {@link PlainUpdateImplementor}
     */
    public static <T> PlainUpdateImplementor<T> from(final Class<T> entity, final Consumer<PlainUpdateImplementor<T>> action) {
        final PlainUpdateImplementor<T> it = from(entity);
        if (action != null) {
            action.accept(it);
        }
        return it;
    }
}
