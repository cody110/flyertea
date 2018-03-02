package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;

import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.MyThreadBean;
import com.ideal.flyerteacafes.model.entity.Userinfo;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.IUserDatum;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cindy on 2016/12/22.
 */
public class UserDatumPresenter extends BasePresenter<IUserDatum> {

    private String uid;
    private Userinfo info;
    private String formhash;
    protected int page = 1;
    protected int perpage = 10;//每页的条数
    public static final String TYPE_TOPIC = "userthread", TYPE_REPLY = "userreply";
    public String type = "userthread";//默认他的帖子
    public List<MyThreadBean> myThreadBeanList = new ArrayList<>();
    private boolean hasNext = false;

    public List<MyThreadBean> getMyThreadBeanList() {
        return myThreadBeanList;
    }

    @Override
    public void init(Activity activity) {
        super.init(activity);
        uid = activity.getIntent().getStringExtra("uid");
        requestFriend(uid);
        requestHisTopic(uid);
    }

    /**
     * 获取用户数据
     *
     * @param uid
     */
    private void requestFriend(String uid) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_PROFILE);
        params.addQueryStringParameter("uid", uid);
        XutilsHttp.Get(params, new DataCallback<Userinfo>(DataCallback.DATA_USER) {
            @Override
            public void flySuccess(DataBean<Userinfo> result) {
                if (!isViewAttached()) return;
                info = result.getDataBean();
                getView().callbackUserInfo(info);
            }
        });
    }

    /**
     * 选择最新 热门精华
     */
    public void actionSelectStatus(String type) {
        this.type = type;
        page = 1;
    }


    public void headerRefresh() {
        page = 1;
        requestHisTopic(uid);
    }

    public void footerRefresh() {
        if (hasNext) {
            page++;
            requestHisTopic(uid);
        }
    }

    /**
     * 获取他的帖子或回复数据
     */
    private void requestHisTopic(String uid) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_HIS_TOPIC);
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("uid", uid);
        params.addQueryStringParameter("page", page + "");
        params.addQueryStringParameter("perpage", perpage + "");
        XutilsHttp.Get(params, new PListDataCallback(ListDataCallback.LIST_KEY_DATA_THREADS, MyThreadBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (!isViewAttached()) return;
                hasNext = result.getHasNext();
                if (hasNext) {
                    getView().addFooterView();
                } else {
                    getView().removeFooterView();
                }
                if (result.getDataList() != null) {
                    if (page == 1)
                        myThreadBeanList.clear();
                    myThreadBeanList.addAll(result.getDataList());
                    getView().callbackTopicList(myThreadBeanList, type);
                }
            }
        });
    }

    public void deleteFriend() {
        getDialog().proDialogShow();
        formhash = SharedPreferencesString.getInstances(context).getStringToKey("formhash");
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_DETELEFRIEND);
        params.addQueryStringParameter("uid", uid);
        params.addBodyParameter("formhash", formhash);
        params.addBodyParameter("friendsubmit", "true");
        params.addBodyParameter("handlekey", "a_ignore_" + uid);
        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                BaseBean bean = JsonAnalysis.getMessageBean(result);
                if (bean == null) {
                    ToastUtils.showToast("删除失败,请稍后再试!");
                    return;
                }
                ToastUtils.showToast(bean.getMessage());
                if (bean.getCode().equals("do_success")) {
                    getView().callbackDeleteFriend();
                }
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                getDialog().proDialogDissmiss();
            }
        });
    }
}
