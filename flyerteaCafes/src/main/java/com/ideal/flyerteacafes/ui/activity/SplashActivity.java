package com.ideal.flyerteacafes.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.AdvertStatisticsManger;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.StartUpManger;
import com.ideal.flyerteacafes.dal.BaseHelper;
import com.ideal.flyerteacafes.model.entity.StartAdvertBean;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.HttpTools;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;
import com.umeng.analytics.MobclickAgent;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * 欢迎页面
 *
 * @author fly
 */
@ContentView(R.layout.activity_splash)
public class SplashActivity extends InstrumentedActivity implements SplashADListener {


    private SplashAD splashAD;
    @ViewInject(R.id.splash_container)
    private ViewGroup container;

    public boolean canJump = false;


    @ViewInject(R.id.splash_advert_img)
    ImageView splashHolder;
    @ViewInject(R.id.mll_splash_bg)
    LinearLayout mll_splash_bg;
    @ViewInject(R.id.mtv_splash_time)
    TextView mtv_splash_time;

    private SharedPreferencesString preferences;
    private StartAdvertBean startAdvertBean;

    private int count;
    private String type = "";
    private String value = "";

    private Handler mHandler;
    private Runnable mRunnable;

    //true=后台进入
    private boolean isBackstageEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);


        preferences = SharedPreferencesString.getInstances();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        type = getIntent().getStringExtra("type");
        isBackstageEntry = TextUtils.equals(type, IntentKey.TYPE_ISACTIVE_SHOW_ADV);

        // 应用从后台到前台，显示只显示广告，不需要数据初始化
        if (!isBackstageEntry) {
            count = SharedPreferencesString.getInstances().getIntToKey("count");
            value = getIntent().getStringExtra("key");

            if (type == null) type = "";
            if (value == null) value = "";

            if (preferences.getIntToKey("pushMode") == 0) {
                JPushInterface.stopPush(this);
            }

            if (!HttpTools.isNetworkAvailable(SplashActivity.this)) {
                Toast.makeText(getApplicationContext(), getString(R.string.network_no_connection), Utils.TOASTTIME).show();
            }

            //应用基础数据初始化
            StartUpManger.getInstance().init(this);

        }

        goHome();

    }


    @Event({R.id.mll_splash_bg, R.id.splash_advert_img})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.mll_splash_bg://跳过
                HashMap<String, String> map = new HashMap<>();
                if (startAdvertBean != null) {
                    map.put("aid", startAdvertBean.getOrder_id() + "");
                }
                MobclickAgent.onEvent(this, FinalUtils.EventId.splashAd_skip, map);
                if (isBackstageEntry) {
                    finish();
                } else {
                    toHomeActivity();
                }
                break;
            case R.id.splash_advert_img://启动页广告
                map = new HashMap<>();
                if (startAdvertBean != null) {
                    map.put("aid", startAdvertBean.getOrder_id() + "");
                    type = IntentKey.TYPE_ADVERT;
                    if (TextUtils.equals(startAdvertBean.getAdtype(), "code")) {
                        value = Utils.HtmlUrl.HTML_ADV + startAdvertBean.getOrder_id();
                    } else {
                        value = startAdvertBean.getUrl();
                    }
                }
                MobclickAgent.onEvent(this, FinalUtils.EventId.splashAd_click, map);

                if (isBackstageEntry) {
                    if (!TextUtils.isEmpty(value)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("url", value);
                        Intent intent = new Intent(this, TbsWebActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    finish();
                } else {
                    toHomeActivity();
                }

                break;
        }
    }

    /**
     * 倒计时
     */
    private void initSpashTime() {

        final int time = 5;
        Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                .take(time) //设置循环11次
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return time - aLong; //
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Long aLong) { //接受到一条就是会操作一次UI
                        mtv_splash_time.setText(aLong + "s");
                    }
                });
    }


    /**
     * 去首页的逻辑操作
     */
    private void goHome() {
//        if (isToLanding()) {
//            toLandingActivity();
//        } else {
        setStartAdvert();
        if (startAdvertBean == null) {
            fetchSplashAD();
        } else {
            mHandler = new Handler();
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    toHomeActivity();
                }
            };
            mHandler.postDelayed(mRunnable, 5000);
        }
