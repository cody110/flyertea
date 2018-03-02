package com.ideal.flyerteacafes.ui.activity.web;

import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.caff.UpgradeManger;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaWebView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * weba activity
 *
 * @author fly
 */
@ContentView(R.layout.activity_cordova_web)
public class CordovaWebActivity extends BaseActivity {

    @ViewInject(R.id.rebate_webview)
    private CordovaWebView webview;
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
        Config.init(this);
        x.view().inject(this);
        boolean showTitle = getIntent().getBooleanExtra("showTitle", true);
        if (!showTitle)
            actionHead.setVisibility(View.GONE);
        url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        WidgetUtils.setText(actionBarCenter, title);
        if (UserManger.isLogin()) {
            synCookies();
        }

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webview.loadUrl(DataUtils.urlAppedToken(url));

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                if (url.endsWith("apk"))
                    UpgradeManger.getInstance().downLoadApk(url);
            }
        });

        webview.setWebChromeClient(new WebChromeClient() {
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
