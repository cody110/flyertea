package com.ideal.flyerteacafes.ui.fragment.page.tab.community;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PagerIndicatorAdapter2;
import com.ideal.flyerteacafes.caff.CacheFileManger;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.model.entity.UserBean;
import com.ideal.flyerteacafes.ui.activity.ReadSortActivity;
import com.ideal.flyerteacafes.ui.activity.search.ThreadSearchActivity;
import com.ideal.flyerteacafes.ui.activity.search.ThreadSearchActivity2;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteMajorThreadContentActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.view.PagerSlidingTabStrip;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.WidgetUtils;
import com.ideal.flyerteacafes.utils.tools.DataTools;
import com.ideal.flyerteacafes.utils.tools.FileUtil;
import com.ideal.flyerteacafes.utils.tools.GsonTools;
import com.ideal.flyerteacafes.xmlparser.TypeModeHandler;
import com.ideal.flyerteacafes.xmlparser.XmlParserUtils;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/6/28.
 */

public class ReadThreadFragment extends BaseFragment {

    @ViewInject(R.id.tv_reply)
    private TextView tv_reply;
    @ViewInject(R.id.tv_send)
    private TextView tv_send;
    @ViewInject(R.id.tv_hot)
    private TextView tv_hot;
    @ViewInject(R.id.tv_digest)
    private TextView tv_digest;
    @ViewInject(R.id.fm_community_home_write_thread_btn)
    private ImageView fm_community_home_write_thread_btn;
    @ViewInject(R.id.screen_layout)
    private View screen_layout;

    @ViewInject(R.id.type_tabstrip)
    PagerSlidingTabStrip mTabStrip;
    @ViewInject(R.id.type_viewpager)
    ViewPager mViewPager;

    private List<TypeMode> typeModeList = new ArrayList<>();
    private List<CommunityFollowListFragment> mListFragments = new ArrayList<>();

