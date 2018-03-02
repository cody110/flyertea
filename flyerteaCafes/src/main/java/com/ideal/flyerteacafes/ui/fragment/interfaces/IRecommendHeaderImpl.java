package com.ideal.flyerteacafes.ui.fragment.interfaces;

import com.ideal.flyerteacafes.model.entity.InterlocutionBean;

import java.util.List;

/**
 * Created by fly on 2017/6/26.
 */

public interface IRecommendHeaderImpl {


    /**
     * 热门互动
     * @param datas
     */
    void callbackInterlocutionList(List<InterlocutionBean> datas);
}
