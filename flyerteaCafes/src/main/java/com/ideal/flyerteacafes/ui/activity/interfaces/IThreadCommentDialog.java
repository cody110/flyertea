package com.ideal.flyerteacafes.ui.activity.interfaces;

/**
 * Created by fly on 2016/11/24.
 */

public interface IThreadCommentDialog {

    /**
     * 去图片评论页面
     */
    void actionToPictureComment(String commentContent);

    /**
     * 发送评论
     *
     * @param msg
     */
    void actionSendComment(String msg);


}
