package com.ideal.flyerteacafes.ui.fragment.presenter;

import android.app.Activity;
import android.content.Intent;

import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.presenter.BasePresenter;
import com.ideal.flyerteacafes.ui.fragment.interfaces.IPullRefresh;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2016/4/5.
 */
public abstract class PullRefreshPresenter<T> extends BasePresenter<IPullRefresh<T>> {

    protected int page = 1;

    protected int perpage = 10;//每页的条数
    protected String ver;

    //参数改变，接口未调用
    private boolean dataChagneNeedHeaderRefresh = false;

    public void setDataChagneNeedHeaderRefresh(boolean bol) {
        dataChagneNeedHeaderRefresh = bol;
    }

    /**
     * fragment 重新添加 ，数据改变，重新刷新
     */
    public void dataChangeHeaderRefresh() {
        if (dataChagneNeedHeaderRefresh) {
            getView().headerRefreshing();
        }
    }


    public String getVer() {
        return ver;
    }

    public int getPage() {
        return page;
    }

    protected List<T> datas = new ArrayList<>();


    public List<T> getDatas() {
        return datas;
    }


    @Override
    public void init(Activity activity) {
        getIntentDatas(activity.getIntent());
        if (isFirstLoadNeedHeader()) {
            getView().headerRefreshing();
        } else {
            requestDatas();
        }
    }


    /**
     * 首次加载是否需要显示header
     * 默认显示
     *
     * @return
     */
    public boolean isFirstLoadNeedHeader() {
        return true;
    }


    /**
     * 如果有要取intent里的数据，需要子类重写
     */
    protected void getIntentDatas(Intent intent) {

    }

    /**
     * 请求列表网络数据
     */
    public abstract void requestDatas();

    /**
     * 上拉加载更多
     */
    public void onFooterRefresh() {
        page++;
        requestDatas();
    }

    /**
     * 下拉刷新数据
     */
    public void onHeaderRefresh() {
        page = 1;
        requestDatas();
    }

    protected boolean hasNext = true;

    public void setHasNext(boolean bol) {
        this.hasNext = bol;
    }

    public boolean getHasNext() {
        return hasNext;
    }

    public class ListDataHandlerCallback extends PListDataCallback {

        public ListDataHandlerCallback(String listKey, Class tClass) {
            super(listKey, tClass);
        }


        @Override
        public void flySuccess(ListDataBean response) {
            if (!isViewAttached()) return;
            if (response.getDataList() != null) {
                hasNext = response.getHasNext();
                int oldSize = datas.size();
                if (page == 1)
                    datas.clear();
                datas.addAll(response.getDataList());
                ver = response.getVer();
                getView().callbackData(datas);
                if (page == 1)
                    getView().headerRefreshSetListviewScrollLocation();
                if (page > 1) {
                    if (oldSize < datas.size()) {
                        getView().footerRefreshSetListviewScrollLocation(oldSize);
                    } else {
                        page--;
                        hasNext = false;
                        getView().notMoreData();
                    }
                }
            }
        }

        @Override
        public void flyFinished() {
            super.flyFinished();
            getView().pullToRefreshViewComplete();
        }
    }

    public String splitPage(String url) {
        return url + "&page=" + page;
    }


    public void removeIndexDatas(int index) {
        if (index >= 0 && index < datas.size()) {
            datas.remove(index);
            getView().callbackData(datas);
        }
    }


}
