package com.ideal.flyerteacafes.ui.activity.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.CacheFileManger;
import com.ideal.flyerteacafes.caff.SetCommonManger;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.ui.controls.SwitchButton;
import com.ideal.flyerteacafes.ui.view.LtctriLayout;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by fly on 2017/5/18.
 */

public class SettingVH extends BaseViewHolder implements CompoundButton.OnCheckedChangeListener {


    IActionListener iActionListener;

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (iActionListener != null) iActionListener.actionCheckedChanged(compoundButton, b);
    }

    public interface IActionListener {

        int ID_BACK = R.id.toolbar_left;
        int ID_PHONE = R.id.phone_layout;
        int ID_PASSWORD = R.id.passwords_layout;
        int ID_WEIXIN = R.id.weixin_layout;
        int ID_QQ = R.id.qq_layout;
        int ID_WEIBO = R.id.weibo_layout;
        int ID_CLEAR_CACHE = R.id.clear_cache_layout;
        int ID_ABOUT = R.id.about_layout;
        int ID_LOGIN_OUT = R.id.mrl_loginout_btn;

        int ID_MESSAGE_PUSH = R.id.setting_server_push_img;
        int ID_PICTURE_MODE = R.id.setting_picture_mode_img;
        int ID_THREAD_MODE = R.id.setting_thread_streamline_mode_img;
        int ID_FLOW_MODE = R.id.setting_flow_img;
        int ID_SEE = R.id.setting_privacy_img;
        int ID_BUSINESS_COOPERATION = R.id.business_cooperation_layout;

        void actionClick(View view);

        void actionCheckedChanged(CompoundButton compoundButton, boolean b);
    }

    private View mView;
    @ViewInject(R.id.phone_layout)
    private LtctriLayout phone_layout;
    @ViewInject(R.id.passwords_layout)
    private LtctriLayout passwords_layout;
    @ViewInject(R.id.weixin_layout)
    private LtctriLayout weixin_layout;
    @ViewInject(R.id.qq_layout)
    private LtctriLayout qq_layout;
    @ViewInject(R.id.weibo_layout)
    private LtctriLayout weibo_layout;
    @ViewInject(R.id.clear_cache_layout)
    private LtctriLayout clear_cache_layout;
    @ViewInject(R.id.setting_server_push_img)
    private SwitchButton servierPushImg;
    @ViewInject(R.id.setting_picture_mode_img)
    private SwitchButton pictureModeImg;
    @ViewInject(R.id.setting_privacy_img)
    private SwitchButton privacyImg;
    @ViewInject(R.id.setting_thread_streamline_mode_img)
    private SwitchButton threadStreamlineSwitch;
    @ViewInject(R.id.setting_flow_img)
    private SwitchButton settingFlowSwitch;
    @ViewInject(R.id.zhanghao_title)
    private View zhanghao_title;
    @ViewInject(R.id.three_login_title)
    private View three_login_title;
    @ViewInject(R.id.mrl_loginout_btn)
    private View mrl_loginout_btn;


    @Event({R.id.toolbar_left, R.id.phone_layout, R.id.passwords_layout, R.id.weixin_layout, R.id.business_cooperation_layout,
            R.id.qq_layout, R.id.weibo_layout, R.id.clear_cache_layout, R.id.about_layout, R.id.mrl_loginout_btn})
    private void click(View v) {
        if (iActionListener != null) iActionListener.actionClick(v);
    }

    public SettingVH(View view, IActionListener iActionListener) {
        mView = view;
        this.iActionListener = iActionListener;
        x.view().inject(this, view);
        init();
    }


    private void init() {

        if (UserManger.isLogin()) {
            setPhone();
            setPasswords();
            if (UserManger.getUserInfo().getBind_webchat().equals(UserBean.bind)) {
                weixin_layout.setTextByCt(mContext.getString(R.string.binding_ok));
            } else {
                weixin_layout.setTextByCt(mContext.getString(R.string.binding_no));
            }
            if (UserManger.getUserInfo().getBind_qq().equals(UserBean.bind)) {
                qq_layout.setTextByCt(mContext.getString(R.string.binding_ok));
            } else {
                qq_layout.setTextByCt(mContext.getString(R.string.binding_no));
            }
            if (UserManger.getUserInfo().getBind_weibo().equals(UserBean.bind)) {
                weibo_layout.setTextByCt(mContext.getString(R.string.binding_ok));
            } else {
                weibo_layout.setTextByCt(mContext.getString(R.string.binding_no));
            }
        } else {
            setLoginOut();
        }

        pictureModeImg.setChecked(SetCommonManger.isNoGraphMode());
        servierPushImg.setChecked(SetCommonManger.isPushMode());
        privacyImg.setChecked(SetCommonManger.isNearbyMode());
        threadStreamlineSwitch.setChecked(SetCommonManger.isThreadStreamlineMode());
        settingFlowSwitch.setChecked(SetCommonManger.isFlowbyMode());

        setCacheSize();

        servierPushImg.setOnCheckedChangeListener(this);
        pictureModeImg.setOnCheckedChangeListener(this);
        privacyImg.setOnCheckedChangeListener(this);
        threadStreamlineSwitch.setOnCheckedChangeListener(this);
        settingFlowSwitch.setOnCheckedChangeListener(this);
    }


    public void setLoginOut() {
        zhanghao_title.setVisibility(View.GONE);
        three_login_title.setVisibility(View.GONE);
        phone_layout.setVisibility(View.GONE);
        passwords_layout.setVisibility(View.GONE);
        weixin_layout.setVisibility(View.GONE);
        qq_layout.setVisibility(View.GONE);
        weibo_layout.setVisibility(View.GONE);
        mrl_loginout_btn.setVisibility(View.GONE);
    }

    public void setPhone() {
        if (UserManger.getUserInfo().getBind_mobile().equals(UserBean.bind)) {
            phone_layout.setTextByCt(UserManger.getUserInfo().getBind_mobile_no());
        } else {
            phone_layout.setTextByCt("绑定手机快速登录");
        }
    }

    private void setPasswords() {
        if (UserManger.getUserInfo() != null) {
            if (TextUtils.equals(UserManger.getUserInfo().getBind_mobile(), "no") && TextUtils.equals(UserManger.getUserInfo().getBind_email(), "no")) {
                passwords_layout.setVisibility(View.GONE);
            } else {
                if (TextUtils.equals(UserManger.getUserInfo().getPassword_status(), "1")) {//有密码
                    passwords_layout.setTextByCt("修改密码");
                } else {
                    passwords_layout.setTextByCt("设置密码快速登录");
                }
            }
        }
    }

    public void setBindOk(String type) {
        if (type.equals(SHARE_MEDIA.QQ.toString())) {
            weixin_layout.setTextByCt(mContext.getString(R.string.binding_ok));
        } else if (type.equals(SHARE_MEDIA.SINA.toString())) {
            qq_layout.setTextByCt(mContext.getString(R.string.binding_ok));
        } else if (type.equals(SHARE_MEDIA.WEIXIN.toString())) {
            weibo_layout.setTextByCt(mContext.getString(R.string.binding_ok));
        }
    }

    public void setCacheSize() {
        clear_cache_layout.setTextByCt(CacheFileManger.getCacheImageSize());
    }


}
