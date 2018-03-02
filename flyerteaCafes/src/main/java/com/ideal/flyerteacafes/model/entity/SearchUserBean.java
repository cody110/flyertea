package com.ideal.flyerteacafes.model.entity;

/**
 * Created by fly on 2018/1/19.
 */

public class SearchUserBean {


    private String uid;
    private String username;
    private String sightml;
    private String face;
    private String isfriend;
    private String groupname;

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
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

    public String getSightml() {
        return sightml;
    }

    public void setSightml(String sightml) {
        this.sightml = sightml;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(String isfriend) {
        this.isfriend = isfriend;
    }
}
