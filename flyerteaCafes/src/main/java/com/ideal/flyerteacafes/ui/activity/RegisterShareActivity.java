package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.fragment.page.login.MobileRegisterFragment;
import com.umeng.socialize.UMShareAPI;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

/**
 * Created by young on 2015/12/25.
 */
@ContentView(R.layout.activity_regist)
public class RegisterShareActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

    }

    @Event({R.id.toolbar_left, R.id.toolbar_right})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left:
                finish();
                break;

            case R.id.toolbar_right:
                jumpActivity(LoginVideoActivity.class, null);
                finish();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MobileRegisterFragment.REQUEST_CODE_PERFECT_DATUM || requestCode == MobileRegisterFragment.REQUEST_CODE_BIND_CHOOSE) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        } else {
            UMShareAPI.get(this.getApplicationContext()).onActivityResult(requestCode, resultCode, data);
        }


    }

}
