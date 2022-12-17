package io.github.mybatisx.util;

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
public final class Optional<T> {

    /**
     * 空值实例
     */
    private static final Optional<?> EMPTY = new Optional<>();
    /**
     * 值
     */
    private final T value;

    private Optional() {
        this.value = null;
    }

    private Optional(T value) {
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
     * @return {@link Optional}
     */
    public <U> Optional<U> ifPresentThen(final Function<? super T, ? extends U> mapper) {
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
     * 如果值为空则转换
     *
     * @param mapper {@link Function}
     * @param <U>    返回值类型
     * @return {@link Optional}
     */
    @SuppressWarnings({"unchecked"})
    public <U> Optional<U> ifAbsentThen(final Function<? super T, ? extends U> mapper) {
        if (this.value == null) {
            return Optional.ofNullable(mapper.apply(null));
        } else {
            return (Optional<U>) this;
        }
    }

    /**
     * 如果值为空则转换
     *
     * @param supplier 其他值供给函数
     * @param <U>      返回值类型
     * @return {@link Optional}
     */
    @SuppressWarnings({"unchecked"})
    public <U> Optional<U> ifAbsentThen(final Supplier<U> supplier) {
        if (this.value == null) {
            return Optional.ofNullable(supplier.get());
        } else {
            return (Optional<U>) this;
        }
    }

    /**
     * 如果值为空则转换，否则返回其他值
     *
     * @param mapper {@link Function}
     * @param other  其他值
     * @param <U>    返回值类型
     * @return {@link Optional}
     */
    public <U> Optional<U> ifAbsentOrElse(final Function<? super T, ? extends U> mapper, final U other) {
        if (this.value == null) {
            return Optional.ofNullable(mapper.apply(null));
        } else {
            return Optional.ofNullable(other);
        }
    }

    /**
     * 如果值为空则转换，否则返回其他值
     *
     * @param mapper {@link Function}
     * @param other  其他值供给函数
     * @param <U>    返回值类型
     * @return {@link Optional}
     */
    public <U> Optional<U> ifAbsentOrElse(final Function<? super T, ? extends U> mapper, final Supplier<U> other) {
        if (this.value == null) {
            return Optional.ofNullable(mapper.apply(null));
        } else {
            return Optional.ofNullable(other.get());
        }
    }

    /**
     * 如果值为空则转换
     *
     * @param absence  {@link Function}
     * @param presence {@link Function}
     * @param <U>      返回值类型
     * @return {@link Optional}
     */
    public <U> Optional<U> ifAbsentOrElse(final Function<? super T, ? extends U> absence, final Function<? super T, ? extends U> presence) {
        if (this.value == null) {
            return Optional.ofNullable(absence.apply(null));
        } else {
            return Optional.ofNullable(presence.apply(this.value));
        }
    }

    /**
     * 如果值不为空则消费并返回，否则返回空值
     *
     * @param action {@link Consumer}
     * @return {@link Optional}
     */
    public Optional<T> either(final Consumer<? super T> action) {
        if (this.value == null) {
            return Optional.empty();
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
     * @return {@link Optional}
     */
    public Optional<T> filter(final Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (this.isEmpty()) {
            return this;
        } else {
            return predicate.test(this.value) ? this : Optional.empty();
        }
    }

    /**
     * 转换
     *
     * @param mapper {@link Function}
     * @param <U>    返回可选值类型
     * @return {@link Optional}
     */
    public <U> Optional<U> map(final Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (this.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(mapper.apply(this.value));
        }
    }

    /**
     * 转换
     *
     * @param mapper {@link Function}
     * @param <U>    可选值类型
     * @return {@link Optional}
     */
    public <U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper) {
        Objects.requireNonNull(mapper);
        if (this.isEmpty()) {
            return Optional.empty();
        } else {
            return Objects.requireNonNull(mapper.apply(this.value));
        }
    }

    /**
     * 如果当前值不为空则返回当前对象，否则根据供给函数获取返回值
     *
     * @param other 供给函数
     * @return {@link Optional}
     */
    public Optional<T> or(final Supplier<? extends Optional<? extends T>> other) {
        Objects.requireNonNull(other);
        if (isPresent()) {
            return this;
        } else {
            @SuppressWarnings({"unchecked"}) final Optional<T> optional = (Optional<T>) other.get();
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Optional)) {
            return false;
        }

        Optional<?> other = (Optional<?>) obj;
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
     * @return {@link Optional}
     */
    public static <T> Optional<T> empty() {
        @SuppressWarnings({"unchecked"}) final Optional<T> optional = (Optional<T>) Optional.EMPTY;
        return optional;
    }

    /**
     * 非空值可选实例
     *
     * @param value 值
     * @param <T>   值类型
     * @return {@link Optional}
     */
    public static <T> Optional<T> of(final T value) {
        return new Optional<>(value);
    }

    /**
     * 根据供给函数获取非空值可选实例
     *
     * @param other 供给函数
     * @param <T>   值类型
     * @return {@link Optional}
     */
    public static <T> Optional<T> ofFrom(final Supplier<T> other) {
        return Optional.of(other.get());
    }

    /**
     * 值不为空则返回非空值可选实例，否则返回空值可选实例
     *
     * @param value 值
     * @param <T>   值类型
     * @return {@link Optional}
     */
    public static <T> Optional<T> ofNullable(final T value) {
        return value == null ? Optional.empty() : of(value);
    }

    /**
     * 根据供给函数获取可选实例
     *
     * @param other 供给函数
     * @param <T>   值类型
     * @return {@link Optional}
     */
    public static <T> Optional<T> ofNullableFrom(final Supplier<T> other) {
        return Optional.ofNullable(other.get());
    }

    /**
     * {@link Optional}转{@link java.util.Optional}
     *
     * @param source 源对象
     * @param <T>    数据类型
     * @return {@link java.util.Optional}
     */
    public static <T> java.util.Optional<T> toOptional(final Optional<T> source) {
        return source.isEmpty() ? java.util.Optional.empty() : java.util.Optional.of(source.get());
    }

}
