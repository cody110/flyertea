package com.ideal.flyerteacafes.ui.fragment.interfaces;

import com.ideal.flyerteacafes.model.entity.MyTaskAllBean;

/**
 * Created by fly on 2017/5/17.
 */

public interface IMyFragment {

    /**
     * 头像本地路径
     */
    void callbackFaceLocaPath(String locaPath);

    /**
     * 任务
     *
     * @param bean
     */
    void callbackTaskAll(MyTaskAllBean bean);

    /**
     * 是否签到
     * @param isSigin
     */
    void setViewByIsSigin(boolean isSigin);

    /**
     * 根据是否设置过生日，显示文案
     */
    void isSetBirthday(boolean bol);

}
