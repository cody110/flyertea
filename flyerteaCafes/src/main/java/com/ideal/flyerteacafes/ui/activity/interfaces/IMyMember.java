package com.ideal.flyerteacafes.ui.activity.interfaces;

import com.ideal.flyerteacafes.model.entity.MyTaskAllBean;
import com.ideal.flyerteacafes.model.entity.UserGroupsBean;

import java.util.List;

/**
 * Created by fly on 2017/5/23.
 */

public interface IMyMember {

    void callbackUserGroups(List<UserGroupsBean> datas);

    void callbackTaskAll(MyTaskAllBean allBean);
}
