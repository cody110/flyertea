package com.ideal.flyerteacafes.ui.fragment.page;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.RecyclerAdapter;
import com.ideal.flyerteacafes.adapters.base.RecyclerViewHolder;
import com.ideal.flyerteacafes.ui.controls.DividerItemDecoration;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.utils.tools.V;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fly on 2015/11/18.
 */
public class SwipeRefreshFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    protected SwipeRefreshLayout mSwipeRefreshWidget;
    protected RecyclerView mRecyclerView;

    int lastVisibleItem;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mSwipeRefreshWidget.setRefreshing(false);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swipe_refreshlayout, container, false);
        mSwipeRefreshWidget = V.f(view, R.id.swipe_refresh_widget);
        mRecyclerView = V.f(view, android.R.id.list);
        mSwipeRefreshWidget.setColorScheme(R.color.app_body_yellow, R.color.app_bg_grey,
                R.color.app_bg_title, R.color.app_body_black);
        mSwipeRefreshWidget.setOnRefreshListener(this);

        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        initVertical();

        // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
        handler.sendEmptyMessageDelayed(0, 6000);
        return view;
    }

    public void initVertical() {

        // 创建一个线性布局管理器
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        // 默认是Vertical，可以不写
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // 创建数据集
        String[] dataset = new String[100];
        for (int i = 0; i < dataset.length; i++) {
            dataset[i] = "item" + i;
        }
        // 创建Adapter，并指定数据集
//        MyAdapter adapter = new MyAdapter(dataset);
        final CodyAdapter adapter = new CodyAdapter(Arrays.asList(dataset), R.layout.listview_forum_one_level_layout);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) {
                    mSwipeRefreshWidget.setRefreshing(true);
                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    handler.sendEmptyMessageDelayed(0, 6000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }

        });

        // 设置Adapter
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshWidget.setRefreshing(true);
        handler.sendEmptyMessageDelayed(0, 6000);
    }

    public class CodyAdapter extends RecyclerAdapter<String> {

        public CodyAdapter(List<String> mDatas, int id) {
            super(mDatas, id);
        }

        @Override
        public void convert(RecyclerViewHolder holder, String s) {
            holder.holder.setText(R.id.forum_one_level_text, s);
        }
    }
}
