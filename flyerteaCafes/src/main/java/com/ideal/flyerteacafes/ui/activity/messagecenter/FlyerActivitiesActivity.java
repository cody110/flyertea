package com.ideal.flyerteacafes.ui.activity.messagecenter;

import com.ideal.flyerteacafes.ui.activity.base.AddFmActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.messagecenter.FlyerActiviesFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.RecommendArticleFragment;
import com.ideal.flyerteacafes.ui.view.ToolBar;

/**
 * 飞客活动
 * Created by Cindy on 2017/3/14.
 */
public class FlyerActivitiesActivity extends AddFmActivity {



    @Override
    public void onBackPressed() {
        jumpActivitySetResult(null);
    }

    @Override
    protected void setToolBar(ToolBar toolBar) {
        toolBar.setTitle("活动精选");
    }

    @Override
    protected BaseFragment createFragment() {
        return new RecommendArticleFragment();
    }
}
