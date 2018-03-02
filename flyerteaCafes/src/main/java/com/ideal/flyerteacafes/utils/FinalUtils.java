package com.ideal.flyerteacafes.utils;

/**
 * 程序中用到的常量
 *
 * @author fly
 */
public class FinalUtils {


    //騰訊appid
    public static final String AD_TENCENT_APPID = "1106633813";
    //开屏广告id
    public static final String AD_TENCENT_OPENING_SCREEN_ID = "3030236021440237";

    //应用md5签名
    public static final String FLYERTEA_MD5_SIGNATURE = "681baba4c9979fd0331afeba58f18f16";
    public static final String REGIST_TYPE = "regist_type";//注册类型  key
    public static final int REGIST_PHONE = 1;//手机注册
    public static final int REGIST_THRID = 2;//第三方注册

    //第三方登录所需的key
    //微信
/*    AppID：wxe3c42a32fb7853fa
    AppSecret：4dde1895fdd5e666715942f064626d51*/
    public static final String WECHATID = "wxe3c42a32fb7853fa";
    public static final String WECHATSECRET = "4dde1895fdd5e666715942f064626d51";
    //QQ
    /**
     * APP ID：	100424468
     * APP KEY：	c7394704798a158208a74ab60104f0ba
     **/
    public static final String QQID = "1104775592";
    public static final String QQKEY = "rrdiIR6IBR7OOqqB";

    //Sina
    /**
     * App Key：	117568512
     * App Secret：	c52f467c7879ccc480f169f23942659a
     **/
    public static final String SINAID = "117568512";
    public static final String SINAKEY = "c52f467c7879ccc480f169f23942659a";

    /**
     * 微信包名
     */
    public static final String WECHART_PACKAGE_NAME = "com.tencent.mm";

    /**
     * QQ包名
     */
    public static final String QQ_PACKAGE_NAME = "com.tencent.mobileqq";

    //距离顶部距离
    public static final int FROM_TOP = 1350;

    //跳转到主页面传递的值
    public static final String TO_HOME_KEY = "code";//key
    public static final int HOME_LOGIN = 2;//登录
    public static final int HOME_TO_FORUM = 7;//社区首页-版块选择
    public static final int HOME_TASK_BACK = 8;//任务详情，返回，刷新任务数据
    public static final int HOME_TO_DRAFT = 9;//发帖保存草稿，去查看草稿
    public static final int HOME_TO_MYTHREAD = 10;//发帖成功，到我的帖子
    public static final int HOME_GONGLUE = 11;//攻略
    public static final int HOME_READ_POST = 12;//读帖
    public static final int HOME_REPORT = 13;//报告
    public static final int HOME_THREAD_HOT = 14;//读帖热帖
    public static final int HOME_THREAD_DIGEST = 15;//读帖精华
    public static final int HOME_PROMOTION = 16;//优惠


    //listview初始页
    public static final int START_PAGE = 1;
    //每页多少条数据
    public static final int PAGE_SIZE = 20;


    public static final int POST_ISSUE = 0;//发帖
    public static final int POST_COMMENT = 1;//评论回复
    public static final int POST_QUOTE = 2;//引用回复
    public static final int REPLY_POST = 3;//文章回复
    public static final int FEED_BACK = 4;//评论我们


    public static final int POST_DETAILS_REPLY = 0;//引用
    public static final int POST_DETAILS_FLOWER = 1;//鲜花
    public static final int POST_DETAILS_CANLCE = 2;//取消
    public static final int POST_DETAILS_DELETE = 3;//删除

    public static final int ROUGHDRAFT_SAVE = 4;//保存
    public static final int ROUGHDRAFT_NOT_SAVE = 5;//不保存
    public static final int ROUGHDRAFT_CANCEL = 6;//取消

    //是否还有下一页
    public static final int NOT_NEXT = 0;
    public static final int HAS_NEXT = 1;

    public static final String OUTLOGIN = "outLogin";
    public static final String TOLOGIN = "toLogin";
    public static final String ADVERT = "advert";
    public static final String GETNUM = "getnum";
    public static final String ISSIGNIN = "isSignin";
    public static final String SIGNINTRUE = "SigninTrue";
    public static final String UPDATEFACE = "UPDATEFACE";

    /**
     * 跳转返回的code 消息中心:私信
     */
    public static final int REQUEST_CODE_PRIVATE_LETTER = 1;

    /**
     * 跳转返回的code 消息中心：消息
     */
    public static final int REQUEST_CODE_MESSAGE = 2;


    /**
     * 友盟统计 统计的事件ID
     *
     * @author fly
     */
    public static class EventId {
        public static final String youzan = "youzan";//飞客优选点击计数
        public static final String sign_in = "sign_in";//App每天签到数
        public static final String register = "register";//app每天注册数
        public static final String search = "search";//搜索次数
        public static final String share_post = "share_post";//帖子分享
        public static final String share_article = "share_article";//文章分享
        public static final String hotel_reservation_1 = "hotel_reservation_1";//到酒店预订页
        public static final String hotel_reservation_2 = "hotel_reservation_2";//到酒店详情页
        public static final String hotel_reservation_3 = "hotel_reservation_3";//网站预订 电话预订
        public static final String forum_post_write_back = "forum_post_write_back";


        public static final String community_filter_click = "community_filter_click";//社区首页帖子筛选tab点击
        public static final String notelist_topmore_click = "notelist_topmore_click";//每个板块帖子列表更多置顶点击
        public static final String click_write_normal = "click_write_normal";//点击发短帖
        public static final String click_write_major = "click_write_major";//点击发长文


        public static final String splashAd_click = "splashAd_click";//闪屏广告点击
        public static final String splashAd_skip = "splashAd_skip";//闪屏广告跳过

