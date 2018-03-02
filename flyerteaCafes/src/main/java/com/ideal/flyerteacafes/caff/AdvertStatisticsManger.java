package com.ideal.flyerteacafes.caff;

import android.content.Context;
import android.webkit.WebView;

import com.ideal.flyerteacafes.utils.tools.FileUtil;
import com.ideal.flyerteacafes.utils.tools.StringTools;

/**
 * Created by fly on 2017/7/10.
 * 广告统计
 */

public class AdvertStatisticsManger {


    private static AdvertStatisticsManger instance;

    private AdvertStatisticsManger() {
    }

    public static AdvertStatisticsManger getInstance() {
        if (instance == null) {
            synchronized (AdvertStatisticsManger.class) {
                instance = new AdvertStatisticsManger();
            }
        }
        return instance;
    }


    public void executeCode(Context activity, String code) {
        if (activity == null || code == null) {
            return;
        }
        WebView webView = new WebView(activity);
        executeCode(activity, webView, code);
    }


    public void executeCode(Context activity, WebView webView, String code) {
        if (activity == null || code == null || webView == null) {
            return;
        }
        webView.getSettings().setJavaScriptEnabled(true);
        String template = FileUtil.readAssetsFile(activity, "advert.html");
        template = StringTools.replaceFirst(template, "<!-- code -->", code);
        webView.loadData(template, "text/html", "UTF-8");
    }


}

