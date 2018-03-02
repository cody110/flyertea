package com.ideal.flyerteacafes.ui.activity.myinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.WeiWangAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.CreditsBean;
import com.ideal.flyerteacafes.ui.activity.PullRefreshActivity;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.systembartint.SystemBarUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.V;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.List;

/**
 * Created by fly on 2017/5/26.
 */

public class WeiWangActivity extends PullRefreshActivity<CreditsBean> {


    @Override
    public void initViews() {
        super.initViews();
        SystemBarUtils.setStatusBarColor(this,R.color.app_bg_title);
        toolBar.setLeftImage(R.drawable.left_back_white);
        toolBar.setBackgroundResource(R.color.app_bg_title);
        toolBar.setTitleTextColorRes(R.color.white);
        toolBar.setTitle("我的威望");
        pullToRefreshView.setNeed_pull_down(false);
        setListviewDividerNull();
        View view = LayoutInflater.from(this).inflate(R.layout.header_weiwang, null);
        TextView weiwang_tv = V.f(view, R.id.weiwang_tv);
        weiwang_tv.setText(String.valueOf(UserManger.getUserInfo().getCredits()));
        listView.addHeaderView(view);


        V.f(view, R.id.get_weiwang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(MyTaskActivity.class, null);
            }
        });
        V.f(view, R.id.to_get_weiwang_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("url", Utils.HtmlUrl.HTML_HOT_SCORE);
                jumpActivity(TbsWebActivity.class, b);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) return;
                if (DataTools.getInteger(mPresenter.getDatas().get(i - 1).getRelatedid()) != 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("tid", mPresenter.getDatas().get(i - 1).getRelatedid());
                    jumpActivity(ThreadActivity.class, bundle);
                }
            }
        });
    }

    @Override
    protected CommonAdapter<CreditsBean> createAdapter(List<CreditsBean> datas) {
        return new WeiWangAdapter(this, datas, R.layout.item_my_weiwang);
    }

    @Override
    protected PullRefreshPresenter<CreditsBean> createPresenter() {
        return new PullRefreshPresenter<CreditsBean>() {
            @Override
            public void requestDatas() {
                FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_CREDITLIST);
                params.addQueryStringParameter("page", String.valueOf(page));
                XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.LIST_KEY_CREDITS, CreditsBean.class));
            }
        };
    }

}
