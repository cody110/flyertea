package com.ideal.flyerteacafes.ui.fragment.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.ThreadSearchBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.search.ThreadSearchActivity;
import com.ideal.flyerteacafes.ui.fragment.interfaces.IThreadSearchFm;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

/**
 * Created by fly on 2016/12/19.
 */

public class ThreadSearchPresenter extends PullRefreshPresenter<ThreadSearchBean> {


    private IThreadSearchFm iThreadSearchFm;
    private String fid = "", kw = "", orderby;
    public String type;
    private boolean needForum = true;

    public void setKw(String kw) {
        this.kw = kw;
    }

    public String getKw() {
        return kw;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }


    public ThreadSearchPresenter setiThreadSearchFm(IThreadSearchFm iThreadSearchFm) {
        this.iThreadSearchFm = iThreadSearchFm;
        return this;
    }

    @Override
    public void init(Activity activity) {
        fid = activity.getIntent().getStringExtra(ThreadSearchActivity.BUNDLE_FID_KEY);
        type = activity.getIntent().getStringExtra(IntentKey.ThreadSearch.BUNDLE_KEY);
        if (TextUtils.isEmpty(type)) {
            type = IntentKey.ThreadSearch.TYPE_THREAD;
        }
    }

    @Override
    public void requestDatas() {

        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_THREAD_SEARCH);
        params.addQueryStringParameter("fid", fid);
        params.addQueryStringParameter("kw", kw);
        params.addQueryStringParameter("orderby", orderby);
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("perpage", String.valueOf(page));
        XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.LIST_KEY_DATA_THREADS, ThreadSearchBean.class) {
            @Override
            public void flySuccess(ListDataBean response) {
                if (!isViewAttached()) return;
                super.flySuccess(response);
                iThreadSearchFm.isNullResult(!(datas.size() > 0));
            }
        });
    }
}
