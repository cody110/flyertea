package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UmengLogin;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.caff.YueManger;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.dal.BaseHelper;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.FriendsInfo;
import com.ideal.flyerteacafes.model.entity.LoginBase;
import com.ideal.flyerteacafes.model.entity.LoginCodeResponse;
import com.ideal.flyerteacafes.model.entity.MyFavoriteBean;
import com.ideal.flyerteacafes.model.entity.MyFrirends;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.ILoginVideo;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHelp;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/6/2.
 */

public class LoginVideoPresenter extends BasePresenter<ILoginVideo> {


    private static final int selectPhone = 1, selectUserName = 2;
    private int selectStatus = selectPhone;
    private String logintype, openid, accesstoken, nickname, faceUrl;

    @Override
    public void init(Activity activity) {
        super.init(activity);
    }


    /**
     * 获取登录验证码
     */
    public void requestGetCode(String mobile_no) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_PHONE_CODE);
        params.addQueryStringParameter("mobile_no", mobile_no);
        params.addQueryStringParameter("type", "login");
        XutilsHttp.Get(params, new PDataCallback<LoginCodeResponse>(DataCallback.DATA_KEY_GET_CODE) {
            @Override
            public void flySuccess(DataBean<LoginCodeResponse> result) {
                if (!isViewAttached()) return;
                if (result.getDataBean() == null) return;
                getView().callbackGetCOdeData(result.getDataBean());
            }
        });
    }

    /**
     * 手机号登录
     */
    protected void mobileLogin(String mobile, String code) {
        getDialog().proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_LOG_MOBILE);
        params.addBodyParameter("username", mobile);
        params.addBodyParameter("code", code);
        // 激光推送设备ＩＤ
        String registrationID = JPushInterface.getRegistrationID(FlyerApplication.getContext());
        params.addBodyParameter("Registrationid", registrationID);
        LogFly.e("Registrationid:" + registrationID);
        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                if (JsonAnalysis.isSuccessEquals1(result)) {
                    LoginBase loginBase = JsonAnalysis.getBean(result, LoginBase.class);
                    loginInit(loginBase);
                } else {
                    ToastUtils.showToast(JsonAnalysis.getMessage(result));
                    getDialog().proDialogDissmiss();
                }
            }
        });
    }


    /**
     * 用户名登陆
     *
     * @param username
     * @param password
     */
    private void usernameLogin(String username, String password) {
        getDialog().proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_login);
        params.addQueryStringParameter("transcode", "yes");
        params.addBodyParameter("username", username);
        params.addBodyParameter("password", password);

        // 激光推送设备ＩＤ
        String RegistrationID = JPushInterface.getRegistrationID(FlyerApplication.getContext());
        params.addBodyParameter("Registrationid", RegistrationID);
        LogFly.e("Registrationid:" + RegistrationID);
        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                if (JsonAnalysis.isSuccessEquals1(result)) {
                    LoginBase loginBase = JsonAnalysis.getBean(result, LoginBase.class);
                    loginInit(loginBase);
                } else {
                    ToastUtils.showToast(JsonAnalysis.getMessage(result));
                    getDialog().proDialogDissmiss();
                }
            }
        });
    }


    /**
     * 获取用户数据成功，初始化用户相关信息
     *
     * @param loginBase
     */
    public void loginInit(LoginBase loginBase) {
        if (loginBase == null) return;
        UserBean bean = loginBase.getVariables();
        if (DataUtils.loginIsOK(loginBase.getMessage().getCode())) {
//            DataUtils.saveLogininfo(bean);
            SharedPreferencesString.getInstances().saveUserinfo(bean);
            XutilsHelp.saveCookie();
            YueManger.getInstance().initUserReadIds();
            requestFriends();
            //TOTD 登录完成获取我的关注
            requestFavorite();
        } else {
            ToastUtils.showToast(loginBase.getMessage().getMessage());
        }

    }

    public void setSelectPhone() {
        selectStatus = selectPhone;
    }

    public void setSelectUserName() {
        selectStatus = selectUserName;
    }


    public void requestLogin(String username, String password) {
        if (selectStatus == selectPhone) {
            if (TextUtils.isEmpty(username)) {
                ToastUtils.showToast("请输入手机号");
            } else if (TextUtils.isEmpty(password)) {
                ToastUtils.showToast("请输入验证码");
            } else {
                mobileLogin(username, password);
            }
        } else {
            if (TextUtils.isEmpty(username)) {
                ToastUtils.showToast("请输入用户名");
            } else if (TextUtils.isEmpty(password)) {
                ToastUtils.showToast("请输入密码");
            } else {
                usernameLogin(username, password);
            }
        }
    }


    /**
     * 好友列表请求
     **/
    private void requestFriends() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_FRIENDS);
        params.addQueryStringParameter("page", String.valueOf(1));
        params.addQueryStringParameter("pagesize", String.valueOf(2500));
        XutilsHttp.Get(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                MyFrirends myFrirends = JsonAnalysis.getFriends(result);
                if (myFrirends == null)
                    return;
                if (myFrirends.getVariables() == null)
                    return;
                if (myFrirends.getVariables().getList() == null)
                    return;

                BaseHelper.getInstance().saveListAll(myFrirends.getVariables().getList(),
                        FriendsInfo.class);
            }
        });
    }

    List<MyFavoriteBean> favList = new ArrayList<>();

    /**
     * 我的关注
     */
    private void requestFavorite() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_FAVORITE);
        XutilsHttp.Get(params, new ListDataCallback(ListDataCallback.LIST_KEY_MY_FAOORITE, MyFavoriteBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (!isViewAttached()) return;
                List<MyFavoriteBean> listFav = result.getDataList();
                if (listFav != null && !listFav.isEmpty()) {
                    for (int i = listFav.size() - 1; i >= 0; i--) {
                        if (TextUtils.equals(listFav.get(i).getId(), "0")) {
                            listFav.remove(i);
                        }
                    }
                    favList.clear();
                    favList.addAll(listFav);
                }
                loginBack();
            }

            @Override
            public void flyError() {
                if (!isViewAttached()) return;
                super.flyError();
                loginBack();
            }
        });
    }

    private void loginBack() {
        TagEvent tagEvent = new TagEvent(TagEvent.FAV_LIST_CHANGE);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) favList);
        tagEvent.setBundle(bundle);
        EventBus.getDefault().post(tagEvent);

        EventBus.getDefault().post(UserManger.getUserInfo());
        getView().loginSuccess();
    }


    /**
     * 第三方登录回调
     */
    public UmengLogin.iFlyUmengLoginCallback uMengLoginCallback = new UmengLogin.iFlyUmengLoginCallback() {
        @Override
        public void flyUmengLoginsuccess(String type, String access_token, String openid, String face, String nickname, String unionid) {
            if (!isViewAttached()) return;
            LoginVideoPresenter.this.openid = openid;
            LoginVideoPresenter.this.accesstoken = access_token;
            LoginVideoPresenter.this.nickname = nickname;
            LoginVideoPresenter.this.faceUrl = face;
            LogFly.e("type:" + type + "  access_token:" + access_token + "  openid:" + openid + "  face:" + face + "  nickname:" + nickname + "  unionid:" + unionid);
            thirdPartyLogin(type, openid, access_token, unionid);
        }

        @Override
        public void flyUmengLoginFailure() {
        }
    };


    /**
     * 第三方登录
     *
     * @param logintype
     * @param openid
     * @param accesstoken
     */
    public void thirdPartyLogin(String logintype, String openid, String accesstoken, final String unionid) {
        getDialog().proDialogShow();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_THIRD_LOGIN);
        if (logintype.equals(SHARE_MEDIA.QQ.toString())) {
            this.logintype = "qq";
        } else if (logintype.equals(SHARE_MEDIA.SINA.toString())) {
            this.logintype = "weibo";
        } else if (logintype.equals(SHARE_MEDIA.WEIXIN.toString())) {
            this.logintype = "webchat";
        } else {
            this.logintype = logintype;
        }
        params.addQueryStringParameter("logintype", this.logintype);
        params.addBodyParameter("openid", openid);
        params.addBodyParameter("accesstoken", accesstoken);
        String RegistrationID = JPushInterface.getRegistrationID(FlyerApplication.getContext());
        params.addBodyParameter("Registrationid", RegistrationID);
        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                BaseBean bean = JsonAnalysis.getSuccessbean(result);
                if (bean.isSuccessEquals1()) {
                    LoginBase loginBase = JsonAnalysis.getBean(result, LoginBase.class);
                    loginInit(loginBase);
                } else {
                    if (TextUtils.equals(bean.getCode(), "10008")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("bindtype", LoginVideoPresenter.this.logintype);
                        bundle.putString("openid", LoginVideoPresenter.this.openid);
                        bundle.putString("accesstoken", LoginVideoPresenter.this.accesstoken);
                        bundle.putString("nickname", nickname);
                        bundle.putString("faceurl", faceUrl);
                        bundle.putString("unionid", unionid);
                        bundle.putInt(FinalUtils.REGIST_TYPE, FinalUtils.REGIST_THRID);
                        getDialog().proDialogDissmiss();
                        getView().threadLoginNotBind(bundle);
                    } else {
                        ToastUtils.showToast(bean.getMessage());
                    }
                    getDialog().proDialogDissmiss();
                }
            }
        });
    }

}
