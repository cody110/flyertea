package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

/**
 * Created by fly on 2016/6/6.
 */
@ContentView(R.layout.activity_freshman)
public class FreshmanActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Event(R.id.toolbar_left)
    private void click(View v){
        finish();
    }
}
