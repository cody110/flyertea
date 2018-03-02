package com.ideal.flyerteacafes.model.entity;

import java.util.List;

/**
 * Created by fly on 2018/1/19.
 */

public class ReportSearchResultBean {

    private List<SearchReportBean> threads;
    //    private ThreadSearchTag tag;
    private List<ThreadSearchForumthreads> forumthreads;
    private int pagesize;
    private int hasnext;
    private String kw;

    public List<SearchReportBean> getThreads() {
        return threads;
    }

    public void setThreads(List<SearchReportBean> threads) {
        this.threads = threads;
    }

    public ThreadSearchTag getTag() {
        return null;
    }

//    public void setTag(ThreadSearchTag tag) {
//        this.tag = tag;
//    }

    public List<ThreadSearchForumthreads> getForumthreads() {
        return forumthreads;
    }

    public void setForumthreads(List<ThreadSearchForumthreads> forumthreads) {
        this.forumthreads = forumthreads;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getHasnext() {
        return hasnext;
    }

    public void setHasnext(int hasnext) {
        this.hasnext = hasnext;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }
}
