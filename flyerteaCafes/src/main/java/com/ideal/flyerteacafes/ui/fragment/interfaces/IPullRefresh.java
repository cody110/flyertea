package com.ideal.flyerteacafes.ui.fragment.interfaces;

import java.util.List;

/**
 * Created by fly on 2016/4/5.
 */
public interface IPullRefresh<T> {


    /**
     * 拉动作完成，回复初始样式
     */
    void pullToRefreshViewComplete();

    /**
     * 返回请求数据
     *
     * @param datas
     */
    void callbackData(List<T> datas);

    /**
     * 上拉加载，滚动listview让用户能看见新的数据
     */
    void footerRefreshSetListviewScrollLocation(int oldSize);


    /**
     * 没有更多下一页数据
     */
    void notMoreData();


    /**
     * 上拉刷新，listview滚动到初始位置
     */
    void headerRefreshSetListviewScrollLocation();


    /**
     * 手动调用显示头部下拉刷新
     */
    void headerRefreshing();


}
