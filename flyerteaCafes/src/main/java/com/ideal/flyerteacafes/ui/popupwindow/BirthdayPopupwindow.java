package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.andexert.calendarlistview.library.CalendarUtils;
import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.ui.wheelview.OnWheelChangedListener;
import com.ideal.flyerteacafes.ui.wheelview.WheelView;
import com.ideal.flyerteacafes.ui.wheelview.adapters.base.ListWheelAdapter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/12/18.
 */

public class BirthdayPopupwindow extends BottomPopupwindow {

    @ViewInject(R.id.wheelView_1)
    private WheelView wheelView1;
    @ViewInject(R.id.wheelView_2)
    private WheelView wheelView2;
    @ViewInject(R.id.wheelView_3)
    private WheelView wheelView3;

    ListWheelAdapter adapter1;
    ListWheelAdapter adapter2;
    ListWheelAdapter adapter3;

    private List<String> years = new ArrayList<>();
    private List<String> months = new ArrayList<>();
    private List<String> days = new ArrayList<>();


    public BirthdayPopupwindow(Context context) {
        super(context);
        bindData();
    }

    @Override
    protected int setBottomViewLayoutId() {
        return R.layout.pop_birthday_choose;
    }

    @Event({R.id.pop_choose_citys_close_btn, R.id.pop_choose_citys_ok_btn})
    private void click(View view) {
        switch (view.getId()) {
            case R.id.pop_choose_citys_close_btn:
                dismiss();
                break;
            case R.id.pop_choose_citys_ok_btn:
                String year = (String) adapter1.getItemText(wheelView1.getCurrentItem());
                String month = (String) adapter2.getItemText(wheelView2.getCurrentItem());
                String day = (String) adapter3.getItemText(wheelView3.getCurrentItem());
                Bundle bundle = new Bundle();
                bundle.putString("year", year);
                bundle.putString("month", month);
                bundle.putString("day", day);
                TagEvent tagEvent = new TagEvent(TagEvent.TAG_BirthDay);
                tagEvent.setBundle(bundle);
                EventBus.getDefault().post(tagEvent);
                dismiss();
                break;
        }
    }


    private void bindData() {
        for (int i = 1700; i <= 2100; i++) {
            years.add(String.valueOf(i));
        }

        for (int i = 1; i <= 12; i++) {
            months.add(String.valueOf(i));
        }


        wheelView1.setVisibleItems(5);
        wheelView2.setVisibleItems(5);
        wheelView3.setVisibleItems(5);


        wheelView1.setViewAdapter(adapter1 = new ListWheelAdapter<>(mContext, years));
        wheelView1.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                bindDaysAdapter();
            }

        });


        wheelView2.setViewAdapter(adapter2 = new ListWheelAdapter<>(mContext, months));
        wheelView2.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                bindDaysAdapter();
            }
        });

        bindDaysAdapter();


        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;


        int yearIndex = 0, monthIndex = 0;
        if (years.contains(String.valueOf(year))) {
            yearIndex = years.indexOf(String.valueOf(year));

        }
        if (months.contains(String.valueOf(month))) {
            monthIndex = months.indexOf(String.valueOf(month));
        }
        wheelView2.setCurrentItem(monthIndex);
        wheelView1.setCurrentItem(yearIndex);
    }

    /**
     * 根据年月 设置日期天数
     */
    private void setDays() {
        if (adapter1 == null || adapter2 == null) return;
        int year = DataTools.getInteger(years.get(wheelView1.getCurrentItem()));
        int month = DataTools.getInteger(months.get(wheelView2.getCurrentItem()));
        month--;
        int day = CalendarUtils.getDaysInMonth(month, year);
        days.clear();
        for (int i = 1; i <= day; i++) {
            days.add(String.valueOf(i));
        }
    }

    /**
     * 设置日期adapter
     */
    private void bindDaysAdapter() {
        setDays();
        if (DataUtils.isEmpty(days)) return;
        int dayIndex = 0;
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //点前日的所在days的下标
        if (days.contains(String.valueOf(day))) {
            dayIndex = days.indexOf(String.valueOf(day));
        }

        //获取上次选中的下标
        if (adapter3 != null) {
            dayIndex = wheelView3.getCurrentItem();
        }

        //上次选择的下标，超出当前月的下标
        if (dayIndex >= days.size()) {
            dayIndex = days.size() - 1;
        }

        wheelView3.setViewAdapter(adapter3 = new ListWheelAdapter<>(mContext, days));
        wheelView3.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                setDays();
            }
        });
        wheelView3.setCurrentItem(dayIndex);
    }


}
