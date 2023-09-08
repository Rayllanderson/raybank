package com.rayllanderson.raybank.utils;

import de.focus_shift.HolidayCalendar;
import de.focus_shift.HolidayManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import static de.focus_shift.ManagerParameters.create;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateManagerUtil {

    private static final HolidayManager holidayManager = HolidayManager.getInstance(create(HolidayCalendar.BRAZIL));

    public static boolean isWorkingDay(final LocalDate date) {
        final DayOfWeek dayOfWeek = date.getDayOfWeek();
        final boolean isWeekend = dayOfWeek.equals(DayOfWeek.SUNDAY) || dayOfWeek.equals(DayOfWeek.SATURDAY);
        return !(holidayManager.isHoliday(date) || isWeekend);
    }

    public static LocalDate getNextWorkingDayOf(final LocalDate date) {
        if (isWorkingDay(date))
            return date;
        else return getNextWorkingDayOf(date.plusDays(1));
    }

    public static LocalDate plusOneMonthOf(int day) {
        var now = LocalDate.now();
        return LocalDate.of(now.getYear(), now.getMonth(), day).plusMonths(1);
    }

    public static LocalDate plusOneMonthKeepingCurrentDayOfMonth(LocalDate date) {
        final Month month = date.getMonth().plus(1);
        final var year = date.plusMonths(1).getYear();
        return LocalDate.of(year, month, date.getDayOfMonth());
    }

    public static boolean isAfterOrEquals(LocalDate compare, LocalDate to) {
        return compare.isAfter(to) || compare.isEqual(to);
    }

    public static boolean isBeforeOrEquals(LocalDate compare, LocalDate to) {
        return compare.isBefore(to) || compare.isEqual(to);
    }
}
