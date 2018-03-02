package com.ideal.flyerteacafes.utils.tools;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;

import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Utils;

import java.io.File;

public class ApplicationTools {

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 根据名称获取资源id
     *
     * @param context
     * @param name
     * @return
     */
    public static int getIdByName(Context context, String name) {
        int id = context.getResources().getIdentifier(name, "drawable",
                context.getPackageName());
        return id;
    }

    /**
     * 根据名称获取资源id
     *
     * @param context
     * @param name
     * @param type
     * @param packageName
     * @return
     */
//    public static int getResourceId(Context context, String name, String type,
//                                    String packageName) {
//
//        Resources themeResources = null;
//        PackageManager pm = context.getPackageManager();
//        try {
//            themeResources = pm.getResourcesForApplication(packageName);
//            return themeResources.getIdentifier(name, type, packageName);
//        } catch (NameNotFoundException e) {
//
//            e.printStackTrace();
//        }
//        return 0;
//    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDPath() {
        File sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        return sdDir.toString();

    }

    /**
     * 判断SD卡是否存在
     *
     * @return
     */
    public static boolean sdCardExist() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        return sdCardExist;
    }

    /**
     * 获取当前进程名称
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
