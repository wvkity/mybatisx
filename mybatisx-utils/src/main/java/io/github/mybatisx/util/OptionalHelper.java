package io.github.mybatisx.util;

import io.github.mybatisx.function.Absence;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 可选操作
 *
 * @author wvkity
 * @created 2022/11/20
 * @since 1.0.0
 */
public final class OptionalHelper<T> {

    /**
     * 空值实例
     */
    private static final OptionalHelper<?> EMPTY = new OptionalHelper<>();
    /**
     * 值
     */
    private final T value;

    private OptionalHelper() {
        this.value = null;
    }

    private OptionalHelper(T value) {
        this.value = Objects.requireNonNull(value, "The given value cannot be null");
    }

    /**
     * 获取值
     *
     * @return 值
     */
    public T get() {
        if (this.value == null) {
            throw new NoSuchElementException("No value present");
        }
        return this.value;
    }

    /**
     * 检查是否存在值
     *
     * @return boolean
     */
    public boolean isPresent() {
        return this.value != null;
    }

    /**
     * 检查值是否为空
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return this.value == null;
    }

    /**
     * 如果值不为空，则消费
     *
     * @param action {@link Consumer}
     * @return {@link OptionalHelper}
     */
    public OptionalHelper<T> ifPresentPeek(final Consumer<T> action) {
        if (this.value != null) {
            action.accept(this.value);
        }
        return this;
    }

    /**
     * 如果值不为空，则消费
     *
     * @param action {@link Consumer}
     */
    public void ifPresent(final Consumer<? super T> action) {
        if (this.value != null) {
            action.accept(this.value);
        }
    }

    /**
     * 如果值不为空则转换
     *
     * @param mapper {@link Function}
     * @param <U>    返回值类型
     * @return {@link OptionalHelper}
     */
    public <U> OptionalHelper<U> ifPresentThen(final Function<? super T, ? extends U> mapper) {
        return this.map(mapper);
    }

    /**
     * 如果值为空，则消费
     *
     * @param action {@link Consumer}
     */
    public void ifAbsent(final Consumer<T> action) {
        if (this.value == null) {
            action.accept(null);
        }
    }

    /**
     * 如果值为空，则消费
     *
     * @param action {@link Consumer}
     */
    public void ifAbsent(final Absence action) {
        if (this.value == null) {
            action.accept();
        }
    }

    /**
     * 如果值为空则转换
     *
     * @param mapper {@link Function}
     * @param <U>    返回值类型
     * @return {@link OptionalHelper}
     */
    @SuppressWarnings({"unchecked"})
    public <U> OptionalHelper<U> ifAbsentThen(final Function<? super T, ? extends U> mapper) {
        if (this.value == null) {
            return OptionalHelper.ofNullable(mapper.apply(null));
        } else {
            return (OptionalHelper<U>) this;
        }
    }

    /**
     * 如果值为空则转换
     *
     * @param supplier 其他值供给函数
     * @param <U>      返回值类型
     * @return {@link OptionalHelper}
     */
    @SuppressWarnings({"unchecked"})
    public <U> OptionalHelper<U> ifAbsentThen(final Supplier<U> supplier) {
        if (this.value == null) {
            return OptionalHelper.ofNullable(supplier.get());
        } else {
            return (OptionalHelper<U>) this;
        }
    }

    /**
     * 如果值为空则转换，否则返回其他值
     *
     * @param mapper {@link Function}
     * @param other  其他值
     * @param <U>    返回值类型
     * @return {@link OptionalHelper}
     */
    public <U> OptionalHelper<U> ifAbsentOrElse(final Function<? super T, ? extends U> mapper, final U other) {
        if (this.value == null) {
            return OptionalHelper.ofNullable(mapper.apply(null));
        } else {
            return OptionalHelper.ofNullable(other);
        }
    }

    /**
     * 如果值为空则转换，否则返回其他值
     *
     * @param mapper {@link Function}
     * @param other  其他值供给函数
     * @param <U>    返回值类型
     * @return {@link OptionalHelper}
     */
    public <U> OptionalHelper<U> ifAbsentOrElse(final Function<? super T, ? extends U> mapper, final Supplier<U> other) {
        if (this.value == null) {
            return OptionalHelper.ofNullable(mapper.apply(null));
        } else {
            return OptionalHelper.ofNullable(other.get());
        }
    }

    /**
     * 如果值为空则转换
     *
     * @param absence  {@link Function}
     * @param presence {@link Function}
     * @param <U>      返回值类型
     * @return {@link OptionalHelper}
     */
    public <U> OptionalHelper<U> ifAbsentOrElse(final Function<? super T, ? extends U> absence, final Function<? super T, ? extends U> presence) {
        if (this.value == null) {
            return OptionalHelper.ofNullable(absence.apply(null));
        } else {
            return OptionalHelper.ofNullable(presence.apply(this.value));
        }
    }

    /**
     * 如果值不为空则消费并返回，否则返回空值
     *
     * @param action {@link Consumer}
     * @return {@link OptionalHelper}
     */
    public OptionalHelper<T> either(final Consumer<? super T> action) {
        if (this.value == null) {
            return OptionalHelper.empty();
        } else {
            action.accept(this.value);
            return this;
        }
    }

