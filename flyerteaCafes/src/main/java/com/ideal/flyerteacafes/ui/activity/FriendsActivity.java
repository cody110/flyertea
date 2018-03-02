package com.ideal.flyerteacafes.ui.activity;

import com.ideal.flyerteacafes.ui.activity.base.AddFmActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.FriendsFragment;
import com.ideal.flyerteacafes.ui.view.ToolBar;

public class FriendsActivity extends AddFmActivity {


    @Override
    protected void setToolBar(ToolBar toolBar) {
        toolBar.setTitle("我的好友");
    }

    @Override
    protected BaseFragment createFragment() {
        return new FriendsFragment();
    }


}
