package com.ideal.flyerteacafes.caff;

import android.text.TextUtils;

import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;
import com.ideal.flyerteacafes.model.entity.MyFavoriteBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.presenter.BasePresenter;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2016/11/28.
 * 版块基础数据管理类
 */

public class ForumDataManger {

    private static ForumDataManger manger;

    public static ForumDataManger getInstance() {
        if (manger == null) {
            manger = new ForumDataManger();
        }
        return manger;
    }


    private ForumDataManger() {
    }


    /**
     * 版块数据
     */
    private List<CommunityBean> forumList = new ArrayList<>();

    /**
     * 我的关注
     */
    private List<MyFavoriteBean> listFav = new ArrayList<>();
    ;

    public List<CommunityBean> getData() {
        return forumList;
    }


    /**
     * 全局初始化
     */
    public void init() {
        requestCommunity();
    }

    /**
     * 登录
     */
    public void login() {
        requestCommunity();
    }

    /**
     * 退出登录
     */
    public void loginOut() {
        requestCommunity();
        listFav.clear();
    }


    /**
     * @description: 获取社区版块数据
     * @author: Cindy
     * @date: 2016/11/15 17:20
     * @version V6.6.0
     */
    private void requestCommunity() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_COMMUNITY_INDEX), new ListDataCallback(ListDataCallback.LIST_KEY_DATA, CommunityBean.class) {
            @Override
            public void flySuccess(ListDataBean response) {
                if (response.getDataList() != null && response.getDataList().size() > 0) {
                    forumList.clear();
                    forumList.addAll(response.getDataList());
                }
            }
        });
    }


    /**
     * 根据fid获取版块名称
     *
     * @param fid
     * @return
     */
    public String getForumName(String fid) {
        for (CommunityBean bean : forumList) {
            if (TextUtils.equals(fid, bean.getFid())) {
                return bean.getName();
            }
            if (bean.getSubforums() != null) {
                for (CommunitySubBean subBean : bean.getSubforums()) {
                    if (TextUtils.equals(fid, subBean.getFid())) {
                        return bean.getName();
                    }
                }
            }
        }
        return "";
    }

    /**
     * 通过二级版块id 获取一级版块信息
     *
     * @return
     */
    public CommunityBean getCommunityBeanByFid2(String fid2) {
        for (CommunityBean bean : forumList) {
            if (bean.getSubforums() != null) {
                for (CommunitySubBean subBean : bean.getSubforums()) {
                    if (TextUtils.equals(fid2, subBean.getFid())) {
                        return bean;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取我关注的版块
     *
     * @return
     */
    public List<CommunitySubBean> getMyFavToCommunitySubList() {
        return getMyFavToCommunitySubList(listFav);
    }

    /**
     * 设置我的关注数据
     *
     * @param listFav
     */
    public void setMyFavToCommunitySubList(List<MyFavoriteBean> listFav) {
        this.listFav = listFav;
    }

    /**
     * 通过关注id，遍历出版块内容
     *
     * @param favList
     * @return
     */
    private List<CommunitySubBean> getMyFavToCommunitySubList(List<MyFavoriteBean> favList) {
        List<CommunitySubBean> sublist = new ArrayList<>();
        if (favList != null && !favList.isEmpty()) {
            for (MyFavoriteBean info : favList) {
                for (CommunityBean bean1 : forumList) {
                    for (CommunitySubBean bean2 : bean1.getSubforums()) {
                        if (TextUtils.equals(info.getId(), bean2.getFid())) {
                            sublist.add(bean2);
                        }
                    }
                }
            }
        }
        return sublist;
    }

}
