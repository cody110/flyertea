package com.ideal.flyerteacafes.ui.activity.interfaces;

import com.ideal.flyerteacafes.model.entity.HotKeyBean;

import java.util.List;

/**
 * Created by fly on 2018/1/19.
 */

public interface IThreadSearchActivity {

    /**
     * 去搜索页面
     *
     * @param value
     */
    void toSearchResult(String value);

    /**
     * 默认关键词配置
     */
    void callbackHotWordDatas(List<HotKeyBean.DefaultWord> datas);

}
