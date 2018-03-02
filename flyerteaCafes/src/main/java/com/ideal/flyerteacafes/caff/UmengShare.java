package com.ideal.flyerteacafes.caff;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.dialog.ThreadShareDialog;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.HashMap;
import java.util.Map;

/**
 * 友盟分享类
 *
 * @author fly
 */
public class UmengShare {


    static UMShareAPI mShareAPI = UMShareAPI.get(FlyerApplication.getContext());
    static Context context = FlyerApplication.getContext();


    public static void shareMedail(final Activity activity, Bitmap bitmap, SHARE_MEDIA share_media) {
        UMImage image = new UMImage(activity, bitmap);//bitmap文件
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        new ShareAction(activity).setPlatform(share_media).withText("飞客茶馆").withMedia(image).share();
    }

    /**
     * 分享茶馆
     */
    public static void setShareFriends(Activity activity, SHARE_MEDIA share_media) {
        setShareContent(activity, context.getString(R.string.app_name),
                context.getString(R.string.share_app_recommend_friends),
                context.getString(R.string.share_app_recommend_fridens_url), share_media, null);

    }

    /**
     * 分享设置
     *
     * @param count
     * @param url
     */
    private static void setShareContent(final Activity activity, final String title, final String count, final String url, SHARE_MEDIA share_media, final UMShareListener listener) {
        if (isInstall(activity, share_media)) {
            final UMImage image = new UMImage(activity, R.drawable.ic_launcher);
            UMWeb web = new UMWeb(url);
            web.setTitle(title);//标题
            web.setThumb(image);  //缩略图
            web.setDescription(count);//描述
            new ShareAction(activity)
                    .setPlatform(share_media)
                    .withMedia(web)
                    .setCallback(listener)
                    .share();
        }
    }


    public static void setShareContent(final Activity activity, final String title, final String count, final String url, SHARE_MEDIA share_media, final int type, final String id) {
        final UMImage image = new UMImage(activity, R.drawable.ic_launcher);

        if (isInstall(activity, share_media)) {
            UMWeb web = new UMWeb(url);
            web.setTitle(title);//标题
            web.setThumb(image);  //缩略图
            web.setDescription(count);//描述
            new ShareAction(activity)
                    .setPlatform(share_media)
                    .withMedia(web)
                    .setCallback(new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {

                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {
                            if (type == ThreadShareDialog.TYPE_THREAD) {
                                FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_SHARE);
                                params.addQueryStringParameter("shareitem", "thread");
                                params.addQueryStringParameter("sid", id);
                                XutilsHttp.Get(params, null);
                            } else if (type == ThreadShareDialog.TYPE_ARTICLE) {
                                FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_SHARE);
                                params.addQueryStringParameter("shareitem", "article");
                                params.addQueryStringParameter("sid", id);
                                XutilsHttp.Get(params, null);
                            }


                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media) {
                        }
                    })
                    .share();
        }
    }


    /**
     * 是否安装
     *
     * @param activity
     * @param share_media
     * @return
     */
    private static boolean isInstall(Activity activity, SHARE_MEDIA share_media) {
        boolean bol = true;
        if (share_media == SHARE_MEDIA.WEIXIN || share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
            bol = mShareAPI.isInstall(activity, SHARE_MEDIA.WEIXIN);
            if (!bol)
                ToastUtils.showToast("未安装微信,不能分享");
        } else if (share_media == SHARE_MEDIA.SINA) {
            bol = mShareAPI.isInstall(activity, SHARE_MEDIA.SINA);
            if (!bol)
                ToastUtils.showToast("未安装微博,不能分享");
        } else if (share_media == SHARE_MEDIA.QQ || share_media == SHARE_MEDIA.QZONE) {
            bol = mShareAPI.isInstall(activity, SHARE_MEDIA.QQ);
            if (!bol)
                ToastUtils.showToast("未安装QQ,不能分享");

        }
        return bol;
    }


}
