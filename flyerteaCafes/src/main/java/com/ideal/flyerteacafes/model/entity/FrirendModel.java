package com.ideal.flyerteacafes.model.entity;

import java.util.List;

public class FrirendModel {

    private String cookiepre;
    private String auth;
    private String saltkey;
    private String member_uid;
    private String member_username;
    private String member_avatar;
    private String groupid;
    private String groupname;
    private String formhash;
    private String ismoderator;
    private String readaccess;
    private NoticeModel notice;
    private String page;
    private String pagesize;
    private String count;
    private List<FriendsInfo> list;

    public String getCookiepre() {
        return cookiepre;
    }

    public void setCookiepre(String cookiepre) {
        this.cookiepre = cookiepre;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getSaltkey() {
        return saltkey;
    }

    public void setSaltkey(String saltkey) {
        this.saltkey = saltkey;
    }

    public String getMember_uid() {
        return member_uid;
    }

    public void setMember_uid(String member_uid) {
        this.member_uid = member_uid;
    }

    public String getMember_username() {
        return member_username;
    }

    public void setMember_username(String member_username) {
        this.member_username = member_username;
    }

    public String getMember_avatar() {
        return member_avatar;
    }

    public void setMember_avatar(String member_avatar) {
        this.member_avatar = member_avatar;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getFormhash() {
        return formhash;
    }

    public void setFormhash(String formhash) {
        this.formhash = formhash;
    }

    public String getIsmoderator() {
        return ismoderator;
    }

    public void setIsmoderator(String ismoderator) {
        this.ismoderator = ismoderator;
    }

    public String getReadaccess() {
        return readaccess;
    }

    public void setReadaccess(String readaccess) {
        this.readaccess = readaccess;
    }

    public NoticeModel getNotice() {
        return notice;
    }

    public void setNotice(NoticeModel notice) {
        this.notice = notice;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<FriendsInfo> getList() {
        return list;
    }

    public void setList(List<FriendsInfo> list) {
        this.list = list;
    }
}
