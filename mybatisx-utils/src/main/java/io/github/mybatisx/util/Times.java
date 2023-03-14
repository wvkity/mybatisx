package io.github.mybatisx.util;

import io.github.mybatisx.lang.StringHelper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * 时间单位
 *
 * @author wvkity
 * @created 2022/12/17
 * @since 1.0.0
 */
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum Times {

    UNKNOWN("", ChronoUnit.FOREVER),
    MILLIS("Millis", ChronoUnit.MILLIS),
    MILLISECOND("Millis", ChronoUnit.MILLIS),
    MILLISECONDS("Millis", ChronoUnit.MILLIS),
    S("Seconds", ChronoUnit.SECONDS),
    SECOND("Seconds", ChronoUnit.SECONDS),
    SECONDS("Seconds", ChronoUnit.SECONDS),
    M("Minutes", ChronoUnit.MINUTES),
    MINUTE("Minutes", ChronoUnit.MINUTES),
    MINUTES("Minutes", ChronoUnit.MINUTES),
    H("Hours", ChronoUnit.HOURS),
    HOUR("Hours", ChronoUnit.HOURS),
    HOURS("Hours", ChronoUnit.HOURS),
    D("Days", ChronoUnit.DAYS),
    DAY("Days", ChronoUnit.DAYS),
    DAYS("Days", ChronoUnit.DAYS),
    W("Weeks", ChronoUnit.WEEKS),
    WEEK("Weeks", ChronoUnit.WEEKS),
    WEEKS("Weeks", ChronoUnit.WEEKS),
    MONTH("Months", ChronoUnit.MONTHS),
    MONTHS("Months", ChronoUnit.MONTHS),
    Y("Years", ChronoUnit.YEARS),
    YEAR("Years", ChronoUnit.YEARS),
    YEARS("Years", ChronoUnit.YEARS);
    /**
     * 单位
     */
    final String unit;
    /**
     * 时间
     */
    final ChronoUnit chron;

    /**
     * 获取{@link Duration}对象
     *
     * @param amount 时间
     * @return {@link Duration}
     */
    public Duration of(final long amount) {
        return Duration.of(amount, this.chron);
    }

    /**
     * 获取{@link Duration}对象
     *
     * @return {@link Duration}
     */
    public Duration getDuration() {
        return this.chron.getDuration();
    }

    /**
     * 转换
     *
     * @param unit 单位
     * @return {@link Times}
     */
    public static Times convert(final String unit) {
        if (StringHelper.isNotWhitespace(unit)) {
            try {
                return Times.valueOf(unit.toUpperCase(Locale.ENGLISH));
            } catch (Exception ignore) {
                // ignore
            }
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return this.unit;
    }
}
