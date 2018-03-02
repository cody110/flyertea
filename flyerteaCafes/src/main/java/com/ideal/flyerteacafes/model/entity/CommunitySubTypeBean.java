package com.ideal.flyerteacafes.model.entity;

/**
 * Created by fly on 2016/11/18.
 */

import java.io.Serializable;

/**
 * 三级版块
 */
public class CommunitySubTypeBean implements Serializable{

    private String fid;
    private String fname;


    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getName() {
        return fname;
    }

    public void setName(String name) {
        this.fname = name;
    }

    @Override
    public String toString() {
        return "CommunitySubTypeBean{" +
                "fid='" + fid + '\'' +
                ", name='" + fname + '\'' +
                '}';
    }
}
