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
import com.ideal.flyerteacafes.ui.activity.UserDatumActvity;
import com.ideal.flyerteacafes.utils.tools.V;

public class UserdatumDeletePopupwindow extends PopupWindow {

    private View mMenuView;
    private Button mbtnDelete,mBtnCancel;

    public UserdatumDeletePopupwindow(Context context, final Handler handler) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.popupwindow_userdatum_delete_layout, null);

        mbtnDelete= V.f(mMenuView,R.id.popupwindow_user_bottom_delete_btn);
        mBtnCancel= V.f(mMenuView,R.id.popupwindow_user_bottom_canlce_btn);

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

        mbtnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.sendEmptyMessage(UserDatumActvity.POP_DELETE);
                dismiss();
            }
        });

        mBtnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.popupwindow_user_bottom_layout).getTop();
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
