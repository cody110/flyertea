package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.caff.FlyerApplication;
import com.ideal.flyerteacafes.ui.activity.base.BaseToolbarActivity;
import com.ideal.flyerteacafes.utils.FragmentMangerUtils;
import com.ideal.flyerteacafes.utils.tools.V;
import com.umeng.socialize.UMShareAPI;

import org.xutils.view.annotation.Event;
import org.xutils.x;

/**
 * Created by fly on 2016/1/7.
 * 登录注册绑定 基类
 */
public abstract class LoginBaseActivity extends BaseToolbarActivity {

    protected int chooseLeft = R.id.share_type_phone, chooseRight = R.id.share_type_username;
    protected TextView leftBtn, rightBtn;
    protected String leftShowTxt, rightShowTxt;//必须在子类赋值
    protected Fragment leftFragment, rightFragment;
    protected String leftFragmentName, rightFragmentName;//必须在子类赋值


    @Override
    protected View createBodyView(Bundle savedInstanceState) {
        View view = getView(R.layout.activity_third_login);
        x.view().inject(this, view);
        init();
        initView(view);
        chooseFragment(chooseLeft);
        return view;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(FlyerApplication.getContext()).onActivityResult(requestCode, resultCode, data);
    }

    protected abstract void init();

    private void initView(View view) {
        leftBtn = V.f(view, R.id.share_type_phone);
        rightBtn = V.f(view, R.id.share_type_username);
        leftBtn.setText(leftShowTxt);
        rightBtn.setText(rightShowTxt);
    }

    private void ChooseTabFragment(int id) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        if (id == R.id.share_type_phone) {
            leftFragment = getSupportFragmentManager().findFragmentByTag(leftFragmentName);
            if (leftFragment == null) {
                leftFragment = FragmentMangerUtils.getFragmentByClass(leftFragmentName);
                transaction.add(R.id.login_fragment_layout, leftFragment, leftFragmentName);
            } else {
                transaction.show(leftFragment);
            }
        } else if (id == R.id.share_type_username) {
            rightFragment = getSupportFragmentManager().findFragmentByTag(rightFragmentName);
            if (rightFragment == null) {
                rightFragment = FragmentMangerUtils.getFragmentByClass(rightFragmentName);
                transaction.add(R.id.login_fragment_layout, rightFragment, rightFragmentName);
            } else {
                transaction.show(rightFragment);
            }
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (leftFragment != null)
            transaction.hide(leftFragment);
        if (rightFragment != null)
            transaction.hide(rightFragment);
    }


    private void chooseTabView(int id) {
        leftBtn.setBackground(getResources().getDrawable(R.drawable.type_login_left_checked));
        rightBtn.setBackground(getResources().getDrawable(R.drawable.type_login_right));
        leftBtn.setTextColor(getResources().getColor(R.color.white));
        rightBtn.setTextColor(getResources().getColor(R.color.app_bg_title));
        if (id == R.id.share_type_phone) {
            leftBtn.setBackground(getResources().getDrawable(R.drawable.type_login_left_checked));
            rightBtn.setBackground(getResources().getDrawable(R.drawable.type_login_right));
            leftBtn.setTextColor(getResources().getColor(R.color.white));
            rightBtn.setTextColor(getResources().getColor(R.color.app_bg_title));
        } else if (id == R.id.share_type_username) {
            leftBtn.setBackground(getResources().getDrawable(R.drawable.type_login_left_unchecked));
            rightBtn.setBackground(getResources().getDrawable(R.drawable.type_login_right_checked));
            leftBtn.setTextColor(getResources().getColor(R.color.app_bg_title));
            rightBtn.setTextColor(getResources().getColor(R.color.white));
        }
    }

    protected void chooseFragment(int id) {
        ChooseTabFragment(id);
        chooseTabView(id);
    }


    @Event({R.id.share_type_phone, R.id.share_type_username})
    private void click(View v) {
        if (v.getId() == R.id.share_type_phone) {
            chooseFragment(chooseLeft);
        } else if (v.getId() == R.id.share_type_username) {
            chooseFragment(chooseRight);
        }
    }


}
