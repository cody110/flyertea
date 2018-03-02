package com.ideal.flyerteacafes.ui.activity.swingcard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PagerIndicatorAdapter;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.view.PagerSlidingTabStrip;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2017/4/12.
 * 刷卡任务
 */
@ContentView(R.layout.activity_swingcard_home)
public class SwingCardHome extends BaseActivity {

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
        x.view().inject(this);
        initPage();
    }


    private void initPage() {
        typeModeList = new ArrayList<>();
        mListFragments = new ArrayList<>();


        typeModeList.add(new TypeMode().setName("待完成"));
        typeModeList.add(new TypeMode().setName("已结束"));
        typeModeList.add(new TypeMode().setName("热门任务"));

        SwingCardUserTaskListFm over = new SwingCardUserTaskListFm();
        Bundle bundle = new Bundle();
        bundle.putString("overtime", "yes");
        over.setArguments(bundle);

        mListFragments.add(new SwingCardUserTaskListFm());
        mListFragments.add(over);
        mListFragments.add(new SwingCardHotTaskListFm());


        mViewPagerAdapter = new PagerIndicatorAdapter(getSupportFragmentManager(), mListFragments, typeModeList);
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

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Event({R.id.toolbar_left, R.id.toolbar_right})
    private void onclick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left:
                finish();
                break;

            case R.id.toolbar_right:
                jumpActivityForResult(SwingCardAddTask.class, null, 11);
                break;
        }

    }

}
