package com.ideal.flyerteacafes.ui.fragment.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.ThreadSubjectBean;
import com.ideal.flyerteacafes.model.entity.TopicBean;
import com.ideal.flyerteacafes.ui.activity.presenter.AutoPlayListFmPresenter;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

/**
 * Created by fly on 2017/10/23.
 */

public class TopicListPresenter extends AutoPlayListFmPresenter {

    private String ctid, orderby;
    private TopicBean topicBean;


    public TopicListPresenter setArguments(Bundle bundle) {
        orderby = bundle.getString("orderby");
        return this;
    }

    @Override
    protected void getIntentDatas(Intent intent) {
        super.getIntentDatas(intent);
        topicBean = (TopicBean) intent.getSerializableExtra("data");
        if (topicBean != null) {
            ctid = topicBean.getCtid();
        } else {
            ctid = intent.getStringExtra("ctid");
        }


    }

    @Override
    public void requestDatas() {
        final FlyRequestParams params;
        String listKey;
        //type=fid 一周热帖
        if (topicBean != null && TextUtils.equals(topicBean.getType(), TopicBean.TYPE_FID)) {
            params = new FlyRequestParams(Utils.HttpRequest.HTTP_APPHEAT);
            params.addQueryStringParameter("fid", ctid);
            listKey = ListDataCallback.LIST_KEY_DATA_FORUM_THREADLIST;
        } else {
            params = new FlyRequestParams(Utils.HttpRequest.HTTP_TOPIC_LIST);
            params.addQueryStringParameter("ctid", ctid);
            listKey = ListDataCallback.LIST_KEY_DATA_THREADS;
        }

        if (page > 1 && !TextUtils.isEmpty(ver)) {
            params.addQueryStringParameter("ver", ver);
        }
        params.addQueryStringParameter("page", page + "");
        params.addQueryStringParameter("orderby", orderby);

        XutilsHttp.Get(params, new VideoListDataHandlerCallback(listKey, ThreadSubjectBean.class));
    }
}
