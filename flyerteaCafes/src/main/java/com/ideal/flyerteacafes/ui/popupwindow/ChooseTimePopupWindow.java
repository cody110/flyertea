package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.RealNameBaseDataBean;
import com.ideal.flyerteacafes.ui.wheelview.OnWheelChangedListener;
import com.ideal.flyerteacafes.ui.wheelview.WheelView;
import com.ideal.flyerteacafes.ui.wheelview.adapters.base.ListWheelAdapter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.DateUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/5/25.
 */

public class ChooseTimePopupWindow extends BottomPopupwindow {

    @ViewInject(R.id.wheelView_1)
    private WheelView wheelView1;
    @ViewInject(R.id.wheelView_2)
    private WheelView wheelView2;
    @ViewInject(R.id.wheelView_3)
    private WheelView wheelView3;

    ListWheelAdapter adapter1;
    ListWheelAdapter adapter2;
    ListWheelAdapter adapter3;


    int year, month, day;

    public ChooseTimePopupWindow(Context context) {
        super(context);

        bindData();
    }

    public void bindData() {

        Calendar date = Calendar.getInstance();

        year = date.get(Calendar.YEAR);
        month = date.get(Calendar.MONTH);
        day = date.get(Calendar.DATE);

        LogFly.e("year:" + year + "month:" + month + "day:" + day);

        final List<String> years = new ArrayList<>();

        years.add(String.valueOf(year));
        years.add(String.valueOf(year + 1));
        years.add(String.valueOf(year + 2));


        wheelView1.setVisibleItems(5);
        wheelView2.setVisibleItems(5);
        wheelView3.setVisibleItems(5);


        wheelView1.setViewAdapter(adapter1 = new ListWheelAdapter<String>(mContext, years) {
            @Override
            public CharSequence getItemText(int index) {
                return items.get(index);
            }
        });
        wheelView1.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                setChooseAdapter2(initMonthDatas(newValue));
            }

        });
        wheelView1.setCurrentItem(0);
        setChooseAdapter2(initMonthDatas(0));

    }

    private List<String> initMonthDatas(int selectIndex) {
        int startMonth = 1, endMontn = 12;
        if (selectIndex == 0) {
            startMonth = month + 1;
        } else if (selectIndex == 1) {
            endMontn = 12;
        } else {
            endMontn = month + 1;
        }
        List<String> months = new ArrayList<>();
        for (int i = startMonth; i <= endMontn; i++) {
            months.add(String.valueOf(i));
        }
        return months;
    }


    private List<String> initDayDatas() {
        int startDay = 1, endDay = 0;


        if (wheelView1.getCurrentItem() == 0 && wheelView2.getCurrentItem() == 0) {//第一个月
            startDay = day;
            endDay = DateUtil.getMaxDayByYearMonth(DataTools.getInteger(adapter1.getItemText(wheelView1.getCurrentItem())), DataTools.getInteger(adapter2.getItemText(wheelView2.getCurrentItem())));
        } else if (wheelView1.getCurrentItem() == adapter1.getItemsCount() - 1 && wheelView2.getCurrentItem() == adapter2.getItemsCount() - 1) {//最后一个月
            endDay = day;
        } else {
            endDay = DateUtil.getMaxDayByYearMonth(DataTools.getInteger(adapter1.getItemText(wheelView1.getCurrentItem())), DataTools.getInteger(adapter2.getItemText(wheelView2.getCurrentItem())));
        }


        List<String> days = new ArrayList<>();
        for (int i = startDay; i < endDay; i++) {
            days.add(String.valueOf(i));
        }
        return days;
    }

    private void setChooseAdapter2(final List<String> datas) {
        if (DataUtils.isEmpty(datas)) return;
        LogFly.e("?:" + datas.size());
        wheelView2.setViewAdapter(adapter2 = new ListWheelAdapter<String>(mContext, datas) {
            @Override
            public CharSequence getItemText(int index) {
                return items.get(index);
            }
        });
        wheelView2.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {

                setChooseAdapter3(initDayDatas());
            }
        });
        wheelView2.setCurrentItem(0);
        setChooseAdapter3(initDayDatas());

    }


    private void setChooseAdapter3(final List<String> datas) {
        if (DataUtils.isEmpty(datas)) return;
        wheelView3.setViewAdapter(adapter3 = new ListWheelAdapter<String>(mContext, datas) {
            @Override
            public CharSequence getItemText(int index) {
                return items.get(index);
            }
        });

        wheelView3.setCurrentItem(0);
    }


    @Override
    protected int setBottomViewLayoutId() {
        return R.layout.pop_4_wheelview;
    }

    @Event({R.id.pop_choose_citys_close_btn, R.id.pop_choose_citys_ok_btn})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.pop_choose_citys_close_btn:
                showEndAnimation();
                break;
            case R.id.pop_choose_citys_ok_btn:
                String value1 = "", value2 = "", value3 = "";
                if (adapter1 != null)
                    value1 = (String) adapter1.getItemText(wheelView1.getCurrentItem());
                if (adapter2 != null)
                    value2 = (String) adapter2.getItemText(wheelView2.getCurrentItem());
                if (adapter3 != null)
                    value3 = (String) adapter3.getItemText(wheelView3.getCurrentItem());

                TagEvent tagEvent = new TagEvent(TagEvent.TAG_SHENGQING_MEDAILS_TIME);
                Bundle bundle = new Bundle();
                bundle.putString("data", value1 + "-" + String.format("%0" + 2 + "d", DataTools.getInteger(value2)) + "-" + String.format("%0" + 2 + "d", DataTools.getInteger(value3)));
                tagEvent.setBundle(bundle);
                EventBus.getDefault().post(tagEvent);

                showEndAnimation();
                break;
        }
    }


}
