package com.ideal.flyerteacafes.ui.fragment.page.Base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;

public class BaseFragment extends Fragment {

    public BaseActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    public void jumpActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent();
        intent.setClass(mActivity, activity);
        startActivity(intent);
    }


    public void jumpActivity(Class<? extends Activity> activity, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mActivity, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void jumpActivityForResult(Class<? extends Activity> activity, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mActivity, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().startActivityForResult(intent, requestCode);
    }

    public void jumpActivitySetResult(Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mActivity.setResult(mActivity.RESULT_OK, intent);
        mActivity.finish();
    }

    /**
     * 初始化变量 intent的数据 activity内部变量
     */
    public void initVariables() {

    }


    /**
     * 加载布局文件，初始化控件，为控件挂上事件方法
     */
    public void initViews() {
    }


    /**
     * 加载mobileAPI获取数据
     */
    public void loadData() {
    }



}
