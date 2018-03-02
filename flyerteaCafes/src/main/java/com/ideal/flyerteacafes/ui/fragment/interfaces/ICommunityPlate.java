package com.ideal.flyerteacafes.ui.fragment.interfaces;

import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;

import java.util.List;
import java.util.Map;

/**
 * Created by fly on 2016/12/21.
 */

public interface ICommunityPlate {

    /**
     * 一级适配器
     *
     * @param communityBeanList
     */
    void actionSetOneLevelAdapter(List<CommunityBean> communityBeanList);

    /**
     * 二级适配器
     *
     * @param communitySubBeanList
     */
    void actionSetTwoLevelAdapter(List<CommunitySubBean> communitySubBeanList,Map<String, String> adv);

    /**
     * 用户未登陆或者已登录但未关注任何版块时，显示引导添加版块图标
     */
    void actionShowNormalAddFollow();

    /**
     * 是否显示版块管理
     */
    void actionIsShowManagementPlate(boolean bol);

    /**
     * 第一次去选择关注版块
     */
    void actionToChooseFav();


}
