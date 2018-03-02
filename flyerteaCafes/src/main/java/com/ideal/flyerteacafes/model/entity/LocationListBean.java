package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fly on 2017/10/17.
 */

public class LocationListBean implements Serializable {

    private List<LocationBean> hotels;
    private List<LocationBean> airports;
    private List<LocationBean> lounges;
    private List<TagBean> hoteltags;
    private List<TagBean> airporttags;
    private List<TagBean> loungetags;

    public List<TagBean> getHoteltags() {
        return hoteltags;
    }

    public List<TagBean> getAirporttags() {
        return airporttags;
    }

    public List<TagBean> getLoungetags() {
        return loungetags;
    }

    public List<LocationBean> getHotels() {
        return hotels;
    }

    public List<LocationBean> getAirports() {
        return airports;
    }

    public List<LocationBean> getLounges() {
        return lounges;
    }

    public static class LocationBean implements Serializable {

        private String id;
        private String name;
        private String address;
        private String type;
        private String distance;
        private String lng;
        private String lat;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public String getType() {
            return type;
        }

        public String getDistance() {
            return distance;
        }

        public String getLng() {
            return lng;
        }

        public String getLat() {
            return lat;
        }
    }
}
