package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

/**
 * 酒店
 *
 * @author fly
 */
public class HotelBean extends BaseBean implements Serializable {

    // 酒店介绍说明
    private String info;
    // 酒店所提供的网站预订
    private String web_site;
    // 酒店所提供的电话预订
    private String telephone;
    // 酒店所提供的AppStore上的下载地址
    private String app_web_site;
    // 酒店所提供的AppStore上的应用介绍
    private String app_info;
    // 酒店所提供的AppStore上的icon url地址
    private String app_icon_url;


    private String hotel_name;
    private int hotel_id;
    private String hotel_icon_url;

    public String getHotel_icon_url() {
        return hotel_icon_url;
    }

    public void setHotel_icon_url(String hotel_icon_url) {
        this.hotel_icon_url = hotel_icon_url;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getWeb_site() {
        return web_site;
    }

    public void setWeb_site(String web_site) {
        this.web_site = web_site;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getApp_web_site() {
        return app_web_site;
    }

    public void setApp_web_site(String app_web_site) {
        this.app_web_site = app_web_site;
    }

    public String getApp_info() {
        return app_info;
    }

    public void setApp_info(String app_info) {
        this.app_info = app_info;
    }

    public String getApp_icon_url() {
        return app_icon_url;
    }

    public void setApp_icon_url(String app_icon_url) {
        this.app_icon_url = app_icon_url;
    }

}
