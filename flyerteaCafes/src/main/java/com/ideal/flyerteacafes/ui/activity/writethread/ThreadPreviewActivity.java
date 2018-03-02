package com.ideal.flyerteacafes.ui.activity.writethread;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.loca.TuwenInfo;
import com.ideal.flyerteacafes.ui.activity.base.MVPBaseActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IThreadPreview;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPreviewPresenter;
import com.ideal.flyerteacafes.ui.activity.video.PlayVideoActivity;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.ideal.flyerteacafes.utils.LogFly;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by fly on 2017/3/14.
 */
@ContentView(R.layout.activity_thread_preview)
public class ThreadPreviewActivity extends MVPBaseActivity<IThreadPreview, ThreadPreviewPresenter> implements IThreadPreview {

    @ViewInject(R.id.webview)
    WebView webView;

    public static final String BUNDLE_CONTENT = "bundle_content";
    public static final String BUNDLE_TITLE = "bundle_title";
    public static final String BUNDLE_SUBJECT = "bundle_subject";
    public static final String BUNDLE_FORUMNAME = "bundle_forumname";
    public static final String BUNDLE_LOCATION = "bundle_location";

    public static String BUNDLE_FID_1 = "fid1", BUNDLE_FID_2 = "fid2", BUNDLE_FID_3 = "fid3", BUNDLE_HOTELID = "hotelid";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initWebview();
        mPresenter.init(this);
    }

    @Override
    protected ThreadPreviewPresenter createPresenter() {
        return new ThreadPreviewPresenter();
    }

    @Event({R.id.toolbar_left, R.id.toolbar_right})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left:
                finish();
                break;

        }
    }


    @Override
    public void bind(String datas) {
        webView.loadDataWithBaseURL(null, datas, "text/html", "UTF-8", null);
    }

    private void initWebview() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new JsInterface(), "android");
    }

    public class JsInterface {
        @JavascriptInterface
        public void clickPlay(String id) {
            if (!TextUtils.isEmpty(id)) {
                if (id.contains("-")) {
                    LogFly.e(id);
                    id = id.substring(id.indexOf("-") + 1);
                    LogFly.e(id);
                    LogFly.e("----------------");
                    for (TuwenInfo info : mPresenter.datas) {
                        LogFly.e(info.getVideoInfo().getVids());
                        if (TextUtils.equals(info.getVideoInfo().getVids(), id)) {
                            Bundle bundle = new Bundle();
                            bundle.putString(IntentKey.VIDEO_URL, info.getVideoPath());
                            jumpActivity(PlayVideoActivity.class, bundle);
                            break;
                        }
                    }
                }
            }

        }
    }
}
