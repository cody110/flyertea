package com.ideal.flyerteacafes.ui.controls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 为帖子详情页，定制滑动隐藏回复布局
 *
 * @author fly
 */
public class PostDetailsScrollView extends ScrollView {
    private OnScrollListenerReturn onScrollListener;
    private int lastScrollY;

    public PostDetailsScrollView(Context context) {
        this(context, null);
    }

    public PostDetailsScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PostDetailsScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnScrollListener(OnScrollListenerReturn onScrollListener) {
        this.onScrollListener = onScrollListener;
    }


    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            int scrollY = PostDetailsScrollView.this.getScrollY();

            if (lastScrollY != scrollY) {
                lastScrollY = scrollY;
                handler.sendMessageDelayed(handler.obtainMessage(), 5);
                if (onScrollListener != null) {
                    onScrollListener.onScroll(false, scrollY);
                }
            } else {
                if (onScrollListener != null) {
                    onScrollListener.onScroll(true, scrollY);
                }
            }


        }

        ;

    };

    int downY;
    boolean flag = false;
    int disY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (onScrollListener != null) {
                    onScrollListener.onScroll(flag, this.getScrollY());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (onScrollListener != null) {
                    Log.d("PostDetailsScrollView", "disY:" + disY + "flag:" + flag);
                    onScrollListener.onScroll(flag, this.getScrollY());
                }
                break;
            case MotionEvent.ACTION_UP:
                if (onScrollListener != null) {
                    onScrollListener.onScroll(flag, this.getScrollY());
                }
                Log.d("PostDetailsScrollView", "ACTION_UP:");
//			handler.sendMessageDelayed(handler.obtainMessage(), 5);

                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = this.getScrollY();
                Log.d("PostDetailsScrollView", "ACTION_DOWN:downY:" + downY);
                break;
            case MotionEvent.ACTION_MOVE:
                disY = this.getScrollY() - downY;
                Log.d("PostDetailsScrollView:", "ACTION_MOVE:getScrollY:" + this.getScrollY() + "");
                Log.d("PostDetailsScrollView:", "disY:" + disY);
                if (disY < 0)
                    disY = (-disY);
                if (disY > 10) {
                    return true;
                }
//			break;
            case MotionEvent.ACTION_UP:
                Log.d("PostDetailsScrollView", "onInterceptTouchEvent  ACTION_UP:");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 滚动监听接口，true,表示没有滚动，false表示滚动了。
     *
     * @author fly
     */
    public interface OnScrollListenerReturn {
        public void onScroll(boolean bol, int scrollY);
    }


}
