package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.UmengLogin;
import com.ideal.flyerteacafes.model.entity.LoginCodeResponse;
import com.ideal.flyerteacafes.ui.activity.base.MVPBaseActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.ILoginVideo;
import com.ideal.flyerteacafes.ui.activity.presenter.LoginVideoPresenter;
import com.ideal.flyerteacafes.ui.activity.viewholder.LoginVideoVH;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;

/**
 * Created by fly on 2017/6/2.
 */

public class LoginVideoActivity extends MVPBaseActivity<ILoginVideo, LoginVideoPresenter> {


    LoginVideoVH vh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attachView(iLoginVideo);
        setContentView(R.layout.activity_login_video);
        vh = new LoginVideoVH(getRootView(), iActionListener);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected LoginVideoPresenter createPresenter() {
        return new LoginVideoPresenter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this.getApplicationContext()).onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            finish();
        }
    }

    ILoginVideo iLoginVideo = new ILoginVideo() {

        @Override
        public void callbackGetCOdeData(LoginCodeResponse loginCodeResponse) {
            ToastUtils.showToast(loginCodeResponse.getResult());
            if ("0".equals(loginCodeResponse.getResultstatus())) {// 1，为成功 0为获取验证码失败的情况
                vh.setGetCodeInitStatus();
            }
        }

        @Override
        public void loginSuccess() {
            jumpActivitySetResult(null);
        }

        @Override
        public void threadLoginNotBind(Bundle bundle) {
            jumpActivityForResult(RegistStationFlyActivity.class, bundle, 10);
        }
    };

    LoginVideoVH.IActionListener iActionListener = new LoginVideoVH.IActionListener() {
        @Override
        public void actionClick(View view) {
            switch (view.getId()) {
                case ID_BACK:
                    finish();
                    break;

                case ID_PHONE_LOGIN:
                    HashMap<String, String> map = new HashMap<>();
                    map.put("method", "手机号");
                    MobclickAgent.onEvent(LoginVideoActivity.this, FinalUtils.EventId.login_method_switch, map);
                    mPresenter.setSelectPhone();
                    vh.setSelectPhone();
                    break;

                case ID_USER_LOGIN:
                    map = new HashMap<>();
                    map.put("method", "用户名");
                    MobclickAgent.onEvent(LoginVideoActivity.this, FinalUtils.EventId.login_method_switch, map);
                    mPresenter.setSelectUserName();
                    vh.setSelectUser();
                    break;

                case ID_CLEAR_USERNAME:
                    vh.setClearUsername();
                    break;

                case ID_GET_CODE:
                    if (TextUtils.isEmpty(vh.getUserNameText())) {
                        ToastUtils.showToast("请输入手机号");
                    } else {
                        if (vh.isNeedGetCode()) {
                            mPresenter.requestGetCode(vh.getUserNameText());
                            vh.setGetCodeTime();
                        }
                    }
                    break;

                case ID_LOGIN:
                    mPresenter.requestLogin(vh.getUserNameText(), vh.getPassWordText());
                    break;

                case ID_REGIST:
                    MobclickAgent.onEvent(LoginVideoActivity.this, FinalUtils.EventId.login_register_click);
                    jumpActivity(RegisterShareActivity.class, null);
                    finish();
//                    jumpActivity(PerfectDatumActivity.class);
                    break;

                case ID_WECHAT:
                    UmengLogin.login(LoginVideoActivity.this, SHARE_MEDIA.WEIXIN, mPresenter.uMengLoginCallback);
                    map = new HashMap<>();
                    map.put("method", "微信");
                    MobclickAgent.onEvent(LoginVideoActivity.this, FinalUtils.EventId.login_thirdParty_click, map);
                    break;

                case ID_QQ:
                    UmengLogin.login(LoginVideoActivity.this, SHARE_MEDIA.QQ, mPresenter.uMengLoginCallback);
                    map = new HashMap<>();
                    map.put("method", "QQ");
                    MobclickAgent.onEvent(LoginVideoActivity.this, FinalUtils.EventId.login_thirdParty_click, map);
                    break;

                case ID_SINE:
                    UmengLogin.login(LoginVideoActivity.this, SHARE_MEDIA.SINA, mPresenter.uMengLoginCallback);
                    map = new HashMap<>();
                    map.put("method", "微博");
                    MobclickAgent.onEvent(LoginVideoActivity.this, FinalUtils.EventId.login_thirdParty_click, map);
                    break;
            }
        }
    };

}
