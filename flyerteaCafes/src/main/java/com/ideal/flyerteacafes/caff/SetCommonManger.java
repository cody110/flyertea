package com.ideal.flyerteacafes.caff;

/**
 * Created by fly on 2016/12/21.
 * 通用设置管理类
 */

public class SetCommonManger {


    static SharedPreferencesString preferences = SharedPreferencesString.getInstances();
    private static final String KEY_PUSHMODE = "pushMode222", KEY_PICTUREMODE = "pictureMode222", KEY_THREADSTREAMLINEMODE = "threadStreamlineMode222", KEY_NEARBY_MODE = "nearMode222", KEY_FLOW_MODE = "settingFlowMode";


    /**
     * 默认设置
     * 消息推送 开
     * 无图模式 关
     * 帖子精简模式 关
     * 对附近的可见 开
     */
    public static final void defaultSetting() {
        //不存在该键时，执行初始化
        if (!preferences.getSharedPreferences().contains(KEY_PUSHMODE)) {
            openPushMode();
            closeNoGraphMode();
            closeThreadStreamlineMode();
            openNearbyMode();
        }
    }


    /**
     * 是否开启了推送
     *
     * @return
     */
    public static boolean isPushMode() {
        return preferences.getBooleanToKey(KEY_PUSHMODE);
    }

    public static void openPushMode() {
        preferences.savaToBoolean(KEY_PUSHMODE, true);
        preferences.commit();
    }

    public static void closePushMode() {
        preferences.savaToBoolean(KEY_PUSHMODE, false);
        preferences.commit();
    }

    /**
     * 是否开启无图模式
     * 只限帖子正文
     *
     * @return
     */
    public static boolean isNoGraphMode() {
        return preferences.getBooleanToKey(KEY_PICTUREMODE);
    }

    public static void openNoGraphMode() {
        preferences.savaToBoolean(KEY_PICTUREMODE, true);
        preferences.commit();
    }

    public static void closeNoGraphMode() {
        preferences.savaToBoolean(KEY_PICTUREMODE, false);
        preferences.commit();
    }

    /**
     * 是否开启帖子精简模式
     * 帖子列表将不显示图片正文和评论
     *
     * @return
     */
    public static boolean isThreadStreamlineMode() {
        return preferences.getBooleanToKey(KEY_THREADSTREAMLINEMODE);
    }

    public static void openThreadStreamlineMode() {
        preferences.savaToBoolean(KEY_THREADSTREAMLINEMODE, true);
        preferences.commit();
    }

    public static void closeThreadStreamlineMode() {
        preferences.savaToBoolean(KEY_THREADSTREAMLINEMODE, false);
        preferences.commit();
    }

    /**
     * 是否附近可见
     *
     * @return
     */
    public static boolean isNearbyMode() {
        return preferences.getBooleanToKey(KEY_NEARBY_MODE);
    }

    public static void openNearbyMode() {
        preferences.savaToBoolean(KEY_NEARBY_MODE, true);
        preferences.commit();
    }

    public static void closeNearbyMode() {
        preferences.savaToBoolean(KEY_NEARBY_MODE, false);
        preferences.commit();
    }


    /**
     * 是否省流模式
     *
     * @return
     */
    public static boolean isFlowbyMode() {
        return preferences.getBooleanToKey(KEY_FLOW_MODE, true);
    }

    public static void openFlowbyMode() {
        preferences.savaToBoolean(KEY_FLOW_MODE, true);
        preferences.commit();
    }

    public static void closeFlowbyMode() {
        preferences.savaToBoolean(KEY_FLOW_MODE, false);
        preferences.commit();
    }

}
