package com.ideal.flyerteacafes.utils.tools;

import android.app.Activity;
import android.content.Context;

import com.ideal.flyerteacafes.caff.FlyerApplication;

/**
 * Created by fly on 2016/2/24.
 */
public class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px( float dpValue) {
       return dip2px(FlyerApplication.getContext(),dpValue);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip( float pxValue) {
        return px2dip(FlyerApplication.getContext(),pxValue);
    }

    /**
     * 获取屏幕高度
     *
     * @param activity
     * @return
     */
    public static int getHeight(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getHeight();
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity
     * @return
     */
    public static int getWidth(Activity activity){
        return activity.getWindowManager().getDefaultDisplay().getWidth();
    }

}
