package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Cindy on 2016/11/15.
 */
public class CommunitySubBean implements Serializable {

    private String fid;
    private String type;
    private String parentfid;
    private String themecolor;
    private String icon;
    private String name;
    private String todayposts;

    private List<CommunitySubTypeBean> types;
    private List<CommunitySubTypeBean> subtypes;
    private List<CommunitySubTypeBean> relatedgroup;

    public String getTodayposts() {
        return todayposts;
    }

    public void setTodayposts(String todayposts) {
        this.todayposts = todayposts;
    }

    /**
     * 本地判断逻辑 ，是否选择
     */
    private boolean isSelect = false;


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

    public List<CommunitySubTypeBean> getSubforumList() {
        return types;
    }

    public List<CommunitySubTypeBean> getSubtypesList() {
        return subtypes;
    }

    public List<CommunitySubTypeBean> getRelatedgroupList() {
        return relatedgroup;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }


    @Override
    public String toString() {
        return "CommunitySubBean{" +
                "fid='" + fid + '\'' +
                ", type='" + type + '\'' +
                ", parentfid='" + parentfid + '\'' +
                ", themecolor='" + themecolor + '\'' +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", todayposts='" + todayposts + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }
}
