package com.ideal.flyerteacafes.ui.activity.viewholder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.PagerIndicatorAdapter;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.model.entity.MyMedalsBean;
import com.ideal.flyerteacafes.ui.activity.swingcard.SwingCardHotTaskListFm;
import com.ideal.flyerteacafes.ui.activity.swingcard.SwingCardUserTaskListFm;
import com.ideal.flyerteacafes.ui.fragment.page.MyMedalsFragment;
import com.ideal.flyerteacafes.ui.view.PagerSlidingTabStrip;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2017/10/11.
 */

public class XunzhangVH extends BaseViewHolder {


    public interface IActionListener {


        //toolbar_left
        int ID_TO_CLOSE = R.id.toolbar_left;


        void actionClick(View view);


    }

    private XunzhangVH.IActionListener iActionListener;


    @ViewInject(R.id.task_tabstrip)
    PagerSlidingTabStrip mTabStrip;
    @ViewInject(R.id.task_viewpager)
    ViewPager mViewPager;

    private List<TypeMode> typeModeList;
    private ArrayList<Fragment> mListFragments;

    private PagerIndicatorAdapter mViewPagerAdapter;

    public XunzhangVH(View view, IActionListener iActionListener) {
        x.view().inject(this, view);
        this.iActionListener = iActionListener;
    }


    @Event({IActionListener.ID_TO_CLOSE})
    private void click(View v) {
        if (iActionListener != null) iActionListener.actionClick(v);
    }


    public void initPage(FragmentManager fm, List<MyMedalsBean> datas, String showmedalid, int selectIndex) {

        typeModeList = new ArrayList<>();
        mListFragments = new ArrayList<>();

        for (int i = 0; i < datas.size(); i++) {
            MyMedalsBean bean = datas.get(i);
            typeModeList.add(new TypeMode().setName(bean.getName()));

            MyMedalsFragment fragment = MyMedalsFragment.getFragment(bean.getMedals());
            if (i == selectIndex) {
                fragment.defShowMedalsPopupWindow(showmedalid);
            }
            mListFragments.add(fragment);
        }


        mViewPagerAdapter = new PagerIndicatorAdapter(fm, mListFragments, typeModeList);
        // 设置标签自动扩展——当标签们的总宽度不够屏幕宽度时，自动扩展使每个标签宽度递增匹配屏幕宽度，注意！这一条代码必须在setViewPager前方可生效
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(selectIndex);
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

}
