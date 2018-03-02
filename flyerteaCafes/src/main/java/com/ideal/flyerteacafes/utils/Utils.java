package com.ideal.flyerteacafes.utils;

/**
 * 常量保存
 *
 * @author JR
 */
public class Utils {


    /**
     * 读帖tab
     */
    public static final String read_tab = "community_read_tab.txt";

    /**
     * 优拍云空间名
     **/
    public static final String bucket = "flyerteaphoto";
    /**
     * 表单密钥
     **/
    public static final String formApiSecret = "t2BcNsAtV1+vRKJDTAtiQgD/hv0=";


    /**
     * 编码格式
     */
    public static final String CHARSET = "gbk";

    /**
     * 存储文件的根目录，需要根据项目进行配置,例如，在下载的时候储存目录
     */
    public static final String ROOT_FILE_NAME = "Flyertea";

    public static final String SHAREDPREFERENCE_NAME = "Flyertea";

    /**
     * 项目logcat标签
     */
    public static final String FLYLOGCAT = "FLogcat";

    /**
     * toast弹出时间
     */
    public static final int TOASTTIME = 5000;

    /**
     * 数据库名字
     */
    public static final String Database_Name = "flyertea.db";

    /**
     * 数据库版本
     */
    public static final int DB_Version = 1;

    /**
     * 包名
     */
    public static final String PACKAGE_NAME = "com.ideal.flyerteacafes.";

    /**
     * 登录成功，返回字段
     */
    public static final String ret_suc_code = "success";

    /**
     * 失败，返回字段
     */
    public static final String ret_def_code = "error";


    /**
     * 手机状态常量
     *
     * @author JR
     */
    public static class PhoneState {

        public static final String PHONESTATE = "phoneState";

        /**
         * 屏幕高度
         */
        public static final String SCREEN_HEIGHT = "screenHeight";
        /**
         * 屏幕宽度
         */
        public static final String SCREEN_WIDTH = "screenWidth";
        /**
         * 手机型号
         */
        public static final String TERMINAL_NAME = "terminalName";
        /**
         * 手机唯一标示，设备号
         */
        public static final String TERMINAL_KEY = "terminalKey";
        /**
         * 手机android版本号
         */
        public static final String TERMINAL_SYSTEMCODE = "terminalSystemCode";
        /**
         * app版本号
         */
        public static final String APP_VERSION = "appVersion";

    }

    /**
     * http网络请求
     *
     * @author JR
     */
    public static class HttpRequest {
        // TODO 根据项目情况 修改

        /**
         * 成功表示，用于和服务器返回表示状态作比较
         */
        public static final String successedCode = "0";

        /**
         * http请求超时时间, 默认是30秒
         */
        public static final int TIMEOUT = 30000;

        /**
         * http请求的contentType
         */
        public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

        // public static login_bg String CONTENT_TYPE_UPDATE = "application/xml";
        public static final String CONTENT_TYPE_UPDATE = "application/json";

        /**
         * 编码格式
         */
        public static final String CHARSET = "utf-8";


        public static final String HTTP_URL = "https://www.flyertea.com";//正式网络
        //        public static login_bg String HTTP_URL = "http://apitest.flyertea.com";//测试环境
//		public static login_bg String HTTP_URL = "http://bbs.flyertrip.com";//内网
//        public static login_bg String HTTP_FLY1="http://114.141.162.147:7777";//7 1
//        public static login_bg String HTTP_FLY1="http://apitest.flyertea.com";//测试环境
        public static final String HTTP_FLY1 = "http://api.flyertea.com";//正式环境

        public static final String HTTP_FLY2 = "http://114.141.162.147:80";
        /**
         * 登录
         */
//        public static login_bg String HTTP_login = HTTP_URL + "/api/mobile/index.php?module=login&version=5&loginsubmit=yes&loginfield=auto&mobile=yes";
        public static final String HTTP_login = HTTP_URL + "/api/mobile/index.php?version=4&mobile=yes&cookietime=2592000&module=login&loginsubmit=yes&loginfield=auto";
//        public static login_bg String HTTP_login = HTTP_URL + "/api/mobile/index.php?version=6&mobile=yes&cookietime=2592000&module=login&loginsubmit=yes&loginfield=auto";

        /**
         * 退出登录
         */
        public static final String HTTP_LOGOUT = HTTP_URL + "/api/mobile/index.php?action=logout&module=login";

        /**
         * 订阅的版块
         */
        public static final String HTTP_myfavforum = HTTP_URL + "/source/plugin/mobile/mobile.php?module=myfavforum";

        /**
         * 订阅版块
         */
        public static final String HTTP_FAVFORUM = HTTP_URL + "/api/mobile/index.php?module=favforum";

        /**
         * 取消订阅版块
         */
        public static final String HTTP_SPACECP = HTTP_URL + "/api/mobile/index.php?module=spacecp&op=delete&ac=favorite&deletesubmit=true&appcan=appcan";

        /**
         * 论坛版块
         */
        public static final String HTTP_forumindex = HTTP_URL + "/newcomment/index.php/Api/Subscribe/datalist.html";

        /**
         * 首页导航栏豆腐块icon
         */
        public static final String HTTP_HOME_ICON = HTTP_URL + "/api/mobile/index.php?module=basicdata&type=navicon&version=6";

        /**
         * 社区版块
         */
        public static final String HTTP_COMMUNITY_INDEX = HTTP_URL + "/source/plugin/mobile/mobile.php?module=basicdata&type=forumlist&version=5";

        /**
         * 社区首页：帖子列表
         */
        public static final String HTTP_COMMUNITY_TOPIC_LIST = HTTP_URL + "/source/plugin/mobile/mobile.php";

        /**
         * 社区首页：送花
         */
        public static final String HTTP_COMMUNITY_TO_FLOWER = HTTP_URL + "/source/plugin/mobile/mobile.php?module=articleaction&version=6";

        /**
         * 论坛子模块详情
         */
        public static final String HTTP_viewthread = HTTP_URL + "/source/plugin/mobile/mobile.php?module=viewthread";

