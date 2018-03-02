package com.ideal.flyerteacafes.ui.controls;

import com.ideal.flyerteacafes.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class BackLinearLayout extends LinearLayout {
    private Context context;

    public BackLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public BackLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public BackLinearLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.setBackgroundColor(context.getResources().getColor(
                    R.color.grey));
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            this.setBackgroundColor(context.getResources().getColor(
                    R.color.app_bg_title));
        }

        return super.onTouchEvent(event);
    }

}
