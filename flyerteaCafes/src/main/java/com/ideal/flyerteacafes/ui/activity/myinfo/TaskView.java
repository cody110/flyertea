package com.ideal.flyerteacafes.ui.activity.myinfo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.ideal.flyerteacafes.utils.LogFly;

/**
 * Created by fly on 2017/5/18.
 */

public class TaskView extends LinearLayout {

    private IIsChildScollTop iIsChildScollTop;

    public interface IIsChildScollTop {

        /**
         * 子view是否滚动到顶部
         *
         * @return
         */
        boolean isChildScollTop();

        /**
         * 返回隐藏了的高度
         *
         * @param heitedHeight
         */
        void hintedHeight(int heitedHeight);
    }

    public void setIIsChildScollTop(IIsChildScollTop i) {
        iIsChildScollTop = i;
    }

    private View mHeaderView;
    //header 高度
    private int mHeaderHeight;
    //滚动到顶部，显示header高度，body 距离 top 停止距离
    private int mShowHeight = 0;
    //隐藏header 高度= mHeaderHeight - mShowHeight
    private int mHintHeight;

    //速度追踪
    private VelocityTracker velocityTracker = VelocityTracker.obtain();
    private Scroller scroller = new Scroller(getContext());

    /**
     * last y
     */
    private int mLastMotionY;

    public TaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addHeaderView(View headerView) {
        this.mHeaderView = headerView;
        addView(headerView, 0);
        mHeaderView.post(new Runnable() {
            @Override
            public void run() {
                mHeaderHeight = mHeaderView.getHeight();
                mHintHeight = mHeaderHeight - mShowHeight;
            }
        });
    }

    public void addHeaderView(View headerView, ViewGroup.LayoutParams params) {
        this.mHeaderView = headerView;
        addView(headerView, 0, params);
        mHeaderView.post(new Runnable() {
            @Override
            public void run() {
                mHeaderHeight = mHeaderView.getHeight();
                mHintHeight = mHeaderHeight - mShowHeight;
            }
        });

    }

    public void setmShowHeight(int height) {
        mShowHeight = height;
    }


    private boolean isRefreshViewScroll() {
        if (mHeaderView == null) return false;


        return true;
    }

    int d_x = 0, d_y = 0, m_x, m_y, dis_x, dis_y;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int getx = (int) e.getX();
        int gety = (int) e.getY();

        int y = (int) e.getRawY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                d_x = getx;
                d_y = gety;
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                if (mHeaderView == null) return false;

                if (iIsChildScollTop == null) return false;

                if (!iIsChildScollTop.isChildScollTop()) return false;

                m_x = getx;
                m_y = gety;
                dis_x = d_x - m_x;
                dis_y = d_y - m_y;

                //纵向大于横向拦截
                if (Math.abs(dis_x) < Math.abs(dis_y)) {
                    // deltaY > 0 是向下运动,< 0是向上运动
                    int deltaY = y - mLastMotionY;

                    //getScaledTouchSlop 被认为滑动的最小距离
                    if (Math.abs(deltaY) < ViewConfiguration.get(getContext()).getScaledTouchSlop())
                        return false;

                    if (deltaY > 0) {
                        if (getHeaderTopMargin() < 0) {
                            return true;
                        }
                    }
                    if (deltaY < 0) {
                        if (getHeaderTopMargin() > -mHintHeight) {
                            return true;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                m_x = getx;
                m_y = gety;
                dis_x = d_x - m_x;
                dis_y = d_y - m_y;
                break;
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        velocityTracker.addMovement(event);

        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // onInterceptTouchEvent已经记录
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastMotionY;
                changingHeaderViewTopMargin(deltaY);
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                velocityTracker.computeCurrentVelocity(500);
                //单位时间滚动的距离
                int xVelocity = (int) velocityTracker.getXVelocity();
                int yVelocity = (int) velocityTracker.getYVelocity();

                LogFly.e("yVelocity:" + yVelocity);

                //当前距离，超过一半，继续滚到底，未超过一半回滚
//                int marginTop = getHeaderTopMargin();
//                if (Math.abs(marginTop) > mHintHeight / 2) {
//                    smoothScrollTo( Math.abs(marginTop) - mHintHeight,1000);
//                } else {
//                    smoothScrollTo( Math.abs(marginTop)，1000);
//                }

                smoothScrollTo(yVelocity, 1000);

                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
//            scrollTo(scroller.getCurrX(), scroller.getCurrY());
//            postInvalidate();
            changingHeaderViewTopMargin(scroller.getCurrY());

        }
    }

    //缓慢滚动到指定位置
//    private void smoothScrollTo(int destX, int destY) {
//        int scrollY = getScrollY();
//        int delta = destY - scrollY;
//
//        //1000ms内滑向的身体，效果是慢慢滑动
//        scroller.startScroll(0, scrollY, 0, delta, 1000);
//    }


    /**
     * 单位时间滚动距离
     * destY 滚动距离
     * time 滚动时间
     */
    public void smoothScrollTo(final int destY, int time) {
        final LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        final int topMargin = params.topMargin;
        final ValueAnimator animator = ValueAnimator.ofFloat(0, 1).setDuration(time);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = animator.getAnimatedFraction();//TODO 当前进度0-1
                float nowDis = destY * fraction;

                float newTopMargin = topMargin + nowDis;


                if (newTopMargin > 0) {
                    if (params.topMargin == 0) {
                        if (iIsChildScollTop != null) iIsChildScollTop.hintedHeight(0);
                        return;
                    } else {
                        newTopMargin = 0;
                    }

                }
                if (newTopMargin < -mHintHeight) {

                    if (params.topMargin == -mHintHeight) {
                        if (iIsChildScollTop != null) iIsChildScollTop.hintedHeight(mHintHeight);
                        return;
                    } else {
                        newTopMargin = -mHintHeight;
                    }
                }

                params.topMargin = (int) newTopMargin;
                mHeaderView.setLayoutParams(params);
                invalidate();
                if (iIsChildScollTop != null) iIsChildScollTop.hintedHeight(-params.topMargin);
            }
        });
        animator.start();
    }


    /**
     * 修改Header view top margin的值
     *
     * @param deltaY
     * @return hylin 2012-7-31下午1:14:31
     * @description
     */
    private int changingHeaderViewTopMargin(int deltaY) {

        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        float newTopMargin = params.topMargin + deltaY;
        if (newTopMargin > 0) {
            if (params.topMargin == 0) {
                if (iIsChildScollTop != null) iIsChildScollTop.hintedHeight(0);
                return 0;
            } else {
                newTopMargin = 0;
            }

        }
        if (newTopMargin < -mHintHeight) {

            if (params.topMargin == -mHintHeight) {
                if (iIsChildScollTop != null) iIsChildScollTop.hintedHeight(mHintHeight);
                return -mHintHeight;
            } else {
                newTopMargin = -mHintHeight;
            }
        }

        params.topMargin = (int) newTopMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
        if (iIsChildScollTop != null) iIsChildScollTop.hintedHeight(-params.topMargin);
        return params.topMargin;

    }

    /**
     * 获取当前header view 的topMargin
     *
     * @return hylin 2012-7-31上午11:22:50
     * @description
     */
    private int getHeaderTopMargin() {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        return params.topMargin;
    }


}
