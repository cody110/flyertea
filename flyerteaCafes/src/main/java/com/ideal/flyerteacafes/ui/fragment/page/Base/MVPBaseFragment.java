package com.ideal.flyerteacafes.ui.fragment.page.Base;

import android.os.Bundle;

import com.ideal.flyerteacafes.ui.activity.presenter.BasePresenter;

public abstract class MVPBaseFragment<V, T extends BasePresenter<V>> extends BaseFragment {

    protected T mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        mPresenter.attachBase(mActivity);
        mPresenter.attachDialog(mActivity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }


    protected abstract T createPresenter();
}
