package com.ideal.flyerteacafes.ui.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.ideal.flyerteacafes.R;

import org.xutils.x;

/**
 * Created by fly on 2017/3/9.
 * 底部弹出基类
 */

public abstract class BottomPopupwindow extends PopupWindow {
    protected RelativeLayout mView;
    protected View mBottomView;
    protected Context mContext;

    public BottomPopupwindow(Context context) {
        mContext = context;
        initPop();
    }

    protected void initPop() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = (RelativeLayout) inflater.inflate(R.layout.comment_pupupwindow_bottom_layout, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mView.addView(mBottomView = inflater.inflate(setBottomViewLayoutId(), null), params);

        x.view().inject(this, mView);
        // 设置SelectPicPopupWindow的View
        this.setContentView(mView);
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
        mView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mBottomView.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        if (isTouchTopViewDismiss())
                            showEndAnimation();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        if (mBottomView != null) {
            Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in);
            mBottomView.startAnimation(anim);
        }
    }

    /**
     * 子类实现，传递返回layout id
     *
     * @return
     */
    protected abstract int setBottomViewLayoutId();


    /**
     * 点击顶部view 默认关闭
     * 不需要关闭，需重写此方法
     *
     * @return
     */
    protected boolean isTouchTopViewDismiss() {
        return true;
    }


    /**
     * 显示关闭动画
     */
    public void showEndAnimation() {
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_out);
        mBottomView.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
