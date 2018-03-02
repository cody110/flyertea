package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.ideal.flyerteacafes.model.entity.ThreadSearchForumthreads;
import com.ideal.flyerteacafes.model.entity.ThreadSearchResultBean;
import com.ideal.flyerteacafes.model.loca.DataBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.IThreadSearchResult;
import com.ideal.flyerteacafes.ui.fragment.presenter.PullRefreshPresenter;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.ideal.flyerteacafes.utils.LogFly;
import com.ideal.flyerteacafes.utils.Marks;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.List;

/**
 * Created by fly on 2018/1/19.
 */

public class ThreadSearchResultPresenter extends PullRefreshPresenter<Object> {


    protected String searchKey, type;
    ThreadSearchResultBean threadSearchResultBean;
    private java.lang.String fid = "0";
    private String orderby = "dateline";
    private Activity activity;


    @Override
    public void init(Activity activity) {
        this.activity = activity;
        fid = activity.getIntent().getStringExtra(IntentKey.BUNDLE_FID_KEY);
    }

    /**
     * 是否是来自指定版块
     */
    public boolean isAppoint() {
        return !TextUtils.isEmpty(activity.getIntent().getStringExtra(IntentKey.BUNDLE_FID_KEY));
    }

    public List<ThreadSearchForumthreads> getForumData() {
        if (threadSearchResultBean == null) return null;
        return threadSearchResultBean.getForumthreads();
    }

    @Override
    protected IThreadSearchResult<Object> getView() {
        return (IThreadSearchResult<Object>) super.getView();
    }

    public void setSearchKey(String searchKey) {
        this.page = 1;
        this.searchKey = searchKey;
        if (activity == null) {
            this.fid = "0";
        } else {
            fid = activity.getIntent().getStringExtra(IntentKey.BUNDLE_FID_KEY);
        }
        this.orderby = "dateline";
        datas.clear();
        getView().callbackData(datas);
        getView().clearPage();
        requestDatas();
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSearchKey() {
        return searchKey;
    }

    @Override
    public void requestDatas() {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_SEARCH_NEW);
        params.addQueryStringParameter(Marks.HttpSearchType.TYPE_KEY, type);
        params.addQueryStringParameter("kw", searchKey);
        params.addQueryStringParameter("perpage", String.valueOf(page));
        params.addQueryStringParameter("fid", fid);
        params.addQueryStringParameter("orderby", orderby);

        XutilsHttp.Get(params, new PDataCallback<ThreadSearchResultBean>() {
            @Override
            public void flySuccess(DataBean<ThreadSearchResultBean> result) {
                if (isViewAttached()) {
                    getView().pullToRefreshViewComplete();
                    if (result.getDataBean() == null) return;
                    hasNext = result.getDataBean().getHasnext() == 1;
                    threadSearchResultBean = result.getDataBean();
                    if (result.getDataBean().getThreads() != null) {
                        int oldSize = datas.size();
                        if (page == 1)
                            datas.clear();
                        datas.addAll(result.getDataBean().getThreads());
                        getView().callbackData(datas);
                        if (page == 1)
                            getView().headerRefreshSetListviewScrollLocation();
                        if (page > 1) {
                            if (oldSize < datas.size()) {
                                getView().footerRefreshSetListviewScrollLocation(oldSize);
                            } else {
                                page--;
                                setHasNext(false);
                                getView().notMoreData();
                            }
                        }
                    }

                    getView().callbackReustData(result.getDataBean());

                }

            }
        });
    }

    /**
     * 筛选版块
     *
     * @param fid
     */
    public void setScreenForum(String fid) {
        if (!TextUtils.equals(fid, this.fid)) {
            this.fid = fid;
            page = 1;
            requestDatas();
        }
    }

    /**
     * 排序
     *
     * @param value
     */
    public void setSort(String value) {
        if (!TextUtils.equals(orderby, value)) {
            this.orderby = value;
            page = 1;
            requestDatas();
        }
    }

    @Override
    public void onFooterRefresh() {
        if (hasNext) {
            page++;
            requestDatas();
        } else {
            getView().pullToRefreshViewComplete();
        }
    }
}
