package com.ideal.flyerteacafes.ui.fragment.page.tab.reportlist;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.ReportAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;
import com.ideal.flyerteacafes.ui.activity.presenter.ArticlePresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ArticleContentActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.ui.fragment.presenter.ReportPresenter;

import java.util.List;

/**
 * Created by fly on 2016/6/2.
 */
public class ReportListFragment extends NewPullRefreshFragment<ArticleTabBean> {


    public static ReportListFragment setArguments(ReportListFragment f, String url) {
        if (f == null || url == null) return f;
        Bundle bundle = new Bundle();
        bundle.putSerializable("url", url);
        f.setArguments(bundle);
        return f;
    }

    public static ReportListFragment setArguments(ReportListFragment f, String url, String key, String value) {
        if (f == null || url == null) return f;
        Bundle bundle = new Bundle();
        bundle.putSerializable("url", url);
        bundle.putString("key", key);
        bundle.putString("value", value);
        f.setArguments(bundle);
        return f;
    }

    private String detailsTitle;

    public String getFmTagName() {
        return getRequestUrl();
    }

    @Override
    public void initViews() {
        super.initViews();
        setListviewDivider(getResources().getDrawable(R.drawable.line_margin_left_right));

        if (createItemClickListener() == null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ArticleTabBean bean = adapter.getItem(position);
                    Bundle bundle = new Bundle();
                    bundle.putString(ArticlePresenter.BUNDLE_AID, String.valueOf(bean.getAid()));
                    bundle.putString(ThreadPresenter.BUNDLE_TITLE, getDetailsTitle());
                    jumpActivity(ArticleContentActivity.class, bundle);

                }
            });
        } else {
            listView.setOnItemClickListener(createItemClickListener());
        }

        listView.setOnScrollListener(createScrollListener());

    }


    public AdapterView.OnItemClickListener createItemClickListener() {
        return null;
    }

    public AbsListView.OnScrollListener createScrollListener() {
        return null;
    }

    @Override
    protected CommonAdapter<ArticleTabBean> createAdapter(List<ArticleTabBean> datas) {
        return new ReportAdapter(mActivity, datas, R.layout.listview_flydaily_item_da_layout);
    }


    ReportPresenter myPresenter;

    @Override
    protected PullRefreshPresenter<ArticleTabBean> createPresenter() {
        return myPresenter = new ReportPresenter().setRequestUrl(getRequestUrl()).setIGetSortid(iGetSortid).setTypeKey(getTypeKey()).setTypeValue(getTypeValue());
    }

    private String getRequestUrl() {
        if (getArguments() == null) return null;
        return getArguments().getString("url");
    }

    private String getTypeKey() {
        if (getArguments() == null) return null;
        return getArguments().getString("key");
    }

    private String getTypeValue() {
        if (getArguments() == null) return null;
        return getArguments().getString("value");
    }


    public void setDetailsTitle(String title) {
        detailsTitle = title;
    }

    public String getDetailsTitle() {
        return detailsTitle;
    }

    public void setType(String key, String value) {
        myPresenter.setType(key, value);
    }

    ReportPresenter.IGetSortid iGetSortid;

    public void setIGetSortid(final ReportPresenter.IGetSortid iGetSortid) {
        this.iGetSortid = iGetSortid;
        if (myPresenter != null) {
            myPresenter.setIGetSortid(iGetSortid);
        }
    }

    public void setSort(String sort) {
        myPresenter.setSort(sort);
    }
}
