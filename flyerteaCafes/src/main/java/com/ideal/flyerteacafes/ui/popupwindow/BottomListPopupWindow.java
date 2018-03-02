package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;

/**
 * Created by fly on 2016/12/20.
 */

public class BottomListPopupWindow extends PopupWindow {

    LinearLayout bottomView;
    Context context;

    public BottomListPopupWindow(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View mMenuView = inflater.inflate(R.layout.pop_comment_sort, null);
        bottomView = (LinearLayout) mMenuView.findViewById(R.id.bottom_btn_layout);

        mMenuView.findViewById(R.id.pop_comment_sort_bottom_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iPopCancle != null) iPopCancle.popCancle();
                dismiss();
            }
        });


        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.pop_comment_sort_bottom_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


        Animation anim = AnimationUtils.loadAnimation(context, R.anim.push_bottom_in);
        bottomView.startAnimation(anim);

    }


    public void setDatas(final IItemClick iClick, final String... datas) {
        if (bottomView == null) return;
        if (datas == null) return;
        bottomView.removeAllViews();

        for (int i = 0; i < datas.length; i++) {
            TextView tv = new TextView(context);
            tv.setTextColor(context.getResources().getColor(R.color.app_body_blacks));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            tv.setText(datas[i]);
            tv.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(50));
            bottomView.addView(tv, params);

            final int finalI = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (iClick != null) iClick.itemClick(finalI, datas[finalI]);
                    dismiss();
                }
            });

            if (i != datas.length - 1) {
                View lineView = new View(context);
                lineView.setBackgroundColor(context.getResources().getColor(R.color.app_line_color));
                LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                bottomView.addView(lineView, lineParams);
            }
        }

    }


    public interface IItemClick {
        void itemClick(int pos, String data);
    }

    private IPopDismiss iPopDismiss;
    private IPopCancle iPopCancle;

    public void setiPopDismiss(IPopDismiss iPopDismiss) {
        this.iPopDismiss = iPopDismiss;
    }

    public void setiPopCancle(IPopCancle iPopCancle) {
        this.iPopCancle = iPopCancle;
    }

    public interface IPopDismiss {
        void popDismiss();
    }

    public interface IPopCancle {
        void popCancle();
    }


    @Override
    public void dismiss() {

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.push_bottom_out);
        bottomView.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (iPopDismiss != null) iPopDismiss.popDismiss();
                BottomListPopupWindow.super.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
