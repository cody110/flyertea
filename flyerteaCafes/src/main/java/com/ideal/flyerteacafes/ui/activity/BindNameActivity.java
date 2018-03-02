package com.ideal.flyerteacafes.ui.activity;

import com.ideal.flyerteacafes.ui.activity.base.AddFmActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.login.BindNameFragment;
import com.ideal.flyerteacafes.ui.view.ToolBar;

/**
 * Created by fly on 2016/1/7.
 * 手机号注册用户绑定用户名界面
 */
public class BindNameActivity extends AddFmActivity {


    @Override
    protected void setToolBar(ToolBar toolBar) {
        toolBar.setTitle("绑定已有账号");
    }

    @Override
    protected BaseFragment createFragment() {
        return new BindNameFragment();
    }
}
