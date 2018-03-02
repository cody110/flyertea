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
import com.ideal.flyerteacafes.utils.tools.V;
import com.ideal.flyerteacafes.utils.FinalUtils;

public class SavaPicturePopupWindow extends PopupWindow {

    private View mMenuView;
    private Handler handler;

    public SavaPicturePopupWindow(Context context, final Handler handler) {
        super(context);
        this.handler = handler;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.popupwindow_postdetails_bottom_layout, null);

        V.f(mMenuView, R.id.popupwindow_postdetails_bottom_reply_btn).setVisibility(View.GONE);


        V.f(mMenuView, R.id.popupwindow_postdetails_bottom_flower_reply_line).setVisibility(View.GONE);
        Button btn= V.f(mMenuView, R.id.popupwindow_postdetails_bottom_flower_btn);
        btn.setText(context.getString(R.string.save));

        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                handler.sendEmptyMessage(FinalUtils.POST_DETAILS_FLOWER);
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

}
