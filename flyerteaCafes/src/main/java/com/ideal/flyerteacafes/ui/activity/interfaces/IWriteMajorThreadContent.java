package com.ideal.flyerteacafes.ui.activity.interfaces;

import com.ideal.flyerteacafes.model.entity.LocationListBean;
import com.ideal.flyerteacafes.model.loca.TuwenInfo;

import java.util.List;

/**
 * Created by fly on 2017/3/10.
 */

public interface IWriteMajorThreadContent {

    /**
     * 绑定正文内容
     *
     * @param
     */
    void bindAdapter(List<TuwenInfo> tuwenInfoList);

    /**
     * 删除确认提示框
     */
    void showDeleteRemindDialog(int pos);

    /**
     * 图片上传进度
     *
     * @param allCount
     * @param successCount
     */
    void uploadProgress(int allCount, int successCount, int errorCount);


    /**
     * 帖子正文内容
     *
     * @param countText
     */
    void threadCountText(String countText, String attachId,String vids);

    void threadTitleSubjectLocation(String title, String subject, String location, String topicName);

}
