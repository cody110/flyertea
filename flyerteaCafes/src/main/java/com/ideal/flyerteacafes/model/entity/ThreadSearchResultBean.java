package com.ideal.flyerteacafes.model.entity;

import com.ideal.flyerteacafes.utils.tools.DataTools;

import java.util.List;

/**
 * Created by fly on 2018/1/19.
 */

public class ThreadSearchResultBean {

    private List<SearchThreadBean> threads;
    private List<ThreadTagBean> tag;
    private List<ThreadSearchForumthreads> forumthreads;
    private String pagesize;
    private String hasnext;
    private String kw;

    public List<SearchThreadBean> getThreads() {
        return threads;
    }

    public void setThreads(List<SearchThreadBean> threads) {
        this.threads = threads;
    }

    public List<ThreadTagBean> getTag() {
        return tag;
    }

    public void setTag(List<ThreadTagBean> tag) {
        this.tag = tag;
    }

    public List<ThreadSearchForumthreads> getForumthreads() {
        return forumthreads;
    }

    public void setForumthreads(List<ThreadSearchForumthreads> forumthreads) {
        this.forumthreads = forumthreads;
    }

    public int getPagesize() {
        return DataTools.getInteger(pagesize);
    }


    public int getHasnext() {
        return DataTools.getInteger(hasnext);
    }


    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }
}
