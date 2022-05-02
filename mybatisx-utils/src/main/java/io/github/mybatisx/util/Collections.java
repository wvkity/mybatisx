package io.github.mybatisx.util;

import io.github.mybatisx.lang.Objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 集合工具
 *
 * @author wvkity
 * @created 2022/5/2
 * @since 1.0.0
 */
public final class Collections {

    private Collections() {
    }

    /**
     * 检查集合是否为空
     *
     * @param iterable 待检查集合
     * @param <T>      元素类型
     * @param <Iter>   {@link Iterable}
     * @return boolean
     */
    public static <T, Iter extends Iterable<? extends T>> boolean isEmpty(final Iter iterable) {
        return iterable == null || (iterable instanceof Collection ? ((Collection<?>) iterable).isEmpty() :
                iterable.iterator().hasNext());
    }

    /**
     * 检查集合是否不为空
     *
     * @param iterable 待检查集合
     * @param <T>      元素类型
     * @param <Iter>   {@link Iterable}
     * @return boolean
     */
    public static <T, Iter extends Iterable<? extends T>> boolean isNotEmpty(final Iter iterable) {
        return !isEmpty(iterable);
    }

    /**
     * 获取集合元素个数
     *
     * @param iterable 集合
     * @param <T>      元素类型
     * @param <Iter>   {@link Iterable}
     * @return 集合元素个数
     */
    public static <T, Iter extends Iterable<? extends T>> int size(final Iter iterable) {
        if (isNotEmpty(iterable)) {
            if (iterable instanceof Collection) {
                return ((Collection<?>) iterable).size();
            }
            return (int) StreamSupport.stream(iterable.spliterator(), false).count();
        }
        return 0;
    }

