package com.ideal.flyerteacafes.ui.activity.viewholder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PagerIndicatorAdapter;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.model.entity.TopicBean;
import com.ideal.flyerteacafes.ui.activity.myinfo.TaskView;
import com.ideal.flyerteacafes.ui.fragment.page.TopicListFragment;
import com.ideal.flyerteacafes.ui.view.PagerSlidingTabStrip;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.WidgetUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2017/10/12.
 */

public class TopicDetailsVH extends BaseViewHolder {

    @ViewInject(R.id.toolbar)
    View toolbar;
    @ViewInject(R.id.title_tv)
    TextView title_tv;

    @ViewInject(R.id.header_viewpager_layout)
    TaskView header_viewpager_layout;
    @ViewInject(R.id.tabstrip)
    PagerSlidingTabStrip mTabStrip;
    @ViewInject(R.id.viewpager)
    ViewPager mViewPager;

    @ViewInject(R.id.topic_title_tv)
    TextView topic_title_tv;
    @ViewInject(R.id.description_tv)
    TextView description_tv;
    @ViewInject(R.id.read_num_tv)
    TextView read_num_tv;
    @ViewInject(R.id.canjia_tv)
    TextView canjia_tv;
    @ViewInject(R.id.fm_community_home_write_thread_btn)
    View fm_community_home_write_thread_btn;

    View headerView;


    private List<TypeMode> typeModeList;
    private ArrayList<Fragment> mListFragments;

    private PagerIndicatorAdapter mViewPagerAdapter;

    public TopicDetailsVH(View view) {
        x.view().inject(this, view);
        headerView = LayoutInflater.from(mContext).inflate(R.layout.header_topic_details, null);
        x.view().inject(this, headerView);

        toolbar.post(new Runnable() {
            @Override
            public void run() {
                int toolbarHeight = toolbar.getHeight();
                initTaskView(toolbarHeight);
            }
        });
    }


    private void initTaskView(final int showHieght) {
        header_viewpager_layout.setmShowHeight(showHieght);
        header_viewpager_layout.addHeaderView(headerView);
        toolbar.getBackground().mutate().setAlpha(0);
        title_tv.setVisibility(View.GONE);
        header_viewpager_layout.setIIsChildScollTop(new TaskView.IIsChildScollTop() {
            @Override
            public boolean isChildScollTop() {
                TopicListFragment fm = (TopicListFragment) mListFragments.get(mViewPager.getCurrentItem());
                return fm.isScrollTop();
            }

            @Override
            public void hintedHeight(int heitedHeight) {
                int headerHeight = headerView.getHeight() - toolbar.getHeight();
                if (heitedHeight < headerHeight) {
                    int alpha = heitedHeight * 255 / headerHeight;
                    alpha = alpha > 255 ? 255 : alpha;
                    toolbar.getBackground().mutate().setAlpha(alpha);
                    title_tv.setVisibility(View.GONE);
                } else {
                    toolbar.getBackground().mutate().setAlpha(255);
                    title_tv.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void bindHeader(TopicBean topicBean) {
        LogFly.e("topicbean:" + (topicBean == null));
        if (topicBean == null) return;
        WidgetUtils.setText(title_tv, topicBean.getName());
        WidgetUtils.setText(topic_title_tv, topicBean.getName());
        WidgetUtils.setText(description_tv, topicBean.getDesc());
        WidgetUtils.setText(read_num_tv, "浏览" + topicBean.getViews());
        WidgetUtils.setText(canjia_tv, "参与" + topicBean.getUsers());
        WidgetUtils.setVisible(fm_community_home_write_thread_btn, TextUtils.equals(topicBean.getPermission(), "1"));

        WidgetUtils.setVisible(read_num_tv, !TextUtils.isEmpty(topicBean.getViews()));
        WidgetUtils.setVisible(canjia_tv, !TextUtils.isEmpty(topicBean.getUsers()));
    }


    public void initPage(FragmentManager fragmentManager, TopicBean topicBean) {
        typeModeList = new ArrayList<>();
        mListFragments = new ArrayList<>();


        //type==fid 一周热帖
        //type!=fid 话题
        if (topicBean != null && TextUtils.equals(topicBean.getType(), TopicBean.TYPE_FID)) {//一周热帖
            typeModeList.add(new TypeMode().setName("最热"));
            typeModeList.add(new TypeMode().setName("最新"));
            mListFragments.add(TopicListFragment.getFragment("heats"));
            mListFragments.add(TopicListFragment.getFragment("dateline"));
        } else {
            typeModeList.add(new TypeMode().setName("推荐"));
            typeModeList.add(new TypeMode().setName("最新"));
            mListFragments.add(TopicListFragment.getFragment(null));
            mListFragments.add(TopicListFragment.getFragment("dateline"));
        }


        mViewPagerAdapter = new PagerIndicatorAdapter(fragmentManager, mListFragments, typeModeList);
        // 设置标签自动扩展——当标签们的总宽度不够屏幕宽度时，自动扩展使每个标签宽度递增匹配屏幕宽度，注意！这一条代码必须在setViewPager前方可生效
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);
        mTabStrip.setShouldExpand(true);
        mTabStrip.setViewPager(mViewPager);

    }

}
