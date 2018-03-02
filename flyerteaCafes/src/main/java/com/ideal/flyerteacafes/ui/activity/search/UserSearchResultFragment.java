package com.ideal.flyerteacafes.ui.activity.search;

import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.SearchUserAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.model.entity.SearchUserBean;
import com.ideal.flyerteacafes.ui.activity.presenter.UserSearchResultPresenter;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.TaskUtil;

import java.util.List;

/**
 * Created by fly on 2018/1/21.
 */

public class UserSearchResultFragment extends NewPullRefreshFragment<SearchUserBean> {

    SearchResultFragment.ISearchResultFragment iSearchResultFragment;

    @Override
    public void initViews() {
        super.initViews();
        setListviewDivider(getResources().getDrawable(R.drawable.line_margin_left_right));
    }


    @Override
    public void callbackData(List<SearchUserBean> datas) {
        this.datas = datas;
        if (adapter == null) {
            adapter = createAdapter(datas);
            listView.setAdapter(adapter);
        }
        ((SearchUserAdapter) adapter).setSearchKey(getPresenter().getSearchKey());
    }

    @Override
    protected CommonAdapter<SearchUserBean> createAdapter(List<SearchUserBean> datas) {
        return new SearchUserAdapter(mActivity, datas, R.layout.item_search_user);
    }


    public UserSearchResultPresenter getPresenter() {
        return (UserSearchResultPresenter) mPresenter;
    }

    @Override
    protected PullRefreshPresenter<SearchUserBean> createPresenter() {
        return new UserSearchResultPresenter();
    }

    @Override
    public void pullToRefreshViewComplete() {
        super.pullToRefreshViewComplete();
    }

    public void setSearchKey(final String searchKey, SearchResultFragment.ISearchResultFragment iSearchResultFragment) {
        this.iSearchResultFragment = iSearchResultFragment;
        TaskUtil.postOnUiThread(new Runnable() {
            @Override
            public void run() {
                getPresenter().setSearchKey(searchKey);
            }
        });
    }

    @Override
    public void notMoreData() {
        super.notMoreData();
        if (iSearchResultFragment != null) {
            iSearchResultFragment.notData();
        }
    }
}
