package com.ideal.flyerteacafes.model.entity;

import java.io.Serializable;

/**
 * Created by fly on 2018/1/19.
 */

public class ThreadSearchForumthreads implements Serializable{

    private int fid;
    private String fname;
    private int threads;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }
}
