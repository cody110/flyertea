package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.UserManger;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.fragment.page.MyReadsFragment2;
import com.ideal.flyerteacafes.ui.fragment.page.MyReadsFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

/**
 * Created by fly on 2016/11/29.
 * 最近浏览
 */
@ContentView(R.layout.activity_my_reads)
public class MyReadsActivity extends BaseActivity {

    /**
     * 本地d
     */
    private MyReadsFragment myReadsFragment;
    /**
     * 线上
     */
    private MyReadsFragment2 myReadsFragment2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        ChooseTabFragment();
    }

    @Event(R.id.toolbar_left)
    private void click(View v) {
        finish();
    }



    private void ChooseTabFragment() {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        // 先隐藏掉显示的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        if (!UserManger.isLogin()) {
            if (myReadsFragment == null) {
                myReadsFragment = new MyReadsFragment();
                transaction.add(R.id.my_reads_fragment, myReadsFragment);
            } else {
                transaction.show(myReadsFragment);
            }
        } else {
            if (myReadsFragment2 == null) {
                myReadsFragment2 = new MyReadsFragment2();
                transaction.add(R.id.my_reads_fragment, myReadsFragment2);
            } else {
                transaction.show(myReadsFragment2);
            }
        }

        transaction.commitAllowingStateLoss();

    }


    private void hideFragments(FragmentTransaction transaction) {
        if(myReadsFragment!=null)
            transaction.hide(myReadsFragment);
        if(myReadsFragment2!=null)
            transaction.hide(myReadsFragment2);
    }

}
