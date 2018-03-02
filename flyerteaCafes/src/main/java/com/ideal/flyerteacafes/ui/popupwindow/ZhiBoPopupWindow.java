package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.ideal.flyerteacafes.utils.tools.V;

public class ZhiBoPopupWindow extends PopupWindow {

    private View deleteLine;
    private Button button1;
    private View mMenuView;
    private Handler handler;

    public ZhiBoPopupWindow(Context context, final Handler handler) {
        super(context);
        this.handler = handler;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.popupwindow_postdetails_bottom_layout, null);
        deleteLine= V.f(mMenuView, R.id.popupwindow_postdetails_bottom_flower_delete_line);
        button1= V.f(mMenuView,R.id.popupwindow_postdetails_bottom_reply_btn);
        Button button2= V.f(mMenuView,R.id.popupwindow_postdetails_bottom_flower_btn);
        Button button3= V.f(mMenuView,R.id.popupwindow_postdetails_bottom_delete_btn);
        button1.setText("小视频");
        button2.setText("拍照");
        button3.setText("相册");
        deleteLine.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                handler.sendEmptyMessage(IntentKey.ZHIBO_SHIPIN);
                dismiss();
            }
        });

        button2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(IntentKey.ZHIBO_PAIZHAO);

                dismiss();
            }
        });

        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(IntentKey.ZHIBO_XIANGCE);
                dismiss();
            }
        });

        mMenuView.findViewById(R.id.popupwindow_postdetails_bottom_canlce_btn).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dismiss();
            }
        });

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
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
                int height = mMenuView.findViewById(R.id.popupwindow_postdetails_bottom_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

    public void setHintShipin(){
//        deleteLine.setVisibility(View.GONE);
        button1.setVisibility(View.GONE);
    }


}
