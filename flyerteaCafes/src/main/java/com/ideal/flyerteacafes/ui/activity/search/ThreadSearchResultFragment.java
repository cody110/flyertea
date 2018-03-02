package com.ideal.flyerteacafes.ui.activity.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.ThreadSearchResultAdapter;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.ThreadSearchForumthreads;
import com.ideal.flyerteacafes.model.entity.ThreadSearchResultBean;
import com.ideal.flyerteacafes.model.entity.ThreadTagBean;
import com.ideal.flyerteacafes.ui.activity.HomeActivity;
import com.ideal.flyerteacafes.ui.activity.RaidersListActivity;
import com.ideal.flyerteacafes.ui.activity.ReportListActivity;
import com.ideal.flyerteacafes.ui.activity.ThreadTagActivity;
import com.ideal.flyerteacafes.ui.activity.interfaces.IThreadSearchResult;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadSearchResultPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteMajorThreadContentActivity;
import com.ideal.flyerteacafes.ui.controls.PullToRefreshView;
import com.ideal.flyerteacafes.ui.fragment.interfaces.IPullRefresh;
import com.ideal.flyerteacafes.ui.fragment.page.Base.MVPBaseFragment;
import com.ideal.flyerteacafes.ui.layout.SearchScreenLayout;
import com.ideal.flyerteacafes.ui.popupwindow.AllForumPopupwindow;
import com.ideal.flyerteacafes.ui.popupwindow.SearchSortPopupwindow;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.TaskUtil;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2018/1/19.
 */

public class ThreadSearchResultFragment extends MVPBaseFragment<IPullRefresh<Object>, ThreadSearchResultPresenter> implements IThreadSearchResult<Object>, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener, AdapterView.OnItemClickListener {

    @ViewInject(R.id.remind_bottom_layout)
    View remind_bottom_layout;
    @ViewInject(R.id.fragment_refreshview)
    PullToRefreshView pullToRefreshView;
    @ViewInject(R.id.fragment_refreshview_listview)
    ListView listView;
    @ViewInject(R.id.fm_item_serach_screen)
    SearchScreenLayout fm_item_serach_screen;
    SearchScreenLayout header_item_serach_screen;

