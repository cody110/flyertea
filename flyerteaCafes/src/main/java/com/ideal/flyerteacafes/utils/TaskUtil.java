package com.ideal.flyerteacafes.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;

/**
 * Created by fly on 2017/5/25.
 */

public class TaskUtil {

    private static Handler sMainHandler;
    private static Handler sBackgroundHandler;

    public static void postOnUiThread(Runnable runnable) {
        __ensureMainHandler();
        sMainHandler.post(runnable);
    }

    public static void postOnUiThreadDelayed(Runnable runnable, long delayMillis) {
        __ensureMainHandler();
        sMainHandler.postDelayed(runnable, delayMillis);
    }

    public static void postOnBackgroundThread(Runnable runnable) {
        __ensureBackgroundHandler();
        sBackgroundHandler.post(runnable);
    }

    public static void postOnBackgroundThreadDelayed(Runnable runnable, long delayMillis) {
        __ensureBackgroundHandler();
        sBackgroundHandler.postDelayed(runnable, delayMillis);
    }

    private static void __ensureMainHandler() {
        if (sMainHandler == null) {
            sMainHandler = new Handler(Looper.getMainLooper());
        }
    }

    private static void __ensureBackgroundHandler() {
        if (sBackgroundHandler == null) {
            HandlerThread handlerThread = new HandlerThread("taskutil-background", Process.THREAD_PRIORITY_BACKGROUND);
            handlerThread.start();
            sBackgroundHandler = new Handler(handlerThread.getLooper());
        }
    }

    public static boolean isOnUiThread() {
        return TextUtils.equals(Thread.currentThread().getName(), "main");
    }

}
