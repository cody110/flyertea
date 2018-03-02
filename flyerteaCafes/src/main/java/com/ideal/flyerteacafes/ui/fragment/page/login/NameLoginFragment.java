package com.ideal.flyerteacafes.ui.fragment.page.login;

import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.LoginBase;
import com.ideal.flyerteacafes.model.entity.TakeNotesBean;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.SmartUtil;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.json.JSONException;

import java.io.IOException;
import java.net.URLEncoder;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by young on 2015/12/25.
 *
 */
public class NameLoginFragment extends LoginBaseFragment {

    private String userName, passWord;
    private View view;
    private boolean dialogFlag = false;// 判断是否是登录框跳转过来的。

    @Override
    protected boolean isPass() {
        return true;
    }

    @Override
    protected void init() {
        hintOneTxt="请输入用户名";
        hintTwoTxt="请输入密码";
        thridTxt=getString(R.string.type_fast_login);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()==R.id.type_login_btn) {
            requestLogin(etPhone.getText().toString(), etCode.getText().toString());
        }
    }

    /**
     * 登录
     */
    @SuppressWarnings("deprecation")
    public void requestLogin(String userName,String passWord) {
        if (!!DataUtils.isEmpty(userName)) {
            SmartUtil.BToast("用户名不能为空");
            return;
        } else if (!!DataUtils.isEmpty(passWord)) {
            SmartUtil.BToast("密码不能为空");
            return;
        }
        this.userName=userName;
        this.passWord=passWord;
        FlyRequestParams params=new FlyRequestParams(Utils.HttpRequest.HTTP_login);
        params.addBodyParameter("username", userName);
        params.addBodyParameter("password", passWord);

        // 激光推送设备ＩＤ
        String RegistrationID = JPushInterface.getRegistrationID(FlyerApplication.getContext());
        params.addBodyParameter("Registrationid", RegistrationID);

        mActivity.showDialog();
        XutilsHttp.Post( params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                BaseBean bean = JsonAnalysis.getSuccessbean(result);
                if (bean.isSuccessEquals1()) {
                    LoginBase loginBase = JsonAnalysis.getBean(result, LoginBase.class);
                    loginInit(loginBase);
                }else{
                    ToastUtils.showToast(bean.getMessage());
                }
            }
        });

    }

}
