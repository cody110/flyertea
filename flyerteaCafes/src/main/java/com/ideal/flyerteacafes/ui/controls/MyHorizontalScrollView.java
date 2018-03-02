package com.ideal.flyerteacafes.ui.controls;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.ideal.flyerteacafes.utils.LogFly;

/**
 * Created by fly on 2016/12/5.
 * 添加滚动监听
 */

public class MyHorizontalScrollView extends HorizontalScrollView {


    public MyHorizontalScrollView(Context context,
                                  AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }


    public interface ScrollViewListener {

        void onScrollChanged(ScrollType scrollType, int currentX);

    }

    private Handler mHandler = new Handler();
    private ScrollViewListener scrollViewListener;

    /**
     * 滚动状态   IDLE 滚动停止  TOUCH_SCROLL 手指拖动滚动         FLING滚动
     *
     * @author DZC
     * @version XHorizontalScrollViewgallery
     * @Time 2014-12-7 上午11:06:52
     */
    public enum ScrollType {
        IDLE, TOUCH_SCROLL, FLING
    }

    ;

    /**
     * 记录当前滚动的距离
     */
    private int currentX = 0;
    /**
     * 当前滚动状态
     */
    private ScrollType scrollType = ScrollType.IDLE;
    /**
     * 滚动监听间隔
     */
    private int scrollDealy = 50;
    /**
     * 滚动监听runnable
     */
    private Runnable scrollRunnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (getScrollX() == currentX) {
                currentX = getScrollX();
                //滚动停止  取消监听线程
                LogFly.d("", "停止滚动");
                scrollType = ScrollType.IDLE;
                if (scrollViewListener != null) {
                    scrollViewListener.onScrollChanged(scrollType, currentX);
                }
                mHandler.removeCallbacks(this);
                return;
            } else {
                currentX = getScrollX();
                //手指离开屏幕    view还在滚动的时候
                LogFly.d("", "Fling。。。。。");
                scrollType = ScrollType.FLING;
                if (scrollViewListener != null) {
                    scrollViewListener.onScrollChanged(scrollType, currentX);
                }
            }
            mHandler.postDelayed(this, scrollDealy);
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float downX = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                break;

            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                int moveX = (int) Math.abs(downX - ev.getX());
                if (moveX < 20) {
                    return false;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                this.scrollType = ScrollType.TOUCH_SCROLL;
                scrollViewListener.onScrollChanged(scrollType, currentX);
                //手指在上面移动的时候   取消滚动监听线程
                mHandler.removeCallbacks(scrollRunnable);
                break;
            case MotionEvent.ACTION_UP:
                //手指移动的时候
                mHandler.post(scrollRunnable);
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 设置滚动监听
     * 2014-12-7 下午3:59:51
     *
     * @param listener
     * @return void
     * @author DZC
     * @TODO
     */
    public void setOnScrollStateChangedListener(ScrollViewListener listener) {
        this.scrollViewListener = listener;
    }

}
