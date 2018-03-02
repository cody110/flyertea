package com.ideal.flyerteacafes.ui.activity;

import com.ideal.flyerteacafes.ui.activity.base.AddFmActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.reportlist.HotelListFragment;
import com.ideal.flyerteacafes.ui.fragment.page.tab.reportlist.ReportListFragment;
import com.ideal.flyerteacafes.ui.view.ToolBar;

/**
 * Created by fly on 2018/2/7.
 */

public class ReportListActivity extends AddFmActivity {
    @Override
    protected void setToolBar(ToolBar toolBar) {
        String title = getIntent().getStringExtra("title");
        toolBar.setTitleMaxEmsEnd(10);
        toolBar.setTitle(title);
    }

    @Override
    protected BaseFragment createFragment() {
        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("requestUrl");
        ReportListFragment hotelListFragment = ReportListFragment.setArguments(new HotelListFragment(), url);
        hotelListFragment.setDetailsTitle(title);
        return hotelListFragment;
    }
}
