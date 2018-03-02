package com.ideal.flyerteacafes.ui.fragment.page.login;

import android.text.TextUtils;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.LoginBase;
import com.ideal.flyerteacafes.model.entity.LoginCodeResponse;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by young on 2015/12/25.
 *
 */
public class MobileLoginFragment extends LoginBaseFragment {

    private boolean dialogFlag = false;// 判断是否是登录框跳转过来的。
    private int time =0;
    private Timer timer = new Timer();
    TimerTask task;
    String mobile_no;

    @Override
    protected void init() {
        hintOneTxt="请输入手机号";
        hintTwoTxt="请输入验证码";
        thridTxt=getString(R.string.type_fast_login);
    }

    @Override
    protected boolean isGetCode() {
        return true;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_obtain_code:
                    mobile_no=etPhone.getText().toString();
                    if(TextUtils.isEmpty(mobile_no)){
                        ToastUtils.showToast("请输入手机号");
                    }else {
                        initCodeBtn();
                    }
                break;
            case R.id.type_login_btn:
                if(TextUtils.isEmpty(etPhone.getText().toString())){
                    ToastUtils.showToast("请输入手机号");
                }else if(TextUtils.isEmpty(etCode.getText().toString())){
                    ToastUtils.showToast("请输入验证码");
                }else {
                    mobileLogin(etPhone.getText().toString(),etCode.getText().toString());
                }

                break;
        }
    }


    private void initCodeBtn(){
        btnCode.setEnabled(false);
        getCode();
        if(task!=null)
            task.cancel();
        task = new TimerTask() {
            @Override
            public void run() {

                mActivity.runOnUiThread(new Runnable() { // UI thread
                    @Override
                    public void run() {
                        if (time <= 0) {
                            btnCode.setEnabled(true);
                            btnCode.setText("获取验证码");
                            btnCode.setBackgroundResource(R.drawable.blue_get_border);
                            task.cancel();
                        } else {
                            btnCode.setText(String.valueOf(time)+"S后重试");
                            btnCode.setBackgroundResource(R.drawable.blue_geted_border);
                        }
                        time--;
                    }
                });
            }
        };
        time = 60;
        timer.schedule(task, 0, 1000);
//        task.cancel();
    }


    /**
     * 获取登录验证码
     */
    protected void getCode(){
        FlyRequestParams params=new FlyRequestParams(Utils.HttpRequest.HTTP_PHONE_CODE);
        params.addQueryStringParameter("mobile_no",mobile_no);
        params.addQueryStringParameter("type", "login");
        XutilsHttp.Get(params, codeCallback);
    }

    /**
     * 验证码回调
     */
    StringCallback codeCallback=new StringCallback() {

        @Override
        public void flySuccess(String result) {
            LoginCodeResponse loginCodeResponse= JsonAnalysis.getloginCodeResponse(result);
            ToastUtils.showToast(loginCodeResponse.getResult());

            if("0".equals(loginCodeResponse.getResultstatus())){// 1，为成功 0为获取验证码失败的情况
                btnCode.setEnabled(true);
                btnCode.setText("获取验证码");
                btnCode.setBackgroundResource(R.drawable.blue_get_border);
                if(task!=null)
                    task.cancel();
            }
        }

        @Override
        public void flyError() {
            super.flyError();
            btnCode.setEnabled(true);
            btnCode.setText("获取验证码");
            btnCode.setBackgroundResource(R.drawable.blue_get_border);
            if(task!=null)
                task.cancel();
        }

    };

    /**
     * 手机号登录
     */
    protected void mobileLogin(String mobile,String code){
        FlyRequestParams params =new FlyRequestParams(Utils.HttpRequest.HTTP_LOG_MOBILE);
        params.addBodyParameter("username", mobile);
        params.addBodyParameter("code", code);

        // 激光推送设备ＩＤ
        String registrationID = JPushInterface.getRegistrationID(FlyerApplication.getContext());
        params.addBodyParameter("Registrationid", registrationID);
        mActivity.showDialog();
        XutilsHttp.Post(params,logMobileCallback);
    }

    /**
     * 手机号登录回调
     */
    StringCallback logMobileCallback=new StringCallback() {
        @Override
        public void flySuccess(String result) {
            LoginBase loginBase= JsonAnalysis.getBean(result, LoginBase.class);
            loginInit(loginBase);
        }
    };
}
