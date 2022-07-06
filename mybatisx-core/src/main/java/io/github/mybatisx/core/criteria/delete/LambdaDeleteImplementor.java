package io.github.mybatisx.core.criteria.delete;

import io.github.mybatisx.core.criteria.Category;
import io.github.mybatisx.core.sql.DefaultSqlManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

/**
 * 删除条件(支持Lambda表达式)
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2022/7/6
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LambdaDeleteImplementor<T> extends AbstractLambdaDeleteCriteria<T, LambdaDeleteImplementor<T>> {

    private static final long serialVersionUID = -6681412239444969296L;

    public LambdaDeleteImplementor(Class<T> entity) {
        this.entity = entity;
        this.newDeleteInit();
        this.sqlManager = new DefaultSqlManager(this, this.fragmentManager);
    }

    @Override
    protected LambdaDeleteImplementor<T> newInstance() {
        final LambdaDeleteImplementor<T> it = new LambdaDeleteImplementor<>();
        it.category = Category.DELETE;
        it.clone(this);
        return it;
    }

    /**
     * 创建{@link LambdaDeleteImplementor}对象
     *
     * @param entity 实体类
     * @param <T>    实体类型
     * @return {@link LambdaDeleteImplementor}
     */
    public static <T> LambdaDeleteImplementor<T> from(final Class<T> entity) {
        return new LambdaDeleteImplementor<>(entity);
    }

    /**
     * 创建{@link LambdaDeleteImplementor}对象
     *
     * @param entity 实体类
     * @param action {@link Consumer}
     * @param <T>    实体类型
     * @return {@link LambdaDeleteImplementor}
     */
    public static <T> LambdaDeleteImplementor<T> from(final Class<T> entity, final Consumer<LambdaDeleteImplementor<T>> action) {
        final LambdaDeleteImplementor<T> it = from(entity);
        if (action != null) {
            action.accept(it);
        }
        return it;
    }
}
