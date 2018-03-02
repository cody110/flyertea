package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PagerIndicatorAdapter;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.AlbumPresenter;
import com.ideal.flyerteacafes.ui.activity.presenter.WriteThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteMajorThreadContentActivity;
import com.ideal.flyerteacafes.ui.activity.writethread.WriteThreadActivity;
import com.ideal.flyerteacafes.ui.fragment.page.tab.hometab.InterlocutionFragment;
import com.ideal.flyerteacafes.ui.popupwindow.WritePostChooseTypePop;
import com.ideal.flyerteacafes.ui.view.PagerSlidingTabStrip;
import com.ideal.flyerteacafes.utils.DialogUtils;
import com.ideal.flyerteacafes.utils.FinalUtils;
import com.ideal.flyerteacafes.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by fly on 2016/6/2.
 */
@ContentView(R.layout.activity_interlocution)
public class InterlocutionActivity extends BaseActivity {


    @ViewInject(R.id.tabstrip)
    PagerSlidingTabStrip mTabStrip;
    @ViewInject(R.id.viewpager_indicator_viewpager)
    ViewPager mViewPager;

    WritePostChooseTypePop writePostChooseTypePop;

    private List<TypeMode> typeModeList;
    private ArrayList<Fragment> mListFragments;

    private PagerIndicatorAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        EventBus.getDefault().register(this);
        initPage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initPage() {
        typeModeList = new ArrayList<>();
        mListFragments = new ArrayList<>();

        setFmList(typeModeList);
    }


    public void setFmList(List<TypeMode> typeModeList) {

        if (UserManger.isLogin()) {
            typeModeList.add(new TypeMode("推荐", Utils.HttpRequest.HTTP_INTERLOCUTION_TUIJAN));
        } else {
            typeModeList.add(new TypeMode("全部", Utils.HttpRequest.HTTP_INTERLOCUTION));
        }
        typeModeList.add(new TypeMode("酒店", Utils.HttpRequest.HTTP_INTERLOCUTION_HOTEL));
        typeModeList.add(new TypeMode("航空", Utils.HttpRequest.HTTP_INTERLOCUTION_AVIATION));
        typeModeList.add(new TypeMode("信用卡", Utils.HttpRequest.HTTP_INTERLOCUTION_CREDITCARD));


        mListFragments.add(InterlocutionFragment.getFragment(typeModeList.get(0)));
        mListFragments.add(InterlocutionFragment.getFragment(typeModeList.get(1)));
        mListFragments.add(InterlocutionFragment.getFragment(typeModeList.get(2)));
        mListFragments.add(InterlocutionFragment.getFragment(typeModeList.get(3)));


        mViewPagerAdapter = new PagerIndicatorAdapter(getSupportFragmentManager(), mListFragments, typeModeList);
        // 设置标签自动扩展——当标签们的总宽度不够屏幕宽度时，自动扩展使每个标签宽度递增匹配屏幕宽度，注意！这一条代码必须在setViewPager前方可生效
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);
        mTabStrip.setShouldExpand(true);
        mTabStrip.setViewPager(mViewPager);
    }


    @Event({R.id.tiwen_btn, R.id.back_btn})
    private void click(View v) {

        if (v.getId() == R.id.back_btn) {
            finish();
        }
        if (v.getId() == R.id.tiwen_btn) {
            if (UserManger.isLogin()) {
                Bundle bundle = new Bundle();
                bundle.putString(WriteThreadPresenter.BUNDLE_FROM_TYPE, WriteThreadPresenter.BUNDLE_FROM_WENDA);
                bundle.putString(WriteThreadPresenter.BUNDLE_TYPEID, "1");
                jumpActivity(WriteMajorThreadContentActivity.class, bundle);
            } else {
                DialogUtils.showDialog(this);
            }
        }
    }


    public void onEventMainThread(String str) {
        if (str.equals(FinalUtils.EventId.forum_post_write_back)) {
            mViewPager.setCurrentItem(0);
            InterlocutionFragment fm = (InterlocutionFragment) mListFragments.get(0);
            fm.headerRefreshing();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int index = data.getIntExtra("position", -1);
            if (index != -1) {
                InterlocutionFragment fm = (InterlocutionFragment) mListFragments.get(mViewPager.getCurrentItem());
                fm.removeIndex(index);
            }
        }
    }
}
