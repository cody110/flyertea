package com.ideal.flyerteacafes.ui.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 回复帖子
 *
 * @author fly
 */
public class RPLinearLayout extends LinearLayout {

    private OnResizeListener mListener;

    public interface OnResizeListener {
        void OnResize(int w, int h, int oldw, int oldh);
    }

    public void setOnResizeListener(OnResizeListener l) {
        mListener = l;
    }

    public RPLinearLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public RPLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public RPLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mListener != null) {
            mListener.OnResize(w, h, oldw, oldh);
        }
    }

}
