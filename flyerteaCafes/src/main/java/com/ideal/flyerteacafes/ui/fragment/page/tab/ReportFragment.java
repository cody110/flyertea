package com.ideal.flyerteacafes.ui.fragment.page.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PagerIndicatorAdapter;
import com.ideal.flyerteacafes.caff.BaseDataManger;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.ui.activity.search.ThreadSearchActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.AlbumPresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.WriteThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.search.ThreadSearchActivity2;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteMajorThreadContentActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.reportlist.LoungeListFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.reportlist.ReportListFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.reportlist.FlyerListFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.reportlist.HotelListFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.ReportPresenter;
import com.ideal.flyerteacafes.ui.view.PagerSlidingTabStrip;
import com.ideal.flyerteacafes.utils.DialogUtils;
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
 * Created by fly on 2017/6/28.
 */
@ContentView(R.layout.fragment_community_home)
public class ReportFragment extends BaseFragment {


    @ViewInject(R.id.task_tabstrip)
    PagerSlidingTabStrip mTabStrip;
    @ViewInject(R.id.fm_community_home_vp)
    ViewPager viewPager;


    List<Fragment> fmList = new ArrayList<>();
    List<TypeMode> typeList = new ArrayList<>();

    private int selectIndex = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_home, container, false);
        x.view().inject(this, view);
        initPage();
        return view;
    }

    private void initPage() {


        typeList.clear();

        typeList.add(new TypeMode("酒店", Utils.HttpRequest.HTTP_REPORT_HOTEL));
        typeList.add(new TypeMode("航空", Utils.HttpRequest.HTTP_REPORT_AVIATION));
        typeList.add(new TypeMode("休息室", Utils.HttpRequest.HTTP_REPORT_LOUNGE));


        ReportListFragment hotelListFragment = ReportListFragment.setArguments(new HotelListFragment(), Utils.HttpRequest.HTTP_REPORT_HOTEL);
        hotelListFragment.setIGetSortid(new ReportPresenter.IGetSortid() {
            @Override
            public String getSortid() {
                if (BaseDataManger.getInstance().getTypeBaseBean() == null) return null;
                if (BaseDataManger.getInstance().getTypeBaseBean().getHotelReport() == null)
                    return null;
                return BaseDataManger.getInstance().getTypeBaseBean().getHotelReport().getSortid();
            }
        });
        hotelListFragment.setDetailsTitle("酒店报告");


        ReportListFragment flyerListFragment = ReportListFragment.setArguments(new FlyerListFragment(), Utils.HttpRequest.HTTP_REPORT_AVIATION);
        flyerListFragment.setIGetSortid(new ReportPresenter.IGetSortid() {
            @Override
            public String getSortid() {
                if (BaseDataManger.getInstance().getTypeBaseBean() == null) return null;
                if (BaseDataManger.getInstance().getTypeBaseBean().getAviationReport() == null)
                    return null;
                return BaseDataManger.getInstance().getTypeBaseBean().getAviationReport().getSortid();
            }
        });
        flyerListFragment.setDetailsTitle("飞行报告");


        ReportListFragment loungeListFragment = ReportListFragment.setArguments(new LoungeListFragment(), Utils.HttpRequest.HTTP_REPORT_LOUNGE);
        loungeListFragment.setDetailsTitle("休息室报告");


        fmList.add(hotelListFragment);
        fmList.add(flyerListFragment);
        fmList.add(loungeListFragment);


        PagerIndicatorAdapter viewpagerFragmentAdapter = new PagerIndicatorAdapter(getChildFragmentManager(), fmList, typeList);
        viewPager.setAdapter(viewpagerFragmentAdapter);


        viewPager.setCurrentItem(selectIndex);
        viewPager.setOffscreenPageLimit(2);
        mTabStrip.setShouldExpand(true);
        mTabStrip.setViewPager(viewPager);

        mTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectIndex = position;

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", typeList.get(position).getName());
                MobclickAgent.onEvent(mActivity, FinalUtils.EventId.report_tab_click, map);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Event({R.id.fm_community_home_forum, R.id.fm_community_home_follow, R.id.fm_community_home_write_thread_btn, R.id.fm_community_home_search})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.fm_community_home_search:
                jumpActivity(ThreadSearchActivity2.class);
                break;

            case R.id.fm_community_home_forum:
                viewPager.setCurrentItem(0);
                break;

            case R.id.fm_community_home_follow:
                viewPager.setCurrentItem(1);
                break;
            case R.id.fm_community_home_write_thread_btn:
                sendThreadClick();
                break;
        }
    }


    private void sendThreadClick() {
        if (UserManger.isLogin()) {
            Bundle bundle = new Bundle();
            bundle.putString(AlbumPresenter.BUNDLE_FROM_TYPE, AlbumPresenter.BUNDLE_FROM_MAJOR_THREAD_FIRST);
            bundle.putInt(AlbumPresenter.BUNDLE_NEED_SIZE, 30);
            if (selectIndex == 0) {
                bundle.putString(WriteThreadPresenter.BUNDLE_FID_1, "19");
                bundle.putString(WriteThreadPresenter.BUNDLE_FID_2, "99");
            } else {
                bundle.putString(WriteThreadPresenter.BUNDLE_FID_1, "57");
                bundle.putString(WriteThreadPresenter.BUNDLE_FID_2, "98");
            }
            jumpActivity(WriteMajorThreadContentActivity.class, bundle);
        } else {
            DialogUtils.showDialog(mActivity);
        }
    }

}
