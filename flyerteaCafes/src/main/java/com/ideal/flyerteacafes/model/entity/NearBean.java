package com.ideal.flyerteacafes.model.entity;

import java.util.List;

/**
 * 附近的人bean
 *
 * @author fly
 */
public class NearBean extends BaseBean {

    List<NearInfo> list;

    public List<NearInfo> getList() {
        return list;
    }

    public void setList(List<NearInfo> list) {
        this.list = list;
    }

    public static class NearInfo {

        private String uid;
        private String username;
        private String mobilestatus;
        private String emailstatus;
        private String adminid;
        private String groupid;
        private String regdate;
        private String credits;
        private String mapx;
        private String mapy;
        private String locationtime;
        private String token;
        private String tokentime;
        private String gender;
        private String resideprovince;
        private String residecity;
        private String occupation;
        private String position;
        private String interest;
        private String field7;
        private String spacename;
        private String spacedescription;
        private String recentnote;
        private String spacenote;
        private String feedfriend;
        private String distance;
        private String follow;
        private String face;
        private String groupname;

        private String sightml;

        private String isfriend;//0不是好友

        public String getIsfriend() {
            return isfriend;
        }

        public void setIsfriend(String isfriend) {
            this.isfriend = isfriend;
        }

        public String getSightml() {
            return sightml;
        }

        public void setSightml(String sightml) {
            this.sightml = sightml;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobilestatus() {
            return mobilestatus;
        }

        public void setMobilestatus(String mobilestatus) {
            this.mobilestatus = mobilestatus;
        }

        public String getEmailstatus() {
            return emailstatus;
        }

        public void setEmailstatus(String emailstatus) {
            this.emailstatus = emailstatus;
        }

        public String getAdminid() {
            return adminid;
        }

        public void setAdminid(String adminid) {
            this.adminid = adminid;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getRegdate() {
            return regdate;
        }

        public void setRegdate(String regdate) {
            this.regdate = regdate;
        }

        public String getCredits() {
            return credits;
        }

        public void setCredits(String credits) {
            this.credits = credits;
        }

        public String getMapx() {
            return mapx;
        }

        public void setMapx(String mapx) {
            this.mapx = mapx;
        }

        public String getMapy() {
            return mapy;
        }

        public void setMapy(String mapy) {
            this.mapy = mapy;
        }

        public String getLocationtime() {
            return locationtime;
        }

        public void setLocationtime(String locationtime) {
            this.locationtime = locationtime;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTokentime() {
            return tokentime;
        }

        public void setTokentime(String tokentime) {
            this.tokentime = tokentime;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getResideprovince() {
            return resideprovince;
        }

        public void setResideprovince(String resideprovince) {
            this.resideprovince = resideprovince;
        }

        public String getResidecity() {
            return residecity;
        }

        public void setResidecity(String residecity) {
            this.residecity = residecity;
        }

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public String getField7() {
            return field7;
        }

        public void setField7(String field7) {
            this.field7 = field7;
        }

        public String getSpacename() {
            return spacename;
        }

        public void setSpacename(String spacename) {
            this.spacename = spacename;
        }

        public String getSpacedescription() {
            return spacedescription;
        }

        public void setSpacedescription(String spacedescription) {
            this.spacedescription = spacedescription;
        }

        public String getRecentnote() {
            return recentnote;
        }

        public void setRecentnote(String recentnote) {
            this.recentnote = recentnote;
        }

        public String getSpacenote() {
            return spacenote;
        }

        public void setSpacenote(String spacenote) {
            this.spacenote = spacenote;
        }

        public String getFeedfriend() {
            return feedfriend;
        }

        public void setFeedfriend(String feedfriend) {
            this.feedfriend = feedfriend;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getFollow() {
            return follow;
        }

        public void setFollow(String follow) {
            this.follow = follow;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }


    }

}
