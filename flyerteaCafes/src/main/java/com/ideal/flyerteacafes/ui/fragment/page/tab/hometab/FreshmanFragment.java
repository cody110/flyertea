package com.ideal.flyerteacafes.ui.fragment.page.tab.hometab;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.FreshmanAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.ArticleBean;
import com.ideal.flyerteacafes.ui.activity.presenter.ArticlePresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ArticleContentActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.List;

/**
 * Created by fly on 2016/6/8.
 */
public class FreshmanFragment extends NewPullRefreshFragment<ArticleBean> {

    @Override
    public void initViews() {
        super.initViews();
        setListviewDivider(null);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArticleBean bean = adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString(ArticlePresenter.BUNDLE_AID, String.valueOf(bean.getAid()));
                jumpActivity(ArticleContentActivity.class, bundle);
            }
        });
    }

    @Override
    protected CommonAdapter<ArticleBean> createAdapter(List<ArticleBean> datas) {
        return new FreshmanAdapter(mActivity, datas, R.layout.listview_flydaily_item_layout);
    }

    @Override
    protected PullRefreshPresenter<ArticleBean> createPresenter() {
        return new PullRefreshPresenter<ArticleBean>() {
            @Override
            public void requestDatas() {
                FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_THREAD_XIAOBAI);
                params.addQueryStringParameter("page", String.valueOf(page));
                XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.LIST_KEY_LISTS, ArticleBean.class));
            }

        };
    }
}
