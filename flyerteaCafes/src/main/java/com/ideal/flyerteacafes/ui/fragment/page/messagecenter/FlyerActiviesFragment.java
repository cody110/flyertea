package com.ideal.flyerteacafes.ui.fragment.page.messagecenter;

import android.view.View;
import android.widget.AdapterView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.NotificationAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.NotificationBean;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.ToastUtils;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cindy on 2017/3/20.
 */
public class FlyerActiviesFragment extends NewPullRefreshFragment<NotificationBean> {

    private List<NotificationBean> notificationBeanList=new ArrayList<>();

    @Override
    public void initViews() {
        super.initViews();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DataUtils.webViewClickUrlDispose(notificationBeanList.get(position).getUrl(), getActivity());
            }
        });
    }

    @Override
    protected CommonAdapter<NotificationBean> createAdapter(List<NotificationBean> datas) {
        this.notificationBeanList=datas;
        if (datas.size() == 0)
            ToastUtils.showToast("暂无数据");
        return new NotificationAdapter(getActivity(), datas, R.layout.item_message_center_other_message) ;
    }

    @Override
    protected PullRefreshPresenter<NotificationBean> createPresenter() {
        return new PullRefreshPresenter<NotificationBean>() {
            @Override
            public void requestDatas() {
                FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_FLYER_ACTIVIES);
                params.addQueryStringParameter("page", String.valueOf(page));
                params.addQueryStringParameter("perpage", String.valueOf(perpage));
                XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.LIST_KEY_NOTIFICATION, NotificationBean.class));
            }
        };
    }
}
