package com.ideal.flyerteacafes.ui.activity.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.ideal.flyerteacafes.callback.ListDataCallback;
import com.ideal.flyerteacafes.dal.SearchHistoryHelper;
import com.ideal.flyerteacafes.model.entity.HotKeyBean;
import com.ideal.flyerteacafes.model.entity.SearchHistoryBean;
import com.ideal.flyerteacafes.model.loca.ListDataBean;
import com.ideal.flyerteacafes.ui.activity.interfaces.ISearchInitFm;
import com.ideal.flyerteacafes.utils.DataUtils;
import com.ideal.flyerteacafes.utils.IntentKey;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2018/1/19.
 */

public class SearchInitFmPresenter extends BasePresenter<ISearchInitFm> {


    private SearchHistoryHelper historyHelper = new SearchHistoryHelper();

    /**
     * 历史记录
     */
    List<SearchHistoryBean> searchHistoryBeanList = new ArrayList<>();

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
        requestHotkey();
    }


    /**
     * 历史记录
     */
    public void getHistoryList() {
        searchHistoryBeanList.clear();
        List<SearchHistoryBean> historyList = historyHelper.getSearchHistoryList();
        if (historyList != null) {
            searchHistoryBeanList.addAll(historyList);
        }
        getView().callbackSearchHistoryData(searchHistoryBeanList);
    }


    /**
     * 热词接口
     */
    private void requestHotkey() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_HOTKEY), new PListDataCallback(ListDataCallback.LIST_KEY_HOTWORD, HotKeyBean.Hotword.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (isViewAttached()) {
                    if (!DataUtils.isEmpty(result.getDataList())) {
                        getView().callbackHotKeyData(result.getDataList());

                    }
                }
            }
        });
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_HOTKEY), new PListDataCallback(ListDataCallback.LIST_KEY_DEFAULT_WORD, HotKeyBean.DefaultWord.class) {
            @Override
            public void flySuccess(ListDataBean result) {
                if (isViewAttached()) {
                    if (!DataUtils.isEmpty(result.getDataList())) {
                        getView().callbackDefaultWordData(result.getDataList());

                    }
                }
            }
        });
    }


    /**
     * 保存记录
     *
     * @param text
     */
    public void saveHistory(String text) {
        if (TextUtils.isEmpty(text)) return;
        historyHelper.deleteBySearchName(text);
        SearchHistoryBean saveBean = new SearchHistoryBean();
        saveBean.setSearchName(text);
        historyHelper.saveBean(saveBean);
    }

    public void deleteAllHistory() {
        historyHelper.deleteSearchHistory();
        //刷新ui
        getHistoryList();
    }

}
