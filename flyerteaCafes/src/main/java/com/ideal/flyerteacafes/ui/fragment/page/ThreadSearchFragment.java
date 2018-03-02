package com.ideal.flyerteacafes.ui.fragment.page;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.ThreadSearchAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.model.entity.ThreadSearchBean;
import com.ideal.flyerteacafes.ui.activity.search.ThreadSearchActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.ArticlePresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ArticleContentActivity;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.fragment.interfaces.IThreadSearchFm;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.ui.fragment.presenter.ThreadSearchPresenter;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.ideal.flyerteacafes.utils.WidgetUtils;


import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by fly on 2016/12/16.
 */

public class ThreadSearchFragment extends NewPullRefreshFragment<ThreadSearchBean> implements IThreadSearchFm {


    @ViewInject(R.id.search_thread_top_left_type)
    TextView search_thread_top_left_type;
    @ViewInject(R.id.search_thread_top_right_type)
    TextView search_thread_top_right_type;


    ThreadSearchActivity threadSearchActivity;


    ThreadSearchPresenter myPresenter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        threadSearchActivity = (ThreadSearchActivity) activity;
    }

    @Override
    public void initViews() {
        super.initViews();
        View topView = addTopLayout(R.layout.fm_search_thread_top_layout);
        x.view().inject(this, topView);
        setSelectView(search_thread_top_left_type);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (TextUtils.equals(myPresenter.type, IntentKey.ThreadSearch.TYPE_THREAD)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ThreadPresenter.BUNDLE_TID, datas.get(i).getTid());
                    jumpActivity(ThreadActivity.class, bundle);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(ArticlePresenter.BUNDLE_AID, datas.get(i).getAid());
                    bundle.putString(ThreadPresenter.BUNDLE_TITLE, datas.get(i).getForumname());
                    jumpActivity(ArticleContentActivity.class, bundle);
                }
            }
        });


        String type = mActivity.getIntent().getStringExtra(IntentKey.ThreadSearch.BUNDLE_KEY);
        if (TextUtils.equals(type, IntentKey.ThreadSearch.TYPE_REPORT)
                ||TextUtils.equals(type, IntentKey.ThreadSearch.TYPE_RAIDERS)) {
            WidgetUtils.setVisible(topView, false);
        }
    }

    @Override
    public void callbackData(List<ThreadSearchBean> datas) {
        this.datas = datas;
        if (adapter == null) {
            adapter = createAdapter(datas);
            listView.setAdapter(adapter);
        } else {
            ((ThreadSearchAdapter) adapter).setSearchKey(myPresenter.getKw());
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected CommonAdapter<ThreadSearchBean> createAdapter(List<ThreadSearchBean> datas) {
        return new ThreadSearchAdapter(mActivity, datas, R.layout.item_thread_search, myPresenter.getKw());
    }

    @Override
    protected PullRefreshPresenter<ThreadSearchBean> createPresenter() {
        return myPresenter = new ThreadSearchPresenter().setiThreadSearchFm(this);
    }


    @Event({R.id.search_thread_top_left, R.id.search_thread_top_right})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.search_thread_top_left:
                myPresenter.setOrderby("");
                setSelectView(search_thread_top_left_type);
                headerRefreshing();
                break;
            case R.id.search_thread_top_right:
                myPresenter.setOrderby("reply");
                setSelectView(search_thread_top_right_type);
                headerRefreshing();
                break;
        }
    }

    private void setSelectView(TextView tv) {
        search_thread_top_left_type.setBackground(null);
        search_thread_top_right_type.setBackground(null);
        search_thread_top_left_type.setTextColor(getResources().getColor(R.color.app_body_grey));
        search_thread_top_right_type.setTextColor(getResources().getColor(R.color.app_body_grey));

        tv.setBackgroundResource(R.drawable.top_grey_text_bottom_line_bg);
        tv.setTextColor(getResources().getColor(R.color.app_body_black));
    }

    public void setSearchKw(String kw) {
        myPresenter.setKw(kw);
        headerRefreshing();
    }


    @Override
    public void isNullResult(boolean bol) {
        threadSearchActivity.searchResultIsNull(bol);
    }
}
