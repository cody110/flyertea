package com.ideal.flyerteacafes.ui.fragment.page.tab.community;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.ThreadSubjectVideoListAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.adapters.interfaces.IGridViewItemClick;
import com.ideal.flyerteacafes.adapters.videoitem.ThreadVideoItem;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.entity.CommunitySubDetailsBean;
import com.ideal.flyerteacafes.model.entity.CommunitySubTypeBean;
import com.ideal.flyerteacafes.model.entity.ThreadSubjectBean;
import com.ideal.flyerteacafes.model.entity.TopThreadBean;
import com.ideal.flyerteacafes.model.entity.TopicBean;
import com.ideal.flyerteacafes.ui.activity.CommunitySubActivity;
import com.ideal.flyerteacafes.ui.activity.video.AutoPlayListFragment;
import com.ideal.flyerteacafes.ui.activity.web.TbsWebActivity;
import com.ideal.flyerteacafes.ui.activity.ThreadTypeActivity;
import com.ideal.flyerteacafes.ui.activity.TopicDetailsActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.controls.MyHorizontalScrollView;
import com.ideal.flyerteacafes.ui.fragment.interfaces.ICommunitySubFm;
import com.ideal.flyerteacafes.ui.fragment.presenter.CommunitySubPresenter;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.ui.popupwindow.BottomListPopupWindow;
import com.ideal.flyerteacafes.ui.view.CommunitySubTypeLayout;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.ViewTools;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2016/11/29.
 */

public class CommunitySubFragment extends AutoPlayListFragment implements ICommunitySubFm, IGridViewItemClick {

    @ViewInject(R.id.community_sub_icon_img)
    ImageView community_sub_icon_img;
    @ViewInject(R.id.community_sub_fname)
    TextView community_sub_fname;
    @ViewInject(R.id.community_sub_info_num)
    TextView community_sub_info_num;
    @ViewInject(R.id.community_sub_top_thread)
    ListView community_sub_top_thread;
    @ViewInject(R.id.community_sub_community_subtype_layout)
    CommunitySubTypeLayout community_sub_community_subtype_layout;
    @ViewInject(R.id.community_sub_more_layout)
    View community_sub_more_layout;
    @ViewInject(R.id.community_sub_add_follow)
    TextView community_sub_add_follow;
    @ViewInject(R.id.item_header_community_sub_type_top_layout)
    View item_header_community_sub_type_top_layout;
    @ViewInject(R.id.community_sub_choose_layout)
    View community_sub_choose_layout;
    @ViewInject(R.id.community_sub_choose_type_name)
    TextView community_sub_choose_type_name;
    @ViewInject(R.id.community_sub_choose_type_icon)
    ImageView community_sub_choose_type_icon;
    @ViewInject(R.id.header_community_sub_forum_info)
    View header_community_sub_forum_info;
    @ViewInject(R.id.community_sub_more_img)
    ImageView community_sub_more_img;
    @ViewInject(R.id.community_sub_more_btn)
    TextView community_sub_more_btn;

    CommonAdapter<TopThreadBean> topAdapter;

    ThreadSubjectVideoListAdapter communityPlateAdapter;

    CommunitySubPresenter myPresenter;

    private int topHeight;
    private int topForumInfoHeight;


    CommunitySubActivity activity;
    View.OnClickListener toThreadTypeListener;

