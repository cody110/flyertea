package com.ideal.flyerteacafes.ui.fragment.presenter;

import android.content.Intent;

import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.ThreadSubjectBean;
import com.ideal.flyerteacafes.model.entity.ThreadTagBean;
import com.ideal.flyerteacafes.ui.activity.presenter.AutoPlayListFmPresenter;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

/**
 * Created by fly on 2017/11/30.
 */

public class ThreadTagListPresenter extends AutoPlayListFmPresenter {

    ThreadTagBean threadTagBean;

    @Override
    protected void getIntentDatas(Intent intent) {
        threadTagBean = (ThreadTagBean) intent.getSerializableExtra("data");
    }

    @Override
    public void requestDatas() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_TAG_THREAD_REPORT_LIST);
        params.addQueryStringParameter("type", "thread");
        params.addQueryStringParameter("tagid", threadTagBean.getTagid());
        params.addQueryStringParameter("page", String.valueOf(page));
        XutilsHttp.Get(params, new VideoListDataHandlerCallback(ListDataCallback.LIST_KEY_TAG_THREAD_LIST, ThreadSubjectBean.class));
    }
}
