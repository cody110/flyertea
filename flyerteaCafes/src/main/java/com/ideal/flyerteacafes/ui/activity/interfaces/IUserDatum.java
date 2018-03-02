package com.ideal.flyerteacafes.ui.activity.interfaces;

import com.ideal.flyerteacafes.model.entity.MyThreadBean;
import com.ideal.flyerteacafes.model.entity.Userinfo;

import java.util.List;

/**
 * Created by Cindy on 2016/12/22.
 */
public interface IUserDatum {

    /**
     * 返回用户信息
     *
     * @param userinfo
     */
    void callbackUserInfo(Userinfo userinfo);

    /**
     * 删除好友
     */
    void callbackDeleteFriend();

    /**
     * 帖子列表
     *
     * @param myThreadBeanList
     */
    void callbackTopicList(List<MyThreadBean> myThreadBeanList,String type);


    /**
     * 添加底部
     */
    void addFooterView();

    /**
     * 移除底部
     */
    void removeFooterView();
}
