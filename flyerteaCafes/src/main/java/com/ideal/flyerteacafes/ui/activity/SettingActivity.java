package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.CacheFileManger;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.SetCommonManger;
import com.ideal.flyerteacafes.caff.UmengLogin;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.ui.activity.base.MVPBaseActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.ISetting;
import com.ideal.flyerteacafes.ui.activity.presenter.SettingPresenter;
import com.ideal.flyerteacafes.ui.activity.viewholder.SettingVH;
import com.ideal.flyerteacafes.ui.activity.web.SystemWebActivity;
import com.ideal.flyerteacafes.ui.view.MyAlertDialog;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.SmartUtil;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.IntentTools;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

@ContentView(R.layout.activity_setting)
public class SettingActivity extends MVPBaseActivity<ISetting, SettingPresenter> {


    SettingVH vh;

    public static final int UPDATE_MOBILE = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mPresenter.attachView(iSetting);
        x.view().inject(this);
        View view = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        vh = new SettingVH(view, iActionListener);
    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter();
    }


    ISetting iSetting = new ISetting() {

        @Override
        public void bindOK(String type) {
            vh.setBindOk(type);
        }

        @Override
        public void loginOut() {
            vh.setLoginOut();
            finish();
        }
    };

    SettingVH.IActionListener iActionListener = new SettingVH.IActionListener() {
        @Override
        public void actionClick(View view) {
            final Bundle bundle = new Bundle();
            switch (view.getId()) {
                case SettingVH.IActionListener.ID_BACK:
                    finish();
                    break;
                case SettingVH.IActionListener.ID_PHONE:
                    if (UserManger.getUserInfo() != null && UserManger.getUserInfo().getPs_token() != null) {

                        if (UserManger.getUserInfo().getBind_mobile().equals(UserBean.bind)) {
                            MyAlertDialog.Builder builder = new MyAlertDialog.Builder(SettingActivity.this);
                            builder.setTitle(null);
                            builder.setMessage("更改登录手机号");
                            builder.setNegativeButton("取消");
                            builder.setPositiveButton("确定", new MyAlertDialog.OnAlertViewClickListener() {
                                @Override
                                public void OnAlertViewClick() {
                                    bundle.putString("url", Utils.HtmlUrl.HTML_UPDATE_PHONE + "?access_token=" + UserManger.getUserInfo().getPs_token().getAccess_token());
                                    jumpActivityForResult(SystemWebActivity.class, bundle, UPDATE_MOBILE);
                                }
                            });
                            builder.create().show();
                        } else {
                            bundle.putString("url", Utils.HtmlUrl.HTML_BIND_PHONE + "?access_token=" + UserManger.getUserInfo().getPs_token().getAccess_token());
                            jumpActivityForResult(SystemWebActivity.class, bundle, UPDATE_MOBILE);
                        }


                    }
                    break;
                case SettingVH.IActionListener.ID_PASSWORD:
                    if (UserManger.getUserInfo() != null && UserManger.getUserInfo().getPs_token() != null) {
                        bundle.putString("url", Utils.HtmlUrl.HTML_UPDATE_PASSWORD + "?access_token=" + UserManger.getUserInfo().getPs_token().getAccess_token());
                        jumpActivity(SystemWebActivity.class, bundle);
                    }
                    break;
                case SettingVH.IActionListener.ID_WEIXIN:
                    if (UserManger.getUserInfo().getBind_webchat().equals(UserBean.bind)) {
                        SmartUtil.BToast("已绑定微信");
                    } else {
                        if (IntentTools.isAppInstalled(FlyerApplication.getContext(), FinalUtils.WECHART_PACKAGE_NAME)) {
                            UmengLogin.login(SettingActivity.this, SHARE_MEDIA.WEIXIN, iFlyUmengLoginCallback);
                        } else {
                            ToastUtils.showToast("请安装微信客户端");
                        }
                    }
                    break;
                case SettingVH.IActionListener.ID_QQ:
                    if (UserManger.getUserInfo().getBind_qq().equals(UserBean.bind)) {
                        SmartUtil.BToast("已绑定QQ");
                    } else {
                        if (IntentTools.isAppInstalled(FlyerApplication.getContext(), FinalUtils.QQ_PACKAGE_NAME)) {
                            UmengLogin.login(SettingActivity.this, SHARE_MEDIA.QQ, iFlyUmengLoginCallback);
                        } else {
                            ToastUtils.showToast("请安装QQ客户端");
                        }

                    }
                    break;
                case SettingVH.IActionListener.ID_WEIBO:
                    if (UserManger.getUserInfo().getBind_weibo().equals(UserBean.bind)) {
                        SmartUtil.BToast("已绑定新浪微博");
                    } else {
                        if (IntentTools.isAppInstalled(FlyerApplication.getContext(), "com.sina.weibo")) {
                            UmengLogin.login(SettingActivity.this, SHARE_MEDIA.SINA, iFlyUmengLoginCallback);
                        } else {
                            ToastUtils.showToast("请安装新浪微博客户端");
                        }

                    }
                    break;
                case SettingVH.IActionListener.ID_CLEAR_CACHE:
                    boolean flag = CacheFileManger.clearCacheImage();
                    if (flag) {
                        BToast("清除缓存成功");
                        vh.setCacheSize();
                    } else {
                        BToast("清除缓存失败");
                    }
                    break;

                case ID_BUSINESS_COOPERATION:
                    jumpActivity(BusinessActivity.class, null);
                    break;
                case SettingVH.IActionListener.ID_ABOUT:
                    jumpActivity(AboutUsActivity.class, null);
                    break;

                case SettingVH.IActionListener.ID_LOGIN_OUT:
                    mPresenter.requestLogout();
                    break;
            }
        }

        @Override
        public void actionCheckedChanged(CompoundButton compoundButton, boolean b) {
            compoundButton.setChecked(b);
            switch (compoundButton.getId()) {
                case SettingVH.IActionListener.ID_MESSAGE_PUSH:
                    if (b) {
                        SetCommonManger.openPushMode();
                        JPushInterface.resumePush(SettingActivity.this);
                    } else {
                        SetCommonManger.closePushMode();
                        JPushInterface.stopPush(SettingActivity.this);
                    }
                    break;
                case SettingVH.IActionListener.ID_PICTURE_MODE:
                    if (b) {
                        SetCommonManger.openNoGraphMode();
                    } else {
                        SetCommonManger.closeNoGraphMode();
                    }
                    EventBus.getDefault().post(FinalUtils.ADVERT);
                    break;
                case SettingVH.IActionListener.ID_THREAD_MODE:
                    if (b) {
                        SetCommonManger.openThreadStreamlineMode();
                    } else {
                        SetCommonManger.closeThreadStreamlineMode();
                    }
                    break;
                case SettingVH.IActionListener.ID_FLOW_MODE:
                    if (b) {
                        SetCommonManger.openFlowbyMode();
                    } else {
                        SetCommonManger.closeFlowbyMode();
                    }
                    break;

                case SettingVH.IActionListener.ID_SEE:
                    mPresenter.requestSetHidden(b);
                    break;
            }
        }
    };

    UmengLogin.iFlyUmengLoginCallback iFlyUmengLoginCallback = new UmengLogin.iFlyUmengLoginCallback() {
        @Override
        public void flyUmengLoginsuccess(String type, String access_token, String openid, String face, String nickname, String unionid) {
            mPresenter.binding(type, nickname, openid, access_token, unionid, face);
        }

        @Override
        public void flyUmengLoginFailure() {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_MOBILE) {
            if (resultCode == RESULT_OK) {
                vh.setPhone();
            }
        } else {
            UMShareAPI.get(this.getApplicationContext()).onActivityResult(requestCode, resultCode, data);
        }
    }


}