    /**
     * 消费
     *
     * @param presence 有值消费函数
     * @param absence  空值消费函数
     */
    public void eitherOrElse(final Consumer<? super T> presence, final Consumer<? super T> absence) {
        if (this.value == null) {
            absence.accept(null);
        } else {
            presence.accept(this.value);
        }
    }

    /**
     * 过滤
     *
     * @param predicate {@link Predicate}
     * @return {@link OptionalHelper}
     */
    public OptionalHelper<T> filter(final Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (this.isEmpty()) {
            return this;
        } else {
            return predicate.test(this.value) ? this : OptionalHelper.empty();
        }
    }

    /**
     * 转换
     *
     * @param mapper {@link Function}
     * @param <U>    返回可选值类型
     * @return {@link OptionalHelper}
     */
    public <U> OptionalHelper<U> map(final Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (this.isEmpty()) {
            return OptionalHelper.empty();
        } else {
            return OptionalHelper.ofNullable(mapper.apply(this.value));
        }
    }

    /**
     * 转换
     *
     * @param mapper {@link Function}
     * @param <U>    可选值类型
     * @return {@link OptionalHelper}
     */
    public <U> OptionalHelper<U> flatMap(Function<? super T, OptionalHelper<U>> mapper) {
        Objects.requireNonNull(mapper);
        if (this.isEmpty()) {
            return OptionalHelper.empty();
        } else {
            return Objects.requireNonNull(mapper.apply(this.value));
        }
    }

    /**
     * 如果当前值不为空则返回当前对象，否则根据供给函数获取返回值
     *
     * @param other 供给函数
     * @return {@link OptionalHelper}
     */
    public OptionalHelper<T> or(final Supplier<? extends OptionalHelper<? extends T>> other) {
        Objects.requireNonNull(other);
        if (isPresent()) {
            return this;
        } else {
            @SuppressWarnings({"unchecked"}) final OptionalHelper<T> optional = (OptionalHelper<T>) other.get();
            return optional;
        }
    }

    /**
     * 如果当前值不为空则返回，否则返回指定值
     *
     * @param other 指定值
     * @return 值
     */
    public T orElse(final T other) {
        return this.value == null ? other : this.value;
    }

    /**
     * 如果当前值不为空则返回，否则根据提供的供给对象获取值并返回
     *
     * @param other 值供给函数
     * @return 值
     */
    public T orElseGet(final Supplier<? extends T> other) {
        return this.value != null ? this.value : other.get();
    }

    /**
     * 如果当前值不为空则返回，否则抛出异常
     *
     * @return 值
     */
    public T orElseThrow() {
        if (this.value == null) {
            throw new NoSuchElementException("No value present");
        }
        return this.value;
    }

    /**
     * 如果当前值不为空则返回，否则抛出异常
     *
     * @param exceptionSupplier 异常供给函数
     * @param <X>               异常类型
     * @return 值
     * @throws X 异常
     */
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    /**
     * {@link OptionalHelper}转{@link java.util.Optional}
     *
     * @return {@link java.util.Optional}
     */
    public java.util.Optional<T> to() {
        return OptionalHelper.toOptional(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof OptionalHelper)) {
            return false;
        }

        OptionalHelper<?> other = (OptionalHelper<?>) obj;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value != null
                ? String.format("Optional[%s]", value)
                : "Optional.empty";
    }

    // ----- static methods -----

    /**
     * 空值可选实例
     *
     * @param <T> 值类型
     * @return {@link OptionalHelper}
     */
    public static <T> OptionalHelper<T> empty() {
        @SuppressWarnings({"unchecked"}) final OptionalHelper<T> optional = (OptionalHelper<T>) OptionalHelper.EMPTY;
        return optional;
    }

    /**
     * 非空值可选实例
     *
     * @param value 值
     * @param <T>   值类型
     * @return {@link OptionalHelper}
     */
    public static <T> OptionalHelper<T> of(final T value) {
        return new OptionalHelper<>(value);
    }

    /**
     * 根据供给函数获取非空值可选实例
     *
     * @param other 供给函数
     * @param <T>   值类型
     * @return {@link OptionalHelper}
     */
    public static <T> OptionalHelper<T> ofFrom(final Supplier<T> other) {
        return OptionalHelper.of(other.get());
    }

    /**
     * 值不为空则返回非空值可选实例，否则返回空值可选实例
     *
     * @param value 值
     * @param <T>   值类型
     * @return {@link OptionalHelper}
     */
    public static <T> OptionalHelper<T> ofNullable(final T value) {
        return value == null ? OptionalHelper.empty() : of(value);
    }

    /**
     * 根据供给函数获取可选实例
     *
     * @param other 供给函数
     * @param <T>   值类型
     * @return {@link OptionalHelper}
     */
    public static <T> OptionalHelper<T> ofNullableFrom(final Supplier<T> other) {
        return OptionalHelper.ofNullable(other.get());
    }

    /**
     * {@link OptionalHelper}转{@link java.util.Optional}
     *
     * @param source 源对象
     * @param <T>    数据类型
     * @return {@link java.util.Optional}
     */
    public static <T> java.util.Optional<T> toOptional(final OptionalHelper<T> source) {
        return source.map(java.util.Optional::of).orElseGet(java.util.Optional::empty);
    }

}
