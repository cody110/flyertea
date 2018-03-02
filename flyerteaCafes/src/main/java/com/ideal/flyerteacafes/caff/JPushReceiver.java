package com.ideal.flyerteacafes.caff;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ideal.flyerteacafes.ui.activity.RemindActivity;
import com.ideal.flyerteacafes.ui.activity.SplashActivity;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.ui.activity.messagecenter.MessageCenterActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ArticleContentActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.tools.IntentTools;
import com.ideal.flyerteacafes.utils.tools.Tools;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

/**
 * 自定义接收器
 * <p/>
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    public static final String app_upgrade = "app_upgrade";// 升级
    public static final String PM = "PM";// 私信
    public static final String announcement = "announcement";// 公告
    public static final String remind = "remind";// 提醒
    public static final String notice = "notice";// 展示人工设置的通知文字
    public static final String AD = "AD";// 广告
    public static final String thread = "thread";// 帖子
    public static final String newsInfo = "newsInfo";// 飞客知道
    public static final String collection = "collection";// 话题

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        // LogFly.d(TAG, "[JPushReceiver] onReceive - " + intent.getAction()
        // + ", extras: " + printBundle(bundle));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle
                    .getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[JPushReceiver] 接收Registration Id : " + regId);
            // send the Registration Id to your server...
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if (extras == null)
                return;
            // LogFly.i(TAG, "extras:" + extras);
            String type = JPushJsonUtils.jsonToType(extras);

            Log.d(TAG, extras + type);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                .getAction())) {
            Log.d(TAG,
                    "[JPushReceiver] 接收到推送下来的自定义消息: "
                            + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction())) {
            EventBus.getDefault().post(FinalUtils.GETNUM);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {
            Log.d(TAG, "[JPushReceiver] 用户点击打开了通知");
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.i(TAG, "extras:" + extras);
            String type = JPushJsonUtils.jsonToType(extras);
            String value = JPushJsonUtils.jsonToValue(extras, "url");
            String key = "key";

            if (type.equals(app_upgrade)) {
                if (IntentTools.isAppInstalled(context, "com.tencent.android.qqdownloader")) {
                    IntentTools.toTencentDownloaderAPP(context);
                } else {
                    IntentTools.openAppStore(context);
                }
            } else if (type.equals(PM)) {// 私信
                intentActivity(context, type, key, value,
                        MessageCenterActivity.class);
            } else if (type.equals(AD)) {// 广告
                key = "url";
                intentActivity(context, type, key, value, TbsWebActivity.class);
            } else if (type.equals(thread)) {// 帖子
                key = "tid";
                intentActivity(context, type, key, value,
                        ThreadActivity.class);
            } else if (type.equals(newsInfo)) {// 飞客知道
                key = "aid";
                intentActivity(context, type, key, value,
                        ArticleContentActivity.class);
            } else if (type.equals(announcement)) {
                intentActivity(context, type, key, value, RemindActivity.class);
            } else if (type.equals(remind)) {
                intentActivity(context, type, key, value, RemindActivity.class);
            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
                .getAction())) {
            Log.d(TAG,
                    "[JPushReceiver] 用户收到到RICH PUSH CALLBACK: "
                            + bundle.getString(JPushInterface.EXTRA_EXTRA));
            // 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
            // 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
                .getAction())) {
            boolean connected = intent.getBooleanExtra(
                    JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[JPushReceiver]" + intent.getAction()
                    + " connected state change to " + connected);
        } else {
            Log.d(TAG,
                    "[JPushReceiver] Unhandled intent - " + intent.getAction());
        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    /**
     * 点击通知跳转的Activity
     *
     * @param context
     * @param type     类型
     * @param value    传递的值
     * @param activity
     */
    @SuppressWarnings("unused")
    private static <T> void intentActivity(Context context, String type,
                                           String key, String value, Class<T> activity) {
        Intent intent = new Intent();
        intent.putExtra("type", type);
        if (Tools.isBackground(context)) {
            intent.setClass(context, SplashActivity.class);
            intent.putExtra("key", value);
        } else {
            intent.setClass(context, activity);
            intent.putExtra(key, value);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
