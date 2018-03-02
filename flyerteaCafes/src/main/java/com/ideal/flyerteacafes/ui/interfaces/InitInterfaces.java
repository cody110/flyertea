package com.ideal.flyerteacafes.ui.interfaces;

import android.view.View;

/**
 * Activity定义借口规范
 *
 * @author fly
 */
public interface InitInterfaces {

    /**
     * 初始化数据
     */
    public void initData();

    /**
     * 初始化view
     *
     * @param view
     */
    public void initView(View view);

    /**
     * 初始化控件
     *
     * @param view
     */
    public void initWidget(View view);

    /**
     * 初始化监听
     *
     * @param view
     */
    public void initLisenner(View view);
}