    Dictionary<Integer, Integer> listViewItemHeights = new Hashtable<>();
    AllForumPopupwindow allForumPopupwindow;
    SearchSortPopupwindow searchSortPopupwindow;
    ThreadSearchResultAdapter adapter;
    SearchResultFragment.ISearchResultFragment iSearchResultFragment;
    int tagViewHeight = 0;
    int serachScreenHeight;
    View headerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_trhead_search_result, container, false);
        x.view().inject(this, view);
        mPresenter.init(mActivity);
        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        listView.setDivider(null);
        initListener();
        if (mPresenter.isAppoint()) {
            bindForumAppoint();
        }
        return view;
    }

    @Override
    public void callbackReustData(Object obj) {
        ThreadSearchResultBean data = (ThreadSearchResultBean) obj;
        if (data == null || (DataUtils.isEmpty(data.getTag()) && DataTools.isEmpty(data.getThreads()))) {
            if (iSearchResultFragment != null) {
                iSearchResultFragment.notData();
            }
            return;
        }
        if (listView.getHeaderViewsCount() == 0) {
            headerView = LayoutInflater.from(mActivity).inflate(R.layout.thread_search_list_header, null);
            header_item_serach_screen = (SearchScreenLayout) headerView.findViewById(R.id.header_item_search_screen);


            listView.addHeaderView(headerView);
            header_item_serach_screen.post(new Runnable() {
                @Override
                public void run() {
                    serachScreenHeight = header_item_serach_screen.getHeight();
                }
            });
        }
        bindTagView(headerView, data.getTag());
        bindScreen2(header_item_serach_screen, !DataUtils.isEmpty(data.getThreads()));

        if (DataUtils.isEmpty(mPresenter.getDatas()) || mPresenter.getDatas().size() < 6) {
            WidgetUtils.setVisible(remind_bottom_layout, true);
        } else {
            WidgetUtils.setVisible(remind_bottom_layout, false);
        }

        if (mPresenter.isAppoint()) {
            bindForumAppoint();
        }
    }

    @Override
    public void clearPage() {
        bindTagView(headerView, null);
        WidgetUtils.setVisible(fm_item_serach_screen, false);
        WidgetUtils.setVisible(header_item_serach_screen, false);
        WidgetUtils.setVisible(remind_bottom_layout, false);
    }


    @Event({R.id.btn_see_raiders, R.id.btn_questions_to})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.btn_see_raiders:
                Bundle bundle = new Bundle();
                bundle.putInt("code", FinalUtils.HOME_GONGLUE);
                jumpActivity(HomeActivity.class, bundle);

                MobclickAgent.onEvent(getContext(), FinalUtils.EventId.search_articleBtn_click);
                break;
            case R.id.btn_questions_to:
                jumpActivity(WriteMajorThreadContentActivity.class);
                MobclickAgent.onEvent(getContext(), FinalUtils.EventId.search_questionBtn_click);
                break;
        }
    }


    @Override
    public void pullToRefreshViewComplete() {
        pullToRefreshView.onFooterRefreshComplete();
        pullToRefreshView.onHeaderRefreshComplete();
    }

    @Override
    public void callbackData(List datas) {
        if (adapter == null) {
            adapter = new ThreadSearchResultAdapter(mActivity, datas, R.layout.item_thread_serach_result);
            listView.setAdapter(adapter);
        }
        adapter.setSearchKey(mPresenter.getSearchKey());
    }

    @Override
    public void footerRefreshSetListviewScrollLocation(int oldSize) {

    }

    @Override
    public void notMoreData() {

    }

    @Override
    public void headerRefreshSetListviewScrollLocation() {

    }

    @Override
    public void headerRefreshing() {
        pullToRefreshView.headerRefreshing();
    }


    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPresenter.onFooterRefresh();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        mPresenter.onHeaderRefresh();
    }

    @Override
    protected ThreadSearchResultPresenter createPresenter() {
        return new ThreadSearchResultPresenter();
    }


    private void initListener() {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View c = view.getChildAt(0); //this is the first visible row
                if (c != null) {
                    int scrollY = -c.getTop();
                    listViewItemHeights.put(view.getFirstVisiblePosition(), c.getHeight());
                    for (int i = 0; i < view.getFirstVisiblePosition(); ++i) {
                        if (listViewItemHeights.get(i) != null) // (this is a sanity check)
                            scrollY += listViewItemHeights.get(i); //add all heights of the views that are gone
                    }

                    WidgetUtils.setVisible(fm_item_serach_screen, (scrollY > tagViewHeight && tagViewHeight != 0) || (tagViewHeight == 0 && scrollY > 0));
                }
            }
        });
        listView.setOnItemClickListener(this);
    }


    /**
     * 绑定tag
     */
    public void bindTagView(View headerView, final List<ThreadTagBean> datas) {
        if (headerView == null) return;
        final LinearLayout search_tag_layout = (LinearLayout) headerView.findViewById(R.id.search_tag_layout);
        search_tag_layout.removeAllViews();
        final View item_search_tag_interval = headerView.findViewById(R.id.item_search_tag_interval);

        WidgetUtils.setVisible(search_tag_layout, !DataUtils.isEmpty(datas));
        WidgetUtils.setVisible(item_search_tag_interval, !DataUtils.isEmpty(datas));

        if (!DataUtils.isEmpty(datas)) {
            for (int i = 0; i < datas.size(); i++) {
                final ThreadTagBean data = datas.get(i);
                final View item_search_tag = LayoutInflater.from(mActivity).inflate(R.layout.item_serach_tag, null);
                search_tag_layout.addView(item_search_tag);
                TextView subjectTv = (TextView) item_search_tag.findViewById(R.id.tv_tag_subject);
                TextView numTv = (TextView) item_search_tag.findViewById(R.id.tv_tag_nums);
                WidgetUtils.setHighLightKey(subjectTv, data.getTagname(), mPresenter.getSearchKey());
                WidgetUtils.setText(numTv, data.getTagcount() + "篇精选文章");
                item_search_tag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (DataUtils.isEmpty(data.getTagurl())) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("data", data);
                            jumpActivity(ThreadTagActivity.class, bundle);
                        } else {
                            if (data.getTagname().contains("攻略")) {
                                RaidersListActivity.startRaidersListActivity(getContext(), data.getTagname(), data.getTagurl(), null, null, null, null);
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString("title", data.getTagname());
                                bundle.putString("requestUrl", data.getTagurl());
                                jumpActivity(ReportListActivity.class, bundle);
                            }
                        }

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("name", data.getTagname());
                        MobclickAgent.onEvent(getContext(), FinalUtils.EventId.search_collection_click, map);
                    }
                });

                //分割线
                if (i < datas.size() - 1) {
                    View lineView = new View(mActivity);
                    lineView.setBackgroundColor(getResources().getColor(R.color.app_line_color));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                    search_tag_layout.addView(lineView, params);
                }
            }
        }


        if (DataUtils.isEmpty(datas)) {
            tagViewHeight = 0;
        } else {
            search_tag_layout.post(new Runnable() {
                @Override
                public void run() {
                    tagViewHeight = search_tag_layout.getHeight() + item_search_tag_interval.getHeight();
                }
            });
        }


    }


    /**
     * 筛选
     *
     * @param headerScreen
     * @param isShow
     */
    public void bindScreen2(View headerScreen, boolean isShow) {
        bindScreen(headerScreen, isShow);
        bindScreen(fm_item_serach_screen, isShow);
    }


    /**
     * 设置
     *
     * @param value
     */
    private void bindForumName2(String value) {
        if (header_item_serach_screen != null)
            header_item_serach_screen.setForumName(value);
        if (fm_item_serach_screen != null)
            fm_item_serach_screen.setForumName(value);
    }

    public void bindSortName2(String value) {
        if (header_item_serach_screen != null)
            header_item_serach_screen.setSortName(value);
        if (fm_item_serach_screen != null)
            fm_item_serach_screen.setSortName(value);
    }

    /**
     * 指定版块
     */
    public void bindForumAppoint() {
        if (header_item_serach_screen != null)
            header_item_serach_screen.setForumAppoint();
        if (fm_item_serach_screen != null)
            fm_item_serach_screen.setForumAppoint();
    }

    /**
     * 两个view都设置
     *
     * @param isSelect
     */
    private void setSelectForum2(boolean isSelect) {
        header_item_serach_screen.setSelectForum(isSelect);
        fm_item_serach_screen.setSelectForum(isSelect);
    }


    /**
     * 两个view都设置
     *
     * @param isSelect
     */
    private void setSelectSort2(boolean isSelect) {
        header_item_serach_screen.setSelectSort(isSelect);
        fm_item_serach_screen.setSelectSort(isSelect);
    }

    private void bindScreen(View headerScreen, boolean isShow) {
        if (headerScreen == null) return;
        //TODO 默认true
        WidgetUtils.setVisible(headerScreen, isShow);
        headerScreen.findViewById(R.id.all_forum_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!DataUtils.isEmpty(mPresenter.getForumData()) && !mPresenter.isAppoint()) {
                    setSelectForum2(true);
                    listView.setSelectionFromTop(0, -tagViewHeight);
                    int[] location = new int[2];
                    fm_item_serach_screen.getLocationOnScreen(location);
                    int h_screen = SharedPreferencesString.getInstances().getIntToKey("h_screen");
                    if (allForumPopupwindow == null) {
                        allForumPopupwindow = new AllForumPopupwindow(mActivity);
                        allForumPopupwindow.setHeight(h_screen - location[1] - serachScreenHeight);
                        allForumPopupwindow.bindData(mPresenter.getForumData());
                    }
                    allForumPopupwindow.showAsDropDown(v);
                }
            }
        });

        headerScreen.findViewById(R.id.new_thread_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectSort2(true);
                listView.setSelectionFromTop(0, -tagViewHeight);
                int[] location = new int[2];
                fm_item_serach_screen.getLocationOnScreen(location);
                int h_screen = SharedPreferencesString.getInstances().getIntToKey("h_screen");
                if (searchSortPopupwindow == null) {
                    searchSortPopupwindow = new SearchSortPopupwindow(mActivity);
                    searchSortPopupwindow.setHeight(h_screen - location[1] - serachScreenHeight);
                }
                searchSortPopupwindow.showAsDropDown(v);
            }
        });


    }


    public void setSearchKey(final String searchKey, final String type, SearchResultFragment.ISearchResultFragment iSearchResultFragment) {
        this.iSearchResultFragment = iSearchResultFragment;
        TaskUtil.postOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPresenter.setType(type);
                mPresenter.setSearchKey(searchKey);
                bindForumName2("所有版块");
                bindSortName2("最新发帖");
                if (mPresenter.isAppoint()) {
                    bindForumAppoint();
                }
            }
        });
    }

    public void onEventMainThread(TagEvent tagEvent) {
        if (tagEvent.getTag() == TagEvent.TAG_SEARCH_SORT) {
            Bundle bundle = tagEvent.getBundle();
            if (bundle != null) {
                listView.smoothScrollToPosition(0);
                fm_item_serach_screen.setVisibility(View.GONE);
                String orderby = bundle.getString("orderby");
                String name = bundle.getString("name");
                bindSortName2(name);
                listView.setSelection(0);
                mActivity.showDialog(getString(R.string.search_dialog_tip));
                mPresenter.setSort(orderby);

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", name);
                MobclickAgent.onEvent(getContext(), FinalUtils.EventId.search_sort_click, map);
            }
            setSelectSort2(false);
        } else if (tagEvent.getTag() == TagEvent.TAG_SEARCH_FORUM) {
            Bundle bundle = tagEvent.getBundle();
            if (bundle != null) {
                listView.smoothScrollToPosition(0);
                fm_item_serach_screen.setVisibility(View.GONE);
                ThreadSearchForumthreads forum = (ThreadSearchForumthreads) bundle.getSerializable("data");
                bindForumName2(forum.getFname());
                listView.setSelection(0);
                mActivity.showDialog(getString(R.string.search_dialog_tip));
                mPresenter.setScreenForum(String.valueOf(forum.getFid()));

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", forum.getFname());
                MobclickAgent.onEvent(getContext(), FinalUtils.EventId.search_filter_click, map);
            }
            setSelectForum2(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) return;
        Bundle bundle = new Bundle();
        bundle.putString("tid", adapter.getItem(position - listView.getHeaderViewsCount()).getTid());
        jumpActivity(ThreadActivity.class, bundle);
    }
}
