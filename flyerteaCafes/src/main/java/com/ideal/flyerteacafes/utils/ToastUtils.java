package com.ideal.flyerteacafes.utils;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.ideal.flyerteacafes.caff.FlyerApplication;

public class ToastUtils {

    private static Toast mToast;


    public static void showToast(final String text) {

        if (TaskUtil.isOnUiThread()) {
            createToast(text);
        } else {
            TaskUtil.postOnUiThread(new Runnable() {
                @Override
                public void run() {
                    createToast(text);
                }
            });
        }
    }

    private static void createToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(FlyerApplication.getContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        if (!TextUtils.isEmpty(text))
            mToast.show();
    }


    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    /**
     * 设置Toast显示位置
     *
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void setGravityToast(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }
}
