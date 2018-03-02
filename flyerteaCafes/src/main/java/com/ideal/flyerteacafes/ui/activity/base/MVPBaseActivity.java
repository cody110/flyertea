package com.ideal.flyerteacafes.ui.activity.base;

import android.os.Bundle;

import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.ui.activity.presenter.BasePresenter;

/**
 * Created by fly on 2016/1/13.
 */
public abstract class MVPBaseActivity<V ,T extends BasePresenter<V>> extends BaseActivity {

    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter=createPresenter();
        mPresenter.attachView((V)this);
        mPresenter.attachDialog(this);
        mPresenter.attachBase(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter.detachDialog();
        mPresenter.detachBase();
    }

    protected abstract T createPresenter();





}
