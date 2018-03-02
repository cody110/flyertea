package com.ideal.flyerteacafes.caff;

import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.utils.LogFly;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by young on 2015/12/26.
 * 友盟第三方登录
 */
public class UmengLogin {

    public interface iFlyUmengLoginCallback {
        void flyUmengLoginsuccess(String type, String access_token, String openid, String face, String nickname, String unionid);

        void flyUmengLoginFailure();
    }


    public static void login(final BaseActivity context, final SHARE_MEDIA platform, final iFlyUmengLoginCallback callback) {
        final UMShareAPI mShareAPI = UMShareAPI.get(context.getApplicationContext());
        mShareAPI.doOauthVerify(context, platform, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                String access_token = null;
                String openid = null;
                if (share_media == SHARE_MEDIA.SINA) {
                    openid = map.get("uid");
                } else {
                    openid = map.get("openid");
                }
                access_token = map.get("access_token");
                final String unionid = map.get("unionid");
                //获取相关授权信息
                final String finalAccess_token = access_token;
                final String finalOpenid = openid;

//                Toast.makeText(context, "授权成功.", Toast.LENGTH_SHORT).show();
                mShareAPI.getPlatformInfo(context, share_media, new UMAuthListener() {

                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> info) {
                        if (info != null) {
                            final String type = share_media.toString();
                            callback.flyUmengLoginsuccess(type, finalAccess_token, finalOpenid, info.get("profile_image_url"), info.get("screen_name"), unionid);
                            mShareAPI.deleteOauth(context, platform, null);
                        }
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {

                    }
                });


            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });

    }


}
