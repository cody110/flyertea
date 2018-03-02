package com.ideal.flyerteacafes.ui.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by fly on 2016/12/26.
 */

public class WriteThreadImageView extends ImageView implements View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {

    int parentViewWidth;
    int parentViewHeight;

    int startX;
    int startY;
    int downX;
    int downY;
    int upX;
    int upY;

    public WriteThreadImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
        this.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }


    public void setParentSize() {
        RelativeLayout relativeLayout = (RelativeLayout) getParent();
        parentViewWidth = relativeLayout.getWidth();
        parentViewHeight = relativeLayout.getHeight();
    }

    int newt, newb, newl, newr;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:// 手指第一次触摸到屏幕
                if (parentViewWidth == 0)
                    setParentSize();
                this.startX = (int) event.getRawX();
                this.startY = (int) event.getRawY();
                this.downX = this.startX;
                this.downY = this.startY;
                break;
            case MotionEvent.ACTION_MOVE:// 手指移动
                int newX = (int) event.getRawX();
                int newY = (int) event.getRawY();

                int dx = newX - this.startX;
                int dy = newY - this.startY;

                // 计算出来控件原来的位置
                int l = this.getLeft();
                int r = this.getRight();
                int t = this.getTop();
                int b = this.getBottom();

                newt = t + dy;
                newb = b + dy;
                newl = l + dx;
                newr = r + dx;

                if ((newl < 0) || (newt < 0)
                        || (newr > parentViewWidth)
                        || (newb > parentViewHeight)) {
                    break;
                }

                // 更新iv在屏幕的位置.
                this.layout(newl, newt, newr, newb);
                this.startX = (int) event.getRawX();
                this.startY = (int) event.getRawY();

                break;
            case MotionEvent.ACTION_UP: // 手指离开屏幕的一瞬间
                this.upX = (int) event.getRawX();
                this.upY = (int) event.getRawY();

                if (Math.abs(upX - downX) < 5 && Math.abs(upY - downY) < 5) {//相当于单击事件
                    if (this.l != null)
                        this.l.onClick(this);
                }

                break;
        }
        return true;
    }

    OnClickListener l;

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        this.l = l;
    }

    //TODO 解决布局重新刷新了一边，导致这个控件又回到了起始位置
    @Override
    public void onGlobalLayout() {
        if (newl != 0)
            this.layout(newl, newt, newr, newb);
    }
}