    BottomListPopupWindow sortPopupWindow;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (CommunitySubActivity) activity;
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
        myPresenter.setiCommunitySubFm(this);
        setListviewDivider(getResources().getDrawable(R.drawable.line_margin_left_right));
        View headerView = LayoutInflater.from(mActivity).inflate(R.layout.item_header_community_sub, null, false);
        x.view().inject(this, headerView);
        listView.addHeaderView(headerView);
        getTopHeight();
        community_sub_choose_layout.setOnClickListener(toThreadTypeListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                community_sub_choose_type_icon.setRotation(180);
                activity.community_sub_choose_type_icon.setRotation(180);
                if (sortPopupWindow == null) {
                    sortPopupWindow = new BottomListPopupWindow(mActivity);
                    sortPopupWindow.setDatas(new BottomListPopupWindow.IItemClick() {
                        @Override
                        public void itemClick(int pos, String data) {

                            if (TextUtils.equals(data, "按回复时间")) {
                                SharedPreferencesString.getInstances().savaToString("sub_sort", "lastpost");
                                SharedPreferencesString.getInstances().commit();
                                myPresenter.setOrderby("lastpost");
                                community_sub_choose_type_name.setText("按回复");
                                activity.community_sub_choose_type_name.setText("按回复");
                                headerRefreshing();
                                EventBus.getDefault().post(new TagEvent(TagEvent.TAG_POST_SORT_CHANGE));
                            }

                            if (TextUtils.equals(data, "按发帖时间")) {
                                SharedPreferencesString.getInstances().savaToString("sub_sort", "dateline");
                                SharedPreferencesString.getInstances().commit();
                                myPresenter.setOrderby("dateline");
                                community_sub_choose_type_name.setText("按发帖");
                                activity.community_sub_choose_type_name.setText("按发帖");
                                headerRefreshing();
                                EventBus.getDefault().post(new TagEvent(TagEvent.TAG_POST_SORT_CHANGE));
                            }
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("name", data);
                            map.put("tabName", myPresenter.fname2);
                            MobclickAgent.onEvent(mActivity, FinalUtils.EventId.notelist_note_sort, map);

                        }
                    }, "按回复时间", "按发帖时间");

                    sortPopupWindow.setiPopDismiss(new BottomListPopupWindow.IPopDismiss() {
                        @Override
                        public void popDismiss() {
                            community_sub_choose_type_icon.setRotation(0);
                            activity.community_sub_choose_type_icon.setRotation(0);
                        }
                    });
                }
                sortPopupWindow.showAtLocation(header_community_sub_forum_info, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        setOnScrollListener(onScrollListener);
    }

    private void getTopHeight() {
        item_header_community_sub_type_top_layout.post(new Runnable() {
            @Override
            public void run() {
                topHeight = item_header_community_sub_type_top_layout.getHeight();
                topForumInfoHeight = header_community_sub_forum_info.getHeight();
            }
        });

    }


    @Override
    protected CommonAdapter<ThreadVideoItem> createAdapter(final List<ThreadVideoItem> datas) {
        return communityPlateAdapter = new ThreadSubjectVideoListAdapter(mActivity, datas, R.layout.item_community_follow_list).setiGridViewItemClick(new IGridViewItemClick() {
            @Override
            public void gridViewItemClick(int i) {
                ThreadSubjectBean bean = communityPlateAdapter.getItem(i).getData();
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
                        map.put("name", myPresenter.fname2);
                        MobclickAgent.onEvent(mActivity, FinalUtils.EventId.notelist_collection_click, map);

                    } else {
                        Bundle bundle = new Bundle();
                        if (TextUtils.equals(bean.getAdtype(), "code")) {
                            bundle.putString("url", Utils.HtmlUrl.HTML_ADV + bean.getTid());
                        } else {
                            bundle.putString("url", bean.getUrl());
                        }
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("aid", bean.getTid());
                        MobclickAgent.onEvent(getActivity(), FinalUtils.EventId.flowAd_click, map);
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

    @Override
    protected PullRefreshPresenter<ThreadVideoItem> createPresenter() {
        return myPresenter = new CommunitySubPresenter();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == mActivity.RESULT_OK) {
            if (requestCode == CommunitySubPresenter.REQUEST_THREAD_TYPE_ACTIVITY) {
                String sort = data.getStringExtra(ThreadTypeActivity.BUNDLE_SORT_STR);
                myPresenter.setOrderby(sort);
                int index = data.getIntExtra(ThreadTypeActivity.BUNDLE_TYPE_INDEX, 0);
                community_sub_community_subtype_layout.setSelectIndex(index);
                activity.getCommunitySubTypeLayout().setSelectIndex(index);
                myPresenter.communitySubTypeLayoutItemClick(index);
            }
        }
    }

    @Event({R.id.community_sub_add_follow, R.id.community_sub_more_layout})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.community_sub_add_follow:
                myPresenter.actionFollow();
                break;
            case R.id.community_sub_more_layout:
                if (v.getTag() == null || !(boolean) v.getTag()) {
                    v.setTag(true);
                    myPresenter.setNeedTopThreadList(true);
                    community_sub_more_img.setRotation(0);
                    community_sub_more_btn.setText("收起");
                    MobclickAgent.onEvent(mActivity, FinalUtils.EventId.notelist_topmore_click);
                } else {
                    v.setTag(false);
                    myPresenter.setNeedTopThreadList(false);
                    community_sub_more_img.setRotation(180);
                    community_sub_more_btn.setText("更多置顶");
                }
                break;
        }
    }

    @Event(value = R.id.community_sub_top_thread, type = AdapterView.OnItemClickListener.class)
    private void topItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (TextUtils.equals(myPresenter.getNeedTopThreadList().get(position).getType(), "2")) {
            if (!TextUtils.isEmpty(myPresenter.getNeedTopThreadList().get(position).getUrl())) {
                Bundle bundle = new Bundle();
                bundle.putString("url", myPresenter.getNeedTopThreadList().get(position).getUrl());
                jumpActivity(TbsWebActivity.class, bundle);
            }
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("tid", myPresenter.getNeedTopThreadList().get(position).getTid());
            jumpActivity(ThreadActivity.class, bundle);

            HashMap<String, String> map = new HashMap<>();
            map.put("tid", myPresenter.getNeedTopThreadList().get(position).getTid());
            if (myPresenter.communitySubDetailsBean != null) {
                map.put("name", myPresenter.communitySubDetailsBean.getName());
                LogFly.e("name:" + myPresenter.communitySubDetailsBean.getName());
            }
            MobclickAgent.onEvent(mActivity, FinalUtils.EventId.notelist_top_click, map);
        }

    }

    public void toWriteThread() {
        myPresenter.toWriteThread();
    }

    public void toSearchThread() {
        myPresenter.toSearchThread();
    }

    @Override
    public void actionTopThread(List<TopThreadBean> topThreadBeanList) {
        if (topThreadBeanList.size() > 0) {

            if (topAdapter == null) {
                community_sub_top_thread.setAdapter(topAdapter = new CommonAdapter<TopThreadBean>(mActivity, topThreadBeanList, R.layout.item_top_thread) {
                    @Override
                    public void convert(ViewHolder holder, TopThreadBean topThreadBean) {

                        TextView subjectTv = holder.getView(R.id.item_top_thread_subject);
                        if (TextUtils.equals(topThreadBean.getType(), "2")) {
                            holder.setText(R.id.type_name, "公告");
                            holder.setTextColorRes(R.id.type_name, R.color.topthread_type_gongao);
                            subjectTv.setSingleLine(false);
                            subjectTv.setMaxLines(2);
                        } else {
                            subjectTv.setSingleLine();
                            holder.setText(R.id.type_name, "置顶");
                            holder.setTextColorRes(R.id.type_name, R.color.topthread_type_zhiding);
                            subjectTv.setSingleLine(true);
                        }

                        holder.setText(R.id.item_top_thread_subject, topThreadBean.getSubject());
                    }
                });
            } else {
                topAdapter.notifyDataSetChanged();
            }

            ViewTools.setListViewHeightBasedOnChildren(community_sub_top_thread);
            getTopHeight();

        }


    }

    @Override
    public void actionIsShowTopMoreBtn(boolean bol) {

        LogFly.e("bol:" + bol);

        WidgetUtils.setVisible(community_sub_more_layout, bol);
    }

    @Override
    public void actionForumInfo(CommunitySubDetailsBean bean) {
        DataUtils.downloadPicture(community_sub_icon_img, bean.getIcon(), R.drawable.icon_def);
        WidgetUtils.setText(community_sub_fname, bean.getName());
        WidgetUtils.setText(community_sub_info_num, "主题" + bean.getPosts() + ",关注" + bean.getFavtimes());
        int color = DataTools.getInteger(bean.getThemecolor());
        if (color == 0) {
            if (!TextUtils.isEmpty(bean.getThemecolor())) {
                try {
                    header_community_sub_forum_info.setBackgroundColor(Color.parseColor(bean.getThemecolor()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            header_community_sub_forum_info.setBackgroundColor(color);
        }
        activity.setTitleBg(bean.getThemecolor());
        activity.setTitleName(bean.getName());
    }

    @Override
    public void actionForumType(final List<CommunitySubTypeBean> typeBeanList, final String forumName) {
        community_sub_community_subtype_layout.setData(typeBeanList, forumName);
        community_sub_community_subtype_layout.setItemClick(this);
        community_sub_community_subtype_layout.setScrollviewLinster(myHorzontalScrollListener);

        community_sub_community_subtype_layout.post(new Runnable() {
            @Override
            public void run() {
                activity.getCommunitySubTypeLayout().setData(typeBeanList, forumName);
                activity.getCommunitySubTypeLayout().setItemClick(CommunitySubFragment.this);
                activity.setSelectClick(toThreadTypeListener);
                activity.getCommunitySubTypeLayout().setScrollviewLinster(myHorzontalScrollListener);
            }
        });

    }

    @Override
    public void actionIsFavorite(boolean isFav) {
        if (isFav) {
            community_sub_add_follow.setText("已关注");
        } else {
            community_sub_add_follow.setText("关注");
        }
    }

    @Override
    public void actionShowAdapterType(boolean bol) {
        if (communityPlateAdapter != null) {
            communityPlateAdapter.actionShowAdapterType(bol);
        }
    }

    @Override
    public void actionSetTvSort(final String sortStr) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.equals("lastpost", sortStr)) {
                    community_sub_choose_type_name.setText("按回复");
                    activity.community_sub_choose_type_name.setText("按回复");
                } else {
                    community_sub_choose_type_name.setText("按发帖");
                    activity.community_sub_choose_type_name.setText("按发帖");
                }
            }
        });
    }


