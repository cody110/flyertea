package com.ideal.flyerteacafes.ui.activity.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.controls.RPLinearLayout;
import com.ideal.flyerteacafes.ui.view.ToolBar;
import com.ideal.flyerteacafes.utils.FragmentMangerUtils;
import com.ideal.flyerteacafes.utils.tools.V;
import com.ideal.flyerteacafes.ui.view.AppTitleBar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2015/11/14.
 */

public abstract class BaseToolbarActivity extends BaseActivity {


    protected static final int fBodyViewId = R.id.basic_content_layout;
    protected ToolBar mToolbar;
    protected FrameLayout mContentContainer;
    protected RPLinearLayout rootView;

    protected abstract void setTitleBar(ToolBar mToolbar);

    protected abstract View createBodyView(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        mToolbar = V.f(this, R.id.basic_titlebar_layout);
        mContentContainer = V.f(this, R.id.basic_content_layout);
        rootView = V.f(this, R.id.basic_root);
        mToolbar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarLeftClick(v);
            }
        });
        mToolbar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarRightClick(v);
            }
        });
        setTitleBar(mToolbar);
        View contentView = createBodyView(savedInstanceState);
        if (contentView != null)
            mContentContainer.addView(contentView);
    }


    /**
     * left click
     *
     * @param v
     */
    protected void toolbarLeftClick(View v) {
        finish();
    }

    protected void toolbarRightClick(View v) {

    }

    protected View getView(int layoutId) {
        View view = LayoutInflater.from(this).inflate(layoutId, null);
        return view;
    }


}
