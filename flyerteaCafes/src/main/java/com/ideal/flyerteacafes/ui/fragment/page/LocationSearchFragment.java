package com.ideal.flyerteacafes.ui.fragment.page;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.adapters.base.ViewHolder;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.LocationListBean;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.List;

/**
 * Created by fly on 2017/11/2.
 */

public class LocationSearchFragment extends NewPullRefreshFragment<LocationListBean.LocationBean> {


    @Override
    public void initViews() {
        super.initViews();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", mPresenter.getDatas().get(i));
                jumpActivitySetResult(bundle);
            }
        });
    }

    String kw;

    @Override
    protected CommonAdapter<LocationListBean.LocationBean> createAdapter(List<LocationListBean.LocationBean> datas) {
        if (DataUtils.isEmpty(datas)) {
            ToastUtils.showToast("未搜索到结果");
        }
        return new CommonAdapter<LocationListBean.LocationBean>(mActivity, datas, R.layout.listview_selection_item) {

            @Override
            public void convert(ViewHolder holder, LocationListBean.LocationBean loca) {
                holder.setText(R.id.selection_text, loca.getName());
                holder.setText(R.id.location_jiedao, loca.getAddress());
            }
        };
    }

    @Override
    protected PullRefreshPresenter<LocationListBean.LocationBean> createPresenter() {


        return new PullRefreshPresenter<LocationListBean.LocationBean>() {

            @Override
            public void init(Activity activity) {

            }

            @Override
            public void requestDatas() {
                FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_SERACH_LOCAION);
                params.addQueryStringParameter("kw", kw);
                XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.LIST_KEY_SERACH_LOCAION, LocationListBean.LocationBean.class));
            }
        };
    }

    public void requestSearch(String kw) {
        this.kw = kw;
        mPresenter.requestDatas();
    }
}
