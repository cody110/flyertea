package com.ideal.flyerteacafes.ui.activity.interfaces;

import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubTypeBean;
import com.ideal.flyerteacafes.model.entity.LocationListBean;

import java.util.List;

/**
 * Created by fly on 2016/11/14.
 */

public interface IWriteThread {


    /**
     * 是否保存草稿
     */
    void showDialogIsSaveDraft();

    /**
     * 設置標題 正文
     *
     * @param title
     * @param content
     */
    void setTvTitleContent(String title, String content,String location,String topicName);

    /**
     * 评分绑定
     */
    void bindRatingLayout(LocationListBean.LocationBean locationBean, float star);


}
