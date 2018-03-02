package com.ideal.flyerteacafes.ui.activity.swingcard;

import android.os.Bundle;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * Created by fly on 2017/4/24.
 */
@ContentView(R.layout.activity_task_interact)
public class SwingCardTaskInteract extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
}
