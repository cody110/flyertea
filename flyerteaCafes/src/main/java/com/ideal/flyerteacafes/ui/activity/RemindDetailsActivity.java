package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.utils.DataUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_remind_details)
public class RemindDetailsActivity extends BaseActivity {

    @ViewInject(R.id.include_left_title_text)
    private TextView titleView;
    @ViewInject(R.id.remind_details_webview)
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        titleView.setText(getResources().getString(
                R.string.title_name_reminddetails));
        String msg = getIntent().getStringExtra("msg");
        if (TextUtils.isEmpty(msg)) return;

        //相对路劲替换为绝对路径
        if (!msg.contains("<a href=\"http://")&&!msg.contains("<a href=\"https://")) {
            msg = msg.replace("<a href=\"", "<a href=\"http://www.flyertea.com/");
        }


        mWebView.setWebViewClient(new WebViewClient() {

            // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。
            // 这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                DataUtils.webViewClickUrlDispose(url, RemindDetailsActivity.this);
                return true;
            }

        });

        mWebView.loadDataWithBaseURL(null, msg, "text/html", "UTF-8", null);
    }


    @Event(R.id.include_left_title_back_layout)
    private void closePage(View v) {
        finish();
    }

}
