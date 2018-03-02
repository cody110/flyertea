package com.ideal.flyerteacafes.ui.activity.interfaces;

import com.ideal.flyerteacafes.model.entity.SearchHistoryBean;

import java.util.List;

/**
 * Created by fly on 2016/12/16.
 */

public interface IThreadSearch {

    /**
     * 联想词列表或历史搜索记录
     */
    void callBackListData(List<SearchHistoryBean> datas,String searchKey);

    void setEtText(String text);


}
