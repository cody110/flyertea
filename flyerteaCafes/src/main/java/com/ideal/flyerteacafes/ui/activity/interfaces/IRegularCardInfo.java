package com.ideal.flyerteacafes.ui.activity.interfaces;

import com.ideal.flyerteacafes.model.entity.UserCardBean;

/**
 * Created by fly on 2017/6/5.
 */

public interface IRegularCardInfo {

    void initViewPager(UserCardBean userCardBean, int index);
    void disableButton();

}
