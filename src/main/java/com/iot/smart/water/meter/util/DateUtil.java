package com.iot.smart.water.meter.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String formatDate(Date date) {
        return formatDate(date, "yyyyMMddHHmmss");
    }

    public static String formatDate(Date date, String patten){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(patten);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDate(String date, String patten) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(patten);
            return sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isSameDay(Date t1, Date t2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(t1.getTime());
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(t2.getTime());
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static String getMonthStartTimestamp(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND, 0);
        return formatDate(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getMonthEndTimestamp(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return formatDate(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    public static int getWeekDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        return week - 1 == 0 ? 7 : week - 1;
    }

    public static String formatDiffDate(Date date, int diffDay, String patten) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (diffDay != 0) {
            calendar.add(Calendar.DATE, diffDay);
        }
        Date targetDate = calendar.getTime();
        return formatDate(targetDate, patten);
    }
}
