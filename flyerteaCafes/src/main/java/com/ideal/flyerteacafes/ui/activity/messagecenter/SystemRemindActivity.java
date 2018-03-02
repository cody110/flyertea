package com.ideal.flyerteacafes.ui.activity.messagecenter;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.AddFmActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.messagecenter.SystemRemindFragment;
import com.ideal.flyerteacafes.ui.view.ToolBar;

/**
 * @version V1.7
 * @author: Cindy
 * @description: 系统提醒
 * @date: 2017/3/14 16:25
 */
public class SystemRemindActivity extends AddFmActivity {


    @Override
    protected void setToolBar(ToolBar toolBar) {
        toolBar.setTitle(getString(R.string.other_message_ac_title));
    }

    @Override
    protected BaseFragment createFragment() {
        return new SystemRemindFragment();
    }


    @Override
    public void onBackPressed() {
        jumpActivitySetResult(null);
    }
}
