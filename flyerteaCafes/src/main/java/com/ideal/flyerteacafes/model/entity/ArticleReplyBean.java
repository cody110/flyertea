package com.ideal.flyerteacafes.model.entity;

import java.util.List;

/**
 * Created by fly on 2016/11/25.
 */

public class ArticleReplyBean {

    private String pid;
    private String tid;
    private String fid;
    private String authorid;
    private String author;
    private String message;
    private String dbdateline;
    private Object groupname;
    private String gender;
    private String has_sm;
    private String avatar;
    private String authortitle;
    private List<Attachments> attachments;

    public String getAuthortitle() {
        return authortitle;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDbdateline() {
        return dbdateline;
    }

    public void setDbdateline(String dbdateline) {
        this.dbdateline = dbdateline;
    }

    public Object getGroupname() {
        return groupname;
    }

    public void setGroupname(Object groupname) {
        this.groupname = groupname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHas_sm() {
        return has_sm;
    }

    public void setHas_sm(String has_sm) {
        this.has_sm = has_sm;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Attachments> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachments> attachments) {
        this.attachments = attachments;
    }
}
