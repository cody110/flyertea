package com.ideal.flyerteacafes.model.loca;

import com.ideal.flyerteacafes.model.entity.BaseBean;

/**
 * Created by fly on 2016/3/10.
 */
public class DataBean<T>  extends BaseBean {

    T dataBean;

    public T getDataBean() {
        return dataBean;
    }

    public void setDataBean(T dataBean) {
        this.dataBean = dataBean;
    }
}