    private PagerIndicatorAdapter2 mViewPagerAdapter;
    private int selectIndex = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_read_thread, container, false);
        EventBus.getDefault().register(this);
        x.view().inject(this, view);
        initPage();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initPage() {
        typeModeList.clear();
        mListFragments.clear();

        final String cacheText = FileUtil.readSDFile(CacheFileManger.getSavePath() + "/" + Utils.read_tab);
        if (TextUtils.isEmpty(cacheText)) {
            TypeModeHandler starLevelHandler = new TypeModeHandler();
            XmlParserUtils.doMyMission(starLevelHandler, "xml/community_read_tab.xml");
            List<TypeMode> datas = starLevelHandler.getDataList();
            typeModeList.addAll(datas);
        } else {
            List<TypeMode> datas = GsonTools.jsonToList(cacheText, TypeMode.class);
            typeModeList.addAll(datas);
        }


        //无关注不显示
        if (DataUtils.isEmpty(UserManger.getInstance().getFavList())) {
            typeModeList.remove(0);
        }

        for (TypeMode mode : typeModeList) {
            mListFragments.add(createListFm(mode));
        }

        if (mViewPagerAdapter == null) {

            mViewPagerAdapter = new PagerIndicatorAdapter2(getChildFragmentManager(), mListFragments, typeModeList);
            // 设置标签自动扩展——当标签们的总宽度不够屏幕宽度时，自动扩展使每个标签宽度递增匹配屏幕宽度，注意！这一条代码必须在setViewPager前方可生效
            mViewPager.setAdapter(mViewPagerAdapter);
            mViewPager.setCurrentItem(0);
            mViewPager.setOffscreenPageLimit(2);
            mTabStrip.setShouldExpand(true);
            mTabStrip.setViewPager(mViewPager);
            mTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    WidgetUtils.setVisible(screen_layout, !TextUtils.equals(typeModeList.get(position).getName(), "直播"));
                    HashMap<String, String> map = new HashMap<>();
                    map.put("name", typeModeList.get(position).getName());
                    MobclickAgent.onEvent(mActivity, FinalUtils.EventId.community_navbar_click, map);
                    selectIndex = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            String sub_sort = SharedPreferencesString.getInstances().getStringToKey("sub_sort");
            if (TextUtils.equals(sub_sort, "dateline")) {
                setSelectSend();
            } else {
                setSelectReply();
            }

        } else {
            mViewPagerAdapter.notifyDataSetChanged();
        }
    }

    private CommunityFollowListFragment createListFm(TypeMode mode) {
        CommunityFollowListFragment fm = new CommunityFollowListFragment();
        fm.setRequestType(mode);
        return fm;
    }


    @Event({R.id.tv_reply, R.id.tv_send, R.id.tv_hot, R.id.tv_digest, R.id.search_btn, R.id.fm_community_home_write_thread_btn, R.id.read_sort_img})
    private void click(View v) {
        HashMap<String, String> view = new HashMap<>();
        HashMap<String, String> map = new HashMap<>();

        switch (v.getId()) {
            case R.id.tv_reply:
                setSelectReply();

                map.put("name", "按回复");
                MobclickAgent.onEvent(mActivity, FinalUtils.EventId.community_sort, map);

                SharedPreferencesString.getInstances().savaToString("sub_sort", "lastpost");
                SharedPreferencesString.getInstances().commit();
                break;
            case R.id.tv_send:
                setSelectSend();

                map.put("name", "按发帖");
                MobclickAgent.onEvent(mActivity, FinalUtils.EventId.community_sort, map);

                SharedPreferencesString.getInstances().savaToString("sub_sort", "dateline");
                SharedPreferencesString.getInstances().commit();
                break;
            case R.id.tv_hot:
                setSelectTypeTv((TextView) v);
                view.put("filter", "heat");
                view.put("orderby", "dateline");
                setFragmentView(view);

                map.put("name", "热门");
                MobclickAgent.onEvent(mActivity, FinalUtils.EventId.community_sort, map);
                break;
            case R.id.tv_digest:
                setSelectTypeTv((TextView) v);
                view.put("filter", "digest");
                view.put("digest", "1");
                view.put("orderby", "dateline");
                setFragmentView(view);

                map.put("name", "精华");
                MobclickAgent.onEvent(mActivity, FinalUtils.EventId.community_sort, map);
                break;
            case R.id.search_btn:
                jumpActivity(ThreadSearchActivity2.class, null);
                break;

            case R.id.fm_community_home_write_thread_btn:
                if (UserManger.isLogin()) {
                    MobclickAgent.onEvent(mActivity, FinalUtils.EventId.forum_post);//友盟统计
                    jumpActivity(WriteMajorThreadContentActivity.class, null);
                } else {
                    DialogUtils.showDialog(mActivity);
                }
                break;

            case R.id.read_sort_img:
                jumpActivity(ReadSortActivity.class, null);
                break;

        }

    }

    private void setFragmentView(HashMap<String, String> view) {
        for (CommunityFollowListFragment fragment : mListFragments) {
            fragment.setRequestSort(view);
        }
    }

    private void setSelectTypeTv(TextView tv) {
        setNotSelectTypeTvStyle(tv_reply);
        setNotSelectTypeTvStyle(tv_send);
        setNotSelectTypeTvStyle(tv_hot);
        setNotSelectTypeTvStyle(tv_digest);
        setSelectTypeTvStyle(tv);
    }

    private void setSelectTypeTvStyle(TextView tv) {
        tv.setTextColor(getResources().getColor(R.color.text_blue));
    }

    private void setNotSelectTypeTvStyle(TextView tv) {
        tv.setTextColor(getResources().getColor(R.color.text_light_grey));
    }


    public void refreshTab() {
        if (TextUtils.equals(typeModeList.get(0).getName(), "关注") && DataUtils.isEmpty(UserManger.getInstance().getFavList())) {
            typeModeList.remove(0);
            mListFragments.remove(0);
            mViewPagerAdapter.notifyDataSetChanged();
        } else if (!TextUtils.equals(typeModeList.get(0).getName(), "关注") && !DataUtils.isEmpty(UserManger.getInstance().getFavList())) {
            TypeMode mode = new TypeMode();
            mode.setName("关注");
            mode.setType("follow");
            typeModeList.add(0, mode);
            mListFragments.add(0, createListFm(mode));
            mViewPagerAdapter.notifyDataSetChanged();
        }
    }

    public void refreshList() {
        MobclickAgent.onEvent(mActivity, FinalUtils.EventId.community_refresh);
        if (DataTools.getBeanByListPos(mListFragments, selectIndex) != null) {
            mListFragments.get(selectIndex).headerRefreshing();
        }
    }


    /**
     * 用户登录刷新版块数据
     */
    public void onEventMainThread(UserBean userBean) {
        refreshTab();
    }

    /**
     * 退出登录
     *
     * @param msg
     */
    public void onEventMainThread(String msg) {
        if (msg.equals(FinalUtils.OUTLOGIN)) {
            refreshTab();
        }
    }


    public void onEventMainThread(TagEvent tagEvent) {
        if (tagEvent.getTag() == TagEvent.TAG_POST_SORT_CHANGE) {
            String sub_sort = SharedPreferencesString.getInstances().getStringToKey("sub_sort");

            if (TextUtils.equals(sub_sort, "dateline")) {
                setSelectSend();
            } else {
                setSelectReply();
            }
        } else if (tagEvent.getTag() == TagEvent.TAG_TAB_SORT_CHANGE) {
            initPage();
        }
    }


    /**
     * 按回复
     */
    private void setSelectReply() {
        HashMap<String, String> view = new HashMap<>();
        setSelectTypeTv(tv_reply);
        view.put("filter", "lastpost");
        view.put("orderby", "lastpost");
        setFragmentView(view);
    }

    /**
     * 按发帖
     */
    private void setSelectSend() {
        HashMap<String, String> view = new HashMap<>();
        setSelectTypeTv(tv_send);
        view.put("filter", "lastpost");
        view.put("orderby", "dateline");
        setFragmentView(view);
    }

    /**
     * 热门
     */
    public void showReadThreadHot() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> view = new HashMap<>();
                setSelectTypeTv(tv_hot);
                view.put("filter", "heat");
                view.put("orderby", "dateline");
                setFragmentView(view);
            }
        });
    }

    /**
     * 精华
     */
    public void showReadThreadDigest() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> view = new HashMap<>();
                setSelectTypeTv(tv_hot);
                view.put("filter", "digest");
                view.put("digest", "1");
                view.put("orderby", "dateline");
                setFragmentView(view);
            }
        });
    }

}