        /**
         * 论坛入口获取的更新文章条目
         */
        public static final String HTTP_FORUMESSAY = HTTP_URL + "/source/plugin/mobile/mobile.php?module=forumindex";

        /**
         * 论坛子模块列表
         */
        public static final String HTTP_FORUMDISPLAY = HTTP_URL + "/source/plugin/mobile/mobile.php?module=forumdisplay&version=5";

        /**
         * 优惠详情
         */
        public static final String HTTP_PREFERENTIAL = HTTP_URL + "/newcomment/index.php/Api/Promotion/detail.html";

        /**
         * 最新帖子
         */
        public static final String HTTP_appnewthread = HTTP_URL
                + "/api/mobile/index.php?module=appnewthread";

        /**
         * 热门帖子
         */
        public static final String HTTP_hotthread = HTTP_URL
                + "/api/mobile/index.php?module=hotthread";

        /**
         * 精华帖子
         */
        public static final String HTTP_appdigest = HTTP_URL
                + "/api/mobile/index.php?module=appdigest";

        /**
         * 搜索
         */
        public static final String HTTP_SEARCH = HTTP_URL + "/source/plugin/mobile/mobile.php?module=searchlist&mod=forum";

        /**
         * 广告
         */
//        public static login_bg String HTTP_AD = HTTP_URL + "/newcomment/index.php/Api/Banner/datalist.html";
        public static final String HTTP_AD = HTTP_URL + "/source/plugin/mobile/mobile.php?module=advis&apptype=flyertea&advtype=banner&version=6";

        /**
         * 我的帖子列表
         */
        public static final String HTTP_MYPOST = HTTP_URL + "/source/plugin/mobile/mobile.php?module=users&version=6&type=mythread";

        /**
         * 我的回复
         */
        public static final String HTTP_MYREPLY = HTTP_URL + "/source/plugin/mobile/mobile.php?module=users&version=6&type=myreply";

        /**
         * 个人主页:他的帖子/回复
         */
        public static final String HTTP_HIS_TOPIC = HTTP_URL + "/source/plugin/mobile/mobile.php?module=users&version=6";

        /**
         * 论坛图片上传
         */
        public static final String HTTP_uploadPictures = HTTP_URL + "/source/plugin/mobile/mobile.php?module=forumupload";

        /**
         * 论坛帖子回复
         */
        public static final String HTTP_REPLYPOST = HTTP_URL + "/source/plugin/mobile/mobile.php?module=sendpost&appcan=appcan&replysubmit=yes&mobile=yes";
        /**
         * 送鲜花
         */
        public static final String HTTP_FLOWER = HTTP_URL + "/plugin.php?check2=check&appcan=appcan&id=floweregg:fegg&do=sendflower&feggsubmit=true&feggsubmit_btn=true&handlekey=sendlove";

        public static final String HTTP_CANCLE_FLOWER = HTTP_URL + "/source/plugin/mobile/mobile.php?module=threadoper&version=6&action=delflower";


        /**
         * 发帖
         */
        public static final String HTTP_PUBLISHPOST = HTTP_URL + "/source/plugin/mobile/mobile.php?module=newthread&appcan=appcan&topicsubmit=yes&mobile=yes&version=6";

        /**
         * 酒店基本数据
         */
        public static final String HTTP_HOTEL = HTTP_URL + "/newcomment/index.php/Api/Hotel/datalist.html";

        /**
         * 信用卡积分基础数据
         */
        public static final String HTTP_CREDIT_BASIC = HTTP_URL + "/newcomment/index.php/Api/Credit/databasic.html";

        /**
         * 信用卡积分查询模块列表
         */
        public static final String HTTP_CREDIT = HTTP_URL + "/newcomment/index.php/Api/Credit/datalist.html";

        /**
         * 资讯详情
         */
        public static final String HTTP_ARTICLE_DETAILS = HTTP_URL + "/newcomment/index.php/Api/Article/detail";

        /**
         * 引用回复
         */
        public static final String HTTP_SENDREPLY = HTTP_URL + "/source/plugin/mobile/mobile.php?module=sendreply";


        /**
         * 资讯详情回复
         */
        public static final String HTTP_ARTICLE_REPLY = HTTP_URL + "/newcomment/index.php/Api/Article/feedback.html";

        /**
         * 表情
         */
        public static final String HTTP_SMILEY = HTTP_URL + "/source/plugin/mobile/mobile.php?module=smiley";
        /**
         * 获得数量
         */
        public static final String HTTP_GETNUM1 = HTTP_URL + "/newcomment/index.php/Api/Common/getNum.html";
        public static final String HTTP_GETNUM = HTTP_URL + "/source/plugin/mobile/mobile.php?module=users&version=6";

        /**
         * 签到
         */
        public static final String HTTP_SIGNIN = HTTP_URL + "/plugin.php?id=k_misign:sign&operation=qiandao&from=insign&appcan=appcan";

        /**
         * 判断是否签到接口
         */
        public static final String HTTP_IS_SIGNIN = HTTP_URL + "/plugin.php?id=k_misign:sign&operation=qiandao&from=insign&appcan=appcan&check=check";

        /**
         * 我的提醒列表
         */
        public static final String HTTP_MYNOTELIST = HTTP_URL + "/newcomment/index.php/Api/Common/getPrompt.html";

        /**
         * 我的公告列表
         */
        public static final String HTTP_ANNOUNCEMENT = HTTP_URL + "/source/plugin/mobile/mobile.php?module=announcement";

        /**
         * 推送消息列表
         */
        public static final String HTTP_PUSH_LIST = HTTP_URL + "newcomment/index.php/Api/Common/getPrompt.html?module=mynotelist";

        /**
         * 我的私信列表
         */
        public static final String HTTP_MYPM = HTTP_URL + "/source/plugin/mobile/mobile.php?module=mypm&filter=privatepm&version=5";

        /**
         * 发送私信
         */
        public static final String HTTP_SENDPM = HTTP_URL + "/source/plugin/mobile/mobile.php?module=sendpm&pmsubmit=yes&appcan=appcan";

