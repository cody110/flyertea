package com.ideal.flyerteacafes.utils.tools;

import android.text.TextUtils;

import com.andexert.calendarlistview.library.CalendarUtils;
import com.ideal.flyerteacafes.utils.LogFly;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * 日期Util类
     *
     * @author calvin
     */
    private static String defaultDatePattern = "yyyyMMddHHmmss";

    /**
     * 在日期上增加数个整月
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();//日历对象
        cal.setTime(date);//设置当前日期
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    public static String getLastDayOfMonth(String year, String month) {
        Calendar cal = Calendar.getInstance();
        // 年
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        // 月，因为Calendar里的月是从0开始，所以要-1
        // cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        // 日，设为一号
        cal.set(Calendar.DATE, 1);
        // 月份加一，得到下个月的一号
        cal.add(Calendar.MONTH, 1);
        // 下一个月减一为本月最后一天
        cal.add(Calendar.DATE, -1);
        return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));// 获得月末是几号
    }


    /**
     * 系统时间
     *
     * @return
     */
    public static Date getSysDate() {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return curDate;
    }

    /**
     * 字符串转Date
     *
     * @param dateString
     * @return
     * @throws java.text.ParseException
     */
//    public static Date getDate(String dateString) throws java.text.ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat(defaultDatePattern);//小写的mm表示的是分钟
//        java.util.Date date = sdf.parse(dateString);
//        return date;
//    }


    /**
     * Date格式化输出字符串
     *
     * @param date
     * @return
     */
    public static String getFormatDateStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(defaultDatePattern);//格式化对象
        return sdf.format(date);
    }

    /**
     * 得到一个月前的时间字符串
     *
     * @return
     */
//    public static String getOneMonthTime() {
//        Date date = getSysDate();//当前日期
//        date = addMonth(date, -1);
//        return getFormatDateStr(date);
//    }

    /**
     * @description: 将时间戳转换为日期格式
     * @author: Cindy
     * @date: 2016/5/11 13:14
     */
    public static String getDateToString(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(Long.parseLong(time));

        return sdf.format(date);
    }

    /**
     * 得到当前时间戳
     *
     * @return
     */
    public static long getDateline() {
        return System.currentTimeMillis();
    }


    public static boolean isSameDate(Date date1, Date date2) {
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

    public static int daysBetween(String sm, String b) {
        if (TextUtils.isEmpty(sm) || TextUtils.isEmpty(b)) return 0;
        Date smdate, bdate;
        smdate = new Date(DataTools.getLong(sm));
        bdate = new Date(DataTools.getLong(b));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);

            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }


    }


    /**
     * 获得某个月最大天数
     *
     * @param year  年份
     * @param month 月份 (1-12)
     * @return 某个月最大天数
     */
    public static int getMaxDayByYearMonth(int year, int month) {
        month -= 1;
        return CalendarUtils.getDaysInMonth(month, year);
    }

}
