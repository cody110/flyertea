package com.ideal.flyerteacafes.ui.fragment.presenter;

import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.SystemMessageBean;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

/**
 * Created by fly on 2017/12/7.
 */

public class SystemMessagePresenter extends PullRefreshPresenter<SystemMessageBean> {

    @Override
    public void requestDatas() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_SYSTEM_MESSAGE);
        params.addQueryStringParameter("page", String.valueOf(page));
        XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.List_KEY_LIST, SystemMessageBean.class));
    }

    /**
     * 设置单条消息为已读
     *
     * @param index
     */
    public void setMessageRead(int index) {
        String pmid = datas.get(index).getId();
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_SYSTEM_SET_READ);
        params.addQueryStringParameter("pmid", pmid);
        XutilsHttp.Get(params, null);
    }
}
