package com.andexert.calendarlistview.library;

import android.text.TextUtils;

import com.baoyz.pg.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by fly on 2016/6/21.
 */
@Parcelable
public class CalendarDay {
    private static final long serialVersionUID = -5456695978688356202L;
    private Calendar calendar;

    int day;
    int month;
    int year;
    String timeStr;

    public CalendarDay() {
        setTime(System.currentTimeMillis());
    }

    public CalendarDay(int year, int month, int day) {
        setDay(year, month, day);
    }

    public CalendarDay(long timeInMillis) {
        setTime(timeInMillis);
    }

    public CalendarDay(Calendar calendar) {
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void setTime(long timeInMillis) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        calendar.setTimeInMillis(timeInMillis);
        month = this.calendar.get(Calendar.MONTH);
        year = this.calendar.get(Calendar.YEAR);
        day = this.calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void set(CalendarDay calendarDay) {
        year = calendarDay.year;
        month = calendarDay.month;
        day = calendarDay.day;
    }

    public void setDay(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }


    private int mark = -1, dayStart = 1, dayEnd = 2;

    public CalendarDay setDayStart() {
        mark = dayStart;
        return this;
    }

    public CalendarDay setDayEnd() {
        mark = dayEnd;
        return this;
    }

    public Date getDate() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        if (mark == dayStart) {
            calendar.set(year, month, day, 0, 0, 0);
        } else if (mark == dayEnd) {
            calendar.set(year, month, day, 23, 59, 59);
        } else {
            calendar.set(year, month, day);
        }
        return calendar.getTime();
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month + 1;
    }

    public int getYear() {
        return year;
    }

    public String getWeekDay() {
        Date date = new Date();
        date.setYear(getYear() - 1900);
        date.setMonth(getMonth() - 1);
        date.setDate(getDay());
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        return dateFm.format(date);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(year);
        stringBuilder.append("-");
        stringBuilder.append(String.format("%0" + 2 + "d", getMonth()));
        stringBuilder.append("-");
        stringBuilder.append(String.format("%0" + 2 + "d", day));
        return this.timeStr = stringBuilder.toString();
    }

    public void setTimeString(String timeStr) {
        this.timeStr = timeStr;
        if (!TextUtils.isEmpty(timeStr)) {
            String[] timeArray = timeStr.split("-");
            if (timeArray.length == 3) {
                year = Integer.parseInt(timeArray[0]);
                month = Integer.parseInt(timeArray[1]);
                month--;
                day = Integer.parseInt(timeArray[2]);
            }
        }
    }

}
