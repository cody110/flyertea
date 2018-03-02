package com.ideal.flyerteacafes.ui.fragment.presenter;

import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.ArticleTabBean;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

/**
 * Created by fly on 2017/6/29.
 */

public class ReportPresenter extends PullRefreshPresenter<ArticleTabBean> {

    public interface IGetSortid {
        String getSortid();
    }

    public String key, value, sort, requestUrl;


    public ReportPresenter setTypeKey(String key) {
        this.key = key;
        return this;
    }

    public ReportPresenter setTypeValue(String value) {
        this.value = value;
        return this;
    }

    public void setType(String key, String value) {
        this.key = key;
        this.value = value;
        getView().headerRefreshing();
    }

    public void setSort(String sort) {
        this.sort = sort;
        getView().headerRefreshing();
    }

    public ReportPresenter setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
        return this;
    }



    private IGetSortid iGetSortid;

    public ReportPresenter setIGetSortid(IGetSortid iGetSortid) {
        this.iGetSortid = iGetSortid;
        return this;
    }

    @Override
    public void requestDatas() {
        if (requestUrl == null) return;
        FlyRequestParams params = new FlyRequestParams(requestUrl);
        if (iGetSortid != null) {
            params.addQueryStringParameter("filter", "sortid");
            params.addQueryStringParameter("sortid", iGetSortid.getSortid());
            params.addQueryStringParameter("searchsort", "1");
            params.addQueryStringParameter(key, value);
            params.addQueryStringParameter("orderby", sort);
        }
        params.addQueryStringParameter("page", String.valueOf(page));
        XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.LIST_KEY_ARTICLE, ArticleTabBean.class));
    }
}
