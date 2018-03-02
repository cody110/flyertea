package com.ideal.flyerteacafes.ui.activity.base;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.fragment.page.Base.BaseFragment;
import com.ideal.flyerteacafes.ui.view.ToolBar;
import com.ideal.flyerteacafes.utils.tools.DateUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by fly on 2017/8/31.
 */
@ContentView(R.layout.add_fm_layout)
public abstract class AddFmActivity extends BaseActivity {

    @ViewInject(R.id.toolbar)
    public ToolBar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initVariables();
        initViews();
        setToolBar(toolBar);
        addFragment();
    }

    BaseFragment fragment;
    String fmTagName;

    private void addFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fmTagName != null)
            fragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(fmTagName);
        if (fragment == null) {
            fragment = createFragment();
            fmTagName = fragment.getClass().getName() + DateUtil.getDateline();
            transaction.add(R.id.fragment_layout, fragment, fmTagName);
        } else {
            transaction.show(fragment);
        }
        transaction.commit();
    }


    protected abstract void setToolBar(ToolBar toolBar);

    protected abstract BaseFragment createFragment();


    @Event(R.id.toolbar_left)
    private void click(View view) {
        onBackPressed();
    }


}
