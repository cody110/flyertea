package com.ideal.flyerteacafes.ui.fragment.interfaces;

import com.ideal.flyerteacafes.model.entity.CommunitySubDetailsBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubTypeBean;
import com.ideal.flyerteacafes.model.entity.TopThreadBean;

import java.util.List;

/**
 * Created by fly on 2016/11/29.
 */

public interface ICommunitySubFm{

    /**
     * 置顶帖
     * @param topThreadBeanList
     */
    void actionTopThread(List<TopThreadBean> topThreadBeanList);

    /**
     * 是否显示置顶那个按钮，大于3条显示
     * @param bol
     */
    void actionIsShowTopMoreBtn(boolean bol);

    /**
     * 设置当前版块信息
     * @param bean
     */
    void actionForumInfo(CommunitySubDetailsBean bean);

    /**
     * 版块类型
     * @param typeBeanList
     */
    void actionForumType(List<CommunitySubTypeBean> typeBeanList,String forumName);


    /**
     * 关注与否
     * @param isFav
     */
    void actionIsFavorite(boolean isFav);

    /**
     * 是否显示适配器的type分类
     * @param bol
     */
    void actionShowAdapterType(boolean bol);

    /**
     * 设置排序文字
     * @param sortStr
     */
    void actionSetTvSort(String sortStr);



}
