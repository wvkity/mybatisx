package io.github.mybatisx.core.criteria.update;

import io.github.mybatisx.core.criteria.Category;
import io.github.mybatisx.core.sql.UpdateSqlManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

/**
 * 更新条件(支持lambda表达式)
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2022/6/26
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LambdaUpdateImplementor<T> extends AbstractLambdaUpdateCriteria<T, LambdaUpdateImplementor<T>> {

    private static final long serialVersionUID = -4762350629155932023L;

    public LambdaUpdateImplementor(Class<T> entity) {
        this.entity = entity;
        this.newUpdateInit();
        this.sqlManager = new UpdateSqlManager(this.fragmentManager, this, this.parameterConverter, this.updateWrapColumns);
    }

    @Override
    protected LambdaUpdateImplementor<T> newInstance() {
        final LambdaUpdateImplementor<T> it = new LambdaUpdateImplementor<>();
        it.category = Category.UPDATE;
        it.clone(this);
        return it;
    }

    /**
     * 创建{@link LambdaUpdateImplementor}对象
     *
     * @param entity 实体类
     * @param <T>    实体类型
     * @return {@link LambdaUpdateImplementor}
     */
    public static <T> LambdaUpdateImplementor<T> from(final Class<T> entity) {
        return new LambdaUpdateImplementor<>(entity);
    }

    /**
     * 创建{@link LambdaUpdateImplementor}对象
     *
     * @param entity 实体类
     * @param action {@link Consumer}
     * @param <T>    实体类型
     * @return {@link LambdaUpdateImplementor}
     */
    public static <T> LambdaUpdateImplementor<T> from(final Class<T> entity, final Consumer<LambdaUpdateImplementor<T>> action) {
        final LambdaUpdateImplementor<T> it = from(entity);
        if (action != null) {
            action.accept(it);
        }
        return it;
    }
}
