package com.ideal.flyerteacafes.ui.fragment.interfaces;

import com.ideal.flyerteacafes.model.entity.NotificationBean;
import com.ideal.flyerteacafes.model.entity.PersonalBean;

/**
 * Created by Cindy on 2017/3/27.
 */
public interface IMessageFm {

    /**
     * 飞客活动
     * @param flyerActivies
     */
    void callbackFlyerActivies(NotificationBean flyerActivies);

    /**
     * 消息中心消息数量
     * @param personalBean
     */
    void callbackPersonal(PersonalBean personalBean);
}
