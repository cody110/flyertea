package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PagerIndicatorAdapter;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.model.entity.ThreadTagBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.fragment.page.ThreadTagListFragment;
import com.ideal.flyerteacafes.ui.fragment.page.ReportTagListFragment;
import com.ideal.flyerteacafes.ui.view.PagerSlidingTabStrip;
import com.ideal.flyerteacafes.ui.view.ToolBar;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fly on 2017/11/30.
 */
@ContentView(R.layout.activity_threadtag)
public class ThreadTagActivity extends BaseActivity {

    @ViewInject(R.id.toolbar)
    private ToolBar toolBar;
    @ViewInject(R.id.tabstrip)
    private PagerSlidingTabStrip mTabStrip;
    @ViewInject(R.id.tabstrip_vp)
    private ViewPager viewPager;


    List<Fragment> fmList = new ArrayList<>();
    List<TypeMode> typeList = new ArrayList<>();

    private ThreadTagBean threadTagBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        threadTagBean = (ThreadTagBean) getIntent().getSerializableExtra("data");
        toolBar.setTitleMaxEmsEnd(10);
        toolBar.setTitle(threadTagBean.getTagname());
        initPage();


        HashMap<String, String> map = new HashMap<>();
        map.put("name", threadTagBean.getTagname());
        MobclickAgent.onEvent(FlyerApplication.getContext(), FinalUtils.EventId.notedetail_tag_click, map);
    }

    @Event(R.id.toolbar_left)
    private void click(View view) {
        finish();
    }


    private void initPage() {
        typeList.clear();

        typeList.add(new TypeMode("相关好文", Utils.HttpRequest.HTTP_REPORT_HOTEL));
        typeList.add(new TypeMode("相关热帖", Utils.HttpRequest.HTTP_REPORT_AVIATION));


        ReportTagListFragment reportTagListFragment = new ReportTagListFragment();
        ThreadTagListFragment threadTagListFragment = new ThreadTagListFragment();


        fmList.add(reportTagListFragment);
        fmList.add(threadTagListFragment);


        PagerIndicatorAdapter viewpagerFragmentAdapter = new PagerIndicatorAdapter(getSupportFragmentManager(), fmList, typeList);
        viewPager.setAdapter(viewpagerFragmentAdapter);


        viewPager.setOffscreenPageLimit(2);
        mTabStrip.setShouldExpand(true);
        mTabStrip.setViewPager(viewPager);


    }
}
