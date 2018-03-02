package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.CommunityBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubBean;
import com.ideal.flyerteacafes.model.entity.FollowInfo;
import com.ideal.flyerteacafes.model.entity.MyFavoriteBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.IChooseCommunity;
import com.ideal.flyerteacafes.ui.fragment.interfaces.ICommunity;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Cindy on 2016/11/15.
 */
public class ChooseCommunityPresenter extends BasePresenter<IChooseCommunity> {

    private List<CommunityBean> communityBeanList = new ArrayList<>();
    /**
     * 第一次进入，点击back键默认关注的版块id
     */
    private String def_follow_fid = "23,110,59";


    @Override
    public void init(Activity activity) {
        super.init(activity);
        requestCommunity();
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
                if (isViewAttached()) {
                    if (response.getDataList() != null && response.getDataList().size() > 0) {
                        communityBeanList.clear();
                        if (!DataUtils.isEmpty(response.getDataList())) {
                            response.getDataList().remove(0);
                        }
                        communityBeanList.addAll(response.getDataList());

                        for (CommunityBean bean : communityBeanList) {
                            if (bean.getSubforums() != null) {
                                for (CommunitySubBean subbean : bean.getSubforums()) {
                                    if (TextUtils.equals(subbean.getFid(), "23") || TextUtils.equals(subbean.getFid(), "110")
                                            || TextUtils.equals(subbean.getFid(), "59")) {
                                        subbean.setIsSelect(true);
                                    } else {
                                        subbean.setIsSelect(false);
                                    }
                                }
                            }
                        }

                        getView().callbackCommunity(communityBeanList);
                    }
                }
            }
        });
    }

    /**
     * 第一默认关注
     */
    public void actionFirstDefFollow() {
        requestFollow(def_follow_fid);
    }


    /**
     * 点击完成
     */
    public void actionOk() {
        String fids = getFavFid();
        if (TextUtils.isEmpty(fids)) {
            ToastUtils.showToast("至少选择一个版块");
        } else {
            requestFollow(fids);
        }
    }

    /**
     * 得到选择的fid
     *
     * @return
     */
    private String getFavFid() {
        String result = null;
        StringBuffer sb = new StringBuffer();
        for (CommunityBean bean : communityBeanList) {
            List<CommunitySubBean> subBeanList = bean.getSubforums();
            if (subBeanList != null) {
                for (CommunitySubBean subBean : subBeanList) {
                    if (subBean.isSelect()) {
                        sb.append(subBean.getFid());
                        sb.append(",");
                    }
                }
            }
        }
        if (sb.length() > 0) {
            result = sb.substring(0, sb.length() - 1);
        }
        return result;
    }


    /**
     * 关注
     */
    public void requestFollow(String fids) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_FORUMACTION);
        params.addQueryStringParameter("action", "followmulti");
        params.addQueryStringParameter("fid", fids, false);
        params.addQueryStringParameter("version", "5");
        getDialog().proDialogShow();

        XutilsHttp.Get(params, new PListDataCallback(ListDataCallback.DATA, MyFavoriteBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (!isViewAttached()) return;
                if (JsonAnalysis.isSuccessEquals1By2(result.getData())) {
                    getView().actionToCommunityFm();
                    List<MyFavoriteBean> list = result.getDataList();
                    if (list != null && !list.isEmpty()) {
                        TagEvent tagEvent = new TagEvent(TagEvent.FAV_LIST_CHANGE);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", (Serializable) list);
                        tagEvent.setBundle(bundle);
                        EventBus.getDefault().post(tagEvent);
                    }
                }
                ToastUtils.showToast(result.getMessage());
            }
        });

    }


}
