package com.ideal.flyerteacafes.ui.activity.myinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PagerIndicatorAdapter;
import com.ideal.flyerteacafes.model.TagEvent;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.model.entity.MyTaskAllBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.HomeActivity;
import com.ideal.flyerteacafes.ui.fragment.page.TopicListFragment;
import com.ideal.flyerteacafes.ui.layout.TaskHeaderLayout;
import com.ideal.flyerteacafes.ui.view.PagerSlidingTabStrip;
import com.ideal.flyerteacafes.ui.view.ToolBar;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.systembartint.SystemBarUtils;
import com.ideal.flyerteacafes.utils.tools.DensityUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2017/5/17.
 */
@ContentView(R.layout.activity_mytask)
public class MyTaskActivity extends BaseActivity {

    @ViewInject(R.id.toolbar)
    ToolBar toolbar;
    @ViewInject(R.id.header_viewpager_layout)
    TaskView header_viewpager_layout;
    @ViewInject(R.id.task_tabstrip)
    PagerSlidingTabStrip mTabStrip;
    @ViewInject(R.id.task_viewpager)
    ViewPager mViewPager;


    private List<TypeMode> typeModeList;
    private ArrayList<Fragment> mListFragments;

    private PagerIndicatorAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setStatusBarColor(this,R.color.app_bg_title);
        x.view().inject(this);
        initTaskView();
        initPage();
    }

    private void initTaskView() {
        //toolbar 高度
        int showHieght = getResources().getDimensionPixelOffset(R.dimen.app_bg_title_height);
        List<MyTaskAllBean.BannerBean> bannerBeanList = (List<MyTaskAllBean.BannerBean>) getIntent().getSerializableExtra("data");
        //headerview
        final TaskHeaderLayout taskHeaderLayout = new TaskHeaderLayout(this);
        taskHeaderLayout.setOrientation(LinearLayout.VERTICAL);
        taskHeaderLayout.bindBanner(bannerBeanList);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        header_viewpager_layout.setmShowHeight(showHieght);
        header_viewpager_layout.addHeaderView(taskHeaderLayout, params);

        toolbar.getBackground().mutate().setAlpha(0);
        header_viewpager_layout.setIIsChildScollTop(new TaskView.IIsChildScollTop() {
            @Override
            public boolean isChildScollTop() {
                if (mViewPager.getCurrentItem() == 0) {
                    MyTaskIngFm fm = (MyTaskIngFm) mListFragments.get(0);
                    return fm.isScrollTop();
                } else {
                    MyTaskListFm fm = (MyTaskListFm) mListFragments.get(mViewPager.getCurrentItem());
                    return fm.isScrollTop();
                }
            }

            @Override
            public void hintedHeight(int heitedHeight) {
                int headerHeight = taskHeaderLayout.getHeight() - toolbar.getHeight();
                if (heitedHeight < headerHeight) {
                    int alpha = heitedHeight * 255 / headerHeight;
                    alpha = alpha > 255 ? 255 : alpha;
                    toolbar.getBackground().mutate().setAlpha(alpha);
                } else {
                    toolbar.getBackground().mutate().setAlpha(255);
                }
            }
        });
    }


    @Event(R.id.toolbar_left)
    private void click(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        EventBus.getDefault().post(new TagEvent(TagEvent.TAG_TASK_BACK));
        finish();
    }

    private void initPage() {
        typeModeList = new ArrayList<>();
        mListFragments = new ArrayList<>();


        typeModeList.add(new TypeMode().setName("进行中"));
        typeModeList.add(new TypeMode().setName("已完成"));
        typeModeList.add(new TypeMode().setName("已过期"));


        mListFragments.add(MyTaskIngFm.getMyTaskIngFm());
        mListFragments.add(MyTaskListFm.getMyTaskDoneFm());
        mListFragments.add(MyTaskListFm.getMyTaskOverFm());


        mViewPagerAdapter = new PagerIndicatorAdapter(getSupportFragmentManager(), mListFragments, typeModeList);
        // 设置标签自动扩展——当标签们的总宽度不够屏幕宽度时，自动扩展使每个标签宽度递增匹配屏幕宽度，注意！这一条代码必须在setViewPager前方可生效
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);
        mTabStrip.setShouldExpand(true);
        mTabStrip.setViewPager(mViewPager);

    }


}
