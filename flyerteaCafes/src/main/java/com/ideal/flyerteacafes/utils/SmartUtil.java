package com.ideal.flyerteacafes.utils;

import android.content.Context;

public class SmartUtil {


    private static long lastClickTime;
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    public static void BToast(Context context, String message) {
        ToastUtils.showToast(message);
    }

    public static void BToast( String message) {
        ToastUtils.showToast(message);
    }

    /**
     * 检测快读点击,现在判断是两次点击 间隔小于500毫秒
     *
     * @param view
     * @return true如果间隔很短;
     */
//    public static boolean checkQuickClick(View view) {
//
//        Object obj = view.getTag(-1);
//        long current = System.currentTimeMillis();
//        if (obj != null && (obj instanceof Long)) {
//            long last = (Long) obj;
//            long distance = current - last;
//            if (distance < 500) {
//                return true;
//            } else {
//                view.setTag(-1, current);
//            }
//        } else {
//            view.setTag(-1, current);
//        }
//        return false;
//    }

}
