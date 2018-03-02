package com.ideal.flyerteacafes.ui.fragment.page.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.LoginBase;
import com.ideal.flyerteacafes.ui.activity.RegistStationFlyActivity;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.SmartUtil;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by fly on 2016/1/7.
 * 注册绑定手机号fragment
 */
public class BindMobileFragment extends MobileLoginFragment {

    @Override
    protected boolean isShowTried() {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        logintype = mActivity.getIntent().getStringExtra("bindtype");
        openid = mActivity.getIntent().getStringExtra("openid");
        accesstoken = mActivity.getIntent().getStringExtra("accesstoken");
        nickname = mActivity.getIntent().getStringExtra("nickname");
        faceUrl = mActivity.getIntent().getStringExtra("faceurl");
        unionid = mActivity.getIntent().getStringExtra("unionid");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void mobileLogin(String mobile, String code) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_REGIST_BIND_THRID_OLD);
        params.addQueryStringParameter("bindtype", logintype);
        params.addQueryStringParameter("smslogin", "yes");
        params.addBodyParameter("openid", openid);
        params.addBodyParameter("accesstoken", accesstoken);
        params.addBodyParameter("nickname", nickname);
        params.addBodyParameter("username", mobile);
        params.addBodyParameter("code", code);
        params.addBodyParameter("unionid", unionid);
        params.addBodyParameter("avatar", faceUrl);
        String registrationID = JPushInterface.getRegistrationID(FlyerApplication.getContext());
        params.addBodyParameter("Registrationid", registrationID);
        mActivity.showDialog();
        XutilsHttp.Post(params, new StringCallback() {

            @Override
            public void flySuccess(String result) {
                BaseBean bean = JsonAnalysis.getSuccessbean(result);
                if (bean.isSuccessEquals1()) {
                    LoginBase loginBase = JsonAnalysis.getBean(result, LoginBase.class);
                    loginInit(loginBase);
                } else {
                    ToastUtils.showToast(bean.getMessage());
                    mActivity.dialogDismiss();
                }
            }

        });
    }

}
