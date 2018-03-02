package com.ideal.flyerteacafes.ui.interfaces;

import android.os.Bundle;

import com.ideal.flyerteacafes.model.entity.CommentBean;
import com.ideal.flyerteacafes.model.entity.ThreadTagBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.IBase;

import java.util.List;

/**
 * Created by fly on 2016/11/21.
 */

public interface IThread extends IBase{


    void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl);

    void webviewLoadUrl(String str);

    void settingView(Object invitationBean);

    void showCommentDialog(Bundle bundle);

    void showShareDialog(Bundle bundle);

    void isConfirmDeleteThread();

    void showDeleteCommentDialog();

    void closeCommentDialog();

    void showCommentSortPop();

    void showCommentPage(int page,int pageAll);

    void showMoreDialog(int pos, CommentBean commentBean);

    void showTagsMoreDialog(List<ThreadTagBean> threadTagBeanList);


    /**
     * 实名认证提示
     */
    void showAuthodDialog();

    /**
     * 送花三次出提示
     */
    void showFlowerThreeDialog(String title,String msg);


    /**
     * 去播放视频
     * @param videoUrl
     */
    void toPlayVideo(String videoUrl);

}
