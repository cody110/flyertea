package com.ideal.flyerteacafes.utils;

/**
 * Created by fly on 2016/8/30.
 * 接口返回数据  类型定义 标识
 */
public class Marks {

    /**
     * 直播视频
     */
    public static final int FEED_SHIPIN = 1;
    /**
     * 直播图片文字
     */
    public static final int FEED_TU_TEXT = 0;

    /**
     * 社区首页：已认证
     */
    public static final String COMMUNITY_HAS_SM = "2";

    /**
     * 社区首页性别：0：未知，1：男，2：女
     */
    public static final String COMMUNITY_GENDER_UNKNOWN = "0", COMMUNITY_GENDER_BOY = "1", COMMUNITY_GENDER_GIRL = "2";

    /**
     * 个人主页：实名认证  0 没有，1认证中，2认证成功
     */
    public static final String USERINFO_HAS_SM = "2";

    /**
     * 帖子 引用回复
     */
    public static final String THREAD_COMMENT_TO_COMMENT = "1";

    /**
     * 是否送过花
     */
    public static final String THREAD_SEND_FLOWER_ED = "1";

    /**
     * 帖子列表广告
     */
    public static final String THREAD_LIST_TYPE_ADVER = "2";

    /**
     * 帖子列表广告下的话题
     */
    public static final String THREAD_LIST_TYPE_TOPIC = "collectionid";


    /**
     * 未读消息 1
     * 已读 0
     */
    public static final String MESSAGE_UNREAD = "1", MESSAGE_READ = "0";


    /**
     * 标记为已读，分类
     * key=marktype
     */
    public static class MarkType {
        public static final String MARKTYPE = "marktype";//key
        public static final String REWARD = "reward";//帖子成就
        public static final String SYSTEM = "system";//系统
        public static final String RECOMMEND = "recommend";//好文推荐
        public static final String ACTIVITY = "activity";//活动精选
        public static final String FLOWER = "flower";//送我鲜花
    }


    /**
     * 搜索接口类型
     * type： user—用户  thread— 帖子 report—报告 strategy— 攻略  promotion— 优惠
     */
    public static class HttpSearchType {
        public static final String TYPE_KEY = "type";
        public static final String TYPE_THREAD = "thread";
        public static final String TYPE_REPORT = "report";
        public static final String TYPE_STRATEGY = "strategy";
        public static final String TYPE_PROMOTION = "promotion";
        public static final String TYPE_USER = "user";
    }


    /**
     * 热词 类型  relatedtype
     */
    public static class HotWordType {
        //relatedtype：0 无类型   1  帖子  2  h5  3 文章
        public static final String normal = "0";
        public static final String thread = "thread";
        public static final String h5 = "h5";
        public static final String report = "article";
    }


}