        public static final String index_banner_click = "index_banner_click";//首页轮播banner点击
        public static final String index_hot_click = "index_hot_click";//首页热门互动点击
        public static final String index_recommend_click = "index_recommend_click";//首页帖子推荐点击
        public static final String index_tab_click = "index_tab_click";//首页顶部tab导航点击

        public static final String flowAd_click = "flowAd_click";//信息流广告点击
        public static final String community_sidebarAd_click = "community_sidebarAd_click";//社区板块选择每个tab页顶部广告点击

        public static final String community_navbar_click = "community_navbar_click";//读帖首页导航栏tab点击
        public static final String community_sort = "community_sort";//读帖首页导航栏tab点击排序
        public static final String community_refresh = "community_refresh";//读帖首页帖子刷新点击

        public static final String notelist_top_click = "notelist_top_click";//每个板块帖子列表置顶帖点击
        public static final String notelist_tab_click = "notelist_tab_click";//每个版块导航栏tab点击
        public static final String notelist_note_sort = "notelist_note_sort";//每个版块导航栏读帖排序更改点击

        public static final String tabbar_click = "tabbar_click";//tabbar点击

        public static final String notedetail_comment_click = "notedetail_comment_click";//帖子详情页评论图标点击
        public static final String notedetail_paging_click = "notedetail_paging_click";//帖子详情页分页图标点击
        public static final String commentlist_paging_click = "commentlist_paging_click";//评论详情页分页图标点击

        public static final String forum_post = "forum_post";//点击发图文/发文字图标

        public static final String guidelist_tab_click = "guidelist_tab_click";//攻略列表页导航栏tab切换

        public static final String report_tab_click = "report_tab_click";//报告列表页导航tab
        public static final String community_collection_click = "community_collection_click";//读帖首页话题点击
        public static final String notelist_collection_click = "notelist_collection_click";//每个版块帖子列表话题点击
        public static final String forum_subscribe = "forum_subscribe";//各个版块被订阅次数
        public static final String recommend_flyertea_to_friends = "recommend_flyertea_to_friends";//	点击【我的】-【推荐好友】
        public static final String notedetail_tag_click = "notedetail_tag_click";//点击【帖子详情页】标签
        public static final String notedetail_tagAd_click = "notedetail_tagAd_click";//点击【帖子详情页】标签广告
        public static final String notedetail_hotcollection_click = "notedetail_hotcollection_click";//点击【帖子详情页】一周热帖
        public static final String guidelist_category_click = "guidelist_category_click";//击【攻略首页】分类
        public static final String message = "message";//	点击消息图标
        public static final String message_category_click = "message_category_click";//	点击消息中心消息分类
        public static final String coupon_tab_click = "coupon_tab_click";//	点击优惠首页tab
        public static final String coupon_list_click = "coupon_list_click";//	点击优惠首页列表内容


        public static final String post_pic_upload = "post_pic_upload";//点击【发帖页面】的【上传图片】图标
        public static final String post_video_upload = "post_video_upload";//点击【发帖页面】的【上传视频】图标
        public static final String post_video_record = "post_video_record";//点击【发帖页面】的【拍摄视频】图标

        public static final String notedetail_editBtn_click = "notedetail_editBtn_click";//点击【帖子详情页】的【帖子编辑】按钮
        public static final String notedetail_deleteBtn_click = "notedetail_deleteBtn_click";//点击【帖子详情页】的【帖子删除】按钮
        public static final String notedetail_comment_deleteBtn_click = "notedetail_comment_deleteBtn_click";//点击【帖子详情页】的【评论删除】按钮

        public static final String search_record_click = "search_record_click";//点击搜索记录
        public static final String search_record_delete = "search_record_delete";//删除搜索记录
        public static final String search_hot_click = "search_hot_click";//点击热门搜索
        public static final String search_filter_click = "search_filter_click";//点击【搜索结果页】版块筛选
        public static final String search_sort_click = "search_sort_click";//点击【搜索结果页】排序
        public static final String search_articleBtn_click = "search_articleBtn_click";//点击【搜索结果页】的【看攻略】按钮
        public static final String search_questionBtn_click = "search_questionBtn_click";//点击【搜索结果页】的【去提问】按钮
        public static final String search_collection_click = "search_collection_click";//点击【搜索结果页】的专题
        public static final String search_navbar_click = "search_navbar_click";//点击【搜索结果页】的分类导航
        public static final String search_noResult_articleBtn_click = "search_noResult_articleBtn_click";//点击【搜索无结果页】的【看攻略】按钮
        public static final String search_noResult_questionBtn_click = "search_noResult_questionBtn_click";//点击【搜索无结果页】的【去提问】按钮


        public static final String tastdetail_doneBtn_click = "tastdetail_doneBtn_click";//点击【任务详情页】的【我已完成】按钮
        public static final String tastdetail_uninterestedBtn_click = "tastdetail_doneBtn_click";//任务详情页不感兴趣点击

        public static final String register_nextStep_click = "register_nextStep_click";//点击【注册页面】的【下一步】按钮
        public static final String register_thirdParty_click = "register_thirdParty_click";//点击【注册页面】的【三方登录】图标
        public static final String perfectInfo_headBtn_click = "perfectInfo_headBtn_click";//点击【完善资料】的【头像】图标
        public static final String perfectInfo_doneBtn_click = "perfectInfo_doneBtn_click";//点击【完善资料】的【完成】图标

        public static final String login_method_switch = "login_method_switch";//切换登录页面手机号/用户名登录
        public static final String login_thirdParty_click = "login_thirdParty_click";//点击【登录页面】的【三方登录】图标
        public static final String login_register_click = "login_register_click";//点击【登录页面】的【注册新用户】按钮


    }

}
