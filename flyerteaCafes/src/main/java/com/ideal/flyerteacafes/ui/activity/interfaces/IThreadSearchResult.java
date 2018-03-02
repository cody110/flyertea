package com.ideal.flyerteacafes.ui.activity.interfaces;

import com.ideal.flyerteacafes.model.entity.ThreadSearchResultBean;
import com.ideal.flyerteacafes.ui.fragment.interfaces.IPullRefresh;

/**
 * Created by fly on 2018/1/19.
 */

public interface IThreadSearchResult<T> extends IPullRefresh<T> {


    /**
     * 所有数据
     *
     * @param data
     */
    void callbackReustData(Object data);

    /**
     * 清空页面
     */
    void clearPage();


}
