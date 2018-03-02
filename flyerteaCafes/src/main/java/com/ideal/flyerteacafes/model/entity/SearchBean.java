package com.ideal.flyerteacafes.model.entity;

public class SearchBean {
    //标识对应帖子id
    private int tid;
    //此贴的最新回复数
    private int replies;
    //此贴对应的标题或摘要
    private String subject;
    //此贴对应发帖时间
    private String dateline;
    //此贴对应的所属子模块名称
    private String name;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SearchBean [tid=" + tid + ", replies=" + replies + ", subject="
                + subject + ", dateline=" + dateline + ", name=" + name + "]";
    }


}