        /**
         * 单独获取用户信息：
         */
        public static final String HTTP_PROFILE = HTTP_URL + "/api/mobile/index.php?module=space&do=profile";

        /**
         * 收藏帖子
         */
        public static final String HTTP_COLLECT = HTTP_URL + "/source/plugin/mobile/mobile.php?module=favthread&appcan=appcan&favoritesubmit=true";

        /**
         * 我的收藏
         */
        public static final String HTTP_MY_COLLECT = HTTP_URL + "/source/plugin/mobile/mobile.php?module=myfavthread";

        /**
         * 取消收藏
         */
        public static final String HTTP_CANCLE_COLLECT = HTTP_URL + "/source/plugin/mobile/mobile.php?module=spacecp&op=delete&ac=favorite&deletesubmit=true&appcan=appcan";

        /**
         * 新手指引
         */
        public static final String HTTP_NEW_HAND = HTTP_URL + "/source/plugin/mobile/mobile.php?module=viewthread&tid=289588";

        /**
         * 检测帖子是否被收藏
         */
        public static final String HTTP_ISCOLLECT = HTTP_URL + "/source/plugin/mobile/mobile.php?module=myfavthread&check2=check";

        /**
         * 新搜索接口
         */
        public static final String HTTP_SEARCH_NEW=HTTP_URL+"/source/plugin/mobile/mobile.php?module=search&version=5";

        /**
         * 热词接口
         **/
        public static final String HTTP_HOTKEY = HTTP_URL + "/source/plugin/mobile/mobile.php?module=hotkey&version=6";

        /**
         * 联想词接口
         */
        public static final String HTTP_ASSOCWORD = HTTP_URL + "/source/plugin/mobile/mobile.php?module=assocword";

        /***
         * 首页飞客日报
         ***/
        public static final String HTTP_HOMEDAILY = HTTP_URL + "/newcomment/index.php/Api/Article/datalistall.html";

        /**
         * 获取版本
         */
        public static final String HTTP_UPVERSION = HTTP_URL + "/source/plugin/mobile/mobile.php?module=upversion";


        /***
         * 首页飞客攻略
         ***/
        public static final String HTTP_GRAIDERS = HTTP_URL + "/newcomment/index.php/Api/Article/datalistguide.html";

        /****
         * 好友列表
         ***/
        public static final String HTTP_FRIENDS = HTTP_URL + "/source/plugin/mobile/mobile.php?module=friend";//&version=1&page=2

        /**
         * 按照名字搜索 会员列表 /source/plugin/mobile/mobile.php?module=spacecp&searchsubmit=true&ac=search&type=all
         **/
        public static final String HTTP_SEARCH_MEMBER = HTTP_URL + "/source/plugin/mobile/mobile.php?module=search&version=6&type=user";

        /**
         * 城市列表
         **/
        public static final String HTTP_CITYLIST = HTTP_URL + "/source/plugin/mobile/mobile.php?module=member_data&ac=district";

        /**
         * 编辑资料
         */
        public static final String HTTP_COMMIT = HTTP_URL + "/api/mobile/index.php?module=spacecp&ac=profile&op=info&version=5";

        /**
         * 新的朋友
         */
        public static final String HTTP_NEWFRIENDS = HTTP_URL + "/source/plugin/mobile/mobile.php?module=spacecp&ac=friend&op=request";

        /**
         * 行业
         */
        public static final String HTTP_HANGYE = HTTP_URL + "/api/mobile/index.php?module=member_data&ac=hangye";

        /**
         * 兴趣列表
         */
        public static final String HTTP_INTEREST = HTTP_URL + "/api/mobile/index.php?module=member_data&ac=interest";

        /**
         * 职位列表
         */
        public static final String HTTP_OCCUPATION = HTTP_URL + "/api/mobile/index.php?module=member_data&ac=occupation";

        /**
         * 加为好友
         */
        public static final String HTTP_ADDFRIEND = HTTP_URL + "/source/plugin/mobile/mobile.php?module=spacecp&ac=friend&op=add";

        /**
         * 删除好友
         **/
        public static final String HTTP_DETELEFRIEND = HTTP_URL + "/api/mobile/index.php?module=spacecp&ac=friend&op=ignore&uid=26749&confirm=1&inajax=1";

        /**
         * 同意添加好友
         */
        public static final String HTTP_AGREEAddFRIENDS = HTTP_URL + "/source/plugin/mobile/mobile.php?module=spacecp&ac=friend&op=add";

        /**
         * 拒绝好友
         */
        public static final String HTPP_IGNOREADDFRIENDS = HTTP_URL + "/source/plugin/mobile/mobile.php?module=spacecp&ac=friend&op=ignore&confirm=1";

        /**
         * 附近的人
         */
        public static final String HTTP_NEAR = HTTP_URL + "/api/mobile/index.php?activedays=1440&distance=10&do=friend&module=space&page%20=1&type=nearmember&view=online";

        /**
         * 设置是否对附近人可见
         */
        public static final String HTTP_HIDDEN = HTTP_URL + "/api/mobile/index.php?module=member_hidden";

        /**
         * 定位单独接口
         */
        public static final String HTTP_LOCATION = HTTP_URL + "/api/mobile/index.php?module=location";

        /**
         * 优惠列表接口
         */
        public static final String HTTP_HOMELIST = HTTP_URL + "/api/mobile/index.php?module=plugin&id=mini_zdm";//&a=4&b=0&s=0&l=0&d=0&category=1;

        /**
         * 优惠详情
         */
        public static final String HTTP_DISINFO = HTTP_URL + "/api/mobile/index.php?module=plugin&id=mini_zdm&mod=view";

        /**
         * 优惠列表评论详情
         **/
        public static final String HTTP_DISCOMMENT = HTTP_URL + "/api/mobile/index.php?id=mini_zdm&mod=view&module=plugin";

        /**
         * 优惠列表发送评论
         **/
        public static final String HTTP_SENDCOMMENT = HTTP_URL + "/api/mobile/index.php?module=plugin&id=mini_zdm&mod=view&applysubmit=true";

