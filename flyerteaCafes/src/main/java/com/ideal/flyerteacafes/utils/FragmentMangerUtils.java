package com.ideal.flyerteacafes.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2018/1/19.
 * 帮助管理fragment
 */

public class FragmentMangerUtils {


    FragmentManager fm;
    private int replyViewId = -1;
    private List<Fragment> fragmentList = new ArrayList<>();
    private Fragment showFragment;


    public FragmentMangerUtils(FragmentManager fragmentManager) {
        fm = fragmentManager;
    }

    public FragmentMangerUtils(FragmentManager fragmentManager, int replyViewId) {
        fm = fragmentManager;
        this.replyViewId = replyViewId;
    }


    public Fragment setShowFragment(Class fclass) {
        if (replyViewId == -1) {
            new RuntimeException(" Please pass in reply viewid ");
        }
        return setShowFragment(fclass, replyViewId);
    }

    /**
     * create or show fragment
     */
    public Fragment setShowFragment(Class fclass, int replyViewId) {
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragments(transaction);
        String tag = fclass.getName();
        Fragment fragment = fm.findFragmentByTag(tag);
        if (fragment == null) {
            try {
                fragment = (Fragment) fclass.newInstance();
                transaction.add(replyViewId, fragment, tag);
                fragmentList.add(fragment);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            transaction.show(fragment);
        }
        showFragment = fragment;
        transaction.commit();
        return fragment;
    }

    /**
     * 隐藏fragment
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        for (Fragment f : fragmentList) {
            transaction.hide(f);
        }
    }

    public Fragment getShowFragment() {
        return showFragment;
    }


    /**
     * 根据类的名称来生产对象
     *
     * @param className
     * @return
     */
    public static Fragment getFragmentByClass(String className) {

        try {
            Fragment fragment = (Fragment) Class.forName(className).newInstance();
            return fragment;
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
