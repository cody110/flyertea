package com.ideal.flyerteacafes.model;

import android.os.Bundle;

/**
 * Created by fly on 2016/3/21.
 */
public class TagEvent {


    // TODO 直播列表刷新
    public static final int TAG_ZHIBO_LIST_FEFRESH = 1;

    // TODO 添加朋友成功
    public static final int TAG_ADD_FRIEND_SUCCESS = 2;

    // TODO 社区帖子列表刷新
    public static final int TAG_PLATE_TOPIC_LIST_FEFRESH = 3;

    // TODO 关注论坛版块状态改变
    public static final int FAV_LIST_CHANGE = 4;

    // TODO 取消版块关注
    public static final int TAG_FAV_CANCLE = 5;

    // TODO 关注成功
    public static final int TAG_FAV_FOLLOW = 6;

    //TODO 按发帖按回复改变
    public static final int TAG_POST_SORT_CHANGE = 7;

    //TODO 阅读记录改变，刷新adapter
    public static final int TAG_YUE_CHANGE = 8;
    //TODO 头像改变本地
    public static final int TAG_FACE_CHANGE = 9;
    //TODO 关闭发文字帖
    public static final int TAG_FINISH_WRITETHREADACTIVITY = 10;
    //TODO tab选中报告
    public static final int TAG_TAB_CHOOSE_REPORT = 11;
    //TODO 修改读帖tab顺序
    public static final int TAG_TAB_SORT_CHANGE = 12;
    //TODO 添加新标签
    public static final int TAG_TAB_ADD_TAG = 13;
    //分享勋章
    public static final int TAG_SHARE_MEDALS = 14;
    //申请勋章
    public static final int TAG_SHENGQING_MEDALS = 15;
    //航班号
    public static final int TAG_FLIGHT = 16;
    //申请勋章有效期
    public static final int TAG_SHENGQING_MEDAILS_TIME = 17;
    //生日选择完成
    public static final int TAG_BirthDay = 18;
    //生日设置成功
    public static final int TAG_BIRTHDAY_SUCCESS = 19;
    //我的会员/任务 返回刷新任务数据
    public static final int TAG_TASK_BACK = 20;
    //帖子搜索排序
    public static final int TAG_SEARCH_SORT = 21;
    //帖子搜索筛选板块
    public static final int TAG_SEARCH_FORUM = 22;
    //取消视频发送
    public static final int TAG_CANCLE_SEND_VIDEO=23;
    //视频选中
    public static final int TAG_VIDEO_LIST_SELECT=24;


    private int tag = -1;

    public TagEvent(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    private Bundle bundle;

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
