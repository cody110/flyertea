package com.ideal.flyerteacafes.model.entity;


import java.io.Serializable;

/**
 * Created by fly on 2016/3/4.
 */
public class HotelsInfo implements Serializable{
    private String id;
    private String title;
    private String city_name_cn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity_name_cn() {
        return city_name_cn;
    }

    public void setCity_name_cn(String city_name_cn) {
        this.city_name_cn = city_name_cn;
    }
}