        /**
         * 点  值得
         */
        public static final String HTTP_ZHIDE = HTTP_URL + "/source/plugin/mobile/mobile.php?id=mini_zdm&mod=zhide&module=plugin";

        /**
         * 点 不值得
         */
        public static final String HTTP_BUZHIDE = HTTP_URL + "/source/plugin/mobile/mobile.php?id=mini_zdm&mod=buzhide&module=plugin";


        /**
         * 管理员删除帖子回复
         */
        public static final String HTTP_DELETE_REPLY_ADMIN = HTTP_URL + "/api/mobile/index.php?module=topicadmin&action=delpost&modsubmit=yes&version=2";

        /**
         * 用户删除帖子回复
         */
        public static final String HTTP_DELETE_REPLY_USER = HTTP_URL + "/api/mobile/index.php?module=forum&mod=post&action=edit&editsubmit=yes";

        /**
         * 删除主贴
         */
        public static final String HTTP_DELETE_POST = HTTP_URL + "/api/mobile/index.php?module=topicadmin&action=moderate&optgroup=3&modsubmit=yes&version=2";

        /**
         * 帖子评论操作
         */
        public static final String HTTP_THREAD_COMMENT_ACTION = HTTP_URL + "/source/plugin/mobile/mobile.php?module=threadoper&version=6";

        /**
         * 获取直播详情
         */
        public static final String HTTP_GET_ZHIBO_DETAIL = HTTP_FLY1 + "/feed";

        /**
         * 发送直播
         */
        public static final String HTTP_FEED = HTTP_FLY1 + "/feed";


        /**
         * 刪除直播
         */
        public static final String HTTP_FEED_DELETE = HTTP_FLY1 + "/feed/del";

        /**
         * 删除评论回复
         */
        public static final String HTTP_FEED_DELETE_COMMENT = HTTP_FLY1 + "/feed/reply/del";

        /**
         * 附近直播
         */
        public static final String HTTP_LOCAL_FEED = HTTP_FLY1 + "/feed/local";

        /**
         * 获取最新直播
         */
        public static final String HTTP_NEW_FEED = HTTP_FLY1 + "/feed/latest";

        /**
         * 获取热门直播
         */
        public static final String HTTP_HOT_FEED = HTTP_FLY1 + "/feed/hotest";

        /**
         * 点赞，取消点赞
         */
        public static final String HTTP_ZHIBO_LIKE = HTTP_FLY1 + "/feed/like";


        /**
         * 单个话题获取
         */
        public static final String HTTP_ZHIBO_TOPIC = HTTP_FLY1 + "/topic";

        /**
         * 置顶话题
         */
        public static final String HTTP_ZHIBO_TOPIC_TOP = HTTP_FLY1 + "/topic/top";

        /**
         * 所有话题
         */
        public static final String HTTP_ZHIBO_TOPIC_ALL = HTTP_FLY1 + "/topic/all";

        /**
         * 获取话题下直播
         */
        public static final String HTTP_ZHIBO_TOPIC_FEED = HTTP_FLY1 + "/topic/feed";

        /**
         * 直播的详情的评论列表
         */
        public static final String HTTP_ZHIBO_COMMENT = HTTP_FLY1 + "/feed/reply";

        /**
         * 发送直播
         */
        public static final String HTTP_ZHIBO_SENDCOM = HTTP_FLY1 + "/feed/reply";

        /**
         * 举报直播
         */
        public static final String HTTP_ZHIBO_EXPOSE = HTTP_FLY1 + "/feed/expose";

        /**
         * 手机号获取验证码
         */
        public static final String HTTP_PHONE_CODE = HTTP_URL + "/api/mobile/index.php?module=plugin&id=dzapp_mobile&mod=ajax&ac=code&inajax=1&version=5";


        /**
         * 验证验证码是否正确
         */
        public static final String HTTP_VERIFY_CODE = HTTP_URL + "/api/mobile/index.php?module=checksmscode&version=5";

        /**
         * 手机号登陆
         */
        public static final String HTTP_LOG_MOBILE = HTTP_URL + "/api/mobile/index.php?module=login&loginsubmit=yes&loginfield=auto&mobile=yes&smslogin=yes&version=4";

        /**
         * 注册
         */
        public static final String HTTP_REGISTER = HTTP_URL + "/source/plugin/mobile/mobile.php?module=register&regsubmit=yes&version=6";


        /**
         * 第三方登录
         */
        public static final String HTTP_THIRD_LOGIN = HTTP_URL + "/api/mobile/index.php?module=login&loginsubmit=yes&loginfield=auto&mobile=no&version=4";

        /**
         * 第三方绑定
         */
        public static final String HTTP_BINDING = HTTP_URL + "/api/mobile/index.php?module=bindthird&version=5";

        /**
         * 手机号绑定
         */
        public static final String HTTP_BINDING_MOBILE = HTTP_URL + "/api/mobile/index.php?module=plugin&id=dzapp_mobile&inajax=1&version=5";


        /**
         * 第三方未注册状态绑定已有账号
         */
        public static final String HTTP_REGIST_BIND_THRID_OLD = HTTP_URL + "/api/mobile/index.php?module=login&loginsubmit=yes&loginfield=auto&mobile=yes&version=4";


        /**
         * 第三方注册及绑定
         */
        public static final String HTTP_REGIST_BIND_THRID_NEW = HTTP_URL + "/source/plugin/mobile/mobile.php?module=register&regsubmit=yes";


        /**
         * 列表
         */
        public static final String HTTP_ARTICLE_TYPE_LIST = HTTP_URL + "/source/plugin/mobile/mobile.php?module=portal&version=6&mod=list";

