package com.ideal.flyerteacafes.model.loca;

/**
 * Created by fly on 2016/12/9.
 */

public class LocationInfo {
    private String title;
    private String locaName;
    private String cityName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocaName() {
        return locaName;
    }

    public void setLocaName(String locaName) {
        this.locaName = locaName;
    }
}
