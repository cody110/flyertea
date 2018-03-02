package com.ideal.flyerteacafes.ui.activity.interfaces;

import com.ideal.flyerteacafes.model.entity.HotKeyBean;
import com.ideal.flyerteacafes.model.entity.SearchHistoryBean;

import java.util.List;

/**
 * Created by fly on 2018/1/19.
 */

public interface ISearchInitFm {

    /**
     * 搜索历史记录
     *
     * @param datas
     */
    void callbackSearchHistoryData(List<SearchHistoryBean> datas);


    /**
     * 热词
     */
    void callbackHotKeyData(List<HotKeyBean.Hotword> datas);

    /**
     * 默认词
     *
     * @param datas
     */
    void callbackDefaultWordData(List<HotKeyBean.DefaultWord> datas);


}
