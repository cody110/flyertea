package com.ideal.flyerteacafes.ui.fragment.page.tab.community;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.AbsListView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.ThreadSubjectVideoListAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.interfaces.IGridViewItemClick;
import com.ideal.flyerteacafes.adapters.videoitem.ThreadVideoItem;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.model.entity.ThreadSubjectBean;
import com.ideal.flyerteacafes.model.entity.TopicBean;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.ui.activity.video.AutoPlayListFragment;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.ui.activity.TopicDetailsActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.fragment.presenter.CommunityFollowListPresenter;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.Utils;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2016/12/22.
 */

public class CommunityFollowListFragment extends AutoPlayListFragment {

    ThreadSubjectVideoListAdapter threadSubjectVideoListAdapter;


    public interface IListScrollListener {
        void onScrollStateChanged(AbsListView absListView, int i);
    }

    private IListScrollListener iListScrollListener;

    public void setIListScrollListener(IListScrollListener iListScrollListener) {
        this.iListScrollListener = iListScrollListener;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initViews() {
        super.initViews();
        setListviewDivider(getResources().getDrawable(R.drawable.line_margin_left_right));
        pullToRefreshView.setPull_to_refresh_release_label("松开刷新您关注版块的内容…");
        pullToRefreshView.setPull_to_refresh_pull_label("下拉刷新您关注版块的内容…");
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (iListScrollListener != null) {
                    iListScrollListener.onScrollStateChanged(absListView, i);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    /**
     * 请求类型
     */
    public void setRequestType(final TypeMode typeMode) {
        myPresenter.actionSelectTab(typeMode);
    }

    public void setRequestSort(Map<String, String> view) {
        myPresenter.actionSelectStatus(view);
    }

    @Override
    protected CommonAdapter<ThreadVideoItem> createAdapter(final List<ThreadVideoItem> datas) {


        return threadSubjectVideoListAdapter = new ThreadSubjectVideoListAdapter(mActivity, datas, R.layout.item_community_follow_list).setIsShowFroumName(true).setIsShowHeat(false).
                setIs_feed(TextUtils.equals(myPresenter.module, CommunityFollowListPresenter.feed_module)).setiGridViewItemClick(new IGridViewItemClick() {
            @Override
            public void gridViewItemClick(int i) {
                ThreadSubjectBean bean = adapter.getItem(i).getData();
                if (TextUtils.equals(bean.getType(), Marks.THREAD_LIST_TYPE_ADVER)) {

                    if (TextUtils.equals(bean.getApptemplatetype(), Marks.THREAD_LIST_TYPE_TOPIC)) {
                        Bundle bundle = new Bundle();
                        TopicBean topicBean = new TopicBean();
                        topicBean.setCtid(bean.getCtid());
                        topicBean.setName(bean.getName());
                        topicBean.setDesc(bean.getDesc());
                        topicBean.setViews(bean.getViews());
                        topicBean.setUsers(bean.getUsers());
                        bundle.putSerializable("data", topicBean);
                        jumpActivity(TopicDetailsActivity.class, bundle);


                        Map<String, String> map = new HashMap<>();
                        map.put("cid", bean.getCtid());
                        map.put("name", myPresenter.getTypeMode().getName());
                        MobclickAgent.onEvent(mActivity, FinalUtils.EventId.community_collection_click, map);

                    } else {
                        Bundle bundle = new Bundle();
                        if (TextUtils.equals(bean.getAdtype(), "code")) {
                            bundle.putString("url", Utils.HtmlUrl.HTML_ADV + bean.getTid());
                        } else {
                            bundle.putString("url", bean.getUrl());
                        }
                        jumpActivity(TbsWebActivity.class, bundle);
                    }
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(ThreadPresenter.BUNDLE_TID, bean.getTid());
                    if (bean.isNormal()) {
                        jumpActivity(ThreadActivity.class, bundle);
                    } else {
                        bundle.putInt(ThreadPresenter.BUNDLE_TYPE_KEY, ThreadPresenter.BUNDLE_TYPE_MAJOR);
                        jumpActivity(ThreadActivity.class, bundle);
                    }
                }
            }
        });
    }

    CommunityFollowListPresenter myPresenter = new CommunityFollowListPresenter();

    @Override
    protected PullRefreshPresenter<ThreadVideoItem> createPresenter() {
        return myPresenter;
    }


    /**
     * 用户登录刷新版块数据
     */
    public void onEventMainThread(UserBean userBean) {
        headerRefreshing();
    }

    /**
     * 退出登录
     *
     * @param msg
     */
    public void onEventMainThread(String msg) {
        if (msg.equals(FinalUtils.OUTLOGIN)) {
            headerRefreshing();
        }
    }

    /**
     * 关注变化
     *
     * @param tagEvent
     */
    public void onEventMainThread(TagEvent tagEvent) {
        if (tagEvent.getTag() == TagEvent.FAV_LIST_CHANGE) {
            headerRefreshing();
        } else if (tagEvent.getTag() == TagEvent.TAG_FAV_FOLLOW) {
            headerRefreshing();
        } else if (tagEvent.getTag() == TagEvent.TAG_FAV_CANCLE) {
            headerRefreshing();
        } else if (tagEvent.getTag() == TagEvent.TAG_YUE_CHANGE) {
            if (threadSubjectVideoListAdapter != null)
                threadSubjectVideoListAdapter.notifyDataSetChanged();
        }
    }


}