        /**
         * 首页tab 显示url
         */
        public static final String HTTP_HOME_TAB = HTTP_URL + "/source/plugin/mobile/mobile.php?module=portal&version=4&mod=list";
        public static final String HTTP_HOME_TAB_TUIJIAN = HTTP_HOME_TAB + "&catid=124";
        public static final String HTTP_HOME_TAB_YOUHUI = HTTP_URL + "/api/mobile/index.php?module=plugin&id=mini_zdm&a=0&b=0&s=0&l=0&d=0&category=1";
        public static final String HTTP_HOME_TAB_LVXING = HTTP_HOME_TAB + "&catid=293";
        public static final String HTTP_HOME_TAB_FEIXING = HTTP_HOME_TAB + "&catid=130";
        public static final String HTTP_HOME_TAB_GONGLUE = HTTP_HOME_TAB + "&catid=298";
        public static final String HTTP_HOME_TAB_FAXIAN = HTTP_HOME_TAB + "&catid=125";
        public static final String HTTP_HOME_TAB_JIUDIAN = HTTP_HOME_TAB + "&catid=196";
        public static final String HTTP_HOME_TAB_WEIWEN = HTTP_URL + "/api/mobile/index.php?mod=list&module=plugin&id=wq_wechatcollecting&classid=2&displayorder=2&version=4";
        public static final String HTTP_HOME_TAB_HANGKONG = HTTP_HOME_TAB + "&catid=3";
        public static final String HTTP_HOME_TAB_XINGYONGKA = HTTP_HOME_TAB + "&catid=4";

        //攻略酒店
        public static final String HTTP_STRATEGY_HOTEL = HTTP_ARTICLE_TYPE_LIST + "&catid=2";
        //攻略航空
        public static final String HTTP_STRATEGY_ARI = HTTP_ARTICLE_TYPE_LIST + "&catid=3";
        //攻略信用卡
        public static final String HTTP_STRATEGY_CARD = HTTP_ARTICLE_TYPE_LIST + "&catid=314";
        //报告全部
        public static final String HTTP_REPORT_ALL = HTTP_ARTICLE_TYPE_LIST + "&catid=319";
        //报告酒店
        public static final String HTTP_REPORT_LOUNGE = HTTP_ARTICLE_TYPE_LIST + "&catid=331";
        //休息室
        public static final String HTTP_REPORT_HOTEL = HTTP_ARTICLE_TYPE_LIST + "&catid=125";
        //报告航空
        public static final String HTTP_REPORT_AVIATION = HTTP_ARTICLE_TYPE_LIST + "&catid=130";
        //优惠
        public static final String HTTP_DISCOUNT = HTTP_ARTICLE_TYPE_LIST + "&catid=322";

        //攻略
        public static final String HTTP_RAIDERS = HTTP_URL + "/source/plugin/mobile/mobile.php?module=basicdata&type=strategy&version=6";


        /**
         * 问答全部
         */
        public static final String HTTP_INTERLOCUTION = HTTP_URL + "/source/plugin/mobile/mobile.php?module=forumdisplay&version=6&fid=all&filter=typeid&typeid=1&orderby=dateline&sum=all";

        /**
         * 问答推荐
         */
        public static final String HTTP_INTERLOCUTION_TUIJAN = HTTP_URL + "/source/plugin/mobile/mobile.php?module=forumdisplay&version=6&fid=1&filter=typeid&typeid=1&orderby=dateline&sum=1";


        /**
         * 酒店求助问答：
         */
        public static final String HTTP_INTERLOCUTION_HOTEL = HTTP_URL + "/source/plugin/mobile/mobile.php?module=forumdisplay&version=6&fid=19&filter=typeid&typeid=1&orderby=dateline&sum=19";


        /**
         * 航空求助问答：
         */
        public static final String HTTP_INTERLOCUTION_AVIATION = HTTP_URL + "/source/plugin/mobile/mobile.php?module=forumdisplay&version=6&fid=57&filter=typeid&typeid=1&orderby=dateline&sum=57";


        /**
         * 信用卡求助问答
         */

        public static final String HTTP_INTERLOCUTION_CREDITCARD = HTTP_URL + "/source/plugin/mobile/mobile.php?module=forumdisplay&version=6&fid=226&filter=typeid&typeid=1&orderby=dateline&sum=226";


        /**
         * 热门互动
         */
        public static final String HTTP_HOT_INTERACTION = HTTP_URL + "/source/plugin/mobile/mobile.php?module=editors&type=homehot&version=6";


        /**
         * 活动
         */
        public static final String HTTP_EXERCISE = HTTP_URL + "/source/plugin/mobile/mobile.php?module=plugin&id=zzbuluo_event";


        /**
         * 帖子推荐
         */
        public static final String HTTP_POST_TUIJIAN = HTTP_URL + "/source/plugin/mobile/mobile.php?module=nge&version=6";


        /**
         * 启动页广告
         */
//        public static login_bg String HTTP_START_ADVERT = HTTP_URL + "/api/mobile/index.php?module=advis&advtype=startpage&apptype=flyertea&ostype=android&version=5";
        public static final String HTTP_START_ADVERT = HTTP_URL + "/source/plugin/mobile/mobile.php?module=advis&apptype=flyertea&advtype=startpage&version=6";


        /**
         * 我的直播
         */
        public static final String HTTP_FEED_MY = HTTP_FLY1 + "/feed/my";

        /**
         * 围观人数
         */
        public static final String HTTP_CIRCUSEE = HTTP_FLY1 + "/feed/circusee";

        /**
         * 帖子详情
         */
        public static final String HTTP_THREAD_DETAIL = HTTP_URL + "/source/plugin/mobile/mobile.php?module=threaddetail&version=4";

        /**
         * 帖子回复
         */
        public static final String HTTP_THREAD_COMMENTLIST = HTTP_URL + "/source/plugin/mobile/mobile.php?module=threadpost&version=6&ppp=10";

        /**
         * 文章详情
         */
        public static final String HTTP_ARTICLE_DETAIL = HTTP_URL + "/source/plugin/mobile/mobile.php?module=articledetail&version=6";

        /**
         * 文章评论
         */
        public static final String HTTP_ARTICLE_COMMENT = HTTP_URL + "/source/plugin/mobile/mobile.php?module=articlepost&version=6";


        /**
         * 文章送花
         */
        public static final String HTTP_ARTICLE_SENDFLOWER = HTTP_URL + "/source/plugin/mobile/mobile.php?module=articleaction&action=flower&version=6";


