package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.RealNameBaseDataBean;
import com.ideal.flyerteacafes.ui.wheelview.OnWheelChangedListener;
import com.ideal.flyerteacafes.ui.wheelview.WheelView;
import com.ideal.flyerteacafes.ui.wheelview.adapters.base.ListWheelAdapter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2017/5/25.
 */

public class CityPopupWindow4 extends BottomPopupwindow {

    @ViewInject(R.id.wheelView_1)
    private WheelView wheelView1;
    @ViewInject(R.id.wheelView_2)
    private WheelView wheelView2;
    @ViewInject(R.id.wheelView_3)
    private WheelView wheelView3;

    List<RealNameBaseDataBean.Areas> datas;
    ListWheelAdapter adapter1;
    ListWheelAdapter adapter2;
    ListWheelAdapter adapter3;

    private Handler handler;
    private int what;

    public CityPopupWindow4(Context context, Handler handler, int what) {
        super(context);
        this.handler = handler;
        this.what = what;
    }

    public void bindData(final List<RealNameBaseDataBean.Areas> datas) {
        if (DataUtils.isEmpty(datas)) return;
        this.datas = datas;
        wheelView1.setVisibleItems(5);
        wheelView2.setVisibleItems(5);
        wheelView3.setVisibleItems(5);


        wheelView1.setViewAdapter(adapter1 = new ListWheelAdapter<RealNameBaseDataBean.Areas>(mContext, datas) {
            @Override
            public CharSequence getItemText(int index) {
                return items.get(index).getName();
            }
        });
        wheelView1.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                RealNameBaseDataBean.Areas bean = DataTools.getBeanByListPos(datas, newValue);
                if (bean != null)
                    setChooseAdapter2(bean.getCitys());
            }

        });
        wheelView1.setCurrentItem(0);


        setChooseAdapter2(datas.get(0).getCitys());
    }

    private void setChooseAdapter2(final List<RealNameBaseDataBean.Citys> datas) {
        if (DataUtils.isEmpty(datas)) return;
        wheelView2.setViewAdapter(adapter2 = new ListWheelAdapter<RealNameBaseDataBean.Citys>(mContext, datas) {
            @Override
            public CharSequence getItemText(int index) {
                return items.get(index).getName();
            }
        });
        wheelView2.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                RealNameBaseDataBean.Citys citys = DataTools.getBeanByListPos(datas, newValue);
                if (citys != null)
                    setChooseAdapter3(datas.get(newValue).getDistricts());
            }
        });
        wheelView2.setCurrentItem(0);
        setChooseAdapter3(datas.get(0).getDistricts());

    }


    private void setChooseAdapter3(final List<RealNameBaseDataBean.Districts> datas) {
        if (DataUtils.isEmpty(datas)) return;
        wheelView3.setViewAdapter(adapter3 = new ListWheelAdapter<RealNameBaseDataBean.Districts>(mContext, datas) {
            @Override
            public CharSequence getItemText(int index) {
                return items.get(index).getName();
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

                Message msg = Message.obtain();
                msg.obj = value1 + "/" + value2 + "/" + value3;

                msg.what = what;
                handler.sendMessage(msg);
                showEndAnimation();
                break;
        }
    }
}
