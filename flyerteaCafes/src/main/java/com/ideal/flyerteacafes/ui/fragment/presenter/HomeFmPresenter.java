package com.ideal.flyerteacafes.ui.fragment.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.StartUpManger;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.AdvertBean;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.HomeIconSubBean;
import com.ideal.flyerteacafes.model.entity.InterlocutionBean;
import com.ideal.flyerteacafes.model.entity.PostBean;
import com.ideal.flyerteacafes.model.entity.UpgradeBean;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.HomeFmBtnInfo;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.InterlocutionActivity;
import com.ideal.flyerteacafes.ui.activity.ExerciseActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.BasePresenter;
import com.ideal.flyerteacafes.ui.fragment.interfaces.IHomeFm;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.SmartUtil;
import com.ideal.flyerteacafes.utils.TaskUtil;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DateUtil;
import com.ideal.flyerteacafes.utils.tools.Tools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2016/5/31.
 */
public class HomeFmPresenter extends BasePresenter<IHomeFm> {

    Activity activity;
    private List<ArticleTabBean> postBeanList = new ArrayList<>();
    public List<AdvertBean> advertList = new ArrayList<>();
    private List<InterlocutionBean> interlocutionBeanList = new ArrayList<>();
    private int page = 1;

    @Override
    public void init(Activity activity) {
        super.init(activity);
        this.activity = activity;
        requestAdvert();
        requestVersion();
    }

    /**
     * 上拉加载更多
     */
    public void onFooterRefresh() {
        page++;
        requestPost();
    }

    /**
     * 下拉刷新数据
     */
    public void onHeaderRefresh() {
        requestHotInteraction();
        page = 1;
        requestPost();
    }


    /**
     * 请求广告数据
     */
    private void requestAdvert() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_AD), new PListDataCallback(ListDataCallback.LIST_KEY_ADV, AdvertBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (!isViewAttached()) return;
                advertList.clear();
                if (result.getDataList() != null)
                    advertList.addAll(result.getDataList());
                getView().callbackAdvertList(advertList);
            }
        });
    }

    /**
     * 热门互动
     */
    private void requestHotInteraction() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_HOT_INTERACTION), new ListDataCallback(ListDataCallback.LIST_KEY_INTERLOCUTION, InterlocutionBean.class) {
            @Override
            public void flySuccess(ListDataBean response) {
                if (isViewAttached()) {
                    if (response.getDataList() != null && response.getDataList().size() > 0) {
                        interlocutionBeanList.clear();
                        interlocutionBeanList.addAll(response.getDataList());
                        getView().callbackInterlocution(interlocutionBeanList);
                    } else {
                        getView().hintInterlocutionView();
                    }
                }
            }


        });
    }

    /**
     * 推荐帖子
     */
    private void requestPost() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_REPORT_ALL);
        params.addQueryStringParameter("perpage", "5");
        params.addQueryStringParameter("page", page + "");
        XutilsHttp.Get(params, new ListDataCallback(ListDataCallback.LIST_KEY_ARTICLE, ArticleTabBean.class) {
            @Override
            public void flySuccess(ListDataBean response) {
                if (isViewAttached()) {
                    int oldSize = postBeanList.size();
                    if (page == 1)
                        postBeanList.clear();
                    if (!DataUtils.isEmpty(response.getDataList())) {
                        if (response.getDataList().size() > 5) {
                            postBeanList.addAll(response.getDataList().subList(0, 5));
                        } else {
                            postBeanList.addAll(response.getDataList());
                        }
                    }
                    getView().callbackPost(postBeanList);
                    if (page > 1) {
                        getView().footerRefreshSetListviewScrollLocation(oldSize + 1);
                    }
                }
                getView().pullToRefreshViewComplete();
            }
        });
    }

    /**
     * 升级接口
     */
    private void requestVersion() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_UPGRADE);
        params.addQueryStringParameter("appversion", Tools.getVersion(context));
        params.addQueryStringParameter("app", "flyertea");
        XutilsHttp.Get(params, new PDataCallback<UpgradeBean>() {
            @Override
            public void flySuccess(DataBean<UpgradeBean> result) {
                if (!isViewAttached()) return;
                if (result.getDataBean() == null) return;

                String time = "remindUpgradeTime", count = "remindUpgradeCount";
                if (TextUtils.equals(result.getDataBean().getHasupdate(), "1")) {
                    if (TextUtils.equals(result.getDataBean().getForceupdate(), "1")) {//TODO 强制更新
                        getView().showUpgradeDialog(result.getDataBean());
                    } else {
                        if (SharedPreferencesString.getInstances().getIntToKey(count) < 3) {
                            if (DataUtils.isEmpty(SharedPreferencesString.getInstances().getStringToKey(time)) || DateUtil.daysBetween(SharedPreferencesString.getInstances().getStringToKey(time), String.valueOf(DateUtil.getDateline())) > 0) {
                                getView().showUpgradeDialog(result.getDataBean());
                                SharedPreferencesString.getInstances().savaToInt(count, SharedPreferencesString.getInstances().getIntToKey(count) + 1);
                                SharedPreferencesString.getInstances().savaToString(time, DateUtil.getDateline());
                                SharedPreferencesString.getInstances().commit();
                            }
                        }
                    }

                } else {
                    SharedPreferencesString.getInstances().savaToInt(count, 0);
                    SharedPreferencesString.getInstances().savaToString(time);
                    SharedPreferencesString.getInstances().commit();
                }
            }
        });
    }

}