//        }
    }


    /**
     * 跳转到首页
     */
    private void toHomeActivity() {
        Intent mainIntent = new Intent();
        mainIntent.setClass(SplashActivity.this, HomeActivity.class);
        mainIntent.putExtra("type", type);
        mainIntent.putExtra("key", value);
        SplashActivity.this.startActivity(mainIntent);
        SplashActivity.this.finish();
    }

    /**
     * 去引导页
     */
    private void toLandingActivity() {
        if (count == 0 && FlyerApplication.isToLandingActivity) {
            startActivity(new Intent(this, LandingActivity.class));
        } else {
            startActivity(new Intent(this, BootVideoActivity.class));
        }
        finish();
    }

    /**
     * 是否是去引导页
     *
     * @return
     */
    private boolean isToLanding() {
        return count == 0 && FlyerApplication.isToLandingActivity || SharedPreferencesString.getInstances().isFirstInstall();
    }


    /**
     * 设置启页广告
     */
    private void setStartAdvert() {
        startAdvertBean = BaseHelper.getInstance().getBeanByFirst(StartAdvertBean.class);
        if (startAdvertBean != null) {
            mll_splash_bg.setVisibility(View.VISIBLE);
            initSpashTime();
            ImageOptions imageOptions = new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.FIT_XY)
                    .setLoadingDrawableId(R.drawable.spalsh_bg)//加载中默认显示图片
                    .setFailureDrawableId(R.drawable.spalsh_bg)//加载失败后默认显示图片
                    .setUseMemCache(true)
                    .build();
            x.image().bind(splashHolder, startAdvertBean.getImg_path(), imageOptions);
            AdvertStatisticsManger.getInstance().executeCode(FlyerApplication.getContext(), startAdvertBean.getPvtrackcode());
        }
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null && mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
        super.onDestroy();
    }

    @Override
    public void onADPresent() {
        Log.i("AD_DEMO", "SplashADPresent");
        splashHolder.setVisibility(View.INVISIBLE); // 广告展示后一定要把预设的开屏图片隐藏起来
    }

    @Override
    public void onADClicked() {
        Log.i("AD_DEMO", "SplashADClicked");
    }

    /**
     * 倒计时回调，返回广告还将被展示的剩余时间。
     * 通过这个接口，开发者可以自行决定是否显示倒计时提示，或者还剩几秒的时候显示倒计时
     *
     * @param millisUntilFinished 剩余毫秒数
     */
    @Override
    public void onADTick(long millisUntilFinished) {
        Log.i("AD_DEMO", "SplashADTick " + millisUntilFinished + "ms");
        mtv_splash_time.setText(String.valueOf(Math.round(millisUntilFinished / 1000f)));
    }

    @Override
    public void onADDismissed() {
        Log.i("AD_DEMO", "SplashADDismissed");
        next();
    }

    @Override
    public void onNoAD(AdError error) {
        Log.i(
                "AD_DEMO",
                String.format("LoadSplashADFail, eCode=%d, errorMsg=%s", error.getErrorCode(),
                        error.getErrorMsg()));
        /** 如果加载广告失败，则直接跳转 */
        toHomeActivity();
    }

    /**
     * 设置一个变量来控制当前开屏页面是否可以跳转，当开屏广告为普链类广告时，点击会打开一个广告落地页，此时开发者还不能打开自己的App主页。当从广告落地页返回以后，
     * 才可以跳转到开发者自己的App主页；当开屏广告是App类广告时只会下载App。
     */
    private void next() {
        if (canJump) {
            toHomeActivity();
        } else {
            canJump = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        canJump = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (canJump) {
            next();
        }
        canJump = true;
    }

    /**
     * 开屏页一定要禁止用户对返回按钮的控制，否则将可能导致用户手动退出了App而广告无法正常曝光和计费
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 腾讯广告
     */
    private void fetchSplashAD() {
        mll_splash_bg.setVisibility(View.VISIBLE);
        splashAD = new SplashAD(this, container, mll_splash_bg, FinalUtils.AD_TENCENT_APPID, FinalUtils.AD_TENCENT_OPENING_SCREEN_ID, this, 10000);
    }


}
