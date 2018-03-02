package com.ideal.flyerteacafes.ui.activity;

import com.ideal.flyerteacafes.ui.activity.base.AddFmActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.login.BindMobileBySetFragment;
import com.ideal.flyerteacafes.ui.view.ToolBar;

/**
 * Created by fly on 2016/1/7.
 */
public class BindShouJiActivity extends AddFmActivity {


    @Override
    protected void setToolBar(ToolBar toolBar) {
        toolBar.setTitle("手机号绑定");
    }

    @Override
    protected BaseFragment createFragment() {
        return new BindMobileBySetFragment();
    }
}
