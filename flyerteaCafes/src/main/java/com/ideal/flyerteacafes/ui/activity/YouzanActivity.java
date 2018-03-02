/*
 * author: lachang@youzan.com
 * Copyright (C) 2016 Youzan, Inc. All Rights Reserved.
 */
package com.ideal.flyerteacafes.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.DataCallback;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.model.entity.YouzanBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;
import com.youzan.sdk.YouzanToken;
import com.youzan.sdk.event.AbsAuthEvent;
import com.youzan.sdk.event.AbsChooserEvent;
import com.youzan.sdk.event.AbsShareEvent;
import com.youzan.sdk.model.goods.GoodsShareModel;
import com.youzan.sdk.web.plugin.YouzanBrowser;
import com.youzan.sdk.web.plugin.YouzanClient;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_store)
public class YouzanActivity extends BaseActivity {
    public static final String SIGN_URL = "url";
    private static final int CODE_REQUEST_LOGIN = 0x101;
    @ViewInject(R.id.store_webview)
    private YouzanBrowser mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        String url = getIntent().getStringExtra(SIGN_URL);

        setupYouzanView(mView);

        //替换成需要展示入口的链接
        mView.loadUrl(url);
    }

    @Event(R.id.toolbar_left)
    private void click(View v) {
        finish();
    }


    private void setupYouzanView(YouzanClient client) {
        //订阅认证事件
        client.subscribe(new AbsAuthEvent() {
            /**
             * 有赞SDK认证回调.
             * 在加载有赞的页面时, SDK相应会回调该方法.
             *
             * 从自己的服务器上请求同步认证后组装成{@link com.youzan.sdk.YouzanToken}, 调用{code view.sync(token);}同步信息.
             *
             * @param view 发起回调的视图
             * @param needLogin 表示当下行为是否需要需要用户角色的认证信息, True需要.
             */
            @Override
            public void call(View view, boolean needLogin) {
                /**
                 * <pre>
                 *     建议代码逻辑:
                 *
                 *     判断App内的用户是否登录?
                 *       => 已登录: 请求带用户角色的认证信息(login接口);
                 *       => 未登录: needLogin为true, 唤起App内登录界面, 请求带用户角色的认证信息(login接口);
                 *       => 未登录: needLogin为false, 请求不带用户角色的认证信息(initToken接口).
                 *  </pre>
                 */

                //实现代码略...


                if (UserManger.isLogin()) {
                    syncYzUser();
                } else if (needLogin) {
                    Intent intent = new Intent(YouzanActivity.this, LoginVideoActivity.class);
                    startActivityForResult(intent, CODE_REQUEST_LOGIN);
                } else {
                    syncYzInit();
                }

            }
        });

        //订阅文件选择事件
        client.subscribe(new AbsChooserEvent() {
            @Override
            public void call(View view, Intent intent, int requestCode) throws ActivityNotFoundException {
                //调用系统图片选择器
                startActivity(intent);
            }
        });

        //订阅分享事件
        client.subscribe(new AbsShareEvent() {
            @Override
            public void call(View view, GoodsShareModel data) {
                /**
                 * 在获取数据后, 可以使用其他分享SDK来提高分享体验.
                 * 这里调用系统分享来简单演示分享的过程.
                 */
                String content = String.format("%s %s", data.getDesc(), data.getLink());
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, content);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, data.getTitle());
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            /**
             * 用户登录成功返回, 从自己的服务器上请求同步认证后组装成{@link com.youzan.sdk.YouzanToken},
             * 调用{code view.sync(token);}同步信息.
             */
            if (CODE_REQUEST_LOGIN == requestCode) {
                //mView.sync(token);
                syncYzUser();
            } else {
                //处理文件上传
                mView.receiveFile(requestCode, data);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!mView.pageGoBack()) {
            super.onBackPressed();
        }
    }

    private void syncYzUser() {
        if (UserManger.getInstance().getYouzanLoginBean() == null) {
            requestYouzanLogin();
        } else {
            YouzanToken token = new YouzanToken();
            token.setAccessToken(UserManger.getInstance().getYouzanLoginBean().getAccess_token());
            token.setCookieKey(UserManger.getInstance().getYouzanLoginBean().getCookie_key());
            token.setCookieValue(UserManger.getInstance().getYouzanLoginBean().getCookie_value());
            mView.sync(token);
        }
    }

    private void syncYzInit() {
        if (UserManger.getInstance().getYouzanInitBean() == null) {
            requestYouzanInit();
        } else {
            YouzanToken token = new YouzanToken();
            token.setAccessToken(UserManger.getInstance().getYouzanInitBean().getAccess_token());
            token.setCookieKey(UserManger.getInstance().getYouzanInitBean().getCookie_key());
            token.setCookieValue(UserManger.getInstance().getYouzanInitBean().getCookie_value());
            mView.sync(token);
        }
    }


    private void requestYouzanLogin() {
        showDialog();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_YOUZAN_LOGIN);
        XutilsHttp.Get(params, new DataCallback<YouzanBean>() {

            @Override
            public void flySuccess(DataBean<YouzanBean> result) {
                if (JsonAnalysis.isSuccessEquals1By2(result.getData())) {
                    if (result.getDataBean() != null) {
                        UserManger.getInstance().setYouzanLoginBean(result.getDataBean());
                        syncYzUser();
                    }
                } else {
                    ToastUtils.showToast(result.getMessage());
                }
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialogDismiss();
            }
        });
    }


    private void requestYouzanInit() {
        showDialog();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_YOUZAN_INIT);
        XutilsHttp.Get(params, new DataCallback<YouzanBean>() {


            @Override
            public void flySuccess(DataBean<YouzanBean> result) {
                if (JsonAnalysis.isSuccessEquals1By2(result.getData())) {
                    UserManger.getInstance().setYouzanInitBean(result.getDataBean());
                } else {
                    ToastUtils.showToast(result.getMessage());
                }

            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                dialogDismiss();
            }
        });
    }

}
