package com.ideal.flyerteacafes.ui.fragment.page.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UmengLogin;
import com.ideal.flyerteacafes.caff.YueManger;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.dal.BaseHelper;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.FriendsInfo;
import com.ideal.flyerteacafes.model.entity.LoginBase;
import com.ideal.flyerteacafes.model.entity.MyFavoriteBean;
import com.ideal.flyerteacafes.model.entity.MyFrirends;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.RegistStationFlyActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.V;
import com.ideal.flyerteacafes.utils.xutils.XutilsHelp;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2016/1/7.
 */
public abstract class LoginBaseFragment extends BaseFragment implements View.OnClickListener, UmengLogin.iFlyUmengLoginCallback {

    private View view;

    protected String hintOneTxt = "", hintTwoTxt = "", thridTxt = "";//需要在init里赋值
    protected String logintype, openid, accesstoken, nickname, faceUrl, unionid;


    List<MyFavoriteBean> favList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_mobile_login, null);
        init();
        initView();
        return view;
    }

    /**
     * 初始化输入框hint的文字，左边的drawable  id
     */
    protected abstract void init();


    /**
     * 是否显示获取验证码
     *
     * @return
     */
    protected boolean isGetCode() {
        return false;
    }

    /**
     * 是否显示第三方的
     *
     * @return
     */
    protected boolean isShowTried() {
        return true;
    }

    /**
     * 是否设置为密码框
     *
     * @return
     */
    protected boolean isPass() {
        return false;
    }


    protected EditText etPhone, etCode;
    protected TextView btnCode, btnLogin;
    private ImageView wechatAuth, qqAuth, sinaAuth;

    protected void initView() {

        if (isShowTried()) {
            wechatAuth = V.f(view, R.id.wechat_login_btn);
            qqAuth = V.f(view, R.id.qq_login_btn);
            sinaAuth = V.f(view, R.id.sina_login_btn);
            wechatAuth.setOnClickListener(this);
            qqAuth.setOnClickListener(this);
            sinaAuth.setOnClickListener(this);

            TextView thridText = V.f(view, R.id.thrid_text_shuoming);
            thridText.setText(thridTxt);
        } else {
            V.f(view, R.id.thrid_mobile_layout).setVisibility(View.GONE);
        }

        btnCode = V.f(view, R.id.password_get_code_btn);
        if (isGetCode()) {
            btnCode.setVisibility(View.VISIBLE);
            btnCode.setOnClickListener(this);
        } else {
            btnCode.setVisibility(View.GONE);
        }


        etPhone = V.f(view, R.id.username_edit);
        etPhone.setHint(hintOneTxt);

        etCode = V.f(view, R.id.password_edit);
        etCode.setHint(hintTwoTxt);

        btnLogin = V.f(view, R.id.type_login_btn);
        btnLogin.setOnClickListener(this);

        if (isPass()) {
            etCode.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.wechat_login_btn) {
            UmengLogin.login(mActivity, SHARE_MEDIA.WEIXIN, this);
        } else if (v.getId() == R.id.qq_login_btn) {
            UmengLogin.login(mActivity, SHARE_MEDIA.QQ, this);
        } else if (v.getId() == R.id.sina_login_btn) {
            UmengLogin.login(mActivity, SHARE_MEDIA.SINA, this);
        }
    }


    /**
     * 第三方登录
     *
     * @param logintype
     * @param openid
     * @param accesstoken
     */
    public void thirdPartyLogin(String logintype, String openid, String accesstoken) {
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
        XutilsHttp.Post(params, thirdLoginCallback);
        mActivity.showDialog();
    }

    /**
     * 三方登陆回掉
     */
    StringCallback thirdLoginCallback = new StringCallback() {
        @Override
        public void flySuccess(String result) {
            if (mActivity.isEnd()) return;
            BaseBean bean = JsonAnalysis.getSuccessbean(result);
            if (bean.isSuccessEquals1()) {
                LoginBase loginBase = JsonAnalysis.getBean(result, LoginBase.class);
                loginInit(loginBase);
            } else {
                if (TextUtils.equals(bean.getCode(), "10008")) {
                    mActivity.dialogDismiss();
                    Bundle bundle = new Bundle();
                    bundle.putString("bindtype", logintype);
                    bundle.putString("openid", openid);
                    bundle.putString("accesstoken", accesstoken);
                    bundle.putString("nickname", nickname);
                    bundle.putString("faceurl", faceUrl);
                    bundle.putString("unionid", unionid);
                    bundle.putInt(FinalUtils.REGIST_TYPE, FinalUtils.REGIST_THRID);
                    jumpActivityForResult(RegistStationFlyActivity.class, bundle, 0);
                } else {
                    ToastUtils.showToast(bean.getMessage());
                }
            }
        }
    };


    @Override
    public void flyUmengLoginsuccess(String type, String access_token, String openid, String face, String nickname, String unionid) {
        this.openid = openid;
        this.accesstoken = access_token;
        this.nickname = nickname;
        this.faceUrl = face;
        this.unionid = unionid;
        if (!mActivity.isEnd())
            thirdPartyLogin(type, openid, access_token);
    }

    @Override
    public void flyUmengLoginFailure() {

    }

    SharedPreferencesString preferences = SharedPreferencesString.getInstances();
    String token1;
    UserBean bean;

    public void loginInit(LoginBase loginBase) {
        bean = loginBase.getVariables();
        if (DataUtils.loginIsOK(loginBase.getMessage().getCode())) {
//            DataUtils.saveLogininfo(bean);
            SharedPreferencesString.getInstances().saveUserinfo(bean);
            XutilsHelp.saveCookie();
            YueManger.getInstance().initUserReadIds();
            requestFriends();
            token1 = bean.getToken();

            //TOTD 登录完成获取我的关注
            requestFavorite();


        } else {
            mActivity.BToast(loginBase.getMessage().getMessage());
            mActivity.dialogDismiss();
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


    /**
     * 我的关注
     */
    public void requestFavorite() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_FAVORITE);
        XutilsHttp.Get(params, new ListDataCallback(ListDataCallback.LIST_KEY_MY_FAOORITE, MyFavoriteBean.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                List<MyFavoriteBean> listFav = result.getDataList();
                if (listFav != null && !listFav.isEmpty()) {
                    for (int i = listFav.size() - 1; i >= 0; i--) {
                        if (TextUtils.equals(listFav.get(i).getId(), "0")) {
                            listFav.remove(i);
                        }
                    }
                    favList.addAll(listFav);
                }
                loginBack();
            }

            @Override
            public void flyError() {
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

        EventBus.getDefault().post(bean);
        jumpActivitySetResult(null);
        mActivity.dialogDismiss();
    }


}
