package com.ideal.flyerteacafes.ui.fragment.page.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.ui.activity.PerfectDatumActivity;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.SmartUtil;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

//import com.ideal.flyerteacafes.presenter.LoginPresenter;

/**
 * Created by young on 2015/12/25.
 * 手机号注册
 */
public class MobileRegisterFragment extends LoginBaseFragment {

    private String mobile;
    private String code;

    public static final int REQUEST_CODE_PERFECT_DATUM = 654;
    public static final int REQUEST_CODE_BIND_CHOOSE = 10;

    @Override
    protected void init() {
        hintOneTxt = "请输入手机号";
        hintTwoTxt = "请输入验证码";
        thridTxt = getString(R.string.type_fast_register);
    }

    @Override
    protected void initView() {
        super.initView();
        btnLogin.setText("注册");
    }

    @Override
    protected boolean isGetCode() {
        return true;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.type_login_btn:
                mobile = etPhone.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    SmartUtil.BToast("请输入手机号");
                    return;
                }
                code = etCode.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    SmartUtil.BToast("请输入验证码");
                    return;
                }
                verify();
                break;
            case R.id.btn_obtain_code:
                initCodeBtn();
                break;
            case R.id.wechat_login_btn:
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", "微信");
                MobclickAgent.onEvent(getContext(), FinalUtils.EventId.register_thirdParty_click, map);
                break;
            case R.id.qq_login_btn:
                map = new HashMap<String, String>();
                map.put("name", "QQ");
                MobclickAgent.onEvent(getContext(), FinalUtils.EventId.register_thirdParty_click, map);
                break;
            case R.id.sina_login_btn:
                map = new HashMap<String, String>();
                map.put("name", "微博");
                MobclickAgent.onEvent(getContext(), FinalUtils.EventId.register_thirdParty_click, map);
                break;
        }

    }

    private Timer timer = new Timer();
    TimerTask task;
    private int time = 0;

    private void initCodeBtn() {
        btnCode.setEnabled(false);
        getYanZhengMa();
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
                            btnCode.setText(String.valueOf(time) + "S后重试");
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
     * 获取验证码
     */
    public void getYanZhengMa() {
        String mobile_no = etPhone.getText().toString();
        if (TextUtils.isEmpty(mobile_no)) {
            SmartUtil.BToast("请输入手机号");
        }
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_PHONE_CODE);
        params.addQueryStringParameter("mobile_no", mobile_no);
        params.addQueryStringParameter("type", "reg");


        XutilsHttp.Get(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                String msg = JsonAnalysis.getRegistMsg(result);
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtils.showToast(msg);
                }
            }

            @Override
            public void flyError() {
                super.flyError();
                ToastUtils.showToast("获取验证码失败，请重新获取");
                btnCode.setEnabled(true);
                btnCode.setText("获取验证码");
                btnCode.setBackgroundResource(R.drawable.blue_get_border);
                if (task != null)
                    task.cancel();
            }
        });
    }

    /**
     * 验证验证码是否正确
     */
    public void verify() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_VERIFY_CODE);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("code", code);
        params.addBodyParameter("type", "reg");
        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                BaseBean bean = JsonAnalysis.getVerify(result);
                if (bean.getCode() != null && bean.getCode().equals("110")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("code", code);
                    bundle.putString("mobile", mobile);
                    bundle.putInt(FinalUtils.REGIST_TYPE, FinalUtils.REGIST_PHONE);
                    jumpActivityForResult(PerfectDatumActivity.class, bundle, REQUEST_CODE_PERFECT_DATUM);
                } else {
                    SmartUtil.BToast("您的验证码输入有误！");
                }
            }
        });
    }


}
