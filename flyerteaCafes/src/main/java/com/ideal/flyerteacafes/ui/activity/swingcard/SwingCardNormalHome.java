package com.ideal.flyerteacafes.ui.activity.swingcard;

import android.os.Bundle;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

/**
 * Created by fly on 2017/4/12.
 */
@ContentView(R.layout.activity_swingcard_normalhome)
public class SwingCardNormalHome extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Event({R.id.toolbar_left})
    private void onclick(View view) {
        finish();
    }


}
