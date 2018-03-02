package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

/**
 * Created by fly on 2016/5/19.
 */
public class TopicBean implements Serializable {


    public static final String TYPE_FID="fid";

    private String ctid;
    private String name;
    private String desc;
    private String users;
    private String views;
    private String permission;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCtid() {
        return ctid;
    }

    public void setCtid(String ctid) {
        this.ctid = ctid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getPermission() {
        return permission;
    }
}
