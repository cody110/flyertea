package com.ideal.flyerteacafes.model.entity;

import java.util.List;

/**
 * Created by fly on 2016/11/29.
 */

public class CommunitySubDetailsBean {


    private String fid;
    private String type;
    private String parentfid;
    private String themecolor;
    private String icon;
    private String name;
    private String threads;
    private String todayposts;
    private String posts;
    private String favtimes;

    private List<CommunitySubTypeBean> types;
    private List<CommunitySubTypeBean> relatedgroup;
    private List<CommunitySubTypeBean> subtypes;

    public List<CommunitySubTypeBean> getRelatedgroupList() {
        return relatedgroup;
    }
    public List<CommunitySubTypeBean> getSubtypesList() {
        return subtypes;
    }
    public List<CommunitySubTypeBean> getSubforumList() {
        return types;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentfid() {
        return parentfid;
    }

    public void setParentfid(String parentfid) {
        this.parentfid = parentfid;
    }

    public String getThemecolor() {
        return themecolor;
    }

    public void setThemecolor(String themecolor) {
        this.themecolor = themecolor;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThreads() {
        return threads;
    }

    public void setThreads(String threads) {
        this.threads = threads;
    }

    public String getTodayposts() {
        return todayposts;
    }

    public void setTodayposts(String todayposts) {
        this.todayposts = todayposts;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getFavtimes() {
        return favtimes;
    }

    public void setFavtimes(String favtimes) {
        this.favtimes = favtimes;
    }

}