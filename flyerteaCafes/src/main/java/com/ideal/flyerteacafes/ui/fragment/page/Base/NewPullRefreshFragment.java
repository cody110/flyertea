package com.ideal.flyerteacafes.ui.fragment.page.Base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView;
import com.ideal.flyerteacafes.ui.fragment.interfaces.IPullRefresh;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.tools.V;

import java.util.List;


/**
 * Created by fly on 2016/4/5.
 */
public abstract class NewPullRefreshFragment<T> extends MVPBaseFragment<IPullRefresh<T>, PullRefreshPresenter<T>> implements IPullRefresh<T>, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    public View mView;
    public PullToRefreshView pullToRefreshView;
    public ListView listView;
    public LinearLayout topLayout;
    public TextView normalRemindTv;
    public RelativeLayout normalLayout;

    public CommonAdapter<T> adapter;
    private int listviewHeight = 0;
    public List<T> datas;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            mPresenter.dataChangeHeaderRefresh();
            return mView;
        }

        mView = inflater.inflate(R.layout.fragment_refreshview_listview, container, false);
        topLayout = V.f(mView, R.id.fragment_refreshview_toplayout);
        pullToRefreshView = V.f(mView, R.id.fragment_refreshview);
        listView = V.f(mView, R.id.fragment_refreshview_listview);
        normalLayout = V.f(mView, R.id.fragment_refreshview_normal_layout);
        normalRemindTv = V.f(mView, R.id.fragment_refreshview_normal_tv);
        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        listView.post(new Runnable() {
            @Override
            public void run() {
                listviewHeight = listView.getHeight();
            }
        });
        initViews();
        initVariables();
        mPresenter.init(mActivity);

        LogFly.e("onCreateView");

        return mView;
    }

    /**
     * 刷新view上
     *
     * @param layoutId
     * @return
     */
    protected View addTopLayout(int layoutId) {
        View view = LayoutInflater.from(getActivity()).inflate(layoutId, null);
        topLayout.removeAllViews();
        topLayout.addView(view);
        return view;
    }

    /**
     * 设置空白页
     *
     * @param resource
     */
    protected void addNormalLayout(int resource) {
        normalLayout.removeAllViews();
        RelativeLayout noMessage = (RelativeLayout) LayoutInflater.from(mActivity).inflate(resource, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        normalLayout.addView(noMessage, params);
        normalLayout.setVisibility(View.GONE);
    }

    protected abstract CommonAdapter<T> createAdapter(List<T> datas);

    @Override
    public void pullToRefreshViewComplete() {
        pullToRefreshView.onFooterRefreshComplete();
        pullToRefreshView.onHeaderRefreshComplete();
    }

    @Override
    public void callbackData(List<T> datas) {
        this.datas = datas;
        if (adapter == null) {
            adapter = createAdapter(datas);
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void headerRefreshSetListviewScrollLocation() {
        listView.setSelection(0);
    }

    @Override
    public void footerRefreshSetListviewScrollLocation(int oldSize) {
        oldSize += listView.getHeaderViewsCount();
        listView.setSelectionFromTop(oldSize, listviewHeight - pullToRefreshView.mFooterViewHeight);
    }


    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPresenter.onFooterRefresh();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        mPresenter.onHeaderRefresh();
    }

    public void setListviewDividerHeight(int height) {
        listView.setDividerHeight(height);
    }

    public void setListviewDivider(Drawable drawable) {
        listView.setDivider(drawable);
    }

    /**
     * 设置边距
     *
     * @param marginLeft
     * @param marginTop
     * @param marginRight
     * @param marginBottom
     */
    public void setListViewMargins(int marginLeft, int marginTop, int marginRight, int marginBottom) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) listView.getLayoutParams();
        params.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        listView.setLayoutParams(params);
    }

    /**
     * 手动调用显示头部下拉刷新
     */
    @Override
    public void headerRefreshing() {
        if (pullToRefreshView != null) {
            pullToRefreshView.headerRefreshing();
            LogFly.e("刷新");
        } else {
            LogFly.e("不刷");
        }
    }

    @Override
    public void notMoreData() {

    }
}
