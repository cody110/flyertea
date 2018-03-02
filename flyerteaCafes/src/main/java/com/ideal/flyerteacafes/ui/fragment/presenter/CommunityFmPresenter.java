package com.ideal.flyerteacafes.ui.fragment.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.ideal.flyerteacafes.caff.ForumDataManger;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.ThreadSubjectBean;
import com.ideal.flyerteacafes.model.entity.MyFavoriteBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.fragment.interfaces.ICommunity;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.List;

/**
 * Created by Cindy on 2016/11/18.
 */
public class CommunityFmPresenter extends PullRefreshPresenter<ThreadSubjectBean> {

    public static final String VIEW_NEWPOST = "newpost", VIEW_HOT = "hot", VIEW_DIGEST = "digest";
    public static final int REQUEST_CHOOSECOMMUNITYACTIVITY = 1;

    private String mfav = "";//关注
    private String view = "newpost";//默认最新发帖
    private ICommunity iCommunity;//回调接口
    public boolean isLoadFav = false;//是否加载了关注


    @Override
    public void init(Activity activity) {
        if (UserManger.isLogin()) {
            requestFavorite();
        } else {
            iCommunity.actionIsFav(false);
            getView().headerRefreshing();
        }
    }

    /**
     * 是否选择关注
     *
     * @param bol
     */
    public void actionSelectFav(boolean bol) {
        if (bol)
            mfav = "1";
        else
            mfav = "";
        page = 1;
    }

    /**
     * 选择最新 热门精华
     */
    public void actionSelectStatus(String view) {
        this.view = view;
        page = 1;
    }

    /**
     * 将回调接口传递到fragment
     *
     * @param iCommunity
     */
    public void getIcommunity(ICommunity iCommunity) {
        this.iCommunity = iCommunity;
    }


    /**
     * 社区首页列表数据
     */
    @Override
    public void requestDatas() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_COMMUNITY_TOPIC_LIST);
        params.addQueryStringParameter("myfav", mfav);
        params.addQueryStringParameter("view", view);
        params.addQueryStringParameter("page", page + "");
        params.addQueryStringParameter("version", "5");
        XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.LIST_KEY_DATA_THREADS, ThreadSubjectBean.class));
    }

    /**
     * 我的关注
     */
    public void requestFavorite() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_FAVORITE);
        XutilsHttp.Get(params, new PListDataCallback(ListDataCallback.LIST_KEY_MY_FAOORITE, MyFavoriteBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (!isViewAttached()) return;
                isLoadFav = true;
                List<MyFavoriteBean> listFav = result.getDataList();
                if (listFav != null && !listFav.isEmpty()) {
                    for (int i = listFav.size() - 1; i >= 0; i--) {
                        if (TextUtils.equals(listFav.get(i).getId(), "0")) {
                            listFav.remove(i);
                        }
                    }
                }

                if (listFav != null && !listFav.isEmpty()) {
                    actionSelectFav(true);
                    iCommunity.actionIsFav(true);
                    ForumDataManger.getInstance().setMyFavToCommunitySubList(listFav);
                } else {
                    actionSelectFav(false);
                    iCommunity.actionIsFav(false);
                    //用户未关注任何版块并且第一次进入社区逻辑
                    if (UserManger.isLogin()) {
                        boolean bol = SharedPreferencesString.getInstances().isHavForumFavUserId(UserManger.getUserInfo().getMember_uid());
                        if (!bol) {
                            SharedPreferencesString.getInstances().saveForumFavUserId(UserManger.getUserInfo().getMember_uid());
                            iCommunity.actionToChooseFav();
                        }
                    }
                }
                getView().headerRefreshing();
            }

            @Override
            public void flyError() {
                super.flyError();
                getView().headerRefreshing();
            }
        });
    }

}
