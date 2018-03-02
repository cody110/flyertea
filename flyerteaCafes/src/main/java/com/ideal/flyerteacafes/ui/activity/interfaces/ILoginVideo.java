package com.ideal.flyerteacafes.ui.activity.interfaces;

import android.os.Bundle;

import com.ideal.flyerteacafes.model.entity.LoginCodeResponse;

/**
 * Created by fly on 2017/6/2.
 */

public interface ILoginVideo{

    /**
     * 获取验证码的数据
     * @param data
     */
    void callbackGetCOdeData(LoginCodeResponse data);

    void loginSuccess();

    void threadLoginNotBind(Bundle bundle);

}
