package com.ideal.flyerteacafes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.utils.tools.V;
import com.ideal.flyerteacafes.ui.view.AppTitleBar;

/**
 * Created by fly on 2016/1/6.
 * 第三方登录第一次，绑定已有账户和注册为新用户
 */
public class RegistStationFlyActivity extends BaseActivity implements View.OnClickListener{

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_station);
        bundle= getIntent().getExtras();
        initView();
    }

    private void initView(){
        AppTitleBar titleBar= V.f(this,R.id.regist_station_titlebar);
        titleBar.setTitleTxt("选择用户类型");
        titleBar.setRightOnClickListener(this);
        titleBar.leftView.setVisibility(View.INVISIBLE);
        titleBar.setRightTxt("取消");
        V.f(this, R.id.binding_to_btn).setOnClickListener(this);
        V.f(this, R.id.regist_to_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case AppTitleBar.fRightViewId:
                finish();
                break;
            case R.id.binding_to_btn:
                jumpActivityForResult(BindingFlyActivity.class, bundle, 0);
                break;
            case R.id.regist_to_btn:
                jumpActivityForResult(PerfectDatumActivity.class, bundle, 0);//268877
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            jumpActivitySetResult(null);
        }
    }
}
