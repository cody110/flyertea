package com.ideal.flyerteacafes.ui.controls;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.utils.LogFly;

public class PullToZoomListView extends ListView implements
        AbsListView.OnScrollListener {
    private static final int INVALID_VALUE = -1;
    private static final String TAG = "PullToZoomListView";
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float paramAnonymousFloat) {
            float f = paramAnonymousFloat - 1.0F;
            return 1.0F + f * (f * (f * (f * f)));
        }
    };
    int mActivePointerId = -1;
    private View mHeaderContainer;
    private int mHeaderHeight;
    public ImageView mHeaderImage;
    float mLastMotionY = -1.0F;
    float mLastScale = -1.0F;
    float mMaxScale = -1.0F;
    private OnScrollListener mOnScrollListener;
    private ScalingRunnalable mScalingRunnalable;
    private float mScreenHeight;

    /*下拉刷新的阈值*/
    private final float REFRESH_SCALE = 1.20F;

    public PullToZoomListView(Context paramContext) {
        super(paramContext);
        init(paramContext);
    }

    public PullToZoomListView(Context paramContext,
                              AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init(paramContext);
    }

    public PullToZoomListView(Context paramContext,
                              AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init(paramContext);
    }

    private void endScraling() {
        if (this.mHeaderContainer.getBottom() >= this.mHeaderHeight)
        this.mScalingRunnalable.startAnimation(200L);
    }

    private void init(Context paramContext) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((Activity) paramContext).getWindowManager().getDefaultDisplay()
                .getMetrics(localDisplayMetrics);
        this.mScreenHeight = localDisplayMetrics.heightPixels;
        this.mHeaderContainer = LayoutInflater.from(getContext()).inflate(R.layout.fragment_user_datum_header, this, false);
        this.mHeaderImage = (ImageView) mHeaderContainer.findViewById(R.id.image);
        int i = localDisplayMetrics.widthPixels;
