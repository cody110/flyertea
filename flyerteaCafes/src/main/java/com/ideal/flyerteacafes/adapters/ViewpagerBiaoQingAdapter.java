package com.ideal.flyerteacafes.adapters;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewpagerBiaoQingAdapter extends FragmentPagerAdapter {

    private List<Fragment> fmList;

    public ViewpagerBiaoQingAdapter(FragmentManager fm, List<Fragment> fmList) {
        super(fm);
        // TODO Auto-generated constructor stub
        this.fmList = fmList;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return fmList.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fmList.size();
    }

}
