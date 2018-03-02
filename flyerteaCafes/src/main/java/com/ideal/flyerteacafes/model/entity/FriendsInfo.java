package com.ideal.flyerteacafes.model.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "FriendsInfo")
public class FriendsInfo {

    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "uid")
    private String uid;
    @Column(name = "fuid")
    private String fuid;
    @Column(name = "fusername")
    private String fusername;
    @Column(name = "gid")
    private String gid;
    @Column(name = "num")
    private String num;
    @Column(name = "dateline")
    private String dateline;
    @Column(name = "isfriend")
    private String isfriend;
    @Column(name = "username")
    private String username;
    @Column(name = "regdate")
    private String regdate;
    @Column(name = "mapx")
    private String mapx;
    @Column(name = "mapy")
    private String mapy;
    @Column(name = "locationtime")
    private String locationtime;
    @Column(name = "spacename")
    private String spacename;
    @Column(name = "spacedescription")
    private String spacedescription;
    @Column(name = "recentnote")
    private String recentnote;
    @Column(name = "spacenote")
    private String spacenote;
    @Column(name = "feedfriend")
    private String feedfriend;
    @Column(name = "follow")
    private String follow;
    @Column(name = "face")
    private String face;
    @Column(name = "sightml")
    private String sightml;
    @Column(name = "sortLetters")
    private String sortLetters;//拼音 排序
    @Column(name = "state")
    private int state;//是否还是朋友  0 是，1不是

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public String getFuid() {
        return fuid;
    }

    public void setFuid(String fuid) {
        this.fuid = fuid;
    }

    public String getFusername() {
        return fusername;
    }

    public void setFusername(String fusername) {
        this.fusername = fusername;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(String isfriend) {
        this.isfriend = isfriend;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
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

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
