package com.ideal.flyerteacafes.ui.activity.interfaces;

import com.ideal.flyerteacafes.model.entity.MapBean;
import com.ideal.flyerteacafes.model.entity.TaskDetailsBean;

/**
 * Created by fly on 2017/5/9.
 */

public interface ISwingCardTaskDetails {
    void callbackTaskDetailsBean(TaskDetailsBean taskDetailsBean);

    void callbackDelete(MapBean result);
}
