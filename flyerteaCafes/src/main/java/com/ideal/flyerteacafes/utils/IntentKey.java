package com.ideal.flyerteacafes.utils;

/**
 * Created by fly on 2016/6/8.
 */
public class IntentKey {



    /**
     * 跳转key
     */
    public static final String KEY_FROM = "from";

    /**
     * 从问答去写帖子
     */
    public static final String FROM_InterlocutionActivity = "InterlocutionActivity";

    /**
     * 从写帖页面去选择
     */
    public static final String FROM_ReplyPostsActivity = "ReplyPostsActivity";


    /**
     * 首页fragment 热门互动
     */
    public static final String FROM_HOMEFRAGMENT_INTERLOCUTION = "homefragment_interlocution";

    /**
     * 再一次—TO
     */
    public static final String KEY_AGAIN = "again";

    public static final int ZHIBO_PAIZHAO = 1, ZHIBO_XIANGCE = 2, ZHIBO_SHIPIN = 3;

    /**
     * 广告
     */
    public static final String TYPE_ADVERT = "advert";

    /**
     * 趣拍token
     */
    public static final String QUPAI_TOKEN = "accessToken";

    /**
     * 视频
     */
    public static final String VIDEO_URL = "videourl";


    /**
     * 应用从后台到前台，显示只显示广告，不需要数据初始化
     */
    public static final String TYPE_ISACTIVE_SHOW_ADV = "TYPE_ISACTIVE_SHOW_ADV";

    /**
     * fid
     */
    public static final String BUNDLE_FID_KEY = "fid";


    /**
     * 日期选择
     */
    public static final class DataChoose {

        public static final String START_TIME = "checkindate";
        public static final String END_TIME = "checkoutdate";

    }

    /**
     * 帖子搜索
     */
    public static final class ThreadSearch {
        public static final String BUNDLE_KEY = "type_name", TYPE_THREAD = "thread", TYPE_REPORT = "report", TYPE_RAIDERS = "strategy";
    }
}
