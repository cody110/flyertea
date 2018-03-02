package com.ideal.flyerteacafes.ui.fragment.page.tab.hometab;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.InterlocutionAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.TypeMode;
import com.ideal.flyerteacafes.model.entity.ThreadSubjectBean;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.List;

/**
 * Created by fly on 2016/6/3.
 */

public class InterlocutionFragment extends NewPullRefreshFragment<ThreadSubjectBean> {

    public static InterlocutionFragment getFragment(TypeMode typeMode) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", typeMode);
        InterlocutionFragment fragment = new InterlocutionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    TypeMode typeMode;


    @Override
    public void initViews() {
        super.initViews();
        typeMode = (TypeMode) getArguments().getSerializable("data");
//        setListViewMargins(getResources().getDimensionPixelSize(R.dimen.app_commen_margin),0,getResources().getDimensionPixelSize(R.dimen.app_commen_margin),0);
        setListviewDivider(null);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ThreadSubjectBean bean = adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString(ThreadPresenter.BUNDLE_TID, bean.getTid());
                bundle.putInt("position", position);
                jumpActivityForResult(ThreadActivity.class, bundle, 0);
            }
        });
    }

    @Override
    protected CommonAdapter<ThreadSubjectBean> createAdapter(List<ThreadSubjectBean> datas) {
        return new InterlocutionAdapter(mActivity, datas, R.layout.item_interlocution_layout);
    }

    @Override
    protected PullRefreshPresenter<ThreadSubjectBean> createPresenter() {
        return new PullRefreshPresenter<ThreadSubjectBean>() {

            @Override
            public void requestDatas() {
                FlyRequestParams params = new FlyRequestParams(splitPage(typeMode.getType()));
                if (mPresenter.getPage() > 1 && !TextUtils.isEmpty(mPresenter.getVer()))
                    params.addQueryStringParameter("ver", mPresenter.getVer());
                XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.LIST_KEY_FORUM_THREADLIST, ThreadSubjectBean.class));

            }
        };
    }

    public void removeIndex(int index) {
        mPresenter.removeIndexDatas(index);
    }

}