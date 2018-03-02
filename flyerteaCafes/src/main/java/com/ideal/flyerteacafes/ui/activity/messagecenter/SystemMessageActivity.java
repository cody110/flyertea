package com.ideal.flyerteacafes.ui.activity.messagecenter;

import com.ideal.flyerteacafes.ui.activity.base.AddFmActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.messagecenter.SystemMessageFragment;
import com.ideal.flyerteacafes.ui.view.ToolBar;

/**
 * Created by fly on 2017/12/7.
 */

public class SystemMessageActivity extends AddFmActivity {


    @Override
    protected void setToolBar(ToolBar toolBar) {
        toolBar.setTitle("系统消息");
    }

    @Override
    protected BaseFragment createFragment() {
        return new SystemMessageFragment();
    }


    @Override
    public void onBackPressed() {
        jumpActivitySetResult(null);
    }
}
