package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.ReportSearchResultBean;
import com.ideal.flyerteacafes.model.entity.SearchReportBean;
import com.ideal.flyerteacafes.model.entity.ThreadSearchForumthreads;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.IReportSearchResult;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.lang.annotation.Retention;
import java.util.List;

/**
 * Created by fly on 2018/1/21.
 */

public class ReportSearchResultPresenter extends PullRefreshPresenter<SearchReportBean> {

    protected String searchKey, type;

    public String getType() {
        return type;
    }

    public String getSearchKey() {
        return searchKey;
    }

    @Override
    public void init(Activity activity) {
    }

    @Override
    public void requestDatas() {
        LogFly.e(Utils.HttpRequest.HTTP_SEARCH_NEW);
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_SEARCH_NEW);
        params.addQueryStringParameter(Marks.HttpSearchType.TYPE_KEY, type);
        params.addQueryStringParameter("kw", searchKey);
        XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.LIST_KEY_DATA_THREADS, SearchReportBean.class));
    }


    public void setSearchKey(String searchKey, String type) {
        page = 1;
        this.searchKey = searchKey;
        this.type = type;

        if (getView() instanceof IReportSearchResult) {
            datas.clear();
            ((IReportSearchResult) getView()).clearPage();
        }
        
        requestDatas();
    }


}