        /**
         * 取消收藏文章
         */
        public static final String HTTP_ARTICLE_CANCLE_Collect = HTTP_URL + "/source/plugin/mobile/mobile.php?module=articleaction&action=unfollow&version=6";

        /**
         * 收藏文章
         */
        public static final String HTTP_ARTICLE_Collect = HTTP_URL + "/source/plugin/mobile/mobile.php?module=articleaction&action=follow&version=6";

        /**
         * 批量获取 图片id
         */
        public static final String HTTP_UPLOADIMAGE = HTTP_URL + "/source/plugin/mobile/mobile.php?module=uploadimage&version=5";


        /**
         * 酒店搜索
         */
        public static final String HTTP_HOTEL_SEARCH = "https://i.flyertrip.com/v5/hotel/search";


        /**
         * 用戶最近浏览&do=favorite&view=me&version=6&type=post
         */
        public static final String HTTP_READS = HTTP_URL + "/source/plugin/mobile/mobile.php?module=users&ac=reads&version=6";


        /**
         * 版块信息详情
         */
        public static final String HTTP_FORUMDETAIL = HTTP_URL + "/source/plugin/mobile/mobile.php?module=basicdata&type=forumdetail&version=5";


        /**
         * 版块置顶帖
         */
        public static final String HTTP_TOPTHREADS = HTTP_URL + "/source/plugin/mobile/mobile.php?module=topthreads&orderby=dateline&version=5";


        /**
         * 关注
         */
        public static final String HTTP_FORUMACTION = HTTP_URL + "/source/plugin/mobile/mobile.php?module=forumaction&version=6";


        /**
         * 我的关注
         */
        public static final String HTTP_FAVORITE = HTTP_URL + "/source/plugin/mobile/mobile.php?module=users&do=favorite&view=me&version=6&type=forum";

        /**
         * 上报最近阅读的帖子
         */
        public static final String HTTP_USER_READS_POST = HTTP_URL + "/source/plugin/mobile/mobile.php?module=users&ac=reads&version=6";

        /**
         * 更新
         */
        public static final String HTTP_UPGRADE = HTTP_URL + "/source/plugin/mobile/mobile.php?module=basicdata&type=appinfo&platform=android&version=6";

        /**
         * 帖子搜查
         */
        public static final String HTTP_THREAD_SEARCH = HTTP_URL + "/source/plugin/mobile/mobile.php?module=search&version=6";

        /**
         * 小白
         */
        public static final String HTTP_THREAD_XIAOBAI = HTTP_URL + "/source/plugin/mobile/mobile.php?module=articles&version=6&type=manual";


        /**
         * 举报原因
         */
        public static final String HTTP_THREAD_JUBAO_LIST = HTTP_URL + "/api/mobile/index.php?module=basicdata&type=reportreason&version=6";

        /**
         * 举报帖子
         */
        public static final String HTTP_THREAD_JUBAO = HTTP_URL + "/source/plugin/mobile/mobile.php?module=report&rtype=post&version=6";

        /**
         * 基础数据
         */
        public static final String HTTP_TEA = HTTP_URL + "/api/mobile/index.php?module=basicdata&type=webinfo&site=flyertea&&version=6";

        /**
         * 个人中心首页
         */
        public static final String HTTP_PERSONAL_HOME = HTTP_URL + "/source/plugin/mobile/mobile.php?module=notifications&noticetype=total&version=5";

        /**
         * 回复我的
         */
        public static final String HTTP_REPLY_MINE = HTTP_URL + "/source/plugin/mobile/mobile.php?module=notifications&noticetype=posts&version=5";

        /**
         * 送我鲜花
         */
        public static final String HTTP_SEND_FLOWERS = HTTP_URL + "/source/plugin/mobile/mobile.php?noticetype=flowers&module=notifications&version=5";

        /**
         * 加我好友
         */
        public static final String HTTP_ADD_MY_FRIENDS = HTTP_URL + "/source/plugin/mobile/mobile.php?noticetype=friends&module=notifications&version=5";

        /**
         * 系统提醒消息
         */
        public static final String HTTP_SYSTEM_MESSAGE_REMIND = HTTP_URL + "/source/plugin/mobile/mobile.php?module=notifications&noticetype=system&version=5";

        /**
         * 飞客活动
         */
        public static final String HTTP_FLYER_ACTIVIES = HTTP_URL + "/source/plugin/mobile/mobile.php?module=notifications&noticetype=activities&version=6";

        /**
         * 全部消息标为已读、标记某条消息为已读
         */
        public static final String HTTP_MARK_MESSAGE_READS = HTTP_URL + "/source/plugin/mobile/mobile.php?module=notifications&version=5";

        /**
         * 服务
         */
        public static final String HTTP_SERVICE = HTTP_URL + "/api/mobile/index.php?module=plugin&id=flyer_service";

        /**
         * 获取系统预设刷卡任务 热门任务
         */
        public static final String HTTP_SWINGCARD_TASKNAME = HTTP_URL + "/source/plugin/mobile/mobile.php?module=creditcardmission&version=6";

        /**
         * 卡组织
         */
        public static final String HTTP_CARD_GROUP = HTTP_URL + "/source/plugin/mobile/mobile.php?module=basicdata&type=cardchannel&version=6";

        /**
         * 银行
         */
        public static final String HTTP_BANK = HTTP_URL + "/source/plugin/mobile/mobile.php?module=basicdata&type=bank&version=6";

        /**
         * 添加刷卡任务
         */
        public static final String HTTP_ADD_TASK = HTTP_URL + "/source/plugin/mobile/mobile.php?module=creditcardmission&action=add&version=6";

        /**
         * 用户的刷卡任务
         */
        public static final String KEY_TASK_USER = HTTP_URL + "/source/plugin/mobile/mobile.php?module=creditcardmission&type=user&version=6";

        /**
         * 用户增加系统预设刷卡任务
         */
        public static final String HTTP_ADD_SYSTEM_TASK = HTTP_URL + "/source/plugin/mobile/mobile.php?module=creditcardmission&action=add&version=6&datatype=sysmission";

