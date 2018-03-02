package com.ideal.flyerteacafes.ui.fragment.page.login;

import android.view.View;

import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.LoginBase;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.utils.SmartUtil;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

/**
 * Created by fly on 2016/1/7.
 * 已登录账户绑定手机号
 */
public class BindMobileBySetFragment extends MobileLoginFragment {

    @Override
    protected void initView() {
        super.initView();
        btnLogin.setText("绑定");
    }

    @Override
    protected boolean isShowTried() {
        return false;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void getCode() {
        FlyRequestParams params=new FlyRequestParams(Utils.HttpRequest.HTTP_PHONE_CODE);
        params.addQueryStringParameter("mobile_no",mobile_no);
        params.addQueryStringParameter("type","bind");
        params.addQueryStringParameter("formhash",preferences.getStringToKey("formhash"));
        XutilsHttp.Get(params, codeCallback);
    }


    /**
     * 手机号绑定
     * @param mobile
     * @param code
     */
    @Override
    protected void mobileLogin(String mobile, String code) {
        bindingMobile(mobile, code);
    }

    private void bindingMobile(String mobile_no,String code){
        FlyRequestParams params=new FlyRequestParams(Utils.HttpRequest.HTTP_BINDING_MOBILE);
        params.addBodyParameter("mobile_no",mobile_no);
        params.addBodyParameter("code",code);
        params.addBodyParameter("mobilesubmit","yes");
        params.addBodyParameter("formhash", SharedPreferencesString.getInstances().getStringToKey("formhash"));
        mActivity.showDialog();
        XutilsHttp.Post( params, new StringCallback() {

            @Override
            public void flySuccess(String result) {
                LoginBase loginBase= JsonAnalysis.getBean(result, LoginBase.class);
                if(loginBase.getMessage().getCode().equals("mobile_bind_succeed")){
                    preferences.savaToString("bind_mobile", UserBean.bind);
                    preferences.commit();
                    jumpActivitySetResult(null);
                }else{
                    SmartUtil.BToast("绑定失败！");
                }
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mActivity.dialogDismiss();
            }

        });
    }


}
