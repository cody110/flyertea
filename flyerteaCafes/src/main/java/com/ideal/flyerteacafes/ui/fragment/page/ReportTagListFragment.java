package com.ideal.flyerteacafes.ui.fragment.page;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.HotelAdapter;
import com.ideal.flyerteacafes.adapters.RaidersListAdapter;
import com.ideal.flyerteacafes.adapters.ReportAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;
import com.ideal.flyerteacafes.ui.activity.presenter.ArticlePresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ArticleContentActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.ui.fragment.presenter.ReportTagListPresenter;

import java.util.List;

/**
 * Created by fly on 2017/11/30.
 */

public class ReportTagListFragment extends NewPullRefreshFragment<ArticleTabBean> {


    @Override
    public void initViews() {
        super.initViews();
        setListviewDivider(getResources().getDrawable(R.drawable.line_margin_left_right));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArticleTabBean bean = adapter.getItem(i);
                Bundle bundle = new Bundle();
                bundle.putString(ArticlePresenter.BUNDLE_AID, String.valueOf(bean.getAid()));
                jumpActivity(ArticleContentActivity.class, bundle);
            }
        });
    }

    @Override
    protected CommonAdapter<ArticleTabBean> createAdapter(List<ArticleTabBean> datas) {
        return new HotelAdapter(mActivity, datas, R.layout.item_hotel_report);
    }

    @Override
    protected PullRefreshPresenter<ArticleTabBean> createPresenter() {
        return new ReportTagListPresenter();
    }
}
