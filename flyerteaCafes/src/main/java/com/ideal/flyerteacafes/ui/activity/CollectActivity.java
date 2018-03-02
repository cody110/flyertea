package com.ideal.flyerteacafes.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.MyCollectAdapter;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.BaseBean;
import com.ideal.flyerteacafes.model.entity.CollectionBean;
import com.ideal.flyerteacafes.model.entity.ListObjectBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView2;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView2.OnFooterRefreshListener2;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView2.OnHeaderRefreshListener2;
import com.ideal.flyerteacafes.ui.view.ToolBar;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏页面
 *
 * @author fly
 */
@ContentView(R.layout.activity_collect)
public class CollectActivity extends BaseActivity implements OnHeaderRefreshListener2, OnFooterRefreshListener2 {

    @ViewInject(R.id.toolbar)
    private ToolBar mToolbar;
    @ViewInject(R.id.collect_refreshview_listview)
    private SwipeMenuListView mListview;
    @ViewInject(R.id.collect_refreshview)
    private PullToRefreshView2 mPullToRefreshView;

    private int page = FinalUtils.START_PAGE;
    List<CollectionBean> collectList = new ArrayList<CollectionBean>();

    private MyCollectAdapter adapter;

    private int pos = -1;// 记录，点击删除项的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mToolbar.setTitle(getString(R.string.title_name_my_collect));
        initListview();
        requestMyfavthread();
    }

    @Event(R.id.toolbar_left)
    private void closePage(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Event(value = R.id.collect_refreshview_listview, type = AdapterView.OnItemClickListener.class)
    private void toPostDetails(AdapterView<?> parent, View view, int position,
                               long id) {
        Bundle bundle = new Bundle();
        bundle.putString(ThreadPresenter.BUNDLE_TID, String.valueOf(collectList.get(position).getTid()));
        jumpActivity(ThreadActivity.class, bundle);
    }

    /**
     * 初始化listview
     */
    private void initListview() {
        adapter = new MyCollectAdapter(CollectActivity.this, collectList);
        mListview.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // TODO Auto-generated method stub
                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0x18,
                        0x5E)));
                item1.setWidth(DataTools.dp2px(90));
                item1.setTitle("删除");
                item1.setTitleColor(getResources().getColor(R.color.white));
                item1.setTitleSize(16);
                menu.addMenuItem(item1);
            }
        };

        mListview.setMenuCreator(creator);

        mListview.setOnMenuItemClickListener(new OnMenuItemClickListener() {

            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                // TODO Auto-generated method stub
                if (pos != -1)
                    return;
                pos = position;
                requestCancleCollect(collectList.get(position).getFavid() + "");
            }
        });
    }

    /**
     * 我的收藏
     */
    private void requestMyfavthread() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MY_COLLECT);
        params.addQueryStringParameter("page", page + "");
        XutilsHttp.Get(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                int position = 0;
                mPullToRefreshView.onHeaderRefreshComplete();
                mPullToRefreshView.onFooterRefreshComplete();
                ListObjectBean beanList = JsonAnalysis.getToMyCollect(result);
                List<CollectionBean> list = beanList.getDataList();
                if (page == FinalUtils.START_PAGE)
                    collectList.clear();
                else
                    position = collectList.size();
                if (!DataUtils.isEmpty(list))
                    collectList.addAll(list);
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                if (page != FinalUtils.START_PAGE)
                    mListview.setSelectionFromTop(position, (int) (FinalUtils.FROM_TOP * SharedPreferencesString.getInstances().getScale()));
            }
        });
    }

    /**
     * 取消收藏
     *
     * @param id
     */
    private void requestCancleCollect(final String id) {
        String formhash = SharedPreferencesString.getInstances(
                CollectActivity.this).getStringToKey("formhash");
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_CANCLE_COLLECT);
        params.addQueryStringParameter("favid", id);
        params.addQueryStringParameter("formhash", formhash);
        XutilsHttp.Get(params, new StringCallback() {
            @Override
            public void flySuccess(String result) {
                BaseBean bean = JsonAnalysis.getToMessage(result);
                if (bean.getCode().equals("do_success")) {
                    collectList.remove(pos);
                    adapter.notifyDataSetChanged();
                }
                BToast(bean.getMessage());
            }

            @Override
            public void flyFinished() {
                super.flyFinished();
                pos = -1;
            }
        });
    }

    @Override
    public void onFooterRefresh(PullToRefreshView2 view) {
        // TODO Auto-generated method stub
        page++;
        requestMyfavthread();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView2 view) {
        // TODO Auto-generated method stub
        page = FinalUtils.START_PAGE;
        requestMyfavthread();
    }

}
