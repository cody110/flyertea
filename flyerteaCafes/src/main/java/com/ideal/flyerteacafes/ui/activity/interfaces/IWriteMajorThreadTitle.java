package com.ideal.flyerteacafes.ui.activity.interfaces;

import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubTypeBean;

import java.util.List;

/**
 * Created by fly on 2017/3/10.
 */

public interface IWriteMajorThreadTitle {

    /**
     * 设置发帖版块
     *
     * @param name
     */
    void actionSetPostForum(String name);

    /**
     * 选择论坛版块
     *
     * @param communityBeanList
     */
    void actionChooseAllFroum(List<CommunityBean> communityBeanList);

    void actionChooseThreeFroum(List<CommunitySubTypeBean> communitySubTypeBeanList);

    void actionChooseWenda(List<CommunityBean> communityBeanList);

    /**
     * 是否显示选择关联酒店的view
     *
     * @param bol
     */
    void actionIsShowHotelView(boolean bol);


    /**
     * 设置关联酒店的名称
     *
     * @param hotelName
     */
    void setTvHotelName(String hotelName);


}
