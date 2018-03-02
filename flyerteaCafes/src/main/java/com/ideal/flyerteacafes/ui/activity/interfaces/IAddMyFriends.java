package com.ideal.flyerteacafes.ui.activity.interfaces;

/**
 * Created by Cindy on 2017/3/24.
 */
public interface IAddMyFriends {

    /**
     * 同意添加好友
     */
    void callbackAgreeAddFriends(String result);

    /**
     * 拒绝添加好友
     * @param result
     */
    void callbackIgnoreAddFriends(String result);
}
