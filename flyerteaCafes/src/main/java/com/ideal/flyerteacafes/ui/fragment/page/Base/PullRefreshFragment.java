package com.ideal.flyerteacafes.ui.fragment.page.Base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView;
import com.ideal.flyerteacafes.ui.fragment.interfaces.IPullRefresh;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.ui.layout.FlyDefaultFooter;
import com.ideal.flyerteacafes.ui.layout.FlyDefaultHeader;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.tools.V;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * Created by fly on 2016/4/5.
 */
public abstract class PullRefreshFragment<T> extends MVPBaseFragment<IPullRefresh<T>, PullRefreshPresenter<T>> implements IPullRefresh<T> {

    public View mView;
    public PtrClassicFrameLayout pullToRefreshView;
    public ListView listView;
    public LinearLayout topLayout;
    public TextView normalRemindTv;

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
            return mView;
        }

        mView = inflater.inflate(R.layout.fragment_refreshview_listview_2, container, false);
        topLayout = V.f(mView, R.id.fragment_refreshview_toplayout);
        pullToRefreshView = V.f(mView, R.id.fragment_refreshview);
        listView = V.f(mView, R.id.fragment_refreshview_listview);
        normalRemindTv = V.f(mView, R.id.fragment_refreshview_normal_tv);
        listView.post(new Runnable() {
            @Override
            public void run() {
                listviewHeight = listView.getHeight();
            }
        });

        FlyDefaultHeader headerView = new FlyDefaultHeader(mActivity);
        pullToRefreshView.setHeaderView(headerView);
        pullToRefreshView.addPtrUIHandler(headerView);


        pullToRefreshView.setPtrHandler(new PtrHandler() {

            //下拉完成 需要加载数据时触发
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPresenter.onHeaderRefresh();
            }

            /**
             * 检查是否可以执行下来刷新，比如列表为空或者列表第一项在最上面时。
             */
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // 默认实现，根据实际情况做改动
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        pullToRefreshView.setLastUpdateTimeRelateObject(this);

        // the following are default settings
        pullToRefreshView.setResistance(1.7f); //阻尼系数 默认: 1.7f，越大，感觉下拉时越吃力。
        pullToRefreshView.setRatioOfHeaderHeightToRefresh(1.2f);//触发刷新时移动的位置比例 默认，1.2f，移动达到头部高度1.2倍时可触发刷新操作。
        pullToRefreshView.setDurationToClose(200);//回弹延时 默认 200ms，回弹到刷新高度所用时间
        pullToRefreshView.setDurationToCloseHeader(500);//头部回弹时间 默认1000ms
        // default is false
        pullToRefreshView.setPullToRefresh(false);// 刷新是保持头部 默认值 true.
        // default is true
        pullToRefreshView.setKeepHeaderWhenRefresh(true);//下拉刷新 / 释放刷新 默认为释放刷新*/


        View footerView = new FlyDefaultFooter(mActivity);
        listView.addFooterView(footerView);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (absListView.getLastVisiblePosition() == absListView.getCount() - 1) {
                        mPresenter.onFooterRefresh();
                        LogFly.e("onFooterRefresh");
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });


        initViews();
        initVariables();
        mPresenter.init(mActivity);
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

    protected abstract CommonAdapter<T> createAdapter(List<T> datas);

    @Override
    public void pullToRefreshViewComplete() {
        pullToRefreshView.refreshComplete();
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
//        oldSize += listView.getHeaderViewsCount();
//        listView.setSelectionFromTop(oldSize, listviewHeight);
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
        if (pullToRefreshView != null)
            pullToRefreshView.autoRefresh(true);
    }


}