    /**
     * 检查参数列表是否包含数组、集合类型
     *
     * @param iterable 参数列表
     * @param <T>      元素类型
     * @param <Iter>   {@link Iterable}
     * @return boolean
     */
    public static <T, Iter extends Iterable<? extends T>> boolean isPureType(final Iter iterable) {
        if (iterable != null) {
            Class<?> current = null;
            for (Object it : iterable) {
                if (it != null) {
                    final Class<?> clazz = it.getClass();
                    if (clazz.isArray() || Objects.isAssignable(Iterable.class, clazz)
                            || Objects.isAssignable(Map.class, clazz)) {
                        return false;
                    }
                    if (current != null && !Objects.isAssignable(current, clazz)) {
                        return false;
                    }
                    current = clazz;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * {@link Iterable}转{@link List}
     *
     * @param iterable 待转换集合
     * @param filter   {@link Predicate}
     * @param <T>      元素类型
     * @param <Iter>   {@link Iterable}
     * @return {@link List}
     */
    public static <T, Iter extends Iterable<? extends T>> List<T> asList(final Iter iterable,
                                                                         final Predicate<? super T> filter) {
        return asCollection(iterable, filter, ArrayList::new);
    }

    /**
     * {@link Iterable}转{@link List}
     *
     * @param iterable 待转换集合
     * @param filter   {@link Predicate}
     * @param <T>      元素类型
     * @param <Iter>   {@link Iterable}
     * @return {@link List}
     */
    public static <T, Iter extends Iterable<? extends T>> Set<T> asSet(final Iter iterable,
                                                                       final Predicate<? super T> filter) {
        return asCollection(iterable, filter, HashSet::new);
    }

    /**
     * {@link Iterable}转{@link List}
     *
     * @param iterable 待转换集合
     * @param filter   {@link Predicate}
     * @param <T>      元素类型
     * @param <Iter>   {@link Iterable}
     * @param <C>      集合类型
     * @return {@link List}
     */
    @SuppressWarnings({"unchecked"})
    public static <T, Iter extends Iterable<? extends T>, C extends Collection<T>> C asCollection(final Iter iterable,
                                                                                                  final Predicate<? super T> filter,
                                                                                                  final Supplier<C> collectionFactory) {
        if (isEmpty(iterable)) {
            return collectionFactory.get();
        }
        if (filter == null && iterable instanceof Collection) {
            final C it = collectionFactory.get();
            it.addAll((Collection<T>) iterable);
            return it;
        }
        return StreamSupport.stream(iterable.spliterator(), false).filter(it -> matches(it, filter))
                .collect(Collectors.toCollection(collectionFactory));
    }

    /**
     * 过滤
     *
     * @param iterable 待过滤集合
     * @param filter   过滤器
     * @param <T>      元素类型
     * @param <Iter>   {@link Iterable}
     * @return 过滤后的列表
     */
    public static <T, Iter extends Iterable<? extends T>> Set<T> filter(final Iter iterable,
                                                                        final Predicate<? super T> filter) {
        return asCollection(iterable, filter, LinkedHashSet::new);
    }

    /**
     * 过滤
     *
     * @param values  待过滤值列表
     * @param filters 过滤器列表
     * @param <T>     值类型
     * @return 过滤后的列表
     */
    @SafeVarargs
    public static <T> Set<T> filters(final T[] values, final Predicate<? super T>... filters) {
        if (Objects.isEmpty(values)) {
            return new LinkedHashSet<>(0);
        }
        return filters(Arrays.asList(values), filters);
    }

    /**
     * 过滤
     *
     * @param iterable 待过滤集合
     * @param filters  过滤器列表
     * @param <T>      元素类型
     * @param <Iter>   {@link Iterable}
     * @return 过滤后的列表
     */
    @SafeVarargs
    public static <T, Iter extends Iterable<? extends T>> Set<T> filters(final Iter iterable,
                                                                         final Predicate<? super T>... filters) {
        if (isEmpty(iterable)) {
            return new LinkedHashSet<>(0);
        }
        return filter(iterable, Objects.and(filters));
    }

    /**
     * 过滤null值
     *
     * @param values 待过滤值列表
     * @param <T>    值类型
     * @return 新的列表
     */
    @SafeVarargs
    public static <T> Set<T> filterNull(final T... values) {
        return filterNull(HashSet::new, values);
    }

    /**
     * 过滤null值
     *
     * @param collectionFactory {@link Supplier}
     * @param values            待过滤值列表
     * @param <T>               元素类型
     * @param <C>               集合类型
     * @return 新的列表
     */
    @SafeVarargs
    public static <T, C extends Collection<T>> C filterNull(final Supplier<C> collectionFactory, final T... values) {
        return filterNull(values, collectionFactory);
    }

    /**
     * 过滤null值
     *
     * @param values            待过滤值列表
     * @param collectionFactory {@link Supplier}
     * @param <T>               元素类型
     * @param <C>               集合类型
     * @return 新的列表
     */
    public static <T, C extends Collection<T>> C filterNull(final T[] values, final Supplier<C> collectionFactory) {
        if (Objects.isEmpty(values)) {
            return collectionFactory.get();
        }
        return filterNull(Arrays.asList(values), collectionFactory);
    }

    /**
     * 过滤null值
     *
     * @param iterable 待过滤值列表
     * @param <T>      元素类型
     * @param <Iter>   {@link Iterable}
     * @return 新的列表
     */
    public static <T, Iter extends Iterable<? extends T>> Set<T> filterNull(final Iter iterable) {
        return filterNull(iterable, HashSet::new);
    }

    /**
     * 过滤null值
     *
     * @param collectionFactory {@link Supplier}
     * @param iterable          待过滤值列表
     * @param <T>               元素类型
     * @param <Iter>            {@link Iterable}
     * @param <C>               集合类型
     * @return 新的列表
     */
    public static <T, Iter extends Iterable<? extends T>, C extends Collection<T>> C filterNull(final Iter iterable,
                                                                                                final Supplier<C> collectionFactory) {
        return asCollection(iterable, Objects::nonNull, collectionFactory);
    }

    /**
     * 检查集合元素是否为空，如果为空，则返回null
     *
     * @param iterable 待检查集合
     * @param <T>      元素类型
     * @param <Iter>   {@link Iterable}
     * @return 集合
     */
    public static <T, Iter extends Iterable<? extends T>> Iter checkNull(final Iter iterable) {
        return checkNull(iterable, null);
    }

    /**
     * 检查集合元素是否为空，如果为空则返回默认值
     *
     * @param iterable     待检查集合
     * @param defaultValue 默认值
     * @param <T>          元素类型
     * @param <Iter>       {@link Iterable}
     * @return 集合
     */
    public static <T, Iter extends Iterable<? extends T>> Iter checkNull(final Iter iterable, final Iter defaultValue) {
        if (isNotEmpty(iterable)) {
            for (T it : iterable) {
                if (it != null) {
                    return iterable;
                }
            }
        }
        return defaultValue;
    }

    /**
     * 字符串元素转成小写
     *
     * @param iterable 字符串集合
     * @param <Iter>   {@link Iterable}
     * @return 处理后的数据
     */
    public static <Iter extends Iterable<String>> List<String> lowerCase(final Iter iterable) {
        return transform(iterable, Objects::nonNull, it -> it.toLowerCase(Locale.ENGLISH));
    }

    /**
     * 字符串元素转成大写
     *
     * @param iterable 字符串集合
     * @param <Iter>   {@link Iterable}
     * @return 处理后的字符串集合
     */
    public static <Iter extends Iterable<String>> List<String> upperCase(final Iter iterable) {
        return transform(iterable, Objects::nonNull, it -> it.toUpperCase(Locale.ENGLISH));
    }

    /**
     * 数据转换
     *
     * @param iterable 原数据
     * @param filter   {@link Predicate}
     * @param mapper   {@link Function}
     * @param <T>      元素类型
     * @param <Iter>   {@link Iterable}
     * @param <R>      返回值类型
     * @return 处理后的数据集合
     */
    public static <T, Iter extends Iterable<? extends T>, R> List<R> transform(final Iter iterable,
                                                                               final Predicate<T> filter,
                                                                               final Function<T, R> mapper) {
        if (isNotEmpty(iterable)) {
            return StreamSupport.stream(iterable.spliterator(), false).filter(filter).map(mapper).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 数据合并
     *
     * @param array  多个集合
     * @param <T>    数据类型
     * @param <Iter> {@link Iterable}
     * @return 合并后的数据
     */
    @SafeVarargs
    public static <T, Iter extends Iterable<? extends T>> List<T> combine(final Iter... array) {
        return combine(null, array);
    }

    /**
     * 数据合并
     *
     * @param filter 过滤器
     * @param array  多个集合
     * @param <T>    数据类型
     * @param <Iter> {@link Iterable}
     * @return 合并后的数据
     */
    @SafeVarargs
    public static <T, Iter extends Iterable<? extends T>> List<T> combine(final Predicate<T> filter, final Iter... array) {
        final Predicate<T> ipt = filter == null ? __ -> true : filter;
        if (Objects.isNotEmpty(array)) {
            final List<T> result = new ArrayList<>();
            for (Iter c : array) {
                if (c != null) {
                    for (T it : c) {
                        if (ipt.test(it)) {
                            result.add(it);
                        }
                    }
                }
            }
            return result;
        }
        return new ArrayList<>(0);
    }

    /**
     * @param iterable 集合类型
     * @param <T>      元素类型
     * @param <Iter>   {@link Iterable}
     * @return {@link Optional}
     */
    public static <T, Iter extends Iterable<? extends T>> Optional<T> first(final Iter iterable) {
        if (isEmpty(iterable)) {
            return Optional.empty();
        }
        return Optional.ofNullable(iterable.iterator().next());
    }

    /**
     * 检查值是否符合
     *
     * @param value  值
     * @param filter {@link Predicate}
     * @param <T>    值类型
     * @return boolean
     */
    static <T> boolean matches(final T value, final Predicate<T> filter) {
        return filter == null || filter.test(value);
    }
}
