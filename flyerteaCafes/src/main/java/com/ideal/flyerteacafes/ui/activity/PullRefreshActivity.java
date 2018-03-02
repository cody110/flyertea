package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.ui.activity.base.MVPBaseActivity;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView;
import com.ideal.flyerteacafes.ui.fragment.interfaces.IPullRefresh;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.ui.view.ToolBar;
import com.ideal.flyerteacafes.utils.tools.V;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.List;

/**
 * Created by fly on 2017/5/26.
 */
@ContentView(R.layout.activity_pullrefresh)
public abstract class PullRefreshActivity<T> extends MVPBaseActivity<IPullRefresh<T>, PullRefreshPresenter<T>> implements IPullRefresh<T>, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {


    public ToolBar toolBar;
    public PullToRefreshView pullToRefreshView;
    public ListView listView;
    public LinearLayout topLayout;
    public TextView normalRemindTv;

    public CommonAdapter<T> adapter;
    private int listviewHeight = 0;
    public List<T> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        toolBar = V.f(this, R.id.toolbar);
        topLayout = V.f(this, R.id.fragment_refreshview_toplayout);
        pullToRefreshView = V.f(this, R.id.fragment_refreshview);
        listView = V.f(this, R.id.fragment_refreshview_listview);
        normalRemindTv = V.f(this, R.id.fragment_refreshview_normal_tv);
        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        listView.post(new Runnable() {
            @Override
            public void run() {
                listviewHeight = listView.getHeight();
            }
        });

        toolBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initViews();
        initVariables();
        mPresenter.init(this);
    }

    /**
     * 刷新view上
     *
     * @param layoutId
     * @return
     */
    protected View addTopLayout(int layoutId) {
        View view = LayoutInflater.from(this).inflate(layoutId, null);
        topLayout.removeAllViews();
        topLayout.addView(view);
        return view;
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
        listView.smoothScrollToPosition(0);//移动到首部
    }

    @Override
    public void footerRefreshSetListviewScrollLocation(int oldSize) {
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

    public void setListviewDividerColor(int color) {
        listView.setDivider(getResources().getDrawable(color));
    }

    public void setListviewDividerNull() {
        listView.setDivider(null);
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
        pullToRefreshView.headerRefreshing();
    }

    @Override
    public void notMoreData() {

    }
}
