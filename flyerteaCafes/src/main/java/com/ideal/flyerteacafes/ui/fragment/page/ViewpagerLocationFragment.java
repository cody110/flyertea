package com.ideal.flyerteacafes.ui.fragment.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PagerIndicatorAdapter;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.model.entity.LocationListBean;
import com.ideal.flyerteacafes.ui.activity.LocationSearchActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.ServiceFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.hometab.DiscountFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.hometab.RaidersFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.hometab.RecommendFragment;
import com.ideal.flyerteacafes.ui.view.PagerSlidingTabStrip;
import com.ideal.flyerteacafes.ui.view.SerachEdittext;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fly on 2017/10/17.
 */

public class ViewpagerLocationFragment extends BaseFragment {

    public static ViewpagerLocationFragment getFragment(LocationListBean data) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        ViewpagerLocationFragment fm = new ViewpagerLocationFragment();
        fm.setArguments(bundle);
        return fm;
    }


    @ViewInject(R.id.tabstrip)
    PagerSlidingTabStrip mTabStrip;
    @ViewInject(R.id.viewpager)
    ViewPager mViewPager;

    private List<TypeMode> typeModeList;
    private ArrayList<Fragment> mListFragments;

    private PagerIndicatorAdapter mViewPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_pager_slidingtabstrip, container, false);
        x.view().inject(this, view);
        LocationListBean locationListBean = (LocationListBean) getArguments().get("data");
        initPage(locationListBean);
        return view;
    }


    @Event(R.id.search_layout)
    private void click(View view) {
        jumpActivityForResult(LocationSearchActivity.class, null,3);
    }

    private void initPage(LocationListBean locationListBean) {
        if (locationListBean == null) return;
        typeModeList = new ArrayList<>();
        mListFragments = new ArrayList<>();


        if (!DataUtils.isEmpty(locationListBean.getHotels())) {
            typeModeList.add(new TypeMode().setName("酒店"));
            mListFragments.add(LocationFragment.getFragment(locationListBean.getHotels(), locationListBean.getHoteltags()));
        }

        if (!DataUtils.isEmpty(locationListBean.getAirports())) {
            typeModeList.add(new TypeMode().setName("机场"));
            mListFragments.add(LocationFragment.getFragment(locationListBean.getAirports(), locationListBean.getAirporttags()));
        }

        if (!DataUtils.isEmpty(locationListBean.getLounges())) {
            typeModeList.add(new TypeMode().setName("候机室"));
            mListFragments.add(LocationFragment.getFragment(locationListBean.getLounges(), locationListBean.getLoungetags()));
        }


//        typeModeList.add(new TypeMode().setName("推荐"));
//        typeModeList.add(new TypeMode().setName("优惠"));
//        typeModeList.add(new TypeMode().setName("攻略"));
//        typeModeList.add(new TypeMode().setName("工具"));
//
//
//        mListFragments.add(new RecommendFragment());
//        mListFragments.add(new DiscountFragment());
//        mListFragments.add(new RaidersFragment());
//        mListFragments.add(new ServiceFragment());

        mViewPagerAdapter = new PagerIndicatorAdapter(getChildFragmentManager(), mListFragments, typeModeList);
        // 设置标签自动扩展——当标签们的总宽度不够屏幕宽度时，自动扩展使每个标签宽度递增匹配屏幕宽度，注意！这一条代码必须在setViewPager前方可生效
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);
        mTabStrip.setShouldExpand(true);
        mTabStrip.setViewPager(mViewPager);
    }

}
