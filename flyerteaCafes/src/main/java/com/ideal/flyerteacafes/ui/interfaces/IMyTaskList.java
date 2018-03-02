package com.ideal.flyerteacafes.ui.interfaces;

import com.ideal.flyerteacafes.model.entity.MyTaskAllBean;

/**
 * Created by fly on 2017/5/17.
 */

public interface IMyTaskList {

    /**
     *任务数据
     * @param allBean
     */
    void callbackTaskAll(MyTaskAllBean allBean);

}
