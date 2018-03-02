package com.ideal.flyerteacafes.ui.activity;

import com.ideal.flyerteacafes.ui.view.ToolBar;

/**
 * Created by fly on 2016/1/6.
 */
public class BindingFlyActivity extends LoginBaseActivity {

    @Override
    protected void setTitleBar(ToolBar mToolBar) {
        mToolBar.setTitle("绑定现有账号");
    }

    @Override
    protected void init() {
        leftShowTxt = "手机号登录";
        rightShowTxt = "用户名登录";
        leftFragmentName = "com.ideal.flyerteacafes.ui.fragment.page.login.BindMobileFragment";
        rightFragmentName = "com.ideal.flyerteacafes.ui.fragment.page.login.BindNameFragment";
    }

}
