package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PagerIndicatorAdapter;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.fragment.page.ManagementPlateAllFragment;
import com.ideal.flyerteacafes.ui.fragment.page.ManagementPlateFollowFragment;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2016/12/22.
 */
@ContentView(R.layout.activity_management_plate)
public class ManagementPlateActivity extends BaseActivity {

    @ViewInject(R.id.viewpager_indicator_viewpager)
    ViewPager viewPager;

    @ViewInject(R.id.fm_community_home_forum)
    TextView fm_community_home_forum;
    @ViewInject(R.id.fm_community_home_follow)
    TextView fm_community_home_follow;

    public static final String BUNDLE_COMMUNITY = "community", BUNDLE_MYFAV = "fav";


    List<Fragment> fmList = new ArrayList<>();
    List<TypeMode> typeList = new ArrayList<>();

    ManagementPlateFollowFragment followFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initVariables();
        initViews();
    }

    @Override
    public void initVariables() {
        super.initVariables();

        fmList.add(new ManagementPlateAllFragment());
        fmList.add(followFragment = new ManagementPlateFollowFragment());
        typeList.add(new TypeMode().setName("所有版块"));
        typeList.add(new TypeMode().setName("关注版块"));
        setSelectTv(0);
    }

    @Override
    public void initViews() {
        super.initViews();
        PagerIndicatorAdapter viewpagerFragmentAdapter = new PagerIndicatorAdapter(getSupportFragmentManager(), fmList, typeList);
        viewPager.setAdapter(viewpagerFragmentAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelectTv(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setSelectTv(int position) {
        if (position == 0) {
            fm_community_home_forum.setTextColor(getResources().getColor(R.color.text_black));
            fm_community_home_forum.setBackground(getResources().getDrawable(R.drawable.title_bg_text_bottom_line_bg));
            fm_community_home_follow.setTextColor(getResources().getColor(R.color.text_black));
            fm_community_home_follow.setBackground(null);
        } else {
            fm_community_home_forum.setTextColor(getResources().getColor(R.color.text_black));
            fm_community_home_follow.setTextColor(getResources().getColor(R.color.text_black));
            fm_community_home_follow.setBackground(getResources().getDrawable(R.drawable.title_bg_text_bottom_line_bg));
            fm_community_home_forum.setBackground(null);
        }
    }

    @Event(R.id.toolbar_left)
    private void closePage(View v) {
        finish();
    }

    @Event({R.id.fm_community_home_forum, R.id.fm_community_home_follow})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.fm_community_home_forum:
                viewPager.setCurrentItem(0);
                break;

            case R.id.fm_community_home_follow:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