        /**
         * 给某项任务添加周期
         */
        public static final String HTTP_ADD_PERIOD_TO_TASK = HTTP_URL + "/source/plugin/mobile/mobile.php?module=creditcardmission&action=add&version=6&datatype=period";

        /**
         * 给周期添加进度
         */
        public static final String HTTP_ADD_PROGRESS_TO_PERIOD = HTTP_URL + "/source/plugin/mobile/mobile.php?module=creditcardmission&action=add&version=6datatype=progress";


        /**
         * 刷卡任务详情
         */
        public static final String HTTP_TASK_DETAILS = HTTP_URL + "/source/plugin/mobile/mobile.php?module=creditcardmission&type=missiondetail&version=6";

        /**
         * 刷卡任务进度
         */
        public static final String HTTP_TASK_PROGRESS = HTTP_URL + "/source/plugin/mobile/mobile.php?module=creditcardmission&action=add&version=6&datatype=progress";

        /**
         * 银行列表
         */
        public static final String HTTP_BRANK = HTTP_URL + "/source/plugin/mobile/mobile.php?module=basicdata&type=bank&version=6";

        /**
         * 删除用户任务
         */
        public static final String HTTP_DELETE_USER_TASK = HTTP_URL + "/source/plugin/mobile/mobile.php?module=creditcardmission&action=del&version=6&datatype=mission";

        /**
         * 我的任务
         */
        public static final String HTTP_MY_TASK_LIST = HTTP_URL + "/api/mobile/index.php?mobile=yes&module=forumtask&tasktype=all&version=5";

        /**
         * 我的任务详情
         */
        public static final String HTTP_MY_TASK_DETAILS = HTTP_URL + "/api/mobile/index.php?version=6&mobile=yes&module=forumtask&tasktype=all&action=detail";


        /**
         * 用户组
         */
        public static final String HTTP_USERGROUPS = HTTP_URL + "/api/mobile/index.php?version=6&mobile=yes&module=basicdata&type=usergroups";

        /**
         * 用户资料(工作，实名信息)
         */
        public static final String HTTP_USERPROFILE = HTTP_URL + "/api/mobile/index.php?version=6&mobile=yes&module=users&type=userprofile";


        /**
         * 常客卡类型
         */
        public static final String HTTP_GROUPMEMBERSHIPS = HTTP_URL + "/api/mobile/index.php?version=6&mobile=yes&module=basicdata&type=groupmemberships";

        /**
         * 认证常客卡
         */
        public static final String HTTP_CARD_RENGZHENG = HTTP_URL + "/source/plugin/mobile/mobile.php?module=users&type=userprofile&subtype=vips&view=me&version=6";


        /**
         * 勋章常客卡申领
         */
        public static final String HTTP_MEDALS_HOTEL_SHENGLING = HTTP_URL + "/api/mobile/index.php?module=medal&action=appapply&version=6";


        /**
         * 常客卡列表
         */
        public static final String HTTP_CARD_LIST = HTTP_URL + "/api/mobile/index.php?version=6&mobile=yes&module=users&type=userprofile&subtype=vips";

        /**
         * 实名资料
         */
        public static final String HTTP_REALNAME_DATA = HTTP_URL + "/source/plugin/mobile/mobile.php?module=users&type=userprofile&subtype=realname&view=me&version=6";

        /**
         * 行业兴趣职业城市等
         */
        public static final String HTTP_MEMBER_DATA_OCCUPATION = HTTP_URL + "/source/plugin/mobile/mobile.php?module=member_data&version=6&ac=occupation";


        /**
         * 我的威望列表
         */
        public static final String HTTP_CREDITLIST = HTTP_URL + "/source/plugin/mobile/mobile.php?module=users&type=creditlist&version=6";


        /**
         * 获取数量
         */
        public static final String HTTP_GET_NUMBER = HTTP_URL + "/api/mobile/index.php?version=6&mobile=yes&module=users&type=trends&view=me";

        /**
         * 删除常客卡
         */
        public static final String HTTP_DELETE_CARD = HTTP_URL + "/source/plugin/mobile/mobile.php?module=users&type=userprofile&subtype=vips&action=del&view=me&version=6";


        /**
         * 领取任务奖励
         */
        public static final String HTTP_FORUMTASK_DRAW = HTTP_URL + "/api/mobile/index.php?version=6&mobile=yes&module=forumtask&action=draw";

        /**
         * 分类
         */
        public static final String HTTP_TYPE = HTTP_URL + "/api/mobile/index.php?version=6&mobile=yes&module=basicdata&type=portalcategory";
        /**
         * 发帖板块
         */
        public static final String HTTP_THREAD_FORUM_LIST = HTTP_URL + "/api/mobile/index.php?version=5&mobile=yes&module=basicdata&type=forumlist";


        /**
         * 有赞登录
         */
        public static final String HTTP_YOUZAN_LOGIN = HTTP_URL + "/source/plugin/mobile/mobile.php?module=youzan&mobile=yes&version=6&ac=login";
        public static final String HTTP_YOUZAN_LOGOUT = HTTP_URL + "/source/plugin/mobile/mobile.php?module=youzan&mobile=yes&version=6";
        public static final String HTTP_YOUZAN_INIT = HTTP_URL + "/source/plugin/mobile/mobile.php?module=youzan&mobile=yes&version=6";


        public static final String HTTP_SHARE = HTTP_URL + "/source/plugin/mobile/mobile.php?module=users&version=6&type=share";


        /**
         * 获取位置名称
         */
        public static final String HTTP_LOCATION_NAME = HTTP_URL + "/source/plugin/mobile/mobile.php?module=location&version=6";

        /**
         * 点评
         */
        public static final String HTTP_COMMENT_LOCATION = HTTP_URL + "/source/plugin/mobile/mobile.php?module=comment&appcan=appcan&topicsubmit=yes&mobile=yes&version=6";

        /**
         * 我的勋章
         */
        public static final String HTTP_GET_MY_MEDALS_DATA = HTTP_URL + "/api/mobile/index.php?module=medal&action=all&version=6";

