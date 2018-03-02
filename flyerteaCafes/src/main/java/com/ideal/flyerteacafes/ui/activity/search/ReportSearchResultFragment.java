package com.ideal.flyerteacafes.ui.activity.search;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.ReportSearchResultAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.model.entity.ReportSearchResultBean;
import com.ideal.flyerteacafes.model.entity.SearchReportBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.IReportSearchResult;
import com.ideal.flyerteacafes.ui.activity.presenter.ReportSearchResultPresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadSearchResultPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ArticleContentActivity;
import com.ideal.flyerteacafes.ui.activity.threads.DiscountArticleContentActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.ui.layout.SearchScreenLayout;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.TaskUtil;
import com.ideal.flyerteacafes.utils.tools.DataTools;

import java.util.List;

/**
 * Created by fly on 2018/1/21.
 */

public class ReportSearchResultFragment extends NewPullRefreshFragment<SearchReportBean> implements IReportSearchResult<SearchReportBean> {


    SearchResultFragment.ISearchResultFragment iSearchResultFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attachView(this);
    }

    @Override
    public void initViews() {
        super.initViews();
        listView.setDivider(null);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("aid", adapter.getItem(position).getAid());
                if (TextUtils.equals(getPresenter().getType(), Marks.HttpSearchType.TYPE_PROMOTION)) {
                    jumpActivity(DiscountArticleContentActivity.class, bundle);
                } else {
                    jumpActivity(ArticleContentActivity.class, bundle);
                }

            }
        });
    }


    @Override
    public void callbackData(List<SearchReportBean> datas) {
        if (DataUtils.isEmpty(datas)) {
            if (iSearchResultFragment != null) {
                iSearchResultFragment.notData();
            }
        }
        this.datas = datas;
        if (adapter == null) {
            adapter = createAdapter(datas);
            listView.setAdapter(adapter);
        }
        ((ReportSearchResultAdapter) adapter).setSearchKey(getPresenter().getSearchKey());
    }

    @Override
    protected CommonAdapter<SearchReportBean> createAdapter(List<SearchReportBean> datas) {
        return new ReportSearchResultAdapter(mActivity, datas, R.layout.item_thread_serach_result);
    }

    @Override
    protected PullRefreshPresenter<SearchReportBean> createPresenter() {
        return new ReportSearchResultPresenter();
    }

    public ReportSearchResultPresenter getPresenter() {
        return (ReportSearchResultPresenter) mPresenter;
    }

    public void setSearchKey(final String searchKey, final String type, SearchResultFragment.ISearchResultFragment iSearchResultFragment) {
        this.iSearchResultFragment = iSearchResultFragment;
        TaskUtil.postOnUiThread(new Runnable() {
            @Override
            public void run() {
                getPresenter().setSearchKey(searchKey, type);
            }
        });
    }

    @Override
    public void clearPage() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
