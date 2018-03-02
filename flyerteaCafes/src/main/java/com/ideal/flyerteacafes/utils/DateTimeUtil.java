package com.ideal.flyerteacafes.utils;

import com.andexert.calendarlistview.library.CalendarDay;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Cindy on 2016/12/27.
 */
public class DateTimeUtil {

    /**
     * 获取系统当前时间（年、月、日、时、分、秒）
     *
     * @return
     */
    public static String getUpdateDatetime() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 日期相减的方法
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDateDays(String date1, String date2) {

        if (date1 == null || date1.length() < 10)
            return 0;
        if (date2 == null || date2.length() < 10)
            return 0;
        int now = DataTools.getInteger(date1);
        int regist = DataTools.getInteger(date2);
        int between = (now - regist);
        between = between / 60 / 60 / 24;
        return between;
    }

    public static String getTime() {

        int time = (int) (System.currentTimeMillis() / 1000);//获取系统时间的10位的时间戳

        String str = String.valueOf(time);

        return str;

    }


    private static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    public static boolean isSameYear(long dateline1, long dateline2) {

        Date date1 = new Date(dateline1);
        Date date2 = new Date(dateline2);

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameYear;
    }


    public static int daysBetween(String smdate, String bdate) {
        long time1 = Long.parseLong(smdate);
        long time2 = Long.parseLong(bdate);
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

}
