package com.ideal.flyerteacafes.ui.activity;

import com.ideal.flyerteacafes.ui.activity.base.AddFmActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.RecommendArticleFragment;
import com.ideal.flyerteacafes.ui.view.ToolBar;

/**
 * Created by fly on 2017/12/14.
 */

public class RecommendArticleActivity extends AddFmActivity {

    @Override
    public void onBackPressed() {
        jumpActivitySetResult(null);
        super.onBackPressed();
    }

    @Override
    protected void setToolBar(ToolBar toolBar) {
        toolBar.setTitle("好文推荐");
    }

    @Override
    protected BaseFragment createFragment() {
        return new RecommendArticleFragment();
    }
}
