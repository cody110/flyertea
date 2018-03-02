package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.dal.CityHelper;
import com.ideal.flyerteacafes.model.entity.CitysBean;
import com.ideal.flyerteacafes.ui.activity.EditDataActivity;
import com.ideal.flyerteacafes.ui.wheelview.OnWheelChangedListener;
import com.ideal.flyerteacafes.ui.wheelview.WheelView;
import com.ideal.flyerteacafes.ui.wheelview.adapters.CityListAdapter;

import java.util.List;

public class CityPopupWindow extends PopupWindow {

    View mMenuView;
    Context context;
    List<CitysBean> shiCityList;
    CityHelper helper;
    WheelView wheelView2;
    WheelView wheelView;
    CitysBean shengBean;
    CitysBean shiBean;

    private String city = "";

    public CityPopupWindow(final Context context, final Handler handler) {
        super(context);
        this.context=context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.popupwindow_choose_citys_layout,
                null);

        View closeBtn = mMenuView.findViewById(R.id.pop_choose_citys_close_btn);
        View ensureBtn = mMenuView.findViewById(R.id.pop_choose_citys_ok_btn);

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.FILL_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView
                        .findViewById(R.id.pop_city_choose_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        helper = new CityHelper();
        final List<CitysBean> cityList = helper.getShengList();

        wheelView2 = (WheelView) mMenuView
                .findViewById(R.id.pop_city_choose_shi);
        wheelView2.setVisibleItems(5);
        chooseAdapter2(1);

        wheelView2.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {

            }

        });

        wheelView = (WheelView) mMenuView
                .findViewById(R.id.pop_city_choose_sheng);
        wheelView.setVisibleItems(5);
        wheelView.setViewAdapter(new CityListAdapter(context,cityList));
        wheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                CitysBean bean = cityList.get(wheelView.getCurrentItem());
                if (bean == null)
                    return;
                chooseAdapter2(bean.getCid());
            }

        });

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                chooseAdapter2(1);
            }
        }, 100);

        closeBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dismiss();
            }
        });

        ensureBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if(cityList!=null){
                    shengBean = cityList.get(wheelView.getCurrentItem());
                }
                if (shengBean != null)
                    city = shengBean.getCity();
                if(shiCityList!=null){
                    shiBean =  shiCityList.get(wheelView2.getCurrentItem());
                }
                if (shiBean != null)
                    city = city + "\t\t" + shiBean.getCity();
                Message msg = Message.obtain();
                msg.obj = city;
                msg.what = EditDataActivity.WHAT_CITY;
                handler.sendMessage(msg);
                dismiss();
            }
        });

    }

    public void chooseAdapter2(int cid) {
        shiCityList = helper.getShiList(cid);
        wheelView2.setViewAdapter(new CityListAdapter(context,shiCityList));
        wheelView2.setCurrentItem(0);

    }

}
