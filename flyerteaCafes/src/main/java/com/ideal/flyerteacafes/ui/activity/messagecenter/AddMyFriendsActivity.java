package com.ideal.flyerteacafes.ui.activity.messagecenter;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.AddFmActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.messagecenter.AddMyFriendsFragment;
import com.ideal.flyerteacafes.ui.view.ToolBar;

/**
 * 加我好友
 * Created by Cindy on 2017/3/14.
 */
public class AddMyFriendsActivity extends AddFmActivity {

    @Override
    protected void setToolBar(ToolBar toolBar) {
        toolBar.setTitle(getString(R.string.add_my_friends_ac_title));
    }

    @Override
    protected BaseFragment createFragment() {
        return new AddMyFriendsFragment();
    }

    @Override
    public void onBackPressed() {
        jumpActivitySetResult(null);
    }
}
