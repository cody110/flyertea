package com.ideal.flyerteacafes.ui.fragment.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;
import com.ideal.flyerteacafes.model.entity.MyFavoriteBean;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.CommunitySubActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.BasePresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.WriteThreadPresenter;
import com.ideal.flyerteacafes.ui.fragment.interfaces.ICommunityPlate;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * Created by fly on 2016/12/21.
 */

public class CommunityPlatePresenter extends BasePresenter<ICommunityPlate> {

    public static final String BUNDLE_FAVLIST_KEY = "favlist";
    public List<CommunityBean> communityBeanList = new ArrayList<>();
    private List<CommunitySubBean> communitySubBeanList = new ArrayList<>();
    public List<MyFavoriteBean> favList = new ArrayList<>();

    @Override
    public void init(Activity activity) {
        super.init(activity);
        EventBus.getDefault().register(this);

        favList.clear();
        favList.addAll(UserManger.getInstance().getFavList());

        requestCommunity();

    }


    /**
     * 设置第一次去关注版块
     */
    private void setIsFirstToChooseFav(List favList) {
        if (favList == null || favList.isEmpty()) {
            if (UserManger.isLogin()) {
                boolean bol = SharedPreferencesString.getInstances().isHavForumFavUserId(UserManger.getUserInfo().getMember_uid());
                if (!bol) {
                    SharedPreferencesString.getInstances().saveForumFavUserId(UserManger.getUserInfo().getMember_uid());
                    getView().actionToChooseFav();
                }
            }
        }
    }


    @Override
    public void detachView() {
        super.detachView();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 用户登录刷新版块数据
     */
    public void onEventMainThread(UserBean userBean) {
        requestCommunity();
    }

    /**
     * 退出登录
     *
     * @param msg
     */
    public void onEventMainThread(String msg) {
        if (msg.equals(FinalUtils.OUTLOGIN)) {
            communityBeanList.get(0).setSubforums(null);
            getView().actionSetOneLevelAdapter(communityBeanList);
        }
    }

    /**
     * 关注变化
     *
     * @param tagEvent
     */
    public void onEventMainThread(TagEvent tagEvent) {
        if (tagEvent.getTag() == TagEvent.FAV_LIST_CHANGE) {
            List<MyFavoriteBean> favList = (List<MyFavoriteBean>) tagEvent.getBundle().getSerializable("data");
            this.favList = favList;
            List<CommunitySubBean> subList = getMyFavToCommunitySubList(favList);
            communityBeanList.get(0).setSubforums(subList);
            getView().actionSetOneLevelAdapter(communityBeanList);

        } else if (tagEvent.getTag() == TagEvent.TAG_FAV_FOLLOW) {
            MyFavoriteBean bean = (MyFavoriteBean) tagEvent.getBundle().getSerializable("data");
            this.favList.add(bean);
            List<CommunitySubBean> subList = getMyFavToCommunitySubList(favList);
            communityBeanList.get(0).setSubforums(subList);
            getView().actionSetOneLevelAdapter(communityBeanList);
        } else if (tagEvent.getTag() == TagEvent.TAG_FAV_CANCLE) {
            MyFavoriteBean bean = (MyFavoriteBean) tagEvent.getBundle().getSerializable("data");
            for (MyFavoriteBean my : favList) {
                if (TextUtils.equals(my.getFavid(), bean.getFavid())) {
                    favList.remove(my);
                    break;
                }
            }
            List<CommunitySubBean> subList = getMyFavToCommunitySubList(favList);
            communityBeanList.get(0).setSubforums(subList);
            getView().actionSetOneLevelAdapter(communityBeanList);
        }
    }


    /**
     * @description: 获取社区版块数据
     * @author: Cindy
     * @date: 2016/11/15 17:20
     * @version V6.6.0
     */
    private void requestCommunity() {

        final FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_COMMUNITY_INDEX);
        params.setLoadCache(true);
        XutilsHttp.Get(params, new ListDataCallback(ListDataCallback.LIST_KEY_DATA, CommunityBean.class) {

            @Override
            public FlyRequestParams getRequestParams() {
                return params;
            }

            @Override
            public void flySuccess(ListDataBean response) {
                if (!isViewAttached()) return;
                if (response.getDataList() != null && response.getDataList().size() > 0) {
                    communityBeanList.clear();
                    communityBeanList.addAll(response.getDataList());
                    getView().actionSetOneLevelAdapter(communityBeanList);

                    setIsFirstToChooseFav(communityBeanList.get(0).getSubforums());
                }

            }
        });
    }

    /**
     * 点击一级版块
     *
     * @param position
     */
    public void actionClickOneLevel(int position) {
        communitySubBeanList.clear();
        if (communityBeanList.get(position).getSubforums() != null)
            communitySubBeanList.addAll(communityBeanList.get(position).getSubforums());
        if (position == 0 && communitySubBeanList.size() == 0) {
            getView().actionShowNormalAddFollow();
            getView().actionIsShowManagementPlate(false);
        } else {
            getView().actionIsShowManagementPlate(position == 0);
            if (communityBeanList.get(position).getAdv() != null) {
                communityBeanList.get(position).getAdv().put("name", communityBeanList.get(position).getName());
            }
            getView().actionSetTwoLevelAdapter(communitySubBeanList, communityBeanList.get(position).getAdv());
        }
    }


    /**
     * 点击二级版块
     *
     * @param position
     */
    public void actionClickTwoLevel(int position) {
        CommunitySubBean subBean = DataTools.getBeanByListPos(communitySubBeanList, position);
        if (subBean == null) return;
        String fid1 = null;
        String fname1 = null;

        for (CommunityBean bean1 : communityBeanList) {
            if (bean1.getSubforums() != null)
                for (CommunitySubBean bean2 : bean1.getSubforums()) {
                    if (TextUtils.equals(subBean.getFid(), bean2.getFid())) {
                        fid1 = bean1.getFid();
                        fname1 = bean1.getName();
                    }
                }
        }

        Bundle bundle = new Bundle();
        bundle.putString(WriteThreadPresenter.BUNDLE_FID_1, fid1);
        bundle.putString(WriteThreadPresenter.BUNDLE_NAME_1, fname1);
        bundle.putString(WriteThreadPresenter.BUNDLE_FID_2, communitySubBeanList.get(position).getFid());
        getBaseView().jumpActivity(CommunitySubActivity.class, bundle);
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
                for (CommunityBean bean1 : communityBeanList) {
                    if (!TextUtils.equals(bean1.getName(), "我的关注"))//去重
                        if (bean1.getSubforums() != null && !bean1.getSubforums().isEmpty()) {
                            for (CommunitySubBean bean2 : bean1.getSubforums()) {
                                if (TextUtils.equals(info.getId(), bean2.getFid())) {
                                    sublist.add(bean2);
                                }
                            }
                        }
                }
            }
        }
        return sublist;
    }

}
