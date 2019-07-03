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

    public static boolean isSameDay(long t1, long t2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(t1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(t2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
