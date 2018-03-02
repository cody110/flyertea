package com.ideal.flyerteacafes.ui.activity;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.AddFmActivity;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.fragment.page.NearbyFragment;
import com.ideal.flyerteacafes.ui.view.ToolBar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class NearbyActivity extends AddFmActivity {


    @Override
    protected void setToolBar(ToolBar toolBar) {
        toolBar.setTitle(getString(R.string.people_nearby));
    }

    @Override
    protected BaseFragment createFragment() {
        return new NearbyFragment();
    }


}