    Dictionary<Integer, Integer> listViewItemHeights = new Hashtable<>();


    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            activity.settingWriteThreadBtn(scrollState);
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
                if (scrollY > topHeight) {
                    activity.setIsShowSubtypeLayout(true);
                    activity.getCommunitySubTypeLayout().setMyHorizontalScrollViewScrollTo(community_sub_community_subtype_layout.getScrollX(), 0);
                } else {
                    activity.setIsShowSubtypeLayout(false);
                }

                if (scrollY >= topForumInfoHeight) {
                    activity.setIsShowTitleName(true);
                } else {
                    activity.setIsShowTitleName(false);
                }

            }

        }
    };


    /**
     * 横向滚动监听
     */
    MyHorizontalScrollView.ScrollViewListener myHorzontalScrollListener = new MyHorizontalScrollView.ScrollViewListener() {
        @Override
        public void onScrollChanged(MyHorizontalScrollView.ScrollType scrollType, int currentX) {
            if (scrollType == MyHorizontalScrollView.ScrollType.IDLE) {
                community_sub_community_subtype_layout.setMyHorizontalScrollViewScrollTo(currentX, 0);
                activity.getCommunitySubTypeLayout().setMyHorizontalScrollViewScrollTo(currentX, 0);
            }

        }
    };


    @Override
    public void gridViewItemClick(int i) {
        MobclickAgent.onEvent(mActivity, FinalUtils.EventId.community_filter_click);
        community_sub_community_subtype_layout.setSelectIndex(i);
        activity.getCommunitySubTypeLayout().setSelectIndex(i);
        myPresenter.communitySubTypeLayoutItemClick(i);
    }

    /**
     * 关注变化
     *
     * @param tagEvent
     */
    public void onEventMainThread(TagEvent tagEvent) {
        if (tagEvent.getTag() == TagEvent.TAG_YUE_CHANGE) {
            if (communityPlateAdapter != null)
                communityPlateAdapter.notifyDataSetChanged();
        }
    }


}
