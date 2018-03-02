package com.ideal.flyerteacafes.ui.activity.web;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.UpgradeManger;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.apache.cordova.Config;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * weba activity
 *
 * @author fly
 */
@ContentView(R.layout.activity_tbs_web)
public class TbsWebActivity extends BaseActivity {

    @ViewInject(R.id.rebate_webview)
    private WebView webview;
    @ViewInject(R.id.actionbar_layout)
    private View actionHead;
    @ViewInject(R.id.actionbar_center)
    private TextView actionBarCenter;
    @ViewInject(R.id.tbs_web_pb)
    private ProgressBar progressBar;


    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        Config.init(this);
        boolean showTitle = getIntent().getBooleanExtra("showTitle", true);
        if (!showTitle)
            actionHead.setVisibility(View.GONE);
        url = getIntent().getStringExtra("url");

        if (TextUtils.isEmpty(url)) return;
        url = DataUtils.urlAppedParams(url, "fromapp", "1");

        //如果url中有 screenOrientation=landscape字段，强制横屏
        if (url.contains("screenOrientation=landscape")) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        String title = getIntent().getStringExtra("title");
        WidgetUtils.setText(actionBarCenter, title);
        if (UserManger.isLogin()) {
            synCookies();
        }

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        //扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);

        webview.loadUrl(urlAppedToken(url));


        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("flyertea://")) {
                    Intent intent = new Intent();
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                if (TextUtils.equals(url, "http://mi.flyertea.com/front/views/html/index.html")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", DataUtils.getFeimiUrl("http://mi.flyertea.com/front/views/html/index.html"));
                    jumpActivity(TbsWebActivity.class, bundle);
                    return true;
                }

                String def_url1 = "http://www.flyertea.com/thread";

                if (url.contains(def_url1)) {
                    String[] array = url.split("-");
                    if (array.length > 2) {
                        String tid = array[1];
                        Bundle bundle = new Bundle();
                        bundle.putString(ThreadPresenter.BUNDLE_TID, tid);
                        jumpActivity(ThreadActivity.class, bundle);
                        return true;
                    }
                }

                view.loadUrl(url);
                return true;
            }
        });

        webview.setDownloadListener(new com.tencent.smtt.sdk.DownloadListener() {
            @Override
            public void onDownloadStart(String apkUrl, String s1, String s2, String s3, long l) {
                if (apkUrl.endsWith("apk"))
                    UpgradeManger.getInstance().downLoadApk(apkUrl);
            }
        });

        webview.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (progressBar.getVisibility() == View.GONE)
                        progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(i);
                }
                super.onProgressChanged(webView, i);
            }
        });

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        String message = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? "屏幕设置为：横屏" : "屏幕设置为：竖屏";
        LogFly.e(message);
    }

    /**
     * 加密token
     *
     * @param url
     * @return
     */
    public String urlAppedToken(String url) {
        return DataUtils.urlAppedToken(url);
    }

    @Event({R.id.actionbar_back, R.id.actionbar_finish})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.actionbar_back:
                onBackPressed();
                break;

            case R.id.actionbar_finish:
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.webview != null) {
            this.webview.destroy();
        }
    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 同步一下cookie
     */
    public void synCookies() {

        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();// 移除
        CookieSyncManager.getInstance().sync();
        String[] cookies = UserManger.getCookie().split(";");
        for (String cookie : cookies) {
            if (cookie != null && !cookie.equals(""))
                cookieManager.setCookie("http://www.flyertea.com", cookie);
        }
        CookieSyncManager.getInstance().sync();
    }

}
