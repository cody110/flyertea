package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;

import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.entity.SearchUserBean;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

/**
 * Created by fly on 2018/1/21.
 */

public class UserSearchResultPresenter extends PullRefreshPresenter<SearchUserBean> {


    private String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
        datas.clear();
        getView().callbackData(datas);
        requestDatas();
    }

    @Override
    public void init(Activity activity) {
    }

    @Override
    public void requestDatas() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_SEARCH_NEW);
        params.addQueryStringParameter(Marks.HttpSearchType.TYPE_KEY, Marks.HttpSearchType.TYPE_USER);
        params.addQueryStringParameter("kw", searchKey);
        XutilsHttp.Get(params, new ListDataHandlerCallback(ListDataCallback.List_KEY_LIST, SearchUserBean.class));
    }
}
