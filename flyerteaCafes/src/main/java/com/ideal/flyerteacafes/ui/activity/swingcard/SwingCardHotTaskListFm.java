package com.ideal.flyerteacafes.ui.activity.swingcard;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.adapters.HotTaskAdapter;
import com.ideal.flyerteacafes.adapters.base.CommonAdapter;
import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.TaskNameBean;
import com.ideal.flyerteacafes.ui.fragment.page.Base.NewPullRefreshFragment;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.List;

/**
 * Created by fly on 2017/4/12.
 * 刷卡任务 -热门任务
 */

public class SwingCardHotTaskListFm extends NewPullRefreshFragment<TaskNameBean> {


    @Override
    public void initViews() {
        super.initViews();
        setListviewDivider(null);
    }

    @Override
    protected CommonAdapter<TaskNameBean> createAdapter(List<TaskNameBean> datas) {
        return new HotTaskAdapter(mActivity, datas, R.layout.item_hot_task);
    }

    @Override
    protected PullRefreshPresenter<TaskNameBean> createPresenter() {
        return new PullRefreshPresenter<TaskNameBean>() {
            @Override
            public void requestDatas() {
                getDialog().proDialogShow();
                FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_SWINGCARD_TASKNAME);
                params.addQueryStringParameter("bankid", "");
                params.addQueryStringParameter("channelid", "");
                params.addQueryStringParameter("keyword", "");
                params.addQueryStringParameter("type", "system");
                XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.LIST_KEY_TASKNAME, TaskNameBean.class));
            }
        };
    }
}
