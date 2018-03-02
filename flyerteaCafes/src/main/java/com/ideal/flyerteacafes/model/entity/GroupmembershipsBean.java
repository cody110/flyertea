package com.ideal.flyerteacafes.model.entity;

import java.util.List;

/**
 * Created by fly on 2017/5/24.
 */

public class GroupmembershipsBean {

    private List<Type> hotelMembership;

    private List<Type> airlineMembership;

    public List<Type> getHotelMembership() {
        return hotelMembership;
    }

    public void setHotelMembership(List<Type> hotelMembership) {
        this.hotelMembership = hotelMembership;
    }

    public List<Type> getAirlineMembership() {
        return airlineMembership;
    }

    public void setAirlineMembership(List<Type> airlineMembership) {
        this.airlineMembership = airlineMembership;
    }

    public static class Type {

        private String groupid;
        private String name;
        private String membership_name;
        private String service_phone;
        private String type;
        private List<Memberships> memberships;

        public String getGroupid() {
            return groupid;
        }

        public String getName() {
            return name;
        }

        public String getMembership_name() {
            return membership_name;
        }

        public String getService_phone() {
            return service_phone;
        }

        public String getType() {
            return type;
        }

        public List<Memberships> getMemberships() {
            return memberships;
        }
    }

    public static class Memberships {
        private String name;
        private String mid;

        public String getMid() {
            return mid;
        }

        public String getName() {
            return name;
        }
    }

}
