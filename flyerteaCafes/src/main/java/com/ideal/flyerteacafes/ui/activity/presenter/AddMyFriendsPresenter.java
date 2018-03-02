package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;

import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.NotificationBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.IAddMyFriends;
import com.ideal.flyerteacafes.ui.dialog.CustomDialog;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

/**
 * Created by Cindy on 2017/3/20.
 */
public class AddMyFriendsPresenter extends PullRefreshPresenter<NotificationBean> {

    SharedPreferencesString preferences;

    /**
     * 页面回调接口
     */
    IAddMyFriends iAddMyFriends;

    public void setiAddMyFriends(IAddMyFriends i) {
        this.iAddMyFriends = i;
    }

    @Override
    protected void getIntentDatas(Intent intent) {
        super.getIntentDatas(intent);
        preferences = SharedPreferencesString.getInstances();
    }

    @Override
    public void init(Activity activity) {
        super.init(activity);

    }

    @Override
    public void requestDatas() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_ADD_MY_FRIENDS);
        params.addQueryStringParameter("page", String.valueOf(page));
        params.addQueryStringParameter("perpage", String.valueOf(perpage));
        XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.LIST_KEY_NOTIFICATION, NotificationBean.class));
    }

    /**
     * 同意添加好友
     *
     * @param uid
     */
    public void agreeAddFriends(final String uid) {
        getDialog().proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_AGREEAddFRIENDS);
        params.addQueryStringParameter("uid", uid);
        params.addBodyParameter("add2submit", "true");
        params.addBodyParameter("formhash", preferences.getStringToKey("formhash"));
        params.addBodyParameter("gid", "6");
        params.addBodyParameter("handlekey", "afr_" + uid);

        XutilsHttp.Post(params, new PStringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                iAddMyFriends.callbackAgreeAddFriends(result);
            }

        });
    }

    /**
     * 拒绝添加好友
     *
     * @param uid
     */
    public void ignoreAddFriends(String uid) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTPP_IGNOREADDFRIENDS);
        params.addQueryStringParameter("uid", uid);
        params.addBodyParameter("formhash", preferences.getStringToKey("formhash"));
        params.addBodyParameter("friendsubmit", "true");
        params.addBodyParameter("handlekey", "afi_" + uid);
        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                iAddMyFriends.callbackIgnoreAddFriends(result);
            }
        });
    }
}