        /**
         * 申请勋章
         */
        public static final String HTTP_MEDALS_SHENGQING = HTTP_URL + "/api/mobile/index.php?module=medal&version=6";

        /**
         * 话题列表
         */
        public static final String HTTP_TOPIC_LIST = HTTP_URL + "/source/plugin/mobile/mobile.php?module=collection&action=view&version=6";

        /**
         * 一周热帖
         */
        public static final String HTTP_APPHEAT = HTTP_URL + "/source/plugin/mobile/mobile.php?module=forumdisplay&filter=appheat&version=5";

        /**
         * 验证航班号
         */
//        public static final String HTTP_CHECK_FLIGHT = HTTP_URL + "/source/plugin/mobile/mobile.php?module=basicdata&action=check&type=flight&app=flyertea&version=6";
        public static final String HTTP_CHECK_FLIGHT = HTTP_URL + "/source/plugin/mobile/mobile.php?module=basicdata&action=check&type=flight&app=flyertea&version=5";

        /**
         * 位置搜索
         */
        public static final String HTTP_SERACH_LOCAION = HTTP_URL + "/source/plugin/mobile/mobile.php?module=search&type=location&version=6";


        /**
         * 话题详情
         */
        public static final String HTTP_TOPIC_DETAILS = HTTP_URL + "/source/plugin/mobile/mobile.php?module=collection&page=1&version=6&action=detail";


        /**
         * tag分类下的列表
         */
        public static final String HTTP_TAG_THREAD_REPORT_LIST = HTTP_URL + "/source/plugin/mobile/mobile.php?module=plugin&id=onexin_tags&version=4";


        /**
         * 帖子成就
         */
        public static final String HTTP_POST_ACHIEVEMENT = HTTP_URL + "/source/plugin/mobile/mobile.php?module=notifications&noticetype=reward&version=5";


        /**
         * 修改生日
         */
        public static final String HTTP_UPDATE_BIRTHDAY = HTTP_URL + "/api/mobile/index.php?module=spacecp&ac=profile&op=info&version=5";

        /**
         * 系统消息列表
         */
        public static final String HTTP_SYSTEM_MESSAGE = HTTP_URL + "/source/plugin/mobile/mobile.php?module=mypm&filter=announcepm&version=5";

        /**
         * 领取银卡
         */
        public static final String HTTP_GET_CARL = HTTP_URL + "/source/plugin/mobile/mobile.php?module=tearights&platform=tnm&version=6";

        /**
         * 把单个系统消息，设为已读
         */
        public static final String HTTP_SYSTEM_SET_READ = HTTP_URL + "/source/plugin/mobile/mobile.php?module=mypm&subop=viewg";
    }

    public static class HtmlUrl {
        /**
         * 体验师
         */
        public static final String HTML_EXPERIENCE = "http://www.flyertea.com/free";

        /**
         * 信用卡
         */
//        public static login_bg String HTML_CREDITCARD = "http://shuabeiapp.com/h5#!/home";
        public static final String HTML_CREDITCARD = "https://c.shuabeiapp.com/?referer=feike";

        /**
         * 常用电话
         */
        public static final String HTML_PLUGIN = "http://www.flyertea.com/plugin.php?id=dzapp_phone114";

        /**
         * 招行酒店查询
         */
        public static final String HTML_HOTEL_SEARCH = "http://wunaoshua.leanapp.cn/tools/cmbchotels";

        /**
         * 飞米商城
         */
//        public static final String HTML_FLASHSALE = "http://www.flyertea.com/scoremall.html";
        public static final String HTML_FLASHSALE = "http://www.flyertea.com/scoremall_wap.html?fromapp=1";

        /**
         * 互助交易
         */
        public static final String HTML_ERSHOU = "http://www.flyertea.com/forum.php?mod=forumdisplay&fid=78&filter=sortid&sortid=128";

        /**
         * 返利
         */
        public static final String HTML_FANLI = "http://fanli.flyertrip.com/plugin.php?mod=wap&m=index";

        /**
         * 飞客管家
         */
//        public static login_bg String HTML_STEWARD="http://www.flyertrip.com/photels/customized.php";
        public static final String HTML_STEWARD = "http://m.flyertrip.com?utm_source=flyerteaApp&utm_medium=referral&utm_content=Android_TabBar";

        /**
         * 公司店铺首页https://kdt.im/N!Hzpr
         */
        public static final String HTML_STORE = "https://wap.youzan.com/v2/showcase/homepage?alias=23ht6s7n";


        /**
         * 广告，拼id
         */
        public static final String HTML_ADV = "http://www.flyertea.com/hot/mobileAdvis/?adid=";


        /**
         * 修改手机号
         */
        public static final String HTML_UPDATE_PHONE = "http://u.flyertea.com/bind/mobile/modify";

        /**
         * 绑定手机号
         */
        public static final String HTML_BIND_PHONE = "http://u.flyertea.com/bind/mobile";

        /**
         * 修改密码
         */
        public static final String HTML_UPDATE_PASSWORD = "http://u.flyertea.com/password/modify";

        /**
         * 绑定微信
         */
        public static final String HTML_BIND_WEIXIN = "http://u.flyertea.com/bind/weixinweb/set";
        /**
         * 绑定QQ
         */
        public static final String HTML_BIND_QQ = "http://u.flyertea.com/bind/qq/set";
        /**
         * 绑定微博
         */
        public static final String HTML_BIND_WEIBO = "http://u.flyertea.com/bind/weibo/set";

        /**
         * 飞米
         */
        public static final String HTML_MY_FEIMI = "http://u.flyertea.com/my/feimi.html";

        /**
         * 余额
         */
        public static final String HTML_MY_YUE = "http://u.flyertea.com/my/balance.html";

        /**
         * 飞客积分体系介绍
         */
        public static final String HTML_HOT_SCORE = "http://www.flyertea.com/hot/score/";

        /**
         * 飞客攻略电子 书
         */
        public static final String HTML_RAIDERS_BOOK="http://www.flyertea.com/forum.php?mod=collection&action=view&ctid=185&fromop=all&mobile=2";

    }
}
