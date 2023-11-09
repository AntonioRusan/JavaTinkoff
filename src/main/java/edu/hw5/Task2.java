package edu.hw5;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Task2 {
    private static final int DAY_13 = 13;

    private Task2() {
    }

    public static List<LocalDate> getAllFriday13th(int yearNumber) {
        Year year = Year.of(yearNumber);
        List<LocalDate> allFriday13th = new ArrayList<>();
        for (var month : EnumSet.range(Month.JANUARY, Month.DECEMBER)) {
            LocalDate day13th = year.atMonth(month).atDay(DAY_13);
            if (day13th.getDayOfWeek() == DayOfWeek.FRIDAY) {
                allFriday13th.add(day13th);
            }
        }
        return allFriday13th;
    }

    public static LocalDate getNearestFriday13th(LocalDate currentDate) {
        LocalDate prev = getPreviousFriday13th(currentDate);
        LocalDate next = getNextFriday13th(currentDate);
        Period prevDiff = Period.between(prev, currentDate);
        Period nextDiff = Period.between(currentDate, next);

        if (LocalDate.MIN.plus(nextDiff).isBefore(LocalDate.MIN.plus(prevDiff))) {
            return next;
        } else {
            return prev;
        }
    }

    public static LocalDate getNextFriday13th(LocalDate inputDate) {
        LocalDate currentDate = inputDate;
        boolean isFriday13th = false;
        while (!isFriday13th) {
            currentDate = currentDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
            isFriday13th = currentDate.getDayOfMonth() == DAY_13;
        }
        return currentDate;
    }

    public static LocalDate getPreviousFriday13th(LocalDate inputDate) {
        LocalDate currentDate = inputDate;
        boolean isFriday13th = false;
        while (!isFriday13th) {
            currentDate = currentDate.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
            isFriday13th = currentDate.getDayOfMonth() == DAY_13;
        }
        return currentDate;
    }
}
