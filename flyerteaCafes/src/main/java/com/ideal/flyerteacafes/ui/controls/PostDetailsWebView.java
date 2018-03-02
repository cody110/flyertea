package com.ideal.flyerteacafes.ui.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

import com.ideal.flyerteacafes.utils.tools.DateUtil;


public class PostDetailsWebView extends WebView {
    public ScrollInterface mScrollInterface;
    private int lastScrollY;
    private int time;

    public PostDetailsWebView(Context context) {
        super(context);
    }

    public PostDetailsWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public PostDetailsWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:

                lastScrollY = this.getScrollY();

            case MotionEvent.ACTION_MOVE:

                if (Math.abs(lastScrollY - this.getScrollY()) > 10) {
                    long nowTime = DateUtil.getDateline();
                    if (time == 0 || nowTime - time > 300) {
                        if (lastScrollY > this.getScrollY())//向上滚动
                        {
                            if (mScrollInterface != null)
                                mScrollInterface.onScrollToBottom(false);
                        } else//向下拉
                        {
                            if (mScrollInterface != null)
                                mScrollInterface.onScrollToBottom(true);
                        }


                        lastScrollY = this.getScrollY();
                        time = (int) nowTime;
                    }
                }


            case MotionEvent.ACTION_UP:
                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        super.onScrollChanged(l, t, oldl, oldt);

        if (mScrollInterface != null)
            mScrollInterface.onSChanged(l, t, oldl, oldt);

    }

    public void setOnCustomScroolChangeListener(ScrollInterface scrollInterface) {

        this.mScrollInterface = scrollInterface;

    }

    public interface ScrollInterface {

        public void onScrollToBottom(boolean flag);

        public void onSChanged(int l, int t, int oldl, int oldt);

    }

}
