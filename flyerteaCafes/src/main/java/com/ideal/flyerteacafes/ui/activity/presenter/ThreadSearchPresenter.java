package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.dal.SearchHistoryHelper;
import com.ideal.flyerteacafes.model.entity.SearchHistoryBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.IThreadSearch;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2016/12/16.
 */

public class ThreadSearchPresenter extends BasePresenter<IThreadSearch> {

    public List<SearchHistoryBean> datas = new ArrayList<>();
    private SearchHistoryHelper historyHelper = new SearchHistoryHelper();

    private int type = SearchHistoryHelper.TYPE_THREAD_CODY;


    @Override
    public void init(Activity activity) {
        super.init(activity);
        String type_name = activity.getIntent().getStringExtra(IntentKey.ThreadSearch.BUNDLE_KEY);
        if (TextUtils.equals(type_name, IntentKey.ThreadSearch.TYPE_REPORT)) {
            type = SearchHistoryHelper.TYPE_REPORT_CODY;
        } else if (TextUtils.equals(type_name, IntentKey.ThreadSearch.TYPE_RAIDERS)) {
            type = SearchHistoryHelper.TYPE_RAIDERS_CODY;
        } else {
            type = SearchHistoryHelper.TYPE_THREAD_CODY;
        }

        getHistoryList();
    }

    /**
     * 联想词接口
     *
     * @param value
     */
    public void requestAssocword(final String value) {
        FlyRequestParams params = new FlyRequestParams(Utils.HttpRequest.HTTP_ASSOCWORD);
        params.addQueryStringParameter("kw", value);
        XutilsHttp.Get(params, new PStringCallback() {
            @Override
            public void flySuccess(String result) {
                if (!isViewAttached()) return;
                datas.clear();
                List<SearchHistoryBean> assocwordList = JsonAnalysis.getSearchAssocwordList(result);
                if (assocwordList != null) {
                    datas.addAll(assocwordList);
                }
                getView().callBackListData(datas,value);
            }
        });
    }

    /**
     * 历史记录
     */
    public void getHistoryList() {
        datas.clear();
        List<SearchHistoryBean> historyList = historyHelper.getSearchHistoryList(type);
        if (historyList != null) {
            datas.addAll(historyList);
        }
        if (datas.size() > 0) {
            SearchHistoryBean bean = new SearchHistoryBean();
            bean.setSearchName(context.getResources().getString(R.string.search_clear_history));
            datas.add(bean);
        }
        getView().callBackListData(datas,null);

    }

    /**
     * 点击处理
     *
     * @param pos
     */
    public void itemClick(int pos) {
        if (TextUtils.equals(datas.get(pos).getSearchName(), context.getResources().getString(R.string.search_clear_history))) {
            historyHelper.deleteSearchHistory(type);
            datas.clear();
            getView().callBackListData(datas,null);
        } else {
            getView().setEtText(datas.get(pos).getSearchName());
            saveHistory(datas.get(pos).getSearchName());
        }
    }

    /**
     * 保存记录
     *
     * @param text
     */
    public void saveHistory(String text) {
        if (TextUtils.isEmpty(text)) return;
        List<SearchHistoryBean> historyList = historyHelper.getSearchHistoryList(type);
        if (historyList != null) {
            for (SearchHistoryBean bean : historyList) {
                if (TextUtils.equals(bean.getSearchName(), text)) {
                    return;
                }
            }
        }
        SearchHistoryBean saveBean = new SearchHistoryBean();
        saveBean.setType(type);
        saveBean.setSearchName(text);
        historyHelper.saveBean(saveBean);
    }


}
