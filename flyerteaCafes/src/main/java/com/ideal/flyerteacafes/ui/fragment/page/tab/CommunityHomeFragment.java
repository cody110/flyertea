package com.ideal.flyerteacafes.ui.fragment.page.tab;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PagerIndicatorAdapter;
import com.ideal.flyerteacafes.caff.SharedPreferencesString;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.ui.activity.search.ThreadSearchActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.community.CommunityPlateFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.community.ReadThreadFragment;
import com.ideal.flyerteacafes.ui.popupwindow.RemindSetPopupWindow;
import com.ideal.flyerteacafes.ui.popupwindow.WritePostChooseTypePop;
import com.ideal.flyerteacafes.ui.view.PagerSlidingTabStrip;
import com.ideal.flyerteacafes.utils.DialogUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 社区首页
 */
public class CommunityHomeFragment extends BaseFragment {


    @ViewInject(R.id.task_tabstrip)
    PagerSlidingTabStrip mTabStrip;
    @ViewInject(R.id.fm_community_home_vp)
    ViewPager viewPager;


    List<Fragment> fmList = new ArrayList<>();
    List<TypeMode> typeList = new ArrayList<>();

    WritePostChooseTypePop writePostChooseTypePop;

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_home, container, false);
        x.view().inject(this, view);
        initPage();
        isShowBarrage();
        return view;
    }

    private void initPage() {

        fmList.add(new ReadThreadFragment());
        fmList.add(new CommunityPlateFragment());

        typeList.clear();

        typeList.add(new TypeMode().setName("读帖"));
        typeList.add(new TypeMode().setName("版块"));

        PagerIndicatorAdapter viewpagerFragmentAdapter = new PagerIndicatorAdapter(getChildFragmentManager(), fmList, typeList);
        viewPager.setAdapter(viewpagerFragmentAdapter);


        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);
        mTabStrip.setShouldExpand(true);
        mTabStrip.setViewPager(viewPager);

    }

    @Event({R.id.fm_community_home_forum, R.id.fm_community_home_follow, R.id.fm_community_home_write_thread_btn, R.id.fm_community_home_search})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.fm_community_home_search:
                jumpActivity(ThreadSearchActivity.class, null);
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

    public void setSelectFragment(final int index) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(index);
            }
        });

    }


    /**
     * 处理是否显示逻辑
     */
    private void isShowBarrage() {
        if (!SharedPreferencesString.getInstances().getBooleanToKey("forum_no_first_1")) {
            RemindSetPopupWindow window = new RemindSetPopupWindow(mActivity);
            window.showAtLocation(viewPager, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }


    private void sendThreadClick() {
        if (UserManger.isLogin()) {
            if (writePostChooseTypePop == null) {
                writePostChooseTypePop = new WritePostChooseTypePop(mActivity);
            }
            writePostChooseTypePop.showAtLocation(viewPager, Gravity.BOTTOM, 0, 0);

        } else {
            DialogUtils.showDialog(getActivity());
        }
    }

}