//        setHeaderViewSize(i, (int) (9.0F * (i / 16.0F)));
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(
                -1, -2);
        localLayoutParams.gravity = 80;
        addHeaderView(this.mHeaderContainer);
        this.mScalingRunnalable = new ScalingRunnalable();
        super.setOnScrollListener(this);
    }


    public void resetHeaderHeight() {
        this.mHeaderHeight = this.mHeaderContainer.getHeight();
    }


    private void onSecondaryPointerUp(MotionEvent event) {
        int i = (event.getAction()) >> 8;
        if (event.getPointerId(i) == this.mActivePointerId)
            if (i != 0) {
                int j = 1;
                this.mLastMotionY = event.getY(0);
                this.mActivePointerId = event.getPointerId(0);
                return;
            }
    }

    private void reset() {
        this.mActivePointerId = -1;
        this.mLastMotionY = -1.0F;
        this.mMaxScale = -1.0F;
        this.mLastScale = -1.0F;
    }

    public View getHeaderView() {
        return this.mHeaderContainer;
    }


    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2,
                            int paramInt3, int paramInt4) {
        super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
        if (this.mHeaderHeight == 0)
            this.mHeaderHeight = this.mHeaderContainer.getHeight();
    }

    @Override
    public void onScroll(AbsListView paramAbsListView, int paramInt1,
                         int paramInt2, int paramInt3) {
        float f = this.mHeaderHeight - this.mHeaderContainer.getBottom();
        if ((f > 0.0F) && (f < this.mHeaderHeight)) {
            int i = (int) (0.65D * f);
//            this.mHeaderImage.scrollTo(0, -i);
        } else if (this.mHeaderImage.getScrollY() != 0) {
            this.mHeaderImage.scrollTo(0, 0);
        }
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(paramAbsListView, paramInt1,
                    paramInt2, paramInt3);
        }
    }

    public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {
        if (this.mOnScrollListener != null)
            this.mOnScrollListener.onScrollStateChanged(paramAbsListView,
                    paramInt);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (this.mHeaderHeight == 0) return false;
                if (!this.mScalingRunnalable.mIsFinished) {
                    this.mScalingRunnalable.abortAnimation();
                }
                this.mLastMotionY = event.getY();
                this.mActivePointerId = event.getPointerId(0);
                this.mMaxScale = (this.mScreenHeight / this.mHeaderHeight);
                this.mLastScale = (this.mHeaderContainer.getBottom() / this.mHeaderHeight);
                break;
            case MotionEvent.ACTION_MOVE:
                int j = event.findPointerIndex(this.mActivePointerId);
                if (j == -1) {
                    LogFly.e("Invalid pointerId="
                            + this.mActivePointerId + " in onTouchEvent");
                } else {
                    if (this.mLastMotionY == -1.0F)
                        this.mLastMotionY = event.getY(j);
                    if (this.mHeaderContainer.getBottom() >= this.mHeaderHeight) {
                        ViewGroup.LayoutParams localLayoutParams = this.mHeaderContainer
                                .getLayoutParams();
                        float f = ((event.getY(j) - this.mLastMotionY + this.mHeaderContainer
                                .getBottom()) / this.mHeaderHeight - this.mLastScale)
                                / 2.0F + this.mLastScale;


                        if ((this.mLastScale <= 1.0D) && (f < this.mLastScale)) {
                            localLayoutParams.height = this.mHeaderHeight;
                            this.mHeaderContainer
                                    .setLayoutParams(localLayoutParams);
                            return super.onTouchEvent(event);
                        }
                        this.mLastScale = Math.min(Math.max(f, 1.0F),
                                this.mMaxScale);
                        localLayoutParams.height = ((int) (this.mHeaderHeight * this.mLastScale));
                        if (localLayoutParams.height < this.mScreenHeight)
                            this.mHeaderContainer
                                    .setLayoutParams(localLayoutParams);
                        this.mLastMotionY = event.getY(j);
                        return true;
                    }
                    this.mLastMotionY = event.getY(j);
                }
                break;
            case MotionEvent.ACTION_UP:
                boolean isItemClick = this.mLastScale <= 1;

                if (this.mLastScale > REFRESH_SCALE) {
                    if (mRefreshListener != null)
                        mRefreshListener.onRefresh();
                }
                reset();
                endScraling();

                if (isItemClick) {
                    return super.onTouchEvent(event);
                } else {
                    return true;
                }

            case MotionEvent.ACTION_CANCEL:
                int i = event.getActionIndex();
                this.mLastMotionY = event.getY(i);
                this.mActivePointerId = event.getPointerId(i);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                onSecondaryPointerUp(event);
                this.mLastMotionY = event.getY(event
                        .findPointerIndex(this.mActivePointerId));
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setHeaderViewSize(int paramInt1, int paramInt2) {
        Object localObject = this.mHeaderContainer.getLayoutParams();
        if (localObject == null)
            localObject = new LayoutParams(paramInt1, paramInt2);
        ((ViewGroup.LayoutParams) localObject).width = paramInt1;
        ((ViewGroup.LayoutParams) localObject).height = paramInt2;
        this.mHeaderContainer
                .setLayoutParams((ViewGroup.LayoutParams) localObject);
        this.mHeaderHeight = paramInt2;
    }

    public void setOnScrollListener(
            OnScrollListener paramOnScrollListener) {
        this.mOnScrollListener = paramOnScrollListener;
    }

    class ScalingRunnalable implements Runnable {
        long mDuration;
        boolean mIsFinished = true;
        float mScale;
        long mStartTime;

        ScalingRunnalable() {
        }

        public void abortAnimation() {
            this.mIsFinished = true;
        }

        public boolean isFinished() {
            return this.mIsFinished;
        }

        public void run() {
            float f2;
            ViewGroup.LayoutParams localLayoutParams;
            if ((!this.mIsFinished) && (this.mScale > 1.0D)) {
                float f1 = ((float) SystemClock.currentThreadTimeMillis() - (float) this.mStartTime)
                        / (float) this.mDuration;
                f2 = this.mScale - (this.mScale - 1.0F)
                        * PullToZoomListView.sInterpolator.getInterpolation(f1);
                localLayoutParams = PullToZoomListView.this.mHeaderContainer
                        .getLayoutParams();
                if (f2 > 1.0F) {
                    localLayoutParams.height = PullToZoomListView.this.mHeaderHeight;
                    ;
                    localLayoutParams.height = ((int) (f2 * PullToZoomListView.this.mHeaderHeight));
                    PullToZoomListView.this.mHeaderContainer
                            .setLayoutParams(localLayoutParams);
                    PullToZoomListView.this.post(this);
                    return;
                }
                this.mIsFinished = true;
            }
        }

        public void startAnimation(long paramLong) {
            this.mStartTime = SystemClock.currentThreadTimeMillis();
            this.mDuration = paramLong;
            this.mScale = ((float) (PullToZoomListView.this.mHeaderContainer
                    .getBottom()) / PullToZoomListView.this.mHeaderHeight);
            this.mIsFinished = false;
            PullToZoomListView.this.post(this);
        }
    }

    /*下拉刷新监听*/
    private OnRefreshListener mRefreshListener;

    public interface OnRefreshListener {
        void onRefresh();
    }

    public void setmRefreshListener(OnRefreshListener listener) {
        mRefreshListener = listener;
    }
}
