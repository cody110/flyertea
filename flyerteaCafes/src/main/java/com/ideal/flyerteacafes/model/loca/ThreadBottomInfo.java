package com.ideal.flyerteacafes.model.loca;

import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by fly on 2016/11/26.
 */

public class ThreadBottomInfo {

    /**
     * 分享，收藏，删除,举报
     */
    public static final int TYPE_SHARE=1,TYPE_COLLECT=2,TYPE_DELETE=3,TYPE_JUBAO=4;

    /**
     * 标题
     */
    private String title;
    /**
     * 图片id
     */
    private int resId;
    /**
     * 类型
     */
    private int type;
    /**
     * 为分享才有
     */
    private SHARE_MEDIA platform;

    public ThreadBottomInfo(String title, int resId, int type, SHARE_MEDIA platform) {
        this.title = title;
        this.resId = resId;
        this.type = type;
        this.platform = platform;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public SHARE_MEDIA getPlatform() {
        return platform;
    }

    public void setPlatform(SHARE_MEDIA platform) {
        this.platform = platform;
    }
}
