package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.UmengLogin;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.view.AppTitleBar;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.SmartUtil;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.ideal.flyerteacafes.utils.tools.V;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by fly on 2016/1/5.
 */
public class SetBindingFlyActivity extends BaseActivity implements View.OnClickListener, UmengLogin.iFlyUmengLoginCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);

        initView();
    }

    private TextView weixinStatus, qqStatus, sineStatus;

    private void initView() {
        AppTitleBar titleBar = V.f(this, R.id.binding_titlebar);
        titleBar.setTitleTxt("第三方账号绑定");
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpActivitySetResult(null);
            }
        });

        View weixinView = V.f(this, R.id.binding_weixin_layout);
        View qqView = V.f(this, R.id.binding_qq_layout);
        View sinaView = V.f(this, R.id.binding_sina_layout);

        ImageView weixinImg = V.f(weixinView, R.id.binding_icon);
        ImageView qqImg = V.f(qqView, R.id.binding_icon);
        ImageView sinaImg = V.f(sinaView, R.id.binding_icon);

        TextView weixinName = V.f(weixinView, R.id.binding_name);
        TextView qqName = V.f(qqView, R.id.binding_name);
        TextView sineName = V.f(sinaView, R.id.binding_name);

        weixinStatus = V.f(weixinView, R.id.binding_status);
        qqStatus = V.f(qqView, R.id.binding_status);
        sineStatus = V.f(sinaView, R.id.binding_status);

        weixinName.setText(getString(R.string.weixin));
        qqName.setText(getString(R.string.qq));
        sineName.setText(getString(R.string.sine));

        if (preferences.getStringToKey("bind_webchat").equals(UserBean.bind)) {
            weixinStatus.setText(getString(R.string.binding_ok));
        } else {
            weixinStatus.setText(getString(R.string.binding_no));
            weixinStatus.setTextColor(getResources().getColor(R.color.app_bg_title));
        }
        if (preferences.getStringToKey("bind_qq").equals(UserBean.bind)) {
            qqStatus.setText(getString(R.string.binding_ok));
        } else {
            qqStatus.setText(getString(R.string.binding_no));
            qqStatus.setTextColor(getResources().getColor(R.color.app_bg_title));
        }
        if (preferences.getStringToKey("bind_weibo").equals(UserBean.bind)) {
            sineStatus.setText(getString(R.string.binding_ok));
        } else {
            sineStatus.setText(getString(R.string.binding_no));
            sineStatus.setTextColor(getResources().getColor(R.color.app_bg_title));
        }

        weixinView.setOnClickListener(this);
        qqView.setOnClickListener(this);
        sinaView.setOnClickListener(this);


        weixinImg.setImageResource(R.drawable.bind_weicin_icon);
        qqImg.setImageResource(R.drawable.bind_qq_icon);
        sinaImg.setImageResource(R.drawable.bind_sina_icon);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        jumpActivitySetResult(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this.getApplicationContext()).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.binding_weixin_layout:
                if (preferences.getStringToKey("bind_webchat").equals(UserBean.bind)) {
                    SmartUtil.BToast("已绑定微信");
                } else {
                    UmengLogin.login(this, SHARE_MEDIA.WEIXIN, this);
                }
                break;
            case R.id.binding_qq_layout:
                if (preferences.getStringToKey("bind_qq").equals(UserBean.bind)) {
                    SmartUtil.BToast("已绑定QQ");
                } else {
                    UmengLogin.login(this, SHARE_MEDIA.QQ, this);
                }
                break;
            case R.id.binding_sina_layout:
                if (preferences.getStringToKey("bind_weibo").equals(UserBean.bind)) {
                    SmartUtil.BToast("已绑定新浪微博");
                } else {
                    UmengLogin.login(this, SHARE_MEDIA.SINA, this);
                }

                break;
        }
    }

    /**
     * 第三方绑定
     *
     * @param type
     * @param nickname
     * @param openid
     */
    private void binding(final String type, String nickname, String openid, String accesstoken, String unionid) {
        showDialog();
        final FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_BINDING);
        if (type.equals(SHARE_MEDIA.QQ.toString())) {
            params.addQueryStringParameter("bindtype", "qq");
        } else if (type.equals(SHARE_MEDIA.SINA.toString())) {
            params.addQueryStringParameter("bindtype", "weibo");
        } else if (type.equals(SHARE_MEDIA.WEIXIN.toString())) {
            params.addQueryStringParameter("bindtype", "webchat");
        }
        params.addBodyParameter("uid", UserManger.getUserInfo().getMember_uid());
        params.addBodyParameter("openid", openid);
        params.addBodyParameter("nickname", nickname);
        params.addBodyParameter("accesstoken", accesstoken);
        params.addBodyParameter("unionid", unionid);
        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                dialogDismiss();
                BaseBean bean = JsonAnalysis.getBasebean(result);
                if (bean.getMessage().equals("bind_ok")) {
                    if (type.equals(SHARE_MEDIA.QQ.toString())) {
                        preferences.savaToString("bind_qq", UserBean.bind);
                        qqStatus.setText(getString(R.string.binding_ok));
                        qqStatus.setTextColor(getResources().getColor(R.color.app_body_grey));
                    } else if (type.equals(SHARE_MEDIA.SINA.toString())) {
                        preferences.savaToString("bind_weibo", UserBean.bind);
                        sineStatus.setText(getString(R.string.binding_ok));
                        sineStatus.setTextColor(getResources().getColor(R.color.app_body_grey));
                    } else if (type.equals(SHARE_MEDIA.WEIXIN.toString())) {
                        preferences.savaToString("bind_webchat", UserBean.bind);
                        weixinStatus.setText(getString(R.string.binding_ok));
                        weixinStatus.setTextColor(getResources().getColor(R.color.app_body_grey));
                    }
                    preferences.commit();
                } else if (bean.getMessage().equals("bind_other")) {
                    BToast("此应用已绑定过其他账号，无法重复绑定");
                } else {
                    BToast("绑定失败！");
                }
            }

            @Override
            public void flyError() {
                super.flyError();
                dialogDismiss();
                BToast("绑定失败！");
            }
        });
    }


    @Override
    public void flyUmengLoginsuccess(String type, String access_token, String openid, String face, String nickname, String unionid) {
        binding(type, nickname, openid, access_token,unionid);
    }

    @Override
    public void flyUmengLoginFailure() {

    }

}
