package com.ideal.flyerteacafes.ui.fragment.interfaces;

/**
 * Created by fly on 2016/1/21.
 */
public interface HomePagerImpl {

    /**
     * 改变签到回调
     * @param bol 是否签到
     */
    void chooseSigninView(boolean bol);

    /**
     * 没有登录
     */
    void notLogin();

}
