package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.wheelview.OnWheelChangedListener;
import com.ideal.flyerteacafes.ui.wheelview.WheelView;
import com.ideal.flyerteacafes.ui.wheelview.adapters.base.ListWheelAdapter;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by fly on 2017/4/18.
 */

public class TaskChooseTimePop extends BottomPopupwindow {


    public interface IChooseTime {
        void chooseData(String showTime, long dataline);
    }

    private IChooseTime iChooseTime;

    public void setIChooseTime(IChooseTime i) {
        iChooseTime = i;
    }


    private static final String KEY_LOG = "xxxxxxxxxxx";
    private static final int MOVE_BEFORE_YEAR = 5;//前移5年
    private static final int MOVE_AFTER_YEAR = 5;//后移5年

    private List<String> years = new ArrayList<>();
    private List<String> months = new ArrayList<>();
    private List<String> days = new ArrayList<>();


    @ViewInject(R.id.pop_three_level_year)
    WheelView yearWv;
    @ViewInject(R.id.pop_three_level_month)
    WheelView monthWv;
    @ViewInject(R.id.pop_three_level_day)
    WheelView dayWv;

    public TaskChooseTimePop(Context context) {
        super(context);
        initData();
        initView();
    }

    @Event({R.id.btn_ok, R.id.cancle_btn})
    private void onclick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                int year = DataTools.getInteger(years.get(yearWv.getCurrentItem()).replace("年", ""));
                int month = DataTools.getInteger(months.get(monthWv.getCurrentItem()).replace("月", "")) - 1;
                int day = DataTools.getInteger(days.get(dayWv.getCurrentItem()).replace("日", ""));
                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                if (iChooseTime != null) {
                    String timeStr;
                    if (year == mYear && month == mMonth && day == mDay) {
                        timeStr = "今天 " + (mMonth + 1) + "-" + day;
                    } else {
                        timeStr = year + "-" + (month + 1) + "-" + day;
                    }
                    iChooseTime.chooseData(timeStr, c.getTimeInMillis());
                }
                dismiss();
                break;

            case R.id.cancle_btn:
                dismiss();
                break;
        }
    }

    @Override
    protected int setBottomViewLayoutId() {
        return R.layout.pop_choose_time;
    }


    private void initView() {
        yearWv.setVisibleItems(5);
        monthWv.setVisibleItems(5);
        dayWv.setVisibleItems(5);
        yearWv.setViewAdapter(new ListWheelAdapter<>(mContext, years));
        yearWv.setCurrentItem(5);
        setMonth(mYear);
        monthWv.setCurrentItem(mMonth);
        setDay(mYear, mMonth);
        dayWv.setCurrentItem(mDay - 1);
        yearWv.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int year = mYear - MOVE_BEFORE_YEAR + yearWv.getCurrentItem();
                setMonth(year);
            }

        });

        monthWv.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int year = mYear - MOVE_BEFORE_YEAR + yearWv.getCurrentItem();
                int month = DataTools.getInteger(months.get(monthWv.getCurrentItem()).replace("月", "")) - 1;
                setDay(year, month);
            }
        });
    }


    private int mYear, mMonth, mDay;


    private void initData() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR); //获取当前年份
        mMonth = c.get(Calendar.MONTH);//获取当前月份
        mDay = c.get(Calendar.DAY_OF_MONTH);//获取当前月份的日期号码
        int mYear = this.mYear - MOVE_BEFORE_YEAR;
        for (int i = 0; i <= MOVE_BEFORE_YEAR + MOVE_AFTER_YEAR; i++) {
            years.add((mYear + i) + "年");
        }
    }


    private void setMonth(int year) {
        months.clear();
        if (year == mYear - MOVE_BEFORE_YEAR) {
            for (int i = 0; i < 12 - mMonth; i++) {
                months.add((mMonth + 1 + i) + "月");
            }
        } else if (year == mYear + 5) {
            for (int i = 1; i <= mMonth + 1; i++) {
                months.add(i + "月");
            }
        } else {
            for (int i = 1; i <= 12; i++) {
                months.add(i + "月");
            }
        }
        monthWv.setViewAdapter(new ListWheelAdapter<>(mContext, months));

        if (months.size() > monthWv.getCurrentItem()) {
            int month = DataTools.getInteger(months.get(monthWv.getCurrentItem()).replace("月", "")) - 1;
            setDay(year, month);
        }
    }

    private void setDay(int year, int month) {
        days.clear();
        int monthDays = getDaysInMonth(month, year);
        if (year == mYear - MOVE_BEFORE_YEAR && month == mMonth) {//五年前的 同月
            for (int i = 0; i <= monthDays - mDay; i++) {
                days.add((i + mDay) + "日");
            }
        } else if (year == mYear + MOVE_AFTER_YEAR && month == mMonth) {//五年后的 同月
            for (int i = 1; i <= mDay; i++) {
                days.add(i + "日");
            }
        } else {
            for (int i = 1; i <= monthDays; i++) {
                days.add(i + "日");
            }
        }
        dayWv.setViewAdapter(new ListWheelAdapter<>(mContext, days));

    }


    public static int getDaysInMonth(int month, int year) {
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Invalid Month" + month + "year" + year);
        }
    }

}
