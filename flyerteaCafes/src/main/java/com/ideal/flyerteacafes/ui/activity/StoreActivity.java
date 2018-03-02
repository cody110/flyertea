package com.ideal.flyerteacafes.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * @description: 有赞商城
 *
 * @author: Cindy
 *
 * @date: 2016/8/10 16:23
 *
 * @version V6.5.0
 */
@ContentView(R.layout.activity_store)
public class StoreActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    void click(View v){
        finish();
    }

}
