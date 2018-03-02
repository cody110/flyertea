package com.ideal.flyerteacafes.utils;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Created by young on 2016/1/20.
 */
public class TvDrawbleUtils {

    public static final int LEFT = 0, TOP = 1, RIGHT = 2, BOTTOM = 3;

    //更换控件的Drawble
    public static void chageDrawble(TextView tv, int pic) {
        setTextDrawble(tv, pic, LEFT);
    }

    public static void setTextDrawbleRight(TextView tv, int pic) {
        setTextDrawble(tv, pic, RIGHT);
    }

    /**
     * 设置textview drawable
     *
     * @param tv
     * @param picRes
     * @param location
     */
    public static void setTextDrawble(TextView tv, int picRes, int location) {
        Drawable drawable = tv.getContext().getResources().getDrawable(picRes);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        switch (location) {
            case LEFT:
                tv.setCompoundDrawables(drawable, null, null, null);
                break;
            case TOP:
                tv.setCompoundDrawables(null, drawable, null, null);
                break;
            case RIGHT:
                tv.setCompoundDrawables(null, null, drawable, null);
                break;
            case BOTTOM:
                tv.setCompoundDrawables(null, null, null, drawable);
                break;
        }

    }
}
