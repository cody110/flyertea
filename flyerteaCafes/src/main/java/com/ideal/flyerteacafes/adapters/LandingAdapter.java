package com.ideal.flyerteacafes.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class LandingAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fmList;

    public LandingAdapter(FragmentManager fm, List<Fragment> fmList) {
        super(fm);
        this.fmList = (ArrayList<Fragment>) fmList;
    }

    @Override
    public Fragment getItem(int arg0) {
        return fmList.get(arg0);
    }

    @Override
    public int getCount() {
        return fmList.size();
    }

}
