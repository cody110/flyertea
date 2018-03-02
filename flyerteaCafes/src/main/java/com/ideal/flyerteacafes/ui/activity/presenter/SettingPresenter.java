package com.ideal.flyerteacafes.ui.activity.presenter;

import com.ideal.flyerteacafes.caff.CacheDataManger;
import com.ideal.flyerteacafes.caff.ForumDataManger;
import com.ideal.flyerteacafes.caff.ReadsManger;
import com.ideal.flyerteacafes.caff.SetCommonManger;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.caff.YueManger;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.dal.MyPostHelper;
import com.ideal.flyerteacafes.dal.RoughDraftHelper;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.model.entity.YouzanBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.ISetting;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.youzan.sdk.YouzanSDK;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/5/18.
 */

public class SettingPresenter extends BasePresenter<ISetting> {

    /**
     * 调用接口设置
     */
    public void requestSetHidden(final boolean bol) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_HIDDEN);
        //0显示位置状态 1  隐身状态， 其他人不能搜索出该会员
        params.addQueryStringParameter("hidden", String.valueOf(bol ? 0 : 1));
        XutilsHttp.Get(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                if (bol) {
                    SetCommonManger.openNearbyMode();
                } else {
                    SetCommonManger.closeNearbyMode();
                }
                SharedPreferencesString.getInstances().commit();
            }
        });
    }


    /**
     * 第三方绑定
     *
     * @param type
     * @param nickname
     * @param openid
     */
    public void binding(final String type, String nickname, String openid, String accesstoken, String unionid, String avatar) {
        getDialog().proDialogShow();
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
        params.addBodyParameter("avatar", avatar);

        XutilsHttp.Post(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                getDialog().proDialogDissmiss();
                BaseBean bean = JsonAnalysis.getBasebean(result);
                if (bean.getMessage().equals("bind_ok")) {
                    if (type.equals(SHARE_MEDIA.QQ.toString())) {
                        SharedPreferencesString.getInstances().savaToString("bind_qq", UserBean.bind);
                    } else if (type.equals(SHARE_MEDIA.SINA.toString())) {
                        SharedPreferencesString.getInstances().savaToString("bind_weibo", UserBean.bind);
                    } else if (type.equals(SHARE_MEDIA.WEIXIN.toString())) {
                        SharedPreferencesString.getInstances().savaToString("bind_webchat", UserBean.bind);
                    }
                    SharedPreferencesString.getInstances().commit();
                    getView().bindOK(type);
                } else if (bean.getMessage().equals("bind_other")) {
                    ToastUtils.showToast("此应用已绑定过其他账号，无法重复绑定");
                } else {
                    ToastUtils.showToast("绑定失败！");
                }
            }
        });

    }

    /**
     * 退出登录
     */
    public void requestLogout() {
        getDialog().proDialogShow();
        //TODO 上报阅读记录
        ReadsManger.getInstance().upload();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_LOGOUT);
        params.addQueryStringParameter("formhash", UserManger.getFormhash());

        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_YOUZAN_LOGOUT), null);//有赞退出
        YouzanSDK.userLogout(context);
        UserManger.getInstance().setYouzanLogOut();
        XutilsHttp.Get(params, new PBaseCallback() {
            @Override
            public void flySuccess(BaseBean bean) {
                if (!isViewAttached()) return;
//                if (bean.getCode().equals("logout_succeed")) {
                    YueManger.getInstance().saveReadIdsByLoca();
                    UserManger.getInstance().getFavList().clear();
                    CacheDataManger.clearCacheDataByLoginOut();
                    MyPostHelper myPostHelper = new MyPostHelper();
                    myPostHelper.deteleAll();
                    RoughDraftHelper roughDraftHelper = new RoughDraftHelper();
                    roughDraftHelper.deteleAll();

                    EventBus.getDefault().post(FinalUtils.OUTLOGIN);
                    ForumDataManger.getInstance().loginOut();
                    UserManger.loginOut();
                    getView().loginOut();
                    ToastUtils.showToast("退出登录成功");
//                } else {
//                    ToastUtils.showToast(bean.getMessage());
//                }
            }
        });
    }

}
