package com.ideal.flyerteacafes.utils.tools;

import java.util.List;

import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.utils.Utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class Tools {

    /**
     * 2 * 获取版本号 3 * @return 当前应用的版本号 4
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getVersion() {
        return getVersion(FlyerApplication.getContext());
    }

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
     * 得到当前运行的是哪一个activity
     *
     * @return
     */
    public static String getRunningActivityName(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(context.ACTIVITY_SERVICE);
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity
                .getClassName();
        return runningActivity;
    }

    /**
     * 判断activity是否在进程中
     *
     * @return
     */
    public static boolean isRunningProcess(Context context, String activityName) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> infoList = activityManager.getRunningTasks(1);
        for (RunningTaskInfo runningTaskInfo : infoList) {
            String runningActivity = runningTaskInfo.topActivity
                    .getClassName();
            if (runningActivity.equals(Utils.PACKAGE_NAME + activityName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断应用是否后台运行
     *
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

}
