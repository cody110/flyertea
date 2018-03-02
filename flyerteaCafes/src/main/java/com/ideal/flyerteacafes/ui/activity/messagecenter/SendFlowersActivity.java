package com.ideal.flyerteacafes.ui.activity.messagecenter;

import com.ideal.flyerteacafes.ui.activity.base.AddFmActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.messagecenter.SendFlowersFragment;
import com.ideal.flyerteacafes.ui.view.ToolBar;

/**
 * @version V1.7
 * @description: 送我鲜花
 * @author: Cindy
 * @date: 2017/3/14 16:25
 */
public class SendFlowersActivity extends AddFmActivity {



    @Override
    protected void setToolBar(ToolBar toolBar) {
        toolBar.setTitle("送我鲜花");
    }

    @Override
    protected BaseFragment createFragment() {
        return new SendFlowersFragment();
    }


    @Override
    public void onBackPressed() {
        jumpActivitySetResult(null);
    }
}
