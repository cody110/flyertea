package com.ideal.flyerteacafes.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by fly on 2016/12/21.
 */

public class ViewpagerFragmentAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fmList;

    public ViewpagerFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fmList) {
        super(fm);
        this.fmList = fmList;
    }

    @Override
    public Fragment getItem(int position) {
        return fmList.get(position);
    }

    @Override
    public int getCount() {
        return fmList.size();
    }
}
