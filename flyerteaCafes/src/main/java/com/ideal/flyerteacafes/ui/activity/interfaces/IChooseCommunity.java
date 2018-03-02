package com.ideal.flyerteacafes.ui.activity.interfaces;

import com.ideal.flyerteacafes.model.entity.CommunityBean;

import java.util.List;

/**
 * Created by Cindy on 2016/11/15.
 */
public interface IChooseCommunity {

    /**
     * 社区版块
     * @param communityBeanList
     */
    void  callbackCommunity(List<CommunityBean> communityBeanList);

    /**
     * 论坛首页
     */
    void actionToCommunityFm();
}
