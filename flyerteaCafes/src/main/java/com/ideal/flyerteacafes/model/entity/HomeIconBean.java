package com.ideal.flyerteacafes.model.entity;

import java.util.List;

/**
 * Created by Cindy on 2017/2/23.
 */
public class HomeIconBean {

    private List<HomeIconSubBean> icons;
    private String update_time;

    public List<HomeIconSubBean> getIcons() {
        return icons;
    }

    public void setIcons(List<HomeIconSubBean> icons) {
        this.icons = icons;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
