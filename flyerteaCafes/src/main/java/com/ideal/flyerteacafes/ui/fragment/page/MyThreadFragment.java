package com.ideal.flyerteacafes.ui.fragment.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.MyThreadAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.MyThreadBean;
import com.ideal.flyerteacafes.ui.activity.presenter.ThreadPresenter;
import com.ideal.flyerteacafes.ui.activity.threads.ThreadActivity;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.List;

/**
 * 我的主贴
 *
 * @author fly
 */
public class MyThreadFragment extends NewPullRefreshFragment<MyThreadBean> {


    @Override
    public void initViews() {
        super.initViews();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Bundle bundle = new Bundle();
                bundle.putString(ThreadPresenter.BUNDLE_TID, datas.get(position).getTid());
                bundle.putInt(ThreadPresenter.BUNDLE_POS, position);
                if (datas.get(position).isNormal()) {
                    jumpActivityForResult(ThreadActivity.class, bundle, 0);
                } else {
                    jumpActivityForResult(ThreadActivity.class, bundle, 0);
                }
            }
        });
    }

    @Override
    protected CommonAdapter<MyThreadBean> createAdapter(List<MyThreadBean> datas) {
        return new MyThreadAdapter(getActivity(), datas, R.layout.item_mythread_mythread);
    }

    @Override
    protected PullRefreshPresenter<MyThreadBean> createPresenter() {

        return new PullRefreshPresenter<MyThreadBean>() {
            @Override
            public void requestDatas() {
                FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_MYPOST);
                params.addQueryStringParameter("page", page + "");
                params.addQueryStringParameter("perpage", perpage + "");
                XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.LIST_KEY_DATA_THREADS, MyThreadBean.class));
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && data != null) {
//            int pos = data.getIntExtra("position", -1);
//            if (pos != -1) {
//                datas.remove(pos);
//                adapter.notifyDataSetChanged();
//            }
            headerRefreshing();
        }
    }
}
