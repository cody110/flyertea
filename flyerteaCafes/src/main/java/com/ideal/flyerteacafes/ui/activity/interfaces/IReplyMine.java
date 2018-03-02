package com.ideal.flyerteacafes.ui.activity.interfaces;

import com.ideal.flyerteacafes.model.entity.NotificationBean;

import java.util.List;

/**
 * Created by Cindy on 2017/3/23.
 */
public interface IReplyMine extends IDialog{

   /**
    * 回复我的
    * @param replyMineList
     */
   void callbackReplyMine(List<NotificationBean> replyMineList);

    /**
     * 标记全部消息为已读
     */
   void  callbackMarkMessageReads(String result);

    /**
     * 标记某条消息为已读
     */
   void  callbackMarkPositionReads(String result);
}
