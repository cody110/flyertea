package com.ideal.flyerteacafes.caff;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.flyer.tusdk.TuSdkManger;
import com.ideal.flyerteacafes.tinker.TinkerManager;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.tools.IntentTools;
import com.ideal.flyerteacafes.utils.xutils.XutilsHelp;
import com.pgyersdk.crash.PgyCrashManager;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.youzan.sdk.YouzanSDK;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;


/**
 * tinker建议编写一个ApplicationLike的子类，你可以当成Application去使用，
 * 注意顶部的注解：@DefaultLifeCycle，其application属性，会在编译期生成一个TeaApplication类。
 */
@DefaultLifeCycle(application = ".TeaApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class FlyerApplication extends ApplicationLike {


    public final static boolean isToLandingActivity = false;//跟新是否显示引导页
    public static boolean wifiIsConnected = false;// wifi是否连接
    public static boolean isNeedPgy = false;//是否需要蒲公英
    public static boolean isNeedUm = true;//是否需要友盟统计
    public static boolean LOGCAT_ENABLE = false;//配置logcat显示与否

    private static Application mFlyApplication;


    public FlyerApplication(Application application, int tinkerFlags,
                            boolean tinkerLoadVerifyFlag,
                            long applicationStartElapsedTime,
                            long applicationStartMillisTime,
                            Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag,
                applicationStartElapsedTime, applicationStartMillisTime,
                tinkerResultIntent);
        mFlyApplication = application;
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //使应用支持分包
        MultiDex.install(base);

        TinkerManager.installTinker(this);
    }


    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

//        CrashHandler catchHandler = CrashHandler.getInstance();
//        catchHandler.init(getApplicationContext());


        //xutils 初始化
        x.Ext.init(getApplication());
        //数据库检查更新
        XutilsHelp.getDbUtils();
        // 友盟统计是否统计错误日志
        MobclickAgent.setCatchUncaughtExceptions(isNeedUm);
        // 激光推送设置
        JPushInterface.setDebugMode(true);
        JPushInterface.init(getContext());
        //有赞
        initYouzanSDK();
        //分享
        initShare();
        //微信浏览器
        initQbSdk();
        //热修复
        AndFixPatchManager.getInstance().initPatch(getContext());
        if (isNeedPgy) {
            //蒲公英
            PgyCrashManager.register(getContext());
        }
        //又拍云视频
        initTuSdk();
    }

    /**
     * 初始化you拍云视频
     */
    public void initTuSdk() {
        com.flyer.tusdk.Config config = new com.flyer.tusdk.Config();
        config.setLocalCachePath(CacheFileManger.getCacheImagePath());
        TuSdkManger.getInstance().init(getContext(), config);
    }

    /**
     * 初始化有赞
     */
    private void initYouzanSDK() {

        /**
         * 初始化SDK
         *
         * @param Context application Context
         * @param userAgent 用户代理 UA
         */
        YouzanSDK.init(getContext(), "7f84d03fe05b11be081469587229030");
    }

    public static Context getContext() {
        return mFlyApplication;
    }


    /**
     * 搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
     */
    private void initQbSdk() {
        if (IntentTools.isAppInstalled(mFlyApplication, FinalUtils.WECHART_PACKAGE_NAME) && IntentTools.isAppInstalled(mFlyApplication, FinalUtils.QQ_PACKAGE_NAME)) {
            QbSdk.initX5Environment(getContext(), null);
        }
    }


    /**
     * 友盟分享
     */
    private void initShare() {
        Config.DEBUG = false;
        UMShareAPI.get(getContext());

        //微信 appid appsecret
        PlatformConfig.setWeixin("wxe3c42a32fb7853fa", "4dde1895fdd5e666715942f064626d51");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("117568512", "c52f467c7879ccc480f169f23942659a", "http://sns.whalecloud.com");
        // QQ和Qzone appid appkey;
        PlatformConfig.setQQZone("1104775592", "rrdiIR6IBR7OOqqB");
    }

}
