package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.RealNameBaseDataBean;
import com.ideal.flyerteacafes.ui.wheelview.OnWheelChangedListener;
import com.ideal.flyerteacafes.ui.wheelview.WheelView;
import com.ideal.flyerteacafes.ui.wheelview.adapters.base.ListWheelAdapter;
import com.ideal.flyerteacafes.utils.DataUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2017/5/25.
 */

public class HangyePopupwindow extends BottomPopupwindow {

    @ViewInject(R.id.pop_city_choose_sheng)
    private WheelView wheelView1;
    @ViewInject(R.id.pop_city_choose_shi)
    private WheelView wheelView2;


    private List<RealNameBaseDataBean.Career> datas;
    private Handler handler;
    private int what;
    private ListWheelAdapter adapter1, adapter2;

    public HangyePopupwindow(Context context, Handler handler, int what) {
        super(context);
        this.handler = handler;
        this.what = what;
    }


    public void bindData(final List<RealNameBaseDataBean.Career> datas) {
        if (DataUtils.isEmpty(datas)) return;
        this.datas = datas;
        wheelView1.setVisibleItems(5);
        wheelView1.setViewAdapter(adapter1 = new ListWheelAdapter<RealNameBaseDataBean.Career>(mContext, datas) {
            @Override
            public CharSequence getItemText(int index) {
                return items.get(index).getName();
            }
        });
        wheelView1.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                RealNameBaseDataBean.Career bean = datas.get(wheelView1.getCurrentItem());
                chooseAdapter2(bean.getSubs());
            }

        });
        wheelView1.setCurrentItem(0);

        wheelView2.setVisibleItems(5);
        chooseAdapter2(datas.get(0).getSubs());
    }

    private void chooseAdapter2(List<RealNameBaseDataBean.Career> datas) {
        if (datas == null) datas = new ArrayList<>();
        wheelView2.setViewAdapter(adapter2 = new ListWheelAdapter<RealNameBaseDataBean.Career>(mContext, datas) {
            @Override
            public CharSequence getItemText(int index) {
                return items.get(index).getName();
            }
        });
        wheelView2.setCurrentItem(0);

    }

    @Override
    protected int setBottomViewLayoutId() {
        return R.layout.popupwindow_choose_citys_layout;
    }

    @Event({R.id.pop_choose_citys_close_btn, R.id.pop_choose_citys_ok_btn})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.pop_choose_citys_close_btn:
                showEndAnimation();
                break;
            case R.id.pop_choose_citys_ok_btn:
                Message msg = handler.obtainMessage();
                msg.what = what;
                msg.obj = datas.get(wheelView1.getCurrentItem()).getName() + "/" + adapter2.getItemText(wheelView2.getCurrentItem());
                handler.sendMessage(msg);
                showEndAnimation();
                break;
        }
    }
}
