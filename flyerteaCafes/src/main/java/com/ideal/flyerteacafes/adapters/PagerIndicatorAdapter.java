package com.ideal.flyerteacafes.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ideal.flyerteacafes.model.TypeMode;

import java.util.List;

/**
 * Created by fly on 2016/5/18.
 */
public class PagerIndicatorAdapter extends FragmentPagerAdapter {

    List<Fragment> fmList;
    List<TypeMode> modeList;

    public PagerIndicatorAdapter(FragmentManager fm, List fmList, List<TypeMode> modeList) {
        super(fm);
        this.fmList = fmList;
        this.modeList = modeList;
    }

    @Override
    public Fragment getItem(int position) {
        return fmList.get(position);
    }

    @Override
    public int getCount() {
        return fmList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return modeList.get(position).getName();
    }


}
